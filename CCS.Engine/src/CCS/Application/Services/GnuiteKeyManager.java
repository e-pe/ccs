/**
 * 
 */
package CCS.Application.Services;

import java.util.*;

/**
 * @version 1.0
 * @since June 3, 2009
 */
public class GnuiteKeyManager implements IServiceKeyManager {

	private Hashtable<String, GnuiteKey> keys;
	
	public GnuiteKeyManager(){
		this.keys = new Hashtable<String, GnuiteKey>();
	}
	
	public void addKey(String name, String value) {
		this.keys.put(name, new GnuiteKey(name, value));
	}

	public IServiceKey getKey() {
		return null;
	}

	public IServiceKey getKey(String name) {
		return this.keys.get(name);
	}

}
