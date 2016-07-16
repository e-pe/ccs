/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

/**
 * This class specifies a stop during a tour.
 * 
 * @version 1.0
 * @since June 4, 2009
 */
public class Stop implements IMarker {
	
	private String id;
	private String name;
	private StopType type;
	private Date acceptedOn;
	private Location location;
	private MarkerColor color;
	private Participant acceptedBy;
	private Participant deniedBy;
	
	
	/**
	 * Constructor
	 * 
	 * @param type
	 * @param location
	 */
	public Stop(String name, StopType type, Location location){
		this.name = name;
		this.type = type;
		this.location = location;
		this.id = UUID.randomUUID().toString();	
		this.color = MarkerColor.createColor(MarkerColorName.Red);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * 
	 */
	public MarkerColor getColor() {
		return this.color;
	}

	/**
	 * 
	 */
	public String getLabel() {
		return "";
	}

	/**
	 * 
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Gets the type of the stop.
	 * @return
	 */
	public StopType getType(){
		return this.type;
	}
	
	/**
	 * Gets the name of the stop.
	 * @return
	 */
	public String getName(){
		return this.name;
	}
		
	/**
	 * 
	 * @return
	 */
	public Date getAcceptedOn() {
		return this.acceptedOn;
	}
	
	/**
	 * 
	 */
	void setAcceptedOn(Date date){
		this.acceptedOn = date;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean getAccepted(){
		return this.acceptedOn != null && this.acceptedBy != null ? true : false;
	}
	
	/**
	 * 
	 * @return
	 */
	public Participant getAcceptedBy(){
		return this.acceptedBy;
	}
	
	/**
	 * 
	 * @return
	 */
	public Participant getDeniedBy(){
		return this.deniedBy;
	}
	
	/**
	 * 
	 * @param participant
	 */
	void setDeniedBy(Participant participant){
		this.deniedBy = participant;
	}
	
	/**
	 * 
	 * @param participant
	 */
	void setAcceptedBy(Participant participant){
		this.acceptedBy = participant;
	}
}
