package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import util.Message;

/**
 * Use LinkedList to implement the directed graph
 * @param <V>
 * @author wangs and you
 */
public class ListDGraph<V> implements DGraph<V>{
   
    /**list of the vertices in a graph*/
    private LinkedList<Vertex<V>> vList;
    
    /**
     * constructor
     */
    public ListDGraph() {
        vList = new LinkedList<Vertex<V>>();
    }
    
    @Override
    public int addV(V v) {
      /**
       * TODO: implement the addV function;
       */
        
    	//Set ID to 0
        int id = 0;
        
        //Create vertex
        Vertex<V> vertex1 = new Vertex<V>(v);
    	
        //Iterate through items on vList
    	for (Vertex<V> vertex2: this.vList) {
    		id++;
    		
    		//if vertex is already on vList
    		if (vertex1.getV() == vertex2.getV()) {
    			
    			//Print message M2 and return -1 (Based on DGraph Interface)
    			System.out.println(Message.M2);
    			return -1;
    		}		

    	}
    	
    	//add to vList
    	this.vList.add(vertex1); 
    	
    	//increment id
    	id++;
    	
    	//return id
    	return id; 
    }

    
    @Override
    public boolean addE(Edge<V> e) {
    	/**
        * TODO: implement the addE function;
        */
    	boolean bool1 = false;	//checks if edge has been added
    	boolean bool2 = false;	//checks if src or dest of the edge is not in the graph
    	
    	//Iterate through items on vList
    	for (int i = 0; i < this.vList.size(); i++) {
    		Vertex<V> vertex2 = this.vList.get(i);
    		//IF Statement 1.0 = Check if vertex2 is source or dest of e
			if(e.getSource() == vertex2.getV() || e.getDest() == vertex2.getV()) {
				
				//if IF Statement 1.0 body executes, IF Statement 2.0 body will not execute
				bool2 = true;		
			}
		}
    	
    	//Iterate through items on vList
    	for (int i = 0; i < this.vList.size(); i++) {
    		Vertex<V> vertex2 = this.vList.get(i);
    		//IF Statement 1.0 = Check if vertex2 is source
			if(e.getSource() == vertex2.getV()) {
				
				//Add edge (if added successfully, bool1 will be true)
				bool1 = vertex2.addEdge(e);
				
				break;		
			}
		}
    	
    	//IF Statement 2.0
    	if(bool2 == false)
    		System.out.println(Message.M5);
		
		return bool1;
    }
    
    @Override
    public V removeV(V v) {
    	/**
         * TODO: implement the removeV function;
         */
    	
    	boolean bool1 = false;
    	
    	//Iterate through items on vList
    	for (Vertex<V> vertex1 : this.vList) {
    		
			if (v == vertex1.getV()) {
				
				bool1 = true;
				
				//Remove vertex from vList
				this.vList.remove(v);
				break;
			}
		}	
    	
    	
    	if (bool1 == false)	//if v doesn't exist
    		System.out.println(Message.M5);	//print message M5
		
		return v;
    }

    @Override
    public Edge<V> removeE(Edge<V> e) {
    	/**
         * TODO: implement the removeE function;
         */
    	
    	boolean bool1 = false;
    	
    	//Create null edge
    	Edge<V> edge1 = null;
    	
    	//Iterate through items on vList
    	for (Vertex<V> vertex1 : this.vList) {
    		
    		V source = e.getSource();
    		V destination = e.getDest();
    		
    		//If vertex1 equals src of e
			if(vertex1.getV() == source) {
				
				bool1 = true;
				
				//remove edge from vertex1, make edge1 not null
				edge1 = vertex1.removeEdge(destination);
				break;
			}
		}
    	
    	//If e cannot be found, print message M6 and return null (edge1 will be null)
		if(bool1 == false)
			System.out.println(Message.M6);
		
		return edge1;
    }

    @Override
    public V getV(int index) {
    	/**
         * TODO: implement the getV function;
         */
    	int len = this.vList.size();
    	
    	//Check if index out of bounds
    	if (index > len || index < 0) {
    		//Print message M4 and return null
			System.out.println(Message.M4);
			return null;
		}
    	
    	V vertex1 = this.vList.get(index).getV();
    	
    	return vertex1;
    }

    @Override
    public Edge<V> getE(int src, int dest) {
    	/**
         * TODO: implement the getE function;
         */
    	
    	//Similar to removeE
    	
    	boolean bool1 = false;
    	
    	//Create null edge
   		Edge<V> edge1 = null;
   		
   		//Iterate through items on vList
		for (Vertex<V> vertex1: this.vList) {
			
			V source = this.getV(src);
			V destination = this.getV(dest);
			
			//If vertex1 equals src of edge
			if (vertex1.getV() == source) {
				
				bool1 =true;
				
				edge1 = vertex1.getEdge(destination);	//edge1 not null anymore
				break;
			}
		}
		
		//If 'src' or 'dest' of the edge is not in the graph: print message `M4` and return null (edge1 will be null)
		if(bool1 == false) {
			System.out.println(Message.M4);
		}
		return edge1;	//edge1 is null
    }

