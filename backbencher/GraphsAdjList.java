package backbencher;
import java.util.*;

/*
 * There are two ways to implement a graph.
 * Well, two popular ways - one via adjacency matrix 
 * where row and column correspond to the vertices
 * and edge is represented as 1 on the matrix
 * 
 * The other representation is an adjacency list
 * Where the vertices are represented as array indexes
 * and each vertex has linked list containing the 
 * other vertices that this vertex is linked to
 * 
 * In case of Java Hashmap can be used to implement the 
 * key as the vertex and the value as linked list
 */

public class GraphsAdjList<T> {
	
	private Map<T,LinkedList<T>> map = new HashMap<>();
	
	private int vertexCount=0;
	
	public void addVertex(T v)
	{
		map.put(v, new LinkedList<T>());
		vertexCount++;
	}
	
	public void addEdge(T from, T to, boolean bidirectional)
	{
		if(!map.containsKey(from))
		{
			addVertex(from);
		}
		
		if(!map.containsKey(to))
		{
			addVertex(to);
		}
		
		map.get(from).add(to);
		
		if(bidirectional)
		{
			map.get(to).add(from);
		}
	}
	
	public int getVertexCount()
	{
		return map.keySet().size();
		
	}
	
	public int getEdgesCount(boolean bidirectional)
	{
		int count=0;
		for(T v: map.keySet())
		{
			count+= map.get(v).size();
			
		}
		
		if(bidirectional)
			count/=2;
		
		return count;
	}
	
	public boolean hasVertex(T v)
	{
		return map.keySet().contains(v);
	}
	
	public boolean hasEdge(T from, T to)
	{
		if(map.containsKey(from))
		{
			return map.get(from).contains(to);
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		String ret="";
		for(T v: map.keySet())
		{
			ret+=v.toString()+":";
			ret+=map.get(v).toString();
			ret+="\n";
		}
		
		return ret;
	}
	
	/*
	 * Breadth first traversal
	 * iterate the key - keep all the child nodes in visited
	 * once all child nodes are marked visited mark the key as processed
	 * then dequeue the child nodes and check their child nodes
	 * if they are already marked visited, then no need to enqueue them
	 * else enqueue the child nodes and mark this as processed
	 * each time a child node is enqueued, the current key is the parent
	 * print the array of parent nodes
	 * 
	 */
	public void bfs()
	{
		Set<T> visited = new HashSet<T>(); // for each vertex we track if it was visited
		Map<T,T> parent = new HashMap<T,T>(); // for each vertex we track the parent
		//Set<T> processed = new HashSet<T>();
		LinkedList<T> queue = new LinkedList<T>();
		
		/*
		 * For each element visited
		 * Print it and add all it's child nodes to 
		 * the Linked list
		 * Keep popping from the Linked list
		 * check if this node is already visited
		 * Print it, push all it's child nodes
		 * mark the key as processed
		 */
		T[] keyArr = (T[])map.keySet().toArray();
		
		queue.add(keyArr[0]);
		visited.add(keyArr[0]);
		
		while(!queue.isEmpty())
		{
			T key = queue.getFirst() ;
			System.out.println("The node is: " + key );
			
			for(T v: map.get(key))
			{
				if(!visited.contains(v))
				{
					parent.put(v, key);
					queue.add(v);
					visited.add(v);
				}
			}
			
			queue.removeFirst();
		}
		
		System.out.println("The child=parents are:" + parent.toString());
	}
	
	/*
	 * Depth first traversal
	 * Start with one of the vertex from the keyset
	 * Idea is to traverse all the child nodes of a given child node
	 * push all the child nodes on a stack
	 * retrieve the last child node, push it's child nodes
	 * each time a child node is pushed on stack - the current node is marked as parent
	 */
	
	public void dfs()
	{
		Set<T> visited = new HashSet<T>();//stores the node that has been pushed to stack
		LinkedList<T> stack = new LinkedList<T>(); //to track the child nodes in depth
		Set<T> processed = new HashSet<T>();//stores the child node that is 
		
		T[] keyArr = (T[])map.keySet().toArray();
		
		stack.add(keyArr[0]);
		System.out.println("The node is: " + keyArr[0]);
		visited.add(keyArr[0]);
		
		while(!stack.isEmpty())
		{
			T key = stack.peekLast() ;
			
			/*
			 * The case that this node has no child nodes
			 * 
			 */
			if(map.get(key).isEmpty())
			{
				processed.add(key);
				stack.removeLast();
			}
			else
			{
				int processedChild=0;
				for(T v: map.get(key))
				{
					
					//When we visit child for first time
					//need to push it in stack
					
					if(!visited.contains(v))
					{
						stack.add(v);
						visited.add(v);
						System.out.println("The node is: " + v );
						break;
					}
					else
					{
						//here it is visited, check it's child nodes
						//push to stack the child node which is not in processed
						if(!processed.contains(v)&&map.get(v).isEmpty())
						{
							processed.add(v);
							stack.removeLast();
							break;
						}
						else
						{
							if(!processed.contains(v))
							{
								stack.add(v);
								visited.add(v);
								System.out.println("The node is: " + v );
								break;
							}
							else {
								processedChild++;
								if(processedChild==map.get(key).size())
								{
									processed.add(key);
									stack.removeLast();
									break;
								}
							}
							
						}
					}
				}
				
			}
			

		}
	}

	
	public static void main(String[] args)
	{
		GraphsAdjList<Integer> gr = new GraphsAdjList<Integer>();
		
		gr.addEdge(1, 2,false);
		gr.addEdge(2, 3, false);
		gr.addEdge(1, 4, false);
		
		System.out.println("The graph is: " + gr.toString());	
		
		System.out.println("The bfs  is: **************" );
		gr.bfs();
		
		System.out.println("The dfs  is: **************" );
		gr.dfs();
		
		/*
		 * The graph is: 1:[2, 4]
2:[3]
3:[]
4:[]

The bfs  is: **************
The node is: 1
The node is: 2
The node is: 4
The node is: 3
The child=parents are:{2=1, 3=2, 4=1}
The dfs  is: **************
The node is: 1
The node is: 2
The node is: 3
The node is: 4
		 */
	}
}
