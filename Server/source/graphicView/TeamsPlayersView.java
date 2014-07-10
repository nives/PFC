package graphicView;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import logic.EnvironmentController;
import misc.Language;
import misc.Configurations;
import net.miginfocom.swing.MigLayout;

/**
 * This class implements the graphic view to show during the team's configuration's view.
 * <p>
 * This class contains all the graphic elements needed to show the team's configuration and the information resulting from it.
 * This class extends from {@link MyPanel} which is a generic, standard view.
 * This class contains a set of {@link JLabel}, {@link JTextField}, {@link JComboBox} and {@link JButton} to let the user
 * choose between the multiple configuration options for the teams and show him feedback information.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/18/2014
 *
 * @see MyPanel
 */
@SuppressWarnings("serial")
public class TeamsPlayersView extends MyPanel {
	
	//Buttons.
	private JButton back_button;
	private JButton configure_button;
	private JButton continue_button;
	
	//Text fields
	private JTextField team_A_text_field;
	private JTextField team_B_text_field;
	
	//Labels
	private JLabel team_color_A_label;
	private JLabel team_color_B_label;
	private JLabel goalkeeper_A_label;
	private JLabel goalkeeper_B_label;
	private JLabel number_of_players_A_label;
	private JLabel number_of_players_B_label;
	private JLabel player_1_A_label;
	private JLabel player_1_B_label;
	private JLabel player_2_A_label;
	private JLabel player_2_B_label;
	private JLabel player_3_A_label;
	private JLabel player_3_B_label;
	private JLabel player_4_A_label;
	private JLabel player_4_B_label;
	
	private JLabel player_1_A_name_label;
	private JLabel player_1_B_name_label;
	private JLabel player_2_A_name_label;
	private JLabel player_2_B_name_label;
	private JLabel player_3_A_name_label;
	private JLabel player_3_B_name_label;
	private JLabel player_4_A_name_label;
	private JLabel player_4_B_name_label;
	
	//Comboboxes.
	private JComboBox<String> team_color_A_combobox;
	private JComboBox<String> team_color_B_combobox;
	private JComboBox<String> number_of_players_A_combobox;
	private JComboBox<String> number_of_players_B_combobox;
	
	
	//Checkboxes.
	private JCheckBox goalkeeper_A_checkbox;
	private JCheckBox goalkeeper_B_checkbox;
	
