

/**
 * ==== Attributes ====
 * - words: number of words
 * - term: the ITerm object
 * - prefixes: number of prefixes 
 * - references: Array of references to next/children Nodes
 * 
 * ==== Constructor ====
 * Node(String word, long weight)
 * 
 * @author Minzhong Gao
 */
public class Node {
    private Term term;
    private int words;
    private int prefixes;
    private Node[] references;
    
    public Node() {
        // term is null
        term = null;
        // initialize references
        references = new Node[26];
        words = 0;
        prefixes = 0;
    }
    
    public Node(String query, long weight) throws IllegalArgumentException {
        // if query is null or weight negative
        if (query == null || weight < 0) {
            throw new IllegalArgumentException();
        } else {
            term = new Term(query, weight);
        }
        // initialize references
        references = new Node[26];
        words = 0;
        prefixes = 0;
    }
    
    protected int getWords() {
        return words;
    }
    protected void setWords(int words) {
        this.words = words;
    }
    protected Term getTerm() {
        return term;
    }
    protected void setTerm(Term term) {
        this.term = term;
    }
    protected int getPrefixes() {
        return prefixes;
    }
    protected void setPrefixes(int prefixes) {
        this.prefixes = prefixes;
    }
    protected Node[] getReferences() {
        return references;
    }
    protected void setReferences(Node[] references) {
        this.references = references;
    }
    
}
