package ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Logic.MainController;
import View.ExecutionView;

/**
 * The class ExecutionActionListener is the event controller of the class {@link ExecutionView}.
 * <p>
 * This class controls all the actions performed by the different JComponents added in the layout of the ExecutionView class.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/13/2014
 *
 */
public class ExecutionActionListener implements ActionListener{

		
	/**
	 * Class constructor.
	 * 
	 * @param execution_view view of the JPanel used to show the execution. Necessary to manage the disconnection button.
	 */
	public ExecutionActionListener (ExecutionView execution_view) {
		execution_view.getDisconnect_button().addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Manages the action occurred after pressing the disconnect button in the execution view.  
	 */
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
