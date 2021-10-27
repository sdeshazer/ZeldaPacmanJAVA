package projectone;

import jig.ResourceManager;
import jig.Shape;
import jig.Vector;

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

public class Level1 extends BasicGameState {

	P1Game p1;

	Potions potions = new Potions();

	private int mazewidth;
	private int mazeheigth;
	
//	private int playerStartY = 23;
//	private int playerStartX = 14;
	
	private int playerStartY = 23;
	private int playerStartX = 14;

	private float inputDelay = 100;
	private float inputCoolDown; 

	public int monsterflag;
	
	enum Moves 
	{
		UP, 
		DOWN,
		LEFT,
		RIGHT
	}
	
	private Moves direction;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) 
	{
		p1 = (P1Game) game;
		
		p1.nextnode = 0;
		p1.previousnode = 0;

		
		container.setSoundOn(true);
		// TODO Add sound here
		mazewidth = p1.MAZEWIDTH;
		mazeheigth = p1.MAZEHEIGHTH;

		// set initial player placement:
		Vector startPosition = Player.convertTileToPos(playerStartY, playerStartX);
		p1.player = new Player(startPosition.getX(), startPosition.getY(), 0, 0);
		p1.player.tileY = playerStartY;
		p1.player.tileX = playerStartX;
		// set initial green monster placement:
		Monster monsterG = new Monster(232, 232);

	}

	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
		// drawing stumps here
		g.drawString("Score: " + p1.score, 300, 30);
		Circle circle = new Circle(1, 1, 1);
		for (int i = 0; i < mazeheigth; i++) 
		{
			for (int j = 0; j < mazewidth; j++) 
			{
				if (p1.maze[i][j] == 1) 
				{
					g.drawImage(ResourceManager.getImage(P1Game.STUMP_NODE).getScaledCopy(16, 16), (j * 16),
							(i * 16));
				}
			}
		}

		for (int i = 0; i < mazeheigth; i++) 
		{
			for (int j = 0; j < mazewidth; j++) 
			{
				if (p1.maze[i][j] == 2) 
				{

					g.drawImage(ResourceManager.getImage(P1Game.POTION).getScaledCopy(16, 16), (j * 16),
							(i * 16));
				}
			}
		}
		for (int i = 0; i < mazeheigth; i++) 
		{
			for (int j = 0; j < mazewidth; j++) 
			{
				if (p1.maze[i][j] == 0) 
				{
					g.draw(circle);
				}
			}
		}
		g.drawImage(ResourceManager.getImage(P1Game.PLAYER).getScaledCopy(16, 16), p1.player.getX(), p1.player.getY());
		g.drawImage(ResourceManager.getImage(P1Game.MONSTERG).getScaledCopy(16, 16), 232, 232);
	}

	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		
		Input input = container.getInput();
		p1 = (P1Game) game;

		listenForCheatCode(input, game);
		// TODO player controls:
		if(inputCoolDown <= 0) 
		{
			Boolean moved = false;
			if(input.isKeyDown(Input.KEY_W)) 
			{
				moved = determineMove(Moves.UP);
			}
			else if(input.isKeyDown(Input.KEY_S)) 
			{
				moved = determineMove(Moves.DOWN);
			}
			else if(input.isKeyDown(Input.KEY_A)) 
			{
				moved = determineMove(Moves.LEFT);
			}
			else if(input.isKeyDown(Input.KEY_D)) 
			{
				moved = determineMove(Moves.RIGHT);
			}
			
			if(moved) 
			{
				inputCoolDown = inputDelay;
				checkCollectibles(p1.player.tileY, p1.player.tileX);
			}
		}
		else
		{
			inputCoolDown -= delta;
		}
	}
	
	
	private void listenForCheatCode(Input input, StateBasedGame game) 
	{
		if (input.isKeyDown(Input.KEY_Q)) 
		{
			p1.enterState(P1Game.LEVEL2);
		}
	}
	
	private void checkCollectibles(int newTileY, int newTileX) 
	{
		int currentTile = p1.maze[newTileY][newTileX];
		
		switch(currentTile) 
		{
		case 0:
			p1.score += 10;
			p1.maze[newTileY][newTileX] = -1;
			break;
		case 2:
			//TODO
			System.out.println("TODO Collectible");
			p1.maze[newTileY][newTileX] = -1;
			break;
		case 6:
			p1.enterState(P1Game.LEVEL2);
			break;
		}
	}

	
	private boolean checkCollision(int newTileY, int newTileX) 
	{
		if(p1.maze[newTileY][newTileX] == 1)
			return true;
		return false;
	}
	
	
	private boolean determineMove(Moves direction) 
	{
		int newTileX = p1.player.tileX;
		int newTileY = p1.player.tileY; 
		
		switch (direction) 
		{
		case UP: 
			newTileY -=1;
			break;
		case DOWN: 
			newTileY +=1;
			break;
		case LEFT:
			newTileX -=1;
			break;
		case RIGHT:
			newTileX +=1;
			break;
		default:
			System.out.println("Error");
			break;
		}

		if(!checkCollision(newTileY, newTileX)) 
		{
			p1.player.movePlayer(newTileY, newTileX);
			return true;
		}
		return false;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return P1Game.lEVEL1;
	}

}
