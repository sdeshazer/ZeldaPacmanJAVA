package projectone;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import projectone.GameOverState;
import projectone.PlayingState;
import projectone.SceneTransition;
import projectone.StartUpState;


public class Game extends StateBasedGame{
	
	public final int ScreenWidth;
	public final int ScreenHeight;

	public Game(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;		
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		addState(new SceneTransition());
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Project One", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}	
}
