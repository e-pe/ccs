/**
 * 
 */
package CCS.Application.Services;

import java.io.*;
import java.net.*;
import java.util.*;

import CCS.Application.Host.*;

/**
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class GoogleMapsService implements IService {
	
	private Integer maxRequests;
	private Long activationInterval;
	private String connectionString;
	private GoogleMapsKeyManager keyManager;
	
	public GoogleMapsService(String connectionString, Integer maxRequests, Long activationInterval){
		this.connectionString = connectionString;
		this.maxRequests = maxRequests;
		this.activationInterval = activationInterval;
		
		this.keyManager = new GoogleMapsKeyManager(this);
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

	
	/**
	 * Gets a generated Google Map.
	 * @param zoomLevel
	 * @param width
	 * @param height
	 * @param markers
	 * @param path
	 */
	public byte[] getMap(Integer zoomLevel, Integer width, Integer height, IMarker center, Vector<IMarker> markers, Path path){
		GoogleMapsView view = new GoogleMapsView(this);
		view.setPath(path);
		view.setWidth(width);
		view.setHeight(height);
		view.setCenter(center);
		view.setMarkers(markers);
		view.setZoomLevel(zoomLevel);
		view.setKey(this.keyManager.getKey().getValue());
		
		return this.generateMap(view);
	}
	
	/**
	 * 
	 * @param view
	 * @return
	 */
	private byte[] generateMap(GoogleMapsView view){
		
		try {
			URL url = new URL(view.getUrl());
			InputStream inputStream = url.openStream();

			 ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			 byte[] bytes = new byte[512];
			 
			 // reads bytes from the input stream in bytes.length-sized chunks and write
			 // them into the output stream
			 int readBytes;
			 while ((readBytes = inputStream.read(bytes)) > 0) {
			        outputStream.write(bytes, 0, readBytes);
			 }
			 
			 // converts the contents of the output stream into a byte array
			  byte[] byteData = outputStream.toByteArray();
			    
			 // closes the streams
			 inputStream.close();
			 outputStream.close();
			 
			 return byteData;

		}
			
		catch (MalformedURLException e) {}
							
		catch (IOException e) {}
		
		return null;
	}

}
