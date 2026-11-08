package daa;

public class EditDistance {
    public long operations = 0;
    public interface Algorithm { int distance(String a, String b); }

    public static class Recursive extends EditDistance implements Algorithm {
        // State: distance between first i and j characters.
        // Transition: insert, delete, replace (or skip equal characters).
        // Base: if one string is empty, return the other length.
        public int distance(String a, String b) {
            operations = 0;
            return solve(a, b, a.length(), b.length());
        }
        private int solve(String a, String b, int i, int k) {
            operations++;
            if (i == 0) return k;
            if (k == 0) return i;
            if (a.charAt(i - 1) == b.charAt(k - 1)) return solve(a, b, i - 1, k - 1);
            int insert = solve(a, b, i, k - 1);
            int delete = solve(a, b, i - 1, k);
            int replace = solve(a, b, i - 1, k - 1);
            return 1 + Math.min(insert, Math.min(delete, replace));
        }
    }

    public static class Table extends EditDistance implements Algorithm {
        // State: dp[i][j] is distance between prefixes of lengths i and j.
        // Transition: minimum of insertion, deletion and replacement.
        // Base: dp[i][0]=i and dp[0][j]=j.
        public int distance(String a, String b) {
            operations = 0;
            int n = a.length(), m = b.length();
            int[][] dp = new int[n + 1][m + 1];
            for (int i = 0; i <= n; i++) dp[i][0] = i;
            for (int k = 0; k <= m; k++) dp[0][k] = k;
            for (int i = 1; i <= n; i++) {
                for (int k = 1; k <= m; k++) {
                    operations++;
                    int cost = a.charAt(i - 1) == b.charAt(k - 1) ? 0 : 1;
                    dp[i][k] = Math.min(dp[i - 1][k] + 1,
                        Math.min(dp[i][k - 1] + 1, dp[i - 1][k - 1] + cost));
                }
            }
            return dp[n][m];
        }
    }

    public static class TwoRows extends EditDistance implements Algorithm {
        // State: previous and current rows store distances for prefixes.
        // Transition: minimum of insertion, deletion and replacement.
        // Base: first row is 0..m; first column of each row is i.
        public int distance(String a, String b) {
            operations = 0;
            if (b.length() > a.length()) { String temp = a; a = b; b = temp; }
            int m = b.length();
            int[] prev = new int[m + 1];
            for (int k = 0; k <= m; k++) prev[k] = k;
            for (int i = 1; i <= a.length(); i++) {
                int[] cur = new int[m + 1];
                cur[0] = i;
                for (int k = 1; k <= m; k++) {
                    operations++;
                    int cost = a.charAt(i - 1) == b.charAt(k - 1) ? 0 : 1;
                    cur[k] = Math.min(prev[k] + 1, Math.min(cur[k - 1] + 1, prev[k - 1] + cost));
                }
                prev = cur;
            }
            return prev[m];
        }
    }
}
