package logic;

import graphicView.EnvironmentView;
import graphicView.LoadingView;
import graphicView.MainFrameView;
import graphicView.OptionsMenuDialog;
import graphicView.StartUpView;
import graphicView.TeamsPlayersView;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import misc.Configurations;
import misc.Language;
import misc.Resolution;
import misc.Tactics;
import Communication.Server;
import actionListener.EnvironmentActionListener;
import actionListener.OptionsMenuActionListener;
import actionListener.StartUpMenuActionListener;
import actionListener.TeamsPlayersActionListener;

/**
 * This class is responsible for managing all the views of the servers's application.
 * <p>
 * This class controls everything but the the buttons' events related to the views of the server's application.
 * It manages the change of views and the closing message.
 * It also contains the controllers' instances that manage the button events and the {@link Server} that manages the connection with 
 * the client.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/25/2014
 *
 */
public class MainFrame {
	
	//Views' names.
	public static final String LOADING_VIEW 		= "loading_view";
	public static final String START_UP_VIEW 		= "start_up_view";
	public static final String TEAMS_PLAYERS_VIEW 	= "teams_players_view";
	public static final String ENVIRONMENT_VIEW 	= "environment_view";
	
	//Main JFrame.
	private MainFrameView 				main_frame;
	
	//Panels.
	private LoadingView					loading_view;
	private StartUpView					start_up_view;
	private TeamsPlayersView			teams_players_view;
	private EnvironmentView				environment_view;
	
	//Listeners.
	private StartUpMenuActionListener 	start_up_listener;
	private TeamsPlayersActionListener	teams_players_listener;
	private EnvironmentActionListener	environment_listener;
	
	//Dialogs Frame, Panel and listener.
	private OptionsMenuDialog			option_menu_dialog;
	private OptionsMenuActionListener	option_menu_listener;
	
	//Environment Controller.
	private EnvironmentController		environment_controller;
	
	//Communications.
	private Server 						server;
	

	/**
	 * Class constructor.
	 */
	public MainFrame() {
    	System.out.println("Loading UI...");
		new Configurations();
		new Tactics();
		
		//Creates Frames, Panels and Dialogs.
		main_frame 			= new MainFrameView();
		loading_view		= new LoadingView();
		main_frame.addToCard(loading_view, LOADING_VIEW);
		main_frame.changeLayoutToPanel(LOADING_VIEW);
		main_frame.repaint();
		main_frame.setVisible(true);
		main_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, Language.getDialog(27), Language.getDialog(3), JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					
					System.exit(0);
				}
			}
		});
		
		start_up_view 			= new StartUpView(); 
		teams_players_view		= new TeamsPlayersView();
		environment_view		= new EnvironmentView();
		option_menu_dialog 		= new OptionsMenuDialog(main_frame, Language.getLanguages_names(), Resolution.getResolutions_list());
		
		environment_listener    = new EnvironmentActionListener();
		environment_controller  = new EnvironmentController(environment_view, environment_listener, teams_players_view);
		
		//Creates ActionListeners.
		start_up_listener 		= new StartUpMenuActionListener(start_up_view, option_menu_dialog, this);
		teams_players_listener	= new TeamsPlayersActionListener(teams_players_view, option_menu_dialog, this, environment_controller);
		option_menu_listener 	= new OptionsMenuActionListener(option_menu_dialog, this);
		
		//Creates communication thread.
		server = new Server(environment_controller);
		new Thread(server).start();
		
		//Adds the Panels to the main frame.
		main_frame.addToCard(start_up_view, START_UP_VIEW);
		main_frame.addToCard(teams_players_view, TEAMS_PLAYERS_VIEW);
		main_frame.addToCard(environment_view, ENVIRONMENT_VIEW);
		
		//Starts the first panel.
		main_frame.changeLayoutToPanel(START_UP_VIEW);
	}
	
	/**
	 * Updates all the displaying information about the server's application's window.
	 * <p>
	 * This method updates the size, language and color blind mode of all the contained panels and option menu dialog.
	 * It also changes the Look and Feel of the application's window. 
	 */
	public void update() {
		String[] links = Configurations.getLaf_links();
		
		main_frame.changeLookAndFeel(links[Configurations.getCurrent_laf()]);
		main_frame.changeLanguage();
		main_frame.changeColors();
		main_frame.resizeFrame();
		
		start_up_view.resizePanel();
		start_up_view.changeLanguage();
		start_up_view.changeColors();
		
		teams_players_view.resizePanel();
		teams_players_view.changeLanguage(environment_controller);
		teams_players_view.changeColors();
		
		environment_controller.resizePanel();
		environment_controller.changeLanguage();
		environment_controller.changeColors();
		
		option_menu_dialog.changeLookAndFeel(links[Configurations.getCurrent_laf()]);
		option_menu_dialog.changeLanguage();
		option_menu_dialog.changeColors();
	}
	
	/**
	 * Chooses a new panel from the ones loaded in the window's {@link CardLayout} to show as actual view.
	 * 
	 * @param layout_name the name of the panel from the {@link CardLayout} to show as actual view.
	 */
	public void changeLayout(String layout_name) {
		main_frame.changeLayoutToPanel(layout_name);
	}
	
	/**
	 * Sets a new LaF (Look and Feel) to the application's window.
	 *  
	 * @param name a valid name of a working Look and Feel.
	 */
	public void changeLookAndFeel(String name) {
		main_frame.changeLookAndFeel(name);
	}
}
