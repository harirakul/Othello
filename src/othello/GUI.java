package othello;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                buttonGrid[i][j] = new Tile(this, i, j);
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
                Tile t = new Tile(this, i, j);
                t.setPiece(board.getPiece(i, j));
                buttonGrid[i][j] = t;
                buttonPannel.add(t);
            }
        }

        SwingUtilities.updateComponentTreeUI(this);
    }

    public void onClick(int row, int col){
        board.playMove(row, col);
        refreshGrid();
    }

    // public void drawBoard(Graphics g)
    // {
    //     Graphics2D gfx = (Graphics2D) g;

    //     int V_dRow = 0;
    //     int H_dCol = 0;
    //     for(int i = 0; i < 10; i++)
    //     {
    //         gfx.drawLine(V_dRow, 0, V_dRow, HEIGHT);
    //         V_dRow = V_dRow + WIDTH / 10;
    //     }
    //     for(int i = 0; i < 10; i++)
    //     {
    //         gfx.drawLine(0, H_dCol, WIDTH, H_dCol);
    //         H_dCol = H_dCol + HEIGHT / 10;
            
    //     }
    // }
    
    // public void paint(Graphics g)
    // {
    //     super.paint(g);
    //     drawBoard(g);
    // }
    
}

class Tile extends JButton {
    private int row;
    private int col;
    private GUI parent;

    public Tile(GUI parent, int row, int col){
        this.parent = parent;
        this.row = row;
        this.col = col;

        super.setSize(60, 60);
        super.setBackground(new Color(45, 174, 82));
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