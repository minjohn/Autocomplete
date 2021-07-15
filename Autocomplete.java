import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Autocomplete implements IAutocomplete {

    private Node root;
    private int k;

    public Autocomplete() {
        // initialize the root of trie
        root = new Node("", 0);
    }

    @Override
    public void addWord(String word, long weight) {
        String token = word.toLowerCase();
        if (!isValid(token)) {
            return;
        }
        int len = token.length();
        Node curr = root;
        Node[] ref;
        for (int i = 0; i < len; i++) {
            int nxt = token.charAt(i) - 'a';
            // set prefixes value for current node
            int prefixes = curr.getPrefixes();
            curr.setPrefixes(prefixes + 1);
            // pass next node to current
            ref = curr.getReferences();
            if (ref[nxt] == null) {
                // initialize a new empty node
                ref[nxt] = new Node();
            }
            curr = ref[nxt];
        }
        // set the last node in the word
        int prefixes = curr.getPrefixes();
        curr.setPrefixes(prefixes + 1);
        curr.setTerm(new Term(token, weight));
        int words = curr.getWords();
        curr.setWords(words + 1);
    }

    @Override
    public Node buildTrie(String filename, int k) {
        // remember to call constructor before buildTrie
        // pass k to k
        this.k = k;
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = in.readLine()) != null) {
                // remove leading and tailing whitespace, split by tab
                String[] tmp = line.trim().split("\t");
                // ignore the header
                if (tmp.length < 2) {
                    continue;
                } else {
                    // convert to lower-case
                    String word = tmp[1].toLowerCase();
                    long weight = Long.parseLong(tmp[0]);
                    // if valid, add word
                    if (isValid(word)) {
                        addWord(word, weight);
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.root;
    }
    
    public boolean isValid(String word) {
        for (char c : word.toCharArray()) {
            // not a-z
            if (c < 97 || c > 122) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int numberSuggestions() {
        return this.k;
    }

    @Override
    public Node getSubTrie(String prefix) {
        if (!isValid(prefix)) {
            return null;
        }
        int len = prefix.length();
        Node curr = root;
        Node next;
        for (int i = 0; i < len; i++) {
            int nxt = prefix.charAt(i) - 'a';
            // pass next node to current
            next = (curr.getReferences())[nxt];
            curr = next;
            if (curr == null) {
                return null;
            }
        }
        return curr;
    }

    @Override
    public int countPrefixes(String prefix) {
        // get subRoot for this subTrie
        Node subRoot = getSubTrie(prefix);
        if (subRoot == null) {
            return 0;
        }
        // call helper function to count words
        int res = countPrefixesHelper(subRoot);
        return res;
    }

    private int countPrefixesHelper(Node subRoot) {
        Queue<Node> q = new LinkedList<>();
        int sum = 0;
        // add the first root
        q.add(subRoot);
        // while queue is not empty, perform bfs
        while (!q.isEmpty()) {
            Node curr = q.remove();
            sum += curr.getWords();
            // if the node has children
            for (Node node : curr.getReferences()) {
                // add child to queue
                if (node != null) {
                    q.add(node);
                }
            }
        }
        return sum;
    }

    @Override
    public List<ITerm> getSuggestions(String prefix) {
        List<ITerm> res = new ArrayList<>();
        if (!isValid(prefix)) {
            return res;
        }
        // get subRoot for this subTrie
        Node subRoot = getSubTrie(prefix);
        if (subRoot == null) {
            return res;
        }
        // call helper function to populate res list
        res = getSuggestionsHelper(subRoot);
        // sort by ReverseWeightOrder and lexicographic order
        Collections.sort(res, ITerm.byReverseWeightOrder());
        Collections.sort(res);
        return res;
    }

    private List<ITerm> getSuggestionsHelper(Node subRoot) {
        List<ITerm> res = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();
        // add the first root
        q.add(subRoot);
        // while queue is not empty, perform bfs
        while (!q.isEmpty()) {
            Node curr = q.remove();
            // if current node containing Term
            if (curr.getWords() != 0) {
                Term term = new Term(curr.getTerm().getQuery(), curr.getTerm().getWeight());
                res.add(term);
            }
            // if the node has children
            for (Node node : curr.getReferences()) {
                // add child to queue
                if (node != null) {
                    q.add(node);
                }
            }
        }
        return res;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

}
