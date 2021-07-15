import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {
    
    Node n1;
    Node n2;
    
    @Before
    public void before() {
        n1 = new Node();
        n2 = new Node("apple", 3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNodeIllegalArg() {
        n1 = new Node(null, 2);
    }

    @Test
    public void testGetWords() {
        assertEquals(0, n2.getWords());
    }

    @Test
    public void testSetWords() {
        n2.setWords(1);
        assertEquals(1, n2.getWords());
    }

    @Test
    public void testGetTerm() {
        assertNull(n1.getTerm());
        assertEquals("apple", n2.getTerm().getQuery());
        assertEquals(3, n2.getTerm().getWeight());
    }

    @Test
    public void testSetTerm() {
        n2.setTerm(new Term("banana", 2));
        assertEquals("banana", n2.getTerm().getQuery());
        assertEquals(2, n2.getTerm().getWeight());
    }

    @Test
    public void testGetPrefixes() {
        assertEquals(0, n1.getPrefixes());
        assertEquals(0, n2.getPrefixes());
    }

    @Test
    public void testSetPrefixes() {
        n2.setPrefixes(1);
        assertEquals(1, n2.getPrefixes());
    }

    @Test
    public void testGetReferences() {
        assertEquals(26, n1.getReferences().length);
        assertEquals(26, n2.getReferences().length);
    }

    @Test
    public void testSetReferences() {
        Node[] ref = new Node[25];
        n2.setReferences(ref);
        assertEquals(25, n2.getReferences().length);
    }

}
