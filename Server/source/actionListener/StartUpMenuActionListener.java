package actionListener;

import graphicView.OptionsMenuDialog;
import graphicView.StartUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import logic.MainFrame;
import misc.Language;

/**
 * This class StartUpMenuActionListener is the event controller of the class {@link StartUpView}.
 * <p>
 * This class controls all the actions performed by the different JComponents added in the layout of the Start up view class.
 * It implements an {@link ActionListener} 
 *  
 * @author Eduard de Torres
 * @version v0.1  02/06/2014
 *
 * @see ActionEvent
 * @see ActionListener
 */
public class StartUpMenuActionListener implements ActionListener{
	private MainFrame main_frame;
	private StartUpView start_up_view;
	private OptionsMenuDialog options_menu_dialog;

	/**
	 * Class constructor
	 * 
	 * @param start_up_view the view of the start up view. Necessary to get the controlled buttons.
	 * @param options_menu_dialog controller of the options menu dialog. Necessary to launch the dialog. 
	 * @param main_frame main applications frame. Necessary to change the different views.
	 */
	public StartUpMenuActionListener(StartUpView start_up_view, 
									 OptionsMenuDialog options_menu_dialog,  
									 MainFrame main_frame) {
		super();
		this.start_up_view = start_up_view;
		this.options_menu_dialog = options_menu_dialog;
		this.main_frame = main_frame;
		
		addToButtons();
	}

	/**
	 * Adds the different buttons of the view to this action listener.
	 */
	private void addToButtons() {
		start_up_view.getStart_button().addActionListener(this);
		start_up_view.getExit_button().addActionListener(this);
		start_up_view.getConfigure_button().addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Manages the action occurred after pressing the multiple buttons in the start up view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		switch (source.getName()) {
			case "Start":
				main_frame.changeLayout(MainFrame.TEAMS_PLAYERS_VIEW);
				break;
			case "Configure":
				options_menu_dialog.setVisible(true);
				break;
			case "Exit":
				int reply = JOptionPane.showConfirmDialog(null, Language.getDialog(27), Language.getDialog(3), JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				break;
		}
	}
	
}
