/**
 * 
 */
package CCS.Application.Services;

/**
 * This class renews a google maps key after 24 hours, 
 * if the limit request has been reached.
 
 * @version 1.0
 * @since June 30, 2009
 */
public class GoogleMapsKeyThread extends Thread {
	
	private GoogleMapsKey key;
	
	public GoogleMapsKeyThread(GoogleMapsKey key){
		this.key = key;
	}
	
	public void run(){
		 try {
	          sleep(this.key.getManager().getService().getActivationInterval());
	     }
	     catch(InterruptedException e) {}
	     
	     this.key.renew();
	}
}
