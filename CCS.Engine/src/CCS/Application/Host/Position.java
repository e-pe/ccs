/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

/**
 * @version 1.0
 * @since June 17, 2009
 */
public class Position extends Location {
	private Date time;
	
	public Position(Double latitude, Double longtitude){
		super(latitude, longtitude);
		
		this.time = new Date();
	}
	
	/**
	 * Gets the point of time when this location was up to date.
	 * @return
	 */
	public Date getTime(){
		return this.time;
	}
}
