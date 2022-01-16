package analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph.*;
/**
 * @desc build and analyze CFG of a given method.
 * @author you
 * This is the final version
 */

public class CFGAnalyzer {
	/**
	 * TODO: build and analyze CFG of a given method. 
	 * 		 You can create auxiliary classes/functions if needed. 
	 */
	
	//STEP 0: DEFINE HOW TO DRAW GRAPH FOR IF, IF-RETURN, FOR
	
	String def_var = "def";	//def Python function
	String ret_var = "return";	//return from Py function
	
	String if_var = "if";	//if-else statement
	String for_var = "for";	//for statement
	String else_var = "else"; //else component
	boolean contains_for = false;

	DGraph<Integer> cfg = new ListDGraph<Integer>();
	int vtx = 1;
	
	public void read (String branch) throws Exception {

		String line;
		
		FileInputStream fileinput = new FileInputStream(branch);
		
		List<String> arrayoflines = new ArrayList<>();
		int counter = 0;	//number of lines
		
		//Buffer reader
		BufferedReader buffer = new BufferedReader(new InputStreamReader(fileinput));
		
		//while there are lines to read
		while((line = buffer.readLine()) != null) {
			arrayoflines.add(line);
			counter++;
		}
		
		//Always add def vertex just to start
		if (counter >= 1) {
			//add vertex but no edge
			cfg.addV(vtx);
			//increment vtx count
			vtx++;
		}
		
		int basic_counter = 0;
		boolean boolbasic = false;
		boolean connect = true;
		boolean runcfg2 = false;	//tells the program to run the second CFG
		boolean boolbasic2 = false;
		
		
		
		//for loop to iterate through each element in arrayoflines
		for (int i = 1; i < counter; i++) {
			//starts with index 1 (2nd element - def vtx was already added)
			String element = arrayoflines.get(i);
			int indentation = countIndentation(element);
			String trimmed = arrayoflines.get(i).trim();
			
			
			//INDENTATION IS 4
			if (indentation == 4) {
				
				//(1) reads if
				if (trimmed.startsWith(if_var)) {
					List<String> basicBlockList = basicBlocksInside(arrayoflines, arrayoflines.get(i));
					boolean hasReturn = containsReturn(basicBlockList);
					int sizeBasicBlock = basicBlockList.size();
					
					if (hasReturn) {
						connect= false;
						add_ifReturnStatement(cfg);
						i = i + sizeBasicBlock;
					}
					else {
						//add three vertices
						add_ifStatement(cfg);
						i = i + sizeBasicBlock;
					}
				}
				
				// (3) reads else
				else if (trimmed.startsWith(else_var)) {
					List<String> basicBlockList = basicBlocksInside(arrayoflines, arrayoflines.get(i));
//					boolean hasReturn = containsReturn(basicBlockList);
					int sizeBasicBlock = basicBlockList.size();
					basic_counter = sizeBasicBlock;
					i = i + sizeBasicBlock;
					
				}
				
				// (3) reads for
				else if (trimmed.startsWith(for_var)) {
					
					contains_for = true;
					add_for(cfg);
				}
				
				// (5) reads return
				else if (trimmed.startsWith(ret_var)) {
					String elementvar = arrayoflines.get(i - basic_counter-1);
					String trimelementvar = elementvar.trim();
					
					if (connect == true) {
						if (trimelementvar.startsWith(else_var)) {
							add_ret(cfg);
							cfg.addE(new Edge<Integer>(vtx-2, vtx));
						}
						else {
							add_ret(cfg);
						}
					}
					else {
						add_ret(cfg);
					}
				}
				
				// (6) else: it is a basic block
				else {
					if (boolbasic == false) {
						boolbasic = true;
						String elementvar = arrayoflines.get(i - basic_counter-1);
						String trimelementvar = elementvar.trim();
						if (trimelementvar.startsWith(else_var)) {
							cfg.addV(vtx);
							cfg.addE(new Edge<Integer>(vtx-1, vtx));
							cfg.addE(new Edge<Integer>(vtx-2, vtx));
							vtx++;
						}
						else {
							cfg.addV(vtx);
							cfg.addE(new Edge<Integer>(vtx-1, vtx));
							vtx++;
						}
					}
				}
			}
		}
		
		String lastline = arrayoflines.get(counter -1);
		String last_trimmed = lastline.trim();
		
		int index = 0;
		//where is 
		for (int i = 0; i < arrayoflines.size(); i++) {
			if (arrayoflines.get(i).trim().startsWith(for_var)) {
				index = i;
			}
		}
		
		int indexif = 0;
		//where is 
		for (int i = 0; i < arrayoflines.size(); i++) {
			if (arrayoflines.get(i).trim().startsWith(else_var)) {
				indexif = i;
			}
		}
		
		List<String> basicBlockList = basicBlocksInside(arrayoflines, arrayoflines.get(index));
		List<String> basicBlockListIf = basicBlocksInside(arrayoflines, arrayoflines.get(indexif));
		
		if(last_trimmed.startsWith(ret_var) == false) {
			String elementvar = arrayoflines.get(arrayoflines.size() - basicBlockList.size() -1);
			String trimelementvar = elementvar.trim();
			
			String elementvar1 = arrayoflines.get(arrayoflines.size() - basicBlockListIf.size() -1);
			String trimelementvar1 = elementvar1.trim();
			
			if (trimelementvar.startsWith(for_var)) {
				cfg.addV(vtx);
				cfg.addE(new Edge<Integer>(vtx-2, vtx));
			}
			else if (trimelementvar1.startsWith(else_var)) {
				cfg.addV(vtx);
				cfg.addE(new Edge<Integer>(vtx-1, vtx));
				cfg.addE(new Edge<Integer>(vtx-2, vtx));
			}
			else {
				cfg.addV(vtx);
				cfg.addE(new Edge<Integer>(vtx-1, vtx));
			}
//			cfg.addV(vtx);
//			cfg.addE(new Edge<Integer>(vtx-1, vtx));
		}
		
		// ******************************************************************
		//						THIS IS RUNNING CFG 2
		// ******************************************************************
		//NOW SEE IF WE SHOULD RUN CFG 2
		fileinput.close();
	}
	
