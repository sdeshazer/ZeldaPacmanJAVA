package projectone;

import jig.Entity;
import jig.ResourceManager;

public class Potions extends Entity{
	
	public Potions() {
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.POTION));	
	}
	

}
