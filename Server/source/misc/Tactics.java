package misc;

import logic.EnvironmentController;

/**
 * This class is a miscellaneous class that is used to manage different player's positions in the game field.
 * <p>
 * This class stores a set of preset tactics in form of player's positions. These tactics are useful to set predefined 
 * team structures for different situations.
 *   
 * @author Eduard de Torres
 * @version v0.1  04/30/2014
 *
 */
public class Tactics {
	private static final int NUM_TACTICS = 2;
	private static final int KICK_OFF_1 = 0;
	private static final int KICK_OFF_2 = 1;
	
	private int grid_x;
	private int grid_y;
	
	private static Coordinates[][][] tactics;
	
	/**
	 * Class constructor.
	 */
	public Tactics() {
		grid_x = EnvironmentController.GRID_SIZE_X;
		grid_y = EnvironmentController.GRID_SIZE_Y;
		
		tactics = new Coordinates[NUM_TACTICS][2][5];
		for (int i = 0; i < tactics.length; i++) {
			for (int j = 0; j < tactics[i].length; j++) {
				for (int k = 0; k < tactics[i][j].length; k++) {
					tactics[i][j][k] = new Coordinates();
				}
			}
			
		}
		
		setKickOff1();
		setKickOff2();
	}

	/**
	 * Sets the preset kickoff tactics one.
	 */
	private void setKickOff1() {
		tactics[KICK_OFF_1][0][0].setCoords(1		  , grid_y / 2);
		tactics[KICK_OFF_1][0][1].setCoords(2 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_1][0][2].setCoords(2 * grid_x / 8, 2 * grid_y / 3);
		tactics[KICK_OFF_1][0][3].setCoords(4 * grid_x / 8 - 1, 2 * grid_y / 4 - 3);
		tactics[KICK_OFF_1][0][4].setCoords(4 * grid_x / 8 - 1, 2 * grid_y / 4 + 3);
		
		tactics[KICK_OFF_1][1][0].setCoords(grid_x - 2    , grid_y / 2);
		tactics[KICK_OFF_1][1][1].setCoords(7 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_1][1][2].setCoords(7 * grid_x / 8, 2 * grid_y / 3);
		tactics[KICK_OFF_1][1][3].setCoords(5 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_1][1][4].setCoords(5 * grid_x / 8, 2 * grid_y / 3);
	}
	
	/**
	 * Sets the preset kickoff tactics two.
	 */
	private void setKickOff2() {
		tactics[KICK_OFF_2][0][0].setCoords(1		      , grid_y / 2);
		tactics[KICK_OFF_2][0][1].setCoords(1 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_2][0][2].setCoords(1 * grid_x / 8, 2 * grid_y / 3);
		tactics[KICK_OFF_2][0][3].setCoords(3 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_2][0][4].setCoords(3 * grid_x / 8, 2 * grid_y / 3);
		
		tactics[KICK_OFF_2][1][0].setCoords(grid_x - 2    , grid_y / 2);
		tactics[KICK_OFF_2][1][1].setCoords(6 * grid_x / 8, 1 * grid_y / 3);
		tactics[KICK_OFF_2][1][2].setCoords(6 * grid_x / 8, 2 * grid_y / 3);
		tactics[KICK_OFF_2][1][3].setCoords(4 * grid_x / 8, 2 * grid_y / 4 - 3);
		tactics[KICK_OFF_2][1][4].setCoords(4 * grid_x / 8, 2 * grid_y / 4 + 3);
	}
	
	/**
	 * Returns the coordinates for all the players in a team following the preset kickoff tactics one.
	 * 
	 * @param team_a true if the returning coordinates are from the team A.
	 * @return the coordinates for all the players in a team following the preset kickoff tactics one.
	 */
	public static Coordinates[] getKickOff1(boolean team_a) {
		if (team_a) {
			return tactics[KICK_OFF_1][0];
		} else {
			return tactics[KICK_OFF_1][1];
		}
	}
	
	/**
	 * Returns the coordinates for all the players in a team following the preset kickoff tactics two.
	 * 
	 * @param team_a true if the returning coordinates are from the team A.
	 * @return the coordinates for all the players in a team following the preset kickoff tactics two.
	 */
	public static Coordinates[] getKickOff2(boolean team_a) {
		if (team_a) {
			return tactics[KICK_OFF_2][0];
		} else {
			return tactics[KICK_OFF_2][1];
		}
	}

}
