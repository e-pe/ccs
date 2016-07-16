/**
 * 
 */
package CCS.Application.Services.DTO;

/**
 * This class represents a data transfer object for client communication. 
 * 
 * @version 1.0
 * @since July 6, 2009
 */
public class MapViewDTO {
	private byte[] bytes;
	private String stopId;
	private Boolean stopAccepted;
	
	public byte[] getBytes(){
		return this.bytes;
	}
	
	public void setBytes(byte[] bytes){
		this.bytes = bytes;
	}
	
	public String getStopId(){
		return this.stopId;
	}
	
	public void setStopId(String stopId){
		this.stopId = stopId;
	}
	
	public Boolean getStopAccepted(){
		return this.stopAccepted;
	}
	
	public void setStopAccepted(Boolean stopAccepted){
		this.stopAccepted = stopAccepted;
	}
}
