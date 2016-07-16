/**
 * 
 */
package CCS.Application.Services.DTO;

/**
 * This class represents a data transfer object for client communication. 
 * @version 1.0
 * @since July 6, 2009
 */
public class SessionDTO {
	private String id;
	private String name;
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
