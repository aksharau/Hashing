package backbencher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


/*
 * Here we discuss two algorithms for finding minimal spanning tree
 * 
 * Prim's and Kruskal's
 * 
 * Minimal spanning tree is one connecting all vertices with minimal weight edges
 * 
 * Note that it's different than shortest path tree
 * 
 * s---3---a---3---t
 * \----5----------/
 * 
 * In above : The shortest path is s to t which is 5, 
 * while MST is s->a->t : with total weight of 6
 * 
 *  
 */

public class MST {
	
	/*
	 * Prim's 
	 * Have two sets: one of the MST vertices
	 * Second of the remaining vertices
	 * 
	 * Initially the MST set is empty
	 * The second set has all vertices marked with value infinite
	 * except the starting vertex has value 0
	 * 
	 * Extract from the set the lowest value vertex
	 * Once this is extracted, update the 'value' of all the vertices in the second set
	 * to update the value - compare the current value with the edge weight from the extracted element
	 * 
	 * If the value of the edge weight is lesser, then update the value, mark as parent and recurse
	 * 
	 * Data structure
	 * 	Vertex is having: name, value and parent
	 *  Min heap for the initial set of Vertices
	 *  Set of Vertex that is the MST
	 *  Adjacency list to keep track of the vertex and it's adjacent edges
	
	 *  
	 */
	
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
	 * Note it's very similar to Dijkstra's
	 * The only difference is that in Dijkstra's
	 * we look at the cumulative weight when updating the distance
	 * Meaning it checks the distance of currently extracted node + edge weight
	 * 
	 * While in Prim's it just takes the edge weight
	 */
	public void prims(int source)
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
							if(node.dist>(edge.dist))
							{
								node.dist=(edge.dist);
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
	
	
	public static void main(String[] args) throws Exception
	{
		MST gr = new MST();
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
		
		gr.prims(0);
		
		/*
		 * The next node is: 0 parent: 0 dist: 0
		   The next node is: 1 parent: 0 dist: 4
		   The next node is: 7 parent: 0 dist: 8
		   The next node is: 6 parent: 7 dist: 1
		   The next node is: 8 parent: 7 dist: 7
		   The next node is: 2 parent: 1 dist: 8
		 */
		
	}
	

}
