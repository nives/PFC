package Logic;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import ActionListener.ConnectionActionListener;
import ActionListener.ExecutionActionListener;
import Comunication.ConnectionClient;
import View.ConnectionView;
import View.ExecutionView;
import View.MainFrame;

/**
 * The class MainController is responsible for managing all the views of the client's application.
 * <p>
 * This class controls everything but the the buttons' events related to the views of the client's application.
 * It manages the connection and disconnection messages, the change of views and the closing message.
 * It also contains the controllers' instances that manage the button events and the {@link ConnectionClient} that manages 
 * the connection with the server.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/26/2014
 *
 */
public class MainController {

	/**
	 * name of the connection view to identify it.
	 */
	public static final String CONNECTION_VIEW = "connection_view";
	/**
	 * name of the execution view to identify it.
	 */
	public static final String EXECUTION_VIEW  = "execution_view";
	
	//Views
	private static MainFrame main_frame;
	private static ConnectionView connection_view;
	private static ExecutionView execution_view;
	
	//Action Listeners
	private ConnectionActionListener connection_action_listener;
	private ExecutionActionListener execution_action_listener;
	
	//Client
	private static ConnectionClient connection_client;
	
	/**
	 * Class Controller.
	 */
	public MainController() {
		connection_view = new ConnectionView();
		execution_view = new ExecutionView();
		
		//Client
		connection_client = new ConnectionClient(connection_view);
		
		//Views
		main_frame = new MainFrame();
		main_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					disconnect();
					System.exit(0);
				}
			}
		});
		
		
		main_frame.addToCard(connection_view, CONNECTION_VIEW);
		main_frame.addToCard(execution_view, EXECUTION_VIEW);
		
		//Action Listeners
		setConnection_action_listener(new ConnectionActionListener(main_frame, connection_view, execution_view, connection_client));
		setExecution_action_listener(new ExecutionActionListener(execution_view));
		
		//Starts the first panel.
		main_frame.changeLayoutToPanel(CONNECTION_VIEW);
		main_frame.setVisible(true);
	}

	/**
	 * Disconnects with the server and changes the view to show.
	 * <p>
	 * Calls the {@link ConnectionClient} disconnect method to disconnect with the server and calls the {@link Main} 
	 * changeLayoutToPanel method to change the view showed in the application. 
	 */
	public static void disconnect() {
		connection_client.disconnect();
		main_frame.changeLayoutToPanel(CONNECTION_VIEW);
	}
	
	/**
	 * Changes the message displayed in the application to inform the user which player is.
	 *   
	 * @param message a message to display in the application to inform the user which player is.
	 */
	public static void changeTeam_selection_label(String message) {
		String text = execution_view.getTeam_selection_label().getText().concat(message);
		execution_view.getTeam_selection_label().setText(text);
	}
	
	/**
	 * Sets a message in the connection view to inform the user.
	 * 
	 * @param message a message to inform the user.
	 */
	public static void setConnectionMessage(String message) {
		connection_view.getMessage_label().setText(message);
	}

	/**
	 * Returns the {@link ConnectionActionListener}.
	 * 
	 * @return a {@link ConnectionActionListener}.
	 */
	public ConnectionActionListener getConnection_action_listener() {
		return connection_action_listener;
	}

	/**
	 * Sets the {@link ConnectionActionListener}.
	 * @param connection_action_listener a {@link ConnectionActionListener}.
	 */
	public void setConnection_action_listener(ConnectionActionListener connection_action_listener) {
		this.connection_action_listener = connection_action_listener;
	}

	/**
	 * Returns the {@link ExecutionActionListener}.
	 * 
	 * @return a {@link ExecutionActionListener}.
	 */
	public ExecutionActionListener getExecution_action_listener() {
		return execution_action_listener;
	}

	/**
	 * Sets the {@link ExecutionActionListener}.
	 * 
	 * @param execution_action_listener a {@link ExecutionActionListener}.
	 */
	public void setExecution_action_listener(ExecutionActionListener execution_action_listener) {
		this.execution_action_listener = execution_action_listener;
	}

}
