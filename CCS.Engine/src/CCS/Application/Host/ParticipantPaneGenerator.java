/**
 * 
 */
package CCS.Application.Host;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

/**
 * @version 1.0
 * @since June 3, 2009
 */
class ParticipantPaneGenerator {
	private static String FontName = "Tahoma";
	private static Integer FontSize = 11;
	private static Integer TextLeftPadding = 15;
	private static Integer TextTopPadding = 14;
	private static Integer ColorBoxLeftPadding = 5;
	private static Integer ColorBoxTopPadding = 8;
	private static Integer ColorBoxSize = 6;
	
	/**
	 * 
	 * @return
	 */
	private static BufferedImage generatePane(Participant participant){
		
		ParticipantCollection participants = participant.getSession().getParticipants();
		
		Integer paneWidth = 240;
		Integer paneLineCount = 1;
		Integer paneHeight = 20 * participants.size();
		
		BufferedImage buffer = new BufferedImage(paneWidth, paneHeight, 
												 BufferedImage.TRANSLUCENT);
		
		Graphics2D g2d = buffer.createGraphics();		

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);		
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							 RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(new Color(190, 200, 221));
		g2d.setComposite(AlphaComposite.getInstance(
									AlphaComposite.SRC_OVER,  7 * 0.1f));
		
		g2d.fillRoundRect(0, 0, paneWidth, paneHeight, 3, 3);
		g2d.setComposite(AlphaComposite.Src);
		
		Enumeration<Participant> ie = participants.elements();
		while(ie.hasMoreElements()){
			Participant par = ie.nextElement();
			//generates a status string of each participant
			String status = par.getLabel().toUpperCase() + " : " + 
			   				par.getFirstName() + " " +
			   				par.getLastName() + " - " +
			   				Math.round(par.getSpeed().getValue()) + " km/h" + " - " + 
			   				Math.round(par.getTank().getValue()) + " l" + " - " ;
			
			Double distance = participant.getDistance(par);
			
			//adds the distance to other participants
			if(distance < 1000)
				status += Math.round(distance) + " m";
			else
				status += Math.round(distance / 1000) + " km";
				
			g2d.setColor(par.getColor().getRgb());	
			
			g2d.fillRect(ColorBoxLeftPadding, 
						 ColorBoxTopPadding + (TextTopPadding * (paneLineCount - 1)), 
					     ColorBoxSize, ColorBoxSize);
				
			g2d.setColor(new Color(68, 85, 120));
			g2d.setFont(new Font(FontName, Font.PLAIN, FontSize));
			g2d.drawString(status, TextLeftPadding, TextTopPadding * paneLineCount);
						 
			
			paneLineCount++;
		}
					
		return buffer;
	}
	
	/**
	 * 
	 * @param map
	 * @param participants
	 * @return
	 */
	public static byte[] generateView(byte[] map, Participant participant){
			
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			
			BufferedImage view = ImageIO.read(new ByteArrayInputStream (map));
			
			Graphics2D g2d = view.createGraphics();
			g2d.drawImage(generatePane(participant), null, 10, 10);
			
			ImageIO.write(view, "gif", outputStream);
			
			byte[] byteData = outputStream.toByteArray();
			
			outputStream.close();
			
			return byteData;
			
		} catch (IOException e) {}
		
		return null;
	}
}
