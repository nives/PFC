package Communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import logic.Action;
import logic.EnvironmentController;
import misc.Coordinates;

/**
 * This class implements the dedicated server that controls all the communication between the client and the server.
 * <p>
 * This class implements a runnable {@link Thread} that manages the communication between the client and the server.
 * The main running cycle of this {@link Thread} is:
 *  - Wait # milliseconds.
 *  - Send the environment information to the client.
 *  - Ask the client for an {@link Action} to do.
 *  - Test the action and do it if possible.
 *  - Give feedback to the client about the {@link Action} returned.
 *   
 * It also manages different events like the initial configuration after the {@link Server} class accepting the connection
 * or the disconnection process.  
 *  
 * @author Eduard de Torres
 * @version v0.1  04/10/2014
 *
 * @see Action
 * @see Thread
 * @see Server
 * @see Socket
 */
public class SocketThread implements Runnable {

	private static final String CHECK 		= "check";
	private static final String ACTION 		= "action";
	private static final String DISCONNECT 	= "disconnect";
	private static final String GOAL 		= "goal";
	private static final String OK 			= "ok";
	private static final String ERROR	 	= "error";
	private static final String FULL	 	= "full";
	
    private Server server;
    private EnvironmentController environment_controller;
    private int socket_number;
    private boolean team_a;
    private int team_number;
    private boolean goal_checked;

