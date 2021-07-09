package backbencher;

/*
 * This is a dynamic programming problem 
 * Given two strings, find the largest common substring
 * 
 * aabcaabc
 * abbc
 * 
 * So the longest common substring is abbc
 * 
 * The technique relies on storing the matching values n
 * and the longest matched length stored so far
 * A matrix is created with string a characters representing the rows
 * and string b characters representing the columns
 * An additional row and column is added which will hold all
 * the 0s as the initial starting values
 * 
 * Everytime a match is found between the row and the columns characters
 * get the value from the diagonal element and increment it and set the path to diagonal
 * else - get the largest value from the top or left element and accordingly set the path to top or left
 * 
 * While getting the result, start from the last element 
 * of the matrix
 * if the value is set based on diagonal - it means there was a match
 * save this character and move diagonal up in the matrix
 * else move in the direction pointed by the current element
 * 
 * Once we hit the 0th row or column, we stop
 * 
 * reverse the string thus formed - this is the answer
 * 
 */

public class LargestCommonSubsequence {

	public class DPItem
	{
		int value;
		int direction;
		char c;
		//0 is diagonal, 1 is top and 2 is left
		
		public DPItem (int v, int d)
		{
			value = v;
			direction = d;
			
		}
	}
	
	
	public String getLCS(String a, String b)
	{
		int lenA = a.length();
		int lenB = b.length();
		
		DPItem[][]  dpMatrix = new DPItem[lenA+1][lenB+1];
		
		//first column is made 0
		for(int i=0; i<=lenA;i++)
		{
			dpMatrix[i][0]= new DPItem(0,0);
		}
		
		//first row is made 0
		for(int i=0; i<=lenB;i++)
		{
			dpMatrix[0][i]= new DPItem(0,0);
		}
		
		for(int i=1;i<=lenA;i++)
		{
			for(int j=1;j<=lenB;j++)
			{
				if(a.charAt(i-1)==b.charAt(j-1))
				{
					DPItem diagItem = dpMatrix[i-1][j-1];
					DPItem newItem = new DPItem(diagItem.value+1,0);
					newItem.c = a.charAt(i-1);
					dpMatrix[i][j] = newItem;
				}
				else
				{
					DPItem topItem = dpMatrix[i-1][j];
					DPItem leftItem = dpMatrix[i][j-1];
					if(topItem.value>=leftItem.value)
					{
						DPItem newItem = new DPItem(topItem.value,1);
						dpMatrix[i][j] = newItem;
					}
					else
					{
						DPItem newItem = new DPItem(leftItem.value,2);
						dpMatrix[i][j] = newItem;
					}
				}
			}
		}
		
		String result="";
		
		int i=lenA;
		int j = lenB;
		while((i>0)&&(j>0))
		{
			DPItem currentItem = dpMatrix[i][j];
			if(currentItem.direction==0) //it means that match was found
			{
				result += currentItem.c;
				i--;
				j--;
			}
			else
			{
				if(currentItem.direction==1)//need to go to top
				{
					i--;
				}
				else
				{
					j--;
				}
			}
			
		}
		
		String ans="";
		for(int ii=result.length()-1;ii>=0;ii--)
		{
			ans +=  result.charAt(ii);
		}
		return ans;
	}
	public static void main(String[] args)
	{
		if(args.length>=2)
		{
			String a = args[0];
			String b = args[1];
			
			LargestCommonSubsequence l = new LargestCommonSubsequence();
			String result = l.getLCS(a,b);
			System.out.println("Result: " + result);
		}
	}
}
