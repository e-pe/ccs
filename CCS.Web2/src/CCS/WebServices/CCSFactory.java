package CCS.WebServices;

import java.util.*;
import javax.jws.*;

import CCS.Application.*;
import CCS.Application.Host.*;
import CCS.Application.Services.DTO.*;

@WebService
/**
 * This class is the end point of CCS-Application.
 * 
 * @version 1.0
 * @since June 8, 2009
 */
public class CCSFactory {
	
	@WebMethod
	/**
	 * Logs in into CCS.
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public String login(
			@WebParam(name="loginName") String loginName, 
			@WebParam(name="password") String password){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Member member = control.login(loginName, password);
		
		if(member != null)
			return member.getId();
		
		return "-1";
	}
	
	@WebMethod
	/**
	 * Creates a new session for a tour.
	 * 
	 * @param memberId
	 * @return
	 */
	public String createSession(
			@WebParam(name="authorId") String authorId, 
			@WebParam(name="name") String name, 
			@WebParam(name="password") String password){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Member author = control.getMembers().get(authorId);
		Session session = control.createSession(author, name, password);
		
		return session.getId();
	}
	
	@WebMethod
	/**
	 * Provides the possibility for an authenticated member to join a tour. 
	 * 
	 * @param sessionId
	 * @param memberId
	 * @param password
	 */
	public Boolean joinSession(
			@WebParam(name="sessionId") String sessionId, 
			@WebParam(name="memberId") String memberId, 
			@WebParam(name="password") String password){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Member member = control.getMembers().get(memberId);
		Session session = control.getSessions().get(sessionId);
		
		if(session.getPassword().equals(password)){
			session.addParticipant(member);
			return true;
		}	
		
		return false;
	}
	
	@WebMethod
	/**
	 * Logs out an authenticated participant.
	 * 
	 * @param sessionId
	 * @param memberId
	 */
	public void exit( 
			@WebParam(name="memberId") String memberId){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Member member = control.getMembers().get(memberId);
		
		control.logout(member);
		
	}
	
	@WebMethod
	/**
	 * Gets a list of current sessions.
	 * @return
	 */
	public Vector<SessionDTO> getSessions(){
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		return control.getSessions().toDTOList();
	}
	
	@WebMethod
	/**
	 * Shows the positions of all participants and an available stop during a tour on the map.
	 * 
	 * @param sessionId
	 * @param participantId
	 * @param zoomLevel
	 * @param width
	 * @param height
	 * @param stopType, allowed only GasStation, RestStop, WC
	 * @param pathType, allowed only ToParticipant, ToStopStation, None  
	 * @param showParticipantPane
	 */
	public MapViewDTO getMap(
			@WebParam(name="sessionId") String sessionId, 
			@WebParam(name="participantId") String participantId, 
			@WebParam(name="zoomLevel") Integer zoomLevel, 
			@WebParam(name="width") Integer width, 
			@WebParam(name="height") Integer height, 
			@WebParam(name="stopType") String stopType, 
			@WebParam(name="pathType") String pathType, 
			@WebParam(name="showParticipantPane") Boolean showParticipantPane){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Session session = control.getSessions().get(sessionId);
		Participant participant = session.getParticipants().get(participantId);
		
		return participant.getMap(zoomLevel, width, height, StopType.valueOf(stopType), PathType.valueOf(pathType), showParticipantPane).getDTO();
	}
		
	@WebMethod
	/**
	 * Sets the participant settings during a tour.
	 * 
	 * @param sessionId
	 * @param participantId
	 * @param latitude
	 * @param longtitude
	 * @param speed
	 * @param tank
	 */
	public void setParticipantSettings(
			@WebParam(name="sessionId") String sessionId, 
			@WebParam(name="participantId") String participantId, 
			@WebParam(name="latitude") Double latitude, 
			@WebParam(name="longitude") Double longitude, 
			@WebParam(name="speed") Double speed, 
			@WebParam(name="tank") Double tank){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Session session = control.getSessions().get(sessionId);
		Participant participant = session.getParticipants().get(participantId);
		
		participant.setTank(new Tank(tank));
		participant.setSpeed(new Speed(speed));
		participant.setPosition(new Position(latitude, longitude));
		participant.commit();
	}
	
	@WebMethod
	/**
	 * Accepts a stop.
	 * 
	 * @param sessionId
	 * @param participantId
	 * @param stopId
	 */
	public void acceptStop(
			@WebParam(name="sessionId") String sessionId, 
			@WebParam(name="participantId") String participantId, 
			@WebParam(name="stopId") String stopId){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Session session = control.getSessions().get(sessionId);
		Participant participant = session.getParticipants().get(participantId);
		Stop stop = participant.getStopManager().getStop(stopId);
		
		participant.accept(stop);
		
	}
	
	@WebMethod
	/**
	 * Denies a stop.
	 * 
	 * @param sessionId
	 * @param participantId
	 * @param stopId
	 */
	public void denyStop(
			@WebParam(name="sessionId") String sessionId, 
			@WebParam(name="participantId") String participantId, 
			@WebParam(name="stopId") String stopId){
		
		Application app = Application.getCurrent();
		
		ConvoyControl control = app.getConvoyControl();
		
		Session session = control.getSessions().get(sessionId);
		Participant participant = session.getParticipants().get(participantId);
		Stop stop = participant.getStopManager().getStop(stopId);
		
		participant.deny(stop);
		
	}
	
	@WebMethod
	public MapViewDTO getTestMap(){
		String participantId = this.login("FloHaas", "test");
		String sessionId = this.createSession(participantId, "", "");
		
		this.setParticipantSettings(sessionId, participantId, 48.353074, 10.886300, 200.0, 60.0);
		
		return this.getMap(sessionId, participantId, 12, 500, 300, "GasStation", "ToStopStation", true);
	}
	
	@WebMethod
	public MapViewDTO getTestMap2(){
		String participantId1 = this.login("FloHaas", "test");
		String partricipantId2 = this.login("Eugen", "test");
		String participantId3 = this.login("Poldi", "test");
		String participantId4 = this.login("Sputnik", "test");
		
		String sessionId = this.createSession(participantId1, "", "");
			
		this.joinSession(sessionId, partricipantId2, "");
		this.joinSession(sessionId, participantId3, "");
		this.joinSession(sessionId, participantId4, "");
		
		this.setParticipantSettings(sessionId, participantId1, 48.353074, 10.886300, 200.0, 60.0);
		this.setParticipantSettings(sessionId, partricipantId2, 48.344213, 10.902987, 150.0, 15.123);
		this.setParticipantSettings(sessionId, participantId3, 48.329093, 11.165199, 100.0, 20.7);
		this.setParticipantSettings(sessionId, participantId4, 48.326126, 17.031555, 50.0, 25.3);
		
		return this.getMap(sessionId, participantId1, 12, 500, 300, "None", "None", true);
	}

	@WebMethod
	/**
	 * returns 1 to check network-connection
	 * 
	 */
	public int checkConnection(){
		return 1;
	}

}
