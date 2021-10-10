package projectone;

import jig.ResourceManager;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class StartUpState extends BasicGameState{

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		Game p1 = (Game)game;

		/* g.drawImage(ResourceManager.getImage(BounceGame.STARTUP_BANNER_RSC),
				225, 270);		
		g.drawImage(ResourceManager.getImage(BounceGame.SPLASH_RSC),
				250,330);
		g.drawImage(ResourceManager.getImage(BounceGame.CONTROLS_RSC),
				10,20); */
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		Game p1 = (Game)game;
		
		//if (input.isKeyDown(Input.KEY_SPACE))
		//	p1.enterState(Game.PLAYINGSTATE);	
		
		//p1.ball.update(delta);

	}
	
	
	@Override
	public int getID() {
		//return Game.STARTUPSTATE;  // resource manager.
		return 0;
	}
}
