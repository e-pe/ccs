/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.System.*;

/**
 * This class represents a collection for Stop objects.
 * 
 * @version 1.0
 * @since June 4, 2009
 */
public class StopCollection extends HashCollectionBase<String, Stop> {
	
	public void add(Stop stop){
		this.collection.put(stop.getId(), stop);
	}
	
	public Stop get(String id){
		return this.collection.get(id);
	}
	
	public void remove(String id){
		this.collection.remove(id);
	}
	
	public void write(Queue<Stop> queue){
		Enumeration<Stop> ie = this.elements();
		
		while(ie.hasMoreElements())
			queue.offer(ie.nextElement());
	}
}
