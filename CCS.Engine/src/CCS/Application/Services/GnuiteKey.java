/**
 * 
 */
package CCS.Application.Services;

/**
 * @version 1.0
 * @since June 3, 2009
 */
public class GnuiteKey implements IServiceKey {

	private String name;
	private String value;
	
	public GnuiteKey(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

}
