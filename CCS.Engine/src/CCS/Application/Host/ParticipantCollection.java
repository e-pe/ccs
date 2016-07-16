/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.System.*;

/**
 * @version 1.0
 * @since June 11, 2009
 */
public class ParticipantCollection extends ArrayCollectionBase<Participant>{
	
	/**
	 * Adds a new participant.
	 * @param participant
	 */
	public void add(Participant participant){
		this.collection.add(participant);
	}
	
	/**
	 * Gets the participant by index.
	 * @param index
	 * @return
	 */
	public Participant get(Integer index){
		return this.collection.get(index);
	}
	
	/**
	 * Gets the participant by id.
	 * @param id
	 * @return
	 */
	public Participant get(String id){
		Enumeration<Participant> ie = this.elements();
		
		while(ie.hasMoreElements()){
			Participant participant = ie.nextElement();
			
			if(participant.getId().equals(id))
				return participant;
		}
		
		return null;
	}
	
	/**
	 * Removes an existing participant by id. 
	 * @param id
	 */
	public void remove(String id){
		this.collection.remove(this.get(id));
	}
	
	/**
	 * Removes an existing participant.
	 * @param participant
	 */
	public void remove(Participant participant){
		this.collection.remove(participant);
	}
	
	/**
	 * Returns a new vector collection of all markers in the collection.
	 * @return
	 */
	public Vector<IMarker> toMarkerList(){
		Vector<IMarker> markers = new Vector<IMarker>();
		Enumeration<Participant> e = this.elements();
		
		while(e.hasMoreElements()){
			markers.add(e.nextElement());
		}
		
		return markers;
	}
	
}
