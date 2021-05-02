package backbencher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/*
 * This is the Dijkstra's shortest path algorithm
 * It assumes two things
 * 1. The weights are positive
 * 2. There are no cycles
 * 
 * Under such conditions, the shortest path is
 * found like so:
 * 1. Have a set of vertices that already have their shortest distance determined
 * 2. Initially this set is empty
 * 3. Have a min heap for the remaining vertices, where each vertex is denoted 
 * by the distance from source. Initially the source has this value as zero, hence this is the min element
 * 4. Extract the min element and place it in the set of point 1
 * 5. For the min element extracted relax the min heap for each of the outgoing edges of the set element
 * 6. Loop to point 4
 */



public class GraphDijkstra {
	
	public class Vertex {
		int name;
		int dist;
		int parent;
		
		public String toString()
		{
			return "Name:" + name + " Parent: "+ parent + " Dist: " + dist;
		}
	};
	
	ArrayList<Vertex> heap=null;
	/*
	 * Min heap value
	 * Have arraylist of object - object having distance and vertix name
	 * as properties
	 */
	public void addToHeap(Vertex v)
	{
		if(heap==null) //first object
		{
			heap = new ArrayList<Vertex>();
			heap.add(v);
		}
		else
		{
			/*
			 * Need to keep min on the root
			 * larger elements go to child nodes
			 * recurse on the child and have the element sift down
			 */
			heap.add(v);
			sift_up(heap.size()-1);
		}
	}
	
	/*
	 * Get the min value
	 * Now place the last value on top
	 * Sift down
	 */
	
	public Vertex extractMin()
	{
		Vertex temp = heap.get(0);
		Vertex last = heap.remove(heap.size()-1);
		if(heap.size()>0)
		{
			heap.set(0, last);
			sift_down(0);
		}
		return temp;
		
	}
	/*
	 * compare the value on the index with it's parent
	 * if the value is lesser than the parent one
	 * then swap with the parent and recurse on the newly swapped
	 */
	public void sift_up(int index)
	{
		if(index==0)
			return;
		int parent = getParentIndex(index);
		if(heap.get(parent).dist>heap.get(index).dist)
		{
			Vertex temp = heap.get(parent);
			heap.set(parent, heap.get(index));
			heap.set(index, temp);
			sift_up(parent);
		}
	}
	
	/*
	 * We need to move the larger value down
	 * compare the current index with it's children
	 * If current node is greater than the child nodes
	 * take the minimum of the child nodes an swap with current
	 * Now recurse on the swapped node
	 */
	public void sift_down(int index)
	{
		int leftChildIndex = getLeftChildIndex(index);
		int rightChildIndex = getRightChildIndex(index);
		int currentVal = heap.get(index).dist;
		int leftChildVal = heap.get(leftChildIndex).dist;
		int rightChildVal = heap.get(rightChildIndex).dist;
		int leastIndex = leftChildIndex;
		int minVal = leftChildVal;
		if(rightChildVal<leftChildVal)
		{
			leastIndex = rightChildIndex;
			minVal = rightChildVal;
		}
		if(currentVal>minVal)
		{
			Vertex temp = heap.get(leastIndex);
			heap.set(leastIndex, heap.get(index));
			heap.set(index, temp);
			sift_down(leastIndex);
		}
		
	}
	
	
	public int getParentIndex(int index)
	{
		if(index==0)
			return index;
		else
		{
			return (index-1)/2;
		}
		
	}
	
	public int getLeftChildIndex(int index)
	{
		if(index==(heap.size()-1))
			return index;
		else
		{
			int leftChildIndex = (2*index)+1;
			if (leftChildIndex >=heap.size())
				return index;
			return (2*index)+1;
		}
	}
	
	public int getRightChildIndex(int index)
	{
		if(index==(heap.size()-1))
			return index;
		else
		{
			int rightChildIndex = (2*index)+2;
			if (rightChildIndex >=heap.size())
				return index;
			return (2*index)+2;
		}
	}
	
	/*
	 * Now we store the graph itself
	 * in an adjacency list
	 * Map of key value
	 * Key is the Vertex, distance as zero
	 * Values are the adjacent nodes with distance from the source node
	 */
	
	private Map<Integer,LinkedList<Vertex>> map = new HashMap<>();
	
	public void addVertex(int name)
	{
		map.put(name, new LinkedList<Vertex>());
	}
	
	public void addEdge(int from,int distance,int to)
	{
	
		if(!map.containsKey(from))
		{
			addVertex(from);
		}
		
		if(!map.containsKey(to))
		{
			addVertex(to);
		}
		
		Vertex v = new Vertex();
		v.name = to;
		v.dist = distance;
		map.get(from).add(v);
		
		
	}
	
	/*
	 * Shortest path 
	 * Get the source from the map
	 * Get all the keys from the map and insert to min heap
	 * Set the source vertex distance to zero
	 * Insert to heap all the vertex
	 * 
	 * Have a linked list of nodes visited
	 * Initially set the value to source
	 * 
	 * Get all the adjacent nodes
	 * relax the distances 
	 * extract the min and repeat
	 */
	
