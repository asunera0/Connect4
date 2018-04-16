package connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Connect4MouseAdapter extends MouseAdapter {
	
    public static void doMouseClick(int xPoint, int yPoint) {
    	//System.out.println("XPpoint::"+xPoint);
    	//System.out.println("GraphicalUserInterface.gamePanel.getColumnWidth()::"+GraphicalUserInterface.gamePanel.getColumnWidth());
        int column = xPoint/GraphicalUserInterface.gamePanel.getColumnWidth();
        //System.out.println("column::"+column);
        if( GraphicalUserInterface.currentPlayer == Connect4Utils.HUMAN ) {
            if( Connect4Utils.isValidMove(GraphicalUserInterface.gameBoard, column) ) {
            	GraphicalUserInterface.dropDisk(column);
            } else {
            	GraphicalUserInterface.showDialog("Illegal move at column:" + column, "Message");
            }
        } else {
        	if( Connect4Utils.isValidMove(GraphicalUserInterface.gameBoard, column) ) {
        		GraphicalUserInterface.dropDisk(column);
        	}
        }
    }
    
	 @Override
     public void mouseClicked( MouseEvent event ) {
         int xPoint = event.getX();
         int yPoint = event.getY();
         //this condition restricts user clicks when computer is playing
         if(GraphicalUserInterface.currentPlayer != Connect4Utils.COMPUTER){
        	 doMouseClick(xPoint, yPoint);
         }
     }
}
