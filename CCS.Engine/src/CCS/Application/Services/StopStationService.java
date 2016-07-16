package CCS.Application.Services;

import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

import CCS.Application.Host.*;

/**
 * This service provides all possible stops(e.g gas stations, rest stops) available in an area.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class StopStationService implements IService {
	
	private static String StopStationTag = "stop-station";
	private static String StopStationNameTag = "name";
	private static String StopStationLatitudeTag = "latitude";
	private static String StopStationLongitudeTag = "longitude";
	private static String StopStationTypeTag = "type";
	
	private Integer maxRequests;
	private Long activationInterval;
	private String connectionString;
	
	private StopStationKeyManager keyManager;
	
	public StopStationService(String connectionString, Integer maxRequests, Long activationInterval){
		this.connectionString = connectionString;
		this.maxRequests = maxRequests;
		this.activationInterval = activationInterval;
		
		this.keyManager = new StopStationKeyManager();
	}
	
	public String getConnectionString() {
		return this.connectionString;
	}

	public IServiceKeyManager getKeyManager() {
		return this.keyManager;
	}
	
	public Long getActivationInterval() {		
		return this.activationInterval;
	}

	public Integer getMaxRequests() {		
		return this.maxRequests;
	}
	
	public StopCollection getStopStations(StopType stopType, Location location){
		
		StopCollection stops = new StopCollection();
		
		try{
			String xml = this.doRequest(stopType.toString(), this.getRadius(), 
					location.getLatitude(), location.getLongitude());
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(xml)));
			
			doc.getDocumentElement().normalize();
			
			NodeList nodeLst = doc.getElementsByTagName(StopStationTag);
			
			for(int i = 0; i < nodeLst.getLength(); i++) {

				Node stopNode = nodeLst.item(i);
				    
				if(stopNode.getNodeType() == Node.ELEMENT_NODE) {
				           
					  Element el = (Element)stopNode;
				      
					  Stop stop = new Stop(el.getAttribute(StopStationNameTag),
							  StopType.valueOf(el.getAttribute(StopStationTypeTag)),
				    		  new Location(Double.parseDouble(el.getAttribute(StopStationLatitudeTag)), 
				    		  			   Double.parseDouble(el.getAttribute(StopStationLongitudeTag))));   
					  
					  stops.add(stop);
				}
			}	    
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
			
		return stops;
	}
	
	/**
	 * Gets the radius of an area in meters where stop stations have to be looked up.
	 * @return
	 */
	public Integer getRadius(){
		return Integer.parseInt(this.keyManager.getKey("radius").getValue());
	}
	
	/**
	 * Gets the radius of the area around a stop station in meters.
	 * @return
	 */
	public Integer getRadiusAroundStopStation(){
		return Integer.parseInt(this.keyManager.getKey("stopArea").getValue());
	}
	
	/**
	 * Gets a default stop duration.
	 * @return
	 */
	public Long getStopDuration(){
		return Long.parseLong(this.keyManager.getKey("stopDuration").getValue());
	}
	
	/**
	 * Sends a request to the stop station service.
	 * @param type
	 * @param radius
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	private String doRequest(String type, Integer radius, Double latitude, Double longitude){
		StopStationView view = new StopStationView(this);
		view.setType(type);
		view.setRadius(radius);
		view.setLatitude(latitude);
		view.setLongitude(longitude);
		
		return this.getResponse(view);
	}
	
	/**
	 * Gets the response from the stop station service.
	 * @param view
	 * @return
	 */
	private String getResponse(StopStationView view){

		try {
			URL url = new URL(view.getUrl());
			InputStream inputStream = url.openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			
			// to convert the InputStream to String we use the BufferedReader.readLine() method.
			String line = null;
			
			while ((line = reader.readLine()) != null) {
			   sb.append(line + "\n");
			}
			
			inputStream.close();
			
			return sb.toString();
		} 
		catch (MalformedURLException e) {}
			
	    catch (IOException e) {
	    	System.out.println(e.getMessage());
	    }

	    return null;
	}
}
