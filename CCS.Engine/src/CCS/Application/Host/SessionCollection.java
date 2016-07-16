/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.Application.Services.DTO.*;
import CCS.System.*;

/**
 * This class represents a collection for Session objects.
 *  
 * @version 1.0
 * @since June 11, 2009
 */
public class SessionCollection extends HashCollectionBase<String, Session> {

	public void add(Session session){
		this.collection.put(session.getId(), session);
	}
	
	public Session get(String id){
		return this.collection.get(id);
	}
	
	public void remove(String id){
		this.collection.remove(id);
	}
	
	/**
	 * Gets the session data transfer object list.
	 * @return
	 */
	public Vector<SessionDTO> toDTOList(){
		Vector<SessionDTO> sessionsDTO = new Vector<SessionDTO>();
		
		Enumeration<Session> ie = this.elements();
		
		while(ie.hasMoreElements())
			sessionsDTO.add(ie.nextElement().getDTO());
		
		return sessionsDTO;
	}
	
	
}
