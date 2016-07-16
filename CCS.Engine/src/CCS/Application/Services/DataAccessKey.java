/**
 * 
 */
package CCS.Application.Services;

/**
 * 
 * @version 1.0
 * @since June 27, 2009
 */
public class DataAccessKey implements IServiceKey {
	
	private String name;
	private String value;
	
	public DataAccessKey(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	/**
	 * 
	 */
	public String getName() {	
		return this.name;
	}

}
