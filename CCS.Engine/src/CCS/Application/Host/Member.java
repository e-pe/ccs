/**
 * 
 */
package CCS.Application.Host;

/**
 * This class represents a user of this system.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class Member implements IMember {

	private String id;
	private String firstName;
	private String lastName;
	private String loginName;
	private String password;
	private String vehicle;
	
	/**
	 * Gets the id.
	 */
	public String getId() {	
		return this.id;
	}
	
	/**
	 * Sets the id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the first name.
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Sets the first name.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Sets the last name.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the login name. 
	 */
	public String getLoginName() {	
		return this.loginName;
	}
	
	/**
	 * Sets the login name.
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * Gets the password.
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the vehicle
	 */
	public String getVehicle(){
		return this.vehicle;
	}
	
	/**
	 * Sets the vehicle
	 */
	public void setVehicle(String vehicle){
		this.vehicle = vehicle;
	}
	
}
