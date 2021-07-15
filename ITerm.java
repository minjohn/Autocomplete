import java.util.Comparator;

/**
 * @author ericfouh
 */
public interface ITerm
    extends Comparable<ITerm> {

    /**
     * Compares the two terms in descending order by weight.
     * 
     * @return comparator Object
     */
    public static Comparator<ITerm> byReverseWeightOrder() {
        Comparator<ITerm> comp = new Comparator<ITerm>() {

            @Override
            public int compare(ITerm o1, ITerm o2) {
                return (int) ((((Term)o2).getWeight() - ((Term)o1).getWeight()));
            }
            
        };
        return comp;
    }


    /**
     * Compares the two terms in lexicographic order but using only the first r
     * characters of each query.
     * 
     * @param r
     * @return comparator Object
     */
    public static Comparator<ITerm> byPrefixOrder(int r) throws IllegalArgumentException {
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        Comparator<ITerm> comp = new Comparator<ITerm>() {

            @Override
            public int compare(ITerm o1, ITerm o2) {
                Term term1 = (Term)o1;
                Term term2 = (Term)o2;
                int p = Math.min(r, Math.min(term1.getQuery().length(), term2.getQuery().length()));
                
                for (int i = 0; i < p; i++) {
                    if ((int)term1.getQuery().charAt(i) == (int)term2.getQuery().charAt(i)) {
                        continue;
                    } else {
                        return (int)term1.getQuery().charAt(i) - (int)term2.getQuery().charAt(i);
                    }
                }
                // Prefix of two strings are same
                return 0;
            }
        };
        return comp;
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(ITerm that);

    /**
     * Returns a string representation of this term in the following format:
     * the weight, followed by a tab, followed by the query.
     * @return
     */
    public String toString();

}
