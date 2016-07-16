/**
 * 
 */
package CCS.Application.Services;

/**
 * This class stores the unique identifier for each http-request, 
 * required by the Google Static Maps API.
 *
 * @version 1.0
 * @since June 3, 2009
 */
class GoogleMapsKey implements IServiceKey {
	
	private String name;
	private String value;
	private Integer requests = 0;
	private GoogleMapsKeyManager keyManager;
	
	public GoogleMapsKey(GoogleMapsKeyManager keyManager, String name, String value){
		this.name = name;
		this.value = value;
		this.keyManager = keyManager;
	}
	
	/**
	 * Gets the value whether the key has been expired.
	 * @return
	 */
	public Boolean getExpired(){
		return (this.requests == this.keyManager.getService().getMaxRequests()) ? true : false;
	}
	
	/**
	 * Gets the key manager.
	 * @return
	 */
	public GoogleMapsKeyManager getManager(){
		return this.keyManager;
	}
	
	/**
	 * 
	 * @return
	 */
	void renew(){
		this.requests = 0;
	}
	
	/**
	 * Gets the key value.
	 * @return
	 */
	public String getValue(){
		this.requests++;
		
		if(this.requests == this.keyManager.getService().getMaxRequests())
			this.keyManager.activate(this);
			
		return this.value;
	}

	/**
	 * 
	 */
	public String getName() {
		return this.name;
	}

}
