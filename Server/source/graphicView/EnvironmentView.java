package graphicView;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import logic.Ball;
import logic.EnvironmentController;
import misc.Configurations;
import misc.Coordinates;
import misc.Language;
import misc.Utils;
import net.miginfocom.swing.MigLayout;

/**
 * This class implements the graphic view to show during the game.
 * <p>
 * This class contains all the graphic elements needed to show the running game and the information resulting from it.
 * This class extends from {@link MyPanel} which is a generic, standard view.
 * The different elements that contains can be classified in three categories: configuration elements, information elements
 * and field elements.
 * The configuration elements category includes elements such as images sizes in percent, images names or reference coordinates.
 * The information elements category includes elements such as {@link JLabel}s to show information like team names, player
 * names or the game score.
 * The field elements category includes elements such as player's and ball's images and player's and ball's coordinates.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/18/2014
 *
 * @see MyPanel
 */
@SuppressWarnings("serial")
public class EnvironmentView extends MyPanel {
	
	private static final String FIELD_IMAGE_NAME = "futsal_field.png";
	private static final double FIELD_SCALE = 0.72;
	private static final double FIELD_START_X = 0.05;
	private static final double FIELD_START_Y = 0.1428;
	private static final double FIELD_END_X = 0.95;
	private static final double FIELD_END_Y = 0.8571;
	
	private static 		 int 	PLAYER_IMAGE_SIZE = 161;
	private static		 int	TEAM_SIZE = EnvironmentController.TEAM_SIZE + 1;
	
	private static final String BALL_IMAGE_NAME = "ball.png";
	
	//Images.
	private BufferedImage 		field_image_raw;
	private ImageIcon 	  		field_image;
	private JLabel 		  		field_label;
	private int 				field_size_x;
	private int 				field_size_y;
	
	private BufferedImage[] 	players_images_raw;
	private BufferedImage[][] 	players_images;
	private int 				player_size;
	
	private BufferedImage[]		team_A;
	private BufferedImage[] 	team_B;
	private int 		  		team_A_color;
	private int 		  		team_B_color;
	
	private BufferedImage 		ball_image_raw;
	private BufferedImage  		ball_image;
	private int					ball_image_size;
	
	private Coordinates[]		team_A_coords;
	private Coordinates[]		team_B_coords;
	private Coordinates			ball_coords;
	
	double start_x;
	double start_y;
	double end_x;
	double end_y;
	double offset_x;
	double offset_y;
	
