package projectone;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class Player extends Entity
{
	public int tileX;
	public int tileY;
	private Vector velocity;
	
	public int life = 1;
	
	public Player(final float x, final float y, final float vx, final float vy) 
	{
		super(x,y);
		velocity = new Vector(vx,vy);
		//TODO add sprite sheets 
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.PLAYER));	
	}
	
	
	public static Vector convertTileToPos(int tileY, int tileX)
	{
		float posY = tileY * 16;
		float posX = tileX * 16;
		return new Vector(posX, posY);
	}
	
	
	public void movePlayer(int newTileY, int newTileX)
	{
		tileY = newTileY;
		tileX = newTileX;
		Vector position = convertTileToPos(tileY, tileX);
		this.setY(position.getY());
		this.setX(position.getX());
	}	
	
	
	public void setVelocity(final Vector v) 
	{	
		velocity = v;
	}
	
	public void moveleft(final int delta, float x, float y, Player player) 
	{
		this.velocity = new Vector(-0.5f,0);
		this.update(delta);	
	}
	
	
	public void update(final int delta) 
	{
		translate(velocity.scale(delta)); 
		
	}
}
