package projectone;

import java.util.ArrayList;
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

	Stack<Node> stack = new Stack<Node>();
	public Node node = null;


	public void update(final int delta) 
	{
		translate(velocity.scale(delta));
	}


	public Monster(final float x, final float y) 
	{
		super(x,y);
		//TODO add image here
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.MONSTERG));
	}


	// checks if the index of the array is an open node
	private int checkOpenNodeAndGetIndex(ArrayList<Node> openNodes, int tileY, int tileX)
	{
		for(int i = 0; i < openNodes.size(); i++)
		{
			Node openNode = openNodes.get(i);
			if(openNode.tileIndex.y == tileY && openNode.tileIndex.x == tileX)
				return i;
		}
		return -1;
	}



	private boolean isClosed(ArrayList<Node> closedNodes, TileIndex tileIndex)
	{
		for(int i = 0; i < closedNodes.size(); i++)
		{
			Node closedNode = closedNodes.get(i);
			if(closedNode.tileIndex == tileIndex)
				return true;
		}
		return false;
	}

	
	// make each directional node a child of the current node.
	private Node makeCornerNodeChildofCurrentAndAddCost(Node current, Node cornerNode)
	{
		cornerNode.cost = current.cost + 1;	
		cornerNode.parent = current;
		return cornerNode;
	}
	
	// check if tiles match within the open list, if they do, compare cost and
	// add the node to the open list if the cost is less than the current.
	private ArrayList<Node> checkCostToAddToOpenList(ArrayList<Node> openNodes, Node cornerNode)
	{
		for(int i = 0; i < openNodes.size(); i++) 
		{
			if(openNodes.get(i).tileIndex.x == cornerNode.tileIndex.x 
					&& openNodes.get(i).tileIndex.y == cornerNode.tileIndex.y) 
			{
				if(openNodes.get(i).cost > cornerNode.cost) 
				{
					openNodes.remove(i);
					openNodes.add(cornerNode);
				}
				break;
			}
		}	
		return openNodes;	
	}


	
	//dijkstra's path finding: 
	public Node shortestPath( int currentTileY, int currentTileX, int destX, int destY, int maze[][], P1Game p1) 
	{

		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();
		ArrayList<Node> closedNodes = new ArrayList<Node>();


		Node startNode = new Node();

		startNode.tileIndex.y = currentTileY;
		startNode.tileIndex.x = currentTileX;
		startNode.cost = 0;
		startNode.parent = null;

		openNodes.add(startNode);

		while(!openNodes.isEmpty()) 
		{
			Node currentNode = new Node();
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
			// we check if the current node is the destination
			if(currentNode.tileIndex.y == destY && currentNode.tileIndex.x == destX) 
			{
				return(currentNode);
			}

			ArrayList<TileIndex> neighboringTileIndecies = new ArrayList<TileIndex>();

			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y, currentNode.tileIndex.x + 1));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y, currentNode.tileIndex.x - 1));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y + 1, currentNode.tileIndex.x));
			neighboringTileIndecies.add(new TileIndex(currentNode.tileIndex.y - 1, currentNode.tileIndex.x));

			for(int i = 0; i < neighboringTileIndecies.size(); i++)
			{
				// skipping nodes we know are already closed:
				if(isClosed(closedNodes, neighboringTileIndecies.get(i)))
				{
					continue;
				}
				TileIndex tileIndex = neighboringTileIndecies.get(i);

				//check for out of bounds.
				if(tileIndex.y > p1.MAZEHEIGHTH)
					break;

				if(tileIndex.x > p1.MAZEWIDTH)
					break;

				//skipping nodes we know have collision:
				if(maze[tileIndex.y][tileIndex.x] == 1) 
				{
					continue;
				}			
			}

			// if we don't have an open node, the index returns -1
			if(checkOpenNodeAndGetIndex(openNodes, currentNode.tileIndex.y, currentNode.tileIndex.x) != -1) 
			{
				Node nodeRight = new Node();
				Node nodeLeft = new Node();
				Node nodeUp = new Node();
				Node nodeDown = new Node();

				// make each corner node a child of the current node and increment
				// the cost of the corner node to be the current cost + 1
				nodeRight = makeCornerNodeChildofCurrentAndAddCost(currentNode, nodeRight);
				nodeLeft = makeCornerNodeChildofCurrentAndAddCost(currentNode, nodeLeft);
				nodeUp = makeCornerNodeChildofCurrentAndAddCost(currentNode, nodeUp);
				nodeDown = makeCornerNodeChildofCurrentAndAddCost(currentNode, nodeDown);

				int currentY = currentNode.tileIndex.y;
				int currentX = currentNode.tileIndex.x;


				nodeRight.tileIndex.y = currentY;
				nodeRight.tileIndex.x = currentX + 1;

				nodeLeft.tileIndex.y = currentY;
				nodeLeft.tileIndex.x = currentX - 1;

				nodeUp.tileIndex.y = currentY - 1;
				nodeUp.tileIndex.x = currentX;

				nodeDown.tileIndex.y = currentY + 1;
				nodeDown.tileIndex.x = currentX;

				// update the openNodes list for each cornerNode depending on the Cost.
				openNodes = checkCostToAddToOpenList(openNodes, nodeRight);
				openNodes = checkCostToAddToOpenList(openNodes, nodeLeft);
				openNodes = checkCostToAddToOpenList(openNodes, nodeUp);
				openNodes = checkCostToAddToOpenList(openNodes, nodeDown);
			}
		}

		//add all openNodes to the list of nodes to traverse:
		int openNodeIndex = 0;
		Node currentNode = new Node();
		while( currentNode != startNode) 
		{	
			currentNode = openNodes.get(openNodeIndex++);
			path.add(currentNode);
		}

		return(path.get(path.size() - 1));
	}



	public Stack<Node> getstack(Node n)
	{
		Stack<Node> tempstack = new Stack<Node>();

		while(n!=null)
		{
			tempstack.add(n);
			n=n.parent;
		}
		tempstack.pop();
		return(tempstack);
	}



	private void move(P1Game p1 , int maze[][]) 
	{	
		if(desired == 3) 
		{
			if(maze[currenttiley][currenttilex-1] != 1) 
			{
				state = 1;
				nexttilex = currenttilex -1;
				nexttiley = currenttiley;
				key = 3;
				check = 10;
				current = 3;
				return;
			}
			else if(current == 3) 
			{
				return;
			}
			return;
		}
		if (desired == 1) 
		{
			if(maze[currenttiley -1][currenttilex] != 1) 
			{
				state = 3;
				nexttilex = currenttilex; 
				nexttiley = currenttiley -1;
				key = 1;
				check = 10;
				current = 1;
				return;
			}
			else if (current == 1) 
			{
				return;
			}
			return;
		}
		if(desired == 2) 
		{
			if(maze[currenttiley+1][currenttilex] != 1) 
			{
				state = 4;
				nexttilex = currenttilex;
				nexttiley = currenttiley + 1;
				key= 2;
				check = 10;
				current = 2;
				return;
			}
			else if (current == 2) 
			{
				return;
			}
			return;
		}
		if(desired == 4) 
		{
			if(maze[currenttiley][currenttilex+1] != 1) 
			{
				state = 2;
				nexttilex = currenttilex + 1;
				nexttiley = currenttiley;
				key = 4;
				check = 10;
				current = 4;
				return;
			}
			else if(current == 4) 
			{
				return;
			}
			return;
		} 
		return;
	}

}

