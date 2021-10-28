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

public class Level3 extends BasicGameState {
	
	private final String TAG = "Level2";

	P1Game p1;

	Potions potions = new Potions();

	private int mazewidth;
	private int mazeheigth;
	
	private int playerStartY = 23;
	private int playerStartX = 15;
	
	private int monsterStartY = 14;
	private int monsterStartX = 13;

	private int monster2StartY = 12;
	private int monster2StartX = 1;

	private int monster3StartY = 14;
	private int monster3StartX = 25;
	
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
	
		// TODO Add sound here
		mazewidth = p1.MAZEWIDTH;
		mazeheigth = p1.MAZEHEIGHTH;

		// set initial player placement:
		Vector startPosition = Player.convertTileToPos(playerStartY, playerStartX);
		Vector monsterStartPosition = Player.convertTileToPos(monsterStartY, monsterStartX);
		Vector monster2StartPosition = Player.convertTileToPos(monster2StartY, monster2StartX);
		Vector monster3StartPosition = Player.convertTileToPos(monster3StartY, monster3StartX);
		p1.player = new Player(startPosition.getX(), startPosition.getY(), 0, 0);
		p1.monster = new Monster(monsterStartPosition.getX(), monsterStartPosition.getY());
		p1.monster2 = new Monster(monster2StartPosition.getX(), monster2StartPosition.getY());
		p1.monster3 = new Monster(monster3StartPosition.getX(), monster3StartPosition.getY());
		p1.player.tileY = playerStartY;
		p1.player.tileX = playerStartX;
		p1.monster.currenttiley = monsterStartY;
		p1.monster.currenttilex = monsterStartX;
		p1.monster2.currenttiley = monster2StartY;
		p1.monster2.currenttilex = monster2StartX;
		p1.monster3.currenttiley = monster3StartY;
		p1.monster3.currenttilex = monster3StartX;
		p1.monster.inputDelay = 200;
		p1.monster2.inputDelay = 200;
		p1.monster3.inputDelay = 170;

	}

	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
		// drawing stumps here
		g.drawString("Score: " + p1.score, p1.ScreenWidth - 150, p1.ScreenHeight - 70);
		Circle circle = new Circle(1, 1, 1);
		for (int i = 0; i < mazeheigth; i++) 
		{
			for (int j = 0; j < mazewidth; j++) 
			{
				if (p1.maze3[i][j] == 1) 
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
				if (p1.maze3[i][j] == 2) 
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
				if (p1.maze3[i][j] == 6) 
				{
					g.drawImage(ResourceManager.getImage(P1Game.DOOR).getScaledCopy(16,16), (j * 16) , (i * 16));					
				}
			}
		}
		g.drawImage(ResourceManager.getImage(P1Game.PLAYER).getScaledCopy(16, 16), p1.player.getX(), p1.player.getY());
		g.drawImage(ResourceManager.getImage(P1Game.MONSTERG).getScaledCopy(16, 16), p1.monster.getX(), p1.monster.getY());
		g.drawImage(ResourceManager.getImage(P1Game.MONSTERG).getScaledCopy(16, 16), p1.monster2.getX(), p1.monster2.getY());
		g.drawImage(ResourceManager.getImage(P1Game.MONSTERG).getScaledCopy(16, 16), p1.monster3.getX(), p1.monster3.getY());
	}

	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		
		Input input = container.getInput();
		p1 = (P1Game) game;

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

				p1.monster.moveMonstertoPath(p1 , p1.maze3, p1.monster.currenttiley, p1.monster.currenttilex,
											p1.player.tileY, p1.player.tileX);
				p1.monster2.moveMonstertoPath(p1 , p1.maze3, p1.monster2.currenttiley, p1.monster2.currenttilex,
						p1.player.tileY, p1.player.tileX);
				p1.monster3.moveMonstertoPath(p1 , p1.maze3, p1.monster3.currenttiley, p1.monster3.currenttilex,
						p1.player.tileY, p1.player.tileX);
			}
		}
		else
		{
			inputCoolDown -= delta;
		}
		p1.monster.update(delta);
		p1.monster2.update(delta);
		p1.monster3.update(delta);
		checkIfDead();
	}
	
	
	
	
	private boolean checkIfDead()
	{
		if(p1.player.tileY == p1.monster.currenttiley && p1.player.tileX == p1.monster.currenttilex)
		{
			
			p1.enterState(P1Game.GAMEOVERSTATE);
			return true;
		}
		if(p1.player.tileY == p1.monster2.currenttiley && p1.player.tileX == p1.monster2.currenttilex)
		{
			p1.enterState(P1Game.GAMEOVERSTATE);
			return true;
		}
		if(p1.player.tileY == p1.monster3.currenttiley && p1.player.tileX == p1.monster3.currenttilex)
		{
			p1.enterState(P1Game.GAMEOVERSTATE);
			return true;
		}
		
		return false;
	}
	
	
	private void checkCollectibles(int newTileY, int newTileX) 
	{
		int currentTile = p1.maze3[newTileY][newTileX];
		
		
		switch(currentTile) 
		{
		case 0:
			p1.maze3[newTileY][newTileX] = -1;
			break;
		case 2:
			//TODO
			p1.score += 100;
			p1.maze3[newTileY][newTileX] = -1;
			break;
		case 6:
			p1.score += 500;
			p1.enterState(P1Game.LEVEL3);
			break;
		}
	}

	
	private boolean checkCollision(int newTileY, int newTileX) 
	{
		if(p1.maze3[newTileY][newTileX] == 1)
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
		return P1Game.LEVEL3;
	}

}
