package student;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import graph.DGraph;
import graph.Edge;
import graph.ListDGraph;
import graph.Vertex;

public class StudentTest {
 /**
  * TODO: 
  * 	write Junit test cases to test your implementation of ListDGraph;
  */
	
DGraph<String> graph = new ListDGraph<String>();
DGraph<String> graph1 = new ListDGraph<String>();
DGraph<String> graph2 = new ListDGraph<String>();
DGraph<String> graph3 = new ListDGraph<String>();
DGraph<String> graph4 = new ListDGraph<String>();
DGraph<String> graph5 = new ListDGraph<String>();
DGraph<String> graph6 = new ListDGraph<String>();
DGraph<String> graph7 = new ListDGraph<String>();
DGraph<String> graph8 = new ListDGraph<String>();
DGraph<String> graph9 = new ListDGraph<String>();
DGraph<String> graph10 = new ListDGraph<String>();
    
    @Before
	public void setUp() throws Exception {
	 	graph1.addV("1");
        graph1.addV("2");
        graph1.addV("3");
        graph1.addV("4");
        graph1.addV("5");
        graph1.addV("6");
        graph1.addV("7");
        graph1.addV("8");
        
        graph1.addE(new Edge<String>("1", "2"));
        graph1.addE(new Edge<String>("2", "3"));
        graph1.addE(new Edge<String>("2", "7"));
        graph1.addE(new Edge<String>("7", "8"));
        graph1.addE(new Edge<String>("3", "4"));
        graph1.addE(new Edge<String>("4", "5"));
        graph1.addE(new Edge<String>("5", "6"));
        graph1.addE(new Edge<String>("6", "5"));
        graph1.addE(new Edge<String>("5", "8"));
        
        graph.addV("1");
        graph.addV("2");
        graph.addV("3");
        graph.addV("4");  
                
        graph.addE(new Edge<String>("1", "2"));
        graph.addE(new Edge<String>("1", "3"));
        graph.addE(new Edge<String>("2", "3"));
        graph.addE(new Edge<String>("2", "4"));
        
        //same as graph1 but with no loops
        graph10.addV("1");
        graph10.addV("2");
        graph10.addV("3");
        graph10.addV("4");
        graph10.addV("5");
        graph10.addV("6");
        graph10.addV("7");
        graph10.addV("8");
        
        graph10.addE(new Edge<String>("1", "2"));
        graph10.addE(new Edge<String>("2", "3"));
        graph10.addE(new Edge<String>("2", "7"));
        graph10.addE(new Edge<String>("7", "8"));
        graph10.addE(new Edge<String>("3", "4"));
        graph10.addE(new Edge<String>("4", "5"));
        graph10.addE(new Edge<String>("5", "6"));
        graph10.addE(new Edge<String>("5", "8"));
	}
    
    @Test
    public void test_addV() { 
		int index = graph.addV("1");
		Assert.assertEquals(-1, index);
     }
    
    @Test
    public void test_addE() { 
		
		boolean bool = true;
		boolean boolf = false;
		Edge<String> edg = new Edge<String>("1", "2");
		boolean success = graph.addE(edg);
		Assert.assertEquals(success, boolf);

		Edge<String> edg1 = new Edge<String>("1", "4");
		success = graph.addE(edg1);
		Assert.assertEquals(success, bool);
     }

	@Test
    public void test_branches_1() { 
        //Iterate the graph from V "1"
    	ArrayList<ArrayList<String>> bs;
    	bs = graph10.branches("1");

    	ArrayList<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
    	ArrayList<String> b1 = new ArrayList<String>();
    	b1.add("1"); b1.add("2"); b1.add("7"); b1.add("8");
    	
    	ArrayList<String> b2 = new ArrayList<String>();
    	b2.add("1"); b2.add("2"); b2.add("3"); b1.add("4"); b1.add("5"); b1.add("8"); 
    	
    	ArrayList<String> b3 = new ArrayList<String>();
    	b3.add("1"); b2.add("2"); b2.add("3"); b1.add("4"); b1.add("5"); b1.add("6"); b1.add("5"); b1.add("8");
    	
    	expected.add(b1);
    	expected.add(b2);
    	expected.add(b3);
        Assert.assertEquals(bs.size(), expected.size());
     }
	
