package Logic;

public class EnvironmentInformation {

	private static final int MAX_PLAYERS = 5;
	public  static final int GRID_SIZE_X = 42*2+2;		//42m (84)
	public  static final int GRID_SIZE_Y = 25*2+2+1;	//25m (53)
	private Position[] my_team;
	private Position[] other_team;
	private int my_number;
	private Position ball;
	private String 	 have_ball;
	private Position my_goal;
	private Position other_goal;
	
	public EnvironmentInformation() {
		my_team = new Position[MAX_PLAYERS];
		other_team = new Position[MAX_PLAYERS];
		
		for (int i = 0; i < MAX_PLAYERS; i++) {
			my_team[i] = new Position();
			other_team[i] = new Position();
		}
		ball = new Position();
	}

	public void parseInfo(String serverInfo) {
		String[] info_aux = serverInfo.split("/");
		
		try {
			for (int i = 0; i < MAX_PLAYERS; i++) {
				String info[] = info_aux[i].split(" ");
				my_team[i].setX(Integer.parseInt(info[0]));
				my_team[i].setY(Integer.parseInt(info[1]));
			}
			
			for (int i = 0; i < MAX_PLAYERS; i++) {
				String info[] = info_aux[i+MAX_PLAYERS].split(" ");
				other_team[i].setX(Integer.parseInt(info[0]));
				other_team[i].setY(Integer.parseInt(info[1]));
			}
			
			String info[] = info_aux[MAX_PLAYERS * 2].split(" ");
			ball.setX(Integer.parseInt(info[0]));
			ball.setY(Integer.parseInt(info[1]));
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isBallMine() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("M") && Integer.parseInt(s[1]) + 1 == my_number) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean isBallMyTeam() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("M")) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean isBallOtherTeam() {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			if (s[0].equals("O")) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean getPlayerHasBall(int num) {
		if (have_ball.equals("NONE")) {
			return false;
		} else {
			String[] s = have_ball.split("/");
			return Integer.parseInt(s[1]) + 1 == num;
		}
	}
	
	public Position getMyPosition() {
		return my_team[my_number];
	}
	
	public Position[] getMy_team() {
		return my_team;
	}

	public void setMy_team(Position[] my_team) {
		this.my_team = my_team;
	}

	public Position[] getOther_team() {
		return other_team;
	}

	public void setOther_team(Position[] other_team) {
		this.other_team = other_team;
	}

	public Position getBall() {
		return ball;
	}

	public void setBall(Position ball) {
		this.ball = ball;
	}

	public static int getMaxPlayers() {
		return MAX_PLAYERS;
	}

	public int getMy_number() {
		return my_number;
	}

	public void setMy_number(int my_number) {
		this.my_number = my_number;
	}

	public String getHave_ball() {
		return have_ball;
	}

	public void setHave_ball(String have_ball) {
		this.have_ball = have_ball;
	}

	public void setGoals(boolean team_a) {
		if (team_a) {
			my_goal = new Position(0, GRID_SIZE_Y/2);
			other_goal = new Position(GRID_SIZE_X - 1, GRID_SIZE_Y/2);
		} else {
			my_goal = new Position(GRID_SIZE_X - 1, GRID_SIZE_Y/2);
			other_goal = new Position(0, GRID_SIZE_Y/2);
		}
	}

	public Position getMy_goal() {
		return my_goal;
	}

	public Position getOther_goal() {
		return other_goal;
	}

	
}
