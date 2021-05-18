package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private JMenuBar menuBar;
    private JMenu file, game, edit, subMenu;
    private JMenuItem quit, reset, undo, color, newPlayer, newAI;
    private Color bg = new Color(45, 174, 82);
    private Timer timer = new Timer(300, this);;

    private Tile[][] buttonGrid = new Tile[10][10];
    private JPanel buttonPannel = new JPanel();
    private Board board = new Board();
    private boolean playingAgainstAI = false;

    public GUI()
    {
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                buttonGrid[i][j] = new Tile(this, i, j);
            }
        }
        
        buttonPannel.setLayout(new GridLayout(10, 10));
        refreshGrid();
        setupMenu();

        this.setTitle("Othello");
        this.add(buttonPannel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
    }

    public void setupMenu(){
        file = new JMenu("File");
        subMenu = new JMenu("New");
        newPlayer = new JMenuItem("Game vs Human"); newPlayer.addActionListener(this);
        newAI = new JMenuItem("Game vs AI"); newAI.addActionListener(this);
        subMenu.add(newPlayer); subMenu.add(newAI);
        quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        file.add(subMenu); file.add(quit);

        edit = new JMenu("Edit");
        color = new JMenuItem("Color");
        color.addActionListener(this);
        edit.add(color);

        game = new JMenu("Game");
        reset = new JMenuItem("Reset");
        reset.addActionListener(this);
        undo = new JMenuItem("Undo");
        undo.addActionListener(this);
        game.add(reset); game.add(undo);

        menuBar = new JMenuBar();
        menuBar.add(file); 
        menuBar.add(edit);
        menuBar.add(game);
        this.setJMenuBar(menuBar);
    }

    public Color getColor(){
        return bg;
    }

    public void refreshGrid(){
        buttonPannel.removeAll();

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Tile t = new Tile(this, i, j);
                t.setHighlighted(board.isLegal(i, j));
                t.setPiece(board.getPiece(i, j));
                buttonGrid[i][j] = t;
                buttonPannel.add(t);
            }
        }

        SwingUtilities.updateComponentTreeUI(this);

        if (board.gameOver() != 0){
            new ResultGUI(board.gameOver());
            return;
        }
    }

    public void engineMove(){
        //Integer[] bestMove = board.getTurn() == 1 ? Engine.greedySelection(board): Engine.limitOpponentOptions(board);
        Integer[] bestMove = Engine.bestMove(board);
        System.out.println("Engine score: " + Engine.evaluate(board));
        board.playMove(bestMove);
        refreshGrid();
        
    }

    public void onClick(int row, int col){
        if (board.isLegal(row, col)){
            board.playMove(row, col);
            refreshGrid();
            System.out.println("Engine score: " + Engine.evaluate(board));

            if (playingAgainstAI){
                timer.setRepeats(false);
                timer.start(); 
            }
        }
    }

    public void simulate(){
        timer.setRepeats(true);
        engineMove();
        timer.start();
    }

    public void run(){
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit){
            System.exit(0);
        }  

        if (e.getSource() == reset || e.getSource() == newPlayer){
            board.reset();
            refreshGrid();
            playingAgainstAI = false;
        }

        if (e.getSource() == undo){
            board.undo();
            refreshGrid();
        }

        if (e.getSource() == color){
            bg = JColorChooser.showDialog(null, "Choose a tile color", bg);
            refreshGrid();
        }

        if (e.getSource() == timer){
            engineMove();
            if (board.getTurn() != 1){
                timer.start();
            }
        }

        if (e.getSource() == newAI){
            board.reset();
            refreshGrid();
            playingAgainstAI = true;
        }
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