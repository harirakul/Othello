package othello;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Tile extends JButton {
    private int row;
    private int col;
    private GUI parent;

    public Tile(GUI parent, int row, int col){
        this.parent = parent;
        this.row = row;
        this.col = col;

        super.setSize(60, 60);
        super.setBackground(parent.getColor());
        super.setBorder(new LineBorder(Color.BLACK));

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(row + " " + col);
                parent.onClick(row, col);
            }
            
        });
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