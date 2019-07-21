package re;


import java.util.LinkedList;
import java.util.ArrayList;

import re.State;
import re.eightPuzzle;

class Node extends State {
    int depth; //level of the Node/ depth from root
    int hCost; //heuristic cost of the node	- we have used Manhattan distance to compute the heuristic cost
    State state;       //state designated to a node
    Node parent;       //parent node
    LinkedList<Node> children; //list of child nodes
    
    Node() {
    	depth = 0;
    	hCost = 0;
    	state = null;
    	parent = null;
    	children = null;
    }
    
    Node(int depth, int hCost, State state, Node parent) {
    	this.depth = depth;
    	this.hCost = hCost;
    	this.state = state;
    	this.parent = parent;
    	this.children = new LinkedList<>();
    }
    
   
    
    LinkedList<Node> getChildren(Node parent, State goalState) 
    {
        LinkedList<Node> childrenPtr = new LinkedList<>();
        State testState = null;
        Node child = null;

        
        //attempt to create states for each moves, and add to the list of children if true
        if(parent.state.action != Move.DOWN) 
        {
        	testState = createState(parent.state, Move.UP);
        	if(!statesMatch(testState, parent.state)) {
	            child = new Node(parent.depth + 1, manhattanDist(testState, goalState), testState, parent);
	            parent.children.add(child);
	            childrenPtr.add(child);
        	}
        }
        
        if(parent.state.action != Move.UP)
        {
            testState = createState(parent.state, Move.DOWN);
        	if(!statesMatch(testState, parent.state)) {
	            child = new Node(parent.depth + 1, manhattanDist(testState, goalState), testState, parent);
	            parent.children.add(child);
	            childrenPtr.add(child);
            }
        }
        
        if(parent.state.action != Move.RIGHT) {
            testState = createState(parent.state, Move.LEFT);
        	if(!statesMatch(testState, parent.state)) {
	            child = new Node(parent.depth + 1, manhattanDist(testState, goalState), testState, parent);
	            parent.children.add(child);
	            childrenPtr.add(child);
            }
        }
        
        if(parent.state.action != Move.LEFT)
        {
            testState = createState(parent.state, Move.RIGHT);
        	if(!statesMatch(testState, parent.state)) {
	            child = new Node(parent.depth + 1, manhattanDist(testState, goalState), testState, parent);
	            parent.children.add(child);
	            childrenPtr.add(child);
            }
        }

        return childrenPtr;
    }
}
