/**
 * 
 */
package CCS.Application.Status;

import CCS.Application.Host.Participant;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusJoinSessionMessage implements IStatusMessage {

	public String getText(Status status) {
		Participant participant = (Participant)status.getMarker();
		
		return participant.getFirstName() + " " + participant.getLastName() + " joined the session";
	}

}
