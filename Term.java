
public class Term implements ITerm {
    
    private String query;
    private long weight;

    public Term(String query, long weight) throws IllegalArgumentException {
        // if query is null or weight negative
        if (query == null || weight < 0) {
            throw new IllegalArgumentException();
        } else {
            this.query = query;
            this.weight = weight;
        }
    }

    @Override
    public int compareTo(ITerm that) {
        return this.getQuery().compareTo(((Term) that).getQuery());
    }
    
    @Override
    public String toString() {
        return this.weight + "\t" + this.query;
    }
    
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
    
    public String getTerm() {
        return query;
    }

    public void setTerm(String query) {
        this.query = query;
    }
    
    

}
