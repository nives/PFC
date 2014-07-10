package misc;

/**
 * This class is an auxiliary class to represent coordinates.
 * <p>
 * This class represents a grid in the environment through it's corresponding coordinates.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/30/2014
 *
 */
public class Coordinates {
	private int x;
	private int y;

	/**
	 * Class constructor.
	 */
	public Coordinates() {
		x = -1;
		y = -1;
	}
	
	/**
	 * Class constructor with parameters.
	 * 
	 * @param x x-coordinate of the grid world bloc.
	 * @param y y-coordinate of the grid world bloc.
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor copy.
	 * 
	 * @param c coordinate to copy from. 
	 */
	public Coordinates(Coordinates c) {
		this.x = c.x;
		this.y = c.x;
	}

	/**
	 * Returns the x-coordinate of the grid world bloc.
	 * @return the x-coordinate of the grid world bloc.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the x-coordinate of the grid world bloc.
	 * @param x a x-coordinate of the grid world bloc.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinate of the grid world bloc.
	 * @return the y-coordinate of the grid world bloc.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the grid world bloc.
	 * 
	 * @param y a y-coordinate of the grid world bloc.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets both x-coordinate and y-coordinate of the grid world bloc.
	 * 
	 * @param x a x-coordinate of the grid world bloc.
	 * @param y a y-coordinate of the grid world bloc.
	 */
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new String(x+" "+y);
	}

}
