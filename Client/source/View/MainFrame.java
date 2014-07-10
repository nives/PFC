package View;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class is the main graphic class. This class extends from a {@link JFrame} and consequently generates the application's
 * window.
 * <p>
 * This class implements all the functionalities needed to configure the application's window. These configurations goes from
 * the application's window's size, to the look and feel loaded. It also contains a {@link CardLayout} to swap between the
 * different views. 
 *  
 * @author Eduard de Torres
 * @version v0.1  03/31/2014
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final int SIZE_X = 300;
	private static final int SIZE_Y = 400;
	
	/**
	 * Class Constructor.
	 */
	public MainFrame() {
		super();
		setTitle("Player Client");
		initialize();
	}
	
	/**
	 * Initializes all the configurable parameters.
	 * <p>
	 * Initializes parameters like size, LaF {@link LookAndFeel}, starting display position, ... 
	 */
	private void initialize() {
		setLookAndFeel();
		//No resizable.
		setResizable(false);
		//Size of the JFrame
		setSize(SIZE_X, SIZE_Y );
		//Centered Location in screen.
		setLocationRelativeTo(null);
		//Close operation.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		

		
		//JPanels and Layouts.
		getContentPane().setLayout(new CardLayout());
	}
	
	/**
	 * Sets the {@link LookAndFeel} to show the application's window with.  
	 */
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(NIMBUS);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  "Error loading the Nimbus Look and Feel.", 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();			
		}
	}
	
	/**
	 * Adds a {@link JPanel} to the {@link CardLayout}.
	 * <p>
	 * Adds a {@link JPanel} to the {@link CardLayout}. Requires a name to call the JPanel.
	 * 
	 * @param panel the {@link JPanel} to add to the {@link CardLayout}.
	 * @param name the name to give to the JPanel. 
	 */
	public void addToCard(JPanel panel, String name) {
		getContentPane().add(panel, name);
	}

	/**
	 * Changes the actual application's view.
	 * <p>
	 * Changes the actual application's view from a set of {@link JPanel} previously loaded to the {@link CardLayout}.
	 * The given name must be the name of an included JPanel.
	 * 
	 * @param name the name of a {@link JPanel} loaded to the {@link CardLayout}.
	 */
	public void changeLayoutToPanel(String name) {
		CardLayout cardL = (CardLayout) getContentPane().getLayout();
		cardL.show(getContentPane(), name);
	}

}
