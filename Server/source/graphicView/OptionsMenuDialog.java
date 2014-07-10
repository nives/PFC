package graphicView;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import misc.Configurations;
import misc.Language;
import misc.Resolution;
import net.miginfocom.swing.MigLayout;

/**
 * This class implements the graphic view to show as option menu dialog. 
 * <p>
 * This class contains all the graphic elements needed to show when the option menu is called.
 * The different elements that contains are all related with the configuration options of the application therefore all the
 * {@link JLabel}, {@link JPanel}, {@link JComboBox} and {@link JCheckBox} are used to configure the language, window
 * resolution, look and feel and color blindness mode options.
 *  
 * @author Eduard de Torres
 * @version v0.1  02/20/2014
 *
 */
@SuppressWarnings("serial")
public class OptionsMenuDialog extends JDialog {
	private static final int DEFAULT_X = 300;
	private static final int DEFAULT_Y = 400;
	private static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	
	private JPanel top_panel;
	private JPanel middle_panel;
	private JPanel bottom_panel;
	
	private JLabel configure_label;
	private JLabel language_label;
	private JLabel window_resolution_label;
	private JLabel look_and_feel_label;
	private JLabel color_blindness_label;
	
	private JComboBox<Object> language_combobox;
	private JComboBox<Object> window_resolution_combobox;
	private JComboBox<Object> look_and_feel_comobox;
	
	private JCheckBox color_blindness_checkbox;
	
	private JButton apply_button;
	private JButton close_button;
	
	private ArrayList<String> language_list;
	private ArrayList<String> window_resolution_list;
	
	/**
	 * Class constructor.
	 */
	public OptionsMenuDialog() {
		super();
	}

	/**
	 * Class constructor with parameters.
	 * 
	 * @param aFrame the {@link JFrame} that calls the dialog.
	 * @param language_list a list of valid preloaded languages.
	 * @param window_resolution_list a list of valid preloaded window's resolutions.
	 */
	public OptionsMenuDialog(JFrame aFrame, ArrayList<String> language_list, ArrayList<String> window_resolution_list) {
		super(aFrame, true);
		setTitle(Language.getDialog(4));
		setSize(DEFAULT_X, DEFAULT_Y);
		setLookAndFeel();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		/****************************
		 * Top panel configuration. *
		 ****************************/
		top_panel = new JPanel();
		getContentPane().add(top_panel);
		top_panel.setLayout(new MigLayout("", "110[][grow]", "[]"));
		
		//Configure label.
		configure_label = new JLabel(Language.getDialog(2));
		top_panel.add(configure_label, "cell 0 0");
		
		/****************************
		 * Middle panel configuration. *
		 ****************************/
		middle_panel = new JPanel();
		getContentPane().add(middle_panel);
		middle_panel.setLayout(new MigLayout("", "[][][][][grow]", "[]10[]10[]10[]"));
		
		//Language label + Combobox.
		language_label = new JLabel(Language.getDialog(5));
		middle_panel.add(language_label, "cell 0 0,alignx left");
		
		language_combobox = new JComboBox<Object>();
		language_combobox.setName("language_combobox");
		this.language_list = language_list;
		language_combobox.setModel(new DefaultComboBoxModel<Object>(this.language_list.toArray()));
		language_combobox.setSelectedIndex(Language.getCurrent_language());
		middle_panel.add(language_combobox, "cell 3 0 2 1,growx");
		
		
		//Window resolution label + Combobox.
		window_resolution_label = new JLabel(Language.getDialog(6));
		middle_panel.add(window_resolution_label, "cell 0 1,alignx left");
		
		window_resolution_combobox = new JComboBox<Object>();
		window_resolution_combobox.setName("window_resolution_combobox");
		this.window_resolution_list = window_resolution_list;
		window_resolution_combobox.setModel(new DefaultComboBoxModel<Object>(this.window_resolution_list.toArray()));
		window_resolution_combobox.setSelectedIndex(Resolution.getCurrent_resolution());
		middle_panel.add(window_resolution_combobox, "cell 3 1 2 1,growx");
		
		//Look and feel label + Combobox.
		look_and_feel_label = new JLabel(Language.getDialog(23));
		middle_panel.add(look_and_feel_label, "cell 0 2,alignx left");
		
		look_and_feel_comobox = new JComboBox<Object>();
		look_and_feel_comobox.setName("look_and_feel_combobox");
		look_and_feel_comobox.setModel(new DefaultComboBoxModel<Object>(Configurations.getLaf_names()));
		look_and_feel_comobox.setSelectedIndex(Configurations.getCurrent_laf());
		middle_panel.add(look_and_feel_comobox, "cell 3 2 2 1,growx");
		
		//Color blindness label + CheckBox.
		color_blindness_label = new JLabel(Language.getDialog(7));
		middle_panel.add(color_blindness_label, "cell 0 3");
		
		color_blindness_checkbox = new JCheckBox("");
		color_blindness_checkbox.setName("color_blindness_checkbox");
		middle_panel.add(color_blindness_checkbox, "cell 3 3");
		
		/****************************
		 * Bottom panel configuration. *
		 ****************************/
		
		bottom_panel = new JPanel();
		getContentPane().add(bottom_panel);
		bottom_panel.setLayout(new MigLayout("", "60[]40[][grow]", "[center]"));
		
		//Apply button.
		apply_button = new JButton(Language.getDialog(8));
		apply_button.setName("Apply");
		bottom_panel.add(apply_button, "cell 0 0");
		
		close_button = new JButton(Language.getDialog(9));
		close_button.setName("Close");
		bottom_panel.add(close_button, "cell 1 0");
	}
	
