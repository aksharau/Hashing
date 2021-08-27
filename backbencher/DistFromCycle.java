package backbencher;

import java.util.*;

/*
 * Consider a connected graph consisting of nodes and bidirectional edges. Each edge is 1 unit of distance long, and only one edge connects any two nodes.  There is one cycle in the graph. For each node, determine its shortest distance from the cycle and return the distances in an integer array. If a node is in the cycle, its distance is 0.

 Example:

g_nodes = 6

g_edges = 6

g_from = [1, 2, 1, 3, 1, 2]

g_to = [2, 3, 3, 5, 4, 6]

 The merged lists, g_from and g_to, are [[1, 2], [2, 3], [1, 3], [3, 5], [1, 4], [2, 6]].

 The cycle contains nodes 1, 2 and 3, and their distance is 0 by definition.  Nodes 4, 5 and 6 are each 1 unit distance from the cycle.  The return array is [0, 0, 0, 1, 1, 1].



Function Description 



Complete the function nodeDistance in the editor below. The function has to return an integer array denoting the remoteness of each node from the cycle.



nodeDistance has the following parameter(s):

    int g_nodes: the number of nodes

    int g_edges: the number of edges

    int g_from[g_edges]: g_from[i] denotes the first node of the ith edge in the graph.

    int g_to[g_edges]: g_to[i] denotes the second node of the ith edge in the graph.



Constraints

3 ≤ g_nodes ≤ 3 x 103
1 ≤ g_from, g_to ≤ g_nodes
g_edges = g_nodes
The graph has no self-loops.  There is no edge that begins and ends at the same node.
The graph contains at most a single edge between any pair of nodes.
 */

public class DistFromCycle {

	public int[] nodeDistance ( int g_nodes,int g_edges, int[] g_from, int[] g_to)
	{
		int[] dist = new int[g_nodes];
		int[] color = new int[g_nodes]; //this is to check which nodes are already seen,in process
		int[] parent = new int[g_nodes];
		Arrays.fill(parent, -1);
		Arrays.fill(dist, -1);

		//create Adjacency list for the graph
		// since the edges are bidirectional so a to:1 from :2 would be same as from:1 and to:2
		// to find the cycle, it's easier to store the relation where from index is less than to index

		Map<Integer,ArrayList<Integer>> graph = new HashMap<Integer,ArrayList<Integer>>();
		Map<Integer,ArrayList<Integer>> bigraph = new HashMap<Integer,ArrayList<Integer>>();

		for(int i=0;i<g_edges;i++)
		{
			int from = g_from[i];
			int to = g_to[i];
			if(to<from)
			{
				from=g_to[i];
				to=g_from[i];
			}
			from=from-1;
			to=to-1;
			if(graph.get(from)==null)
			{
				ArrayList<Integer> child= new ArrayList<Integer>();
				child.add(to);
				graph.put(from, child);

				if(bigraph.get(to)==null)
				{
					ArrayList<Integer> revchild= new ArrayList<Integer>();
					revchild.add(from);
					bigraph.put(to,revchild);
				}
				else
				{
					bigraph.get(to).add(from);
				}
			}
			else
			{
				graph.get(from).add(to);
				if(bigraph.get(to)==null)
				{
					ArrayList<Integer> revchild= new ArrayList<Integer>();
					revchild.add(from);
					bigraph.put(to,revchild);
				}
				else
				{
					bigraph.get(to).add(from);
				}
			}
		}

		// we do dfs and iterate over the child nodes
		// if the current child is already being processed
		// that means there is a cycle
		// need to see the parent and it's parent until we reach the current node
		// all these are part of cycle

		for(int i=0;i<g_nodes;i++)
		{
			if(color[i]==0)
				dfs(i,color,parent,dist,graph);
		}


		for(int i=0;i<g_nodes;i++)
		{
			if(dist[i]==0) //cycle node
			{
				ArrayList<Integer> children = graph.get(i);
				if(children!=null)
				{
					for(Integer child: children)
					{
						if(dist[child]!=0)
							dist[child]=1;
					}
				}

				ArrayList<Integer> revchildren = bigraph.get(i);
				if(revchildren!=null) {
					for(Integer revchild: revchildren)
					{
						if(dist[revchild]!=0)
							dist[revchild]=1;
					}
				}
			}
		}

		
		//for the nodes we could not find the distance yet
		// check the distance of parent 
		for(int i=0;i<g_nodes;i++)
		{
			if(dist[i]==-1)
				dist[i]=getDist( i,parent,dist,graph,bigraph)+1;
		}


		return dist;
	}

	//gets the distance of the current node from it's parent node
	
	public int getDist(int current,int[] parent,int[] dist,Map<Integer,ArrayList<Integer>> graph,Map<Integer,ArrayList<Integer>> bigraph)
	{

		int ans=0;
		int p = parent[current];
		if(p!=-1)
		{
			if(dist[p]!=-1)
			{
				ans=dist[p];
				return ans;
			}
		}

		ArrayList<Integer> revChild = bigraph.get(current);
		if(revChild!=null) {
			for(Integer x: revChild)
			{
				ans=Math.min(ans,getDist(x,parent,dist,graph,bigraph) );
			}
		}
		else
		{
			ArrayList<Integer> child = graph.get(current);
			if(child!=null) {
				for(Integer x: child)
				{
					if(parent[x]==current)
						return dist[x];
				}
			}
		}
		
		


		return ans;
	}

	
	//This detects the cycle
	// Also populates the distance when the cycle is detected
	// populates the parent information while doing dfs
	
	public void dfs(int current,int[] color,int[] parent,int[] dist, Map<Integer,ArrayList<Integer>> graph)
	{
		if(color[current]!=0)
		{
			//we have encountered a cycle on this node
			dist[current]=0;
			int p = parent[current];
			if(p==-1)
				return;
			while(color[p]!=1)//1 is when the processing has started but not ended
			{
				dist[p]=0;
				p=parent[p];
			}
			dist[p]=0;//this is the parent that terminates the cycle

		}
		else
		{
			color[current]=1;
			ArrayList<Integer> children = graph.get(current);
			if(children!=null) {
				for(Integer child: children)
				{
					if(parent[child]==-1)
						parent[child]=current;
					dfs(child,color,parent,dist,graph);
				}

			}
			color[current]=2;
		}
	}


	public static void main(String[] args)
	{
		DistFromCycle d = new DistFromCycle();
		int[] from = new int[6];
		int[] to = new int[6];
		//[1, 2, 1, 3, 1, 2]
		/*from[0]=1;
		from[1]=2;
		from[2]=1;
		from[3]=3;
		from[4]=1;
		from[5]=2;

		//[2, 3, 3, 5, 4, 6]
		to[0]=2;
		to[1]=3;
		to[2]=3;
		to[3]=5;
		to[4]=4;
		to[5]=6;*/
		
		/*from[0]=1;
		from[1]=2;
		from[2]=3;
		from[3]=4;
		from[4]=5;
		from[5]=4;

		//[2, 3, 3, 5, 4, 6]
		to[0]=2;
		to[1]=3;
		to[2]=4;
		to[3]=5;
		to[4]=3;
		to[5]=6;*/
		
		
		from[0]=1;
		from[1]=2;
		from[2]=3;
		from[3]=4;
		from[4]=1;
		from[5]=3;
		

		
		to[0]=2;
		to[1]=3;
		to[2]=4;
		to[3]=1;
		to[4]=5;
		to[5]=6;
		

		System.out.println(Arrays.toString(d.nodeDistance(6, 6, from, to)));
	}

}