	@Test
    public void test_branches_2() { 
        //Iterate the graph from V "1"
    	ArrayList<ArrayList<String>> bs;
    	bs = graph1.branches("15");
        Assert.assertEquals(bs, null);
     }
	
	
	@Test
    public void test_matrix_1() {
		/**
		 * generate the matrix
		 */
		
		int [][] matrix = graph1.matrix();
		
		/**
		 * expected matrix `m`
		 */
		 int m [][] = new int[8][8];
    	 m[0][0] =0; m[0][1] =1; m[0][2] =0; m[0][3] =0; m[0][4] =0; m[0][5] =0; m[0][6] =0; m[0][7] =0;
    	 m[1][0] =0; m[1][1] =0; m[1][2] =1; m[1][3] =0; m[1][4] =0; m[1][5] =0; m[1][6] =1; m[1][7] =0;
    	 m[2][0] =0; m[2][1] =0; m[2][2] =0; m[2][3] =1; m[2][4] =0; m[2][5] =0; m[2][6] =0; m[2][7] =0;
    	 m[3][0] =0; m[3][1] =0; m[3][2] =0; m[3][3] =0; m[3][4] =1; m[3][5] =0; m[3][6] =0; m[3][7] =0;
    	 m[4][0] =0; m[4][1] =0; m[4][2] =0; m[4][3] =0; m[4][4] =0; m[4][5] =1; m[4][6] =0; m[4][7] =1;
    	 m[5][0] =0; m[5][1] =0; m[5][2] =0; m[5][3] =0; m[5][4] =1; m[5][5] =0; m[5][6] =0; m[5][7] =0;
    	 m[6][0] =0; m[6][1] =0; m[6][2] =0; m[6][3] =0; m[6][4] =0; m[6][5] =0; m[6][6] =0; m[6][7] =1;
    	 m[7][0] =0; m[7][1] =0; m[7][2] =0; m[7][3] =0; m[7][4] =0; m[7][5] =0; m[7][6] =0; m[7][7] =0;
    	
    	
        Assert.assertEquals(matrix.length, m.length);
        
        String str1 = "";
        String str_expected = "";
        
        for (int num = 0; num < matrix.length; num++) {
        	for (int num2 = 0; num2 < matrix.length; num2++) {
        		str1 = str1 + matrix[num][num2];
        	}
        }
        
        for (int num = 0; num < m.length; num++) {
        	for (int num2 = 0; num2 < m.length; num2++) {
        		str_expected = str_expected + m[num][num2];
        	}
        }
        System.out.println(str1);
        System.out.println(str_expected);
        
        System.out.println(m[3][0]);
        
        Assert.assertEquals(str1, str_expected);
     }
	
	@Test
    public void test_matrix_2() {
		
		graph2.addV("1");
		
		/**
		 * generate the matrix
		 */
		int [][] matrix = graph2.matrix();
    	
		int expc_length = 1;
		
        Assert.assertEquals(matrix.length, expc_length);
     }
	
	@Test
    public void test_matrix_3() {
		
		graph3.addV("1");
		graph3.addV("2");
		graph3.addV("3");
		graph3.addV("4");
		
		/**
		 * generate the matrix
		 */
		int [][] matrix2 = graph3.matrix();
    	
		int expc_length = 4;
		
        Assert.assertEquals(matrix2.length, expc_length);
     }
	
	@Test
    public void test_matrix_4() {
	 
		int [][] matrix = graph.matrix();
		int len = 4;
		
		int m [][] = new int[len][len];
        m[0][0] =0; m[0][1] =1; m[0][2] =1; m[0][3] =1;
        m[1][0] =1; m[1][1] =0; m[1][2] =1; m[1][3] =0;
        m[2][0] =1; m[2][1] =1; m[2][2] =0; m[2][3] =0;
        m[3][0] =1; m[3][1] =0; m[3][2] =0; m[3][3] =0;
        
        int lenmat = matrix.length;
        int lenexpected = m.length;
        Assert.assertEquals(lenexpected, lenmat);
     }
    
