package Logic;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;


public class Player {
	
	//private static final int PLAYER_SIZE = 4;
	private EnvironmentInformation env;
	private int pass;
	private int desm;
	private boolean defending;
	private boolean incorrect;
	private Random random;
	
		
	public Player(EnvironmentInformation env) {
		this.env = env;
		this.pass = 0;
		this.desm = 3;
		this.incorrect = false;
		random = new Random();
	}
	
		
	private Action moveToBall() {
		return Action.getMoveAction(env.getBall().getX(), env.getBall().getY());
	}
	
	private boolean iAmTheClosestToBall() {
		Position player = env.getMyPosition();
		double mydist = Math.sqrt (Math.pow (env.getBall().getX() - player.getX(), 2) + Math.pow (env.getBall().getY() - player.getY(), 2));
		for (int i = 0; i < EnvironmentInformation.getMaxPlayers(); i++) {
			if (i != env.getMy_number() && env.getMy_team()[i].getX() > -1) {
				double dist = Math.sqrt (Math.pow (env.getBall().getX() - env.getMy_team()[i].getX(), 2) + Math.pow (env.getBall().getY() - env.getMy_team()[i].getY(), 2));
				if (dist < mydist) {
					return false;
				}
			}
		}
		return true;
	}
	
	private Action shoot() {
		Position player = env.getMyPosition();
		
		if (passPossible(player.getX(), player.getY(), env.getOther_goal().getX(), env.getOther_goal().getY())) {
			double dist = Math.sqrt (Math.pow (env.getOther_goal().getX() - player.getX(), 2) + Math.pow (env.getOther_goal().getY() - player.getY(), 2));
			if (dist < EnvironmentInformation.GRID_SIZE_X / 4) {
				return Action.getShootAction(env.getOther_goal().getX(), env.getOther_goal().getY());
			}
		}
		return null;		
	}

	
	private Position offTheBall() {
		double[][] distances = new double[EnvironmentInformation.GRID_SIZE_X][EnvironmentInformation.GRID_SIZE_Y];
		double dist;
		Position zonaP = new Position();
		Position zonaD = new Position();
		Position zonaA1 = new Position();
		Position zonaA2 = new Position();
		double distZonaP = 0.0, distZonaD = 0.0, distZonaA1 = 0.0, distZonaA2 = 0.0;
		int[] positions = new int[EnvironmentInformation.getMaxPlayers()];

		for (int x = 1; x < EnvironmentInformation.GRID_SIZE_X - 1; x++) {
			for( int y = 1; y < EnvironmentInformation.GRID_SIZE_Y - 1; y++) {
				distances[x][y] = Double.MAX_VALUE;

				for (int n = 0; n < EnvironmentInformation.getMaxPlayers(); n++) {
					//Calculem la distancia de la posicio a tots els rivals.
					if (env.getOther_team()[n].getX() != -1) {
						dist = Math.sqrt (Math.pow(env.getOther_team()[n].getX() - x, 2) + Math.pow(env.getOther_team()[n].getY() - y, 2));
						if( dist < distances[x][y] ) {
							distances[x][y] = dist;
						}
					}
					
					//Calculem la distancia de la posicio a tots els meus companys.
					if (env.getMy_team()[n].getX() != -1 && n != env.getMy_number()) {
						dist = Math.sqrt (Math.pow(env.getMy_team()[n].getX() - x, 2) + Math.pow(env.getMy_team()[n].getY() - y, 2));
						if (dist < distances[x][y] ) {
							distances[x][y] = dist;
						}
					}
				}
				 
				//Calculem la distancia de la posicio al limit nord.
				dist = Math.abs(1 - y);
				if( dist < distances[x][y] ) {
					distances[x][y] = dist;
				}
				//Calculem la distancia de la posicio al limit sud.
				dist = Math.abs(EnvironmentInformation.GRID_SIZE_Y-2 - y);
				if( dist < distances[x][y] ) {
					distances[x][y] = dist;
				}
				//Calculem la distancia de la posicio al limit est.
				dist = Math.abs(EnvironmentInformation.GRID_SIZE_X-1 - x);
				if( dist < distances[x][y] ) {
					distances[x][y] = dist;
				}
				//Calculem la distancia de la posicio al limit oest.
				dist = Math.abs(1 - x);
				if( dist < distances[x][y] ) {
					distances[x][y] = dist;
				}
				//Calculem la posicio mes desmarcada de la zonaPivot/zonaDefensa.
				if (x < EnvironmentInformation.GRID_SIZE_X / 2 && distances[x][y] > distZonaD ) {
					distZonaD = distances[x][y];
					zonaD.setX(x);
					zonaD.setY(y);
				}
				else if(x > EnvironmentInformation.GRID_SIZE_X / 2 && distances[x][y] > distZonaP) {
					distZonaP = distances[x][y];
					zonaP.setX(x);
					zonaP.setY(y);
				}
				//Calculem la posicio mes desmarcada de la zonaAla1
				else if(y < EnvironmentInformation.GRID_SIZE_Y / 2 - 1 && distances[x][y] > distZonaA1 ) {
					distZonaA1 = distances[x][y];
					zonaA1.setX(x);
					zonaA1.setY(y);
				}
				//Calculem la posicio mes desmarcada de la zonaAla1
				else if(y > EnvironmentInformation.GRID_SIZE_Y / 2  - 1 && distances[x][y] > distZonaA2 ) {
					distZonaA2 = distances[x][y];
					zonaA2.setX(x);
					zonaA2.setY(y);
				}
			}
		}
			
		//Inicialitzem les posicions assignades a cada company d'equip.
		for (int i = 0; i < EnvironmentInformation.getMaxPlayers(); i++) {
			positions[i] = -1;
		}
		double min = 99999.9;
		int jug = -1;
		//Assignem jugador a la posicio pivot(1).
		for (int i = 1; i < EnvironmentInformation.getMaxPlayers(); i++) {
			double d = Math.sqrt( Math.pow(env.getMy_team()[i].getX() - zonaP.getX(),2) + Math.pow(env.getMy_team()[i].getY() - zonaP.getY(),2) );
			if( d < min && positions[i]==-1 ) {
				min = d;
				jug = i;
			}
		}
		if (jug == env.getMy_number()) {
			return zonaP;
		}
		positions[jug] = 1;
		min = 99999.9;
		jug = -1;
		
		//Assignem jugador a la posicio defensa(2).
		for( int i=1; i<EnvironmentInformation.getMaxPlayers(); i++ ) {
			double d = Math.sqrt( Math.pow(env.getMy_team()[i].getX() - zonaD.getX(),2) + Math.pow(env.getMy_team()[i].getY() - zonaD.getY(),2) );
			if( d < min && positions[i]==-1 ) {
				min = d;
				jug = i;
			}
		}
		if (jug == env.getMy_number()) {
			return zonaD;
		}
		positions[jug] = 2;
		min = 99999.9;
		jug = -1;
		//Assignem jugador a la posicio ala1(3).
		for( int i=1; i<EnvironmentInformation.getMaxPlayers(); i++ ) {
			double d = Math.sqrt( Math.pow(env.getMy_team()[i].getX() - zonaA1.getX(),2) + Math.pow(env.getMy_team()[i].getY() - zonaA1.getY(),2) );
			if( d < min && positions[i]==-1 ) {
				min = d;
				jug = i;
			}
		}
		if (jug == env.getMy_number()) {
			return zonaA1;
		}
		positions[jug] = 3;
		min = 99999.9;
		jug = -1;
		//Assignem jugador a la posicio ala2(4).
		for( int i=1; i<EnvironmentInformation.getMaxPlayers(); i++ ) {
			double d = Math.sqrt( Math.pow(env.getMy_team()[i].getX() - zonaA2.getX(),2) + Math.pow(env.getMy_team()[i].getY() - zonaA2.getY(),2) );
			if( d < min && positions[i]==-1 ) {
				min = d;
				jug = i;
			}
		}
		positions[jug] = 4;
		if (jug == env.getMy_number()) {
			return zonaA2;
		}
		return zonaP;
	}	

