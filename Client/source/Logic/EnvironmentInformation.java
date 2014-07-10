package Logic;

/**
 * The class EnvironmentInformation provides the client with all the informations available about the environment the game
 * it's being played.
 * <p>
 * This class stores all the useful information the client may need about the environment the game it's taking place.
 * The field that is used as environment is represented by a grid world.
 * This information stored includes three categories: field's information, players' information and ball's information.
 *  - The field's information category consists of the number of rows and columns the grid world has and the position of the 
 *    goals.
 *  - The players' information category consists of the maximum number of players per team and the players' position on
 *    the grid world.    
 *  - The ball's category consists of the position of the ball in the grid world and the information of who has the ball.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/10/2014
 *
 * @see Position
 */
public class EnvironmentInformation {

	/**
	 * maximum number of players in the environment.
	 */
	public  static final int MAX_PLAYERS = 5;
	/**
	 * maximum number of rows in the grid world.
	 */
	public  static final int GRID_SIZE_X = 42*2+2;		//42m (84)
	/**
	 * maximum number of columns in the grid world.
	 */
	public  static final int GRID_SIZE_Y = 25*2+2+1;	//25m (53)
	private Position[] my_team;
	private Position[] other_team;
	private int my_number;
	private Position ball;
	private String 	 have_ball;
	private Position my_goal;
	private Position other_goal;
	
	/**
	 * Class Constructor.
	 */
	public EnvironmentInformation() {
		my_team = new Position[MAX_PLAYERS];
		other_team = new Position[MAX_PLAYERS];
		
		for (int i = 0; i < MAX_PLAYERS; i++) {
			my_team[i] = new Position();
			other_team[i] = new Position();
		}
		ball = new Position();
	}

