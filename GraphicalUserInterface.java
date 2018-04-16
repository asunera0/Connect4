package connect4;
import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

public class GraphicalUserInterface {
	
	public static JFrame gameFrame = null;
	public static JPanel gameStatusPanel = null;
	public static GamePanel gamePanel = null;
	public static JLabel gameStatusLabel = null;
    public static Color[][] gameBoard;
    public static Color currentPlayer;
    public static int depth = 2;

    public static void startConnect4Game() {
    	 createGamePanel();
    	 createGameStatusPanel();
    	 createGameFrame();
         updateStatusLabel();
    	 winnerCheck();        
    }
    
    public static void createGamePanel(){
    	gamePanel = new GamePanel(gameBoard);
        gamePanel.setPreferredSize(new Dimension(900,600));
        gamePanel.addMouseListener(new Connect4MouseAdapter());
    }
    
    public static void createGameStatusPanel(){
    	gameStatusPanel = new JPanel();
    	gameStatusPanel.setPreferredSize(new Dimension(200,600));
    	
    	JLabel gameStatusLabelTemp = new JLabel();
    	gameStatusLabelTemp.setText("Course: Artificial Intelligence");
    	gameStatusPanel.add(gameStatusLabelTemp,BorderLayout.CENTER);
    	
    	JLabel gameStatusLabelTemp1 = new JLabel();
    	gameStatusLabelTemp1.setText("Prof: Stephen Lucci");
    	gameStatusPanel.add(gameStatusLabelTemp1,BorderLayout.CENTER);
    	
    	JLabel gameStatusLabelTemp2 = new JLabel();
    	gameStatusLabelTemp2.setPreferredSize(new Dimension(200,230));
    	gameStatusPanel.add(gameStatusLabelTemp2,BorderLayout.CENTER);
    	
    	gameStatusLabel = new JLabel();
        gameStatusPanel.add(gameStatusLabel,BorderLayout.CENTER);
    }
    
    public static void createGameFrame(){
    	gameFrame = new JFrame();
        gameFrame.setTitle("Connect Four");
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(gameStatusPanel, BorderLayout.EAST);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true); 
    }
    
    public static void dropDisk(int column) {
        Connect4Utils.dropDisk(gameBoard, currentPlayer,  column);
        currentPlayer = Connect4Utils.getOppositePlayer(currentPlayer);
        gameFrame.repaint();
        winnerCheck();
    }

    public static void computerTurn() {
        if(currentPlayer == Connect4Utils.COMPUTER) {
        	 Color[][] boardCopy = new Color[Connect4Utils.ROWS][Connect4Utils.COLUMNS];
             for(int i = 0; i < gameBoard.length; i++) {
                 boardCopy[i] = Arrays.copyOf(gameBoard[i], gameBoard[i].length);
             }
             int column = Connect4Utils.getMoveForComputer(boardCopy, depth);
             
             if( Connect4Utils.isValidMove(gameBoard, column) ) {
                 dropDisk(column);
             } else {
            	 updateStatusLabelAndPopMessage("Illegal move at column : " + column, "Message");
             }
        }
    }

    public static void winnerCheck() {
        Color winner = Connect4Utils.findWinner(gameBoard);
        boolean gameDone = true;
        if( winner == Connect4Utils.HUMAN ) {
        	updateStatusLabelAndPopMessage("Human wins the game!","Game Done");	
        } else if( winner == Connect4Utils.COMPUTER ) {
        	updateStatusLabelAndPopMessage("Computer wins the game!","Game Done");	
        } else if (Connect4Utils.isPanelFull(gameBoard)) {
        	updateStatusLabelAndPopMessage("Game Drawn","Game Done");	
        } else {
        	gameDone = false;
            updateStatusLabel();
        }
        if(gameDone) { 
        	System.exit(0);
        }else if(currentPlayer == Connect4Utils.COMPUTER){
        	computerTurn();
        }
    }
    
    public static void showDialog(String message,String title){
    	 JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void updateStatusLabelAndPopMessage(String message,String title) {
    	  gameStatusLabel.setText(message);
          showDialog(message,title);
    }

    public static void updateStatusLabel() {
        if(currentPlayer == Connect4Utils.HUMAN) {
            gameStatusLabel.setText("Human's turn");
        } else {
            gameStatusLabel.setText("Computer's turn");
        } 
        gameFrame.repaint();
    }
}