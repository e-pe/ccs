/**
 * 
 */
package CCS.Application.Services;

import java.util.*;

/**
 * @version 1.0
 * @since July 7, 2009
 */
public class StopStationKeyManager implements IServiceKeyManager{

	private Hashtable<String, StopStationKey> keys;
	
	public StopStationKeyManager() {
		this.keys = new Hashtable<String, StopStationKey>();
	}
	
	public void addKey(String name, String value) {
		this.keys.put(name, new StopStationKey(name, value));	
	}

	public IServiceKey getKey() {
		return null;
	}

	public IServiceKey getKey(String name) {
		return this.keys.get(name);
	}

}
