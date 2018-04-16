package connect4;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Color[][] board;

    public GamePanel(Color[][] board) {
        setBackground(Connect4Utils.PANEL_COLOR);
        this.board = board;
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        int rowOffset = getRowHeight() / 8;
        int colOffset = getColumnWidth() / 8;

        for(int row = 0; row < Connect4Utils.ROWS; row++) {
            for (int col = 0; col < Connect4Utils.COLUMNS; col++) {
                g.setColor(board[Connect4Utils.ROWS-row-1][col]);
                g.fillOval( col * getColumnWidth() + colOffset,
                            row * getRowHeight() + rowOffset,
                            getColumnWidth() - 2*colOffset,
                            getRowHeight() - 2*rowOffset);
            }
        }
    }
    

    public int getColumnWidth() {
        return getWidth()/Connect4Utils.COLUMNS;
    }

    public int getRowHeight() {
        return getHeight()/Connect4Utils.ROWS;
    }

}
