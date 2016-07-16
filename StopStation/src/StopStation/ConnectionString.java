package StopStation;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;


public class ConnectionString {
	
	private String url;
	private String login;
	private String password;
	
	public ConnectionString(){}
	
	public String getUrl(){
		return this.url;
	}
	
	public void setUrl(String value){
		this.url = value;
	}
	
	public String getLogin(){
		return this.login;
	}
	
	public void setLogin(String value){
		this.login = value;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String value){
		this.password = value;
	}
	
	public static ConnectionString getConnectionString(String configPath){
			
		try{
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(new File(configPath));
										
			Element root = doc.getDocumentElement();
			
			NodeList nl = root.getElementsByTagName("connectionString");
			
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {

					//get the employee element
					Element el = (Element)nl.item(i);
					
					ConnectionString connectionString = new ConnectionString();
					connectionString.setUrl(el.getAttribute("url"));
					connectionString.setLogin(el.getAttribute("login"));
					connectionString.setPassword(el.getAttribute("password"));
					
					return connectionString;
				}
			}

		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}
