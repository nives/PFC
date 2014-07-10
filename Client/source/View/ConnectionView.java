package View;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

/**
 * This class is the view to set during the connection phase.
 * <p>
 * This class contains all the necessary {@link JComponent} for the client to manage the connection view.
 * It contains all the necessary {@link JLabel} to show useful information to the application's user, all the {@link JTextField}
 * necessary to insert information (Player name, server hostname and server port) and the necessary buttons ({@link JButton} 
 * and {@link JRadioButton}) to select the player's team and to connect to the server. 
 *  
 * @author Eduard de Torres
 * @version v0.1  03/25/2014
 *
 * @see JComponent
 * @see JPanel
 */
@SuppressWarnings("serial")
public class ConnectionView extends JPanel {
	
	private JLabel name_label;
	private JLabel server_name_label;
	private JLabel port_label ;
	private JLabel team_label;
	
	private JTextField name_field;
	private JTextField server_name_field;
	private JTextField port_field;
	
	private JRadioButton radio_button_a;
	private JRadioButton radio_button_b;

	private JButton connect_button;
	
	private ButtonGroup team_button_group;
	private JLabel message_label;
	
	/**
	 * Class Constructor.
	 */
	public ConnectionView() {
		setLayout(new MigLayout("", "[:5%:5%][:18%:18%][:5%:5%][:18%:18%][:8%:8%][:18%:18%][:5%:5%][:18%:18%][:5%:5%]", "[:10%:10%][:10%:10%][:10%:10%][:10%:10%][:10%:10%][:10%:10%][:5%:5%][:10%:10%][:15%:15%]"));
		
		name_label = new JLabel("Name");
		
		name_field = new JTextField();
		name_field.setText("Player name");
		name_field.setColumns(10);
		
		server_name_label = new JLabel("Server Name");
		
		port_label = new JLabel("Port");
		
		server_name_field = new JTextField();
		server_name_field.setText("localhost");
		server_name_field.setColumns(10);
		
		port_field = new JTextField();
		port_field.setText("23000");
		port_field.setColumns(10);
		
		team_label = new JLabel("Team");
		
		radio_button_a = new JRadioButton("Team A");
		radio_button_a.setSelected(true);
		
		radio_button_b = new JRadioButton("Team B");
		
		team_button_group = new ButtonGroup();
		team_button_group.add(radio_button_a);
		team_button_group.add(radio_button_b);
		
		connect_button = new JButton("Connect");
		connect_button.setName("connect_button");
		
		add(name_label, "cell 1 0 3 1,growx,aligny bottom");
		add(name_field, "cell 1 1 7 1,growx,aligny center");
		add(server_name_label, "cell 1 2 3 1,growx,aligny bottom");
		add(port_label, "cell 7 2,growx");
		add(server_name_field, "cell 1 3 5 1,growx,aligny center");
		add(port_field, "cell 7 3,growx");
		add(team_label, "cell 1 4 3 1,aligny bottom");
		add(radio_button_a, "cell 1 5 3 1,growx,aligny center");
		add(radio_button_b, "cell 5 5 3 1,growx,aligny center");
		add(connect_button, "cell 3 7 3 1,growx");
		
		message_label = new JLabel("");
		add(message_label, "cell 1 8 7 1,alignx center,aligny top");

	}

	
	/***********************
	 * Getters and Setters *
	 ***********************/
	
	/**
	 * Returns the player's name.
	 * 
	 * @return the player's name.
	 */
	public String getName() {
		return name_field.getText();
	}
	
	/**
	 * Returns the {@link JTextField} with the player's name.
	 *  
	 * @return the {@link JTextField} with the player's name.
	 */
	public JTextField getName_field() {
		return name_field;
	}
	
	/**
	 * Returns the {@link JTextField} with the server's hostname.
	 *  
	 * @return the {@link JTextField} with the server's hostname.
	 */
	public JTextField getServer_name_field() {
		return server_name_field;
	}
	
	/**
	 * Returns the {@link JTextField} with the server's connection port.
	 *  
	 * @return the {@link JTextField} with the server's connection port.
	 */
	public JTextField getPort_field() {
		return port_field;
	}

	/**
	 * Returns the {@link JButton} to connect with the server.
	 * 
	 * @return the {@link JButton} to connect with the server.
	 */
	public JButton getConnect_button() {
		return connect_button;
	}

	/**
	 * Returns the {@link ButtonGroup} that includes the {@link JRadioButton} to select the player's team.
	 * 
	 * @return the {@link ButtonGroup} that includes the {@link JRadioButton} to select the player's team.
	 */
	public ButtonGroup getTeam_button_group() {
		return team_button_group;
	}

	/**
	 * Returns the {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 * <p>
	 * Returns the {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 * These messages are useful to inform the application's user of different events as a connection error, a disconnection 
	 * from the server o the player's team and team's number.
	 * 
	 * @return a {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 */
	public JLabel getMessage_label() {
		return message_label;
	}

	/**
	 * Sets the {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 * <p>
	 * Sets the {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 * These messages are useful to inform the application's user of different events as a connection error, a disconnection 
	 * from the server o the player's team and team's number.
	 * 
	 * @param message_label a {@link JLabel} that contains the messages to show to the user to inform him of different events.
	 */
	public void setMessage_label(JLabel message_label) {
		this.message_label = message_label;
	}
	
}
