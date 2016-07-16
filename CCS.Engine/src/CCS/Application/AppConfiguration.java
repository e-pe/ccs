/**
 * 
 */
package CCS.Application;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import CCS.Application.Host.*;
import CCS.Application.Services.*;


/**
 * This class reads the ccs configuration xml.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class AppConfiguration {	
	private String nameTag = "name";
	private String valueTag = "value";
	private String classTag = "class";
	private String connectionStringTag = "connectionString";
	private String maxRequestsTag = "maxRequests";
	private String activationIntervalTag = "activationInterval";
	private String keysTag = "keys";
	private String servicesTag = "services";
	private String convoyControlTag = "convoyControl";
	private String statusBarHideIntervalTag = "statusBarHideInterval";
	private String allowedLagDistanceTag = "allowedLagDistance";
	private String verifyingLagDistanceIntervalTag = "verifyingLagDistanceInterval";

	private Application application;
	
	/**
	 * 
	 * @param application
	 */
	public AppConfiguration(Application application){
		this.application = application;
	}
	
	/**
	 * Sets the configuration of the application.
	 * @param configPath
	 */
	public void setConfig(String configPath){
		try{
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(configPath));
			
			Element root = doc.getDocumentElement();  
			this.application.setServices(this.createServices(root));
			//initializes the convoy control object
			this.initializeConvoyControl(
					(Element)doc.getElementsByTagName(this.convoyControlTag).item(0));

			
	    }catch(Exception e){
	    	
	    }
	}
	
	private void initializeConvoyControl(Element element){
		ConvoyControl control = this.application.getConvoyControl();
		control.setAllowedLagDistance(Long.parseLong(element.getAttribute(this.allowedLagDistanceTag)));
		control.setStatusBarHideInterval(Long.parseLong(element.getAttribute(this.statusBarHideIntervalTag)));
		control.setVerifyingLagDistanceInterval(Long.parseLong(element.getAttribute(this.verifyingLagDistanceIntervalTag)));
	}
	
	/**
	 * Creates a new service collection. 
	 * @param root
	 * @return
	 */
	private ServiceCollection createServices(Element root){
		ServiceCollection services = new ServiceCollection();
		
		NodeList servicesList = root.getChildNodes();
		
		for(int i=0; i < servicesList.getLength(); i++)
		{
			 Node servicesNode = servicesList.item(i);
			
			 if (servicesNode.getNodeName().equals(this.servicesTag)) {
				 NodeList serviceList = servicesNode.getChildNodes();
				 
				 for(int k=0; k < serviceList.getLength(); k++)
				 {
					 Node serviceNode = serviceList.item(k);
					 
					 if (serviceNode.getNodeType() == Node.ELEMENT_NODE) {  
						 Element element = (Element)serviceNode;
												 
						 IService service = this.createService(element);
						 
						 if(service != null)
							 services.add(service);
					 }
				 }
			 }
		}
		
		return services;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	private IService createService(Element element){
		String name = element.getAttribute(this.classTag);
		String connectionString = element.getAttribute(this.connectionStringTag);
		Integer maxRequests = Integer.parseInt(element.getAttribute(this.maxRequestsTag));
		Long activationInterval = Long.parseLong(element.getAttribute(this.activationIntervalTag));
		
		try {
			
			Class<?> cService = Class.forName(name);
	        IService service = (IService)cService.getConstructor(
	        		String.class, Integer.class, Long.class).newInstance(
	        		connectionString, maxRequests, activationInterval);
			
			this.addKeys(element, service.getKeyManager());
			
			return service;
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		return null;
	}
	
	/**
	 * 
	 * @param root
	 * @param keyManager
	 */
	private void addKeys(Element root, IServiceKeyManager keyManager){
		NodeList keysList = root.getChildNodes();
		
		for(int i=0; i < keysList.getLength(); i++)
		{
			Node keysNode = keysList.item(i);
			
			if (keysNode.getNodeName().equals(this.keysTag)) {
		
				NodeList keyList = keysNode.getChildNodes(); 
				
				 for(int k=0; k < keyList.getLength(); k++)
				 {
					 Node keyNode = keyList.item(k);
					 
					 if (keyNode.getNodeType() == Node.ELEMENT_NODE) {  
						Element keyElement = (Element)keyNode;
							
						keyManager.addKey(keyElement.getAttribute(this.nameTag), keyElement.getAttribute(this.valueTag));
					}
				 }
			}
			
		}
		
	}
}
