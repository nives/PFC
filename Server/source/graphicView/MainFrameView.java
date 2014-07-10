package graphicView;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import misc.Configurations;
import misc.Language;

/**
 * This class implements the application's main frame which contains all the {@link JPanel} with the different views and 
 * consequently generates the application's window.
 * <p>
 * This class implements all the functionalities needed to configure the application's window. These configurations goes from
 * the application's window's size, to the look and feel loaded. It also contains a {@link CardLayout} to swap between the
 * different views. 
 *  
 * @author Eduard de Torres
 * @version v0.1  03/25/2014
 * 
 */
@SuppressWarnings("serial")
public class MainFrameView extends JFrame{
	
	private static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
	/**
	 * Class constructor.
	 */
	public MainFrameView() {
		super();
		setTitle(Language.getDialog(0));
		initialize();
	}
	
	/**
	 * Initializes all the configurations such as the LaF (Look and Feel), the window's size, the closing operation or the 
	 * initial position to set the window.
	 */
	private void initialize() {
		setLookAndFeel();
		//No resizable.
		setResizable(false);
		//Size of the JFrame
		setSize(Configurations.getSize_x(), Configurations.getSize_y());
		//Centered Location in screen.
		setLocationRelativeTo(null);
		//Close operation.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		
		//JPanels and Layouts.
		getContentPane().setLayout(new CardLayout());
		UIManager.put("OptionPane.yesButtonText", Language.getDialog(21));  
		UIManager.put("OptionPane.noButtonText", Language.getDialog(22));  
		UIManager.put("OptionPane.cancelButtonText", Language.getDialog(9));  
	}
	
	/**
	 * Resizes the application's window and repaints it's elements.
	 */
	public void resizeFrame() {
		setSize(Configurations.getSize_x(), Configurations.getSize_y());
		Dimension d = getSize();
		Configurations.setSize_x((int)d.getWidth());
		Configurations.setSize_y((int)d.getHeight());
		repaint();
	}
	
	/**
	 * Changes the window's title and the standard button language to new selected language. 
	 */
	public void changeLanguage() {
		setTitle(Language.getDialog(0));
		UIManager.put("OptionPane.yesButtonText", Language.getDialog(21));  
		UIManager.put("OptionPane.noButtonText", Language.getDialog(22));  
		UIManager.put("OptionPane.cancelButtonText", Language.getDialog(9)); 
	}

	/**
	 * Changes all the necessary elements from the window's application to show them in color blind mode.
	 */
	public void changeColors() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Adds a new {@link MyPanel} to window's {@link CardLayout} and give a name to that panel.
	 * 
	 * @param panel a panel to load to the {@link CardLayout}.
	 * @param name the name to give to the panel to load to the {@link CardLayout}.
	 */
	public void addToCard(MyPanel panel, String name) {
		getContentPane().add(panel, name);
	}

	/**
	 * Chooses a new panel from the ones loaded in the window's {@link CardLayout} to show as actual view.
	 * 
	 * @param name the name of the panel from the {@link CardLayout} to show as actual view.
	 */
	public void changeLayoutToPanel(String name) {
		CardLayout cardL = (CardLayout) getContentPane().getLayout();
		cardL.show(getContentPane(), name);
	}

	/**
	 * Sets the default LaF (Look and Feel).
	 */
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(NIMBUS);
			Configurations.setCurrent_laf(1);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  Language.getDialog(24)+"Nimbus"+Language.getDialog(25), 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();			
		}
	}
	
	/**
	 * Sets a new LaF (Look and Feel) to the application's window.
	 *  
	 * @param link a valid link to a working Look and Feel.
	 */
	public void changeLookAndFeel(String link) {
		try {
			UIManager.setLookAndFeel(link);
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  Language.getDialog(24)+
					  Configurations.getLaf_names()[Configurations.getCurrent_laf()]+
					  Language.getDialog(25), 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
	}
	
}
