/**
 * 
 */
package CCS.Application.Status;

import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * This class generates a status bar for the map.
 * @version 1.0
 * @since July 17, 2009
 */
public class StatusBarGenerator {
	private static String FontName = "Tahoma";
	private static Integer FontSize = 11;
	private static Integer TextLeftPadding = 15;
	private static Integer TextTopPadding = 14;
	private static Integer ColorBoxLeftPadding = 5;
	private static Integer ColorBoxTopPadding = 8;
	private static Integer ColorBoxSize = 6;
	
	private static BufferedImage generateBar(Status status, Integer width, Integer height){
		
		BufferedImage buffer = new BufferedImage(width, height, 
				 BufferedImage.TRANSLUCENT);

		Graphics2D g2d = buffer.createGraphics();		

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);		

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							 RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(190, 200, 221));
		g2d.setComposite(AlphaComposite.getInstance(
									AlphaComposite.SRC_OVER,  8 * 0.1f));

		g2d.fillRoundRect(0, 0, width, height, 3, 3);
		g2d.setComposite(AlphaComposite.Src);
		
		g2d.setColor(status.getMarker().getColor().getRgb());	
		
		g2d.fillRect(ColorBoxLeftPadding, ColorBoxTopPadding,
					 ColorBoxSize, ColorBoxSize);
				     		
		g2d.setColor(new Color(68, 85, 120));
		g2d.setFont(new Font(FontName, Font.PLAIN, FontSize));
		g2d.drawString(status.getText(), TextLeftPadding, TextTopPadding);
		
		return buffer;
	}
	
	public static byte[] generateBar(byte[] map, Status status){
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			
			BufferedImage view = ImageIO.read(new ByteArrayInputStream (map));
			
			Graphics2D g2d = view.createGraphics();
			
			g2d.drawImage(generateBar(status, view.getWidth() - 20, 20),
					null, 10, view.getHeight() - 25);
			
			ImageIO.write(view, "gif", outputStream);
			
			byte[] byteData = outputStream.toByteArray();
			
			outputStream.close();
			
			return byteData;
			
		} catch (IOException e) {}
		
		return null;
	}
}
