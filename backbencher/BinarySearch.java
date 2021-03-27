package backbencher;

public class BinarySearch {
	
	
	/*
	 * Returns the index of the array that contains the element
	 * If the element is not found , returns -1
	 */
	public static int search(int item,int[] array,int min,int max)
	{
		if(min>max)
		{
			return -1; //we exhausted the array search
		}
		else 
		{
		int half = (min + max)/2;
		if(array[half]<item)
			return search(item,array,half+1,max);
		else
			if(array[half]>item)
				return search(item,array,min,half-1);
			else
				if(array[half]== item)
					return half;
		}
		
		return -1;
		
	}
	
	
	public static void main(String[] args) throws Exception
	{
		int firstArry[] = { 11, 12,13,14,15,16,17};
		
		System.out.println("The index for 14 is " + search(14,firstArry,0,6));
		System.out.println("The index for 17 is " + search(17,firstArry,0,6));
		System.out.println("The index for 11 is " + search(11,firstArry,0,6));
		System.out.println("The index for 44 is " + search(44,firstArry,0,6));
		System.out.println("The index for 10 is " + search(44,firstArry,0,6));
		
	}

}
