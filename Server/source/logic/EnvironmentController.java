package logic;

import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.Random;

import misc.Coordinates;
import misc.Language;
import misc.Tactics;
import Communication.Server;
import actionListener.EnvironmentActionListener;
import graphicView.EnvironmentView;
import graphicView.TeamsPlayersView;

/**
 * This class is the controller of the game's Environment. It manages all the information about the environment.
 * <p>
 * This class manages all the information of the game's Environment. It controls the {@link EnvironmentView} and all the
 * information shown in it.
 * This class also contains and manages the information about the teams, the ball and the final score. 
 *  
 * @author Eduard de Torres
 * @version v0.1  04/30/2014
 *
 */
public class EnvironmentController {

	public static final int TEAM_SIZE = 4;
	public static final int GRID_SIZE_X = 42*2+2;		//42m (84)
	public static final int GRID_SIZE_Y = 25*2+2+1;		//25m (53)
	
	private EnvironmentView environment_view;
	private EnvironmentActionListener environment_listener;
	private TeamsPlayersView team_players_view;
	private Team team_A;
	private Team team_B;
	private Ball ball;
	private int result_a;
	private int result_b;
    private boolean goal;
    private boolean team_goal;
	
	
	/**
	 * Class constructor.
	 * 
	 * @param environment_view view of the game's phase.
	 * @param environment_listener ActionListener that controls the environment's view's events.
	 * @param team_players_view view of the team's configuration.
	 */
	public EnvironmentController(EnvironmentView environment_view, EnvironmentActionListener environment_listener, TeamsPlayersView team_players_view) {
		this.environment_view = environment_view;
		this.environment_listener = environment_listener;
		this.team_players_view = team_players_view;
		this.environment_view.getStop_button().addActionListener(environment_listener);
		
		team_A = new Team(true, this);
		team_B = new Team(false, this);
		team_A.setColor(0);
		team_B.setColor(1);
		
		result_a = 0;
		result_b = 0;
		setGoal(false);
		
		ball = new Ball(this);
	}
	
	/**
	 * Function that initialize all information about the players.
	 */
	private void initilizePlayers() {
		Random rand = new Random();
		Coordinates[] team_A_coords;
		Coordinates[] team_B_coords;
		if (/*rand.nextBoolean()*/true) {
			team_A_coords = Tactics.getKickOff1(true);
			team_B_coords = Tactics.getKickOff1(false);
		} else {
			team_A_coords = Tactics.getKickOff2(true);
			team_B_coords = Tactics.getKickOff2(false);
		}
		
		team_A.setGoalkeeper_x(team_A_coords[0].getX());
		team_A.setGoalkeeper_y(team_A_coords[0].getY());
		
		team_B.setGoalkeeper_x(team_B_coords[0].getX());
		team_B.setGoalkeeper_y(team_B_coords[0].getY());
		
		for (int i = 0; i < team_A.getNumber_of_players(); i++) {
			team_A.getPlayer_list().get(i).setPos_x(team_A_coords[i+1].getX());
			team_A.getPlayer_list().get(i).setPos_y(team_A_coords[i+1].getY());
		}
		for (int i = 0; i < team_B.getNumber_of_players(); i++) {
			team_B.getPlayer_list().get(i).setPos_x(team_B_coords[i+1].getX());
			team_B.getPlayer_list().get(i).setPos_y(team_B_coords[i+1].getY());
		}
		
		environment_view.repaintPlayers(team_A_coords, team_B_coords, ball);
	}
	
