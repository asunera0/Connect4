package connect4;

import java.awt.Color;

public class RunConnect4 {

	public static void main(String[] args){
   	 	Color[][] board = new Color[Connect4Utils.ROWS][Connect4Utils.COLUMNS];
        for(int row = 0; row < Connect4Utils.ROWS; row++) {
            for (int col = 0; col < Connect4Utils.COLUMNS; col++) {
                board[row][col] = Connect4Utils.NONE;
            }
        }
/*        
        board[0][0] = Connect4Utils.NONE;
        board[0][1] = Connect4Utils.NONE;
        board[0][2] = Connect4Utils.NONE;
        board[0][3] = Connect4Utils.COMPUTER;
        board[0][4] = Connect4Utils.HUMAN;
        board[0][5] = Connect4Utils.HUMAN;
        board[0][6] = Connect4Utils.HUMAN;
        int score = Connect4Utils.getScoreInRows(board, Connect4Utils.HUMAN);
        
        System.out.println("ROWS:::"+score);
        
        board[0][0] = Connect4Utils.COMPUTER;
        board[1][0] = Connect4Utils.COMPUTER;
        board[2][0] = Connect4Utils.COMPUTER;
        board[3][0] = Connect4Utils.HUMAN;
        board[4][0] = Connect4Utils.HUMAN;
        board[5][0] = Connect4Utils.COMPUTER;
        
        score = Connect4Utils.getScoreInColumns(board, Connect4Utils.COMPUTER);
        System.out.println("COL:::"+score);
        board[0][0] = Connect4Utils.HUMAN;
        board[1][1] = Connect4Utils.HUMAN;
        board[2][2] = Connect4Utils.HUMAN;
        board[3][3] = Connect4Utils.COMPUTER;
        board[4][4] = Connect4Utils.HUMAN;
        board[5][5] = Connect4Utils.HUMAN;
        
        score = Connect4Utils.getScoreDiagonals(board, Connect4Utils.HUMAN);
        System.out.println("DIAG:::"+score);*/
        
        GraphicalUserInterface.gameBoard = board;
        GraphicalUserInterface.currentPlayer = Connect4Utils.HUMAN;
        GraphicalUserInterface.startConnect4Game();
   }

}