	//JComponents.
	private JButton stop_button;
	private JLabel team_A_label;
	private JLabel result_label;
	private JLabel team_B_label;
	private JLabel goalkeeper_A_label;
	private JLabel goalkeeper_B_label;
	private JLabel player_1_A_label;
	private JLabel player_1_B_label;
	private JLabel player_2_A_label;
	private JLabel player_2_B_label;
	private JLabel player_3_A_label;
	private JLabel player_3_B_label;
	private JLabel player_4_A_label;
	private JLabel player_4_B_label;
	
	
	/**
	 * Class Constructor.
	 */
	public EnvironmentView() {
		setLayout(new MigLayout("", "[:14%:14%][:14%:14%][:44%:44%][:14%:14%][:14%:14%]", "[:8%:8%][:5%:5%][:8%:8%][:5%:5%][:7%:7%][:7%:7%][:7%:7%][:7%:7%][:7%:7%][:37%:37%][:2%:2%]"));
		
		stop_button = new JButton(Language.getDialog(26));
		stop_button.setName("stop_button");
		
		team_A_label = new JLabel("Team A");
		team_A_label.setFont(new Font("Courier New", Font.BOLD, 20));
		result_label = new JLabel("0 - 0");
		result_label.setFont(new Font("Courier New", Font.BOLD, 20));
		team_B_label = new JLabel("Team B");
		team_B_label.setFont(new Font("Courier New", Font.BOLD, 20));
		
		goalkeeper_A_label = new JLabel("1 - Goalkeeper A");
		goalkeeper_B_label = new JLabel("1 - Goalkeeper B");
		player_1_A_label = new JLabel("2 - Player 1 A");
		player_1_B_label = new JLabel("2 - Player 1 B");
		player_2_A_label = new JLabel("3 - Player 2 A");
		player_2_B_label = new JLabel("3 - Player 2 B");
		player_3_A_label = new JLabel("4 - Player 3 A");
		player_3_B_label = new JLabel("4 - Player 3 B");
		player_4_A_label = new JLabel("5 - Player 4 A");
		player_4_B_label = new JLabel("5 - Player 4 B");
		
		loadField();
		loadPlayers();
		loadBall();
		initializeCoords();
		
		add(stop_button, "cell 4 0,alignx right,aligny center");
		add(new JSeparator(), "cell 0 1 5 1,growx");
		add(team_A_label, "cell 1 2,alignx center,aligny center");
		add(result_label, "cell 2 2,alignx center,aligny center");
		add(team_B_label, "cell 3 2,alignx center,aligny center");
		add(new JSeparator(), "cell 0 3 5 1,growx");
		
		add(field_label, "cell 1 4 3 6,alignx left,aligny top");
		add(goalkeeper_A_label, "cell 0 4,alignx left,aligny center");
		add(goalkeeper_B_label, "cell 4 4,alignx left,aligny center");
		add(player_1_A_label, "cell 0 5,alignx left,aligny center");
		add(player_1_B_label, "cell 4 5,alignx left,aligny center");
		add(player_2_A_label, "cell 0 6,alignx left,aligny center");
		add(player_2_B_label, "cell 4 6,alignx left,aligny center");
		add(player_3_A_label, "cell 0 7,alignx left,aligny center");
		add(player_3_B_label, "cell 4 7,alignx left,aligny center");
		add(player_4_A_label, "cell 0 8,alignx left,aligny center");
		add(player_4_B_label, "cell 4 8,alignx left,aligny center");
	}
	
	/**
	 * Loads the field's image and rescales it.	
	 */
	private void loadField() {
		field_image_raw = Utils.loadImage(FIELD_IMAGE_NAME);
		field_label = new JLabel();
		field_image = new ImageIcon();
		rescaleField(field_image_raw);
		field_label = Utils.loadImageToLabel(field_label, field_image);
	}

	/**
	 * Rescales the field image from the raw image.
	 * 
	 * @param field_raw raw image to use to get the rescaled image.
	 */
	private void rescaleField(BufferedImage field_raw) {
		//Set field size.
		field_size_x = (int) (Configurations.getSize_x() * FIELD_SCALE) ;
		field_size_y = (int) (Configurations.getSize_y() * FIELD_SCALE);
		
		Image image = field_raw.getScaledInstance(field_size_x, field_size_y, Image.SCALE_SMOOTH);
		field_image.setImage(image);
		field_label.setBounds(0, 0, field_size_x, field_size_y);
	}
	
	/**
	 * Loads the player's images and rescales them.
	 */
	private void loadPlayers(){	
		players_images_raw = new BufferedImage[Language.getNumberOfColors()+Language.getNumberOfTextures()];
		players_images	= new BufferedImage[Language.getNumberOfColors() + Language.getNumberOfTextures()][TEAM_SIZE];
		
		team_A = new BufferedImage[TEAM_SIZE];
		team_B = new BufferedImage[TEAM_SIZE];
		
		//Load all RAW players images (Colors).
		for (int index = 0; index < Language.getNumberOfColors(); index++) {
			players_images_raw[index] = Utils.loadImage("Players_"+Language.getEnglishColors()[index]+".png");	
		}
		
		//Load all RAW players images (Textures).
		int num_of_colors = Language.getNumberOfColors();
		for (int index = 0; index < Language.getNumberOfTextures(); index++) {
			players_images_raw[index + num_of_colors] = Utils.loadImage("Players_"+Language.getEnglishTextures()[index]+".png");
		}
		
		//Resize all players from all colors and textures.
		rescalePlayers();
	}
	
