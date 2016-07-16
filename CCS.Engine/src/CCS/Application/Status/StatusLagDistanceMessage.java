/**
 * 
 */
package CCS.Application.Status;

import CCS.Application.Host.*;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusLagDistanceMessage implements IStatusMessage{
	
	private Participant first;
	
	public StatusLagDistanceMessage(Participant first){
		this.first = first;
	}
	
	public String getText(Status status) {
		Participant last = (Participant)status.getMarker();
		
		return last.getFirstName() + " " + 
			   last.getLastName() + " " +
			   "farthest away, over" + " " +
			   Math.round(last.getDistance(first) / 1000) + " km from "  +
			   first.getFirstName() + " " + first.getLastName();
	}

}
