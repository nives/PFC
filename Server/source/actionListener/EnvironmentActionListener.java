package actionListener;

import graphicView.EnvironmentView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import misc.Language;

/**
 * This class EnvironmentActionListener is the event controller of the class {@link EnvironmentView}.
 * <p>
 * This class controls all the actions performed by the different JComponents added in the layout of the Environment view class.
 * It implements an {@link ActionListener} 
 *  
 * @author Eduard de Torres
 * @version v0.1  02/06/2014
 *
 * @see ActionEvent
 * @see ActionListener
 */
public class EnvironmentActionListener implements ActionListener{
	
	/**
	 * Class constructor.
	 */
	public EnvironmentActionListener() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Manages the action occurred after pressing the stop button in the environment view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent source = (JComponent) e.getSource();
		switch (((JComponent) source).getName()) {
			case "stop_button":
				int reply = JOptionPane.showConfirmDialog(null, Language.getDialog(27), Language.getDialog(3), JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				break;
		}
		
	}

}
