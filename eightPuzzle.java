//package re;

//import re.State;
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


