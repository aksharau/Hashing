package backbencher;

import java.util.*;

public class shortestSuperString {

        public static int solve(ArrayList<String> A) {
            /*
            for each pair of strings
            find the common substring
            then concatenate the two strings <string1pefix><commonsubstring><string1suffix><string2prefix><string2suffix>
            with this concatenated string, compare the next
            so on and then get the smallest common superstring
            */
            
            if(A==null||A.size()==0)
                return 0;
            
            if(A.size()==1)
                return A.get(0).length();
                
            String a = A.get(0);
            String b = A.get(1);
            
            String common = getMergesSubstring(a,b);
            for(int i=2;i<A.size();i++)
            {
                common = getMergesSubstring(common,A.get(i));
            }
            
            return common.length();
        }
        
        public static String getMergesSubstring(String a,String b) {
            
            
            int[][] dp = new int[a.length()+1][b.length()+1];
            for(int i=0;i<=a.length();i++) {
                dp[i][0]=0;
            }
            
             for(int i=0;i<=b.length();i++) {
                dp[0][i]=0;
            }
            
            int maxIdx=-1,maxIdx2=-1;
            int maxMatch=0;
            for(int i=1;i<=a.length();i++)
            {
                for(int j=1;j<=b.length();j++)
                {
                    if(a.charAt(i-1)==b.charAt(j-1))
                    {
                        dp[i][j]=dp[i-1][j-1]+1;
                        if(dp[i][j]>maxMatch)
                        {
                            maxMatch=dp[i][j];
                            maxIdx = i-1;
                            maxIdx2=j-1;
                        }
                        
                    }
                    else
                    {
                        dp[i][j]=0;
                    }
                }
            }
            
            //at this point maxMatch has length of max matched characters
            //maxIdx is pointing to matching point end in a 
            //maxIdx2 is pointing to matching point end in b
            int matchStartA = maxIdx-maxMatch+1;
            int matchStartB = maxIdx2-maxMatch+1;
            
            StringBuilder merged = new StringBuilder();
            merged.append(a);
            if(matchStartB>=0)
                merged.append(b.substring(0,matchStartB));
            merged.append(b.substring(maxIdx2+1));
            
            return merged.toString();
        }
        
    
        public static void main(String[] args) {
            ArrayList<String> a = new ArrayList<>();

            a.add("bcd");
            a.add("ab");
            a.add("cd");

            System.out.println("The ans is: "+ solve(a));
        }
    
}
