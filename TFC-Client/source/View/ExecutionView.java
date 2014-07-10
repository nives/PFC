package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

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
	 * Create the panel.
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

	public JButton getDisconnect_button() {
		return disconnect_button;
	}

	public JLabel getName_field() {
		return name_field;
	}

	public JLabel getServer_name_field() {
		return server_name_field;
	}

	public JLabel getPort_field() {
		return port_field;
	}

	public JLabel getTeam_selection_label() {
		return team_selection_label;
	}

		
}
