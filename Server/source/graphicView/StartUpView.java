package graphicView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import misc.Configurations;
import misc.Language;
import misc.Utils;
import net.miginfocom.swing.MigLayout;

/**
 * This class implements the graphic view to show as initial view.
 * <p>
 * This class contains all the graphic elements needed to show the initial view and the information resulting from it.
 * This class extends from {@link MyPanel} which is a generic, standard view.
 * The different elements that contains are: the application's logo and the view's buttons.
 *  *  
 * @author Eduard de Torres
 * @version v0.1  02/05/2014
 *
 * @see MyPanel
 */
@SuppressWarnings("serial")
public class StartUpView extends MyPanel{
	
	private static final String LOGO_NAME = "default_logo.png";
 
	private JPanel 			top_panel;
	private JPanel 			bottom_panel;
	
	private BufferedImage 	logo_raw;
	private ImageIcon 		logo;
	private JLabel 			logo_label;
	private int				logo_size_x;
	private int				logo_size_y;
	
	private JButton 		start_button;
	private JButton 		configure_button;
	private JButton 		exit_button;
	
	/**
	 * Class constructor.
	 */
	public StartUpView() {
		super();
		setLayout(new BorderLayout());
		
		//Configuring the North Panel to display the Logo.
		top_panel = new JPanel();
		top_panel.setLayout(new FlowLayout());
		
		//Configuring the South Panel to display the buttons.
		bottom_panel = new JPanel();
		bottom_panel.setLayout(new MigLayout("", "[grow,center]", "[center]10[]10[]"));
		
		//Adding the panels to the "content pane".
		add(top_panel, BorderLayout.NORTH);
		add(bottom_panel, BorderLayout.CENTER);
		
		//Loading the Logo
		loadLogo();
				
		//Loading the start_button.
		start_button = new JButton(Language.getDialog(1));
		start_button.setName("Start");
		//Loading the configure_button.
		configure_button = new JButton(Language.getDialog(2));
		configure_button.setName("Configure");
		//Loading the exit_button.
		exit_button = new JButton(Language.getDialog(3));
		exit_button.setName("Exit");
		
		top_panel.add(logo_label);
		bottom_panel.add(start_button, "cell 0 0, gaptop 30%");
		bottom_panel.add(configure_button, "cell 0 1");
		bottom_panel.add(exit_button, "cell 0 2");
		
		repaint();
	}
	
	/**
	 * Loads the lodo image and rescales it.
	 */
	private void loadLogo() {
		logo_raw = Utils.loadImage(LOGO_NAME);
		logo_label = new JLabel();
		logo = new ImageIcon();
		rescaleLogo();
		logo_label = Utils.loadImageToLabel(logo_label, logo);
		logo_label.setBounds(Configurations.getSize_x()/2 - logo.getIconWidth()/2, 
							 Configurations.getSize_y()/20, 
							 logo.getIconWidth(), 
							 logo.getIconHeight());
	}
	
	/**
	 * Rescales the logo image from a raw image.
	 */
	private void rescaleLogo() {
		//Set logo size.
		logo_size_x = logo_raw.getWidth()  * Configurations.getSize_x() / Configurations.getDefaultX() ;
		logo_size_y = logo_raw.getHeight() * Configurations.getSize_y() / Configurations.getDefaultY();
				
		Image image = logo_raw.getScaledInstance(logo_size_x, logo_size_y, Image.SCALE_FAST);
		logo.setImage(image);
		logo_label.setBounds(Configurations.getSize_x()/2 - logo.getIconWidth()/2, 
				 Configurations.getSize_y()/20, 
				 logo.getIconWidth(), 
				 logo.getIconHeight());
	}
	
	/**
	 * {@inheritDoc}	
	 */
	@Override
	public void resizePanel() {
		rescaleLogo();
	}
	
	/**
	 * {@inheritDoc}	
	 */
	public void changeLanguage() {
		start_button.setText(Language.getDialog(1));
		configure_button.setText(Language.getDialog(2));
		exit_button.setText(Language.getDialog(3));
	}

	/**
	 * {@inheritDoc}	
	 */
	public void changeColors() {
		// TODO Auto-generated method stub
		
	}
	
		
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the Start button.
	 * @return the Start button.
	 */
	public JButton getStart_button() {
		return start_button;
	}

	/**
	 * Returns the Configure button.
	 * @return the Configure button.
	 */
	public JButton getConfigure_button() {
		return configure_button;
	}

	/**
	 * Returns the Exit button.
	 * @return the Exit button.
	 */
	public JButton getExit_button() {
		return exit_button;
	}

}