	private Position selectCover2() {
		double x, y, d;
		double distCovers[][] = new double[EnvironmentInformation.getMaxPlayers()][EnvironmentInformation.getMaxPlayers()];
		int priCover[] = new int[EnvironmentInformation.getMaxPlayers()];
		Position covers[] = new Position[EnvironmentInformation.getMaxPlayers()];
		
		for (int i = 0; i < EnvironmentInformation.getMaxPlayers(); i++) {
			x = env.getMy_goal().getX() - env.getOther_team()[i].getX();
			y = env.getMy_goal().getY() - env.getOther_team()[i].getY();
			d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			
			covers[i] = new Position(env.getOther_team()[i].getX() + (int)(Math.round(x / d * 5.0)), env.getOther_team()[i].getY() + (int)(Math.round(y / d * 5.0)));
			
			for (int j = 0; j < EnvironmentInformation.getMaxPlayers(); j++) {
				distCovers[i][j] = Math.sqrt(Math.pow(env.getMy_team()[j].getX() - covers[i].getX(), 2) + Math.pow(env.getMy_team()[j].getY() - covers[i].getY(), 2));	
			}
			priCover[i] = -1;
		}
		
		for (int i = 1; i < EnvironmentInformation.getMaxPlayers(); i++) {
			double min = Double.MAX_VALUE;
			int pos = 0;
			if (env.getOther_team()[i].getX() != -1) {
				for (int j = 1; j < EnvironmentInformation.getMaxPlayers(); j++) {
					if (distCovers[i][j] < min && priCover[j] == -1) {
						pos = j;
						min = distCovers[i][j];
					}
				}
				priCover[pos] = i;
			}
		}
				
		return covers[priCover[env.getMy_number()]];			
	} 
	
