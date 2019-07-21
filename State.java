package re;

class State 
{
	Move action;	//move that led to this State
	int[][] board = new int[3][3];	//resulting state of the board
	
	public static final int BLANK_SPACE = 0;	//denotes the blank box on the board
	
	/*Function arguments: state - current state
	 * 					  move - move to be applied to state
	 * Return value		: returns the resultant state if move is a valid move
	 */
	State createState(State state, Move move)
	{
		State newState = new State();
		
		//copy the board configuration of `state` to `newState`		
	   
	    int i, j;        //used for traversing the 3x3 arrays
	    int row=9, col=9;    //coordinates of the blank space char

	    for(i = 0; i < 3; i++) 
	    {
	        for(j = 0; j < 3; j++) 
	        {
	            if(state.board[i][j] == BLANK_SPACE) 
	            {		//search for the BLANK_SPACE
	                row = i;
	                col = j;
	            }

	            newState.board[i][j] = state.board[i][j];
	        }
	    }
	    
	    //check if the coordinates are valid and make changes in the new board configuration to show the new move made
	    //completing the actions and changing the board accordingly
	    if(move == Move.UP && (row - 1) >=0 && row<3 && col<3) 
	    {
	        int temp = newState.board[row - 1][col];
	        newState.board[row - 1][col] = BLANK_SPACE;
	        newState.board[row][col] = temp;
	        newState.action = Move.UP;
	    }
	    
	    else if(move == Move.DOWN  && row + 1 < 3) 
	    {
	        int temp = newState.board[row + 1][col];
	        newState.board[row + 1][col] = BLANK_SPACE;
	        newState.board[row][col] = temp;
	        newState.action = Move.DOWN;
	    }
	    
	    else if(move == Move.LEFT  && col - 1 >= 0 && row<3 && col<3) 
	    {
	        int temp = newState.board[row][col - 1];
	        newState.board[row][col - 1] = BLANK_SPACE;
	        newState.board[row][col] = temp;
	        newState.action = Move.LEFT;
	    }
	    
	    else if(move == Move.RIGHT && col + 1 < 3) {
	        int temp = newState.board[row][col + 1];
	        newState.board[row][col + 1] = BLANK_SPACE;
	        newState.board[row][col] = temp;
	        newState.action = Move.RIGHT;
	    }
	    
	    return newState;		//returns the new state or the same state if a move is invalid
	}
	
	/*Function arguments: curr - curr state
	 * 					  goal - goal state
	 * Return value		: returns the heuristic cost (here, Manhattan distance) between current state and goal state
	 */
	public int manhattanDist(State curr, State goal)
	{
		int x1, x2;		//coordinates in curr board
		int y1, y2;		//corresponding coordinates in goal board
		int xdist, ydist;
		int sum = 0;	
		int sumChanged = 0;
		
		//for coordinates (x1, y1) in curr, find corresponding coordinates (x2,y2) in goal board
		for(x1=0; x1<3; x1++)	
		{
			for(y1=0; y1<3; y1++)	
			{		
				for(x2=0; x2<3; x2++)	
				{
					for(y2=0; y2<3; y2++) 
					{
						if(curr.board[x1][y1] == goal.board[x2][y2])	
						{
							//find distance between row coord and column coord 
							xdist = java.lang.Math.abs(x1-x2);
							ydist = java.lang.Math.abs(y1-y2);
							
							sum = sum+(xdist + ydist);
							sumChanged = 1;
						}
					}
				}
			}
		}
		if(sumChanged==1)
			return sum;
		else
			return 999;
	}
	
	/*Function arguments: testState - state to be matched
	 * 					  goalState - state to be matched with
	 * Return value		: returns true if states match, else returns false
	 */
	boolean statesMatch(State testState, State goalState) 
	{
		for(int i=0; i<3; i++) 
		{
			for(int j=0; j<3; j++)	
			{
				if(testState.board[i][j] != goalState.board[i][j])	
				{
					return false;
				}
			}
		}
		return true;
	}
	
}


