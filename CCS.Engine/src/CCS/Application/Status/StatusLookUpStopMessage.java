/**
 * 
 */
package CCS.Application.Status;

import CCS.Application.Host.*;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusLookUpStopMessage implements IStatusMessage {
	private Participant participant;
	
	public StatusLookUpStopMessage(Participant participant){
		this.participant = participant;
	}
	
	public String getText(Status status) {
		Stop stop = (Stop)status.getMarker();
		
		return stop.getName() + " - " + 
			   stop.getType().toString() + ", " +
			   "located" + " " +
			   Math.round(participant.getDistance(stop) / 1000) + " km away " + ", " +
			   "was looked up by" + " " +
			   participant.getFirstName() + " " +
			   participant.getLastName();
	}

}
