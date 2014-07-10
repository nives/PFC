package actionListener;

import graphicView.OptionsMenuDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import logic.MainFrame;
import misc.Configurations;
import misc.Language;

/**
 * The class OptionsMenuActionListener is the event controller of the class {@link OptionsMenuDialog}.
 * <p>
 * This class controls all the actions performed by the different JComponents added in the layout of the Option menu
 * dialog view class.
 * It implements an {@link ActionListener} 
 *  
 * @author Eduard de Torres
 * @version v0.1  02/06/2014
 *
 * @see ActionEvent
 * @see ActionListener
 */
public class OptionsMenuActionListener implements ActionListener{
	
	private OptionsMenuDialog view;
	private MainFrame main_frame;

	/**
	 * Class constructor.
	 * 
	 * @param view the view of the option menu dialog.
	 * @param main_frame main JFrame for the application. Necessary to be able to change the different layouts.
	 */
	public OptionsMenuActionListener(OptionsMenuDialog view, MainFrame main_frame) {
		super();
		this.view = view;
		this.main_frame = main_frame;
		
		addToButtons();
	}
	
	/**
	 * Adds the different buttons of the view to this action listener.
	 */
	private void addToButtons() {
		view.getApply_button().addActionListener(this);
		view.getClose_button().addActionListener(this);
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Manages the action occurred after pressing the multiple buttons in the option menu dialog's view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		switch (source.getName()) {
			case "Apply":
				Configurations.updateResolution(String.valueOf(view.getWindow_resolution_combobox().getSelectedItem()));
				Language.setCurrent_language(view.getLanguage_combobox().getSelectedIndex());
				Configurations.setCurrent_laf(view.getLook_and_feel_combobox().getSelectedIndex());
				Configurations.setColor_blind(view.getColor_blindness_checkbox().isSelected());
				main_frame.update();
				view.showFrame(false);
				break;
			case "Close":
				view.showFrame(false);
				break;
		}
		
	}
	

}
