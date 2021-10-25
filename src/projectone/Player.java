package projectone;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class Player extends Entity{
	
	private Vector velocity;
	
	public Player(final float x, final float y, final float vx, final float vy) {
		super(x,y);
		velocity = new Vector(vx,vy);
		//TODO add sprite sheets 
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.PLAYER));	
	}
	
	public void setVelocity(final Vector v) {	
		velocity = v;
	}
	
	public void moveleft(final int delta, float x, float y, Player player) {
		this.velocity = new Vector(-0.5f,0);
		this.update(delta);	
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta)); 
		
	}
}
