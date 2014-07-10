package graphicView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import misc.Configurations;
import misc.Utils;

/**
 * This class implements the graphic view to show while the application is creating and configuring all the necessary elements. 
 * <p>
 * This class shows an animated loading image while the application is creation and configuring all the necessary elements.
 * The animated loading images is shown to give feedback to the user to make it more usable as otherwise the user might think
 * that the application doesn't work.
 * This class extends from {@link MyPanel} which is a generic, standard view. 
 * 
 * @author Eduard de Torres
 * @version v0.1  04/18/2014
 *
 * @see MyPanel
 */
@SuppressWarnings("serial")
public class LoadingView extends MyPanel {
	
	private static final String LOADING_GIF_NAME = "loading.gif";
	private static final double SCALE = 0.5;
	private ImageIcon loading_gif;
	private JLabel loading_label;
	
	/**
	 * Class constructor.
	 */
	public LoadingView() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		loadGif();

		add(loading_label, BorderLayout.CENTER);
	}

	/**
	 * Loads the loading image and rescales it.	
	 */
	private void loadGif() {
		loading_gif = new ImageIcon(Utils.getClassPath()+File.separator+"images"+File.separator+LOADING_GIF_NAME);
		//loading_gif = new ImageIcon(Utils.getClassPath()[0]+File.separator+"images"+File.separator+LOADING_GIF_NAME);
		loading_label = new JLabel();
		loading_label.setHorizontalAlignment(SwingConstants.CENTER);
		rescaleGif();
		Utils.loadImageToLabel(loading_label, loading_gif);
		loading_gif.setImageObserver(loading_label);
	}
	
	/**
	 * Rescales the loading image.
	 */
	private void rescaleGif() {
		//Set field size.
		int size_x = (int) (Configurations.getSize_x() * SCALE);
		int size_y = (int) (Configurations.getSize_y() * SCALE);
		
		Image image = loading_gif.getImage().getScaledInstance(size_x, size_y, Image.SCALE_FAST);
		loading_gif.setImage(image);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resizePanel() {
		rescaleGif();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeColors() {
		// TODO Auto-generated method stub
		
	}

}
