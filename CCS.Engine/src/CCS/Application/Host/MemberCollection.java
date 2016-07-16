/**
 * 
 */
package CCS.Application.Host;

import CCS.System.*;

/**
 * @version 1.0
 * @since June 11, 2009
 */
public class MemberCollection extends HashCollectionBase<String, Member>{
	
	public void add(Member member){
		this.collection.put(member.getId(), member);
	}
	
	public Member get(String id){
		return this.collection.get(id);
	}
	
	public void remove(String id){
		this.collection.remove(id);
	}

}
