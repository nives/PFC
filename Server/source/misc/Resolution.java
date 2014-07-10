package misc;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

/**
 * This class is a miscellaneous class that is used to manage the different screen resolution of the application.
 * <p>
 * This class manages all the screen resolution. It loads the different available screen resolution from the file 
 * *%RESOLUTIONS_FILE_NAME%*. 
 *  
 * @author Eduard de Torres
 * @version v0.1  02/05/2014
 *
 */
public class Resolution {
	
	//Name of the languages file.
	private static final String RESOLUTIONS_FILE_NAME = "resolutions.xml";
	//Number of existing languages.
	private static int number_of_resolutions;
	//Number of the current language.
	private static int current_resolution;
	//Names of the existing languages.
	private static ArrayList<String> resolutions_list;
	
	
	/**
	 * Class constructor.
	 */
	public Resolution() {
		current_resolution = 2;
		resolutions_list = new ArrayList<>();
		readResolutionsFile();
	}
	
	/**
	 * Load the data from the *%RESOLUTIONS_FILE_NAME%* file with all the screen resolution names.
	 */
	private void readResolutionsFile() {
		File file = new File(Utils.getClassPath()+File.separator+"files"+File.separator+RESOLUTIONS_FILE_NAME);
		//File file = new File(Utils.getClassPath()[0]+File.separator+"files"+File.separator+RESOLUTIONS_FILE_NAME);
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(file);
		 
			NodeList nList = doc.getElementsByTagName("resolution");
			number_of_resolutions = nList.getLength();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 		Node nNode = nList.item(temp);		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index = Integer.parseInt(eElement.getAttribute("id"));
					String res = new String(eElement.getElementsByTagName("width").item(0).getTextContent()
								  			+"x"
								  			+eElement.getElementsByTagName("height").item(0).getTextContent());
					resolutions_list.add(index, res);
				}
			}
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  "Unable to load "+RESOLUTIONS_FILE_NAME+".", 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		
	}

	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the current selected resolution.
	 * @return the current selected resolution.
	 */
	public static int getCurrent_resolution() {
		return current_resolution;
	}

	/**
	 * Sets the current selected screen resolution..
	 * @param current_resolution the number of the screen resolution that will be the current selected resolution.
	 */
	public static void setCurrent_resolution(int current_resolution) {
		Resolution.current_resolution = current_resolution;
	}

	/**
	 * Returns the number of available screen resolutions.
	 * @return the number of available screen resolutions.
	 */
	public static int getNumber_of_resolutions() {
		return number_of_resolutions;
	}

	/**
	 * Returns a list with all the available screen resolutions.
	 * @return a list with all the available screen resolutions.
	 */
	public static ArrayList<String> getResolutions_list() {
		return resolutions_list;
	}
		
}
