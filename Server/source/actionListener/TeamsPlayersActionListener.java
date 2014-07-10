package actionListener;

import graphicView.OptionsMenuDialog;
import graphicView.TeamsPlayersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import logic.EnvironmentController;
import logic.MainFrame;
import misc.Language;
import Communication.Server;

/**
 * The class TeamsPlayersActionListener is the event controller of the class {@link TeamsPlayersView}.
 * <p>
 * This class controls all the actions performed by the different JComponents added in the layout of the team players
 * view class.
 * It implements an {@link ActionListener} 
 *  
 * @author Eduard de Torres
 * @version v0.1  04/02/2014
 *
 * @see ActionEvent
 * @see ActionListener
 */
public class TeamsPlayersActionListener implements ActionListener{
	private MainFrame 				main_frame;
	private EnvironmentController	environment_controller;
	private TeamsPlayersView 		teams_players_view;
	private OptionsMenuDialog 		options_menu_dialog;
	
	/**
	 * Class constructor.
	 * 
	 * @param team_players_view view of the team players view. Necessary to get the controlled buttons.
	 * @param options_menu_dialog controller of the options menu dialog. Necessary to launch the dialog. 
	 * @param main_frame main applications frame. Necessary to change the different views.
	 * @param environment_controller controller of the environment view. Necessary to configure the view's elements.
	 */
	public TeamsPlayersActionListener(TeamsPlayersView team_players_view, OptionsMenuDialog options_menu_dialog, MainFrame main_frame, EnvironmentController environment_controller) {
		super();
		this.teams_players_view 	= team_players_view;
		this.options_menu_dialog 	= options_menu_dialog;
		this.main_frame 			= main_frame;
		this.environment_controller = environment_controller;
		
		addToButtons();
	}

