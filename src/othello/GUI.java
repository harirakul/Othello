package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private JMenuBar menuBar;
    private JMenu file, game, edit, subMenu;
    private JMenuItem quit, reset, undo, color, newPlayer, newAI, simulate;
    private Color bg = new Color(45, 174, 82);
    private Timer timer = new Timer(300, this);;

    private Tile[][] buttonGrid = new Tile[10][10];
    private JPanel buttonPannel = new JPanel();
    private Board board = new Board();
    private boolean playingAgainstAI = false;

    public GUI()
    {
        //Initialize tiles in the buttonGrid:
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                buttonGrid[i][j] = new Tile(this, i, j);
            }
        }
        
        buttonPannel.setLayout(new GridLayout(10, 10)); // Set the layout of the pannel.
        refreshGrid();
        setupMenu();

        // Preferences for the GUI.
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
        simulate = new JMenuItem("Simulate");
        simulate.addActionListener(this);
        game.add(reset); game.add(undo); game.add(simulate);

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
                // If the tile is a legal move, Highlight it:
                t.setHighlighted(board.isLegal(i, j));
                // Set the tile's corresponding image to the correct piece
                t.setPiece(board.getPiece(i, j));
                buttonGrid[i][j] = t;
                buttonPannel.add(t);
            }
        }

        // Need to update the componentTreeUI for changes to take effect
        SwingUtilities.updateComponentTreeUI(this);

        // If the game is over:
        if (board.gameOver() != 0){
            timer.setRepeats(false);
            // Show the message of who wins.
            String text = board.gameOver() == 1 ? "Black wins!" : "White wins!";
            JOptionPane.showMessageDialog(null, text);
            return; // Exit the function
        }
    }

    public void engineMove(){
        //Integer[] bestMove = board.getTurn() == 1 ? Engine.greedySelection(board): Engine.limitOpponentOptions(board);
        Integer[] bestMove = Engine.bestMove(board);
        //System.out.println("Engine score: " + Engine.evaluate(board));
        board.playMove(bestMove);
        refreshGrid();
        
    }

    public void onClick(int row, int col){
        // If the selected square is legal:
        if (board.isLegal(row, col)){
            board.playMove(row, col);
            refreshGrid();

            // If the user is in a game against the AI:
            if (playingAgainstAI){
                // Tell the AI to make a move:
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

        if (e.getSource() == simulate){
            this.simulate();
        }
    }
}