	/**
	 * Changes the dialog's title and the language of the different elements to new selected language. 
	 */
	public void changeLanguage() {
		setTitle(Language.getDialog(4));
		configure_label.setText(Language.getDialog(2));
		language_label.setText(Language.getDialog(5));
		window_resolution_label.setText(Language.getDialog(6));
		look_and_feel_label.setText(Language.getDialog(23));
		color_blindness_label.setText(Language.getDialog(7));
		apply_button.setText(Language.getDialog(8));
		close_button.setText(Language.getDialog(9));
	}

	/**
	 * Changes all the necessary elements from the dialog's application to show them in color blind mode.
	 */
	public void changeColors() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Sets the dialog frame visible or invisible.
	 * 
	 * @param show if true the dialog will be visible. 
	 */
	public void showFrame(boolean show) {
		setVisible(show);
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
			
		}
	}
	
	/**
	 * Sets a new LaF (Look and Feel) to the dialog's window.
	 *  
	 * @param name a valid link to a working Look and Feel.
	 */
	public void changeLookAndFeel(String name) {
		try {
			UIManager.setLookAndFeel(name);
			SwingUtilities.updateComponentTreeUI(this);
			//this.pack();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the combobox with the different available languages.
	 * 
	 * @return a combobox with the different available languages.
	 */
	public JComboBox<Object> getLanguage_combobox() {
		return language_combobox;
	}

	/**
	 * Returns the combobox with the different available window's resolutions.
	 * 
	 * @return a combobox with the different available window's resolutions.
	 */
	public JComboBox<Object> getWindow_resolution_combobox() {
		return window_resolution_combobox;
	}
	
	/**
	 * Returns the combobox with the different available look and feels.
	 * 
	 * @return a combobox with the different available look and feel.
	 */
	public JComboBox<Object> getLook_and_feel_combobox() {
		return look_and_feel_comobox;
	}

	/**
	 * Returns the checkbox with the color blindness mode.
	 * 
	 * @return a checkbox with the color blindness mode.
	 */
	public JCheckBox getColor_blindness_checkbox() {
		return color_blindness_checkbox;
	}

	/**
	 * Returns the close button.
	 * 
	 * @return the close button.
	 */
	public JButton getClose_button() {
		return close_button;
	}
	
	/**
	 * Returns the apply button.
	 * 
	 * @return the apply button.
	 */
	public JButton getApply_button() {
		return apply_button;
	}
	
}