	private Position openPass (int number) {
		Position p = new Position(-1, -1);
		Position[] team = env.getMy_team();
		Position goal = env.getOther_goal();
		double min_dist = Double.MAX_VALUE;
		double dist;
		
		for (int i = 1; i < EnvironmentInformation.getMaxPlayers(); i++) {
			if (i != number && team[i].getX() != -1 && 
				!isCovered(team[number].getX(), team[number].getY(), team[i].getX(), team[i].getY())) {
				dist = Math.sqrt (Math.pow (goal.getX() - team[i].getX(), 2) + Math.pow (goal.getY() - team[i].getY(), 2));
				if (dist < min_dist) {
					p = team[i];
				}
			}
		}
		
		return p;
	}
	
	private boolean isCovered (int my_x, int my_y, int pos_x, int pos_y) {
		if (passPossible(pos_x, pos_y, env.getOther_goal().getX(), env.getOther_goal().getY()) &&
			passPossible(pos_x, pos_y, my_x, my_y)) {
			return false;
		}
		return true;
	}
	

	private Position choosePassTarget() {
		Position p = new Position(-1, -1);
		double dist = Double.MAX_VALUE;
		
		Position player = env.getMyPosition();
		for (int i = 0; i < EnvironmentInformation.getMaxPlayers(); i++) {
			if (i != env.getMy_number() && passPossible(player.getX(), player.getY(), env.getMy_team()[i].getX(), env.getMy_team()[i].getY())) {
				double distTarg = Math.sqrt (Math.pow (env.getOther_goal().getX() - env.getMy_team()[i].getX(), 2) + Math.pow (env.getOther_goal().getY() - env.getMy_team()[i].getY(), 2));
				if( distTarg < dist ) {
					p.setX(env.getMy_team()[i].getX());
					p.setY(env.getMy_team()[i].getY());
					dist = distTarg;
				}
			}
		}	
		return p;
	}
	
	private Action passBall (int x, int y) {
		return Action.getPassAction(x, y);
	}
	
	private boolean passPossible (int posX, int posY, int dx, int dy) {
		int x = posX;
		int y = posY;
		double distTarg = Double.MAX_VALUE, distEne;

		while (x != dx && y != dy && distTarg > 3.0) {
			System.out.println("X="+x+", Y="+y);
			distTarg = Math.sqrt (Math.pow (dx - x, 2) + Math.pow (dy - y,2));
			x += (int)(Math.round((dx - x) / distTarg));
			y += (int)(Math.round((dy - y) / distTarg));
			
			for (int i = 1; i < EnvironmentInformation.getMaxPlayers(); i++) {
				distEne = Math.sqrt (Math.pow (env.getOther_team()[i].getX() - x, 2) + Math.pow (env.getOther_team()[i].getY() - y, 2));
				System.out.println("Dist "+i+": "+distEne);
				if( distEne <= 5.0 ) return false;
			}
		}
		return true;	
	
	}
	
