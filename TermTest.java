import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class TermTest {
    
    Term t1;
    Term t2;
    Term t3;
    Term t4;
    ArrayList<Term> l;
    
    @Before
    public void before() {
        // three Term object
        t1 = new Term("app", 3);
        t2 = new Term("april", 4);
        t3 = new Term("apple", 5);
        
        
        // add three term to ArrayList
        l = new ArrayList<>();
        l.add(t1);
        l.add(t2);
        l.add(t3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTermIllegalArg() {
        t4 = new Term(null, 2);
    }

    @Test
    public void testCompareTo() {
        Collections.sort(l);
        assertEquals("app", l.get(0).getQuery());
        assertEquals("apple", l.get(1).getQuery());
        assertEquals(3, l.size());
    }
    
    @Test
    public void testByReverseWeightOrder() {
        Collections.sort(l, ITerm.byReverseWeightOrder());
        assertEquals(5, l.get(0).getWeight());
        assertEquals(4, l.get(1).getWeight());
        assertEquals(3, l.get(2).getWeight());
    }
    
    @Test
    public void testByPrefixOrder() {
        Collections.sort(l, ITerm.byPrefixOrder(3));
        assertEquals("app", l.get(0).getQuery());
        assertEquals("apple", l.get(1).getQuery());
    }

    @Test
    public void testToString() {
        assertEquals("4\tapril", t2.toString());
        assertEquals("5\tapple", t3.toString());
    }

    @Test
    public void testGetQuery() {
        assertEquals("apple", t3.getQuery());
    }

    @Test
    public void testSetQuery() {
        t3.setQuery("banana");
        assertEquals("banana", t3.getQuery());
    }

    @Test
    public void testGetWeight() {
        assertEquals(5, t3.getWeight());
    }

    @Test
    public void testSetWeight() {
        t3.setWeight(1);
        assertEquals(1, t3.getWeight());
    }
    
    @Test
    public void testGetTerm() {
        assertEquals("apple", t3.getTerm());
    }

    @Test
    public void testSetTerm() {
        t3.setTerm("banana");
        assertEquals("banana", t3.getTerm());
    }

}
