package projectone;

import jig.Entity;
import jig.ResourceManager;


public class Stump extends Entity{
	
	public Stump() {
		
		addImageWithBoundingBox(ResourceManager.getImage(Game.STUMP_NODE));
		
	}

}
