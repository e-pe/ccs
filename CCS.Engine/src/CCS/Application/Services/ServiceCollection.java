/**
 * 
 */
package CCS.Application.Services;

import CCS.System.*;;

/**
 * This collection stores the services of CCS.
 *
 * @version 1.0
 * @since June 3, 2009
 */
public class ServiceCollection extends HashCollectionBase<Class<?>, IService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void add(IService service){
		this.collection.put(service.getClass(), service);
	}
	
	public IService get(Class<?> klass){
		return this.collection.get(klass);
	}
	
	public void remove(Class<?> klass){
		this.collection.remove(klass);
	}
}
