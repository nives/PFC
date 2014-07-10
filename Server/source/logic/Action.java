package logic;

import misc.Coordinates;

/**
 * The class Action manages the possible actions available for the player to carry out.
 * <p>
 * This class serves as a Factory Design Pattern for Actions. It's main objective is to give the client a 
 * way to create valid predefined actions.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/13/2014
 *
 */
public class Action {
	
	/**
	 * Keyword to set a move action.
	 */
	public  static final String MOVE  = "move";
	/**
	 * Keyword to set a pass action.
	 */
	public  static final String PASS  = "pass";
	/**
	 * Keyword to set a shoot action.
	 */
	public  static final String SHOOT = "shoot";
	
	private String action_name;
	private int x;
	private int y;

	/**
	 * Creates a new undefined Action.
	 * <p>
	 * Creating an Action with this method may generate a non valid action for the server. 
	 */
	public Action() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates a new Action. The client may specify the action type and the coordinates to do this action.
	 *  
	 * @param name type of action to create. This parameter may be filled with any of the action keywords (MOVE, PASS, SHOOT).
	 * @param x x-coordinate to do this action.
	 * @param y y-coordinate to do this action.
	 */
	public Action(String name, int x, int y) {
		this.action_name = name;
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new MOVE Action. 
	 * <p>
	 * Returns a new Movement Action. The client must specify the coordinates where he wants to go. 
	 * This coordinates will be converted into a unit vector movement to determine which neighboring grid will move. 
	 * So it doesn't matter if the coordinates chosen represent a neighboring grid or not.
	 * 
	 * @param x x-coordinate representing a grid.
	 * @param y y-coordinate representing a grid
	 * @return movement Action to the coordinates x, y.
	 */
	public static Action getMoveAction(int x, int y) {
		Action a = new Action(MOVE, x, y);
		return a;
	}
	
	/**
	 * Creates a new PASS Action. 
	 * <p>
	 * Returns a new Pass Action. The client must specify the coordinates where he wants to pass the ball. 
	 * This coordinates will represent the destination grid for the pass.
	 * 
	 * @param x x-coordinate representing a grid.
	 * @param y y-coordinate representing a grid
	 * @return pass Action to the coordinates x, y.
	 */
	public static Action getPassAction(int x, int y) {
		Action a = new Action(PASS, x, y);
		return a;
	}
	
	/**
	 * Creates a new SHOOT Action. 
	 * <p>
	 * Returns a new Shoot Action. The client must specify the coordinates where he wants to shoot the ball. 
	 * This coordinates will represent the destination grid for the shoot.
	 * 
	 * @param x x-coordinate representing a grid.
	 * @param y y-coordinate representing a grid
	 * @return shoot Action to the coordinates x, y.
	 */
	public static Action getShootAction(int x, int y) {
		Action a = new Action(SHOOT, x, y);
		return a;
	}
	
	
	/***********************
	 * Getters and Setters *
	 ***********************/

	/**
	 * Returns the type of the Action.
	 * 
	 * @return MOVE, PASS or SHOOT string.
	 */
	public String getAction_name() {
		return action_name;
	}

	/**
	 * Returns the x-coordinate of the Action.
	 * 
	 * @return x-coordinate of the Action.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate of the Action.
	 * 
	 * @return y-coordinate of the Action.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the type of the Action.
	 * 
	 * @param action_name a keyword to set a kind of action. 
	 */
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	/**
	 * Sets the x-coordinate of the Action.
	 * @param x the x-coordinate of the Action.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of the Action.
	 * 
	 * @param y the y-coordinate of the Action.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the {@link Coordinates} of an Action.
	 * @return the {@link Coordinates} of an Action.
	 */
	public Coordinates getCoordenates() {
		Coordinates coords = new Coordinates(getX(), getY());
		return coords;
	}
}
