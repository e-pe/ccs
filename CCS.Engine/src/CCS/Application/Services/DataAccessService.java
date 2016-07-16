package CCS.Application.Services;

import java.sql.*;
import java.util.*;

import CCS.Application.Host.*;

/**
 * This service provides the possibility to communicate with the CCS-DataBase.
 * 
 * @version 1.0
 * @since June 3, 2009
 */
public class DataAccessService implements IService {

	private Integer maxRequests;
	private Long activationInterval;
	private String connectionString;
	private DataAccessKeyManager keyManager;
	
	public DataAccessService(String connectionString, Integer maxRequests, Long activationInterval){
		this.connectionString = connectionString;
		this.maxRequests = maxRequests;
		this.activationInterval = activationInterval;
		
		this.keyManager = new DataAccessKeyManager();
	}
	
	public String getConnectionString() {
		return this.connectionString;
	}

	public IServiceKeyManager getKeyManager() {
		return this.keyManager;
	}
	
	public Long getActivationInterval() {		
		return this.activationInterval;
	}

	public Integer getMaxRequests() {		
		return this.maxRequests;
	}
	
	/**
	 * Tries to connect to the database
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException{
		 			
		Class.forName("com.mysql.jdbc.Driver");
			
		Connection connection = DriverManager.getConnection(this.getConnectionString(),
					this.getLoginName(), this.getPassword());
			
		Statement statement = connection.createStatement();
			
		return statement.executeQuery(query);
	}
	
	 /** 
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public boolean execute(String query) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
			
		Connection connection = DriverManager.getConnection(this.getConnectionString(), 
					this.getLoginName(), this.getPassword());
			
		Statement statement = connection.createStatement();
			
		Boolean state = statement.execute(query);
			
		statement.close();
		connection.close();
			
		return state;
	}
	
	/**
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void releaseResultSet(ResultSet resultSet) throws SQLException{
		Statement statement = resultSet.getStatement();
		Connection connection = statement.getConnection();
		
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	/**
	 * Gets the login name of the user that has access to the ccs database.
	 * @return
	 */
	public String getLoginName(){
		return this.keyManager.getKey("login").getValue();
	}
	
	/**
	 * Gets the password of the user that has access to the ccs database.
	 * @return
	 */
	public String getPassword(){
		return this.keyManager.getKey("password").getValue();
	}
	
	/**
	 * validateMember check if the user registered
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Member validateMember(String loginName, String password){
		Member member = null;
		
		try{
				ResultSet result = this.executeQuery("SELECT * FROM Member WHERE " +
																"LoginName = '"+ loginName + "' AND " +
																"Password='"+ password.hashCode() +"'");
				
				while(result.next())
				{
					member = new Member();
					member.setId(result.getString("MemberID"));
					member.setLoginName(loginName);
					member.setPassword(password);
					member.setFirstName(result.getString("FirstName"));
					member.setLastName(result.getString("LastName"));	
				}
				
				this.releaseResultSet(result);
				
				return member;
			}
			catch (Exception e){}
			
		return member;
	}
	
	/**
	 * get MemberID from MemberName
	 * @param loginName
	 * @return MemberID
	 */
	public String getMemberID(String loginName){
		try{
			ResultSet result = this.executeQuery("SELECT * From Member Where LoginName = '"+loginName+"'");
			result.next();
			return result.getString("MemberID");
		}
		catch(Exception e){}
		return null;
	}
	
	/**
	 * get SessionID from SessionName
	 * @param sessionName
	 * @return sessionID
	 */
	public String getSessionID(String sessionName){
		try{
			ResultSet result = this.executeQuery("SELECT * From Session " +
					"Where Name = '"+sessionName+"'");
			result.next();
			return result.getString("SessionID");
		}
		catch(Exception e){}
		return null;
	}
	
	
	
	/**
	 * Inserts a new session into the database
	 * @param sessionName
	 * @param password
	 * @return boolean
	 */
	public boolean setSession(Session session){
		try {
				this.execute("INSERT INTO Session(SessionID, Name, Password, StartedOn) " +
						     "VALUES ('"+ session.getId() +"','"+ session.getName() +"', '"+ session.getPassword() +"', '"+ new Timestamp(session.getStartedOn().getTime()) +"')");
				return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public boolean commitSession(Session session){
		try {
			this.execute("UPDATE Session SET " +
					     "EndedOn = '" + new Timestamp(session.getEndedOn().getTime()) + "' WHERE SessionID='" + session.getId() + "'");
			return true;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	
	return false;
	}
	
	/**
	 * setGPS insert new GPS coordinates into the database
	 * @param SessionName
	 * @param loginName
	 * @param longitude
	 * @param latitude
	 * @param speed
	 * @param tank
	 * @return boolean
	 */
	public boolean setGps(Participant participant){
		try {
			
			this.execute("INSERT INTO GPS (SessionID, MemberID, Latitude, Longitude, TimeStamp, Speed, Tank)" +
					     "VALUES ('" + participant.getSession().getId() + "', " +
					     		"'" + participant.getId() + "', " +
					     		participant.getPosition().getLatitude() + "," +
					     		participant.getPosition().getLongitude() + "," +
					     		"'" + new Timestamp(participant.getPosition().getTime().getTime()) + "'," +
					     		Math.round(participant.getSpeed().getValue()) + "," +
					     		Math.round(participant.getTank().getValue()) + ")");
					    
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * setMemberRoute insert new entry
	 * @param loginName
	 * @param sessionName
	 * @return boolean
	 */
	public boolean setMemberRoute(String loginName, String sessionName){
		try{
			this.execute("INSERT into MemberRoute (MemberID, SessionID) " +
						 "Values ('"+getMemberID(loginName)+"', '"+getSessionID(sessionName)+"')");
			return true;
		}
		catch (Exception e){}
		
		return false;
	}
	
	/**
	 * getMemberFromRoute
	 * @param sessionName
	 * @return ArrayList
	 */
	public ArrayList<String> getMemberFromRoute(String sessionName){
		try{
			ArrayList<String> array = new ArrayList<String>();
			ResultSet result = this.executeQuery("Select LoginName " +
												 "FROM MemberRoute, Member" +
												 "WHERE SessionID = '"+sessionName+"' " +
													"AND Member.MemberID = MemberRoute.MemberID");
			while(result.next()){
				array.add(result.getString("LoginName"));
			}
			return array;
		}
		catch(Exception e){}
		return null;
	}
	
	/**
	 * getFriend get all friends of a member
	 * @param loginName
	 * @return ArrayList
	 */
	public ArrayList<String> getFriend(String loginName){
		try{
			ArrayList<String> array = new ArrayList<String>();
			ResultSet result = this.executeQuery("SELECT LoginName FROM Member, FriendList " +
					"WHERE FriendID = Member.MemberID AND FriendList.MemberID = '"+getMemberID(loginName)+"'");
			while(result.next()){
				array.add(result.getString("LoginName"));
			}
			this.releaseResultSet(result);
			return array;
		}
		catch(Exception e){}
		return null;
	}
}
