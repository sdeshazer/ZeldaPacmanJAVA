package projectone;

import jig.ResourceManager;
import jig.Shape;
import jig.Vector;
import projectone.P1Game.Moves;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.tiled.TiledMap;


public class Level1 extends BasicGameState 
{

	Player player;
	P1Game p1;
	
	
	Potions potions = new Potions();
	
	private int mazewidth;
	private int mazeheigth;
	
	int currentnodex; 
	int currentnodey;	
	int nextnode; 
	
	int tileup = 0;
	int tiledown = 0;
	int tileright = 0;
	int tileleft = 0;
	int tilesvisited = 0;
	
	int nodewidth = 16;
	int nodeheight = 16;
	int tilex;
	int tiley;
	int nexttilex = 0;
	int nexttiley = 0;
	int prevtilex = 0;
	int prevtiley = 0;
	int check;
	int secondcheck=-1;

	int state = 0;
	int move = 0;
	int wrapflag=0;

	
	int flag = 0;
	int current = 0;
	int currentkey = 3;
	float playermovex; // set these
	float playermovey;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
	
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) 
	{
		p1 = (P1Game)game;
		//currentkey =3;
		current = 0;
		p1.nextnode = 0;
		p1.previousnode = 0;
		playermovex = 232;
		playermovey = 424;

	//	p1.health = 3; 
		container.setSoundOn(true);
		//TODO Add sound here
		mazewidth = p1.MAZEWIDTH;
		mazeheigth = p1.MAZEHEIGHTH;
		currentnodex = 14;
		currentnodey = 23;
		tilex = currentnodex;
		tiley = currentnodey;
		
		tileup=0;
		tileleft=0;
		tileright=0;
		tiledown=0;
		p1.player.setX(playermovex);
		p1.player.setY(playermovey);
		nexttilex = 0;
		nexttiley = 0;
		check = 0;
		current = 0;
		move = 0;
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
		// drawing stumps here
		g.drawImage(ResourceManager.getImage(P1Game.DEBUG),0, 48);	
		g.drawString("Score: " + p1.score , 300, 30);
		Circle circle = new Circle(1, 1, 1);  
		for(int i=0;i<mazeheigth;i++){		
			for(int j=0;j < mazewidth; j++){
				if(p1.maze[i][j]==1){
					g.drawImage(ResourceManager.getImage(P1Game.STUMP_NODE).getScaledCopy(16, 16), (j*16) + 8, (i*16)+50);
				}
			}
		}
	
		for(int i = 0; i<mazeheigth;i++) {
			for(int j = 0; j <mazewidth; j++ ) {
				if(p1.maze[i][j] == 2) {
					g.drawImage(ResourceManager.getImage(P1Game.POTION).getScaledCopy(16, 16), (j*16) +8, (i*16)+50);
				}
			}
		}	
		for (int i = 0; i < mazeheigth; i++ ) {
			for(int j = 0; j < mazewidth; j++) {
				if(p1.maze[i][j] == 0) {
				g.draw(circle);
				}
			}
		}
		g.drawImage(ResourceManager.getImage(P1Game.PLAYER).getScaledCopy(16, 16),playermovex, playermovey);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		p1 = (P1Game)game;	
		
		currentnodex = (int)((p1.player.getX() - 8/16));
		currentnodey = (int)((p1.player.getY() - 56/16));
		
		
		
		tileup=currentnodey-1;
		tiledown=currentnodey+1;
		tileright=currentnodex+1;
		tileleft=currentnodex;
		
		
		listenForCheatCode(input, game);
		// TODO player controls:
		if(state == 0) {
			if(input.isKeyDown(Input.KEY_D))
			{
				System.out.println("player move right");
				p1.move = Moves.RIGHT;
				p1.nextnode= 4;
				this.tryMove(4);
			}
			else if(currentkey == 4) {
				this.tryMove(4);
			}
			if(input.isKeyDown(Input.KEY_S))
			{
				System.out.println("player move down");
				p1.move = Moves.DOWN;
				p1.nextnode= 2;
				this.tryMove(2);
			}else if(currentkey == 2) {
				this.tryMove(2);
			}
			if(input.isKeyDown(Input.KEY_W))
			{
				System.out.println("player move up");
				p1.move = Moves.UP;
				p1.nextnode= 1;
				this.tryMove(1);
			}else if(currentkey == 1) {
				this.tryMove(1);
			}
			if(input.isKeyDown(Input.KEY_A)) 
			{
				System.out.println("player move left");
				p1.move = Moves.LEFT;
				p1.nextnode= 3;
				this.tryMove(3);
			}else if(currentkey == 3) {
				this.tryMove(3);
			}	
		}
		//TODO optimize:
		if(state == 1){ // negative x direction
			if(check > 0) {
				System.out.println("neg x");

				//TODO adjust:
				playermovex -=1;
				p1.player.setX(playermovex);
				p1.maze[tiley][tilex] = -1;
				check -= 1;
				if(check == 0 && tilesvisited == 0) {
					p1.score += 100;
				}else if(check == 0 && tilesvisited == 2) {
					//TODO set a state for monsters here.
					//possibly a flag or power up
				}else if(check == 0 && tilesvisited == 8) {
					// TODO check if this case even makes sense
				}
			}
			else {
				// reset state and go to next horizontal tile:
				state = 0;
				tilex = nexttilex;
			}
		}
		if(state == 2) {  // positive x direction
			if(check > 0) {
				System.out.println("pos x");
				//TODO adjust:
				playermovex +=1;
				p1.player.setX(playermovex);
				p1.maze[tiley][tilex] = -1;
				check -=1;
				if(check == 0 && tilesvisited == 0) {
					//TODO set a state for player here
				}else if(check == 0 && tilesvisited == 2) {
					//TODO set a state for monsters here
					//possibly a flag or power up
				}else if (check ==0 && tilesvisited == 8) {
					p1.score += 500;
					//TODO check if this is even a possible case
				}
			}else {
				// reset state and go to next horizontal tile:
				state = 0;
				tilex = nexttilex;
			}
		}
		if(state == 3) {  // negative y direction
			if(check > 0 ) {
				System.out.println("neg y");

				playermovey -=1;
				p1.player.setY(playermovey);
				p1.maze[tiley][tilex] = -1;
				check -=1;
				if(check == 0 && tilesvisited == 0) {
					p1.score += 100;
				}else if (check == 0 && tilesvisited == 2) {
					//TODO add a state for monsters here
					//possibly a flag or power up
				}else if (check == 0 && tilesvisited == 8) {
				p1.score += 500;
				//TODO check if this is even a possible case
				}
			}
			else {
				state = 0;
				tiley = nexttiley;
			}
		}
		if(state == 4) {  // positive y direction
			if(check > 0) {
				System.out.println("pos y");

				playermovey += 1;
				p1.player.setY(playermovey);
				p1.maze[tiley][tilex] = -1;
				check -=1;
				if(check == 0 && tilesvisited == 0) {
					p1.score += 100;
				}else if(check == 0 && tilesvisited == 2) {
					// TODO add a state for monsters here
					// possibly a power up
				}else if (check == 0 && tilesvisited == 8) {
					p1.score += 500;
					//TODO check if this is even a possible case
				}
				
			}else {
				// reset state and go to next horizontal tile:
				state = 0;
				tiley = nexttiley;
			}
		}
		if(state == 5) {
			if(check > 0) {
				playermovex += 1;
				check -=1;
				p1.player.setX(0);
				tilex = 2;
				tiley = 14;
			}// TODO check line 726 - > 744
		}
		if(state == 6) {
			if(check > 0) {
				playermovex -= 1;
				p1.player.setX(playermovex);
				tilex = 25;
				tiley = 14;
				check -=1;
			}// TODO repeat above
			
		}
	}
	
	
	
	private void listenForCheatCode(Input input, StateBasedGame game)
	{
		if(input.isKeyDown(Input.KEY_Q)) 
		{
			p1.enterState(P1Game.lEVEL2);
		}
	}


	private void tryMove(int move) 
	{
		if(move == 3) // moves left
		{
			System.out.println("move = 3");

			if(p1.maze[tiley][tilex] == 6)
			{
				System.out.println("maze x y  = 6");
				flag = 6;
				check = 20;
				wrapflag = 0;
				return;
			}
			if(p1.maze[tiley][tilex-1] != 1)
			{
				state = 1;
				tilesvisited = p1.maze[tiley][tilex-1];
				nexttilex = tilex - 1;
				nexttiley = tiley;
				currentkey = 3;
				check = 10;
				current = 3;
				return; 
			}
			else if(current == 3) {
				return;
			}
			return; 
		}
		if(move == 1) {
			if(p1.maze[tiley-1][tilex]!=1) {
				state = 3;
				tilesvisited = p1.maze[tiley-1][tilex];
				nexttilex = tilex;
				nexttiley = tiley - 1;
				currentkey = 1; 
				check = 10;
				current = 1;
				return;
			}
			else if(current == 1) {
				return;
			}
			return;
		}
		if(move ==2) {
			if(p1.maze[tiley+1][tilex] != 1) {
				state = 4;
				tilesvisited = p1.maze[tiley+1][tilex];
				nexttilex = tilex;
				nexttiley = tiley+1;
				currentkey = 2;
				check = 10;
				current = 2;
				return;
			}else if(current == 2) {
				return;
			}
			return; 
		}
		if(move == 4) { // move right
			if(p1.maze[tiley][tilex] == 5) {
				System.out.println("MADE IT");
				state = 5;
				check = 20;
				return;
			}
			if(p1.maze[tiley][tilex+1] != 1) {
				state = 2;
				tilesvisited = p1.maze[tiley][tilex+1];
				nexttilex = tilex + 1;
				nexttiley = tiley; 
				currentkey = 4;
				check = 10;
				current = 4;
				return;
			}
			else if (current == 4) {
				return;
			}
			return;
		}
		return;
	}
	
	private void determinePath(int state) {
		
		switch(state) {
		case 0: 
			break;
		case 1: // moved left
			break;
		case 2: // 
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		default:
			System.out.println("Error");
			break;
		}
		
		
	}
	
	@Override
	public int getID() 
	{
		// TODO Auto-generated method stub
		return  P1Game.lEVEL1;
	}

	
}