	/**
	 * Sets the {@link Coordinates} of all the selected team players to their default positions.
	 * 
	 * @param team_a if true, the team selected will be team A.
	 */
	private void restartPlayers(boolean team_a) {
		Coordinates[] team_A_coords;
		Coordinates[] team_B_coords;
		if (team_a) {
			team_A_coords = Tactics.getKickOff1(true);
			team_B_coords = Tactics.getKickOff1(false);
		} else {
			team_A_coords = Tactics.getKickOff2(true);
			team_B_coords = Tactics.getKickOff2(false);
		}
		
		if (team_A.isGoalkeper()) {
			team_A.setGoalkeeper_x(team_A_coords[0].getX());
			team_A.setGoalkeeper_y(team_A_coords[0].getY());
		}
		
		if (team_B.isGoalkeper()) {
			team_B.setGoalkeeper_x(team_B_coords[0].getX());
			team_B.setGoalkeeper_y(team_B_coords[0].getY());
		}
		
		for (int i = 0; i < team_A.getNumber_of_players(); i++) {
			if (team_A.getPlayer_list().get(i).getPos_x() != -1) {
				team_A.getPlayer_list().get(i).setPos_x(team_A_coords[i+1].getX());
				team_A.getPlayer_list().get(i).setPos_y(team_A_coords[i+1].getY());
			}
		}
		for (int i = 0; i < team_B.getNumber_of_players(); i++) {
			if (team_B.getPlayer_list().get(i).getPos_x() != -1) {
				team_B.getPlayer_list().get(i).setPos_x(team_B_coords[i+1].getX());
				team_B.getPlayer_list().get(i).setPos_y(team_B_coords[i+1].getY());
			}
		}
				
		repaintPlayers();
	}
	
	/**
	 * Prepares the information from the players and the ball to let the View repaint the images.
	 */
	public void repaintPlayers() {
		Coordinates[] team_A_coords = getTeamCoordenates(true);
		Coordinates[] team_B_coords = getTeamCoordenates(false);
		
		environment_view.repaintPlayers(team_A_coords, team_B_coords, ball);
	}
	
	/**
	 * Returns a {@link Coordinates}'s array with the information about the position of the selected's team's players in the
	 * environment's grid world.
	 * 
	 * @param team_a if true, the selected team will be team A.
	 * @return a {@link Coordinates}'s array with the positions of the selected's team's players.
	 */
	public Coordinates[] getTeamCoordenates(boolean team_a) {
		Coordinates[] team_coords = new Coordinates[TEAM_SIZE+1];
		Team team;
		
		if (team_a) {
			team = team_A;
		} else {
			team = team_B;
		}
		
		for (int i = 0; i < TEAM_SIZE+1; i++) {
			team_coords[i] = new Coordinates(-1, -1);
		}
		
		if (team.isGoalkeper()) {
			team_coords[0].setCoords(team.getGoalkeeper_x(), team.getGoalkeeper_y());
		}
		for (int i = 0; i < team.getNumber_of_players(); i++) {
			team_coords[i+1].setCoords(team.getPlayer_list().get(i).getPos_x(), team.getPlayer_list().get(i).getPos_y());
		}
		
		return team_coords;
		
	}
	
