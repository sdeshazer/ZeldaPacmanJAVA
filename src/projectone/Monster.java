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
		//todo add image here
		addImageWithBoundingBox(ResourceManager.getImage(P1Game.MONSTERG));
	}
	
	
	//dijkstra's algorithm : 
	public Node shortestPath(int x, int y, int destx, int desty, int maze[][], P1Game p1) 
	{
		int graph[][] = new int[p1.MAZEHEIGHTH][p1.MAZEWIDTH];
		
		for(int i = 0; i < p1.MAZEHEIGHTH; i++) 
		{
			for(int j = 0; j < p1.MAZEWIDTH; j++ ) 
			{
				graph[i][j] = maze[i][j];
			}
		}
		
		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		
		int minCost = 1;
		int pop = 0;
		Node root = new Node();
		
		root.x = x;
		root.y = y;
		root.cost = 0;
		root.parent = null;
		
		queue.add(root);
		
		graph[y][x] = -1;
		
		while(queue.isEmpty() == false) 
		{
			Node currentNode = new Node();
			minCost = 300;
			for(int i = 0; i< queue.size(); i++) 
			{
				if(queue.get(i).cost < minCost) 
				{
					minCost = queue.get(i).cost;
					pop = i;
				}
			}
			currentNode = queue.remove(pop);
			// we check if the current node is the destination
			if(currentNode.x == destx && currentNode.y == desty) 
			{
				return(currentNode);
			}
			
			// otherwise we look at the next tiles in the graph:
			if(graph[currentNode.y][currentNode.x + 1] == 0) 
			{
				Node node1 = new Node();
				// looking at next x :
				node1.x = currentNode.x +1;
				node1.y = currentNode.y;
				// set the parent to the node we are searching from and
				// increment the cost by 1:
				node1.parent = currentNode;
				node1.cost = currentNode.cost + 1;
				
				// we add this node as visited:
				graph[node1.y][node1.x] = -1 ;
				queue.add(node1);
			}
			else if(graph[currentNode.y][currentNode.x+1] == -1) 
			{
				Node node1 = new Node();
				node1.x = currentNode.x + 1;
				node1.y = currentNode.y;
				node1.parent = currentNode;
				node1.cost = currentNode.cost +1;
				
				for(int i = 0; i < queue.size(); i++) 
				{
					if(queue.get(i).x == node1.x && queue.get(i).y == node1.y) 
					{
						if(queue.get(i).cost > node1.cost) 
						{
							
								queue.remove(i);
								queue.add(node1);
							}
							break;
						}
					}
				}
				if(graph[currentNode.y][currentNode.x -1] == 0) 
				{
					Node node2 = new Node();
					 node2.x = currentNode.x-1;
					 node2.y= currentNode.y;
					 node2.parent = currentNode;
					 node2.cost = currentNode.cost + 1;
					 graph[node2.y][node2.x] = -1;
					 queue.add(node2);
					
				}
				else if (graph[currentNode.y][currentNode.x -1] == -1) 
				{
					Node node2 = new Node();
					node2.x = currentNode.x - 1;
					node2.y = currentNode.y;
					node2.parent = currentNode;
					node2.cost = currentNode.cost +1;
					
					for(int i = 0; i < queue.size(); i++) 
					{
						if(queue.get(i).x == node2.x && queue.get(i).y == node2.y) 
						{
							if(queue.get(i).cost > node2.cost) 
							{
								queue.remove(i);
								queue.add(node2);
							}
							break;
						}
					}
					
				}
				
				if(graph[currentNode.y +1][currentNode.x] == 0) 
				{
					Node node3 = new Node();
					node3.x = currentNode.x;
					node3.y = currentNode.y + 1;
					node3.parent = currentNode;
					node3.cost = currentNode.cost + 1;
					graph[node3.y][node3.x] = -1;
					queue.add(node3);
				}
				else if(graph[currentNode.y +1 ][currentNode.x] == -1) 
				{
					Node node3 = new Node();
					node3.x = currentNode.x;
					node3.y = currentNode.y+1;
					node3.parent = currentNode;
					node3.cost = currentNode.cost + 1;
					
					for(int i = 0; i < queue.size(); i++) 
					{
						if(queue.get(i).x == node3.x && queue.get(i).y == node3.y) 
						{
							if(queue.get(i).cost > node3.cost) 
							{
								queue.remove(i);
								queue.add(node3);
							}
							break;
						}
					}
				}
				if(graph[currentNode.y - 1][currentNode.x] == 0) 
				{
					Node node4 = new Node();
					node4.x = currentNode.x;
					node4.y = currentNode.y - 1;
					node4.parent = currentNode;
					node4.cost = currentNode.cost + 1;
					graph[node4.y][node4.x] = -1;
					queue.add(node4);
				}
				else if (graph[currentNode.y -1][currentNode.x] == -1) 
				{
					Node node4 = new Node();
					node4.x = currentNode.x;
					node4.y = currentNode.y -1;
					node4.parent = currentNode;
					node4.cost = currentNode.cost + 1;
					
					for(int i = 0; i < queue.size(); i++) 
					{
						if(queue.get(i).x == node4.x && queue.get(i).y == node4.y) 
						{
							if(queue.get(i).cost > node4.cost) 
							{
								queue.remove(i);
								queue.add(node4);
							}
							break;
						}
					}
				}
				path.add(currentNode);	
		}
			return(path.get(path.size() -1));
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
	
	
	//TODO follow maze param here for all paths per maze.  
	public void choice(int mazex, int mazey, int maze[][], P1Game p1) 
	{
		
		if(state == 0) 
		{	
			stack = this.getstack(this.shortestPath(currenttilex, currenttiley, mazex, mazey, maze, p1));
			if(!stack.isEmpty()) 
			{
				node = stack.pop();
			}
			
			if(currenttiley - node.y == -1) 
			{// move down
				desired = 2;
				this.move(p1, maze);
			}
			if(currenttiley - node.y == 1) 
			{// move up
				desired = 1;
				this.move(p1, maze);
			}
			if(currenttilex - node.x == -1) 
			{// move right
				desired = 4;
				this.move(p1,maze);
			}
			if(currenttilex - node.x == 1) 
			{// move left
				desired = 3; 
				this.move(p1, maze);
			}
		}
		if(state == 1) 
		{
			if(check > 0)
			{
				monstermovex -= 1;
				check-=1;
				p1.monster.setX(monstermovex);
			}
			else
			{
				state = 0;
				currenttilex = nexttilex;
			}
		}
		if(state == 2)
		{
			if(check > 0) 
			{
				monstermovex +=1;
				check -=1;
				p1.monster.setX(monstermovex);
			}
		}
		if(state == 3)
		{
			if(check>0)
			{
				monstermovey -=1;
				check -= 1;
				p1.monster.setY(monstermovey);
			}
			else
			{
				state = 0;
				currenttiley = nexttiley;
			}
		}
		if(state == 4)
		{
			if(check >0)
			{
				monstermovey +=1;
				check -= 1;
				p1.monster.setY(monstermovey);
			}
			else
			{
				state = 0;
				currenttiley = nexttiley;
			}
		}
	}		
}

