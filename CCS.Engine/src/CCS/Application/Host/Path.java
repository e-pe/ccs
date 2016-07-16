/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

import CCS.Application.Application;
import CCS.Application.Services.GnuiteService;

/**
 * This class represents a path on the map. 
 * 
 * @version 1.0
 * @since June 5, 2009
 */
public class Path {
	
	private Location[] points;
	
	/**
	 * Constructor
	 */
	public Path(Location[] points){
		this.points = points;
	}
	
	/**
	 * Gets all points of the path. 
	 * @return
	 */
	public Location[] getPoints(){
		return this.points;
	}
	
	/**
	 * Creates a new path object with the right configuration
	 * @param type
	 * @param stop
	 * @return
	 */
	static Path getPath(PathType type, Vector<Position> positions, Stop stop){
		Application app = Application.getCurrent();
		
		GnuiteService service = (GnuiteService)app.getServices().get(GnuiteService.class);
		
		if(type == PathType.ToPartcipant)
			return new Path((Location[])positions.toArray(new Location[]{}));
		
		else if(stop != null && type == PathType.ToStopStation)
			return new Path(service.getPathLocations(positions.lastElement(), stop.getLocation()));

		return null;
	}
}
