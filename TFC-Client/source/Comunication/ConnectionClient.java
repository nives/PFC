package Comunication;

import java.io.IOException;
import java.net.Socket;

import View.ConnectionView;
import View.MainFrame;

public class ConnectionClient {

	private int portNumber = 23000;
	private String hostName = "localhost";
	private Socket socket;
	private Thread thread;
	private boolean team_a;
	private ConnectionView connection_view;
	
	public ConnectionClient(ConnectionView connection_view) {
		socket = null;
		this.connection_view = connection_view;
	}
	

	public void connect(MainFrame main_frame) throws IOException {
		thread = new Thread(new SocketThread(this));
		thread.start();
    }
	
	public void disconnect() {
		//if (socket != null) {
			try {
				System.out.println("Closing socket");
				socket.close();
				socket = null;
			} catch (Exception e){
				
			}
		//}
	}
	
	
	/***********************
	 * Getters and Setters *
	 ***********************/

	public int getPortNumber() {
		return portNumber;
	}


	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;		
	}
	
	public boolean isTeamA() {
		return team_a;
	}


	public void setTeamA(boolean team_a) {
		this.team_a = team_a;
	}


	public ConnectionView getConnection_view() {
		return connection_view;
	}


	public void setConnection_view(ConnectionView connection_view) {
		this.connection_view = connection_view;
	}
	
	

}
