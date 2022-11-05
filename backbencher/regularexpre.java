package backbencher;

public class regularexpre {

    public static boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0]=true; //empty string always matches

        if(p.charAt(0)=='*') {
            dp[0][1]=true;
        }

        for(int i=0;i<p.length();i++) {

            if(p.charAt(i)=='*') {
                dp[0][i+1]=dp[0][i];
                for(int j=0;j<s.length();j++) {
                    dp[j+1][i+1]=dp[j][i+1] || dp[j+1][i] || dp[j][i];
                }
            }
            else if(p.charAt(i)=='?') {
                for(int j=s.length()-1;j>=0;j--) {
                    dp[j+1][i+1]=dp[j][i];
                }
            }
            else {
                for(int j=0;j<s.length();j++) {
                    dp[j+1][i+1]=dp[j][i];
                }
            }
        }

        return dp[s.length()][p.length()];
    }

    public static void main(String[] args) {
        String s = "abc";
        String p= "*?c";

        System.out.println("The response is" + isMatch(s, p));
    }
    
}
