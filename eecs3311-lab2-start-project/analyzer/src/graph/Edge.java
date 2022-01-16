package graph;

/**
 * The edge class; an edge has one source vertex and one destination vertex;
 * @author wangs and you
 * @param <V> 
 */
public class Edge<V> {
	
    /**source of an edge*/
    private V src;
    /**destination of an edge*/
    private V dest;
    
    /**
     * @param src
     * @param dest
     */
    
    //Constructor
    public Edge(V src, V dest) {
        this.src = src;
        this.dest = dest;
    }
    
    /**
     * get the source of this edge
     * @return
     */
    public V getSource() {
        return this.src;
    }
    
    /**
     * get the destination of this edge
     * @return
     */
    public V getDest() {
        return this.dest;
    }

    public boolean equals(Edge<V> o) { 
    	/**
		 * TODO: implement the comparison between two edges
		 * IFF `src` and `dest` are the same return true
		 */ 
    	
    	boolean bool1 = false;
    	//(1) Check if any of the edge1 or edge2 src or dest values are null
    	if (this.src.equals(null) || o.src.equals(null) || this.dest.equals(null) || o.dest.equals(null)) {
    		return bool1;
    	}
    	
    	//(2) If method hasn't returned, it means none of them are null. Thus, we now have to compare between both edges
    	if (this.dest.equals(o.dest) & this.src.equals(o.src)) {
    		bool1 = true;
    		return bool1;
    	}
    	
    	//(3) Otherwise, return false
		return bool1;
	}
    
    @Override
    public String toString() {
        String ret = String.format("src: %s , dest: %s", src, dest);
        return ret;
    } 
    	
    	
}

