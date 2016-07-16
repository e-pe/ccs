/**
 * 
 */
package CCS.Application.Host;

import java.util.*;

/**
 * This class stores all colors for participants that they can be marked with on the map.
 * @version 1.0
 * @since June 17, 2009
 */
public class MarkerColorManager {
	private Stack<MarkerColor> colors;
	
	
	public MarkerColorManager(){
		this.colors = new Stack<MarkerColor>();
	}
	
	public MarkerColor getMarkerColor(){
		if(this.colors.empty())
			this.addColors(this.colors);
		
		return this.colors.pop();
	}
		
	private void addColors(Stack<MarkerColor> colors){
		MarkerColorName[] type = MarkerColorName.values();
		
		for(int i = 0; i < type.length; i++){
			MarkerColorName color = type[i];
			//the red color is required to mark all possible stops
			if(color != MarkerColorName.Red)
				colors.push(MarkerColor.createColor(color));
		}
	}
		
}
