/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.Application.*;
import CCS.Application.Services.*;

/**
 * This class controls the process of convoy driving.
 * @version 1.0
 * @since July 7, 2009
 */
public class ConvoyControl {
	
	private Application application;
	private MemberCollection members;
	private SessionCollection sessions;
	private Long statusBarHideInterval;
	private Long allowedLagDistance;
	private Long verifyingLagDistanceInterval;
	
	public ConvoyControl(Application application){
		this.application = application;
		
		this.members = new MemberCollection();
		this.sessions = new SessionCollection();	
	}
	
	/**
	 * Gets the session collection. 
	 * @return
	 */
	public SessionCollection getSessions(){
		return this.sessions;
	}
	
	/**
	 * Gets all authenticated members.
	 * @return
	 */
	public MemberCollection getMembers(){
		return this.members;
	}
	
	/**
	 * Gets the interval in milliseconds when the status bar should be hidden if no event happens.
	 * @return
	 */
	public Long getStatusBarHideInterval(){
		return this.statusBarHideInterval;
	}
	
	/**
	 * Sets the interval in milliseconds when the status bar should be hidden if no event happens.
	 * @param statusBarHideInterval
	 */
	public void setStatusBarHideInterval(Long statusBarHideInterval){
		this.statusBarHideInterval = statusBarHideInterval;
	}
	
	/**
	 * Gets the distance in meters that indicates if a participant lags behind or not.
	 * @return
	 */
	public Long getAllowedLagDistance(){
		return this.allowedLagDistance;
	}
	
	/**
	 * Sets the distance in meters that indicates if a participant lags behind or not.
	 * @param allowedLagDistance
	 */
	public void setAllowedLagDistance(Long allowedLagDistance){
		this.allowedLagDistance = allowedLagDistance;
	}
	
	/**
	 * Gets the interval in milliseconds when the lag distance should be verified.
	 * @return
	 */
	public Long getVerifyingLagDistanceInterval(){
		return this.verifyingLagDistanceInterval;
	}
	
	/**
	 * Sets the interval in milliseconds when the lag distance should be verified.
	 * @param verifyingLagDistanceInterval
	 */
	public void setVerifyingLagDistanceInterval(Long verifyingLagDistanceInterval){
		this.verifyingLagDistanceInterval = verifyingLagDistanceInterval;
	}

	/**
	 * Creates a new session, if a member want to start a new tour. 
	 * @return
	 */
	public Session createSession(Member author, String name, String password){
		Session session = null;
		
		if(this.isInSession(author) == null){
			session = new Session(author, name, password);
			//adds a new session to the session collection
			this.sessions.add(session);
		}
		
		return session;
	}
	
	/**
	 * Looks up if a member belongs to a session.
	 * @param member
	 * @return
	 */
	public Session isInSession(IMember member){
		Enumeration<Session> ie = this.sessions.elements();
		
		while(ie.hasMoreElements()){
			Session session = ie.nextElement();
			Participant participant = session.getParticipants().get(member.getId());
			
			if(participant != null)
				return session;
		}
		
		return null;
	}
	
	/**
	 * Validates a member account.
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Member login(String loginName, String password){
		ServiceCollection services = this.application.getServices();
		
		DataAccessService service = (DataAccessService)services.get(DataAccessService.class);
		
		Member member = service.validateMember(loginName, password);
			
		if(member != null)
			this.logout(member);
			this.members.add(member);
		
		return member;
	}
	
	/**
	 * Logs out from the system.
	 */
	public void logout(IMember member){
		Session session = this.isInSession(member);
		
		if(session != null){
			session.removeParticipant(
					session.getParticipants().get(member.getId()));
		
			if(session.getParticipants().size() == 0){
				session.dispose();
				this.sessions.remove(session.getId());
				
				session = null;
			}
		}
		
		this.members.remove(member.getId());
	}
}
