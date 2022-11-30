package backbencher;

import java.util.*;

public class palindromincSubstring {

    public static int countSubstrings(String s) {
        
        return getCount(s,0,s.length()-1);
     }
 
     public static int getCount(String s,int start,int end)
     {
         if(start==s.length()||end<0)
             return 0;
 
         if(start==end)
             return 1;
         
         int count;
         
         if(s.charAt(start)==s.charAt(end))
         {
             count= getCount(s,start+1,end-1);
             return count;
         }
         else
         {
             int countA = getCount(s,start+1,end);
             int countB= getCount(s,start,end-1);
             return countA+countB;
         }
     }
    
     public static void main(String[] args) {
        System.out.println(countSubstrings("abc"));
     }
}