    @Test
    public void test_matrix_5() {
        int [][] matrix = graph.matrix();
        int len = 4;
        
         int m [][] = new int[len][len];
         m[0][0] =0; m[0][1] =1; m[0][2] =1; m[0][3] =0;
         m[1][0] =0; m[1][1] =0; m[1][2] =1; m[1][3] =1;
         m[2][0] =0; m[2][1] =0; m[2][2] =0; m[2][3] =0;
         m[3][0] =0; m[3][1] =0; m[3][2] =0; m[3][3] =0;
        
        Assert.assertArrayEquals(matrix, m);
     }
	
	@Test
    public void test_getV_1() {
		
		graph4.addV("1");
		graph4.addV("2");
		graph4.addV("3");
		graph4.addV("4");
		
		graph4.addE(new Edge<String>("1", "2"));
		graph4.addE(new Edge<String>("2", "3"));
		graph4.addE(new Edge<String>("3", "4"));
		
		
		String str1 = "";
		String str2 = "1234";	//Expected string
		
		for (int index = 0; index < 4; index++) {
			str1 = str1 + graph4.getV(index);
		}
		Assert.assertEquals(str1, str2);
     }
	
	@Test
    public void test_getV_2() {
		
		graph5.addV(" ");
		
		
		String str1 = "";
		String str2 = " ";
		
		for (int index = 0; index < 1; index++) {
			str1 = str1 + graph5.getV(index);
		}
		Assert.assertEquals(str1, str2);
     }
	
	@Test
    public void test_removeE_1() { 
		Edge<String> e1 = new Edge<String>("1", "4");
		graph1.addE(e1);
	
		Edge<String> e2 = graph1.removeE(new Edge<String>("1", "4"));
		
        Assert.assertEquals(true, e1.equals(e2));
     }
	
	@Test
    public void test_removeV_1() { 
    	String v = graph.removeV("4");
        Assert.assertEquals("4", v);
     }
	
	@Test
    public void test_getE_1() { 
    	
		String src = graph1.getE(0, 1).getSource();
		String dest = graph1.getE(0, 1).getDest();
		
		Assert.assertEquals(src, "1");
		Assert.assertEquals(dest, "2");
     }
	
	@Test
    public void test_edge_equals_1() { 
		
    	Edge<String> edge1 = new Edge<String>("A","B");
    	Edge<String> edge2 = new Edge<String>("A","B");
    	
    	Assert.assertEquals(edge1.equals(edge2), true);
    	
     }
	
	@Test
    public void test_edge_toString_1() { 
		
    	Edge<String> edge1 = new Edge<String>("A","B");
    	
    	String str_test = edge1.toString();
    	String str_expected = "src: A , dest: B";
    	
    	Assert.assertEquals(str_test, str_expected);
     }
	
	@Test
    public void test_removeV() { 
    	String vtx = graph.removeV("4");
    	String expected = "4";
        Assert.assertEquals(vtx, expected);
     }
	
	@Test
    public void test_removeE() { 
		boolean bool = true;
		
		Edge<String> edg1 = new Edge<String>("1", "4");
		graph.addE(edg1);
	
		Edge<String> edg2 = graph.removeE(new Edge<String>("1", "4"));
		
        Assert.assertEquals(edg2.equals(edg1), bool);
     }
	
	@Test
    public void test_vertex_toString_1() { 
		
    	Vertex<String> vertex1 = new Vertex<String>("Hello");
    	Edge<String> edge1 = new Edge<String>("Hello","Bye");
    	vertex1.addEdge(edge1);
    	
    	Assert.assertEquals("v : Hello , edges: [src: Hello , dest: Bye]", vertex1.toString());
     }
	
	@Test
    public void test_vertex_equals_2() { 
		
    	Vertex<String> vertex1 = new Vertex<String>("Hello");
    	Edge<String> edge1 = new Edge<String>("Hello","Bye");
    	vertex1.addEdge(edge1);
    	
    	Vertex<String> vertex2 = new Vertex<String>("Hello");
    	Edge<String> edge2 = new Edge<String>("Hello","Bye");
    	vertex2.addEdge(edge2);

    	Assert.assertEquals(vertex1.equals(vertex2), true);
     }
	
	@Test
    public void test_main() { 
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
		System.out.println("******************************");
		System.out.println("TEST SOFTWARE");
		
		
		
		
    	
     }
	
	
}
