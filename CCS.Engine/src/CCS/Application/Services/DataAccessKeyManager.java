/**
 * 
 */
package CCS.Application.Services;

import java.util.*;

/**
 * 
 * @version 1.0
 * @since June 27, 2009
 */
public class DataAccessKeyManager implements IServiceKeyManager {

	private Hashtable<String, DataAccessKey> keys;
	
	public DataAccessKeyManager(){
		this.keys = new Hashtable<String, DataAccessKey>();
	}
	
	public IServiceKey getKey() {
		return null;
	}

	/**
	 * @see CCS.Application.Services.IServiceKeyManager#addKey(java.lang.String)
	 */
	public void addKey(String name, String value) {
		this.keys.put(name, new DataAccessKey(name, value));
	}

	/** (non-Javadoc)
	 * @see CCS.Application.Services.IServiceKeyManager#getActivationInterval()
	 */
	public Long getActivationInterval() {
		return null;
	}

	/**
	 * @see CCS.Application.Services.IServiceKeyManager#getMaxRequests()
	 */
	public Integer getMaxRequests() {
		return null;
	}

	/** (non-Javadoc)
	 * @see CCS.Application.Services.IServiceKeyManager#setActivationInterval(java.lang.Long)
	 */
	public void setActivationInterval(Long activationInterval) {}			

	/** (non-Javadoc)
	 * @see CCS.Application.Services.IServiceKeyManager#setMaxRequests(java.lang.Integer)
	 */
	public void setMaxRequests(Integer maxRequests) {}

	/** (non-Javadoc)
	 * @see CCS.Application.Services.IServiceKeyManager#getKey(java.lang.String)
	 */
	public IServiceKey getKey(String name) {
		return this.keys.get(name);
	}
	
}
