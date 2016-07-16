/**
 * 
 */
package CCS.Application.Services;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import CCS.Application.Host.*;

/**
 * This service provides gps coordinates between two points.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class GnuiteService implements IService {
	private static String TrkptTag = "trkpt";
	private static String LatTag = "lat";
	private static String LonTag = "lon";
	
	private Integer maxRequests;
	private Long activationInterval;
	private String connectionString;

	private GnuiteKeyManager keyManager;
	
	public GnuiteService(String connectionString, Integer maxRequests, Long activationInterval){
		this.connectionString = connectionString;
		this.maxRequests = maxRequests;
		this.activationInterval = activationInterval;
		
		this.keyManager = new GnuiteKeyManager();
	}
	
	public Long getActivationInterval() {
		return this.activationInterval;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public IServiceKeyManager getKeyManager() {
		return this.keyManager;
	}

	public Integer getMaxRequests() {
		return this.maxRequests;
	}
	
	public Integer getSkipInterval(){
		return Integer.parseInt(this.keyManager.getKey("skipInterval").getValue());
	}
	
	public Location[] getPathLocations(Location srcLocation, Location dstLocation){
		
		Vector<Location> locations = new Vector<Location>();
		
		try{
				
			String xml = this.doRequest(srcLocation.getLatitude(), srcLocation.getLongitude(), 
						dstLocation.getLatitude(), dstLocation.getLongitude());
						
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(xml)));
			
			doc.getDocumentElement().normalize();
			
			NodeList nodeLst = doc.getElementsByTagName(TrkptTag);
			
			Integer skip = 0;
			for(int i = 0; i < nodeLst.getLength(); i++) {
				Node pointNode = nodeLst.item(i);
				
				if(pointNode.getNodeType() == Node.ELEMENT_NODE) {       
					Element el = (Element)pointNode;
					
					if(i == skip){
						Location location = new Location(Double.parseDouble(el.getAttribute(LatTag)), 
							Double.parseDouble(el.getAttribute(LonTag)));
					
						locations.add(location);
						
						skip += this.getSkipInterval();
					}
				}
			}	
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if(locations.size() == 0){
			locations.add(srcLocation);
			locations.add(dstLocation);
		}
		
		return (Location[])locations.toArray(new Location[]{});
	}
	
	private String doRequest(Double srcLatitude, Double srcLongitude, Double dstLatitude, Double dstLongitude){
		GnuiteView view = new GnuiteView(this);
		view.setSrcLatitude(srcLatitude);
		view.setSrcLongitude(srcLongitude);
		view.setDstLatitude(dstLatitude);
		view.setDstLongitude(dstLongitude);
		
		return this.getResponse(view);
	}
	
	private String getResponse(GnuiteView view){
		
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