	public void shortestPath(int source)
	{
		for(Integer i: map.keySet())
		{
			Vertex v = new Vertex();
			v.name=i;
			//System.out.println("Adding to heap: "+ i);
			if(i==source)
			{				
				v.dist=0;
				v.parent=source;
			}
			else
			{
				v.dist=1000;
			}
			
			addToHeap(v);
			
		}
		
		Set<Integer> s = new HashSet();
		
		while(!heap.isEmpty())
		{
			Vertex x = extractMin();
			int minDist = x.dist;
			if(!s.contains(x.name))
			{
				System.out.println("The next node is: " + x.name + " parent: " + x.parent + " dist: " + x.dist);
				s.add(x.name);
				/* 
				 * get all outgoing edges from this vertex
				 */
				for(Vertex edge:map.get(x.name))
				{
					int iter=0;
					for(Vertex node:heap)
					{
						if(node.name==edge.name)
						{
							if(node.dist>(edge.dist+minDist))
							{
								node.dist=(edge.dist+minDist);
								node.parent=x.name;
								sift_up(iter);
							}
						}
						iter++;
					}
				}
			}
		}
	}
	
	/*
	 * It just goes over each edge
	 * and relaxes the edge
	 * This loop continues for Number of vertex -1
	 * Once the loop ends, check if there is any edge
	 * that was left un-relaxed - this can only happen 
	 * when there is a -ve cycle - return that there is negative cycle
	 * else just return the shortest path
	 */
	
	public void shortestPathBellmanFord(int source)
	{
		Map<Integer,Vertex> spv = new HashMap<Integer,Vertex>();
		
		/*
		 * Initialize step - all except source
		 * are initialized with infinite distance
		 */
		for(Integer i: map.keySet())
		{
			Vertex v = new Vertex();
			v.name=i;
			//System.out.println("Adding to heap: "+ i);
			if(i==source)
			{				
				v.dist=0;
				v.parent=source;
			}
			else
			{
				v.dist=1000;
			}
			spv.put(i,v);
		}
		
		for(int i=1;i<map.keySet().size();i++)
		{
			/* 
			 * Go over each edge, for this
			 * go over each item in the array list of 
			 * adjacency list
			 * Relax the edges
			 */
			//Integer[] keyArr = (Integer[]) map.keySet().toArray();
			for(Integer key: map.keySet())
			{
				for(Vertex v: map.get(key))
				{
					Vertex fromSpvV = spv.get(v.name);
					Vertex fromSpvU = spv.get(key);
					if(fromSpvV.dist>(v.dist+fromSpvU.dist))
							{
								fromSpvV.dist=v.dist+fromSpvU.dist;
								fromSpvV.parent = fromSpvU.name;
							}
				}
			}
			
			
		}
		
		/*
		 * Check for -ve edges
		 */
		
		for(Integer key: map.keySet())
		{
			for(Vertex v: map.get(key))
			{
				Vertex fromSpvV = spv.get(v.name);
				Vertex fromSpvU = spv.get(key);
				if(fromSpvV.dist>(v.dist+fromSpvU.dist))
						{
							System.out.println("There is a negtaive loop:" );
							return;
						}
			}
		}
		
		System.out.println("The map is:" + spv.toString());
	}
	
	
	public static void main(String[] args) throws Exception
	{
		GraphDijkstra gr = new GraphDijkstra();
		gr.addVertex(0);
		gr.addVertex(1);
		gr.addVertex(7);
		gr.addVertex(2);
		gr.addVertex(8);
		gr.addVertex(6);
		
		gr.addEdge(0, 4, 1);
		gr.addEdge(0, 8, 7);
		gr.addEdge(1, 11, 7);
		gr.addEdge(7, 7, 8);
		gr.addEdge(1,8,2);
		gr.addEdge(2, 2, 8);
		gr.addEdge(8, 6, 6);
		gr.addEdge(7, 1, 6);
		
		gr.shortestPath(0);
		
		
		/*
		 * The next node is: 0 parent: 0 dist: 0
		   The next node is: 1 parent: 0 dist: 4
           The next node is: 7 parent: 0 dist: 8
           The next node is: 6 parent: 7 dist: 9
           The next node is: 2 parent: 1 dist: 12
           The next node is: 8 parent: 2 dist: 14
		 */
		
		gr.shortestPathBellmanFord(0);
		/*
		The map is:
		{0=Name:0 Parent: 0 Dist: 0, 
		 1=Name:1 Parent: 0 Dist: 4, 
		 2=Name:2 Parent: 1 Dist: 12, 
		 6=Name:6 Parent: 7 Dist: 9, 
		 7=Name:7 Parent: 0 Dist: 8, 
		 8=Name:8 Parent: 2 Dist: 14}
		 */
		
		GraphDijkstra gr2 = new GraphDijkstra();
		gr2.addVertex(0);
		gr2.addVertex(1);
		gr2.addVertex(2);
		gr2.addVertex(3);
		
		gr2.addEdge(0, 3, 1);
		gr2.addEdge(0, 4, 2);
		gr2.addEdge(2, -4, 1);
		gr2.addEdge(1, 5, 3);
		gr2.addEdge(3, -2, 2);
		
		gr2.shortestPathBellmanFord(0);
		
		/*
		 * There is a negtaive loop:
		 */

	}

}