	//Helper function: adds vertex when it reads 'def'
	public void add_def(DGraph<Integer> cfg) {
		//add vertex but no edge
		cfg.addV(vtx);
		//increment vtx count
		vtx++;
	}
	
	//Helper function: adds vertex when it reads an If Statement
	public void add_ifStatement(DGraph<Integer> cfg) {
		//creates 3 vertices
		
		//if component (start if)
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;

		//body if
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;
		
		//body else
		cfg.addV(vtx);
		Edge<Integer> edge1 = new Edge<Integer>(vtx-2, vtx);
		cfg.addE(edge1);
		vtx++;
	}
	
	public void add_ifReturnStatement(DGraph<Integer> cfg) {
		//adds 4 vertices
		
		//if component
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;

		//body if (start if)
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;
		
		//return
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;
		
		//body else
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-3, vtx));
		vtx++;
	}
	
	public void add_for(DGraph<Integer> cfg) {
		
		//for component (start for)
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;
		
		//last element reached?
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
		vtx++;
		
		//action inside for
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx, vtx-1)); //this step does not execute
		cfg.addE(new Edge<Integer>(vtx-1, vtx));	//this step does not execute and returns false.. WHY??
		vtx++;
	}
	
	public void add_ret(DGraph<Integer> cfg) {
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
	}
	
	public void add_basicBlock(DGraph<Integer> cfg) {
		cfg.addV(vtx);
		cfg.addE(new Edge<Integer>(vtx-1, vtx));
	}
	
	/**
	 * Helper method: counts the number of indentation in line
	 */
	public static int countIndentation(String line) {
		
		int counter = 0;
		for (int i = 0; i < line.length(); i++){
		    char c = line.charAt(i);
		    if ( c != ' ') {
		    	return counter;
		    }
		    else {
		    	counter++;
		    }
		}
		return counter;
   
	}
	
	/**
	 * Helper method: returns list of lines contained inside line
	 * For example: list1 = [if,    basic_block1,    basic_block2]
	 * basic_block1 and basic_block2 are contained inside if
	 * containsReturn(list1, "if") would return [basic_block1, basic_block2]
	 */
	public static List<String> basicBlocksInside(List<String> arrayoflines, String line) {
		int at = arrayoflines.indexOf(line);
		int startIndentation = countIndentation(line);
		List<String> arraycontains = new ArrayList<>();
		
		int i = at + 1;
		while (i < arrayoflines.size() && countIndentation(arrayoflines.get(i)) > startIndentation) {
			String element = arrayoflines.get(i);
			arraycontains.add(element);
			i++;
		}
//		for (int i = at + 1; i < arrayoflines.size(); i++) {
//			int indentation = countIndentation(arrayoflines.get(i));
//			if (indentation > startIndentation) {
//				String element = arrayoflines.get(i);
//				arraycontains.add(element);
//			}
//		}

		return arraycontains;
	}
	
	/**
	 * Helper method: returns true if the element contains return
	 */
	
	public static boolean containsReturn(List<String> arrayoflines) {
		
		boolean ret = false;
		for (int i = 0; i < arrayoflines.size(); i++) {
			String element = arrayoflines.get(i);
			String element_trimmed = element.trim();
			if (element_trimmed.startsWith("return")) {
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * Helper method: returns true if the element contains "for" or "if" keywords
	 */
	
	public static boolean containsForOrIf(List<String> arrayoflines) {
		
		boolean ret = false;
		for (int i = 0; i < arrayoflines.size(); i++) {
			String element = arrayoflines.get(i);
			String element_trimmed = element.trim();
			if (element_trimmed.startsWith("for") || element_trimmed.startsWith("if")) {
				ret = true;
			}
		}
		return ret;
	}
	
//	public static void main (String [] args) throws Exception {
//    	
//    	System.out.println(countIndentation("    for loop"));
//    	System.out.println(countIndentation("        for loop bla bla bla"));
//    	System.out.println(countIndentation("def function bla bla bla"));
//    	
//    	List<String> list1 = new ArrayList<>();
//    	list1.add("if");
//    	list1.add("    basic_block1");
//    	list1.add("    basic_block2");
//    	list1.add("return element");
//    	
//    	List<String> list2 = new ArrayList<>();
//    	list2 = basicBlocksInside(list1, "if");
//    	System.out.println(containsReturn(list2));
//    	
//    	
//    }
}

