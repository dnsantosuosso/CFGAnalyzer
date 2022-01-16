package graph;

import java.util.LinkedList;
import java.util.List;

import util.Message;

/**
 * The vertex class; a Vertex object has a vertex and a list of edges started from it;
 * @author wangs and you
 * @param <V> 
 */
public class Vertex<V> {
	
	/**vertex */
    private V v;
    /** edges started from this vertex*/
    private List<Edge<V>> edgeList;
    
    /**
     * constructor
     * @param v
     */
    public Vertex(V v) {
        this.v = v;
        this.edgeList = new LinkedList<Edge<V>>();
    }
    
    public V getV() {
		return v;
	}

	public List<Edge<V>> getEdgeList() {
		return edgeList;
	}

	/**
     * add an edge to the edge list of this vertex.
     * if add successfully return true;
     * if edge exists: print `M3` and return false;
     * if `e`'s src is not this vertex: print `M5` and return false;
     * @param e
     */
    public boolean addEdge(Edge<V> e) {
       /**
        * TODO: add an edge to the edge list;
        */
    	
    	// If e's source is not this vertex - vertex does not exist
    	V source_ =  e.getSource();
    	if(this.v != source_) {
    		System.out.println(Message.M5);
    		return false;
    	}
    	
    	else {
    		 for(Edge<V> edge: edgeList) {
    			 // if edge exists
                 if(e.equals(edge)) {
                     System.out.println(Message.M3);
                     return false;
                 }
             }
    		 
    		 // Otherwise, add edge
    		 edgeList.add(e);
    		 return true;
    	}
    }
    
    /**
     * get an edge between this vertex and the destination V "dest";
     * if 'dest' does not exist: print `M5` and return null; 
     * if edge does not exist: print `M6` and return null; 
     * @param dest
     * @return 
     */
    public Edge<V> getEdge(V dest) {
    	/**
         * TODO: get the edge between this vertex and the destination V "dest";
         */
    	
    	// if dest does not exist: print M5 and return null
    	
//    	if(dest == null) {
//    		System.out.println(Message.M5);
//    		return null;
//    	}
    	// Prof said to ignore if dest == null
    	
    	// dest exists
    	if (dest != null) {
    		
    		//create edge
            Edge<V> edge1 = new Edge<V>(v, dest);
        	
            //Iterate through edges in EdgeList
            for(Edge<V> edge2: this.getEdgeList()) {
            	if(edge1.equals(edge2)) {
                	return edge2;
            	}
            }
            
            // edge does not exist: print M6 and return null
            
            System.out.println(Message.M6);
    		return null;
    	}
    	return null;
    }
    
    /**
     * remove an edge from the edge list of this vertex
     * if 'dest' exists return the removed edge;
     * if 'dest' does not exist: print `M5` and return null; 
     * if edge does not exist: print `M6` and return null; 
     * @param dest
     * @return removed Edge<V>
     */
    public Edge<V> removeEdge(V dest) {
    	/**
         * TODO: removed an edge
         */
    	
    	// If dest does not exist: print `M5` and return null
    	if(dest.equals(null)) {
        	System.out.println(Message.M5);
        	return null;
        }
    	
    	// If dest exists: iterate through edges in edgList
        for(Edge<V> edge1 : edgeList) {
        	//if dest == destination of edge1: remove edge1
        	if (dest.equals(edge1.getDest())) {
    			edgeList.remove(edge1);
    			return edge1;
    		}
        }
        
        // Otherwise, edge does not exist: print `M6` and return null
        System.out.println(Message.M6);	 		
    	return null; 
    }

    public boolean equals(Vertex<V> o) { 
		/**
		 * TODO: implement the comparison between two vertices
		 * IFF `v` and `edgeList` are the same return true
		 */
    	if (o.getV() == this.getV()) {
    		
    		String str1 = o.getEdgeList().toString();
        	String str2 = this.getEdgeList().toString();
        	
    		if (str1.equals(str2)) {
    			return true;
    		}
    	}
    	return false;
	}
    
    @Override
    public String toString() {
        String ret = String.format("v : %s , edges: %s", v, edgeList.toString());
        return ret;
    }    
    
//    public static void main (String [] args) throws Exception {
//    	
//    	Vertex<String> vertex1 = new Vertex<String>("Hello");
//    	Edge<String> edge1 = new Edge<String>("Hello","Bye");
//    	vertex1.addEdge(edge1);
//    	
//    	Vertex<String> vertex2 = new Vertex<String>("Hello");
//    	Edge<String> edge2 = new Edge<String>("Hello","Bye");
//    	vertex2.addEdge(edge2);
//    	
//    	System.out.println(vertex1.toString());
//    	System.out.println(vertex2.toString());
//    	
//    	System.out.println(vertex1.getV() == vertex2.getV());
//    	System.out.println(vertex1.getEdgeList().equals(vertex2.getEdgeList()));
//    	
//    	String str1 = vertex1.getEdgeList().toString();
//    	String str2 = vertex2.getEdgeList().toString();
//    	
//    	System.out.println(str1.equals(str2));
//    	System.out.println(vertex1.getEdgeList().toString());
//    	System.out.println(vertex2.getEdgeList().toString());
//    	
//    	System.out.println(vertex1.equals(vertex2));
//    	
//    }
}