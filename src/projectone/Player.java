package projectone;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class Player extends Entity{
	
	private Vector velocity;
	
	public void update(final int delta) {
		
		translate(velocity.scale(delta)); 
		
	}
}
