package Logic;

/**
 * This class is an auxiliary class to represent coordinates.
 * <p>
 * This class represents a grid in the environment through it's corresponding coordinates.
 *  
 * @author Eduard de Torres
 * @version v0.1  03/31/2014
 *
 */
public class Position {
	
	private int x;
	private int y;
	
	/**
	 * Class Constructor.
	 */
	public Position() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Class Constructor with parameters.
	 * <p>
	 * This class constructor receives all the necessary initialization parameters.
	 * 
	 * @param x x-coordinate of the Position.
	 * @param y y-coordinate of the Position.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x-coordinate of the Position.
	 * 
	 * @return the x-coordinate of the Position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the Position.
	 * 
	 * @param x a x-coordinate to set the Position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinate of the Position.
	 * 
	 * @return the y-coordinate of the Position.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the Position.
	 * 
	 * @param y an y-coordinate to set the Position.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
