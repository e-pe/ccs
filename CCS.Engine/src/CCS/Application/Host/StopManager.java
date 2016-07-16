/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.Application.Services.*;
import CCS.Application.Status.*;

/**
 * @version 1.0
 * @since July 7, 2009
 */
public class StopManager {
	
	private Participant participant;
	private Stop suggestedCurrentStop;
	private Queue<Stop> availableStops;
	private StopCollection suggestedStops;
	
	public StopManager(Participant participant){
		this.participant = participant;	
		this.availableStops = new LinkedList<Stop>();
		this.suggestedStops = new StopCollection();
	}
	
	public Session getSession(){
		return this.participant.getSession();
	}
	
	public StopStationService getStopStationService(){
		return (StopStationService)this.getSession().getApplication().getServices().get(StopStationService.class);
	}
	
	/**
	 * Gets all available stops during a tour.  
	 * @return
	 */
	public Stop getStop(StopType stopType){
		if(this.availableStops.isEmpty()){
			
			StopCollection stations = this.getStopStationService().getStopStations(stopType, 
					this.participant.getLocation());
		
			stations.write(this.availableStops);
		}
		
		Stop stop = this.availableStops.poll();
		
		if(stop != null && this.getIsStopInArea(stop)){
			this.suggestedStops.add(stop);
			
			this.getSession().setStatus(new Status(stop, 
					new StatusLookUpStopMessage(this.participant)));
		}
		
		this.suggestedCurrentStop = stop;
		
		return this.getSuggestedCurrentStop();
	}
	
	/**
	 * Gets a stop by stop id.
	 * @param stopId
	 * @return
	 */
	public Stop getStop(String stopId){
		Stop stop = this.suggestedStops.get(stopId);
		
		if(stop != null)
			return stop;
		
		return this.getSession().getAcceptedStops().get(stopId);
	}
	
	/**
	 * Gets the current stop that is displayed on the map of a participant.
	 * @return
	 */
	public Stop getCurrentStop(){
		Stop stop = this.getSuggestedCurrentStop();
		
		return stop != null ? stop : this.getAcceptedCurrentStop();
	}
	
	/**
	 * Gets the current stop that was suggested by ccs. 
	 * @return
	 */
	public Stop getSuggestedCurrentStop(){
		if(!this.getIsStopInArea(this.suggestedCurrentStop))
			this.suggestedCurrentStop = null;
		
		return this.suggestedCurrentStop;
	}
	
	/**
	 * Sets the current stop that was suggested by ccs.
	 * @param stop
	 */
	public void setSuggestedCurrentStop(Stop stop){
		this.suggestedCurrentStop = stop;
	}
	
	/**
	 * Gets the current stop that has been accepted by a participant.
	 * @return
	 */
	public Stop getAcceptedCurrentStop(){
		Session session = this.getSession();
		Stop stop = session.getCurrentStop();
		StopStationService service = this.getStopStationService();
		
		if(stop != null){
			
			if(!this.getIsStopInArea(stop)){
				this.participant.deny(stop);
				
				return null;
			}
			
			//gets the distance from the participant to the current stop
			Double distance = this.participant.getDistance(stop);
			//gets the duration in milliseconds of how long a stop has been continuing
			Long duration = (new Date()).getTime() - this.participant.getPosition().getTime().getTime();
			//if a participant was longer than 5 minutes  in the area around a stop station,
			//we suppose that a stop took place successfully at the current stop station
			if(distance <= service.getRadiusAroundStopStation() && 
					duration >= service.getStopDuration()){
				session.setCurrentStop(null);
			
				return null;
			}
		}				
		return stop;
	}
	
	/**
	 * 
	 * @param stop
	 * @return
	 */
	private Boolean getIsStopInArea(Stop stop){
		StopStationService service = this.getStopStationService();
		
		if(stop != null){
			//gets the distance from the participant to the current stop
			Double distance = this.participant.getDistance(stop);
			//if the distance is longer than the radius of the area where the stop station is located,
			//then it means that the participant veers away from this stop station
			if(distance > service.getRadius()){
				return false;
			}
		}
		
		return true;
	}
}
