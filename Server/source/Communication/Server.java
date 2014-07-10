package Communication;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import logic.Action;
import logic.EnvironmentController;
import misc.Language;

/**
 * This class implements the server that receives the connection petitions from the clients.
 * <p>
 * This class implements a runnable {@link Thread} that waits for the client's connection's petitions.
 * It also configures the maximum number of connections accepted, the port number and manages the dedicated servers
 * that creates for each petition.  
 *  
 * @author Eduard de Torres
 * @version v0.1  04/10/2014
 *
 * @see Thread
 * @see Socket
 * @see SocketThread
 */
public class Server implements Runnable{

	protected static final int MAX_CONNECTIONS = 5;
	private static final int PORT = 23000;
	/**
	 * Time to wait in milliseconds when the game starts.
	 */
	public  static final int INIT_WAIT = 10000;
	/**
	 * Time for the {@link SocketThread} to wait between new {@link Action} petitions. 
	 */
	public  static final int WAIT = 300;
	private int counter_a;
	private int counter_b;
	private static Socket[] sockets;
	private ServerSocket serverSocket;
	private static boolean start;
	private EnvironmentController env;

	/**
	 * Class constructor.
	 * 
	 * @param env controller of the environment view. Necessary for the dedicated server to get the data. 
	 */
    public Server(EnvironmentController env) {
    	this.env = env;
    	Server.start = false;
    	sockets = new Socket[MAX_CONNECTIONS*2];
    	for (int i = 0; i < MAX_CONNECTIONS*2; i++) {
			sockets[i] = null;
		}
        serverSocket = null;
        counter_a = 0;
        counter_b = 0;
        
    }

    /**
     * Returns the number of connections assigned to the team A.
     * 
     * @return number of connections assigned to the team A.
     */
	public int getCounterA() {
		return counter_a;
	}

	/**
     * Sets the number of connections assigned to the team A.
     * 
     * @param counter of connections assigned to the team A.
     */
	public void setCounterA(int counter) {
		this.counter_a = counter;
	}
	
	/**
     * Returns the number of connections assigned to the team B.
     * 
     * @return number of connections assigned to the team B.
     */
	public int getCounterB() {
		return counter_b;
	}

	/**
     * Sets the number of connections assigned to the team B.
     * 
     * @param counter of connections assigned to the team B.
     */
	public void setCounterB(int counter) {
		this.counter_b = counter;
	}

	/**
	 * Returns the array of dedicated server's sockets. 
	 * 
	 * @return an array of dedicated server's sockets.
	 */
	public Socket[] getSockets() {
		return sockets;
	}
	
	/**
	 * Sets a socket to the indicated position of the dedicated server's sockets' array. 
	 * 
	 * @param socket a socket to add to the dedicated server's sockets' array.
	 * @param i the position to set the socket in the dedicated server's sockets' array
	 */
	public void setSocket(Socket socket, int i) {
		sockets[i] = socket;
	}

	/**
	 * Sets all the sockets of the dedicated server's sockets' array.
	 * 
	 * @param sockets an array of dedicated server's sockets.
	 */
	public void setSockets(Socket[] sockets) {
		Server.sockets = sockets;
	}

	/**
	 * Returns if the game is running or not.
	 * 
	 * @return true if the game is running, false otherwise.
	 */
	public static boolean isStart() {
		return start;
	}

	/**
	 * Sets the status of the game. If the game is running the parameter should be true.
	 * 
	 * @param start status of the game.
	 */
	public static void setStart(boolean start) {
		Server.start = start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		try {
            serverSocket = new ServerSocket(PORT, 100);
            while (true) {
                int i = 0;
            	while (i < 8 && sockets[i] != null) {
            		i++;
            	}
            	sockets[i] = serverSocket.accept();
                new Thread(new SocketThread(this, i, env)).start();
	            System.out.println("New connection!");
            }
        } catch (IOException exp) {
        	JOptionPane.showMessageDialog(null, Language.getDialog(30));
        	System.exit(0);
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
            }
        }
		
	}

}
