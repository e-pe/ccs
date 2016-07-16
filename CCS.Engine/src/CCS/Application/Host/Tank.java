/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

/**
 * This class reflects the status of the fuel value in a tank.
 *
 */
public class Tank {
	
	private Double value;
	private Date time;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Tank(Double value){
		this.value = value;
		this.time = new Date();
	}
	
	/**
	 * Gets the tank value.
	 * @return
	 */
	public Double getValue(){
		return this.value;
	}
	
	/**
	 * Gets the point of time when the tank had this status. 
	 * @return
	 */
	public Date getTime(){
		return this.time;
	}
}
