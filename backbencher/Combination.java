package backbencher;

public class Combination {
    public static int[][] combine(int A, int B) {
        
        if(B>A)
            return new int[0][0];
        
        if(A==B)
            {
                int[][] ret = new int[1][A];
                for(int i=0;i<A;i++)
                    ret[0][i]=i+1;
                return ret;
            }
        
        if(B==1)
        {
            int[][] ret = new int[A][1];
             for(int i=0;i<A;i++)
                    ret[i][0]=i+1;
                return ret;
        }
        /*
        the response is A choose B
        */
        int size = (factorial(A)/(factorial(B)))/factorial(A-B);
        
        int[][] ans = new int[size][B];
        
        int count=0;
        for(int i=1;i<=A-B+1;i++)
        {
            for(int l=i+1;l<=A;l++)
            {
             
                ans[count][0]=i;
                for(int k=1,j=l;k<B;k++,j++)
                {
                    ans[count][k]=j;
                }
                count++;
            }
        }
        
        return ans;
    }
    
    public static int factorial(int n) {
        if(n==1)
            return 1;
        int[] fact = new int[n+1];
        fact[0]=1;
        fact[1]=1;
        for(int i=2;i<=n;i++)
            fact[i]=i*fact[i-1];
        
        return fact[n];
    }

    public static void  main(String[] args)
    {
        combine(4, 3);
    }
}
