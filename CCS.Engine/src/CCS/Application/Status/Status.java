/**
 * 
 */
package CCS.Application.Status;

import java.util.*;

import CCS.Application.Host.IMarker;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class Status {
	
	private Date time;
	private IMarker marker;
	private IStatusMessage message;
	
	public Status(IMarker marker, IStatusMessage message){
		this.marker = marker;
		this.message = message;
		this.time = new Date();
	}
	
	/**
	 * Gets the time when the status has been created. 
	 * @return
	 */
	public Date getTime(){
		return this.time;
	}
	
	/**
	 * Gets the text.
	 * @return
	 */
	public String getText(){
		return this.message.getText(this);
	}
	
	/**
	 * Gets the marker.
	 * @return
	 */
	public IMarker getMarker() {
		return this.marker;
	}
}
