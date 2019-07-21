package re;

import re.State;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.LinkedList;
import java.util.Scanner;

public class eightPuzzle extends Node {
	static int nodesGenerated;		//counts total number of nodes generated 
	static int nodesExpanded;		//counts number of nodes expanded every time
	static int totalMoves;			//total number of moves needed to reach the solution
	
	static long runTime;			//computes runtime of the BFS algorithm
	
	
	/*
	 * Function Arguments: inital - inital state
	 * 					   goal - goal state
	 * Return value		 : returns the solution of 8-puzzle in a linked list; returns null if solution can't be found.
	 */
	/*SolutionPath*/ ArrayList BFS (State initial, State goal) {
		
		nodesGenerated = 0;
		nodesExpanded = 0;
		totalMoves = 0;
		runTime = 0;
		
		LinkedList<Node> queue = new LinkedList<>();	//holds the nodes 
		//System.out.println("Created children linked list: "+children);
		Node node = null;
		
		 long start = System.currentTimeMillis();		//start time
		 
		 int mdist = manhattanDist(initial, goal);
		 queue.add(new Node(0, mdist, initial, null));	//add first node
		 while(!queue.isEmpty()) {		//while queue has nodes, do the following:
			 node = queue.removeFirst();	//get the last node 

			 if(statesMatch(node.state, goal)) 		//if their states match, break
			 {
				 break;
			 }
			 
			 if(!getChildren(node, goal).isEmpty()) {
			 	nodesGenerated += node.children.size();
			 	nodesExpanded++;
			 }
			 
			 if(!node.children.isEmpty())
			 {
				 queue.addAll(node.children);		//add all the child nodes to the queue
			 }
			 
			 
			 
		 }
		 
		 long finish = System.currentTimeMillis();	//finish time
		 runTime = finish - start;	
		 
		 //SolutionPath pathHead = new SolutionPath();
		 ArrayList<Node> pathHead = new ArrayList<>();
		 while(node!=null)	{
			 pathHead.add(node);
			 ++totalMoves;
			 node = node.parent;
		 }
		 
		 --totalMoves;
		 
		 return pathHead;
	}
	
	
	//This displays the '8-Puzzle Solver' ASCII art to the screen
	void welcomeUser()
	{
		System.out.println("Welcome to the 8 Puzzle Solver!");
	}

	//DESCRIPTION: This displays the input instructions for the user to read
	void printInstructions()
	{
		System.out.println
		(
	    	"------------------------------------------------------------------------\n"+
	    	"Instructions:\n"+
	    	"	Enter the initial and goal state of the 8-puzzle board. Input\n"+
	    	"	either integers 0-8, 0 representing the space character, to assign\n"+
	    	"	symbols to each board[row][col].\n"+
	    	"------------------------------------------------------------------------\n"
		);
	}

	/*getting the state from the user (non-repeated integer between 0-9)
	Called twice from main, one for initial and one for final state*/

	void inputState(State state)
	{
		Scanner sc= new Scanner(System.in);
		int row, col;
		int symbol;

		// flags for input validation
		int isNumUsed[] = new int[9];
		for(int i=0; i<9; i++)
		{
			isNumUsed[i]=0;
		}

	    	//taking input for the board, one element at a time
		for(row = 0; row < 3; ++row)
		{
	    	for(col = 0; col < 3; ++col)
	    	{
	        	System.out.println("	board["+row+"]["+col+"]: ");   	 

	        	// to prevent scanning newline from the input stream
	        	symbol=sc.nextInt();  				 

	        	// check if input is a blank character or is a number greater than 0 and less than 9
	        	if(symbol >= 0 && symbol < 9)
	        	{
	            	// check if input is repeated
	            	if(isNumUsed[symbol]==0)
	            	{
	                	state.board[row][col] = symbol;
	                	isNumUsed[symbol] = 1;
	            	}
	           	 
	            	else
	            	{
	                	System.out.print("	ERROR: Number "+symbol+" is already used. Try again with different input.\n");
	                	--col;
	            	}
	        	}
	        	else
	        	{
	            	System.out.print("	ERROR: Invalid input. Enter a number from 0 to 8.\n");
	            	--col;
	        	}
	    	}
		}
		System.out.println();
	}

	//Standard output display
	void printBoard(int board[][])
	{
		int row, col;

		for(row = 0; row < 3; ++row)
		{
	    	System.out.print("+---+---+---+\n");
	    	for(col = 0; col < 3; ++col)
	    	{
	        	System.out.print("| "+board[row][col]);
	    	}
	    	System.out.print("|\n");
		}
		System.out.print("+---+---+---+\n");
	}

	/* This function interprets numerical instructions of the move to make, to it's verbal counterpart to be displayed to the screen.
	Here, path is the solution path consisting of a list of nodes from the root to the goal */
	void printSolution( ArrayList path)
	{
	    //check if solution exists
		if(path.isEmpty())
		{
	    	System.out.print("No solution found.\n");
	    	return;
		}

		 //if the initial state is already the goal state
		else if(path.size()==1)
	    {
	   	 System.out.print("No moves needed. The initial state is already the goal state.\n");
	   	 return;
	    }

		else 
		{
			Iterator<Node> itr = path.iterator();
			System.out.println();
			while(itr.hasNext()) {
				System.out.print("->"+itr.next().state.action);
			}
		}
		
		System.out.print("\n\nSOLUTION: (Relative to the space character)\n");

		System.out.print(
	    	"DETAILS:\n - Solution length : "+ totalMoves + "\n"+
	   	 " - Nodes expanded  : "+ nodesExpanded+ "\n"+
	    	" - Nodes generated : "+ nodesGenerated+ "\n"+
	    	" - Runtime     	: "+ runTime + "\n"); //only counting allocated `Node`s
	}