	public Action decide() {
		Action action;
		
		if (incorrect) {
			incorrect = false;
			return move_random();
		}
		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (env.isBallMine()) {
			defending = false;
			//Si estic en condicions de xutar, xuto.
			action = shoot();
			if (action != null) {
				System.out.println("Shooting.");
				return action;
			}
			//Si no xuto.
			else {
				//Incremento el comptador de temps per passar la pilota.
				pass++;
				//Miro si tinc via lliure cap a la porteria rival.
				System.out.println("Check to goal.");
				if (passPossible(env.getMyPosition().getX(), env.getMyPosition().getY(), 
					env.getOther_goal().getX(), env.getOther_goal().getY())) {
					System.out.println("To goal!");
					try {
						System.in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Action.getMoveAction(env.getOther_goal().getX(), env.getOther_goal().getY());
				}
				//Miro si algun company d'equip té via lliure cap a la porteria rival.
				Position p = openPass(env.getMy_number());
				if (p.getX() != -1) {
					System.out.println("Open pass.");
					return passBall(p.getX(), p.getY());
				}
				//Miro si toca passar la pilota.
				if (pass >= 7) {
					p = choosePassTarget();
					pass = 0;
					desm = 3;
					System.out.println("Passing.");
					return passBall(p.getX(), p.getY());
				} 
				//Si tot el demés falla, em desmarco.
				p = offTheBall();
				System.out.println("OffTheBall.");
				return Action.getMoveAction(p.getX(), p.getY());
			}
		} else if (env.isBallMyTeam()) {
			/*if (desm > 0) {
				desm--;
				return Action.getMoveAction(env.getOther_goal().getX(), env.getOther_goal().getY());
			}*/
			defending = false;
			Position p = offTheBall();
			//System.out.println("Move Offball -> ("+p.getX()+","+p.getY()+")");
			return Action.getMoveAction(p.getX(), p.getY());
		} else if (env.isBallOtherTeam()) {
			defending = true;
			Position p = selectCover2();
			//System.out.println("Move Cover -> ("+p.getX()+","+p.getY()+")");
			return Action.getMoveAction(p.getX(), p.getY());
		} else {
			if (iAmTheClosestToBall()) {
				//System.out.println("Move -> to Ball");
				return moveToBall();
			}
			else {
				if (!defending) {
					Position p = offTheBall();
					//System.out.println("Move OfftheBall-> ("+p.getX()+","+p.getY()+")");
					return Action.getMoveAction(p.getX(), p.getY());
				}
				//Si estava defensant, cobreixo els rivals.
				else {
					Position p = selectCover2();
					//System.out.println("Move Cover -> ("+p.getX()+","+p.getY()+")");
					return Action.getMoveAction(p.getX(), p.getY());
				}
			}
		}	
	} //Fi funcio run.


	private Action move_random() {
		Position p = env.getMyPosition();
		switch (random.nextInt(8)) {
			case 0:
				return Action.getMoveAction(p.getX(), p.getY()-1);
			case 1:
				return Action.getMoveAction(p.getX()+1, p.getY()-1);
			case 2:
				return Action.getMoveAction(p.getX()+1, p.getY());
			case 3:
				return Action.getMoveAction(p.getX()+1, p.getY()+1);
			case 4:
				return Action.getMoveAction(p.getX(), p.getY()+1);
			case 5:
				return Action.getMoveAction(p.getX()-1, p.getY()+1);
			case 6:
				return Action.getMoveAction(p.getX()-1, p.getY());
			case 7:
				return Action.getMoveAction(p.getX()-1, p.getY()-1);
		}
		return null;
	}


	public void setIncorrect(boolean b) {
		incorrect = b;
	}
}