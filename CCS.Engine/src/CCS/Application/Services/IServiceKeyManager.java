/**
 * 
 */
package CCS.Application.Services;

/**
 * 
 * @version 1.0
 * @since June 27, 2009
 */
public interface IServiceKeyManager {
	/**
	 * Adds a new key.
	 * @param value
	 */
	void addKey(String name, String value);
	
	/**
	 * Gets the current key. 
	 * @return
	 */
	IServiceKey getKey();
	
	/**
	 * Gets a key by name.
	 * @param name
	 * @return
	 */
	IServiceKey getKey(String name);
	
}
