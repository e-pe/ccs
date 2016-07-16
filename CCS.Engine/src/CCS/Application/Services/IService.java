/**
 * 
 */
package CCS.Application.Services;

/**
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public interface IService {
	
	/**
	 * Gets the connection string.
	 * @return
	 */
	String getConnectionString();
	
	/**
	 * Gets the KeyManager for maintaining all keys required for external services.
	 * @return
	 */
	IServiceKeyManager getKeyManager();
	
	/**
	 * Gets the count of requests that can be made by a key. 
	 * @return
	 */
	Integer getMaxRequests();
	
	/**
	 * Gets the time interval when the key can be renewed.
	 * @return
	 */
	Long getActivationInterval();
	
}
