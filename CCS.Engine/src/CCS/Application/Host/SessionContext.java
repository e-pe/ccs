/**
 * 
 */
package CCS.Application.Host;

/**
 * @version 1.0
 * @since July 18, 2009
 */
public class SessionContext {
	private Session session;
	private Participant leader;
	private Participant behind;
	
	/**
	 * Constructor
	 * @param session
	 */
	public SessionContext(Session session){
		this.session = session;
		
		this.initialize();
	}
	
	/**
	 * Gets the first participant.
	 * @return
	 */
	public Participant getLeader(){
		return this.leader;
	}
	
	/**
	 * Gets the last participant. 
	 * @return
	 */
	public Participant getBehind(){
		return this.behind;
	}
		
	/**
	 * Finds the first and the last participant.
	 */
	private void initialize(){
		try {
			Double maxDistance = 0.0;
			ParticipantCollection participants = this.session.getParticipants();
			
			for(int i = 0; i < participants.size(); i++){
				Participant pivot = participants.get(i);
				
				for(int j = i + 1; j < participants.size(); j++){
					Participant current = participants.get(j);
					
					Double distance = pivot.getDistance(current);
					
					if(distance > maxDistance){
						maxDistance = distance;
						this.behind = pivot;
						this.leader = current;
					}
				}
			}
			
		}catch(Exception e){
			
		}
	}
}