	/**
	 * Adds the different buttons of the view to this action listener.
	 */
	private void addToButtons() {
		teams_players_view.getBack_button().addActionListener(this);
		teams_players_view.getConfigure_button().addActionListener(this);
		teams_players_view.getContinue_button().addActionListener(this);
		teams_players_view.getNumber_of_players_A_combobox().addActionListener(this);
		teams_players_view.getNumber_of_players_B_combobox().addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Manages the action occurred after pressing the multiple buttons in the team player's view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent source = (JComponent) e.getSource();
		switch (((JComponent) source).getName()) {
			case "back_button":
				main_frame.changeLayout(MainFrame.START_UP_VIEW);
				break;
				
			case "configure_button":
				options_menu_dialog.setVisible(true);
				break;
				
			case "continue_button":
				boolean ok = true;
				int num_players_A = Integer.parseInt(teams_players_view.getNumber_of_players_A_combobox().getSelectedItem().toString());
				int num_players_B = Integer.parseInt(teams_players_view.getNumber_of_players_B_combobox().getSelectedItem().toString());
				if (environment_controller.checkTeams(num_players_A, num_players_B)) {
					int reply = JOptionPane.showConfirmDialog(null, Language.getDialog(31), Language.getDialog(3), JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.NO_OPTION) {
						ok = false;						
					}
				}
				
				if (ok) {
					environment_controller.updateInfo(teams_players_view.getTeam_A_textfield().getText(),
							  teams_players_view.getTeam_B_textfield().getText(),
							  teams_players_view.getGoalkeeper_A_checkbox().isSelected(),
							  teams_players_view.getGoalkeeper_B_checkbox().isSelected(),
							  teams_players_view.getNumber_of_players_A_combobox().getSelectedItem().toString(),
							  teams_players_view.getNumber_of_players_B_combobox().getSelectedItem().toString(),
							  teams_players_view.getTeam_color_A_combobox().getSelectedIndex(),
							  teams_players_view.getTeam_color_B_combobox().getSelectedIndex()
							  );
					
					environment_controller.getEnvironment_view().getPlayer_1_A_label().setText("2 - "+ teams_players_view.getPlayer_1_A_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_1_B_label().setText("2 - "+ teams_players_view.getPlayer_1_B_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_2_A_label().setText("3 - "+ teams_players_view.getPlayer_2_A_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_2_B_label().setText("3 - "+ teams_players_view.getPlayer_2_B_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_3_A_label().setText("4 - "+ teams_players_view.getPlayer_3_A_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_3_B_label().setText("4 - "+ teams_players_view.getPlayer_3_B_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_4_A_label().setText("5 - "+ teams_players_view.getPlayer_4_A_name_label().getText());
					environment_controller.getEnvironment_view().getPlayer_4_B_label().setText("5 - "+ teams_players_view.getPlayer_4_B_name_label().getText());
					
					environment_controller.repaintPlayers();
					main_frame.changeLayout(MainFrame.ENVIRONMENT_VIEW);
					
					Server.setStart(true);
					new Thread(environment_controller.getBall()).start();
					if (environment_controller.getTeam_A().isGoalkeper()) {
						new Thread(environment_controller.getTeam_A().getGoalkeeper()).start();
					}
					if (environment_controller.getTeam_B().isGoalkeper()) {
						new Thread(environment_controller.getTeam_B().getGoalkeeper()).start();
					}
				}
				break;
				
			case "number_of_players_A_combobox":
				setTeamAVisibility();
				break;
				
			case "number_of_players_B_combobox":
				setTeamBVisibility();
				break;
		}
	}
	
	private void setTeamAVisibility () {
		switch (teams_players_view.getNumber_of_players_A_combobox().getSelectedIndex()) {
			case 0:
				teams_players_view.getPlayer_1_A_name_label().setVisible(true);
				teams_players_view.getPlayer_1_A_label().setVisible(true);
				teams_players_view.getPlayer_2_A_name_label().setVisible(false);
				teams_players_view.getPlayer_2_A_label().setVisible(false);
				teams_players_view.getPlayer_3_A_name_label().setVisible(false);
				teams_players_view.getPlayer_3_A_label().setVisible(false);
				teams_players_view.getPlayer_4_A_name_label().setVisible(false);
				teams_players_view.getPlayer_4_A_label().setVisible(false);
				break;
			case 1:
				teams_players_view.getPlayer_1_A_name_label().setVisible(true);
				teams_players_view.getPlayer_1_A_label().setVisible(true);
				teams_players_view.getPlayer_2_A_name_label().setVisible(true);
				teams_players_view.getPlayer_2_A_label().setVisible(true);
				teams_players_view.getPlayer_3_A_name_label().setVisible(false);
				teams_players_view.getPlayer_3_A_label().setVisible(false);
				teams_players_view.getPlayer_4_A_name_label().setVisible(false);
				teams_players_view.getPlayer_4_A_label().setVisible(false);
				break;
			case 2:
				teams_players_view.getPlayer_1_A_name_label().setVisible(true);
				teams_players_view.getPlayer_1_A_label().setVisible(true);
				teams_players_view.getPlayer_2_A_name_label().setVisible(true);
				teams_players_view.getPlayer_2_A_label().setVisible(true);
				teams_players_view.getPlayer_3_A_name_label().setVisible(true);
				teams_players_view.getPlayer_3_A_label().setVisible(true);
				teams_players_view.getPlayer_4_A_name_label().setVisible(false);
				teams_players_view.getPlayer_4_A_label().setVisible(false);
				break;
			case 3:
				teams_players_view.getPlayer_1_A_name_label().setVisible(true);
				teams_players_view.getPlayer_1_A_label().setVisible(true);
				teams_players_view.getPlayer_2_A_name_label().setVisible(true);
				teams_players_view.getPlayer_2_A_label().setVisible(true);
				teams_players_view.getPlayer_3_A_name_label().setVisible(true);
				teams_players_view.getPlayer_3_A_label().setVisible(true);
				teams_players_view.getPlayer_4_A_name_label().setVisible(true);
				teams_players_view.getPlayer_4_A_label().setVisible(true);
				break;
		}
	}
	
	private void setTeamBVisibility () {
		switch (teams_players_view.getNumber_of_players_B_combobox().getSelectedIndex()) {
			case 0:
				teams_players_view.getPlayer_1_B_name_label().setVisible(true);
				teams_players_view.getPlayer_1_B_label().setVisible(true);
				teams_players_view.getPlayer_2_B_name_label().setVisible(false);
				teams_players_view.getPlayer_2_B_label().setVisible(false);
				teams_players_view.getPlayer_3_B_name_label().setVisible(false);
				teams_players_view.getPlayer_3_B_label().setVisible(false);
				teams_players_view.getPlayer_4_B_name_label().setVisible(false);
				teams_players_view.getPlayer_4_B_label().setVisible(false);
				break;
			case 1:
				teams_players_view.getPlayer_1_B_name_label().setVisible(true);
				teams_players_view.getPlayer_1_B_label().setVisible(true);
				teams_players_view.getPlayer_2_B_name_label().setVisible(true);
				teams_players_view.getPlayer_2_B_label().setVisible(true);
				teams_players_view.getPlayer_3_B_name_label().setVisible(false);
				teams_players_view.getPlayer_3_B_label().setVisible(false);
				teams_players_view.getPlayer_4_B_name_label().setVisible(false);
				teams_players_view.getPlayer_4_B_label().setVisible(false);
				break;
			case 2:
				teams_players_view.getPlayer_1_B_name_label().setVisible(true);
				teams_players_view.getPlayer_1_B_label().setVisible(true);
				teams_players_view.getPlayer_2_B_name_label().setVisible(true);
				teams_players_view.getPlayer_2_B_label().setVisible(true);
				teams_players_view.getPlayer_3_B_name_label().setVisible(true);
				teams_players_view.getPlayer_3_B_label().setVisible(true);
				teams_players_view.getPlayer_4_B_name_label().setVisible(false);
				teams_players_view.getPlayer_4_B_label().setVisible(false);
				break;
			case 3:
				teams_players_view.getPlayer_1_B_name_label().setVisible(true);
				teams_players_view.getPlayer_1_B_label().setVisible(true);
				teams_players_view.getPlayer_2_B_name_label().setVisible(true);
				teams_players_view.getPlayer_2_B_label().setVisible(true);
				teams_players_view.getPlayer_3_B_name_label().setVisible(true);
				teams_players_view.getPlayer_3_B_label().setVisible(true);
				teams_players_view.getPlayer_4_B_name_label().setVisible(true);
				teams_players_view.getPlayer_4_B_label().setVisible(true);
				break;
		}
	}
	
}