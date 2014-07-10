package View;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final int SIZE_X = 300;
	private static final int SIZE_Y = 400;
	
	public MainFrame() {
		super();
		setTitle("Player Client");
		initialize();
	}
	
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
	
	public void addToCard(JPanel panel, String name) {
		getContentPane().add(panel, name);
	}

	public void changeLayoutToPanel(String name) {
		CardLayout cardL = (CardLayout) getContentPane().getLayout();
		cardL.show(getContentPane(), name);
	}

}
