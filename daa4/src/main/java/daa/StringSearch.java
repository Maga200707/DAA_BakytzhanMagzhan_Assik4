package daa;
import java.util.*;

public class StringSearch {
    public long comparisons = 0;
    public interface Algorithm { List<Integer> search(String text, String pattern); }
    public static class Naive extends StringSearch implements Algorithm {
        public List<Integer> search(String text, String pattern) {
            if (pattern.isEmpty()) throw new IllegalArgumentException("Empty pattern");
            comparisons = 0;
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i <= text.length() - pattern.length(); i++) {
                int j = 0;
                while (j < pattern.length()) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) break;
                    j++;
                }
                if (j == pattern.length()) result.add(i);
            }
            return result;
        }
    }
    public static class KMP extends StringSearch implements Algorithm {
        public int[] lps(String p) {
            int[] arr = new int[p.length()];
            int len = 0, i = 1;
            while (i < p.length()) {
                comparisons++;
                if (p.charAt(i) == p.charAt(len)) arr[i++] = ++len;
                else if (len != 0) len = arr[len - 1];
                else arr[i++] = 0;
            }
            return arr;
        }
        public List<Integer> search(String text, String pattern) {
            if (pattern.isEmpty()) throw new IllegalArgumentException("Empty pattern");
            comparisons = 0;
            List<Integer> result = new ArrayList<>();
            if (pattern.length() > text.length()) return result;
            int[] lps = lps(pattern);
            int i = 0, j = 0;
            while (i < text.length()) {
                comparisons++;
                if (text.charAt(i) == pattern.charAt(j)) {
                    i++; j++;
                    if (j == pattern.length()) {
                        result.add(i - j);
                        j = lps[j - 1];
                    }
                } else if (j > 0) j = lps[j - 1];
                else i++;
            }
            return result;
        }
    }
}