	public static void main(String args[])
	{		
		eightPuzzle obj= new eightPuzzle();
		obj.welcomeUser();       	//display welcome message
		obj.printInstructions(); 	//display instructions
		
		State initial=new State();       	//initial board state
		State goalState=new State();     	//goal board configuration
	    
		//solution path of each search method
		ArrayList bfs;
		
		//input initial board state
		System.out.print("INITIAL STATE:\n");
		obj.inputState(initial);

		//input the goal state
		System.out.print("\nGOAL STATE:\n");
		obj.inputState(goalState);

		System.out.print("INITIAL BOARD STATE:\n");
		obj.printBoard(initial.board);

		System.out.print("GOAL BOARD STATE:\n");
		obj.printBoard(goalState.board);

		//perform breadth-first search
		bfs = obj.BFS(initial, goalState);
		System.out.println("\n------------------------- USING BFS ALGORITHM --------------------------\n");
		obj.printSolution(bfs);
	}


}


/*OUTPUT*
 
 ==test case #1: same initial state and goal state==
 CONCLUSION: SUCCESS

 Welcome to the 8 Puzzle Solver!
------------------------------------------------------------------------
Instructions:
	Enter the initial and goal state of the 8-puzzle board. Input
	either integers 0-8, 0 representing the space character, to assign
	symbols to each board[row][col].
------------------------------------------------------------------------

INITIAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0


GOAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
7
	board[2][0]: 

	ERROR: Invalid input. Enter a number from 0 to 8.
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0

INITIAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+
GOAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+

------------------------- USING BFS ALGORITHM --------------------------

No moves needed. The initial state is already the goal state.

 */
/*
==test case #2: only one move needed==
CONCLUSION: SUCCESS

Welcome to the 8 Puzzle Solver!
------------------------------------------------------------------------
Instructions:
	Enter the initial and goal state of the 8-puzzle board. Input
	either integers 0-8, 0 representing the space character, to assign
	symbols to each board[row][col].
------------------------------------------------------------------------

INITIAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
0
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
6


GOAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0

INITIAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 0|
+---+---+---+
| 7| 8| 6|
+---+---+---+
GOAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+

------------------------- USING BFS ALGORITHM --------------------------

->DOWN->null

SOLUTION: (Relative to the space character)
DETAILS:
 - Solution length : 1
 - Nodes expanded  : 2
 - Nodes generated : 4
 - Runtime     	: 1


===testcase#3: 2 moves needed to reach goalstate===
CONCLUSION: SUCCESS

Welcome to the 8 Puzzle Solver!
------------------------------------------------------------------------
Instructions:
	Enter the initial and goal state of the 8-puzzle board. Input
	either integers 0-8, 0 representing the space character, to assign
	symbols to each board[row][col].
------------------------------------------------------------------------

INITIAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
0
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
3
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
6


GOAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0

INITIAL BOARD STATE:
+---+---+---+
| 1| 2| 0|
+---+---+---+
| 4| 5| 3|
+---+---+---+
| 7| 8| 6|
+---+---+---+
GOAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+

------------------------- USING BFS ALGORITHM --------------------------

->DOWN->DOWN->null

SOLUTION: (Relative to the space character)
DETAILS:
 - Solution length : 2
 - Nodes expanded  : 3
 - Nodes generated : 6
 - Runtime     	: 2


===testcase#4: 3 moves needed to reach goal===
CONCLUSION: SUCCESS

Welcome to the 8 Puzzle Solver!
------------------------------------------------------------------------
Instructions:
	Enter the initial and goal state of the 8-puzzle board. Input
	either integers 0-8, 0 representing the space character, to assign
	symbols to each board[row][col].
------------------------------------------------------------------------

INITIAL STATE:
	board[0][0]: 
1
	board[0][1]: 
0
	board[0][2]: 
2
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
3
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
6


GOAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0

INITIAL BOARD STATE:
+---+---+---+
| 1| 0| 2|
+---+---+---+
| 4| 5| 3|
+---+---+---+
| 7| 8| 6|
+---+---+---+
GOAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+

------------------------- USING BFS ALGORITHM --------------------------


->DOWN->DOWN->RIGHT->null

SOLUTION: (Relative to the space character)
DETAILS:
 - Solution length : 3
 - Nodes expanded  : 17
 - Nodes generated : 28
 - Runtime     	: 1


===testcase#5: random input=== 
CONCLUSION: NO RESULT. BFS is an inefficient algorithm for difficult problem state due to very high space and time complexity.

Welcome to the 8 Puzzle Solver!
------------------------------------------------------------------------
Instructions:
	Enter the initial and goal state of the 8-puzzle board. Input
	either integers 0-8, 0 representing the space character, to assign
	symbols to each board[row][col].
------------------------------------------------------------------------

INITIAL STATE:
	board[0][0]: 
2
	board[0][1]: 
1
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
8
	board[2][0]: 
6
	board[2][1]: 
7
	board[2][2]: 
0


GOAL STATE:
	board[0][0]: 
1
	board[0][1]: 
2
	board[0][2]: 
3
	board[1][0]: 
4
	board[1][1]: 
5
	board[1][2]: 
6
	board[2][0]: 
7
	board[2][1]: 
8
	board[2][2]: 
0

INITIAL BOARD STATE:
+---+---+---+
| 2| 1| 3|
+---+---+---+
| 4| 5| 8|
+---+---+---+
| 6| 7| 0|
+---+---+---+
GOAL BOARD STATE:
+---+---+---+
| 1| 2| 3|
+---+---+---+
| 4| 5| 6|
+---+---+---+
| 7| 8| 0|
+---+---+---+


*/