	@Override
	public ArrayList<ArrayList<V>> branches(V v) {
		
		//Iterate through each vertex in vList
		for (Vertex<V> vertex1 : vList) {
			//sets where to begin traversing
			if (v.equals(vertex1.getV())) {
				
				//Create 2D Array List
				ArrayList<ArrayList<V>> list2d = new ArrayList<ArrayList<V>>();
				ArrayList<V> list1 = new ArrayList<V>();
				
				//Call helper function
				dfs_helper(list2d,list1, vertex1);
				
				swapDups(list2d);
				return list2d;
			}
		}

		System.out.println(Message.M5);
		return null;
	}
	
	//Helper method for branches
	public void dfs_helper(ArrayList<ArrayList<V>> complexpath, ArrayList<V> path, Vertex<V> vtx) {

		// Define iterator variable
		Iterator<Edge<V>> var = vtx.getEdgeList().iterator();
		if (no3plusDups(path) == true) {
			//only keep traversing if element has next (which means it's not an end node)
			while (var.hasNext()) {
				Vertex<V> vertex1 = null;
				Edge<V> edge1 = var.next();
				V dest = edge1.getDest();	//child
				
				//Iterate through each vertex in vList
				for (Vertex<V> vertex2 : vList) {
					
					// if destination (child) equals value of vertex 2
					if (dest.equals(vertex2.getV()))
					{
						// make vertex 1 equal vertex 2
						vertex1 = vertex2;
						break;
						}
					}
				// Call helper function 2 (which calls back helper function 1) - only while vertex is not end node
				if (vertex1 != null){
					V element = vtx.getV();
					path.add(element);
					
					//if it's an end node - which means that has empty edgeList
					if((vertex1.getEdgeList().isEmpty() == true)){
						path.add(dest);
						
						ArrayList<V> innerpath = new ArrayList<V>(path);
						complexpath.add(innerpath);
						path.remove(dest);
					}
					
					else{
						//recursion
						dfs_helper(complexpath, path, vertex1);
						}
					}
				
				boolean consecutives = consecutiveDups(path);
//				if (consecutives == true) {
//					V element_to_remove = vtx.getV();
//					path.remove(element_to_remove);
//				}
				V element_to_remove = vtx.getV();
				path.remove(element_to_remove);
			}
		}
	}
	
	//Helper function: returns true if there are no duplicates
	public boolean no3plusDups(ArrayList<V> list1) {
		int size = list1.size();
		
		for (int i = 0; i < size; i++) {
			int count = 0;
			for (int j = 0; j < size; j++) {
				if (list1.get(i).equals(list1.get(j))) {
					count++;
				}
			}
			if (count > 1) {
				return false;
			}
		}
		return true;
	}
	
	//Helper function: returns true if there are no duplicates
	public boolean consecutiveDups(ArrayList<V> list1) {
		boolean consecutives = false;
		for (int i = 0; i < list1.size()-1; i ++) {
			if (list1.get(i).equals(list1.get(i + 1))) {
				consecutives = true;
			}
		}
		return consecutives;
	}
	
	//Helper function: returns true if there are no duplicates
		public ArrayList<ArrayList<V>> swapDups(ArrayList<ArrayList<V>> list2d) {
			for (int i = 0; i < list2d.size(); i++) {
				int size_sublist = list2d.get(i).size();
				for (int j = 0; j < size_sublist-1; j++) {
					if (list2d.get(i).get(j).equals(list2d.get(i).get(j+1))) {
						V b4dup = list2d.get(i).get(j-1);
						V dup1 = list2d.get(i).get(j);
						
						list2d.get(i).set(j-1, dup1);
						list2d.get(i).set(j, b4dup);
					}
				}
			}
			return list2d;
		}
	
	
	
    @Override
    public int [][] matrix() {
    	/**
    	 * TODO: generate the adjacency matrix of a graph;
    	 */
    	
    	int len = vList.size();
    	int create_edge = 1;
    	int[][] mat = new int[len][len];
    	
    	
    	for(Vertex<V> vtx: vList) {
    		for(Edge<V> edg: vtx.getEdgeList()) {
    			String src_str = String.valueOf(edg.getSource());
    			String dest_str = String.valueOf(edg.getDest());;
    			
    			int src_int = Integer.valueOf(src_str);
    			int dest_int = Integer.valueOf(dest_str);
    			
    			mat[src_int - 1][dest_int - 1] = create_edge;
    		}
    	}
    	
    	return mat;
    }	
    
//    public static void main (String [] args) throws Exception {
//    	
//    	DGraph<String> graph = new ListDGraph<String>();
//    	graph.addV("1");
//    	graph.addV("2");
//    	graph.addV("3");
//    	graph.addV("4");
//    	graph.addV("5");
//    	
//    	graph.addE(new Edge<String>("1", "2"));
//    	graph.addE(new Edge<String>("2", "3"));
//    	graph.addE(new Edge<String>("3", "4"));
//    	graph.addE(new Edge<String>("4", "3"));
//    	graph.addE(new Edge<String>("3", "5"));
//    	
//    	System.out.println(graph.branches("1"));
//    	
//    	ArrayList<ArrayList<Integer>> list2d = new ArrayList<ArrayList<Integer>>();
//		ArrayList<Integer> list1 = new ArrayList<Integer>();
//		ArrayList<Integer> list2 = new ArrayList<Integer>();
//		
//		list1.add(1);
//		list1.add(2);
//		list1.add(4);
//		list1.add(3);
//		list1.add(3);
//		
//		list2.add(1);
//		list2.add(2);
//		list2.add(3);
//		
//		list2d.add(list1);
//		list2d.add(list2);
//    	
//    	
//    	
//    }
}