package backbencher;

import java.util.*;

public class generateParen {

    public static List<String> generateParenthesis(int n) {

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        return recurse(sb,1,0,n);
    }

    public static List<String> recurse(StringBuilder sb,int openCount,int closeCount,int n)
    {
        List<String> ans = new ArrayList<>();
        if(openCount==n&&closeCount==n)
            {ans.add(sb.toString());
                return ans;}
        if(openCount<n)
            {
                 StringBuilder op = new StringBuilder();
                 op.append(sb.toString());
                op.append("(");
                List<String> ret1 = recurse(op,openCount+1,closeCount,n);
                if(ret1!=null)
                {
                    for(String s: ret1)
                        ans.add(s);
                }
                
            }
            if(closeCount==openCount-1||openCount==n)
            {
                 StringBuilder op = new StringBuilder();
                 op.append(sb.toString());
                op.append(")");
                List<String> ret1 = recurse(op,openCount,closeCount+1,n);
                if(ret1!=null)
                {
                    for(String s: ret1)
                        ans.add(s);
                }    
            }

            return ans;
    }

    public static void main(String[] args)
    {
        generateParenthesis(3);
    }
}
