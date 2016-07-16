/**
 * 
 */
package CCS.Application.Host;

/**
 * This class specifies a latitudinal value with precision to 6 decimal places
 * and a longitudinal value with precision to 6 decimal places.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class Location {
	
	private Double latitude;
	private Double longitude;
	
	
	/**
	 * Constructor
	 * 
	 * @param latitude
	 * @param longtitude
	 */
	public Location(Double latitude, Double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	/**
	 * Gets the latitude value.
	 * @return
	 */
	public Double getLatitude(){
		return this.latitude;
	}
	
	/**
	 * Gets the longitude value.
	 * @return
	 */
	public Double getLongitude(){
		return this.longitude;
	}
	
	/**
	 * Returns the approximate distance in meters between this location and the given location. 
	 * Distance is defined using the "Haversine" formula.
	 * @param location
	 * @return
	 */
	public Double getDistanceTo(Location location){
		//average earth radius in meters
		Integer radius = 6368000;   
		
		Double lat1 = this.getLatitude();
		Double lat2 = location.getLatitude();
		
		Double lon1 = this.getLongitude();
		Double lon2 = location.getLongitude();
		
		Double dLat = Math.toRadians(lat2-lat1);
		Double dLon = Math.toRadians(lon2-lon1);
	    
		Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	      		   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	      		   Math.sin(dLon/2) * Math.sin(dLon/2);
	    
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    
	    return radius * c; 
	}

}
