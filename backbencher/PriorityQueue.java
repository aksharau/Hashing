package backbencher;

import java.util.Arrays;

/*
 * These are also called binary heaps
 * The max element is on the top
 * It can also be made to have minimum  value on top
 * 
 * It's visualized as a tree but implemented as array
 * 
 * The first element of the array is the largest element
 * the left and right child are the next two elements
 * so given an index i [ 0 based ] the left child is 2*i + 1, and the right child is 2*i +2
 * 
 * The parent is i-1/2 index
 * 
 * Insert is O(log n)
 * Extract max is O(log n)
 * get_max O(1)
 * 
 */
public class PriorityQueue {
	
	
	int[] pq = new int[10];
	int maxSize = 10;
	int count=0;
	
	@Override
	public String toString()
	{
		return Arrays.toString(pq);
	}
	
	public void setArr(int[] arr, int size)
	{
		pq = arr;
		count = size;
	}
	
	public void insert(int item)
	{
		if(count==0) //first item
		{
			pq[count] = item;
			count++;
		}
		else
		{
			if(count==maxSize)
			{
				System.out.println("The priority queue is full!!");
				return;
			}
			pq[count]=item;
			count++;
			sift_up(count-1);
		}
		return;
	}
	
	/*
	 * Sift up is bubbling up the child
	 * Compare the value with it's parent
	 * if the child has more priority, then swap it with parent
	 * Now bubble the parent
	 */
	
	public void sift_up(int index)
	{
		if(index==0)
			return;
		else
		{
			int parent = getParentIndex(index);
			if (pq[index]>pq[parent])
			{
				int temp = pq[parent];
				pq[parent]=pq[index];
				pq[index]=temp;
				sift_up(parent);
			}
			return;		
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
		if(index==(count-1))
			return index;
		else
		{
			int leftChildIndex = (2*index)+1;
			if (leftChildIndex >=count)
				return index;
			return (2*index)+1;
		}
	}
	
	public int getRightChildIndex(int index)
	{
		if(index==(count-1))
			return index;
		else
		{
			int rightChildIndex = (2*index)+2;
			if (rightChildIndex >=count)
				return index;
			return (2*index)+2;
		}
	}
	
	public int get_max()
	{
		return pq[0];
	}
	
	public int get_size()
	{
		return count;
	}
	
	
	public boolean is_empty()
	{
		return count ==0 ;
	}
	
	/*
	 * Here we take the last leaf
	 * Put it on the top and then 
	 * sift this down
	 */
	public int extract_max()
	{
		if(count==0)
			return -1;
		
		int retval = pq[0];
		if(count==1)
		{
			count--;
		}
		else
		{
			pq[0]=pq[count-1];
			pq[count-1]=0;
			count--;
			sift_down(0);
		}
		
		return retval;
	}
	
	/*
	 * When sifting down the node, compare the left child and right child
	 * if the current node is lesser than the max of the left,right child
	 * swap it with the max(left,right) child value
	 * Now sift the child further on the node just swapped
	 */
	
	public void sift_down(int index)
	{
		if(index==(count-1))
			return;
		else
		{
			
				int leftChildIndex = getLeftChildIndex(index);
				int rightChildIndex = getRightChildIndex(index);
				
				int maxIndex = leftChildIndex;
				if(pq[leftChildIndex]<pq[rightChildIndex])
					maxIndex = rightChildIndex;
				
				if(pq[index]<pq[maxIndex])
				{
					int temp = pq[maxIndex];
					pq[maxIndex]=pq[index];
					pq[index]=temp;
					sift_down(maxIndex);
				}
			
			
			return;
		}
		
	}
	
	/*
	 * remove is first sifting the node upwards - by increasing the priority
	 * Then re-prioritizing the post removing this largest node
	 */
	
	public void remove(int index)
	{
		pq[index]=1000;
		sift_up(index);
		extract_max();
		
	}
	
	/*
	 * It takes the nodes from n/2 index to 0
	 * sift the index down to see if it's balanced
	 * This is claimed to be O(n log n)
	 */
	
	public static void heapify(int[] arr,int size)
	{
		PriorityQueue myPq = new PriorityQueue();
		myPq.setArr(arr, size);
		for(int i = (size/2);i>=0;i--)
			myPq.sift_down(i);
		
		System.out.println("Post heapify:" + myPq.toString());
		/*
		 * Post heapify:[80, 50, 44, 15, 23, 7, 32, 0, 0, 0]
		 */
	}
	
	/*
	 * heapify the array
	 * the max is at the top
	 * swap this one in the bottom most node
	 * then reduce the heap size by 1
	 * Sift the top most element to get the largest one on top
	 * O(nlogn)
	 */
	public static void heapSort(int[] arr,int size)
	{
		heapify(arr,size);
		PriorityQueue myPq = new PriorityQueue();
		myPq.setArr(arr, size);
		
		for(int i=size-1;i>=0;i--)
		{
			int temp = arr[0];
			arr[0]=arr[size-1];
			arr[size-1]=temp;
			size--;
			myPq.setArr(arr, size);
			myPq.sift_down(0);
		}
		
		System.out.println("Post heapsort:" + myPq.toString());
		/*
		
		Post heapsort:[7, 15, 23, 32, 44, 50, 80, 0, 0, 0]
		*/

	}
	
	public static void main(String[] args) throws Exception
	{
		PriorityQueue myPq = new PriorityQueue();
		System.out.println("The pq is:" + myPq.toString());
		
		myPq.insert(20);
		myPq.insert(100);
		
		System.out.println("The pq is:" + myPq.toString());
		
		myPq.insert(200);
		myPq.insert(300);
		myPq.insert(50);
		
		System.out.println("The pq is:" + myPq.toString());
		
		
		myPq.extract_max();
		
		System.out.println("The max is " + myPq.toString());
		
		myPq.remove(1);
		System.out.println("The max is " + myPq.toString());
		
		/*
		 * Response looks like so
		 * The pq is:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
		The pq is:[100, 20, 0, 0, 0, 0, 0, 0, 0, 0]
		The pq is:[300, 200, 100, 20, 50, 0, 0, 0, 0, 0]
		The max is [200, 50, 100, 20, 0, 0, 0, 0, 0, 0]
		The max is [200, 20, 100, 0, 0, 0, 0, 0, 0, 0]

		 */
		
		
		int[] someStuff = new int[10];
		someStuff[0] = 23;
		someStuff[1] = 50;
		someStuff[2] = 32;
		someStuff[3] = 15;
		someStuff[4] = 80;
		someStuff[5] = 7;
		someStuff[6] = 44;
		
		heapify(someStuff,7);
		
		heapSort(someStuff,7);
		
	}
}
