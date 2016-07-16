package StopStation;

import java.sql.*;

public class StopStationResource {

	private Double latitude;
	private Double longitude;
	private Double radius;
	private String type;
	
	private static String PetrolStationQuery = "SELECT * FROM PetrolStation";
	private static String CompanyColumn = "Company";
	private static String LatitudeColumn = "Latitude";
	private static String LongitudeColumn = "Longitude";
	
	private ConnectionString connectionString;
	
	public StopStationResource(Double latitude, Double longitude, Double radius, String type){
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.type = type;
		
		this.connectionString = ConnectionString.getConnectionString(
				this.getClass().getResource("config.xml").getFile());
	}
	
	private String getResource(){
		String  xml = "<stop-stations>";
			
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(this.connectionString.getUrl(),
					this.connectionString.getLogin(), this.connectionString.getPassword());
			
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(PetrolStationQuery);
					
			while(result.next()){
				Double lat = result.getDouble(LatitudeColumn);
				Double lon = result.getDouble(LongitudeColumn);
				String name = result.getString(CompanyColumn);
				
				if(this.getDistance(this.latitude, this.longitude, lat, lon) <= this.radius){
					xml += "<stop-station name=\""+ name +"\" type=\""+ this.type +"\" latitude=\""+ lat +"\" longitude=\""+ lon +"\" />";
				}
			}
			
			result.close();
			statement.close();
			connection.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		return xml + "</stop-stations>";
	}

	private Double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
		//average earth radius in meters
		Integer radius = 6368000;   
			
		Double dLat = Math.toRadians(lat2-lat1);
		Double dLon = Math.toRadians(lon2-lon1);
	    
		Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	      		   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	      		   Math.sin(dLon/2) * Math.sin(dLon/2);
	    
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    
	    return radius * c; 
	}
		
	public String getXml(){
		return this.getResource();
	}
}
