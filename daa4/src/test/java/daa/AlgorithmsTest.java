package daa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class AlgorithmsTest {
    @Test void editReferences() {
        for (EditDistance.Algorithm a : List.of(new EditDistance.Recursive(), new EditDistance.Table(), new EditDistance.TwoRows())) {
            assertEquals(3,a.distance("kitten","sitting"));
            assertEquals(2,a.distance("flaw","lawn"));
            assertEquals(5,a.distance("intention","execution"));
            assertEquals(3,a.distance("","abc"));
            assertEquals(0,a.distance("abc","abc"));
            assertEquals(0,a.distance("",""));
        }
    }
    @Test void randomEdit() {
        Random r = new Random(42);
        for (int k=0;k<100;k++) {
            String a = random(r,r.nextInt(5)), b=random(r,r.nextInt(5));
            int expected = new EditDistance.Table().distance(a,b);
            assertEquals(expected,new EditDistance.Recursive().distance(a,b));
            assertEquals(expected,new EditDistance.TwoRows().distance(a,b));
        }
    }
    static String random(Random r,int n) {
        StringBuilder b=new StringBuilder();
        for(int i=0;i<n;i++) b.append((char)('A'+r.nextInt(3)));
        return b.toString();
    }
    @Test void lpsAndSearch() {
        StringSearch.KMP k = new StringSearch.KMP();
        assertArrayEquals(new int[]{0,1,2,0,1,2,3,3},k.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0,0,1,2,0,1,2,3,4},k.lps("ABABCABAB"));
        for (StringSearch.Algorithm a : List.of(new StringSearch.Naive(),new StringSearch.KMP())) {
            assertEquals(List.of(10),a.search("ABABDABACDABABCABAB","ABABCABAB"));
            assertEquals(List.of(0,1,2,3),a.search("AAAAA","AA"));
            assertEquals(List.of(0,2,4),a.search("ABABABA","ABA"));
            assertEquals(List.of(),a.search("AB","ABC"));
            assertThrows(IllegalArgumentException.class,()->a.search("ABC",""));
        }
    }
    @Test void randomSearch() {
        Random r=new Random(77);
        for(int i=0;i<100;i++) {
            String text=random(r,r.nextInt(40)), p=random(r,1+r.nextInt(6));
            assertEquals(new StringSearch.Naive().search(text,p),new StringSearch.KMP().search(text,p));
        }
    }
}
