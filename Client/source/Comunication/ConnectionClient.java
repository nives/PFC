package Comunication;

import java.io.IOException;
import java.net.Socket;

import View.ConnectionView;


/**
 * The class ConnectionClient manages the connection with the server.
 * <p>
 * This class manages the actions of connect and disconnect through a {@link Socket} connection with the server application.
 * It creates the new {@link Thread} dedicated to manage the connection with the server and to answer the server petitions 
 * for new actions.    
 *  
 * @author Eduard de Torres
 * @version v0.1  03/14/2014
 *
 */
public class ConnectionClient {

	private int portNumber = 23000;
	private String hostName = "localhost";
	private Socket socket;
	private Thread thread;
	private boolean team_a;
	private ConnectionView connection_view;
	
	/**
	 * Class constructor.
	 * 
	 * @param connection_view view of the JPanel used to show the connection options.
	 */
	public ConnectionClient(ConnectionView connection_view) {
		this.socket = null;
		this.connection_view = connection_view;
	}
	
	/**
	 * Creates the new Thread that will manage the connection with the server.
	 * <p>
	 * Creates a new {@link SocketThread} instance and starts it.
	 * 
	 * @throws IOException If an input or output exception occurred.
	 */
	public void connect() throws IOException {
		thread = new Thread(new SocketThread(this));
		thread.start();
    }
	
	/**
	 * Closes the connection with the server. 
	 */
	public void disconnect() {
		try {
			System.out.println("Closing socket");
			socket.close();
			socket = null;
		} catch (Exception e){
			
		}
	}
	
	
	/***********************
	 * Getters and Setters *
	 ***********************/
	
	/**
	 * Returns the remote port number to which the socket will be connected.
	 * @return the port number stored to use during the Socket connection.
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets the port value to be used with the socket connection. 
	 * @param portNumber value of the port.
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Returns the current hostname to which the socket will be connected.
	 * @return the hostname stored to use during the Socket connection.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Sets the hostname to be used with the socket connection. 
	 * @param hostName IP or name of the server to connect with.
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * Returns the {@link Socket} used to create the connection with the server.
	 * @return the socket valeu that connected with the server or null if the socket is disconnected. 
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Sets the {@link Socket} used to create the connection with the server.
	 * @param socket the socket.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;		
	}
	
	/**
	 * Returns either if the client is configured to connect as a player of the team A or the team B.
	 * @return true for team A, false for team B.
	 */
	public boolean isTeamA() {
		return team_a;
	}

	/**
	 * Sets the configuration to connect as a player of the team A or the team B.
	 * @param team_a true for team A, false for team B.
	 */
	public void setTeamA(boolean team_a) {
		this.team_a = team_a;
	}

	/**
	 * Returns the JPanel that manages the view when the client isn't connected to the server.
	 * @return a JPanel containing all JComponents used in the connection view.
	 */
	public ConnectionView getConnection_view() {
		return connection_view;
	}

	/**
	 * Sets the JPanel that manages the view when the client isn't connected to the server.
	 * @param connection_view a JPanel containing all JComponents used in the connection view.
	 */
	public void setConnection_view(ConnectionView connection_view) {
		this.connection_view = connection_view;
	}

}
