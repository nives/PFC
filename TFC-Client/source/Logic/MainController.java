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

public class MainController {

	public static final String CONNECTION_VIEW = "connection_view";
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

	public static void disconnect() {
		connection_client.disconnect();
		main_frame.changeLayoutToPanel(CONNECTION_VIEW);
	}
	
	public static void changeTeam_selection_label(String message) {
		String text = execution_view.getTeam_selection_label().getText().concat(message);
		execution_view.getTeam_selection_label().setText(text);
	}
	
	public static void setConnectionMessage(String message) {
		connection_view.getMessage_label().setText(message);
	}

	public ConnectionActionListener getConnection_action_listener() {
		return connection_action_listener;
	}

	public void setConnection_action_listener(ConnectionActionListener connection_action_listener) {
		this.connection_action_listener = connection_action_listener;
	}

	public ExecutionActionListener getExecution_action_listener() {
		return execution_action_listener;
	}

	public void setExecution_action_listener(ExecutionActionListener execution_action_listener) {
		this.execution_action_listener = execution_action_listener;
	}

}
