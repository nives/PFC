package Logic;


public class Action {
	
	private static final String MOVE  = "move";
	private static final String PASS  = "pass";
	private static final String SHOOT = "shoot";
	
	private String action_name;
	private int x;
	private int y;

	public Action() {
		// TODO Auto-generated constructor stub
	}
	
	public Action(String name, int x, int y) {
		this.action_name = name;
		this.x = x;
		this.y = y;
	}

	public static Action getMoveAction(int x, int y) {
		Action a = new Action(MOVE, x, y);
		return a;
	}
	
	public static Action getPassAction(int x, int y) {
		Action a = new Action(PASS, x, y);
		return a;
	}
	
	public static Action getShootAction(int x, int y) {
		Action a = new Action(SHOOT, x, y);
		return a;
	}
	
	
	/***********************
	 * Getters and Setters *
	 ***********************/

	public String getAction_name() {
		return action_name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

	
}
