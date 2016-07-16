/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

/**
 * This class reflects the speed status at a particular time.
 *
 * @version 1.0
 * @since June 15, 2009
 */
public class Speed {
	private Double value;
	private Date time;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Speed(Double value){
		this.value = value;
		this.time = new Date();
	}
	
	/**
	 * Gets the speed value.
	 * @return
	 */
	public Double getValue(){
		return this.value;
	}
	
	/**
	 * Gets the point of time when this speed value has been registered.
	 * @return
	 */
	public Date getTime(){
		return this.time;
	}
	
}
