package othello;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GUI extends JFrame{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private Tile[][] buttonGrid = new Tile[10][10];
    private JPanel buttonPannel = new JPanel();
    private Board board = new Board();

    public GUI()
    {
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                buttonGrid[i][j] = new Tile(i, j);
            }
        }
        
        buttonPannel.setLayout(new GridLayout(10, 10));
        refreshGrid();

        this.setTitle("Othello");
        this.add(buttonPannel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }

    public void refreshGrid(){
        buttonPannel.removeAll();

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Tile t = new Tile(i, j);
                t.setPiece(board.getPiece(i, j));
                buttonGrid[i][j] = t;
                buttonPannel.add(t);
            }
        }

        SwingUtilities.updateComponentTreeUI(this);
    }

}

class Tile extends JButton {
    private int row;
    private int col;

    public Tile(int row, int col){
        this.row = row;
        this.col = col;

        super.setSize(60, 60);
        super.setBackground(new Color(45, 174, 82));
        super.setBorder(new LineBorder(Color.BLACK));
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setPiece(int pieceColor){
        if (pieceColor == 1){
            super.setIcon(new ImageIcon("assets/blackPiece.gif"));
        }
        else if (pieceColor == -1){
            super.setIcon(new ImageIcon("assets/whitePiece.png"));
        }
    }


}
