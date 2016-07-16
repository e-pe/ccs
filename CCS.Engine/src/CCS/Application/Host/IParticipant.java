/**
 * 
 */
package CCS.Application.Host;

/**
 * This interface specifies the information about each participant. 
 *
 * @version 1.0
 * @since June 3, 2009
 */
public interface IParticipant{
	/**
	 * Gets the tank object.
	 * @return
	 */
	Tank getTank();
	/**
	 * Gets the speed object.
	 * @return
	 */
	Speed getSpeed();

}
