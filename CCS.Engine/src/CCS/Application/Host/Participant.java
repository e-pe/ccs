package CCS.Application.Host;

import java.util.*;

import CCS.Application.*;
import CCS.Application.Services.*;
import CCS.Application.Status.*;

/**
 * 
 * This class represents a participant during a tour.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class Participant implements IMember, IParticipant, IMarker {
	private IMember member;
	private Session session;
	private String label;
	private MarkerColor color;
	
	private StopManager stopManager;
	private Vector<Tank> tankStorage;
	private Vector<Speed> speedStorage;	
	private Vector<Position> positionStorage;

	/**
	 * Constructor
	 * 
	 * @param session
	 * @param member
	 */
	public Participant(Session session, IMember member, String label,  MarkerColor color){	
		this.member = member;
		this.session = session;
		this.color = color;
		this.label = label;
		
		this.tankStorage = new Vector<Tank>();
		this.speedStorage = new Vector<Speed>();
		this.stopManager = new StopManager(this);
		this.positionStorage = new Vector<Position>();				
	}
	
	/**
	 * Gets the application object.
	 * @return
	 */
	private Application getApplication(){
		return this.session.getApplication();
	}
	
	/**
	 * Gets the session object.
	 * @return
	 */
	public Session getSession(){
		return this.session;
	}
	
	/**
	 * Gets the first name. 
	 */
	public String getFirstName() {
		return this.member.getFirstName();
	}

	/**
	 * Gets the id.
	 */
	public String getId() {
		return this.member.getId();
	}

	/**
	 * Gets the last name.
	 */
	public String getLastName() {
		return this.member.getLastName();
	}

	/**
	 * Gets the login name. 
	 */
	public String getLoginName() {
		return this.member.getLoginName();
	}

	/**
	 * Gets the password.
	 */
	public String getPassword() {
		return this.member.getPassword();
	}

	/**
	 * Gets the color.
	 */
	public MarkerColor getColor() {
		return this.color;
	}

	/**
	 * Gets the label.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Gets the location object.
	 */
	public Location getLocation() {
		return this.getPosition();
	}
	
	/**
	 * Gets the position object.
	 * @return
	 */
	public Position getPosition(){
		return this.positionStorage.lastElement();
	}
	
	/**
	 * Sets the current location of a participant.
	 * @param position
	 */
	public void setPosition(Position position){
		this.positionStorage.add(position);
		
	}

	/**
	 * 	Gets the speed object. 
	 */
	public Speed getSpeed() {
		return this.speedStorage.lastElement();
	}
	
	/**
	 * Sets the current speed of the participant.
	 * @param speed
	 */
	public void setSpeed(Speed speed){
		this.speedStorage.add(speed);
	}

	/**
	 * Gets the tank object.
	 */
	public Tank getTank() {
		return this.tankStorage.lastElement();
	}
	
	/**
	 * Sets the tank value of the participant. 
	 * @param tank
	 */
	public void setTank(Tank tank){
		this.tankStorage.add(tank);
	}
	
	/**
	 * Gets the StopManager object that stores all requested stops by the current participant. 
	 * @return
	 */
	public StopManager getStopManager(){
		return this.stopManager;
	}
	
	/**
	 * Gets the distance to another participant in meters.
	 * @param participant
	 * @return
	 */
	public Double getDistance(Participant participant){
		return this.getLocation().getDistanceTo(participant.getLocation());
	}
	
	/**
	 * Gets the distance to a stop in meters.
	 * @param stop
	 * @return
	 */
	public Double getDistance(Stop stop){
		return this.getLocation().getDistanceTo(stop.getLocation());
	}
	
	/**
	 * Gets the distance to another location.
	 * @param location
	 * @return
	 */
	public Double getDistance(Location location){
		return this.getLocation().getDistanceTo(location);
	}
				
	/**
	 * Gets a map with a stop that can be accepted by the participant.
	 * @param type
	 * @param zoomLevel
	 * @param showPath
	 * @param showParticipantPane
	 * @param showParticipants
	 * @return
	 */
	public MapView getMap(Integer zoomLevel, Integer width, Integer height, StopType stopType, PathType  pathType, Boolean showParticipantPane){		
		return this.requestMap(zoomLevel, width, height, (stopType == StopType.None) ? this.stopManager.getCurrentStop() : this.stopManager.getStop(stopType), pathType, showParticipantPane);
	}
	
	/**
	 * Gets the map.
	 * @param stop
	 * @param zoomLevel
	 * @param width
	 * @param height
	 * @param pathType
	 * @return
	 */
	private MapView requestMap(Integer zoomLevel, Integer width, Integer height, Stop stop, PathType pathType, Boolean showParticipantPane){
		ServiceCollection services = this.getApplication().getServices();
		
		GoogleMapsService service = (GoogleMapsService)services.get(GoogleMapsService.class);
			
		Vector<IMarker> markers = this.session.getParticipants().toMarkerList();
						
		if(stop != null)
			markers.add(stop);
		
		byte[] map = service.getMap(zoomLevel, width, height, this, markers,												   
				  Path.getPath(pathType, this.positionStorage, stop));
		
		return MapView.createMapView(this, map, stop, this.session.getStatus(), showParticipantPane);				
	}
		
	/**
	 * Accepts a stop suggested by the system.
	 * @param stop
	 */
	public void accept(Stop stop){
		//sets by whom has been the current stop accepted
		stop.setAcceptedBy(this);
		stop.setAcceptedOn(new Date());
		
		this.session.setCurrentStop(stop);
		this.session.getAcceptedStops().add(stop);
		
		this.session.setStatus(new Status(stop, 
				new StatusAcceptStopMessage()));
	}
	
	/**
	 * Aborts a stop that was accepted by a participant.
	 * @param stop
	 */
	public void deny(Stop stop){
		stop.setDeniedBy(this);
				
		this.session.setCurrentStop(null);
		this.session.getAcceptedStops().remove(stop.getId());
		
		this.session.setStatus(new Status(stop, 
				new StatusDenyStopMessage()));
	}
	
	/**
	 * Saves the current location, speed etc. of the participant in the database.
	 */
	public void commit(){
		ServiceCollection services = this.getApplication().getServices();
    	DataAccessService service = (DataAccessService)services.get(DataAccessService.class);
    	
    	service.setGps(this);
	}	
}