	/**
	 * Rescales the player's images from a raw set of images.
	 */
	private void rescalePlayers() {
		player_size = (int)(Configurations.getSize_x() * FIELD_SCALE / 25);
		for (int color = 0; color < Language.getNumberOfColors() + Language.getNumberOfTextures(); color++) {
			for (int player_number = 0; player_number < TEAM_SIZE; player_number++) {
				players_images[color][player_number] = ((BufferedImage) players_images_raw[color]).getSubimage(player_number * PLAYER_IMAGE_SIZE,
																											 0, 
																											 PLAYER_IMAGE_SIZE, 
																											 140);
				players_images[color][player_number] = Utils.resize(players_images[color][player_number], player_size, player_size);
			}
		}
	}
	
	/**
	 * Loads the ball's images and rescales it.
	 */
	private void loadBall(){	
		ball_image_raw = Utils.loadImage(BALL_IMAGE_NAME);
		rescaleBall();
	}
	
	/**
	 * Rescales the ball's images from a raw image.
	 */
	private void rescaleBall() {
		ball_image_size = (int)(Configurations.getSize_x() * FIELD_SCALE / 25 / 2);
		ball_image = Utils.resize(ball_image_raw, ball_image_size, ball_image_size);
	}
	
	/**
	 * Initializes all the configuration coordinates and the player's and ball's coordinates. 
	 */
	private void initializeCoords() {
		team_A_coords = new Coordinates[TEAM_SIZE];
		team_B_coords = new Coordinates[TEAM_SIZE];
		ball_coords   = new Coordinates(-1, -1);
		
		start_x 	= field_label.getX() + field_size_x * FIELD_START_X;
		start_y 	= field_label.getY() + field_size_y * FIELD_START_Y;
		end_x		= field_label.getX() + (field_size_x - player_size / 2) * FIELD_END_X;
		end_y		= field_label.getY() + (field_size_y - player_size / 2) * FIELD_END_Y;
		offset_x 	= (end_x - start_x) / (EnvironmentController.GRID_SIZE_X - 2);
		offset_y 	= (end_y - start_y) / (EnvironmentController.GRID_SIZE_Y - 2);
	}
	
	/**
	 * Repaints the player's and ball's images.
	 * <p>
	 * Repaints the player's and ball's images from a set of given coordinates.
	 * If any of the given coordinates equals (-1,-1) the image won't be painted.
	 * 
	 * @param teamA set of player's coordinates in a grid world representing the team A.
	 * @param teamB set of player's coordinates in a grid world representing the team B.
	 * @param ball coordinates in a grid world representing the ball's position.
	 */
	public void repaintPlayers(Coordinates[] teamA, Coordinates[] teamB, Ball ball) {
		team_A_coords = teamA;
		team_B_coords = teamB;
		ball_coords.setCoords(ball.getBall_x(), ball.getBall_y());
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		Toolkit.getDefaultToolkit().sync();
		
		start_x 	= field_label.getX() + field_size_x * FIELD_START_X;
		start_y 	= field_label.getY() + field_size_y * FIELD_START_Y;
		end_x		= field_label.getX() + (field_size_x - player_size / 2) * FIELD_END_X;
		end_y		= field_label.getY() + (field_size_y - player_size / 2) * FIELD_END_Y;
		offset_x 	= (end_x - start_x) / (EnvironmentController.GRID_SIZE_X - 2);
		offset_y 	= (end_y - start_y) / (EnvironmentController.GRID_SIZE_Y - 2);
		
		//Repaint the field image.
		field_label.repaint();	
		
		//Repaint all the "valid" players. Valid players are those that have positive coords.
		for (int i = 0; i < TEAM_SIZE; i++) {
			if (team_A_coords[i].getX() != -1) {
				g2d.drawImage(players_images[team_A_color][i], 
							  (int)((team_A_coords[i].getX() - 1) * offset_x + start_x), 
							  (int)((team_A_coords[i].getY() - 1) * offset_y + start_y), 
							  this);
			}
			if (team_B_coords[i].getX() != -1) {
				g2d.drawImage(players_images[team_B_color][i], 
							  (int)((team_B_coords[i].getX() - 1) * offset_x + start_x), 
							  (int)((team_B_coords[i].getY() - 1) * offset_y + start_y),  
							  this);
			}
		}
		
		//Repaint the ball.
		g2d.drawImage(ball_image, 
					 (int)(ball_coords.getX() * offset_x + start_x), 
					 (int)(ball_coords.getY() * offset_y + start_y), 
					 this);
		
		g.dispose();
	}
	
