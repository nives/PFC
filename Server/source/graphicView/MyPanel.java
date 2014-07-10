package graphicView;

import javax.swing.JPanel;

/**
 * This class is an auxiliary abstract class used to generate standard {@link JPanel} to use as application's views. 
 * <p>
 * This class is used to generate standard panels to use as views that will be loaded in the application's main frame.
 * This class defines a set of abstract methods to make the configuration process a bit easier.  
 *  
 * @author Eduard de Torres
 * @version v0.1  01/27/2014
 *
 */
@SuppressWarnings("serial")
public abstract class MyPanel extends JPanel{
	
	/**
	 * Resizes the panel.
	 * <p>
	 * This method will be called when the main frame changes it's size.
	 * This method must implement all the necessary operations to adapt it's elements to the new size of the panel. 
	 */
	public abstract void resizePanel();
	
	/**
	 * Changes the language of the panel.
	 * <p>
	 * This method will be called when the main frame changes it's language.
	 * This method must implement all the necessary operations to change the language of all the dialogs contained in the panel.
	 */
	public abstract void changeLanguage();
	
	/**
	 * Changes the color's mode of the panel between normal and color blind mode.
	 * <p>
	 * This method will be called when the main frame changes it's color mode.
	 * This method must implement all the necessary operations to adapt it's element's colors to the new color mode.
	 */
	public abstract void changeColors();
		
}
