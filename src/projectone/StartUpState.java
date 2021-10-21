package projectone;

import jig.ResourceManager;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class StartUpState extends BasicGameState
{
	P1Game p1;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException 
	{
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) 
	{
		container.setSoundOn(true);
		p1 = (P1Game)game;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException 
	{
	
		P1Game p1 = (P1Game)game;
		
		//TODO add splash here
	
		/* g.drawImage(ResourceManager.getImage(BounceGame.STARTUP_BANNER_RSC),
				225, 270);		
		g.drawImage(ResourceManager.getImage(BounceGame.SPLASH_RSC),
				250,330);
		g.drawImage(ResourceManager.getImage(BounceGame.CONTROLS_RSC),
				10,20); */
			
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException 
	{
		Input input = container.getInput();
		
		if (input.isKeyDown(Input.KEY_SPACE))
		  p1.enterState(P1Game.lEVEL1);	
   		  p1.player.update(delta);
	}
	
	
	@Override
	public int getID() 
	{
		return P1Game.STARTUPSTATE;  // resource manager.
	}
}
