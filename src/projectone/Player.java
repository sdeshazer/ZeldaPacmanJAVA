package projectone;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class Player extends Entity{
	
	private Vector velocity;
	
	public Player(final float x, final float y, final float vx, final float vy) {
		super(x,y);
		velocity = new Vector(vx,vy);
		addImageWithBoundingBox(ResourceManager.getImage(Game.PLAYER));
		
		
		
	}
	
	public void update(final int delta) {
		
		translate(velocity.scale(delta)); 
		
	}
}
