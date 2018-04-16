package connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Connect4Utils {

    public static final Color PANEL_COLOR = Color.GREEN;
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final Color COMPUTER = Color.BLACK;
    public static final Color HUMAN = Color.RED;
    public static final Color NONE = Color.WHITE;

    public static int getMoveForComputer(Color[][] board, int depth) {
       int[] result = minmax(board, depth, COMPUTER);
   	   return result[1];//location one has column number where computer will drop disk
   }
    
	 public static int[] minmax(Color[][] board, int depth, Color player) {
	
	   int bestScore = (player == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
	   int currentScore;
	   int bestCol = -1;
	   
	   if(isPanelFull(board) || depth == 0){
			   bestScore = getHeuristicScore(board);
	   }else {
		   List<Integer> columns = new ArrayList<Integer>();
		   for(int col=0; col < COLUMNS; col++){     
	           if(board[ROWS-1][col]==Connect4Utils.NONE) {   
	        	   dropDisk(board, player, col);
	        	   if(player == COMPUTER){//maximize
	        		   currentScore = minmax(board, depth-1, HUMAN)[0];
	        		   if (currentScore >= bestScore) {
	        			   if(currentScore == bestScore){
	        				   columns.add(col);
	        			   }else{
	        				   columns = new ArrayList<Integer>();
	        			   }
	                       bestScore = currentScore;
	                       bestCol = col;
	        		   }
	        	   }else{
	        		   currentScore = minmax(board, depth-1, COMPUTER)[0];
	        		   if (currentScore <= bestScore) {
	                       bestScore = currentScore;
	                       bestCol = col;
	        		   }
	        	   }
	        	   undoDrop(board, col);
	           }
		   	}
		   
		   if(player == COMPUTER){
			   if(columns.size() > 1){
				 bestCol = getBestColumnInCaseOfDuplicates(columns, board);
			   }
		   }
	     }
	   	 return new int[] {bestScore,bestCol};
	  }
	 
	 public static int getBestColumnInCaseOfDuplicates(List<Integer> columns,Color[][] board){
		   int bestCol = 0;
		
		   Random rd = new Random();
		   boolean foundCol = false;
		   for (Integer col : columns) {
			   for(int row=0;row<ROWS;row++){
				   
				   if(col < COLUMNS-1){
					   if(board[row][col+1] == Connect4Utils.HUMAN){
						   bestCol = col;
						   foundCol = true;
					   }
				   }
				   
				   if((col-1) > 0){
					   if(board[row][col-1] == Connect4Utils.HUMAN){
						   bestCol = col;
						   foundCol = true;
					   }
				   }
				   
				   if(row < ROWS-1){
					   if(board[row+1][col] == Connect4Utils.HUMAN){
						   bestCol = col;
						   foundCol = true;
					   }
				   }
				   
			   }
		   }
		   if(!foundCol)
		    bestCol = columns.get(rd.nextInt(columns.size()-1));
		   
		   return bestCol;
	 }
	 
	 private static int getBestScore(List<Integer> hScore,List<Integer> cScore){
		 int hs = 0;
		   int cs = 0;
		   if(hScore.size() > 0){
			   Collections.sort(hScore);
			   hs = hScore.get(hScore.size() - 1);
		   }
		   
		   if(cScore.size() > 0){
			   Collections.sort(cScore);
			   cs = cScore.get(cScore.size() - 1);
		   }
		   int bestScore = 0;
		   if(hs > cs){
			   bestScore = -hs;
		   }else{
			   bestScore = cs;
		   }
		   return bestScore;
	 }
    

    private static int getHeuristicScore(Color[][] field){
    	int humanHScore = getScoreInRows(field, HUMAN);
    	int humanVScore = getScoreInColumns(field, HUMAN);
    	int humanDScore = getScoreDiagonals(field, HUMAN);
    	int computerHScore = getScoreInRows(field, COMPUTER);
    	int computerVScore = getScoreInColumns(field, COMPUTER);
    	int computerDScore = getScoreDiagonals(field, COMPUTER);
    	int hScore = 0;
    	
    	if(humanHScore == 1 || humanVScore == 1 || humanDScore == 1){
    		hScore = 1;
    	}
    	
    	if(humanHScore == 2 || humanVScore == 2 || humanDScore == 2){
    		hScore = 2;
    	}
    	
    	if(humanHScore == 3 || humanVScore == 3 || humanDScore == 3){
    		hScore = 3;
    	}
    	
    	if(humanHScore > 4 || humanVScore > 4 || humanDScore > 4){
    		hScore = 2;
    	}
    	
    	if(humanHScore == 4 || humanVScore == 4 || humanDScore == 4){
    		hScore = 4;
    	}
    	
    	int cScore = 0;
    	
    	if(computerHScore == 1 || computerVScore == 1 || computerDScore == 1){
    		cScore = 1;
    	}
    	
    	if(computerHScore == 2 || computerVScore == 2 || computerDScore == 2){
    		cScore = 2;
    	}
    	
    	if(computerHScore == 3 || computerVScore == 3 || computerDScore == 3){
    		cScore = 3;
    	}
    	
    	if(computerHScore > 4 || computerVScore > 4 || computerDScore > 4){
    		cScore = 2;
    	}
    	
    	if(computerHScore == 4 || computerVScore == 4 || computerDScore == 4){
    		cScore = 4;
    	}
    	
    	if(hScore > cScore){
    		return -hScore;
    	}
    	
    	return cScore;
    }
    
    
    public static int getScoreInRows(Color[][] board,Color player) {
    	int counter = 0;
    	int playerFound = 0;
    	int rowValue = -1;
    	for (int row = 0; row < ROWS; row++) {
            int count = 0;
            int counterLCL = 0;
            for (int column = 1; column < COLUMNS; column++) {
            	if (board[row][column] == player){
            		playerFound = 1;
            	}else{
            		if(counterLCL > 0){
            			count = 0;
            		}
            	}
                if (board[row][column] == player &&
                    board[row][column] == board[row][column-1])
                {
                	count++;
                }
                if(count > counterLCL){
                	counterLCL = count;
                }
            }
            if(counterLCL > counter){
            	counter = counterLCL;
            	rowValue = row;
            }
        }
    	counter = counter + playerFound;
    	if(rowValue >= 0 && counter != 4){
       	 if (board[rowValue][3] != NONE && board[rowValue][3] == getOppositePlayer(player)){
            	counter = 0;
            }
       }
        return counter;
    }

    public static int getScoreInColumns(Color[][] board,Color player) {
    	int counter = 0;
    	int playerFound = 0;
    	int colValue = -1;
        for (int column = 0; column < COLUMNS; ++column) {
            int count = 0;
            int counterLCL = 0;
            for (int row = 1; row < ROWS; ++row) {
            	if (board[row][column] == player){
            		playerFound = 1;
            	}else{
            		if(counterLCL > 0){
            			count = 0;
            		}
            	}
            	
                if (board[row][column] == player &&
                    board[row][column] == board[row-1][column])
                {
                	++count; 
                }
                if(count > counterLCL){
                	counterLCL = count;
                }
            }
            if(counterLCL > counter){
            	counter = counterLCL;
            	colValue = column;
            }
        }
        counter = counter + playerFound;
        
        if(colValue >= 0 && counter != 4){
        	 if (board[2][colValue] != NONE && board[2][colValue] == getOppositePlayer(player)){
             	counter = 0;
             }
             
             if (board[3][colValue] != NONE && board[3][colValue] == getOppositePlayer(player)){
             	counter = 0;
             }
        }
        
        return counter;
    }


    public static int getScoreDiagonals(Color[][] board,Color player) {
    	int counter = 0;
    	int playerFound = 0;
    	for (int column = 0; column < COLUMNS; ++column) {
            int counterLCL = 0;
        	int count = 0;
            for (int row = 1; row < ROWS; ++row) {
                
            	if (column + row >= ROWS){
                	break;
                }
            	
            	try{
            		if (board[row][column+row] == player){
                		playerFound = 1;
                	}else{
                		if(counterLCL > 0){
                			count = 0;
                		}
                	}
                    
                    if (board[row][column+row] == player &&
                        board[row-1][column + (row-1)] == board[row][column+row])
                    {
                    	++count;
                    }
                    
                    if(count > counterLCL){
                    	counterLCL = count;
                    }
                    
            	}catch(Exception e){
            		//continue in case of arrayindex out of bound exception
            	}
                
            }
            if(counterLCL > counter){
            	counter = counterLCL;
            }
            
        }

        for (int row = 0; row < ROWS; ++row) {
            int count = 0;
            int counterLCL = 0;
        	for (int column = 1; column < COLUMNS; ++column) {
        		try{ 
                if (column + row >= ROWS) {
                	break;
                }
                if (board[row + column][column] == player){
            		playerFound = 1;
            	}else{
            		if(counterLCL > 0){
            			count = 0;
            		}
            	}
                
                if (board[row + column][column] == player &&
                    board[row+(column-1)][column - 1] == board[row + column][column]){
                	 ++count;
                }
                if(count > counterLCL){
                	counterLCL = count;
                }
        		}catch(Exception e){
            		//continue in case of arrayindex out of bound exception
            	}
            }
            if(counterLCL > counter){
            	counter = counterLCL;
            }
        }

        if(counter < 4){
        	for (int column = 0; column < COLUMNS; ++column) {
                int count = 0;
                int counterLCL = 0;
                for (int row = 1; row < ROWS; ++row) {
                	try{    
                		if (column - row < 0) {
                        	break;
                        }
                    	
                    	if (board[row][column-row] == player){
                    		playerFound = 1;
                    	}else{
        	           		if(counterLCL > 0){
        	           			count = 0;
        	           		}
                   	    }
                        
                        if (board[row][column-row] == player &&
                            board[row - 1][column - row + 1] == board[row][column-row]){
                        	++count;
                        }
                        if(count > counterLCL){
                        	counterLCL = count;
                        }
                	}catch(Exception e){
                		//continue in case of arrayindex out of bound exception
                	}
                }
                if(counterLCL > counter){
                	counter = counterLCL;
                }
                
            }
        }
        
        if(counter < 4){
        	  for (int row = 0; row < ROWS; ++row) {
                  int count = 0;
                  int counterLCL = 0;
                  for (int column = 5; column >= 0; --column) {
                	  
                	try{    
                		if (column - row < 0)
                      	{ 
                          	break;
                          }
                          
                          if ((column - (row - 1))  < 0)
                          { 
                          	break; 
                          }
                          
                          if (board[column - row][column] == player){
                      		playerFound = 1;
                      	}else{
          	           		if(counterLCL > 0){
          	           			count = 0;
          	           		}
                     	    }
                          
                          if (board[column - row][column] == player &&
                              board[column - (row - 1)][column + 1] == board[column - row][column])
                          {
                          	 ++count;
                          }
                          if(count > counterLCL){
                          	counterLCL = count;
                          }
                  	}catch(Exception e){
                  		//continue in case of arrayindex out of bound exception
                  	}
                  }
                  if(counterLCL > counter){
                  	counter = counterLCL;
                  }
                  
              }
        }
        counter = counter + playerFound;
        return counter;
    }
    
    public static boolean isValidMove(Color[][] board, int column) {
    	for(int row = 0; row < Connect4Utils.ROWS; row++) {
    		//if we find at-least one column as NONE the move is valid
    		if(board[row][column] == Connect4Utils.NONE){
    			return true;
    		}
        }
    	return false;
    }
    
    public static boolean isPanelFull(Color[][] board) {
        for(int row = 0; row < Connect4Utils.ROWS; row++) {
            for (int col = 0; col < Connect4Utils.COLUMNS; col++) {
                if(board[row][col] == Connect4Utils.NONE){
                	return false;
                }
            }
        }
        return true;
    }

    public static void dropDisk(Color[][] board, Color color, int column) {
    	for(int row = 0; row < Connect4Utils.ROWS; row++) {
    		if(board[row][column] == Connect4Utils.NONE){
    			board[row][column] = color;
    			return;
    		}
        }
    }
    
    public static void undoDrop(Color[][] board, int column) {
        int row = ROWS - 1;
        while(board[row][column] == NONE && row > 0) {
            row--;
        }
        board[row][column] = NONE;
    }

    
    public static Color getOppositePlayer(Color color) {
        if(color == Connect4Utils.COMPUTER){
        	return Connect4Utils.HUMAN;
        }
        return Connect4Utils.COMPUTER;
    }

    public static Color findWinner(Color[][] matrix2D) {
	    for (int row = 0; row < Connect4Utils.ROWS; row++) { 
	        
	    	for (int column = 0; column < Connect4Utils.COLUMNS; column++) { 
	            
	    		Color player = matrix2D[row][column];
	            
	            if (player == Connect4Utils.NONE){
	                continue; //No value found. start checking only when value is non-zero
	            }

	            if((column + 3) < Connect4Utils.COLUMNS){//Check to avoid ArrayIndexOutOfBoundsException and to ignore columns
	              // look right
	              if (player == matrix2D[row][column+1] && 
     	              player == matrix2D[row][column+2] &&
     	              player == matrix2D[row][column+3])
     	            {
     	                return player;
     	            }
	            }
	            
	            if ((row + 3) < Connect4Utils.ROWS) {//Check to avoid ArrayIndexOutOfBoundsException and to ignore rows
	            	// look down
	                if (player == matrix2D[row+1][column] && 
	                    player == matrix2D[row+2][column] &&
	                    player == matrix2D[row+3][column])
	                {
	                	  return player;
	                }
	                
	                // look down & right
	                if ((column + 3) < Connect4Utils.COLUMNS){//Check to avoid ArrayIndexOutOfBoundsException
                	  if (player == matrix2D[row+1][column+1] && 
      	                    player == matrix2D[row+2][column+2] &&
      	                    player == matrix2D[row+3][column+3])
      	                {
      	                	  return player;
      	                }
	                }
	                
	                // look down & left
	                if ((column - 3) >= 0){//Check to avoid ArrayIndexOutOfBoundsException
	                	if (player == matrix2D[row+1][column-1] && 
	     	                player == matrix2D[row+2][column-2] &&
	     	                player == matrix2D[row+3][column-3])
     	                {
     	                	return player;
     	                }
	                }
	                    
	            }
	        }
	    }
	    return Connect4Utils.NONE; // no body wins
    }
 }