	/**
	 * Class constructor.
	 */
	public TeamsPlayersView() {
		setLayout(new MigLayout("", "[:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%][:9%:9%]", "[:5%:5%][:10%:10%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:5%:5%][:25%:25%]"));
		
		//Buttons.
		back_button = new JButton(Language.getDialog(10));
		back_button.setName("back_button");
		configure_button = new JButton(Language.getDialog(2));
		configure_button.setName("configure_button");
		continue_button = new JButton(Language.getDialog(11));
		continue_button.setName("continue_button");
		
		//Labels
		team_color_A_label = new JLabel(Language.getDialog(14));
		team_color_B_label = new JLabel(Language.getDialog(14));
		
		goalkeeper_A_label = new JLabel(Language.getDialog(15));
		goalkeeper_B_label = new JLabel(Language.getDialog(15));
		
		number_of_players_A_label = new JLabel(Language.getDialog(16));
		number_of_players_B_label = new JLabel(Language.getDialog(16));
		
		player_1_A_label = new JLabel(Language.getDialog(17));
		player_1_B_label = new JLabel(Language.getDialog(17));
		player_2_A_label = new JLabel(Language.getDialog(18));
		player_2_B_label = new JLabel(Language.getDialog(18));
		player_3_A_label = new JLabel(Language.getDialog(19));
		player_3_B_label = new JLabel(Language.getDialog(19));
		player_4_A_label = new JLabel(Language.getDialog(20));
		player_4_B_label = new JLabel(Language.getDialog(20));
		
		player_1_A_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_1_B_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_2_A_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_2_B_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_3_A_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_3_B_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_4_A_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		player_4_B_name_label = new JLabel("- "+Language.getDialog(29)+" -");
		
		//Comboboxes.
		team_color_A_combobox = new JComboBox<String>();
		team_color_A_combobox.setModel(new DefaultComboBoxModel<String>(Language.getColor()));
		team_color_A_combobox.setSelectedIndex(0);
		team_color_B_combobox = new JComboBox<String>();
		team_color_B_combobox.setModel(new DefaultComboBoxModel<String>(Language.getColor()));
		team_color_B_combobox.setSelectedIndex(1);
		
		number_of_players_A_combobox = new JComboBox<String>();
		number_of_players_A_combobox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4"}));
		number_of_players_A_combobox.setName("number_of_players_A_combobox");
		number_of_players_A_combobox.setSelectedIndex(3);
		number_of_players_B_combobox = new JComboBox<String>();
		number_of_players_B_combobox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4"}));
		number_of_players_B_combobox.setName("number_of_players_B_combobox");
		number_of_players_B_combobox.setSelectedIndex(3);
		
		//CheckBoxes.
		goalkeeper_A_checkbox = new JCheckBox("");
		goalkeeper_A_checkbox.setSelected(true);
		goalkeeper_B_checkbox = new JCheckBox("");
		goalkeeper_B_checkbox.setSelected(true);
				
		
		add(back_button, "cell 0 0,alignx left");
		add(configure_button, "cell 5 0,alignx center");
		add(continue_button, "cell 10 0 2 1,alignx right");
		
		add(new JSeparator(), "cell 0 1 12 1, growx, wrap");
		
		//Labels
		team_A_text_field = new JTextField(Language.getDialog(12));
		add(team_A_text_field, "cell 1 2 4 1,growx,aligny center");
		team_B_text_field = new JTextField(Language.getDialog(13));
		add(team_B_text_field, "cell 6 2 4 1,growx,aligny center");
		add(team_color_A_label, "cell 1 4 2 1");
		add(team_color_B_label, "cell 6 4");
		add(goalkeeper_A_label, "cell 1 6");
		add(goalkeeper_B_label, "cell 6 6");
		add(number_of_players_A_label, "cell 1 7 2 1");
		add(number_of_players_B_label, "cell 6 7 2 1");
		add(player_1_A_label, "cell 1 9 2 1");
		add(player_1_B_label, "cell 6 9 2 1");
		add(player_2_A_label, "cell 1 10 2 1");
		add(player_2_B_label, "cell 6 10");
		add(player_3_A_label, "cell 1 11 2 1");
		add(player_3_B_label, "cell 6 11");
		add(player_4_A_label, "cell 1 12");
		add(player_4_B_label, "cell 6 12");
		add(team_color_A_combobox, "cell 3 4 2 1,growx");
		add(team_color_B_combobox, "cell 8 4 2 1,growx");
		add(number_of_players_A_combobox, "cell 3 7,growx");
		add(number_of_players_B_combobox, "cell 8 7,growx");
		add(player_1_A_name_label, "cell 3 9 2 1,growx");
		add(player_1_B_name_label, "cell 8 9 2 1,growx");
		add(player_2_A_name_label, "cell 3 10 2 1,growx");
		add(player_2_B_name_label, "cell 8 10 2 1,growx");
		add(player_3_A_name_label, "cell 3 11 2 1,growx");
		add(player_3_B_name_label, "cell 8 11 2 1,growx");
		add(player_4_A_name_label, "cell 3 12 2 1,growx");
		add(player_4_B_name_label, "cell 8 12 2 1,growx");
		add(goalkeeper_A_checkbox, "cell 3 6,alignx left");
		add(goalkeeper_B_checkbox, "cell 8 6");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resizePanel() {
		// TODO Auto-generated method stub
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Changes the language of the panel.
	 * <p>
	 * This method will be called when the main frame changes it's language.
	 * This method must implement all the necessary operations to change the language of all the dialogs contained in the panel.
	 *
	 * @param env the Environment controller with all the information contained in it.
	 */
	public void changeLanguage(EnvironmentController env) {
		int color_a = team_color_A_combobox.getSelectedIndex();
		int color_b = team_color_B_combobox.getSelectedIndex();
		
		back_button.setText(Language.getDialog(10));
		configure_button.setText(Language.getDialog(2));
		continue_button.setText(Language.getDialog(11));
		
		//Labels
		team_A_text_field.setText(Language.getDialog(12));
		team_B_text_field.setText(Language.getDialog(13));
		
		team_color_A_label.setText(Language.getDialog(14));
		team_color_B_label.setText(Language.getDialog(14));
		
		goalkeeper_A_label.setText(Language.getDialog(15));
		goalkeeper_B_label.setText(Language.getDialog(15));
		
		number_of_players_A_label.setText(Language.getDialog(16));
		number_of_players_B_label.setText(Language.getDialog(16));
		
		player_1_A_label.setText(Language.getDialog(17));
		player_1_B_label.setText(Language.getDialog(17));
		player_2_A_label.setText(Language.getDialog(18));
		player_2_B_label.setText(Language.getDialog(18));
		player_3_A_label.setText(Language.getDialog(19));
		player_3_B_label.setText(Language.getDialog(19));
		player_4_A_label.setText(Language.getDialog(20));
		player_4_B_label.setText(Language.getDialog(20));
		
		if (env.getTeam_A().getPlayer_list().get(0).getSocket() == null) {
			player_1_A_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_B().getPlayer_list().get(0).getSocket() == null) {
			player_1_B_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_A().getPlayer_list().get(1).getSocket() == null) {
			player_2_A_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_B().getPlayer_list().get(1).getSocket() == null) {
			player_2_B_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_A().getPlayer_list().get(2).getSocket() == null) {
			player_3_A_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_B().getPlayer_list().get(2).getSocket() == null) {
			player_3_B_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_A().getPlayer_list().get(3).getSocket() == null) {
			player_4_A_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		if (env.getTeam_B().getPlayer_list().get(3).getSocket() == null) {
			player_4_B_name_label.setText(("- "+Language.getDialog(29)+" -"));
		}
		
		//Comboboxes.
		team_color_A_combobox.setModel(new DefaultComboBoxModel<String>(new String[] {"Red", "Black", "Green", "Yellow", "Blue"}));
		team_color_B_combobox.setModel(new DefaultComboBoxModel<String>(new String[] {"Red", "Black", "Green", "Yellow", "Blue"}));
		
		team_color_A_combobox.setSelectedIndex(color_a);
		team_color_B_combobox.setSelectedIndex(color_b);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeColors() {
		if (!Configurations.isColor_blind()) {
			team_color_A_combobox.setModel(new DefaultComboBoxModel<String>(Language.getColor()));
			team_color_B_combobox.setModel(new DefaultComboBoxModel<String>(Language.getColor()));
			team_color_A_label.setText(Language.getDialog(14));
			team_color_B_label.setText(Language.getDialog(14));
		} else {
			team_color_A_combobox.setModel(new DefaultComboBoxModel<String>(Language.getTexture()));
			team_color_B_combobox.setModel(new DefaultComboBoxModel<String>(Language.getTexture()));
			team_color_A_label.setText(Language.getDialog(28));
			team_color_B_label.setText(Language.getDialog(28));
		}
	}


	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the Back button.
	 * @return the Back button.
	 */
	public JButton getBack_button() {
		return back_button;
	}

	/**
	 * Returns the Configure button.
	 * @return the Configure button.
	 */
	public JButton getConfigure_button() {
		return configure_button;
	}

	/**
	 * Returns the Continue button.
	 * @return the Continue button.
	 */
	public JButton getContinue_button() {
		return continue_button;
	}

	/**
	 * Returns the Team A's color's combobox. 
	 * @return the Team A's color's combobox.
	 */
	public JComboBox<String> getTeam_color_A_combobox() {
		return team_color_A_combobox;
	}

	/**
	 * Returns the Team B's color's combobox.
	 * @return the Team B's color's combobox.
	 */
	public JComboBox<String> getTeam_color_B_combobox() {
		return team_color_B_combobox;
	}

	/**
	 * Returns the Team A's number of players's combobox.
	 * @return the Team A's number of players's combobox.
	 */
	public JComboBox<String> getNumber_of_players_A_combobox() {
		return number_of_players_A_combobox;
	}

	/**
	 * Returns the Team B's number of players's combobox.
	 * @return the Team B's number of players's combobox.
	 */
	public JComboBox<String> getNumber_of_players_B_combobox() {
		return number_of_players_B_combobox;
	}

	/**
	 * Returns the Team A's first player's name's label.
	 * @return the Team A's first player's name's label.
	 */
	public JLabel getPlayer_1_A_name_label() {
		return player_1_A_name_label;
	}

	/**
	 * Returns the Team B's first player's name's label.
	 * @return the Team B's first player's name's label.
	 */
	public JLabel getPlayer_1_B_name_label() {
		return player_1_B_name_label;
	}

	/**
	 * Returns the Team A's second player's name's label.
	 * @return the Team A's second player's name's label.
	 */
	public JLabel getPlayer_2_A_name_label() {
		return player_2_A_name_label;
	}

	/**
	 * Returns the Team B's second player's name's label.
	 * @return the Team B's second player's name's label.
	 */
	public JLabel getPlayer_2_B_name_label() {
		return player_2_B_name_label;
	}

	/**
	 * Returns the Team A's third player's name's label.
	 * @return the Team A's third player's name's label.
	 */
	public JLabel getPlayer_3_A_name_label() {
		return player_3_A_name_label;
	}

	/**
	 * Returns the Team B's third player's name's label.
	 * @return the Team B's third player's name's label.
	 */
	public JLabel getPlayer_3_B_name_label() {
		return player_3_B_name_label;
	}

	/**
	 * Returns the Team A's forth player's name's label.
	 * @return the Team A's forth player's name's label.
	 */
	public JLabel getPlayer_4_A_name_label() {
		return player_4_A_name_label;
	}

	/**
	 * Returns the Team B's forth player's name's label.
	 * @return the Team B's forth player's name's label.
	 */
	public JLabel getPlayer_4_B_name_label() {
		return player_4_B_name_label;
	}

	/**
	 * Returns the Team A's goalkeeper checkbox.
	 * @return the Team A's goalkeeper checkbox.
	 */
	public JCheckBox getGoalkeeper_A_checkbox() {
		return goalkeeper_A_checkbox;
	}

	/**
	 * Returns the Team B's goalkeeper checkbox.
	 * @return the Team B's goalkeeper checkbox.
	 */
	public JCheckBox getGoalkeeper_B_checkbox() {
		return goalkeeper_B_checkbox;
	}

	/**
	 * Returns the Team A's name's label.
	 * @return the Team A's name's label.
	 */
	public JTextField getTeam_A_textfield() {
		return team_A_text_field;
	}
	
	/**
	 * Returns the Team B's name's label.
	 * @return the Team B's name's label.
	 */
	public JTextField getTeam_B_textfield() {
		return team_B_text_field;
	}

	/**
	 * Returns the Team A's color's label.
	 * @return the Team A's color's label.
	 */
	public JLabel getTeam_color_A_label() {
		return team_color_A_label;
	}

	/**
	 * Returns the Team B's color's label.
	 * @return the Team B's color's label.
	 */
	public JLabel getTeam_color_B_label() {
		return team_color_B_label;
	}

	/**
	 * Returns the Team A's first player's label.
	 * @return the Team A's first player's label.
	 */
	public JLabel getPlayer_1_A_label() {
		return player_1_A_label;
	}

	/**
	 * Returns the Team B's first player's label.
	 * @return the Team B's first player's label.
	 */
	public JLabel getPlayer_1_B_label() {
		return player_1_B_label;
	}

	/**
	 * Returns the Team A's second player's label.
	 * @return the Team A's second player's label.
	 */
	public JLabel getPlayer_2_A_label() {
		return player_2_A_label;
	}

	/**
	 * Returns the Team B's second player's label.
	 * @return the Team B's second player's label.
	 */
	public JLabel getPlayer_2_B_label() {
		return player_2_B_label;
	}

	/**
	 * Returns the Team A's third player's label.
	 * @return the Team A's third player's label.
	 */
	public JLabel getPlayer_3_A_label() {
		return player_3_A_label;
	}

	/**
	 * Returns the Team B's third player's label.
	 * @return the Team B's third player's label.
	 */
	public JLabel getPlayer_3_B_label() {
		return player_3_B_label;
	}

	/**
	 * Returns the Team A's fourth player's label.
	 * @return the Team A's fourth player's label.
	 */
	public JLabel getPlayer_4_A_label() {
		return player_4_A_label;
	}

	/**
	 * Returns the Team B's fourth player's label.
	 * @return the Team B's fourth player's label.
	 */
	public JLabel getPlayer_4_B_label() {
		return player_4_B_label;
	}

}
