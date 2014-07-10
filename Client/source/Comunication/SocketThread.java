package Comunication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Logic.Action;
import Logic.DecisionMaker;
import Logic.EnvironmentInformation;
import Logic.MainController;

/**
 * The class SocketThread implements a {@link Thread} dedicated to manage the communication with the server.
 * <p>
 * This class manages all the communication with the server and answers to all it's petitions.
 * Those petitions may be:
 * 		- CHECK: Checks that the client is still connected to the server.
 * 		- ACTION: Asks the client for a new action to carry out.
 * 		- DISCONNECT: Ends the connection.
 * It also manages different events .
 * Those event may be:
 * 		- GOAL: One of the teams scored a goal.
 * 		- OK: The action provided has been accepted.
 * 		- ERROR: The action provided is not valid.
 * 		- FULL: The server is full and the connection can be managed by the server.
 *  
 * @author Eduard de Torres
 * @version v0.1  04/10/2014
 *
 */
public class SocketThread implements Runnable {

	private static final String CHECK 		= "check";
	private static final String ACTION 		= "action";
	private static final String DISCONNECT 	= "disconnect";
	private static final String GOAL 		= "goal";
	private static final String OK 			= "ok";
	private static final String ERROR	 	= "error";
	private static final String FULL	 	= "full";
	
    private ConnectionClient connection_client;
    private EnvironmentInformation env_info;
    private DecisionMaker decisions;
    
    /**
     * Class constructor.
     * 
     * @param connection_client instance of the class that manages the connection with the server.
     */
    public SocketThread(ConnectionClient connection_client) {
        this.connection_client = connection_client;
        env_info = new EnvironmentInformation();
        decisions = new DecisionMaker(env_info);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void run() {
		try {
			connection_client.setSocket(new Socket(connection_client.getHostName(), connection_client.getPortNumber()));
			BufferedWriter out 	 = new BufferedWriter(new OutputStreamWriter(connection_client.getSocket().getOutputStream()));
            BufferedReader in 	 = new BufferedReader(new InputStreamReader(connection_client.getSocket().getInputStream()));
            
			String serverCommand;
			String serverInfo;
			
			//Send the team (A/B) to the Server.
			if (connection_client.isTeamA()) {
				out.write("A");
				env_info.setGoals(true);
			} else {
				out.write("B");
				env_info.setGoals(false);
			}
    		out.newLine();
    		out.flush();
    		//Send the name to the server..
    		out.write(connection_client.getConnection_view().getName());
    		out.newLine();
    		out.flush();
			
    		//Checks if the server connection is ok or if the server is full.
			serverCommand = in.readLine();
			if (serverCommand.equals(FULL)) {
				System.out.println("Server is full.");
				MainController.setConnectionMessage("Team selected is full.");
				MainController.disconnect();
			} else {
				MainController.changeTeam_selection_label(" ("+serverCommand+")");
				//Sets the number of the player.
				env_info.setMy_number(Integer.parseInt(serverCommand));
				System.out.println("Connection!");
	            while (connection_client.getSocket() != null && (serverCommand = in.readLine()) != null) {
	        		try {
						switch (serverCommand) {
							case ACTION:
								//Reads the information of the environment from the server.
								serverInfo = in.readLine();
				            	if (serverInfo != null) {
				            		//Parses the information read from the server.
				            		env_info.parseInfo(serverInfo);
				            		
				            		//Reads if anyone have the ball. 
				    				serverCommand = in.readLine();
				    				env_info.setHave_ball(serverCommand);
				            		Action action = decisions.makeDecision();
				            		
				            		//Sends the action to the server.
				            		out.write(action.getAction_name());
				            		out.newLine();
				            		out.flush();
				            		out.write(Integer.toString(action.getX()));
				            		out.newLine();
				            		out.flush();
				            		out.write(Integer.toString(action.getY()));
				            		out.newLine();
				            		out.flush();
				            		
				            		//Reads if the action is valid or not.
				            		serverCommand = in.readLine();
				            		switch (serverCommand) {
										case OK:	
											decisions.evaluateDecision(true);
											break;
										case ERROR:	
											decisions.evaluateDecision(false);
											break;
										default:
											break;
									}
				            	}
								break;
							case DISCONNECT:
								decisions.disconnect();
								MainController.disconnect();
								break;
								
							case CHECK:
								//Sends the action to the server.
			            		out.write(CHECK);
			            		out.newLine();
			            		out.flush();
								break;
								
							case GOAL:
								//Reads if the has been a goal.
			            		serverCommand = in.readLine();
			            		if (serverCommand.equals("M")) {
			            			decisions.goal(true);
			            		} else {
			            			decisions.goal(false);
			            		}
								break;
							
						}
					} catch (Exception e) {
					}
	            }
			}
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + connection_client.getHostName());
            
        } catch (IOException e) {
        	MainController.setConnectionMessage("Server disconnected.");
        	MainController.disconnect();
        }
    }
	
}
