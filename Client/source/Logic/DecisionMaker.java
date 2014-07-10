package Logic;


/**
 * The class DecisionMaker is an empty class to be implemented by the client. It contains all the methods related to
 * the decision process of the artificial intelligence.  
 * <p>
 * This class has all the methods needed to implement the decision process of the artificial intelligent the client 
 * wants to create. These different methods will cover parts of the process as active decision (choose which action do 
 * you want to do) and event handling like when a team scores a goal or how to react to a valid/invalid action. It also 
 * provides auxiliary methods to manage different functional events like the connection or disconnection to the server. 
 * 
 * It works together with the class {@link EnvironmentInformation} which provides all the informations available about 
 * the environment. 
 *  
 * @author Eduard de Torres
 * @version v0.1  04/10/2014
 *
 * @see Action
 * @see EnvironmentInformation
 */
public class DecisionMaker {
	
	private EnvironmentInformation env_info;

	/**
	 * Class Constructor.
	 * 
	 * @param env an EnvironmentInformation with the information to use by the client.
	 */
	public DecisionMaker(EnvironmentInformation env) {
		env_info = env;
	}
	
	/**
	 * Returns an {@link Action} the client wants to execute in the server.
	 * <p>
	 * Returns an Action (preferably a valid Action) chosen by the client which will be executed by the server in the 
	 * environment. This method works together with <code> evaluateDecision </code> method in the way that evaluateDecision 
	 * gives feedback to the client informing whether the last Action executed was a valid action or not. This action will 
	 * be the last Action chosen by the method <code> makeDecision </code>.
	 * 
	 * @return an Action to be executed by the server in the given environment.
	 */
	public Action makeDecision() {
		return null;
	}
	
	/**
	 * This method gives feedback to the client informing whether the last Action executed was a valid action or not. 
	 * This action will be the last Action chosen by the method <code> makeDecision </code>.
	 * 
	 * @param correct_action true if the last Action was a valid Action and false if it was not.
	 */
	public void evaluateDecision(boolean correct_action) {
				
	}

	/**
	 * This method is executed during the disconnection process with the server.
	 * <p>
	 * The client should use this method to implement all the necessary things to do during the disconnection process. 
	 */
	public void disconnect() {
				
	}

	/**
	 * This method is executed when any of the teams scores a goal.
	 * <p>
	 * The client should use this method to implement all the necessary things to do when a goal is scored. The method 
	 * also provides information to know which team scored the goal to act consequently. 
	 * 
	 * @param my_team_goal true if the team I'm playing for scored a goal, false otherwise.
	 */
	public void goal(boolean my_team_goal) {
				
	}
	
	/**
	 * This is an auxiliary method to use in case the client want to shoot the rival's goal.
	 * <p>
	 * The client can use this method when he wants to shoot directly to the center of the rival goal. This method
	 * automatically aims for the center of the rival's goal and returns a SHOOT Action with the rival's goal coordinates. 
	 * 
	 * @return a SHOOT Action with the rival's goal coordinates.
	 */
	public Action shootToGoal() {
		return Action.getShootAction(env_info.getOther_goal().getX(), env_info.getOther_goal().getY());
	}

}