	/**
	 * Returns an encoded string indicating who has the ball.
	 * <p>
	 * Returns an encoded string indicating who has the ball.
	 * The encoding method has the format <code>"T/N"</code>, where <code>T</code> can be {A, B} depending on which team has
	 * possession of the ball, and <code>N</code> represents the player's number in possession of the ball [-1..TEAM_SIZE] 
	 * starting with -1 representing the goalkeeper. If noone has the ball, the method will return null. 
	 * 
	 * @param ball_x x-coordinate of the ball.
	 * @param ball_y y-coordinate of the ball.
	 * @return null if noone has the ball. An encoded string if any player has the ball.
	 */
	public String whoHaveBall(int ball_x, int ball_y) {
		ball_coords.setCoords(ball_x, ball_y);
		for (int i = 0; i < TEAM_SIZE; i++) {
			if (haveBall(team_A_coords[i])) {
				return "A/"+(i-1);
			}
		}
		
		for (int i = 0; i < TEAM_SIZE; i++) {
			if (haveBall(team_B_coords[i])) {
				return "B/"+(i-1);
			}
		}
		return null;
	}
	
	/**
	 * Returns if the given player has the ball.
	 * <p>
	 * Returns if a player with the given coordinates would be in possession of the ball.
	 * 
	 * @param player coordinates of the player to check.
	 * @return true if the given player would be in possession of the ball.
	 */
	public boolean haveBall(Coordinates player) {
		double a = Math.pow((ball_coords.getX() * offset_x + start_x + ball_image_size / 2) - ((player.getX() - 1) * offset_x + start_x + player_size / 2), 2.0);
		double b = Math.pow((ball_coords.getY() * offset_y + start_y + ball_image_size / 2) - ((player.getY() - 1) * offset_y + start_y + player_size / 2), 2.0);
		double dist = Math.sqrt(a+b);
		if (dist < player_size / 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns if a given player would be in possession of a given ball.
	 * <p>
	 * Returns if a player with the given coordinates would be in possession of a ball with the given coordinates.
	 * 
	 * @param player coordinates of the given player.
	 * @param ball coordinates of the given ball.
	 * @return true if the given player would be in possession of the given ball.
	 */
	public boolean checkCatchBall(Coordinates player, Coordinates ball) {
		double a = Math.pow((ball.getX() * offset_x + start_x + ball_image_size / 2) - ((player.getX() - 1) * offset_x + start_x + player_size / 2), 2.0);
		double b = Math.pow((ball.getY() * offset_y + start_y + ball_image_size / 2) - ((player.getY() - 1) * offset_y + start_y + player_size / 2), 2.0);
		double dist = Math.sqrt(a+b);
		if (dist < player_size / 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether a given players runs into any other player in the field.
	 * <p>
	 * Returns whether the chosen player of one team runs into one of their teammates or against any of its rivals.
	 * 
	 * @param team_a true if the player plays for team A.
	 * @param number number of the player in his team.
	 * @param teamA coordinates of all the players from team A.
	 * @param teamB coordinates of all the players from team B.
	 * @return false if the player runs into any other player.
	 */
	public boolean checkValidPosition(boolean team_a, int number, Coordinates[] teamA, Coordinates[] teamB) {
		Coordinates player;
		int error = 0;
		double a, b, dist;
		
		if (team_a) {
			player = teamA[number];
		} else {
			player = teamB[number];
		}
		for (int i = 0; i < TEAM_SIZE; i++) {
			a = Math.pow(((teamA[i].getX() - 1) * offset_x + start_x) - ((player.getX() - 1) * offset_x + start_x), 2.0);
			b = Math.pow(((teamA[i].getY() - 1) * offset_y + start_y) - ((player.getY() - 1) * offset_y + start_y), 2.0);
			dist = Math.sqrt(a+b);
			if (dist < player_size) {
				error++;
			}
			a = Math.pow(((teamB[i].getX() - 1) * offset_x + start_x) - ((player.getX() - 1) * offset_x + start_x), 2.0);
			b = Math.pow(((teamB[i].getY() - 1) * offset_y + start_y) - ((player.getY() - 1) * offset_y + start_y), 2.0);
			dist = Math.sqrt(a+b);				
			if (dist < player_size) {
				error++;
			}
		}
		
		if (error > 1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the x-offset with the JLabel containing the field.
	 * 
	 * @return the x-offset with the JLabel containing the field.
	 */
	public double getBoxSizeX() {
		return offset_x;
	}
	
	/**
	 * Returns the y-offset with the JLabel containing the field.
	 * 
	 * @return the y-offset with the JLabel containing the field.
	 */
	public double getBoxSizeY() {
		return offset_y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resizePanel() {
		rescaleField(field_image_raw);
		rescalePlayers();
		rescaleBall();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeLanguage() {
		setGoalkeeper_A_label("1 - " + Language.getDialog(15));
		setGoalkeeper_B_label("1 - " + Language.getDialog(15));
		stop_button.setText(Language.getDialog(26));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeColors() {
		for (int i = 0; i < TEAM_SIZE; i++) {
			team_A[i] = players_images[team_A_color][i];
			team_B[i] = players_images[team_B_color][i];
		}
	}
	
	
	/***********************
	 * Getters AND Setters *
	 ***********************/

	/**
	 * Returns the {@link JButton} that stops the game.
	 * @return the {@link JButton} that stops the game.
	 */
	public JButton getStop_button() {
		return stop_button;
	}

	/**
	 * Sets the {@link JButton} that stops the game.
	 * @param stop_button the {@link JButton} that stops the game.
	 */
	public void setStop_button(JButton stop_button) {
		this.stop_button = stop_button;
	}

	/**
	 * Sets the team A name's label.
	 * @param text name of the team A.
	 */
	public void setTeam_A_label(String text) {
		this.team_A_label.setText(text);
	}

	/**
	 * Sets the text in the rsult label.
	 */
	public void setResult_label(String text) {
		this.result_label.setText(text);
	}

	/**
	 * Sets the team B name's label.
	 * @param text name of the team B.
	 */
	public void setTeam_B_label(String text) {
		this.team_B_label.setText(text);
	}

	/**
	 * Sets the name of the goalkeeper for the team A.
	 * @param text the name of the goalkeeper for the team A.
	 */
	public void setGoalkeeper_A_label(String text) {
		this.goalkeeper_A_label.setText(text);
	}

	/**
	 * Sets the name of the goalkeeper for the team B.
	 * @param text the name of the goalkeeper for the team B.
	 */
	public void setGoalkeeper_B_label(String text) {
		this.goalkeeper_B_label.setText(text);
	}

	/**
	 * Sets the name of the first player for the team A.
	 * @param text the name of the first player for the team A.
	 */
	public void setPlayer_1_A_label(String text) {
		this.player_1_A_label.setText(text);
	}

	/**
	 * Sets the name of the first player for the team B.
	 * @param text the name of the first player for the team B.
	 */
	public void setPlayer_1_B_label(String text) {
		this.player_1_B_label.setText(text);
	}

	/**
	 * Sets the name of the second player for the team A.
	 * @param text the name of the second player for the team A.
	 */
	public void setPlayer_2_A_label(String text) {
		this.player_2_A_label.setText(text);
	}

	/**
	 * Sets the name of the second player for the team B.
	 * @param text the name of the second player for the team B.
	 */
	public void setPlayer_2_B_label(String text) {
		this.player_2_B_label.setText(text);
	}

	/**
	 * Sets the name of the third player for the team A.
	 * @param text the name of the third player for the team A.
	 */
	public void setPlayer_3_A_label(String text) {
		this.player_3_A_label.setText(text);
	}

	/**
	 * Sets the name of the third player for the team B.
	 * @param text the name of the third player for the team B.
	 */
	public void setPlayer_3_B_label(String text) {
		this.player_3_B_label.setText(text);
	}

	/**
	 * Sets the name of the fourth player for the team A.
	 * @param text the name of the fourth player for the team A.
	 */
	public void setPlayer_4_A_label(String text) {
		this.player_4_A_label.setText(text);
	}

	/**
	 * Sets the name of the fourth player for the team B.
	 * @param text the name of the fourth player for the team B.
	 */
	public void setPlayer_4_B_label(String text) {
		this.player_4_B_label.setText(text);
	}

	/**
	 * Returns the name of the team A.
	 * @return the name of the team A.
	 */
	public JLabel getTeam_A_label() {
		return team_A_label;
	}

	/**
	 * Returns the text of the result.
	 * @return the text of the result.
	 */
	public JLabel getResult_label() {
		return result_label;
	}

	/**
	 * Returns the name of the team B.
	 * @return the name of the team B.
	 */
	public JLabel getTeam_B_label() {
		return team_B_label;
	}

	/**
	 * Returns the name of the goalkeeper for the team A.
	 * @return the name of the goalkeeper for the team A.
	 */
	public JLabel getGoalkeeper_A_label() {
		return goalkeeper_A_label;
	}

	/**
	 * Returns the name of the goalkeeper for the team B.
	 * @return the name of the goalkeeper for the team B.
	 */
	public JLabel getGoalkeeper_B_label() {
		return goalkeeper_B_label;
	}

	/**
	 * Returns the name of the first player for the team A.
	 * @return the name of the first player for the team A.
	 */
	public JLabel getPlayer_1_A_label() {
		return player_1_A_label;
	}

	/**
	 * Returns the name of the first player for the team B.
	 * @return the name of the first player for the team B.
	 */
	public JLabel getPlayer_1_B_label() {
		return player_1_B_label;
	}

	/**
	 * Returns the name of the second player for the team A.
	 * @return the name of the second player for the team A.
	 */
	public JLabel getPlayer_2_A_label() {
		return player_2_A_label;
	}

	/**
	 * Returns the name of the second player for the team B.
	 * @return the name of the second player for the team B.
	 */
	public JLabel getPlayer_2_B_label() {
		return player_2_B_label;
	}

	/**
	 * Returns the name of the third player for the team A.
	 * @return the name of the third player for the team A.
	 */
	public JLabel getPlayer_3_A_label() {
		return player_3_A_label;
	}

	/**
	 * Returns the name of the third player for the team B.
	 * @return the name of the third player for the team B.
	 */
	public JLabel getPlayer_3_B_label() {
		return player_3_B_label;
	}

	/**
	 * Returns the name of the fourth player for the team A.
	 * @return the name of the fourth player for the team A.
	 */
	public JLabel getPlayer_4_A_label() {
		return player_4_A_label;
	}

	/**
	 * Returns the name of the fourth player for the team B.
	 * @return the name of the fourth player for the team B.
	 */
	public JLabel getPlayer_4_B_label() {
		return player_4_B_label;
	}
	
	/**
	 * Returns the color chosen for the team A.
	 * @return the color chosen for the team A.
	 */
	public int getTeam_A_color() {
		return team_A_color;
	}

	/**
	 * Sets the color chosen for the team A.
	 * @param team_A_color the color chosen for the team A.
	 */
	public void setTeam_A_color(int team_A_color) {
		this.team_A_color = team_A_color;
		if (Configurations.isColor_blind()) {
			this.team_A_color += Language.getNumberOfColors();
		}
	}
	
	/**
	 * Returns the color chosen for the team B.
	 * @return the color chosen for the team B.
	 */
	public int getTeam_B_color() {
		return team_B_color;
	}
	
	/**
	 * Sets the color chosen for the team B.
	 * @param team_B_color the color chosen for the team B.
	 */
	public void setTeam_B_color(int team_B_color) {
		this.team_B_color = team_B_color;
		if (Configurations.isColor_blind()) {
			this.team_B_color += Language.getNumberOfColors();
		}
	}
	
	/**
	 * Returns the size in pixels of the players' image.
	 * @return the size in pixels of the players' image.
	 */
	public int getPlayer_size() {
		return player_size;
	}
	
}
