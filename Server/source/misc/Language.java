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
 * This class is a miscellaneous class that is used to manage the different languages of the application.
 * <p>
 * This class manages all the languages and their dialogues. It loads the different available languages from the file 
 * *%LANGUAGES_FILE_NAME%*. Then it loads all the dialogues stored in the different language files.
 *   
 * @author Eduard de Torres
 * @version v0.1  03/26/2014
 *
 */
public class Language {
	//Name of the languages file.
	private static final String LANGUAGES_FILE_NAME = "languages.xml";
	//Number of existing languages.
	private static int number_of_languages;
	//Number of the current language.
	private static int current_language;
	//Names of the existing languages.
	private static ArrayList<String> languages_names;
	//All dialogs for each language. 
	private static ArrayList<String> [] languages_dialogs;
	private static ArrayList<String> [] colors_dialogs;
	private static ArrayList<String> [] textures_dialogs;
	
	
	/**
	 * Class Constructor.
	 */
	public Language() {
		current_language = 0;
		languages_names = new ArrayList<>();
		readLanguagesFile();
		readDialogsFiles();
	}
	
	/**
	 * Loads the data from the *%LANGUAGES_FILE_NAME%* file with all the languages names.
	 */
	private void readLanguagesFile() {
		File file = new File(Utils.getClassPath()+File.separator+"files"+File.separator+LANGUAGES_FILE_NAME);
		//File file = new File(Utils.getClassPath()[0]+File.separator+"files"+File.separator+LANGUAGES_FILE_NAME);
		System.out.println(Utils.getClassPath()+File.separator+"files"+File.separator+LANGUAGES_FILE_NAME);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(file);
		 
			NodeList nList = doc.getElementsByTagName("language");
			number_of_languages = nList.getLength();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 		Node nNode = nList.item(temp);		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index = Integer.parseInt(eElement.getAttribute("id"));
					languages_names.add(index, eElement.getTextContent());
				}
			}
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  "Unable to load "+LANGUAGES_FILE_NAME+".", 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	/**
	 * Reads the different dialogues from the different language's dialogues' files.
	 */
	@SuppressWarnings("unchecked")
	private void readDialogsFiles() {
		//String classpath = System.getProperty("java.class.path");
		//String[] classpathEntries = classpath.split(File.pathSeparator);
		languages_dialogs = (ArrayList<String>[])new ArrayList[number_of_languages];
		colors_dialogs 	  = (ArrayList<String>[])new ArrayList[number_of_languages];
		textures_dialogs  = (ArrayList<String>[])new ArrayList[number_of_languages];
		
		for (int i = 0; i < number_of_languages; i++) {
			languages_dialogs[i] = new ArrayList<>();
			colors_dialogs[i]    = new ArrayList<>();
			textures_dialogs[i]  = new ArrayList<>();
			File file = new File(Utils.getClassPath()+File.separator+"files"+File.separator+languages_names.get(i)+".xml");
			readDialog (i, file);
		}
	}
	
	/**
	 * Reads all the dialogs from a specific language.
	 * 
	 * @param index the number of the specific language.
	 * @param file the file containing the dialogues of the specific language.
	 */
	private void readDialog (int index, File file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(file);
		 
			NodeList nList = doc.getElementsByTagName("dialog");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 		Node nNode = nList.item(temp);		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index_aux = Integer.parseInt(eElement.getAttribute("id"));
					languages_dialogs[index].add(index_aux, eElement.getTextContent());
				}
			}
			
			nList = doc.getElementsByTagName("color");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 		Node nNode = nList.item(temp);		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index_aux = Integer.parseInt(eElement.getAttribute("id"));
					colors_dialogs[index].add(index_aux, eElement.getTextContent());
				}
			}
			
			nList = doc.getElementsByTagName("texture");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 		Node nNode = nList.item(temp);		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int index_aux = Integer.parseInt(eElement.getAttribute("id"));
					textures_dialogs[index].add(index_aux, eElement.getTextContent());
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					  "Unable to load "+languages_names.get(index)+".xml", 
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			//System.exit(0);
		}
	}
	
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the specified dialog from the current selected language.
	 * 
	 * @param index the number of the specified dialog.
	 * @return the specified dialog from the current selected language.
	 */
	public static String getDialog (int index) {
		return languages_dialogs[current_language].get(index);
	}
	
	/**
	 * Returns an array of available colors' names. 
	 * @return an array of available colors' names.
	 */
	public static String[] getColor () {
		return colors_dialogs[current_language].toArray(new String[colors_dialogs[current_language].size()]);
	}
	
	/**
	 * Returns an array of available textures' names. 
	 * @return an array of available textures' names.
	 */
	public static String[] getTexture () {
		return textures_dialogs[current_language].toArray(new String[textures_dialogs[current_language].size()]);
	}

	/**
	 * Returns the number of the current selected language.
	 * @return the number of the current selected language.
	 */
	public static int getCurrent_language() {
		return current_language;
	}

	/**
	 * Sets the current selected language.
	 * @param current_language the number of the language that will be the current selected language.
	 */
	public static void setCurrent_language(int current_language) {
		Language.current_language = current_language;
	}

	/**
	 * Returns the number of available languages.
	 * @return the number of available languages.
	 */
	public static int getNumber_of_languages() {
		return number_of_languages;
	}

	/**
	 * Returns a list of available languages' names.
	 * @return a list of available languages' names.
	 */
	public static ArrayList<String> getLanguages_names() {
		return languages_names;
	}

	/**
	 * Returns an array of lists of dialogues.
	 * <p>
	 * Returns an array of available languages' dialogues as a list. 
	 * @return an array of available languages' dialogues as a list.
	 */
	public static ArrayList<String>[] getLanguages_dialogs() {
		return languages_dialogs;
	}

	/**
	 * Returns an array of lists of colors' names.
	 * <p>
	 * Returns an array of available language's colors' names as a list. 
	 * @return an array of available language's colors' names as a list.
	 */
	public static ArrayList<String>[] getColors_dialogs() {
		return colors_dialogs;
	}

	/**
	 * Returns an array of lists of textures' names.
	 * <p>
	 * Returns an array of available language's textures' names as a list. 
	 * @return an array of available language's textures' names as a list.
	 */
	public static ArrayList<String>[] getTextures_dialogs() {
		return textures_dialogs;
	}
	
	/**
	 * Returns an array of available colors in English.
	 * @return an array of available colors in English.
	 */
	public static String[] getEnglishColors(){
		for (int i = 0; i < number_of_languages; i++) {
			if (languages_names.get(i).equals("English")) {
				return colors_dialogs[i].toArray(new String[colors_dialogs[i].size()]);
			}
		}
		return null;
	}
	
	/**
	 * Returns an array of available textures in English.
	 * @return an array of available textures in English.
	 */
	public static String[] getEnglishTextures() {
		for (int i = 0; i < number_of_languages; i++) {
			if (languages_names.get(i).equals("English")) {
				return textures_dialogs[i].toArray(new String[textures_dialogs[i].size()]);
			}
		}
		return null;
	}

	/**
	 * Returns the number of available colors.
	 * @return the number of available colors.
	 */
	public static int getNumberOfColors() {
		return colors_dialogs[0].size();
	}
	
	/**
	 * Returns the number of available textures.
	 * @return the number of available textures.
	 */
	public static int getNumberOfTextures() {
		return textures_dialogs[0].size();
	}
}
