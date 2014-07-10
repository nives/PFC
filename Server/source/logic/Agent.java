package logic;

import java.net.Socket;

import misc.Coordinates;

/**
 * This class represents an Agent inside a multi-agent system.
 * <p>
 * This class represents a player from one of either team. It also represents an Agent in a multi-agent system as we can
 * represent a Team as a multi-agent system.
 * This class has all the important information about a player like the name, team number or position in the field.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/13/2014
 *
 */
public class Agent {
	
	private String name;
	private int number;
	private boolean team_A;
	private int pos_x;
	private int pos_y;
	private Socket socket;
	
	/**
	 * Class constructor.
	 * 
	 * @param name name of the player.
	 * @param number number of the player inside its team.
	 * @param team_A true if the player plays for the team A.
	 * @param socket the {@link Socket} that will be used to work with the client application that will manage this player.
	 * might be null if the client is disconnected.
	 */
	public Agent(String name, int number, boolean team_A, Socket socket) {
		this.name	= name;
		this.number = number;
		this.team_A = team_A;
		this.pos_x 	= -1;
		this.pos_y  = -1;
		this.socket = socket;
	}

	
	
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the name of the player. 
	 * @return the name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player.
	 * @param name the new name of the player. 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the number of the player inside its team.
	 * @return the number of the player inside its team.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number of the player inside its team.
	 * @param number the new number of the player inside its team. 
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Returns if the team of the player is the team A.
	 * @return true if the player's team is the team A.
	 */
	public boolean isTeam_A() {
		return team_A;
	}

	/**
	 * Sets if the player's team is the team A.
	 * @param team_A if true, the player's team will be the team A.
	 */
	public void setTeam_A(boolean team_A) {
		this.team_A = team_A;
	}

	/**
	 * Returns the x-coordinate of the player inside the grid world.
	 * @return the x-coordinate of the player inside the grid world.
	 */
	public int getPos_x() {
		return pos_x;
	}

	/**
	 * Sets the x-coordinate of the player inside the grid world.
	 * @param pos_x the x-coordinate of the player inside the grid world.
	 */
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	/**
	 * Returns the y-coordinate of the player inside the grid world.
	 * @return the y-coordinate of the player inside the grid world.
	 */
	public int getPos_y() {
		return pos_y;
	}

	/**
	 * Sets the y-coordinate of the player inside the grid world.
	 * @param pos_y the y-coordinate of the player inside the grid world.
	 */
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	/**
	 * Returns the connection {@link Socket} of the player.
	 * @return the connection {@link Socket} of the player.
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Sets the connection {@link Socket} of the player.
	 * @param socket the new connection {@link Socket} of the player.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Returns the {@link Coordinates} of the player inside the grid world.
	 * @return the {@link Coordinates} of the player inside the grid world.
	 */
	public Coordinates getCoordenates() {
		return new Coordinates(pos_x, pos_y);
	}
}
