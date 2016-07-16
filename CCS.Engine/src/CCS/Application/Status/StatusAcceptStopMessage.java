/**
 * 
 */
package CCS.Application.Status;

import CCS.Application.Host.Stop;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusAcceptStopMessage implements IStatusMessage {

	public String getText(Status status) {
		Stop stop = (Stop)status.getMarker();
		
		return stop.getName() + " - " + 
			   stop.getType().toString() + " " +
			   "was accepted by" + " " +	
			   stop.getAcceptedBy().getFirstName() + " " + stop.getAcceptedBy().getLastName();
	}

}