	/**
	 * Converts the server information String into accessible information stored in the EnvironmentInformation instance.
	 * <p>
	 * Parses the information provided through the server information string and updates the information stored in the
	 * EnvironmentInformation instance.
	 * 
	 * @param serverInfo server information String.
	 */
	public void parseInfo(String serverInfo) {
		String[] info_aux = serverInfo.split("/");
		
		try {
			for (int i = 0; i < MAX_PLAYERS; i++) {
				String info[] = info_aux[i].split(" ");
				my_team[i].setX(Integer.parseInt(info[0]));
				my_team[i].setY(Integer.parseInt(info[1]));
			}
			
			for (int i = 0; i < MAX_PLAYERS; i++) {
				String info[] = info_aux[i+MAX_PLAYERS].split(" ");
				other_team[i].setX(Integer.parseInt(info[0]));
				other_team[i].setY(Integer.parseInt(info[1]));
			}
			
			String info[] = info_aux[MAX_PLAYERS * 2].split(" ");
			ball.setX(Integer.parseInt(info[0]));
			ball.setY(Integer.parseInt(info[1]));
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Return whether the ball is in possession of the client's player or not.
	 * 
	 * @return true if the client's player have the ball in possession, false otherwise.
	 */
	public boolean isBallMine() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("M") && Integer.parseInt(s[1]) + 1 == my_number) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Return whether the ball is in possession of the client's player's team or not.
	 * 
	 * @return true if any of the client's player's teammates has the ball in possession, false otherwise.
	 */
	public boolean isBallMyTeam() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("M")) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Return whether the ball is in possession of the client's player's rival team or not.
	 * 
	 * @return true if any of the players in the rival team has the ball in possession, false otherwise.
	 */
	public boolean isBallOtherTeam() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("O")) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Returns whether any of the players with the number <code>num</code> in any of both teams has the ball.
	 * 
	 * @param num the number of the player the ball possession will be checked on.
	 * @return true if any of both team players with number <code>num</code> has the ball.
	 */
	public boolean getPlayerHasBall(int num) {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			return Integer.parseInt(s[1]) + 1 == num;
		}
	}
	
	/**
	 * Returns the {@link Position} of the client's player.
	 * 
	 * @return the {@link Position} of the client's player.
	 */
	public Position getMyPosition() {
		return my_team[my_number];
	}
	
	/**
	 * Returns a {@link Position} array of the client's player's team coordinates.
	 * @return a {@link Position} array of the client's player's team coordinates.
	 */
	public Position[] getMy_team() {
		return my_team;
	}

	/**
	 * Sets the {@link Position} array of the client's player's team coordinates.
	 * @param my_team a {@link Position} array of the client's player's team coordinates.
	 */
	public void setMy_team(Position[] my_team) {
		this.my_team = my_team;
	}

	/**
	 * Returns a {@link Position} array of the client's player's opponent team coordinates.
	 * @return a {@link Position} array of the client's player's opponent team coordinates.
	 */
	public Position[] getOther_team() {
		return other_team;
	}

	/**
	 * Sets the {@link Position} array of the client's player's opponent team coordinates.
	 * @param other_team a {@link Position} array of the client's player's opponent team coordinates.
	 */
	public void setOther_team(Position[] other_team) {
		this.other_team = other_team;
	}

	/**
	 * Returns the {@link Position} of the ball.
	 * @return the {@link Position} of the ball.
	 */
	public Position getBall() {
		return ball;
	}

	/**
	 * Sets the {@link Position} of the ball.
	 * @param ball the {@link Position} of the ball.
	 */
	public void setBall(Position ball) {
		this.ball = ball;
	}

	/**
	 * Returns the maximum number of players in a team.
	 * @return the maximum number of players in a team.
	 */
	public static int getMaxPlayers() {
		return MAX_PLAYERS;
	}

	/**
	 * Returns the number of the client's player in the team from 1 to <code>MAX_PLAYERS</code>.
	 * @return the number of the client's player in the team.
	 */
	public int getMy_number() {
		return my_number;
	}

	/**
	 * Sets the number of the client's player in the team.
	 * @param my_number the number of the client's player in the team. Must be in range from 1 to <code>MAX_PLAYERS</code>.
	 */
	public void setMy_number(int my_number) {
		this.my_number = my_number;
	}

	/**
	 * Returns a coded string with the information of the team and player number that has the ball.
	 * <p>
	 * Returns a string with the format "<code>Team/Number</code>". The <code>Team</code> field can be either <code>M</code>
	 * for the client's player's team of <code>O</code> for the client's player's rival team. The <code>Number</code> field
	 * can go from 1 to <code>MAX_PLAYERS</code>.    
	 * 
	 * @return a coded string with the information of the team and player number that has the ball.
	 */
	public String getHave_ball() {
		return have_ball;
	}

	/**
	 * Sets the information of the team and player number that has the ball with a coded string.
	 * <p>
	 * Sets the information of the team and player number that has the ball with a coded string using the format 
	 * "<code>Team/Number</code>". The <code>Team</code> field can be either <code>M</code> for the client's player's team
	 *  of <code>O</code> for the client's player's rival team. The <code>Number</code> field can go from 1 to 
	 *  <code>MAX_PLAYERS</code>. 
	 * 
	 * @param have_ball a coded string with the information of the team and player number that has the ball
	 */
	public void setHave_ball(String have_ball) {
		this.have_ball = have_ball;
	}


	public void setGoals(boolean team_a) {
		if (team_a) {
			my_goal = new Position(0, GRID_SIZE_Y/2);
			other_goal = new Position(GRID_SIZE_X - 1, GRID_SIZE_Y/2);
		} else {
			my_goal = new Position(GRID_SIZE_X - 1, GRID_SIZE_Y/2);
			other_goal = new Position(0, GRID_SIZE_Y/2);
		}
	}

	/**
	 * Returns the {@link Position} of the client's player's team's goal.
	 * 
	 * @return a {@link Position} with the client's player's team's goal's coordinates.
	 */
	public Position getMy_goal() {
		return my_goal;
	}

	/**
	 * Returns the {@link Position} of the client's player's rival team's goal.
	 * 
	 * @return a {@link Position} with the client's player's rival team's goal's coordinates.
	 */
	public Position getOther_goal() {
		return other_goal;
	}
	
}
