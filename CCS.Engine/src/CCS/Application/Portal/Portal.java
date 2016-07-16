/**
 * 
 */
package CCS.Application.Portal;

import java.sql.ResultSet;
import java.util.*;


import CCS.Application.*;
import CCS.Application.Host.Member;
import CCS.Application.Services.*;

/**
 * @version 1.0
 * @since July 7, 2009
 */
public class Portal {
	private Application application;
	
	public Portal(Application application){
		this.application = application;
	}
	
	public DataAccessService getDataAccessService(){
		return (DataAccessService) this.application.getServices().get(DataAccessService.class);
	}
	
	/**
	 * registerMember insert a new member to the database
	 * @param loginName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param vehicleID
	 * @return
	 */
	public boolean registerMember(String loginName, String firstName, String lastName, String password, String vehicleID){

		try{
			ResultSet result = this.getDataAccessService().executeQuery("Select * From Member Where LoginName ='"+loginName+"'");
			if(!result.next()){
				return this.getDataAccessService().execute("Insert into Member (LoginName, FirstName, LastName, Password, VehicleID) Values ('"+loginName+"','"+firstName+"', '"+lastName+"', '"+password.hashCode()+"','"+vehicleID+"')");
			}
				this.getDataAccessService().releaseResultSet(result);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * unregisterMember delete member from database
	 * @param LoginName
	 * @param Password
	 * @return
	 */
	public boolean unregisterMember(String LoginName, String Password){
		if(this.checkPassword(LoginName, Password)){
			
			try{
				this.getDataAccessService().execute("Delete FROM Member WHERE '"+LoginName+"' = LoginName");
				System.out.println("test");
				return true;
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				System.out.println("Cannot Delete User");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * checkPassword check if the password and user are correct
	 * @param LoginName
	 * @param Password
	 * @return
	 */
	public boolean checkPassword(String LoginName, String Password)	{
		try{
			ResultSet ergebnis = this.getDataAccessService().executeQuery("SELECT * FROM Member WHERE '"+LoginName+"' = LoginName");
			ergebnis.next();
			Password = ("" + Password.hashCode());
			if(Password.equals(ergebnis.getString("Password"))){
				return true;
			}
			this.getDataAccessService().releaseResultSet(ergebnis);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println("Problem in Valedating Password");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * getVehicle returns the VehicleType
	 * @param LoginName
	 */
	public String getVehicle(String vehicleID){
		try{
			
			ResultSet result = this.getDataAccessService().executeQuery("SELECT Vehicle FROM VehicleType " +
					"WHERE '"+vehicleID+"' = VehicleID");
			if(!result.next()){
				return "Fehler";
			}
			String vehicle = result.getString("Vehicle");
			
			this.getDataAccessService().releaseResultSet(result);
			
			return vehicle;
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * getallVehicle returns the all Vehicle
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAllVehicle(){
		try{
			ArrayList<String> array = new ArrayList<String>();
			ResultSet result = this.getDataAccessService().executeQuery("SELECT Vehicle FROM VehicleType");
			
			while(result.next()){
				String car = result.getString("Vehicle");
				array.add(car);
				
			}
			this.getDataAccessService().releaseResultSet(result);
			
			return array;
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * getVehicleID returns the VehicleTypeID
	 * @param vehicle
	 * @return String
	 */
	public String getVehicleID(String vehicle){
		try{
			
			ResultSet result = this.getDataAccessService().executeQuery("SELECT * FROM VehicleType WHERE '"+vehicle+"' = Vehicle");
			result.next();
			String vehicleID = result.getString("VehicleID");
			
			this.getDataAccessService().releaseResultSet(result);
			
			return vehicleID;
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * setVehicleToMember update Member
	 * @param LoginName
	 * @param Vehicle
	 */
	public boolean setVehicleToMember(String loginName, String vehicle){
		try{
			this.getDataAccessService().execute("UPDATE Member, VehicleType SET " +
					"Member.VehicleID = VehicleType.VehicleID " +
					"WHERE LoginName = '"+loginName+"' AND VehicleType.Vehicle = '"+vehicle+"'");
			
			return true;
		}
		catch(Exception e){}
		
		return false;
	}
	
	/**
	 * getSessionFromMember get all session of a member
	 * @param loginName
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getSessionFromMember(String loginName){
		try{
			ArrayList<String> array = new ArrayList<String>();
			ResultSet result = this.getDataAccessService().executeQuery("SELECT Name FROM MemberRoute, Session" +
																		"WHERE MemberID = '"+this.getDataAccessService().getMemberID(loginName)+"' " +
																		"AND Session.SessionID = MemberRoute.SessionID");
			while(result.next()){
				array.add(result.getString("Name"));
			}
			this.getDataAccessService().releaseResultSet(result);
			return array;
		}
		catch(Exception e){}
		return null;
	}
	
	/**
	 * setFriend insert a new relationship into the database
	 * @param loginName
	 * @param friendName
	 * @return 
	 */
	public boolean setFriend(String loginName, String friendName){
		try{
			ResultSet result = this.getDataAccessService().executeQuery("Select * From FriendList " +
					"Where MemberID ='"+this.getDataAccessService().getMemberID(loginName)+"'" +
					"AND FriendID = '"+this.getDataAccessService().getMemberID(friendName)+"'");
		
			if(!result.next()){
				this.getDataAccessService().execute("Insert into Friendlist" +
													"(MemberID, FriendID) " +
													"Values ('"+this.getDataAccessService().getMemberID(loginName)+"'" +
													"'"+this.getDataAccessService().getMemberID(friendName)+"'");
				this.getDataAccessService().execute("Insert into Friendlist" +
													"(MemberID, FriendID) " +
													"Values ('"+this.getDataAccessService().getMemberID(friendName)+"'" +
													"'"+this.getDataAccessService().getMemberID(loginName)+"'");
				return true;
			}
			
		}
		catch(Exception e){}
		return false;
	}
	
	/**
	 * setFriendID insert a new relationship into the database
	 * @param memberID
	 * @param friendID
	 * @return 
	 */
	public boolean setFriendID(String memberID, String friendID){
		try{
			
		
				this.getDataAccessService().execute("Insert into FriendList (MemberID, FriendID) Values ('"+memberID+"','"+friendID+"')");
				this.getDataAccessService().execute("Insert into FriendList (MemberID, FriendID) Values ('"+friendID+"','"+memberID+"')");
				return true;
		
			
		}
		catch(Exception e){}
		return false;
	}
	
	/**
	 * deleteFriend delete entry from FriendList
	 * @param memberID
	 * @param friendID
	 * @return boolean
	 */
	public boolean deleteFriend(String memberID, String friendID){
		try{
			this.getDataAccessService().execute("Delete From FriendList " +
					"Where MemberID = '"+memberID+"'" +
					"AND FriendID = '"+friendID+"'");
			this.getDataAccessService().execute("Delete From FriendList " +
					"Where MemberID = '"+friendID+"'" +
					"AND FriendID = '"+memberID+"'");
			return true;
		}
		catch(Exception e){}
		return false;
	}
	
	/**
	 * validateMember check if the user registered
	 * @param memberID
	 * @return
	 */
	public Member validateMember(String memberID){
		Member member = null;
		
		try{
				ResultSet result = this.getDataAccessService().executeQuery("SELECT * FROM Member WHERE " +
																"MemberID = '"+ memberID + "'");
				
				while(result.next())
				{
					member = new Member();
					member.setId(result.getString("MemberID"));
					member.setLoginName(result.getString("LoginName"));
					member.setPassword(result.getString("Password"));
					member.setFirstName(result.getString("FirstName"));
					member.setLastName(result.getString("LastName"));
					member.setVehicle(this.getVehicle(result.getString("VehicleID")));
					
				}
				
				this.getDataAccessService().releaseResultSet(result);
				
				return member;
			}
			catch (Exception e){}
			
		return member;
	}
	
	/**
	 * validateMember check if the user registered
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Member validateMember(String firstName, String lastName){
		Member member = null;
		
		try{
				ResultSet result = this.getDataAccessService().executeQuery("SELECT * FROM Member WHERE " +
																"FirstName = '"+ firstName + "' AND" +
																"LastName = '"+ lastName +"'");
				
				while(result.next())
				{
					member = new Member();
					member.setId(result.getString("MemberID"));
					member.setLoginName(result.getString("LoginName"));
					member.setPassword(result.getString("Password"));
					member.setFirstName(result.getString("FirstName"));
					member.setLastName(result.getString("LastName"));
					member.setVehicle(this.getVehicle(result.getString("VehicleID")));
					
				}
				
				this.getDataAccessService().releaseResultSet(result);
				
				return member;
			}
			catch (Exception e){}
			
		return member;
	}
	
	/**
	 * getLoginNameFromID from Member
	 * @param memberID
	 * @return loginName
	 */
	public String getLoginNameFromID(String memberID){
		try{
			ResultSet result = this.getDataAccessService().executeQuery("SELECT * " +
					"From Session " +
					"Where MemberID = '"+memberID+"'");
			result.next();
			return result.getString("LoginName");
		}
		catch(Exception e){}
		return null;
	}
	
	/**
	 * updateMember update member
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param vehicleID
	 * @return boolean
	 */
	public boolean updateMember(String memberID,String firstName, String lastName, String password, String vehicleID){

		try{
			ResultSet result = this.getDataAccessService().executeQuery("Select * " +
					"From Member Where MemberID ='"+memberID+"'");
			if(result.next()){
				return this.getDataAccessService().execute("Update Member " +
					"Set FirstName = '"+firstName+"', LastName = '"+lastName+"', Password = '"+password.hashCode()+"', VehicleID = '"+vehicleID+"' " +
					"Where MemberID = '"+memberID+"'");
			}
				this.getDataAccessService().releaseResultSet(result);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	} 
	
	/**
	 * searchFriend get all member with firstName and lastName
	 * @param firstName
	 * @param lastName
	 * @return ArrayList<Member>
	 */
	public ArrayList<Member> searchFriend(String firstName, String lastName){
		try{
			ArrayList<Member> array = new ArrayList<Member>();
			Member member;
			ResultSet result = this.getDataAccessService().executeQuery("Select * " +
					"From Member Where FirstName = '"+firstName+"' And LastName = '"+lastName+"' Order By LoginName");
			
			while(result.next())
			{
				member = new Member();
				member.setId(result.getString("MemberID"));
				member.setLoginName(result.getString("LoginName"));
				member.setPassword(result.getString("Password"));
				member.setFirstName(result.getString("FirstName"));
				member.setLastName(result.getString("LastName"));
				member.setVehicle(this.getVehicle(result.getString("VehicleID")));
				array.add(member);
				
			}
			this.getDataAccessService().releaseResultSet(result);
			return array;
		}
		catch(Exception e){}
		return null;
	}
	
	/**
	 * getFriend get all friends of a member
	 * @param memberID
	 * @return ArrayList
	 */
	public ArrayList<Member> getFriend(String memberID){
		try{
			ArrayList<Member> array = new ArrayList<Member>();
			ResultSet result = this.getDataAccessService().executeQuery("SELECT FriendID FROM FriendList " +
					"Where MemberID = '"+memberID+"'");
			while(result.next()){ 
				array.add(this.validateMember(result.getString("FriendID")));
			}
			this.getDataAccessService().releaseResultSet(result);
			return array;
		}
		catch(Exception e){}
		return null;
	}
	
}
