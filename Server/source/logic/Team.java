package logic;

import java.util.ArrayList;

import misc.Language;

/**
 * This class stores all the information about a team.
 * <p>
 * This class contains the information about the team's name and team's color, the number a players the team has, the list of players
 * playing for the team and the goalkeeper information.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/02/2014
 *
 */
public class Team {
	
	private String 				name;
	private boolean				team_A;
	private int 				color;
	private int 				number_of_players;
	private boolean 			isGoalkeaper;
	private Goalkeeper			goalkeeper;		
	private ArrayList<Agent> 	player_list;
	
	/**
	 * Class constructor.
	 * 
	 * @param team_A if true, the team will be created as team A.
	 * @param env controller of the game's environment.
	 */
	public Team(boolean team_A, EnvironmentController env) {
		this.name = "Team";
		this.setTeam_A(team_A);
		this.color = 0;
		this.number_of_players = EnvironmentController.TEAM_SIZE;
		this.isGoalkeaper = true;
		this.goalkeeper = new Goalkeeper(env, team_A);
		this.player_list = new ArrayList<Agent>();
		for (int i = 0; i < EnvironmentController.TEAM_SIZE; i++) {
			this.player_list.add(new Agent("- "+ Language.getDialog(29)+ " -", i, team_A, null));
		}
	}
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the team's name.
	 * @return the team's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the team's name.
	 * @param name the team's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns whether the team is team A or B.
	 * @return true if the team is team A.
	 */
	public boolean isTeam_A() {
		return team_A;
	}

	/**
	 * Sets the team to be team A or B.
	 * @param team_A true if the team is to be team A.
	 */
	public void setTeam_A(boolean team_A) {
		this.team_A = team_A;
	}

	/**
	 * Returns the code number of the team's color.
	 * @return the code number of the team's color.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Sets the code number of the team's color.
	 * @param color the code number of the team's color.
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Returns the number of players the team has.
	 * @return the number of players the team has.
	 */
	public int getNumber_of_players() {
		return number_of_players;
	}

	/**
	 * Sets the number of players the team has.
	 * @param number_of_players the number of players the team has.
	 */
	public void setNumber_of_players(int number_of_players) {
		this.number_of_players = number_of_players;
	}

	/**
	 * Returns if the team has a goalkeeper.
	 * @return true if the team has a goalkeeper.
	 */
	public boolean isGoalkeper() {
		return isGoalkeaper;
	}

	/**
	 * Returns the team's goalkeeper's information.
	 * @return the team's goalkeeper's information.
	 */
	public Goalkeeper getGoalkeeper() {
		return goalkeeper;
	}
	
	/**
	 * Sets whether the team has a goalkeeper or not.
	 * @param goalkeper if true, the team will have a goalkeeper.
	 */
	public void setGoalkeper(boolean goalkeper) {
		this.isGoalkeaper = goalkeper;
	}

	/**
	 * Returns the team's goalkeeper's x-coordinate in the grid world.
	 * @return the team's goalkeeper's x-coordinate in the grid world.
	 */
	public int getGoalkeeper_x() {
		return goalkeeper.getPos_x();
	}

	/**
	 * Sets the team's goalkeeper's x-coordinate in the grid world.
	 * @param goalkeeper_x the team's goalkeeper's x-coordinate in the grid world.
	 */
	public void setGoalkeeper_x(int goalkeeper_x) {
		this.goalkeeper.setPos_x(goalkeeper_x);
	}

	/**
	 * Returns the team's goalkeeper's y-coordinate in the grid world.
	 * @return the team's goalkeeper's y-coordinate in the grid world.
	 */
	public int getGoalkeeper_y() {
		return goalkeeper.getPos_y();
	}

	/**
	 * Sets the team's goalkeeper's y-coordinate in the grid world.
	 * @param goalkeeper_y the team's goalkeeper's y-coordinate in the grid world.
	 */
	public void setGoalkeeper_y(int goalkeeper_y) {
		this.goalkeeper.setPos_y(goalkeeper_y);
	}

	/**
	 * Returns the team's player's list.
	 * @return the team's player's list.
	 */
	public ArrayList<Agent> getPlayer_list() {
		return player_list;
	}

	/**
	 * Sets the team's player's list.
	 * @param player_list
	 */
	public void setPlayer_list(ArrayList<Agent> player_list) {
		this.player_list = player_list;
	}

	/**
	 * Prints the team's information in terminal.
	 */
	public void print() {
		System.out.println("Name      : "+name);
		System.out.println("Team_A    : "+team_A);
		System.out.println("Color     : "+color);
		System.out.println("# players : "+number_of_players);
		System.out.println("Goalkeeper: "+isGoalkeaper);
		for (int i = 0; i < player_list.size(); i++) {
			System.out.println("Player: "+player_list.get(i).getName()+" "+player_list.get(i).getNumber()+" "+player_list.get(i).getSocket());
		}
		
		
	}

}
