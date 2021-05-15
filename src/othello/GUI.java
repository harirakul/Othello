package othello;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
        if (board.gameOver() != 0){
            new ResultGUI(board.gameOver());
            return;
        }

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

    public void simulate(){
        
        while (board.gameOver() == 0){
            ArrayList<Integer[]> legalMoves = board.getLegalMoves();
            Integer[] move = legalMoves.get((int) (Math.random()*legalMoves.size()));
            board.playMove(move[0], move[1]);
            refreshGrid();

            try {
                TimeUnit.MILLISECONDS.sleep(125);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //new ResultGUI(board.gameOver());
    }
}

class ResultGUI extends JFrame {
    private int result;

    public ResultGUI(int result){
        this.result = result;

        super.setSize(300, 200);
        super.setTitle("Game Over");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String text = this.result == 1 ? "Black wins!" : "White wins!";
        super.add(new JLabel(text, SwingConstants.CENTER));

        super.setVisible(true);
    }
}