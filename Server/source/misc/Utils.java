package misc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class is an auxiliary class with a set of miscellaneous methods.
 * <p>
 * This class has a set of miscellaneous methods that are usefull in different situations.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/28/2014
 *
 */
public class Utils {
	
	/**
	 * Returns the system classpath.
	 * @return the system classpath.
	 */
	public static /*String[]*/String getClassPath() {
		//String classpath = System.getProperty("java.class.path");
		//return "C:/Users/Eduard/Dropbox/PFC-TFM/Workspace/IATrainer/bin";//classpath.split(File.pathSeparator);
		return "C:/Users/Eduard/Dropbox/Salle/Dropbox/PFC-TFM/Workspace/IATrainer/bin";//classpath.split(File.pathSeparator);
	}
	
	/**
	 * Loads an {@link Image} from "Images" folder using the image's file name.
	 * 
	 * @param name the image's file name to load.
	 * @return an {@link Image} loaded from the file.
	 */
	public static BufferedImage loadImage (String name) {
		try {
			File file = new File(Utils.getClassPath()+File.separator+"images"+File.separator+name);
			//File file = new File(Utils.getClassPath()[0]+File.separator+"images"+File.separator+name);
				
			BufferedImage image_raw = ImageIO.read(file);
			return image_raw;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), 
										  "Unable to open "+name+".", 
										  "Error",
										  JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
			System.exit(0);
		}
		return null;		
	}
	
	/**
	 * Resizes an image to a specified size.
	 * <p>
	 * Given a raw image, this method returns a new image resulting from the resize of the raw image. 
	 * 
	 * @param image a raw image to resize.
	 * @param width the width of the resulting image.
	 * @param height the heigh of the resulting image.
	 * @return a resized image.
	 */
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		int w = image.getWidth();  
        int h = image.getHeight();  
        BufferedImage dimg = new BufferedImage(width, height, image.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
        g.drawImage(image, 0, 0, width, height, 0, 0, w, h, null);  
        g.dispose();  
        return dimg; 
	}
	
	/**
	 * Loads an {@link Image} to a {@link JLabel}.
	 * 
	 * @param label the {@link JLabel} to contain the {@link Image}
	 * @param image the {@link Image} to add to the {@link JLabel}
	 * @return the {@link JLabel} with the {@link Image} in it.
	 */
	public static JLabel loadImageToLabel(JLabel label, ImageIcon image) {
		label.setIcon(image);
		return label;
	}
	
	/**
	 * Configures a new {@link JButton}.
	 * <p>
	 * Sets the name and the size of a {@link JButton}.
	 * 
	 * @param name the name of the resulting {@link JButton}.
	 * @param pos_x the X position of the button.
	 * @param pos_y the Y position of the button.
	 * @return a configured {@link JButton} with a name and position.
	 */
	public static JButton configureButton(String name, int pos_x, int pos_y) {
		JButton button = new JButton(name);
		button.setBounds(pos_x - button.getPreferredSize().width / 2, 
					     pos_y - button.getPreferredSize().width / 2, 
						 button.getPreferredSize().width, 
						 button.getPreferredSize().height);
		return button;
	}
		
}
