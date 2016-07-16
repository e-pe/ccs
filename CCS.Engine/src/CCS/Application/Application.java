package CCS.Application;

import CCS.Application.Host.*;
import CCS.Application.Portal.*;
import CCS.Application.Services.*;

/**
 * This class stores all started sessions. 
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class Application {
	private static Application current;	
	
	private Portal portal;
	private ServiceCollection services;
	private ConvoyControl convoyControl;
	private AppConfiguration configuration;
	
	/**
	 * Constructor
	 */
	private Application(){
		this.portal = new Portal(this);
		this.convoyControl = new ConvoyControl(this);
		this.configuration = new AppConfiguration(this);
		
		//initializes the application
		this.initialize();
	}
	
	/**
	 * gets the current application.
	 * @return
	 */
	public static Application getCurrent(){
		if(current == null)
			current = new Application();
		
		return current;
	}
		
	/**
	 * Gets the path of the application configuration file.
	 * @return
	 */
	private String getConfigPath(){
		return this.getClass().getResource("ccs-config.xml").getFile();
	}
	
	/**
	 * 
	 * @return
	 */	
	public ConvoyControl getConvoyControl(){
		return this.convoyControl;
	}
	
	public Portal getPortal(){
		return this.portal;
	}
	
	/**
	 * Gets the services of the application. 
	 * @return
	 */
	public ServiceCollection getServices(){
		return this.services;
	}
	
	void setServices(ServiceCollection services){
		this.services = services;
	}
		
	/**
	 * Initializes the application with default settings from a configuration XML file.
	 * @param xml 
	 */
	private void initialize(){			
		this.configuration.setConfig(this.getConfigPath());		
	}
		
}
