import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AutocompleteTest {

    String root = "/autograder/submission/";
    //String root = "src\\";
    Autocomplete ac;

    @Before
    public void before() {
        // create a new Autocomplete object(new root)
        ac = new Autocomplete();
    }
    
//    @Test
//    public void testAddWord() {
//        ac.addWord("DAR", 10);
//        ac.buildTrie(root + "test3.txt", 3);
//    }

    @Test
    public void testBuildTrie() {
        ac.buildTrie(root + "test1.txt", 3);
        assertEquals(6, ac.getRoot().getPrefixes());
        assertEquals(2, ((ac.getRoot().getReferences())[19]).getPrefixes());
        Node node = (((ac.getRoot().getReferences())[19]).getReferences()[7]);
        assertEquals("the", (node.getReferences()[4]).getTerm().getQuery());
        assertEquals(1, (node.getReferences()[4]).getPrefixes());
        assertEquals(1, (node.getReferences()[4]).getWords());
        
        //ac.buildTrie(root + "test2.txt", 3);
    }

    @Test
    public void testNumberSuggestions() {
        ac.buildTrie(root + "test1.txt", 3);
        assertEquals(3, ac.numberSuggestions());
    }

    @Test
    public void testGetSubTrie() {
        ac.buildTrie(root + "test1.txt", 3);
        Node subTrie = ac.getSubTrie("tha");
        assertEquals("that", (subTrie.getReferences()[19]).getTerm().getQuery());
        assertEquals(2, (subTrie.getReferences()[19]).getTerm().getWeight());
        assertEquals(1, (subTrie.getReferences()[19]).getWords());
        
        Node subTrie2 = ac.getSubTrie("thas");
        assertNull(subTrie2);
        
        Node subTrie3 = ac.getSubTrie("32");
        assertNull(subTrie3);
        
        Node subTrie4 = ac.getSubTrie("");
        assertEquals(6, subTrie4.getPrefixes());
    }

    @Test
    public void testCountPrefixes() {
        ac.buildTrie(root + "test1.txt", 3);
        assertEquals(2, ac.countPrefixes("th"));
        assertEquals(1, ac.countPrefixes("a"));
        assertEquals(0, ac.countPrefixes("as"));
    }

    @Test
    public void testGetSuggestions() {
        ac.buildTrie(root + "test1.txt", 3);
        List<ITerm> res = ac.getSuggestions("o");
        assertEquals("oak", ((Term)res.get(0)).getQuery());
        assertEquals(2, ((Term)res.get(0)).getWeight());
        assertEquals("of", ((Term)res.get(1)).getQuery());
        assertEquals("offer", ((Term)res.get(2)).getQuery());
        assertEquals(0, ac.getSuggestions("32").size());
        assertEquals(0, ac.getSuggestions("ofs").size());

    }
    
    @Test
    public void testSetRoot() {
        ac.setRoot(new Node("", 100));
        assertEquals(100, ac.getRoot().getTerm().getWeight());
    }


    @Test
    public void testSetK() {
        ac.setK(100);
        assertEquals(100, ac.getK());
    }
    
    @Test
    public void testIsValid() {
        assertFalse(ac.isValid("32"));
    }

}
