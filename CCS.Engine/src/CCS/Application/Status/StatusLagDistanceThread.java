/**
 * 
 */
package CCS.Application.Status;

import CCS.Application.Host.*;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusLagDistanceThread extends Thread {
	private Session session;
	private Boolean threadDone = false;

	public StatusLagDistanceThread(Session session){
		this.session = session;
	}
	
	public ConvoyControl getConvoyControl(){
		return this.session.getConvoyControl();
	}
	
	public void run(){
		while (!this.threadDone) {

			try {
				sleep(this.getConvoyControl().getVerifyingLagDistanceInterval());
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		
			SessionContext context = this.session.getContext();
			Participant first = context.getLeader();
			Participant last = context.getBehind();
			
			if(first != null && last != null){
				if(last.getDistance(first) > this.getConvoyControl().getAllowedLagDistance())
					this.session.setStatus(new Status(last, new StatusLagDistanceMessage(first)));
			}
		}	
	}
	
	
	public void done() {
	    this.threadDone = true;
    }

}
