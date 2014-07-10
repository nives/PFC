package View;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

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
	 * Create the panel.
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

	public String getName() {
		return name_field.getText();
	}
	
	/***********************
	 * Getters and Setters *
	 ***********************/
	
	public JTextField getName_field() {
		return name_field;
	}
	
	public JTextField getServer_name_field() {
		return server_name_field;
	}
	
	public JTextField getPort_field() {
		return port_field;
	}

	public JButton getConnect_button() {
		return connect_button;
	}

	public ButtonGroup getTeam_button_group() {
		return team_button_group;
	}

	public JLabel getMessage_label() {
		return message_label;
	}

	public void setMessage_label(JLabel message_label) {
		this.message_label = message_label;
	}
	

}
