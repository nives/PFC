package ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Logic.MainController;
import View.ExecutionView;

public class ExecutionActionListener implements ActionListener{

				
		public ExecutionActionListener (ExecutionView execution_view) {
			execution_view.getDisconnect_button().addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			switch (source.getName()) {
				case "disconnect_button":
					MainController.disconnect();
					MainController.setConnectionMessage("");
					break;
			}
			
		}

	}
