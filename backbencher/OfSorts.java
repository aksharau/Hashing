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
		
		int[] val = mergeSort(one,3,0,3);
		
		System.out.println(Arrays.toString(val));
	}

}
