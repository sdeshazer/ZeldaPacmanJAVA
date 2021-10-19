package projectone;

import jig.ResourceManager;
import jig.Vector;
import projectone.P1Game.Moves;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	
	int tileup;
	int tiledown;
	int tileright;
	int tileleft;
	
	int nodewidth = 16;
	int nodeheight = 16;
	int tilex;
	int tiley;
	
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
		container.setSoundOn(true);
		//TODO Add sound here
		mazewidth = p1.MAZEWIDTH;
		mazeheigth = p1.MAZEHEIGHTH;
		currentnodex = 14;
		currentnodey = 23;
		tilex = currentnodex;
		tiley = currentnodey;
		
		playermovex = 232;
		playermovey = 424;
		p1.player.setX(playermovex);
		p1.player.setY(playermovey);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
		// drawing stumps here
	//	g.drawImage(ResourceManager.getImage(P1Game.MAZE),0, 48);	
		for(int i=0;i<mazeheigth;i++){		
			for(int j=0;j < mazewidth; j++){
				if(p1.maze[i][j]==1){
					g.drawImage(ResourceManager.getImage(P1Game.STUMP_NODE).getScaledCopy(16, 16), (j*16) +8, (i*16)+50);
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
		g.drawImage(ResourceManager.getImage(P1Game.PLAYER).getScaledCopy(18, 18),playermovex, playermovey);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		p1 = (P1Game)game;
		
		
	
		
		tileup=currentnodey-1;
		tiledown=currentnodey+1;
		tileright=currentnodex+1;
		tileleft=currentnodex;

		listenForCheatCode(input, game);
		// TODO player controls:
		if(input.isKeyDown(Input.KEY_D))
		{
			System.out.println("player move right");
			p1.move = Moves.RIGHT;
		}
		if(input.isKeyDown(Input.KEY_S))
		{
			p1.move = Moves.DOWN;
		}
		if(input.isKeyDown(Input.KEY_W))
		{
			p1.move = Moves.UP;
		}
		if(input.isKeyDown(Input.KEY_A)) 
		{
			p1.move = Moves.LEFT;
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
		if(move == 3) 
		{
			System.out.println("move = 3");

			if(p1.maze[tilex][tiley] == 6)
			{
				System.out.println("maze x y  = 6");
				return;
			}
		}
		
	}
	@Override
	public int getID() 
	{
		// TODO Auto-generated method stub
		return  p1.lEVEL1;
	}

	
}
