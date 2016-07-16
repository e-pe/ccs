package CCS.Application.Host;

import java.util.*;

import CCS.Application.*;
import CCS.Application.Services.*;
import CCS.Application.Services.DTO.*;
import CCS.Application.Status.*;

/**
 * 
 * This class stores all participants taking part at the same tour.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class Session {
	
	private String id;
	private Member author;
	private String name;
	private Date endedOn;
	private Date startedOn;
	private String password;
	private Stop currentStop;
	private StopCollection acceptedStops;
	private MarkerColorManager colorManager;
	private ParticipantCollection participants;
	private Vector<Status> statusStorage;
	private StatusLagDistanceThread lagDistanceThread;

	/**
	 * Constructor
	 * 
	 * @param author
	 */
	public Session(Member author, String name, String password){		
		this.name = name;
		this.author = author;
		this.password = password;
		this.startedOn = new Date();
		this.id = UUID.randomUUID().toString();
		
		this.acceptedStops = new StopCollection();
		this.colorManager = new MarkerColorManager();
		this.participants = new ParticipantCollection();
		this.statusStorage = new Vector<Status>();
		this.lagDistanceThread = new StatusLagDistanceThread(this);
		
		//adds the author to the participant list
		this.addParticipant(author);
		
		//starts the the thread for checking if any participant remained behind
		this.lagDistanceThread.start();
		
		//saves the session in the database
		this.save();
	}
	/**
	 * Gets the id of the current session. 
	 * @return
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * Gets the author of the current session.
	 * @return
	 */
	public Member getAuthor(){
		return this.author;
	}
	
	/**
	 * Gets the name.
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the password of the current session.
	 * @return
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * Gets the time when the session has been started.
	 * @return
	 */
	public Date getStartedOn(){
		return this.startedOn;
	}
	
	/**
	 * Gets the time when the session ended.
	 * @return
	 */
	public Date getEndedOn(){
		return this.endedOn;
	}
		
	/**
	 * Gets the application object.
	 * @return
	 */
	public Application getApplication(){
		return Application.getCurrent();
	}
	
	/**
	 * 
	 * @return
	 */
	public ConvoyControl getConvoyControl(){
		return this.getApplication().getConvoyControl();
	}
	
	/**
	 * Gets all participants.
	 * @return
	 */
	public ParticipantCollection getParticipants(){
		return this.participants;
	}
	
	/**
	 * Gets all stops during a tour. 
	 * @return
	 */
	public StopCollection getAcceptedStops(){
		return this.acceptedStops;
	}
	
	/**
	 * Gets the current stop during a tour.
	 * @return
	 */
	public Stop getCurrentStop(){
		return this.currentStop;
	}
	
	/**
	 * Sets the current stop during a tour.
	 */
	void setCurrentStop(Stop stop){
		this.currentStop = stop;
		
		//resets all suggested stops for each participant
		Enumeration<Participant> ie = this.participants.elements();		
		while(ie.hasMoreElements())
			ie.nextElement().getStopManager().setSuggestedCurrentStop(null);
	}
	
	/**
	 * Adds a participant.
	 * @param member
	 */
	public void addParticipant(Member member){
		//creates a new participant
		Participant participant = new Participant(this, member, 
				Character.toString((char)(97 + this.participants.size())), 
				this.colorManager.getMarkerColor());
							
		this.participants.add(participant);
		//sets the status message
		this.statusStorage.add(new Status(participant, 
				new StatusJoinSessionMessage()));
	}
	
	/**
	 * Removes a participant.
	 */
	public void removeParticipant(Participant participant){
		this.participants.remove(participant);
	}
	
	/**
	 * Gets a session data transfer object for client side communication.
	 * @return
	 */
	public SessionDTO getDTO(){
		SessionDTO dto = new SessionDTO();
		dto.setId(this.id);
		dto.setName(this.name);
		
		return dto;
	}
		
	/**
	 * Gets an action status of a participant.
	 * @return
	 */
	public Status getStatus(){
		Status status = this.statusStorage.lastElement();
		
		Long duration = (new Date()).getTime() - status.getTime().getTime();
		
		//status will be showed only *** minutes
		if(duration < this.getConvoyControl().getStatusBarHideInterval())
			return status;
		
		return null;
	}
	
	/**
	 * Sets a new status object.
	 * @param status
	 */
    public void setStatus(Status status){
		this.statusStorage.add(status);
	}
    
    /**
     * Gets the session context.
     * @return
     */
    public SessionContext getContext(){
    	return new SessionContext(this);
    }
	
    private void save(){
    	ServiceCollection services = Application.getCurrent().getServices();
    	DataAccessService service = (DataAccessService)services.get(DataAccessService.class);
    	
    	service.setSession(this);
    }
    
	/**
	 * Commits changes to the database.
	 */
	private void commit(){
		ServiceCollection services = Application.getCurrent().getServices();
    	DataAccessService service = (DataAccessService)services.get(DataAccessService.class);
    	
    	service.commitSession(this);
	}
	
	/**
	 * Disposes the current session.
	 */
	public void dispose(){
		this.endedOn = new Date();
		this.lagDistanceThread.done();
		this.commit();
	}
}
