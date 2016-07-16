/**
 * 
 */
package CCS.Application.Services;

import java.util.*;
/**
 * This class stores all keys available for CCS in order to request 
 * a map image from the Google Static Maps Service.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
class GoogleMapsKeyManager implements IServiceKeyManager {
	
	private GoogleMapsService service;
	private Vector<GoogleMapsKey> keys;
	
	public GoogleMapsKeyManager(GoogleMapsService service){
		this.service = service;
		this.keys = new Vector<GoogleMapsKey>(); 
	}
	
	public void addKey(String name, String value){
		this.keys.add(new GoogleMapsKey(this, name, value));
	}
	
	public GoogleMapsService getService(){
		return this.service;
	}
	
	/**
	 * Gets the non-expired key.
	 * @return
	 */
	public GoogleMapsKey getKey(){
		Enumeration<GoogleMapsKey> e = this.keys.elements();
		
		while(e.hasMoreElements()){
			GoogleMapsKey key = e.nextElement();
			
			if(!key.getExpired())
				return key;
		}
		
		return this.keys.get(0);
	}
	
	/**
	 * 
	 * @param key
	 */
	void activate(GoogleMapsKey key){
		(new GoogleMapsKeyThread(key)).start();
	}

	/** (non-Javadoc)
	 * @see CCS.Application.Services.IServiceKeyManager#getKey(java.lang.String)
	 */
	
	public IServiceKey getKey(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
