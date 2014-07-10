package ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Comunication.ConnectionClient;
import Logic.MainController;
import View.ConnectionView;
import View.ExecutionView;
import View.MainFrame;



public class ConnectionActionListener implements ActionListener{

	private MainFrame main_frame;
	private ConnectionView connection_view;
	private ExecutionView execution_view;
	private ConnectionClient connection_client;
	
	public ConnectionActionListener (MainFrame main_frame, 
									 ConnectionView connection_view, 
									 ExecutionView execution_view, 
									 ConnectionClient connection_client) {
		this.main_frame = main_frame;
		this.connection_view = connection_view;
		this.execution_view = execution_view;
		this.connection_client = connection_client;
		
		connection_view.getConnect_button().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		switch (source.getName()) {
			case "connect_button":
				execution_view.getName_field().setText(connection_view.getName_field().getText());
				execution_view.getServer_name_field().setText(connection_view.getServer_name_field().getText());
				execution_view.getPort_field().setText(connection_view.getPort_field().getText());

				for (Enumeration<AbstractButton> buttons = connection_view.getTeam_button_group().getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected()) {
		            	execution_view.getTeam_selection_label().setText(button.getText());
		            	connection_client.setTeamA(button.getText().equals("Team A"));
		            }
		        }
				
				connection_client.setHostName(connection_view.getServer_name_field().getText());
				connection_client.setPortNumber(Integer.parseInt(connection_view.getPort_field().getText()));
				

				main_frame.changeLayoutToPanel(MainController.EXECUTION_VIEW);
				try {
					connection_client.connect(main_frame);
				} catch (IOException e1) {
					main_frame.changeLayoutToPanel(MainController.CONNECTION_VIEW);
					JOptionPane.showMessageDialog(main_frame, "Unable to connect to "+connection_client.getHostName()+".");
				}
				
				break;
		}
		
	}

}
