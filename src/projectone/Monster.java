package projectone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


public class Monster extends Entity 
{

	private Vector velocity;
	public int nexttile;
	public int currenttilex = 14;
	public int currenttiley = 11;
	public int nexttilex = 0;
	public int nexttiley = 0;
	public int check = 0;
	public float monstermovex = 232;
	public float monstermovey = 232;
	public int desired = 0;
	public int current = 0;
	public int key = 0;
	public int state = 0;
	public boolean dead = false;
	
	private Node pathNode = null;
	
	private float inputCoolDown = 0;
	private float inputDelay = 300;


	public void update(final int delta) 
	{
	
		
		if(inputCoolDown <= 0) 
		{
			if(pathNode != null)
			{
				int pathY = pathNode.tileIndex.y;
				int pathX = pathNode.tileIndex.x;
				
				moveMonster(pathY,pathX);
				
				pathNode = pathNode.successor;
				inputCoolDown = inputDelay;
			}
		}
		else
		{
			inputCoolDown -= delta;
		}
	}


	public Monster(final float x, final float y) 
	{
		super(x,y);
		//TODO add image here
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.MONSTERG));
	}



	private Node getNode(ArrayList<Node> nodeList, TileIndex tileIndex) 
	{
		for(int i = 0; i < nodeList.size(); i++)
		{
			Node openNode = nodeList.get(i);
			if(openNode.tileIndex.y == tileIndex.y && openNode.tileIndex.x == tileIndex.x)
				return openNode;
		}
		return null;
	}


	private boolean isClosed(ArrayList<Node> closedNodes, TileIndex tileIndex)
	{
		for(int i = 0; i < closedNodes.size(); i++)
		{
			Node closedNode = closedNodes.get(i);
			if(closedNode.tileIndex.y == tileIndex.y && closedNode.tileIndex.x == tileIndex.x)
				return true;
		}
		return false;
	}
	
	//dijkstra's path finding: 
	private Node shortestPath( P1Game p1, int maze[][], int currentTileY, int currentTileX, 
							int destY, int destX) 
	{

		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();
		ArrayList<Node> closedNodes = new ArrayList<Node>();


		Node startNode = new Node();
		System.out.println("Monster monster Y " + currentTileY);
		startNode.tileIndex = new TileIndex(currentTileY, currentTileX); 

		startNode.cost = 0;
		startNode.parent = null;
		
		openNodes.add(startNode);
		
		
		Node currentNode = new Node();
		while(!openNodes.isEmpty()) 
		{	
			int minCost = Integer.MAX_VALUE;
			int minIndex = 0;

			for(int i = 0; i < openNodes.size(); i++) 
			{
				if(openNodes.get(i).cost < minCost) 
				{
					minCost = openNodes.get(i).cost;
					minIndex = i;
				}
			}

			currentNode = openNodes.remove(minIndex);
			System.out.println("Monster currentNode yx " + currentNode.tileIndex.y + " , " + currentNode.tileIndex.x);			
			// we check if the current node is the destination
			if(currentNode.tileIndex.y == destY && currentNode.tileIndex.x == destX) 
			{
				System.out.println("Monster found destination" + currentNode.tileIndex.y + " , " + currentNode.tileIndex.x);			
				Node prevNode = null;
				
				while( currentNode != startNode) 
				{	
					currentNode.successor = prevNode;
					//p1.maze[currentNode.tileIndex.y][currentNode.tileIndex.x] = 2;
					path.add(currentNode);
					prevNode = currentNode;
					currentNode = currentNode.parent;
					
				}
				
				return(path.get(path.size() - 1));
			}

			ArrayList<TileIndex> neighboringTileIndecies = new ArrayList<TileIndex>();

			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y,
													  currentNode.tileIndex.x + 1));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y,
													  currentNode.tileIndex.x - 1));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y + 1,
													  currentNode.tileIndex.x));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y - 1, 
													  currentNode.tileIndex.x));
			int newCost = currentNode.cost + 1;
			
			for(int i = 0; i < neighboringTileIndecies.size(); i++)
			{
				// skipping nodes we know are already closed:
				if(isClosed(closedNodes, neighboringTileIndecies.get(i))) {
					System.out.println("Monster neighbor is closed" );
					continue;
				}

				
				TileIndex tileIndex = neighboringTileIndecies.get(i);

				if(tileIndex.y > p1.MAZEHEIGHTH || tileIndex.y < 0)
					continue;

				if(tileIndex.x > p1.MAZEWIDTH || tileIndex.x < 0)
					continue;

				//skipping nodes we know have collision:
				if(maze[tileIndex.y][tileIndex.x] == 1) 
				{
					System.out.println("Monster collision found" );
					continue;
				} 
				Node openNode = getNode(openNodes,tileIndex);
				
				if(openNode != null)
				{
					if(openNode.cost <= newCost)
						continue;
				}
				else
				{
					openNode = new Node();
					openNode.tileIndex = tileIndex;
				
				}
				openNode.cost = newCost;
				
				openNode.parent = currentNode;
				openNodes.add(openNode);	
			}
			
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			System.out.println("Monster closedNode: yx " + currentNode.tileIndex.y + " , " + currentNode.tileIndex.x);	
			
		}
		
		Node prevNode = null;
		while( currentNode != startNode) 
		{	
			currentNode.successor = prevNode;
			path.add(currentNode);
			prevNode = currentNode;
			currentNode = currentNode.parent;
		}
		
		if(path.isEmpty()) 
		{
			System.out.println("Monster path list found to be empty");
		}
		
		for(int i = 0; i < path.size(); i++) 
		{
			System.out.println(path.get(i));
			p1.maze[path.get(i).tileIndex.y][path.get(i).tileIndex.x] = 2;
		}

		return(path.get(path.size() - 1));
	}

	
	private static Vector convertTileToPos(int tileY, int tileX)
	{
		float posY = tileY * 16;
		float posX = tileX * 16;
		return new Vector(posX, posY);
	}
	
	
	private void moveMonster(int newTileY, int newTileX)
	{
		currenttiley = newTileY;
		currenttilex = newTileX;
		System.out.println("Monster moving monster to " + currenttiley +" , " + currenttilex);
		Vector position = convertTileToPos(currenttiley, currenttilex);
		this.setY(position.getY());
		this.setX(position.getX());
	}
	
	
	// move the monster(s) based on the path found to the destination
	// where the current tiles are the starting point and the 
	// destination is near the player's location.
	public void moveMonstertoPath(P1Game p1 , int maze[][], 
							 int currentTileY, int currentTileX, int destY, int destX) 
	{	
		// if the current tile is equal to the destination, shortestPath will return
		// the destination node.
		pathNode = shortestPath(p1, maze, currentTileY, currentTileX, destY, destX);
		
	}
}

