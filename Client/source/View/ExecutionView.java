package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;

import net.miginfocom.swing.MigLayout;

/**
 * This class is the view to set during the execution phase.
 * <p>
 * This class contains all the necessary {@link JComponent} for the client to manage the execution view.
 * It contains all the necessary {@link JLabel} to show useful information to the application's user and the {@link JButton}
 * to let the user disconnect from the server, 
 *  
 * @author Eduard de Torres
 * @version v0.1  03/12/2014
 *
 */
@SuppressWarnings("serial")
public class ExecutionView extends JPanel {

	private JLabel name_label;
	private JLabel server_name_label;
	private JLabel port_label ;
	private JLabel team_label;
	
	private JLabel name_field;
	private JLabel server_name_field;
	private JLabel port_field;

	private JButton disconnect_button;
	
	private JLabel team_selection_label;
	
	/**
	 * Class Constructor.
	 */
	public ExecutionView() {
		setLayout(new MigLayout("", "[:5%:5%][:18%:18%][:5%:5%][:18%:18%][:8%:8%][:18%:18%][:5%:5%][:18%:18%][:5%:5%]", "[:10%:10%][:10%:10%][:10%:10%][:10%:10%][:10%:10%][:10%:10%][:5%:5%][:10%:10%][:15%:15%]"));
		
		name_label = new JLabel("Name");
		
		name_field = new JLabel();
		name_field.setText("Player name");
				
		server_name_label = new JLabel("Server Name");
		
		port_label = new JLabel("Port");
		
		server_name_field = new JLabel();
		server_name_field.setText("localhost");
				
		port_field = new JLabel();
		port_field.setText("23000");
				
		team_label = new JLabel("Team");
		team_selection_label = new JLabel("");
				
		disconnect_button = new JButton("Disconnect");
		disconnect_button.setName("disconnect_button");
		
		add(name_label, "cell 1 0 3 1,growx,aligny bottom");
		add(name_field, "cell 1 1 7 1,growx,aligny center");
		add(server_name_label, "cell 1 2 3 1,growx,aligny bottom");
		add(port_label, "cell 7 2,growx");
		add(server_name_field, "cell 1 3 5 1,growx,aligny center");
		add(port_field, "cell 7 3,growx");
		add(team_label, "cell 1 4 3 1,aligny bottom");
		
		
		add(team_selection_label, "cell 1 5 7 1");
		add(disconnect_button, "cell 3 7 3 1,growx");

	}

	/**
	 * Returns the disconnection {@link JButton}.
	 * 
	 * @return the disconnection {@link JButton}.
	 */
	public JButton getDisconnect_button() {
		return disconnect_button;
	}

	/**
	 * Returns the {@link JLabel} that contains the name of the player.
	 * 
	 * @return the {@link JLabel} that contains the name of the player.
	 */
	public JLabel getName_field() {
		return name_field;
	}

	/**
	 * Returns the {@link JLabel} that contains the server's hostname.
	 * 
	 * @return the {@link JLabel} that contains the server's hostname.
	 */
	public JLabel getServer_name_field() {
		return server_name_field;
	}

	/**
	 * Returns the {@link JLabel} that contains the server's port.
	 * 
	 * @return the {@link JLabel} that contains the server's port.
	 */
	public JLabel getPort_field() {
		return port_field;
	}

	/**
	 * Returns the {@link JLabel} that contains the information about the player's team.
	 * 
	 * @return the {@link JLabel} that contains the information about the player's team.
	 */
	public JLabel getTeam_selection_label() {
		return team_selection_label;
	}

}