    /**
     * Class constructor.
     * 
     * @param server instance of the server that contains all the configuration parameters. 
     * @param number position of the socket that manages the dedicated server in the dedicated server's array in the
     * {@link Server} class.
     * @param env controller of the environment that contains all the information of the elements in the game field.
     */
    public SocketThread(Server server, int number, EnvironmentController env) {
        this.server = server;
        this.socket_number = number;
        this.environment_controller = env;
        goal_checked = false;
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public void run() {
    	Action action = new Action();
    	
    	try (
    		//Creates the reading and printing channels with the client.
    		BufferedWriter out 	 = new BufferedWriter(new OutputStreamWriter(server.getSockets()[socket_number].getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getSockets()[socket_number].getInputStream()));
        ) {
    		
    		//Reads the Team (A/B).
    		String team = in.readLine();
    		//Reads the name of the client.
    		String name = in.readLine();
    		
    		if (team.equals("A")) {
    			server.setCounterA(server.getCounterA()+1);
    			team_a = true;
    		} else if (team.equals("B")) {
    			server.setCounterB(server.getCounterB()+1);
    			team_a = false;
    		}
    		team_number = environment_controller.assignSocket(server.getSockets()[socket_number], team_a, name);    
    		if (team_number >= 0) {
    			//Sends the team number.
    			out.write(Integer.toString(team_number+1));
            	System.out.println("Sending <accepted> to client "+(socket_number+1)+".");
            	out.newLine();
            	out.flush();
            	System.out.println("Waiting.");
            	while (!Server.isStart() && server.getSockets()[socket_number] != null) {
            		try {
	            		//Sends a check petition to the client
	                    out.write(CHECK);
	                	out.newLine();
	                	out.flush();
	                	//Reads the check petition
	                	in.readLine();
            		} catch (IOException e) {
	            		//server.getSockets()[socket_number].close();
	            		server.setSocket(null, socket_number);
	            		environment_controller.disconnectSocket(team_a, team_number);
	            	}
                	
            	}
            	
            	if (server.getSockets()[socket_number] != null) {
            		try {
						Thread.sleep(Server.INIT_WAIT);
					} catch (InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
            	}
            	
	            while (server.getSockets()[socket_number] != null && !server.getSockets()[socket_number].isClosed()) {
	            	
	                try {
						Thread.sleep(Server.WAIT);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                if (Server.isStart()) {
	                	goal_checked = false;
		            	try {
		            		//Sends an action petition to the client
		                    out.write(ACTION);
		                	out.newLine();
		                	out.flush();
		                	//Sends the information of the environment to the client.
		                	out.write(environment_controller.prepareInfo(team_a));
		                	out.newLine();
		                	out.flush();
		                	//Sends who have the ball (if any).
		                	out.write(environment_controller.getWhoHaveBall(team_a));
		                	out.newLine();
		                	out.flush();
		                	//Reads the action from the client.
		                	action.setAction_name(in.readLine());
		                	action.setX(Integer.parseInt(in.readLine()));
		                	action.setY(Integer.parseInt(in.readLine()));
		                	boolean valid;
		                	Coordinates player;
		                	switch (action.getAction_name()) {
		            			case Action.MOVE:
		            				//Pre-moves the player.
		            				Coordinates new_position = environment_controller.movePlayer(team_a, team_number, action.getCoordenates());
		            				//Checks of the new position is valid.
		            				valid = environment_controller.checkValidPosition(team_a, team_number, new_position);
		            				//If valid, moves the player.
		            				if (valid) {
		            					boolean have_ball;
		            					if (team_a) {
		            						player = environment_controller.getTeam_A().getPlayer_list().get(team_number).getCoordenates();
		            						have_ball = environment_controller.haveBall(player);
		            						environment_controller.getTeam_A().getPlayer_list().get(team_number).setPos_x(new_position.getX());
		            						environment_controller.getTeam_A().getPlayer_list().get(team_number).setPos_y(new_position.getY());
		            					} else {
		            						player = environment_controller.getTeam_B().getPlayer_list().get(team_number).getCoordenates();
		            						have_ball = environment_controller.haveBall(player);
		            						environment_controller.getTeam_B().getPlayer_list().get(team_number).setPos_x(new_position.getX());
		            						environment_controller.getTeam_B().getPlayer_list().get(team_number).setPos_y(new_position.getY());
		            					}
		            					if (have_ball) {
		            						environment_controller.getBall().setBall_x(new_position.getX());
		            						environment_controller.getBall().setBall_y(new_position.getY());
		            					}
		            					//Send OK to the client!
		            					environment_controller.repaintPlayers();
		            					out.write(OK);
		    		                	out.newLine();
		    		                	out.flush();
		            				} else {
		            					//Send error to the client!
		            					out.write(ERROR);
		    		                	out.newLine();
		    		                	out.flush();
		            				}
		            				break;
		            			case Action.PASS:
		            				//Gets the player coordinates.
		            				if (team_a) {
		            					player = new Coordinates(environment_controller.getTeam_A().getPlayer_list().get(team_number).getPos_x(),
		            											 environment_controller.getTeam_A().getPlayer_list().get(team_number).getPos_y());
		            				} else {
		            					player = new Coordinates(environment_controller.getTeam_B().getPlayer_list().get(team_number).getPos_x(),
												 				 environment_controller.getTeam_B().getPlayer_list().get(team_number).getPos_y());
		            				}
		            				//Checks if the player have the ball to pass.
		            				valid = environment_controller.haveBall(player);
		            				//If valid, moves the ball.
		            				if (valid) {
		            					//Send OK to the client!
		            					environment_controller.getBall().setDirection(action.getX(), action.getY(), team_a, team_number);
		            					out.write(OK);
		    		                	out.newLine();
		    		                	out.flush();
		            				} else {
		            					//Send error to the client!
		            					out.write(ERROR);
		    		                	out.newLine();
		    		                	out.flush();
		            				}
		            				break;
		            			case Action.SHOOT:
		            				//Gets the player coordinates.
		            				if (team_a) {
		            					player = new Coordinates(environment_controller.getTeam_A().getPlayer_list().get(team_number).getPos_x(),
		            											 environment_controller.getTeam_A().getPlayer_list().get(team_number).getPos_y());
		            				} else {
		            					player = new Coordinates(environment_controller.getTeam_B().getPlayer_list().get(team_number).getPos_x(),
												 				 environment_controller.getTeam_B().getPlayer_list().get(team_number).getPos_y());
		            				}
		            				//Checks if the player have the ball to shoot.
		            				valid = environment_controller.haveBall(player);
		            				//If valid, moves the ball.
		            				if (valid) {
		            					//Send OK to the client!
		            					environment_controller.getBall().setDirection(action.getX(), action.getY(), team_a, team_number);
		            					out.write(OK);
		    		                	out.newLine();
		    		                	out.flush();
		            				} else {
		            					//Send error to the client!
		            					out.write(ERROR);
		    		                	out.newLine();
		    		                	out.flush();
		            				}
		            				break;
		            		}
		            	
	            	
		            	} catch (IOException e) {
		            		server.getSockets()[socket_number].close();
		            		server.setSocket(null, socket_number);
		            		environment_controller.disconnectSocket(team_a, team_number);
		            	}
	                } else {
	                	//If the server is "paused" and there's been a goal that I haven't still checked.
	                	if (environment_controller.isGoal() && !goal_checked) {
	                		goal_checked = true;
	                		//Send Goal message
	                		out.write(GOAL);
		                	out.newLine();
		                	out.flush();
		                	//Send team that scored the goal.
	                		if (environment_controller.isTeam_goal() == team_a) {
	                			out.write("M");
    		                	out.newLine();
    		                	out.flush();
	                		} else {
	                			out.write("O");
    		                	out.newLine();
    		                	out.flush();
	                		}
	                	}
	                }
	            }
	            if (server.getSockets()[socket_number] != null) {
	                out.write(DISCONNECT);
	            	System.out.println("Sending <disconnect> to client "+socket_number+".");
	            	out.newLine();
	            	out.flush();
	            	environment_controller.disconnectSocket(team_a, team_number);
	            }
    		} else {
    			out.write(FULL);
            	System.out.println("Sending <full> to client "+(socket_number+1)+".");
            	out.newLine();
            	out.flush();
    		}
        } catch (IOException e) {
        	try {
				server.getSockets()[socket_number].close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		server.setSocket(null, socket_number);
    		environment_controller.disconnectSocket(team_a, team_number);
        }
    	server.setSocket(null, socket_number);
    	environment_controller.disconnectSocket(team_a, team_number);
    	if (team_a) {
    		server.setCounterA(server.getCounterA() - 1);
    	} else {
    		server.setCounterB(server.getCounterB() - 1);
    	}
    	System.out.println("Client "+socket_number+" disconnected.");
    }
    
}
