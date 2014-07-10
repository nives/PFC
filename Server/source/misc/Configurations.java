package misc;

import javax.swing.UIManager;

import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.pagosoft.plaf.PgsLookAndFeel;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;

/**
 * This class is a miscellaneous class is used to store system configurations.
 * <p>
 * This class stores system configurations such as the default and actual window size, the installed and current LaF 
 * (Look and Feel) and the color blind mode. 
 *  
 * @author Eduard de Torres
 * @version v0.1  02/05/2014
 *
 */
public class Configurations {
	
	//Default size of the JFrame.
	private static final int DEFAULT_X = 1024;
	private static final int DEFAULT_Y =  768;
	
	//Size of the JFrames.
	private static int size_x;
	private static int size_y;
	
	//Names of the installed look and feel.
	private static String[] laf_links;
	private static String[] laf_names;
	//Current using look and feel.
	private static int current_laf;
	
	//Color-blind mode.
	private static boolean color_blind;
	
	/**
	 * Class constructor.
	 */
	public Configurations() {
		size_x = DEFAULT_X;
		size_y = DEFAULT_Y;
		color_blind = false;
		getLookAndFeels();
		new Language();
		new Resolution();
	}
	
	/**
	 * Changes the current size of the application's window given a new screen resolution.
	 * 
	 * @param window_resolution a new screen resolution to set the size of the application's window. 
	 */
	public static void updateResolution(String window_resolution) {
		String resolutions[] = window_resolution.split("x");
		size_x = Integer.parseInt(resolutions[0]);
		size_y = Integer.parseInt(resolutions[1]);
	}
	
	/**
	 * Installs all the preset LaFs (Look and Feel).
	 */
	private static void getLookAndFeels() {
		//UIManager.installLookAndFeel("System", UIManager.getSystemLookAndFeelClassName());
		UIManager.installLookAndFeel(new SeaGlassLookAndFeel().getName(), new SeaGlassLookAndFeel().getClass().getName());
		UIManager.installLookAndFeel(new SmartLookAndFeel().getName(), new SmartLookAndFeel().getClass().getName());
		UIManager.installLookAndFeel("PGS", new PgsLookAndFeel().getClass().getName());
		UIManager.LookAndFeelInfo laf[] = UIManager.getInstalledLookAndFeels();
		
		laf_links = new String[laf.length];
		laf_names = new String[laf.length];
		int i;
		for (i = 0; i < laf.length; i++) {
			laf_links[i] = laf[i].getClassName();
			laf_names[i] = laf[i].getName();
		}
	}
	
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the X-size of the window.
	 * @return the X-size of the window.
	 */
	public static int getSize_x() {
		return size_x;
	}
	
	/**
	 * Sets the X-size of the window.
	 * @param size_x the X-size of the window.
	 */
	public static void setSize_x(int size_x) {
		Configurations.size_x = size_x;
	}
	
	/**
	 * Returns the Y-size of the window.
	 * @return the Y-size of the window.
	 */
	public static int getSize_y() {
		return size_y;
	}
	
	/**
	 * Sets the Y-size of the window.
	 * @param size_y the Y-size of the window.
	 */
	public static void setSize_y(int size_y) {
		Configurations.size_y = size_y;
	}
	
	/**
	 * Returns the default X-size of the window.
	 * @return the default X-size of the window.
	 */
	public static int getDefaultX() {
		return DEFAULT_X;
	}
	
	/**
	 * Returns the default Y-size of the window.
	 * @return the default Y-size of the window.
	 */
	public static int getDefaultY() {
		return DEFAULT_Y;
	}

	/**
	 * Returns the current Look and Feel number.
	 * @return the current Look and Feel number.
	 */
	public static int getCurrent_laf() {
		return current_laf;
	}

	/**
	 * Sets the current Look and Feel number.
	 * @param current_laf a current Look and Feel number.
	 */
	public static void setCurrent_laf(int current_laf) {
		Configurations.current_laf = current_laf;
	}

	/**
	 * Returns all the links of the installed Look and Feel.
	 * @return all the links of the installed Look and Feel.
	 */
	public static String[] getLaf_links() {
		return laf_links;
	}

	/**
	 * Returns the names of the installed Look and Feel.
	 * @return the names of the installed Look and Feel.
	 */
	public static String[] getLaf_names() {
		return laf_names;
	}

	/**
	 * Returns if the color blind mode is activated or not.
	 * @return true if the color blind mode is activated.
	 */
	public static boolean isColor_blind() {
		return color_blind;
	}

	/**
	 * Sets the color blind mode to active or not.
	 * @param color_blind if true, the color blind mode will activate.
	 */
	public static void setColor_blind(boolean color_blind) {
		Configurations.color_blind = color_blind;
	}
		
}
