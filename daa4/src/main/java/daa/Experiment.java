package daa;
import java.nio.file.*;
import java.util.*;

public class Experiment {
    static Random random = new Random(42);
    static String randomString(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) s.append((char)('A' + random.nextInt(4)));
        return s.toString();
    }
    static String repeated(int n, char c) { return String.valueOf(c).repeat(n); }
    static double median(double[] a) { Arrays.sort(a); return a[a.length / 2]; }
    static void record(List<String> lines, String name, String type, int n, int run, double time, long count) {
        lines.add(String.format(Locale.US, "%s,%s,%d,%d,%.6f,%d", name, type, n, run, time, count));
    }
    public static void main(String[] args) throws Exception {
        random = new Random(42);
        List<String> lines = new ArrayList<>();
        lines.add("algorithm,input,n,run,time_ms,operations");
        for (String type : new String[]{"random", "different"}) {
            for (int n : new int[]{2,4,6,8}) {
                String a = type.equals("random") ? randomString(n) : repeated(n,'A');
                String b = type.equals("random") ? randomString(n) : repeated(n,'B');
                EditDistance.Recursive rec = new EditDistance.Recursive();
                for (int run=1; run<=5; run++) {
                    long start=System.nanoTime(); rec.distance(a,b);
                    record(lines,"recursive",type,n,run,(System.nanoTime()-start)/1e6,rec.operations);
                }
            }
            for (int n : new int[]{50,100,200,400,800}) {
                String a = type.equals("random") ? randomString(n) : repeated(n,'A');
                String b = type.equals("random") ? randomString(n) : repeated(n,'B');
                for (String alg : new String[]{"table","two_rows"}) {
                    EditDistance ed = alg.equals("table") ? new EditDistance.Table() : new EditDistance.TwoRows();
                    EditDistance.Algorithm method = (EditDistance.Algorithm) ed;
                    for (int run=1; run<=5; run++) {
                        long start=System.nanoTime(); method.distance(a,b);
                        record(lines,alg,type,n,run,(System.nanoTime()-start)/1e6,ed.operations);
                    }
                }
            }
        }
        for (String type : new String[]{"random", "repeated"}) {
            for (int n : new int[]{1000,4000,16000,64000}) {
                String text = type.equals("random") ? randomString(n) : repeated(n,'A');
                String pattern = type.equals("random") ? "ABACAB" : "AAAAAB";
                for (String alg : new String[]{"naive","kmp"}) {
                    StringSearch ss = alg.equals("naive") ? new StringSearch.Naive() : new StringSearch.KMP();
                    StringSearch.Algorithm method = (StringSearch.Algorithm)ss;
                    for (int run=1; run<=5; run++) {
                        long start=System.nanoTime(); method.search(text,pattern);
                        record(lines,alg,type,n,run,(System.nanoTime()-start)/1e6,ss.comparisons);
                    }
                }
            }
        }
        Files.createDirectories(Path.of("results"));
        Files.write(Path.of("results/results.csv"),lines);
        System.out.println("Saved " + (lines.size()-1) + " runs to results/results.csv");
    }
}
