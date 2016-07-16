/**
 * 
 */
package CCS.System;

import java.util.*;

/**
 * @version 1.0
 * @since July 17, 2009
 */
public class ArrayCollectionBase<V> {
	protected Vector<V> collection;
	
	/**
	 * Constructor
	 */
	public ArrayCollectionBase(){
		this.collection = new Vector<V>();
		
	}
	/**
	 * Gets the enumeration object.
	 * @return
	 */
	public Enumeration<V> elements(){
		return this.collection.elements();
	}
		
	/**
	 * Gets the size of the collection.
	 * @return
	 */
	public Integer size(){
		return this.collection.size();
	}
}
