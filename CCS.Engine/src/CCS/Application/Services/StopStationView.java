/**
 * 
 */
package CCS.Application.Services;

/**
 * @version 1.0
 * @since June 29, 2009
 */
class StopStationView implements IServiceView{
	private static final String Type = "type";
	private static final String Latitude = "latitude";
	private static final String Longitude = "longitude";
	private static final String Radius = "radius";
	
	private String type;
	private Integer radius;
	private Double latitude;
	private Double longitude;
	
	private StopStationService service;
	/**
	 * Constructor
	 * @param service
	 */
	public StopStationView(StopStationService service){
		this.service = service;
	}
	
	/**
	 * 
	 * @param latitude
	 */
	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}
	
	/**
	 * 
	 * @param longtitude
	 */
	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}
	
	/**
	 * 
	 * @param radius
	 */
	public void setRadius(Integer radius){
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}
	
	public String getUrl(){
		String url = this.service.getConnectionString() + "?";
		url += Type + "=" + this.type + "&";
		url += Radius + "=" + this.radius + "&";
		url += Latitude + "=" + this.latitude + "&";
		url += Longitude + "=" + this.longitude;
		
		return url;
	}
}
