/**
 * 
 */
package CCS.Application.Host;

/**
 * This interface represents a user that does not belong to any tour.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public interface IMember {
	/**
	 * Gets the id.
	 * @return
	 */
	String getId();
	/**
	 * Gets the login name.
	 * @return
	 */
	String getLoginName();
	/**
	 * Gets the password.
	 * @return
	 */
	String getPassword();
	/**
	 * Gets the first name.
	 * @return
	 */
	String getFirstName();
	/**
	 * Gets the last name.
	 * @return
	 */
	String getLastName();
}
