package logic;

import misc.Coordinates;
import Communication.Server;

/**
 * This class implements all the functionalities and information of the game's goalkeepers.
 * <p>
 * This class has all the information needed to represent a goalkeeper in the game's Environment and has all the methods needed to 
 * implement its movements and decisions.
 * The main decision's process of the goalkeeper's artificial intelligence is set to determine where and when to move, and what to do
 * in case the goalkeeper has the ball's possession.   
 *  
 * @author Eduard de Torres
 * @version v0.1  04/25/2014
 *
 */
public class Goalkeeper implements Runnable{
	
	private static final double GOAL_DISTANCE = 2.0;
	
	private EnvironmentController env;
	private int pos_x;
	private int pos_y;
	private boolean team_a;
	
	/**
	 * Class constructor.
	 * 
	 * @param env controller of the game's execution's phase with all the information about the game's environment.
	 * @param team_a if true, the goalkeeper plays for team A.
	 */
	public Goalkeeper(EnvironmentController env, boolean team_a) {
		this.env = env;
		this.team_a = team_a;
		this.pos_x = -1;
		this.pos_y = -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(Server.INIT_WAIT);
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		double x;
		if (team_a) {
			x = 0;
		} else {
			x = (EnvironmentController.GRID_SIZE_X - 1);
		}
		while (true) {
			try {
				Thread.sleep((int)(Server.WAIT));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (Server.isStart()) {
				//The Keeper have the ball.
				if (env.haveBall(new Coordinates(pos_x, pos_y)) && env.getBall().isStopped()) {
					Coordinates pass_destination = chooseClosestTeammate();
					if (pass_destination != null) {
						env.getBall().setDirection(pass_destination.getX(), pass_destination.getY(), team_a, -1);
					} else {
						if (team_a) {
							env.getBall().setDirection(EnvironmentController.GRID_SIZE_X - 1, EnvironmentController.GRID_SIZE_Y / 2 - 1, team_a, -1);
						} else {
							env.getBall().setDirection(0                  					, EnvironmentController.GRID_SIZE_Y / 2 - 1, team_a, -1);
						}
					}
				} else {
					//The keeper doesn't have the ball.
					double a = env.getBall().getBall_x() - x;
					double b = env.getBall().getBall_y() - EnvironmentController.GRID_SIZE_Y / 2;
					double module = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
					
					Coordinates dest = new Coordinates((int)(x + (Math.round(a / module * GOAL_DISTANCE))), (int)((EnvironmentController.GRID_SIZE_Y / 2) + Math.round(b / module * GOAL_DISTANCE)));

					dest = env.movePlayer(team_a, -1, dest);	
					pos_x = dest.getX();
					pos_y = dest.getY();
					
					env.repaintPlayers();
				}
			}
		}
		
	}
	
	/**
	 * Returns the coordinates of the closest teammate.
	 * <p>
	 * This method looks at the closest teammate with a free of rivals pass line.
	 *   
	 * @return the {@link Coordinates} of the closest teammate to pass the ball or null if there isn't any possible pass line.
	 */
	private Coordinates chooseClosestTeammate() {
		Coordinates teammate = null;
		double dist = Double.MAX_VALUE;
		Team my_team; 
		Team other_team;
		
		if (team_a) {
			my_team = env.getTeam_A();
			other_team = env.getTeam_B();
		} else {
			my_team = env.getTeam_B();
			other_team = env.getTeam_A();
		}
		
		//Check the pass to each of my teammates.
		for (int i = 0; i < my_team.getNumber_of_players(); i++ ) {
			int x = pos_x;
			int y = pos_y;
			double dist_targ = Math.sqrt (Math.pow (my_team.getPlayer_list().get(i).getPos_x() - x, 2) + Math.pow (my_team.getPlayer_list().get(i).getPos_y() - y, 2));
			double dist_aux;
			//Check the path to the selected teammate.
			while (x != my_team.getPlayer_list().get(i).getPos_x() && y != my_team.getPlayer_list().get(i).getPos_y()) {
				dist_aux = Math.sqrt(Math.pow( my_team.getPlayer_list().get(i).getPos_x() - x, 2) + Math.pow(my_team.getPlayer_list().get(i).getPos_y() - y, 2));
				x = (int) Math.round(x + (my_team.getPlayer_list().get(i).getPos_x() - x) / dist_aux);
				y = (int) Math.round(y + (my_team.getPlayer_list().get(i).getPos_y() - y) / dist_aux);
				
				//
				for (int j = 0; j < other_team.getNumber_of_players(); j++) {
					if (env.checkCatchBall(other_team.getPlayer_list().get(j).getCoordenates(), new Coordinates(x, y))) {
						dist_targ = Double.MAX_VALUE;
					}
				}
			}
			if (dist_targ < dist) {
				dist = dist_targ;
				teammate = new Coordinates(my_team.getPlayer_list().get(i).getPos_x(), my_team.getPlayer_list().get(i).getPos_y());
			}
		}
		
		
		return teammate;
	}

	/**
	 * Returns the controller of the game's execution's phase with all the information about the game's environment. 
	 * @return the controller of the game's execution's phase with all the information about the game's environment.
	 */
	public EnvironmentController getEnv() {
		return env;
	}

	/**
	 * Sets the controller of the game's execution's phase with all the information about the game's environment.
	 * @param env the controller of the game's execution's phase with all the information about the game's environment.
	 */
	public void setEnv(EnvironmentController env) {
		this.env = env;
	}

	/**
	 * Returns the x-coordinate of the goalkeeper in the game's grid world.
	 * @return the x-coordinate of the goalkeeper in the game's grid world.
	 */
	public int getPos_x() {
		return pos_x;
	}

	/**
	 * Sets the x-coordinate of the goalkeeper in the game's grid world.
	 * @param pos_x the x-coordinate of the goalkeeper in the game's grid world.
	 */
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	/**
	 * Returns the y-coordinate of the goalkeeper in the game's grid world.
	 * @return the y-coordinate of the goalkeeper in the game's grid world.
	 */
	public int getPos_y() {
		return pos_y;
	}

	/**
	 * Sets the y-coordinate of the goalkeeper in the game's grid world.
	 * @param pos_y the y-coordinate of the goalkeeper in the game's grid world.
	 */
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	/**
	 * Returns the goalkeeper's team.
	 * @return true if the goalkeeper is playing for team A.
	 */
	public boolean isTeam_a() {
		return team_a;
	}

	/**
	 * Sets the goalkeeper's team.
	 * @param team_a if true, the goalkeeper will be playing for team A.
	 */
	public void setTeam_a(boolean team_a) {
		this.team_a = team_a;
	}
	
}
