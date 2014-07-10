package logic;

import java.util.Random;

import Communication.Server;

/**
 * This class implements the ball and its behavior.
 * <p>
 * This class manages the information that defines the ball inside the game's Environment and implements its behavior.
 * Some of the information of the ball are its coordinates inside the grid world, its moving information or the event controls.
 * The balls behavior can be easily defined. If any player sets a direction for the ball to move, the ball moves to that
 * direction until it reaches the destination or until and interrupting event takes place. An interrupting event may be
 * a goal scored, a ball going outside the field or a player catching the ball.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/18/2014
 *
 */
public class Ball implements Runnable {
	
	private static final int STOP_PROB = 20;
	
	private EnvironmentController env;
	private int ball_x;
	private int ball_y;
	private int direction_x;
	private int direction_y;
	private boolean team_pass;
	private int team_pass_number;
	private int goal;
	private boolean team_goal;
	private int out;
		
	/**
	 * Class constructor.
	 * 
	 * @param env the {@link EnvironmentController} that has all the information about the game's environment.
	 */
	public Ball(EnvironmentController env) {
		this.env = env;
		goal = 0;
		out = 0;
		initBall();
	}

	/**
	 * Initializes the ball position and moving direction.
	 * <p>
	 * Initializes the ball position to the center of the game field and sets the moving direction to -1 which means that
	 * the ball isn't moving.
	 */
	public void initBall() {
		ball_x = EnvironmentController.GRID_SIZE_X / 2 - 1;//new Random().nextInt(EnvironmentController.GRID_SIZE_X-1);
		ball_y = EnvironmentController.GRID_SIZE_Y / 2 - 1;//new Random().nextInt(EnvironmentController.GRID_SIZE_Y-1);
		direction_x = -1;
		direction_y = -1;
	}
		
	/***********************
	 * Getters AND Setters *
	 ***********************/
	
	/**
	 * Returns the x-coordinate of the ball inside the grid world.
	 * @return the x-coordinate of the ball inside the grid world.
	 */
	public int getBall_x() {
		return ball_x;
	}

	/**
	 * Sets the x-coordinate of the ball inside the grid world.
	 * @param ball_x a x-coordinate to set the ball.
	 */
	public void setBall_x(int ball_x) {
		this.ball_x = ball_x;
	}

	/**
	 * Returns the y-coordinate of the ball inside the grid world.
	 * @return the y-coordinate of the ball inside the grid world.
	 */
	public int getBall_y() {
		return ball_y;
	}

	/**
	 * Sets the y-coordinate of the ball inside the grid world.
	 * @param ball_y a y-coordinate to set the ball.
	 */
	public void setBall_y(int ball_y) {
		this.ball_y = ball_y;
	}

	/**
	 * Gives a new direction to where the ball should be moved to.
	 * @param x the x-coordinate of the balls destination.
	 * @param y the y-coordinate of the balls destination.
	 * @param team_a true if the player setting the ball in motion is part of team A.
	 * @param team_number the number of the player inside its team.
	 */
	public void setDirection(int x, int y, boolean team_a, int team_number) {
		this.direction_x = x;
		this.direction_y = y;
		this.team_pass = team_a;
		this.team_pass_number = team_number;
	}

	/**
	 * Checks if the ball is stopped or moving.
	 * @return true if the ball is stopped.
	 */
	public boolean isStopped() {
		if (direction_x == -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		Random rand = new Random();
		try {
			Thread.sleep(Server.INIT_WAIT);
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		while (true) {
			try {
				Thread.sleep((int)(Server.WAIT / 3));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//The ball is moving.
			if (direction_x != -1) {
				//Compute the next position.
				double a = direction_x - ball_x;
				double b = direction_y - ball_y;
				double module = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
				
				ball_x += (int)(Math.round(a / module));
				ball_y += (int)(Math.round(b / module));
				
				//The ball is moving inside the net.
				if (goal > 0) {
					goal--;
					//The ball stops inside the net.
					if (goal == 0) {
						env.manage_goal(team_goal);
					}
				}
				//Te ball is moving outside the field.
				if (out > 0) {
					out--;
					//The ball stops outside the field.
					if (out == 0) {
						env.manage_goal(team_pass);
					}
				}
				
				//Check if team A scored a goal.
				if (checkGoal(true) && goal == 0) {
					team_goal = true;
					env.setResultA(env.getResultA() + 1);
					env.init_goal(true);
					direction_x = EnvironmentController.GRID_SIZE_X+2;
					goal = 3;
				} else if (checkGoal(false) && goal == 0) {
					//Check if team B scored a goal.
					team_goal = false;
					env.setResultB(env.getResultB() + 1);
					env.init_goal(false);
					direction_x = -3;
					goal = 3;
				} else if (checkOut()) {
					//Checks if the ball is outside the field.
					manageOut();				
				} else {
					String have_ball = env.getWhoHaveBall(team_pass);
					//Some player have the ball.
					if (!have_ball.equals("NONE")) {
						//The player that have the ball isn't the rival goalkeaper (isn't a shoot). 
						if (!have_ball.equals("O/-1")) {
							//The player that have the ball isn't the same that passed or shot it.
							String[] aux = have_ball.split("/");
							if (aux[0].equals("O") || !aux[1].equals(Integer.toString(team_pass_number))) {
								direction_x = -1;
								direction_y = -1;
							}
						//The player is the rival goalkeeper.	
						} else {
							//Rival goalkeeper may stop the ball.
							int chance = Math.abs(rand.nextInt(100)); 
							if (chance < STOP_PROB) {
								direction_x = -1;
								direction_y = -1; 
							}
						}
					}
				}
				env.repaintPlayers();
			}
		}
	}

	/**
	 * Checks if the ball is inside a goal.
	 * @param team_a the team that wants to check if scored a goal. True for team A.
	 * @return true if the given team has scored a goal. 
	 */
	private boolean checkGoal(boolean team_a) {
		if (team_a && ball_x >= EnvironmentController.GRID_SIZE_X - 1 && ball_y >= 23 && ball_y <= 28 ) {
			return true;
		} else if (!team_a && ball_x <= 0 && ball_y >= 23 && ball_y <= 28 ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if the ball is outside the field's bounds.
	 * @return true if the ball is outside the field's bounds.
	 */
	private boolean checkOut() {
		if (ball_x >= EnvironmentController.GRID_SIZE_X - 1 && (ball_y < 23 || ball_y > 28)) {
			return true;
		} else if (ball_x <= 0 && (ball_y < 23 || ball_y > 28)) {
			return true;
		} else if (ball_y <= 0 || ball_y >= EnvironmentController.GRID_SIZE_Y){
			return true;
		}
		return false;
	}
	
	/**
	 * Manages the situation in which the ball is outside the field's bounds.
	 */
	private void manageOut() {
		//Ball is outside on Right side.
		if (ball_x >= EnvironmentController.GRID_SIZE_X - 1 && (ball_y < 23 || ball_y > 28)) {
			direction_x = EnvironmentController.GRID_SIZE_X + 2;
		} 
		//Ball is outside on Left side.
		else if (ball_x <= 0 && (ball_y < 23 || ball_y > 28)) {
			direction_x = -3;
		} 
		//Ball is outside on Top side.
		else if (ball_y <= 0) {
			direction_y = -3;
		} 
		//Ball is outside on Bottom side.
		else if (ball_y >= EnvironmentController.GRID_SIZE_Y){
			direction_y = EnvironmentController.GRID_SIZE_Y +2;
		}
		out = 3;
		env.init_out();
	}
	
}
