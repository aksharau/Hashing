package backbencher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/*
 * Kruskal's works with taking minimum edges
 * and checking if connecting the edges does not
 * from a cycle. Note that Kruskal's algorithm works on undirect graph
 * 
 * Hence following data structures will be used
 * 1.Adjacency list for the original graph
 * 2.ArrayList of ArrayList of Vertex to store vertex partitions.
 *   Each Vertex object stores vertex name and the name of parent.
 *   Find will find in the ArrayList iteratively
 * 3.Min heap to store the edge. Min heap is based on the edge weight. 
 *   Edge object stores the weight and the nodes it connects
 *   
 */
public class Kruskal {
	
	public class Edge
	{
		int weight;
		int v1;
		int v2;
		
		public String toString()
		{
			return " V1:" + v1 + " V2: " + v2 + " Weight: " + weight;
		}
	};
	
	public class Vertex
	{
		int name;
		int parent;
		
		public String toString()
		{
		return "Name:" + name + " Parent: "+ parent ;
		}
	};
	
	ArrayList<Edge> heap=null;
	
	/*
	 * Min heap value
	 * Have arraylist of object - object having distance and vertix name
	 * as properties
	 */
	public void addToHeap(Edge e)
	{
		if(heap==null) //first object
		{
			heap = new ArrayList<Edge>();
			heap.add(e);
		}
		else
		{
			/*
			 * Need to keep min on the root
			 * larger elements go to child nodes
			 * recurse on the child and have the element sift down
			 */
			heap.add(e);
			sift_up(heap.size()-1);
		}
	}
	
	/*
	 * Get the min value
	 * Now place the last value on top
	 * Sift down
	 */
	
	public Edge extractMin()
	{
		Edge temp = heap.get(0);
		Edge last = heap.remove(heap.size()-1);
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
		if(heap.get(parent).weight>heap.get(index).weight)
		{
			Edge temp = heap.get(parent);
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
		int currentVal = heap.get(index).weight;
		int leftChildVal = heap.get(leftChildIndex).weight;
		int rightChildVal = heap.get(rightChildIndex).weight;
		int leastIndex = leftChildIndex;
		int minVal = leftChildVal;
		if(rightChildVal<leftChildVal)
		{
			leastIndex = rightChildIndex;
			minVal = rightChildVal;
		}
		if(currentVal>minVal)
		{
			Edge temp = heap.get(leastIndex);
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
	
	private Map<Integer,LinkedList<Edge>> map = new HashMap<>();
	
	public void addVertex(int name)
	{
		map.put(name, new LinkedList<Edge>());
	}
	
	public void addEdge(int from,int weight,int to)
	{
	
		if(!map.containsKey(from))
		{
			addVertex(from);
		}
		
				
		Edge e = new Edge();
		e.v1=to;
		e.v2=from;
		e.weight=weight;
		
		/*
		 * since we are storing both to and from, no need to store
		 * the same edge twice
		 */		
		
		map.get(from).add(e); 
		
	}
	
	public class Ufs
	{
		public class Node{
			/*
			 * Relevant only for the root node
			 * This is not same as height
			 * But just a number that helps to decide on the merge
			 * 
			 * When merging two sets of the same rank
			 * the rank of the parent increases by 1
			 * 
			 * When merging two sets of different ranks
			 * the tree with higher rank becomes the parent
			 * of the tree with lower rank
			 */
			int rank;
			Vertex v;
			
			public String toString()
			{
				return "\n Rank: " + rank + v.toString() + "\n";
			}
			
		}
		
		/*
		 * This is the disjoint set
		 * It stores vertices assuming they start with 0 
		 * Initially, each vertex has parent as it's own
		 */
		ArrayList<Node> ufs;
		
		
		public Ufs(int totalVertexCount)
		{
			ufs = new ArrayList<Node>(totalVertexCount);
			for(int i=0;i<totalVertexCount;i++)
			{
				Node n = new Node();
				ufs.add(n);
			}
		}
		
		/*
		 * Setting the vertex at it;s respective index
		 */
		public void add(Vertex v)
		{
			Node n = new Node();
			n.rank=0;
			n.v=v;

			ufs.set(v.name, n);
		}
		
		/*
		 * Given a vertex, find which parent is belongs to
		 * Find also updates the parent
		 */
		public int findInUfs(int name)
		{
			Node n = ufs.get(name);
			if(n.v.name!=n.v.parent)
			{
				n.v.parent=findInUfs(n.v.parent);
			}
			
			return n.v.parent;

		}
		
		/*
		 * Get the ranks
		 * The one with lower rank becomes child of one with higher rank
		 * Else for equal ranks, anyone becomes the parent
		 * and the parent rank increases
		 */
		public void union(int set1,int set2)
		{
			Node parent1 = ufs.get(set1);
			Node parent2 = ufs.get(set2);
			
			if(parent1.rank>parent2.rank)
			{
				parent2.v.parent=parent1.v.name;
			}
			else
			{
				parent1.v.parent=parent2.v.name;
				if(parent2.rank==parent1.rank)
				{
					parent2.rank++;
				}
			}
		}
		
		public String toString()
		{
			return ufs.toString();
		}
	};

	
	public void kruskals()
	{
		/*
		 * Iterate over the map and initialize the 
		 * union find set
		 * and the heap
		 */
		int totalVertexCount  = map.keySet().size();
		//System.out.println("The totalVertexCount: " + totalVertexCount);
		Ufs ufs = new Ufs(totalVertexCount);
		
		
		for(Integer i: map.keySet())
		{
			Vertex v = new Vertex();
			v.name=i;
			v.parent=i;
			
			ufs.add(v);
		
			
			for(Edge e : map.get(i))
			{
			
				addToHeap(e);
			}
			
			
		}
		
		
		int edgesAdded =0;
		while(edgesAdded!=(totalVertexCount-1))
		{
			Edge e = extractMin();
			//System.out.println("The edge extracted is: " + e.toString());
			
			int set1 = ufs.findInUfs(e.v1);
			int set2 = ufs.findInUfs(e.v2);
			if(set1!=set2)
			{
				edgesAdded++;
				System.out.println("The edge added is: " + e.toString());
				ufs.union(set1,set2);
				//System.out.println("Ufs is"+ ufs.toString());
			}
		}
		
	}//kruskals
	
	public static void main(String[] args) throws Exception
	{
		Kruskal gr = new Kruskal();
		gr.addVertex(0);
		gr.addVertex(1);
		gr.addVertex(3);
		gr.addVertex(2);
		gr.addVertex(4);
		gr.addVertex(5);
		
		gr.addEdge(0, 4, 1);
		gr.addEdge(0, 8, 3);
		gr.addEdge(1, 11, 3);
		gr.addEdge(3, 7, 4);
		gr.addEdge(1,8,2);
		gr.addEdge(2, 2, 4);
		gr.addEdge(4, 6, 5);
		gr.addEdge(3, 1, 5);
		
	
		gr.kruskals();		
		
		/*
		 * The edge added is:  V1:5 V2: 3 Weight: 1
		   The edge added is:  V1:4 V2: 2 Weight: 2
		   The edge added is:  V1:1 V2: 0 Weight: 4
		   The edge added is:  V1:5 V2: 4 Weight: 6
		   The edge added is:  V1:2 V2: 1 Weight: 8

		 */
		
	}

}
