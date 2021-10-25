package projectone;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level3 extends BasicGameState {
	Player player;
	P1Game p1;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		// TODO Auto-generated method stub
		Input input = container.getInput();

		listenForCheatCode(input,game);
	}
	
	private void listenForCheatCode(Input input, StateBasedGame game)
	{
		if(input.isKeyDown(Input.KEY_Q)) 
		{
			p1.enterState(P1Game.GAMEOVERSTATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
