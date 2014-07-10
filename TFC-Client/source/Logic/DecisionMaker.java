package Logic;


public class DecisionMaker {
	
	private EnvironmentInformation env_info;
	private Player player;

	public DecisionMaker(EnvironmentInformation env) {
		env_info = env;
		player = new Player(env);
	}
	
	public Action makeDecision() {
		return player.decide();		
	}
	
	public void evaluateDecision(boolean correct_action) {
		if (!correct_action) {
			player.setIncorrect(true);
		}
		
	}

	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	public void goal(boolean my_team_goal) {
		// TODO Auto-generated method stub
		System.out.println("GOAL! "+my_team_goal);
		
	}
	
	public Action shootToGoal() {
		return Action.getShootAction(env_info.getOther_goal().getX(), env_info.getOther_goal().getY());
	}

}
