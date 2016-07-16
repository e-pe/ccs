/**
 * 
 */
package CCS.Application.Host;

/**
 * This interface marks inherited object with special attributes.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public interface IMarker {
	/**
	 * Gets the color which the participant is marked with on the map.
	 * @return
	 */
	MarkerColor getColor();
	/**
	 * Gets the label of the participant on the map.
	 * @return
	 */
	String getLabel();
	/**
	 * Gets the location of the marker.
	 * @return
	 */
	Location getLocation();
}
