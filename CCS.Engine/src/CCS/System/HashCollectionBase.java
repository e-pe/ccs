/**
 * 
 */
package CCS.System;

import java.util.*;

/**
 * 
 * @version 1.0
 * @since June 11, 2009
 * @param <K>
 * @param <V>
 *
 */
public abstract class HashCollectionBase<K, V>  {

	protected Hashtable<K, V> collection;
	
	/**
	 * Constructor
	 */
	public HashCollectionBase(){
		this.collection = new Hashtable<K, V>();
		
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
