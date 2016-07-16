/**
 * 
 */
package CCS.Application.Services;

import java.util.*;

import CCS.Application.Host.*;
/**
 * This class generates a view specifying all parameters required to request a map 
 * from Google Static Maps.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
class GoogleMapsView implements IServiceView {
	private static final String MapType = "roadmap";
	private static final String PathColorValue = "0x0000ff";
	private static final String WeightValue = "5";
	
	private static final String SensorKey = "sensor";
	private static final String CenterKey = "center";
	private static final String LicenseKey = "key";
	private static final String PathKey = "path";
	private static final String MapTypeKey = "maptype";
	private static final String ZoomKey = "zoom";
	private static final String SizeKey = "size";
	private static final String MarkersKey = "markers";
	private static final String PathColorTypeKey = "rgb";
	private static final String WeightKey = "weight";
	
	private Path path;
	private String key;
	private Integer width;
	private Integer height;	
	private IMarker center;
	private Integer zoomLevel;
	private Vector<IMarker> markers;
	private GoogleMapsService service;
	
	public GoogleMapsView(GoogleMapsService service){
		this.service = service;
	}
			
	/**
	 * 
	 * @param key
	 */
	public void setKey(String key){
		this.key = key;
	}
	
	/**
	 * 
	 * @param path
	 */
	public void setPath(Path path){
		this.path = path;
	}
	
	/**
	 * 
	 * @param zoomLevel
	 */
	public void setZoomLevel(Integer zoomLevel){
		this.zoomLevel = zoomLevel;
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setWidth(Integer width){
		this.width = width;
	}
	
	/**
	 * 
	 * @param height
	 */
	public void setHeight(Integer height){
		this.height = height;
	}
	
	public void setCenter(IMarker center){
		this.center = center;
	}
	
	/**
	 * 
	 * @param markers
	 */
	public void setMarkers(Vector<IMarker> markers){
		this.markers = markers;
	}
	
	/**
	 * Gets the url request for Google Maps API.
	 * e.g
	 * http://maps.google.com/staticmap?zoom=14&size=512x512&maptype=mobile
	 * @return
	 */
	public String getUrl(){
		String url = this.service.getConnectionString() + "?";
		url += CenterKey + "=" + this.center.getLocation().getLatitude() + "," + 
								 this.center.getLocation().getLongitude() + "&";
		
		url += ZoomKey + "=" + this.zoomLevel + "&";
		url += SizeKey + "=" + this.width + "x" + this.height + "&";
		url += MapTypeKey + "=" + MapType + "&";
		url += this.getMarkersPart() + "&";
		
		if(this.path != null)
			url += this.getPathPart() + "&";
		
		url += LicenseKey + "=" + this.key + "&";
 		url += SensorKey + "=" + true;
 		
		return url;
	}
	
	/**
	 * e.g
	 * markers=40.702147,-74.015794,blues|40.711614,-74.012318,greeng
	 * @return
	 */
	private String getMarkersPart(){
		String result = MarkersKey + "=";
		
		for(int i=0; i < this.markers.size(); i++){
			IMarker marker = this.markers.get(i);
			result += marker.getLocation().getLatitude() + ",";
			result += marker.getLocation().getLongitude() + ",";
			result += marker.getColor().getName().toString().toLowerCase();
			result += marker.getLabel();
			
			if (i != this.markers.size() - 1) 
				result += "|";
		}
		
		return result;
	}
	
	/**
	 * e.g
	 * path=rgba:0x0000FF80,weight:5|37.39907,-122.05553|37.38571,-122.07373
	 * @return
	 */
	private String getPathPart(){
		String result = PathKey + "=";
		result += PathColorTypeKey + ":" + PathColorValue + ",";
		result += WeightKey + ":" + WeightValue + "|";
		
		Location[] points = this.path.getPoints();
		for(int i = 0; i < points.length; i++){
			Location location = points[i];
			result += location.getLatitude() + ",";
			result += location.getLongitude();
			
			if(i != points.length - 1)
				result += "|";
		}
		
		return result;
	}
}
