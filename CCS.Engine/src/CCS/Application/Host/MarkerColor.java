/**
 * 
 */
package CCS.Application.Host;

import java.awt.*;

/**
 * @version 1.0
 * @since July 8, 2009
 */
public class MarkerColor {
	private MarkerColorName name;
	private Color color;
	
	/**
	 * Constructor
	 * @param name
	 * @param color
	 */
	public MarkerColor(MarkerColorName name, Color color){
		this.name = name;
		this.color = color;
	}
	
	public MarkerColorName getName(){
		return this.name;
	}
	
	public Color getRgb(){
		return this.color;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static MarkerColor createColor(MarkerColorName name){
		Color color = null;
		
		if(name == MarkerColorName.Black)
			color = new Color(100, 100, 100);
		else if(name == MarkerColorName.Blue)
			color = new Color(67, 170, 254);
		else if(name == MarkerColorName.Brown)
			color = new Color(181, 135, 0);
		else if(name == MarkerColorName.Gray)
			color = new Color(186, 186, 185);
		else if(name == MarkerColorName.Green)
			color = new Color(100, 185, 73);
		else if(name == MarkerColorName.Orange)
			color = new Color(254, 168, 0);
		else if(name == MarkerColorName.Purple)
			color = new Color(151, 115, 254);
		else if(name == MarkerColorName.Red)
			color = new Color(254, 99, 86);
		else if(name == MarkerColorName.White)
			color = new Color(254, 253, 253);
		else if(name == MarkerColorName.Yellow)
			color = new Color(252, 243, 87);
		
		return new MarkerColor(name, color);
			
	}
}