	/**
	 * Finds an empty socket slot to assign a new player connection to it.
	 * <p>
	 * Assigns the given socket and name to an empty socket space of the given team. The method returns the player's number
	 * of the team. If there weren't any empty socket space in the given team, the method returns -1; 
	 * 
	 * @param socket the socket to assign to the player.
	 * @param team_A the player's team. If true, team A will be selected.
	 * @param name the name of the player.
	 * @return the number of the player's team or -1 if the team was full.
	 */
	public int assignSocket(Socket socket, boolean team_A, String name) {
		Team team;
		if (team_A) {
			team = this.team_A;
		} else {
			team = this.team_B;
		}
		
		for (int i = 0; i < team.getPlayer_list().size(); i++) {
			if (team.getPlayer_list().get(i).getSocket() == null) {
				team.getPlayer_list().get(i).setSocket(socket);
				team.getPlayer_list().get(i).setName(name);
				changePlayerNameLabel(name, team_A, i);
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Disconnects a socket and cleans the data to represent a free socket slot.
	 * <p>
	 * Disconnects the {@link Socket} of the given player from the given team and sets if as an empty space.
	 * 
	 * @param team_A the team of the selected player. If true, team A will be selected.
	 * @param position the number of the player inside its team.
	 */
	public void disconnectSocket(boolean team_A, int position) {
		Team team;
		if (team_A) {
			team = this.team_A;
		} else {
			team = this.team_B;
		}
		team.getPlayer_list().get(position).setSocket(null);
		changePlayerNameLabel("- "+Language.getDialog(29)+" -", team_A, position);
	}
	
	/**
	 * Changes the text of a given player's label from the {@link EnvironmentView}.
	 * <p>
	 * Given a player from a given team, changes the text in the player's label and sets the given text to it.
	 * 
	 * @param name the text to set to the player's label.
	 * @param team_A the player's team. If true, the team is team A.
	 * @param position the number of the player inside its team.
	 */
	private void changePlayerNameLabel(String name, boolean team_A, int position) {
		switch(position) {
			case 0:
				if (team_A) {
					team_players_view.getPlayer_1_A_name_label().setText(name);
				} else {
					team_players_view.getPlayer_1_B_name_label().setText(name);
				}
				break;
			case 1:
				if (team_A) {
					team_players_view.getPlayer_2_A_name_label().setText(name);
				} else {
					team_players_view.getPlayer_2_B_name_label().setText(name);
				}
				break;
			case 2:
				if (team_A) {
					team_players_view.getPlayer_3_A_name_label().setText(name);
				} else {
					team_players_view.getPlayer_3_B_name_label().setText(name);
				}
				break;
			case 3:
				if (team_A) {
					team_players_view.getPlayer_4_A_name_label().setText(name);
				} else {
					team_players_view.getPlayer_4_B_name_label().setText(name);
				}
				break;
		}
	}
	
	/**
	 * Updates all the information contained in this class.
	 * 
	 * @param team_a_name the name of the team A.
	 * @param team_b_name the name of the team B.
	 * @param goalkeeper_a true if the team A has a goalkeeper.
	 * @param goalkeeper_b true if the team B has a goalkeeper.
	 * @param number_players_a number of "active" players (non dummies) in team A.
	 * @param number_players_b number of "active" players (non dummies) in team B.
	 * @param color_a color of the team A.
	 * @param color_b color of the team B.
	 */
	public void updateInfo(String team_a_name, String team_b_name, boolean goalkeeper_a, boolean goalkeeper_b, String number_players_a, String number_players_b, int color_a, int color_b) {
		environment_view.setResult_label("0 - 0");
		team_A.setName(team_a_name);
		environment_view.setTeam_A_label(team_a_name);
		team_B.setName(team_b_name);
		environment_view.setTeam_B_label(team_b_name);
		
		team_A.setGoalkeper(goalkeeper_a);
		if (!goalkeeper_a) {
			environment_view.getGoalkeeper_A_label().setVisible(false);
			team_A.setGoalkeeper_x(-1);
			team_A.setGoalkeeper_y(-1);
		}
		
		team_B.setGoalkeper(goalkeeper_b);
		if (!goalkeeper_b) {
			environment_view.getGoalkeeper_B_label().setVisible(false);
			team_B.setGoalkeeper_x(-1);
			team_B.setGoalkeeper_y(-1);
		}
		
		team_A.setNumber_of_players(Integer.parseInt(number_players_a));
		switch (number_players_a) {
			case "1":
				environment_view.getPlayer_2_A_label().setVisible(false);
				environment_view.getPlayer_3_A_label().setVisible(false);
				environment_view.getPlayer_4_A_label().setVisible(false);
				break;
				
			case "2":
				environment_view.getPlayer_3_A_label().setVisible(false);
				environment_view.getPlayer_4_A_label().setVisible(false);
				break;
			
			case "3":
				environment_view.getPlayer_4_A_label().setVisible(false);
				break;
			
			case "4":
				break;
		}
		
		team_B.setNumber_of_players(Integer.parseInt(number_players_b));
		switch (number_players_b) {
			case "1":
				environment_view.getPlayer_2_B_label().setVisible(false);
				environment_view.getPlayer_3_B_label().setVisible(false);
				environment_view.getPlayer_4_B_label().setVisible(false);
				break;
				
			case "2":						
				environment_view.getPlayer_3_B_label().setVisible(false);
				environment_view.getPlayer_4_B_label().setVisible(false);
				break;
			
			case "3":						
				environment_view.getPlayer_4_B_label().setVisible(false);
				break;
			
			case "4":
				break;
		}
		initilizePlayers();		
		team_A.setColor(color_a);
		team_B.setColor(color_b);
		environment_view.setTeam_A_color(color_a);
		environment_view.setTeam_B_color(color_b);
		environment_view.changeColors();
	}

	/**
	 * Returns if there is any "empty player" in the game.
	 * <p>
	 * Returns if there is any player of either team that is "empty" which means that there isn't any client controlling it's
	 * movements.  
	 * 
	 * @param num_players_A number of players in team A.
	 * @param num_players_B number of players in team B.
	 * @return true if there is any "empty player" in the game.
	 */
	public boolean checkTeams(int num_players_A, int num_players_B) {
		for (int i = 0; i < num_players_A; i++) {
			if (team_A.getPlayer_list().get(i).getSocket() == null) {
				return true;
			}
		}
		for (int i = 0; i < num_players_B; i++) {
			if (team_B.getPlayer_list().get(i).getSocket() == null) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Parses the information about the game's environment and creates a encoded string with all the information.
	 * <p>
	 * Creates an encoded string with the information of all the players in the game's environment and the ball.
	 * This encoded string is constructed as follows: 
	 *  - If any player is not configured (the number of players in a team is <5) it's coordinates are (-1, -1).
	 *  - Any player, goalkeeper or ball coordinate will be represented as a string like "x-coordinate y-coordinate" 
	 *  (e.g. "3 15").
	 *  - After any player, goalkeeper or ball coordinate the string will contain a "/".
	 *  - The goalkeepers, players and ball coordinates will be ordered as follows:
	 *    + GM  - Goalkeeper from my team
	 *    + GO  - Goalkeeper from the rival team
	 *    + P#M - Player number # from my team
	 *    + P#O - Player number # from the rival team
	 *    + B   - Ball
	 *    String = "GM/P1M/P2m/P3M/P4M/GO/P1O/P2O/P3O/P4O/B"
	 *     
	 * @param team_a team that is "my team". If true, my team will be team A.
	 * @return an encoded string with the information of all the players in the game's environment and the ball.
	 */
	public String prepareInfo(boolean team_a) {
		String s = new String();
		Team first;
		Team second;
		
		if (team_a) {
			first = team_A;
			second = team_B;
		} else {
			first = team_B;
			second = team_A;
		}
		if (first.isGoalkeper()) {
			s = s.concat(first.getGoalkeeper_x()+" "+first.getGoalkeeper_y()+"/");
		} else {
			s = s.concat("-1 -1/");
		}
		for (int i = 0; i < TEAM_SIZE; i++) {
			s = s.concat(first.getPlayer_list().get(i).getPos_x()+" "+first.getPlayer_list().get(i).getPos_y()+"/");
		}
		
		if (second.isGoalkeper()) {
			s = s.concat(second.getGoalkeeper_x()+" "+second.getGoalkeeper_y()+"/");
		} else {
			s = s.concat("-1 -1/");
		}
		for (int i = 0; i < TEAM_SIZE; i++) {
			s = s.concat(second.getPlayer_list().get(i).getPos_x()+" "+second.getPlayer_list().get(i).getPos_y()+"/");
		}
		
		s = s.concat(ball.getBall_x()+" "+ball.getBall_y()+"/");
		
		return s;
	}

	/**
	 * Returns an encoded string indicating who has the ball.
	 * <p>
	 * Returns an encoded string indicating who has the ball.
	 * The encoding method has the format <code>"T/N"</code>, where <code>T</code> can be {M, O} depending on which team has
	 * possession of the ball (M for my team, O for rival team), and <code>N</code> represents the player's number in 
	 * possession of the ball [-1..TEAM_SIZE] starting with -1 representing the goalkeeper. If noone has the ball, the 
	 * method will return "NONE". 
	 * 
	 * @param team_a team that is "my team". If true, my team will be team A.
	 * @return an encoded string indicating who has the ball.
	 */
	public String getWhoHaveBall(boolean team_a) {
		String s = environment_view.whoHaveBall(ball.getBall_x(), ball.getBall_y());
		
		if (s == null) return "NONE";
		else {
			String s1[] = s.split("/");
			if (team_a && s1[0].equals("A")) 	    return "M/"+s1[1];
			else if (team_a && !s1[0].equals("A"))  return "O/"+s1[1];
			else if (!team_a && s1[0].equals("A"))  return "O/"+s1[1];
			else if (!team_a && !s1[0].equals("A")) return "M/"+s1[1];
			return "NONE";
		}
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
		return environment_view.haveBall(player);
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
		return environment_view.checkCatchBall(player, ball);
	}
	
	/**
	 * Returns the {@link Coordinates} of a given player in a given team after moving towards a given {@link Coordinates}.  
	 * <p>
	 * Returns the {@link Coordinates} of a player after trying to move the player towards a given {@link Coordinates}. If
	 * the given team number is -1, the moving player will be the goalkeeper.
	 * The player will move from the actual bloc in the grid world to the contiguous bloc that is closer to the destination.
	 * 
	 * @param team_a the player's team. If true, the team is team A.
	 * @param number the players number inside its team.
	 * @param coords the destination of the movement.
	 * @return te {@link Coordinates} of the contiguous bloc that is closer to the destination.
	 */
	public Coordinates movePlayer(boolean team_a, int number, Coordinates coords) {
		Coordinates player;
		Coordinates result;
		if (team_a) {
			if (number == -1) {
				player = new Coordinates(team_A.getGoalkeeper_x(), team_A.getGoalkeeper_y());
			} else {
				player = new Coordinates(team_A.getPlayer_list().get(number).getPos_x(), team_A.getPlayer_list().get(number).getPos_y());
			}
		} else {
			if (number == -1) {
				player = new Coordinates(team_B.getGoalkeeper_x(), team_B.getGoalkeeper_y());
			} else {
				player = new Coordinates(team_B.getPlayer_list().get(number).getPos_x(), team_B.getPlayer_list().get(number).getPos_y());
			}
		}
		
		double a = coords.getX() - player.getX();
		double b = coords.getY() - player.getY();
		double module = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		
		result = new Coordinates(player.getX() + (int)(Math.round(a / module)), player.getY() + (int)(Math.round(b / module)));
		
		return result;
	}
	
	/**
	 * Returns whether a given players runs into any other player in the field.
	 * <p>
	 * Returns whether the chosen player of either team runs into one of their teammates, against any of its rivals or is outside the
	 * game's field's boundaries.
	 * 
	 * @param team_a the player's team. If true, the team is team A.
	 * @param number the players number inside its team.
	 * @param position {@link Coordinates} to check where the player would be.
	 * @return false if the player runs into any other player.
	 */
	public boolean checkValidPosition(boolean team_a, int number, Coordinates position) {
		Coordinates[] teamA = getTeamCoordenates(true);
		Coordinates[] teamB = getTeamCoordenates(false);
		
		//Check if the new position is inside the playable field.
		if (position.getX() <= 0 || position.getX() >= GRID_SIZE_X - 1 ||
			position.getY() <= 0 || position.getY() >= GRID_SIZE_Y - 1) {
			return false;
		}
		
		if (team_a) {
			teamA[number+1] = position;
		} else {
			teamB[number+1] = position;
		}
		
		return environment_view.checkValidPosition(team_a, number+1, teamA, teamB);
	}
	
	/**
	 * Configures all the Environment and server status to a scored goal status.
	 * <p>
	 * This method must be called when a goal is scored. It configures the environment and the server to identify a that a goal has 
	 * been scored.
	 * 
	 * @param team_a sets which team has scored the goal. True if team A has scored a goal.
	 */
	public void init_goal(boolean team_a) {		
		Server.setStart(false);
		setGoal(true);
		setTeam_goal(team_a);
		
		environment_view.setResult_label(result_a+" - "+result_b);
	}
	
	/**
	 * Configures all the Environment and server status to a ball outside the field's boundaries status.
	 * <p>
	 * This method must be called when the ball moves outside the game field's boundaries. It configures the environment and the server
	 * to identify that the ball has gone outside the field's boundaries.
	 */
	public void init_out() {		
		Server.setStart(false);
	}
	
	/**
	 * Manages a goal scoring event.
	 * <p>
	 * This method manages all the configurations and action that needs to be done when either team scores a goal.
	 * After scoring a goal, the application must stop the player's actions, reset the ball's and player's positions in the field
	 * and restart the game.
	 * 
	 * @param team_a sets which team has scored the goal. True if team A has scored a goal.
	 */
	public void manage_goal(boolean team_a) {
		try {
			Thread.sleep(Server.INIT_WAIT / 2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ball.initBall();
		restartPlayers(!team_a);
		
		try {
			Thread.sleep(Server.INIT_WAIT / 2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setGoal(false);
		Server.setStart(true);
		
	}

	/**
	 * Resizes the panel.
	 * <p>
	 * This method will be called when the main frame changes it's size.
	 * This method must implement all the necessary operations to adapt it's elements to the new size of the panel. 
	 */
	public void resizePanel() {
		environment_view.resizePanel();
	}
	
	/**
	 * Changes the language of the panel.
	 * <p>
	 * This method will be called when the main frame changes it's language.
	 * This method must implement all the necessary operations to change the language of all the dialogs contained in the panel.
	 */
	public void changeLanguage() {
		environment_view.changeLanguage();
	}

	/**
	 * Changes the color's mode of the panel between normal and color blind mode.
	 * <p>
	 * This method will be called when the main frame changes it's color mode.
	 * This method must implement all the necessary operations to adapt it's element's colors to the new color mode.
	 */
	public void changeColors() {
		environment_view.changeColors();
	}
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the environment's view that is being displayed during the game execution phase.
	 * @return the environment's view that is being displayed during the game execution phase.
	 */
	public EnvironmentView getEnvironment_view() {
		return environment_view;
	}

	/**
	 * Sets the environment's view that is being displayed during the game execution phase.
	 * @param environment_view the environment's view that is being displayed during the game execution phase.
	 */
	public void setEnvironment_view(EnvironmentView environment_view) {
		this.environment_view = environment_view;
	}

	/**
	 * Returns the information about the team A.
	 * @return the information about the team A.
	 */
	public Team getTeam_A() {
		return team_A;
	}

	/**
	 * Sets the information about the team A.
	 * @param team_A the information about the team A.
	 */
	public void setTeam_A(Team team_A) {
		this.team_A = team_A;
	}

	/**
	 * Returns the information about the team B.
	 * @return the information about the team B.
	 */
	public Team getTeam_B() {
		return team_B;
	}

	/**
	 * Sets the information about the team B.
	 * @param team_B the information about the team B.
	 */
	public void setTeam_B(Team team_B) {
		this.team_B = team_B;
	}

	/**
	 * Returns the {@link ActionListener} that controls all the events occurring during the game execution phase.
	 * @return the {@link ActionListener} that controls all the events occurring during the game execution phase.
	 */
	public EnvironmentActionListener getEnvironment_listener() {
		return environment_listener;
	}

	/**
	 * Sets the {@link ActionListener} that controls all the events occurring during the game execution phase.
	 * @param environment_listener the {@link ActionListener} that controls all the events occurring during the game execution phase.
	 */
	public void setEnvironment_listener(EnvironmentActionListener environment_listener) {
		this.environment_listener = environment_listener;
	}

	/**
	 * Returns the information about the ball.
	 * @return the information about the ball.
	 */
	public Ball getBall() {
		return ball;
	}

	/**
	 * Sets the information about the ball.
	 * @param ball the information about the ball.
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	/**
	 * Returns the number of goals scored by the team A.
	 * @return the number of goals scored by the team A.
	 */
	public int getResultA() {
		return result_a;
	}
	
	/**
	 * Returns the number of goals scored by the team B.
	 * @return the number of goals scored by the team B.
	 */
	public int getResultB() {
		return result_b;
	}
	
	/**
	 * Sets the number of goals scored by the team A.
	 * @param res the number of goals scored by the team A.
	 */
	public void setResultA(int res) {
		result_a = res;
	}
	
	/**
	 * the number of goals scored by the team B.
	 * @param res the number of goals scored by the team B.
	 */
	public void setResultB(int res) {
		result_b = res;
	}
	
	/**
	 * Returns the size of a player's image in pixels.
	 * @return the size of a player's image in pixels.
	 */
	public int getPlayerSize() {
		return environment_view.getPlayer_size();
	}
	
	/**
	 * Returns the x-size of a box in the game's grid world in the environment's view representation.
	 * @return the x-size of a box in the game's grid world in the environment's view representation.
	 */
	public double getBoxSizeX() {
		return environment_view.getBoxSizeX();
	}
	
	/**
	 * Returns the y-size of a box in the game's grid world in the environment's view representation.
	 * @return the y-size of a box in the game's grid world in the environment's view representation.
	 */
	public double getBoxSizeY() {
		return environment_view.getBoxSizeY();
	}

	/**
	 * Returns whether a goal from either team has been scored or not.
	 * @return true if a goal from either team has been scored.
	 */
	public boolean isGoal() {
		return goal;
	}

	/**
	 * Sets if a goal from either team has been scored.
	 * @param goal true if a goal from either team has been scored.
	 */
	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	/**
	 * Returns which team have scored the last goal.
	 * @return true if team A has scored the last goal.
	 */
	public boolean isTeam_goal() {
		return team_goal;
	}

	/**
	 * Sets which team have scored the last goal.
	 * @param team_goal if true, team A has scored the last goal.
	 */
	public void setTeam_goal(boolean team_goal) {
		this.team_goal = team_goal;
	}
}
