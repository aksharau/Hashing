package backbencher;

import java.util.Arrays;

public class OfSorts {
	
	/*
	 * merges two sorted arrays
	 * takes the first element from both
	 * takes the lesser and puts it in output array
	 * advances the pointer to next element from the array just read
	 * continues till either of the arrays are exhausted
	 * then remanining elements of the non traversed array are appended to the output array
	 */
	public static int[] merge(int[] arr1,int[] arr2,int len1,int len2)
	{
		int[] ret = new int[len1+len2];
		
		int merg=0;
		int first=0;
		int sec=0;
		
		while(first<len1&&sec<len2)
		{
				if(arr1[first]<=arr2[sec])
				{
					ret[merg]=arr1[first];
					first++;
				}
				else {
					if(arr1[first]>arr2[sec])
					{
						ret[merg]=arr2[sec];
						sec++;
					}				
				}
				merg++;
			}
		for(;first < len1;first++,merg++)
			ret[merg]=arr1[first];
		
		for(;sec<len2;sec++,merg++)
			ret[merg]=arr2[sec];
		
		return ret;
	}
	
	/*
	 * Split the array into smaller parts, divide by half each time
	 * Then call merge
	 * In java it's bit not so good as we have to copy the elements
	 * to right and left arrays - so there is an additional  pass
	 * over the entire array
	 * If we had pointers, it would be more efficient
	 */
	public static int[] mergeSort(int arr[], int len,int low,int high)
	{
		
		if(len<2)
			return arr;
		else
		{
			int[] ret;
			int mid = len/2;
			
			int[] leftInput = new int[mid];
			for(int i=0;i<mid;i++)
				leftInput[i]=arr[i];
			
			int[] rightInput = new int[len - mid];
			for(int j=0,i=mid;i<len;i++,j++)
				rightInput[j]=arr[i];
			
			int[] left = mergeSort(leftInput,mid,0,mid);
			int[] right= mergeSort(rightInput,len-mid,mid+1,high);
			ret = merge(left,right,mid,len-mid);
			return ret;
		}
		
	}
	
	/*
	 * quick sort partitions based on any pivot element
	 * chose any element as pivot - maybe the first one
	 * then have two pointers from low to high, increment the low
	 * until it finds an element higher than pivot
	 * decrement the high one until it finds an element lower than pivot
	 * swap the entries pointed by high and low
	 * continue until the high and low cross each other
	 * at this point, swap the pivot with the element at high
	 */
	public static int shuffleToPivot(int[] arr,int low,int high)
	{
		int pivot = arr[low];
		int i = low+1;
		int j = high;
		
		//System.out.println(Arrays.toString(arr));
		
		while((i<j)&&(i<high)&&(j>low))
		{
			while(arr[i]<pivot)
				i++;
			while(arr[j]>pivot)
				j--;
			
			if(i<j)
			{
			int temp=arr[j];
			arr[j]=arr[i];
			arr[i]=temp;
		//	System.out.println(Arrays.toString(arr));
			 i++;
			 j--;
			}

		}
		
		int temp=arr[j];
		arr[j]=arr[low];
		arr[low]=temp;
		System.out.println(Arrays.toString(arr));
		return j;
		
	}
	
	public static void quickSort(int[] arr,int low,int high)
	{
		if(low>=high)
			return;
		
			int idx = shuffleToPivot(arr,low,high);
		
			quickSort(arr,low,idx-1);
			quickSort(arr,idx+1,high);		
		
	}
	
	/*
	 * kth smallest element
	 * also known as quick select
	 * 
	 */
	
	public static int kThSmallest(int[] arr, int low,int high,int k)
	{
		int idx = shuffleToPivot(arr,low,high);
		
		if(idx==(k-1))
		{
			return arr[idx];
			
		}
		else
		{
			if(idx<k)
				return kThSmallest(arr,idx+1,high,k);
			else
				return kThSmallest(arr,low,idx-1,k);
		}
	}
	public static void main(String[] args) throws Exception
	{
		int[] one = new int[3];
		int[] two = new int[5];
		
		one[0]= 11;
		one[1]=1;
		one[2]=5;
		two[0]= 3;
		two[1]=15;
		two[2]=45;	
		two[3]=67;
		two[4]=78;
		
		//int[] val = mergeSort(one,3,0,3);
		
		//System.out.println(Arrays.toString(val));
		
		int[] three = new int[9];
		three[0]=8;
		three[1]=13;
		three[2]=4;
		three[3]=15;
		three[4]=6;
		three[5]=5;
		three[6]=12;
		three[7]=15;
		three[8]=3;
		
			//	quickSort(three,0,8);
			//	System.out.println(Arrays.toString(three));
		
		int idx = kThSmallest(three, 0, 8, 6);
		System.out.println("The 6th smallest:" + idx);
				
	}

}
