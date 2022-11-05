package backbencher;

import java.util.*;


public class UnboundedKnapsack {

    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
    
        int n = weights.length;

        int[][] dp = new int[n][capacity+1];
        
        for(int i=0;i<n;i++)
        {
            dp[i][0]=0; //zero capacity has zero profit
        }

        for(int i=1;i<=capacity;i++)
        {
            if(weights[0]*i<=i)
            {
                dp[0][i]=dp[0][i-1]+profits[0];
            }
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<=capacity;j++){
                if(weights[i]<=j)
                {
                    dp[i][j]=Math.max(dp[i-1][j],profits[i]+dp[i][j-weights[i]]);
                }
            }
        }

    }    
}
