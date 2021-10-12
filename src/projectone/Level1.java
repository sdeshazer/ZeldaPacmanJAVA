package projectone;

import jig.ResourceManager;
import jig.Vector;

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


public class Level1 extends BasicGameState {

	Player player;
	Game p1;
	
	private int mazewidth;
	private int mazeheigth;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
			
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		p1 = (Game)game;
		mazewidth = p1.MAZEWIDTH;
		mazeheigth = p1.MAZEHEIGHTH;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
		
		for(int i=0;i<mazeheigth;i++){		
			for(int z=0;z < mazewidth; z++){
				if(p1.maze[i][z]==1){
					g.drawImage(ResourceManager.getImage(Game.STUMP_NODE),48, 48);
				}
			}
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
