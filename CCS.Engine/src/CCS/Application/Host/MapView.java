/**
 * 
 */
package CCS.Application.Host;

import CCS.Application.Status.*;
import CCS.Application.Services.DTO.*;

/**
 * @version 1.0
 * @since June 17, 2009
 */
public class MapView {
	
	private byte[] bytes;
	private Stop displayedStop;
	
	/**
	 * Constructor
	 * 
	 * @param bytes
	 * @param displayedStop
	 */
	public MapView(byte[] bytes, Stop displayedStop){
		this.bytes = bytes;
		this.displayedStop = displayedStop;
	}
	
	/**
	 * Gets the map as an byte array.
	 * @return
	 */
	public byte[] getBytes(){
		return this.bytes;
	}
	
	/**
	 * Gets the displayed stop on the map.
	 * @return
	 */
	public Stop getDisplayedStop(){
		return this.displayedStop;
	}
	
	/**
	 * Gets the MapView Data Transfer Object.
	 * @return
	 */
	public MapViewDTO getDTO(){
		MapViewDTO dto = new MapViewDTO();
		dto.setBytes(this.bytes);
		
		Stop stop = this.displayedStop;	
		if(stop != null){
			dto.setStopId(stop.getId());
			dto.setStopAccepted(stop.getAccepted());
		}
		else{
			dto.setStopId("-1");
			dto.setStopAccepted(false);
		}
						
		return dto;
	}
	
	/**
	 * Creates a new MapView object.
	 * @param map
	 * @param showParticipantPane
	 * @param participants
	 * @param stop
	 * @return
	 */
	static MapView createMapView(Participant participant, byte[] map, Stop stop, Status status, Boolean showParticipantPane){
		
		byte[] view = null;
		
		if(showParticipantPane)
			view = ParticipantPaneGenerator.generateView(map, participant);
		else
			view = map;
		
		if(status != null)
			view = StatusBarGenerator.generateBar(view, status);
		
		return new MapView(view, stop);
	}
}
