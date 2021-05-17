package othello;

import java.util.ArrayList;
import java.util.Arrays;

// -1 represents a white piece
// 1 represents a black piece

public class Board{
    private int[][] grid;
    private int turn = 1; //Black pieces play first
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\033[0;97m";
    private static final String ANSI_BLACK = "\033[0;90m";

    private String player = ANSI_WHITE + "W " + ANSI_RESET;             // Human Player
    private String AI = ANSI_BLACK + "B " + ANSI_RESET;
    private String emptySpace = "  ";

    private ArrayList<int[][]> history; // Used to undo moves.

    private int[][] offsets = {
        {0, 1}, {1, 0}, {0, -1}, {-1, 0},   // Adjacent Squares
        {1, 1}, {1, -1}, {-1, -1}, {-1, 1}  // Diagonal Squares
    };

    public Board(){
        grid = new int[10][10];
        grid[4][4] = -1;
        grid[5][5] = -1;
        grid[4][5] = 1;
        grid[5][4] = 1;

        history = new ArrayList<int[][]>();
        savePosition(grid);
    }

    /*
    Returns the piece at the specified square.
    */
    public int getPiece(int row, int col){
        return grid[row][col];
    }

    /*
    Sets the square on the grid to the specified piece color.
    */
    public void setPiece(int pieceColor, int row, int col){
        if (grid[row][col] == 0){
            grid[row][col] = pieceColor;
        }
    }

    /*
    Returns the current turn.
    */
    public int getTurn(){
        return turn;
    }

    /*
    Sets the current turn to the inputted integer.
    */
    public void setTurn(int theTurn){
        turn = theTurn;
    }

    /*
    Adds a copy of the inputted grid to the Board history.
    */
    public void savePosition(int[][] position){
        int[][] matrix = new int[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                matrix[i][j] = grid[i][j];
            }
        }
        history.add(matrix);
    }

    // /*
    // Returns a new grid with the current position.
    // */
    // public int[][] getPosition(){
    //     int[][] matrix = new int[10][10];
    //     for (int i = 0; i < 10; i++){
    //         for (int j = 0; j < 10; j++){
    //             matrix[i][j] = grid[i][j];
    //         }
    //     }
    //     return matrix;
    // }

    /*
    Sets the position to an inputted grid.
    */
    public void setPosition(int[][] grid){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                this.grid[i][j] = grid[i][j];
            }
        }
    }

    /*
    Flips the piece (switches color) at the specified square.
    */
    public void flip(int row, int col){
        grid[row][col] *= -1;
    }

    /*
    Returns true of the indicated square is within the array, false otherwise.
    */
    public boolean inBounds(int row, int col){
        return (row >= 0 && row <= 9) && (col >= 0 && col <= 9);
    }

    /*
    Returns true of the indicated square is empty, otherwise false.
    */
    public boolean isEmpty(int row, int col){
        return grid[row][col] == 0;
    }

    /*
    Changes the current turn to represent the next player.
    If the next player has no legal moves, play returns to the current player.
    */
    public void incrementTurn(){
        turn *= -1;
        if (getLegalMoves().size() == 0){
            turn *= -1;
        }
    }

    /*
    Returns all spaces on the board have been occupied.
    */
    public boolean isFilled(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (grid[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /*
    Returns the number of pieces of the specified color.
    */
    public int count(int color){
        int num = 0;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (grid[i][j] == color){
                    num++;
                }
            }
        }
        return num;
    }

    /*
    Returns 1 if black wins in the current position.
    Returns -1 if white wins in the current position.
    Returns 0 if the game is not over.
    */
    public int gameOver(){
        if (isFilled()){
            System.out.println(count(1) + " " + count(-1));
            // There are more white pieces than black pieces
            if (count(-1) > count(1)){
                return -1;
            }
            // There are more black pieces than white pieces
            return 1;
        }
        // If both players can't move:
        if (getLegalMoves().size() == 0){
            turn *= -1;
            if (getLegalMoves().size() == 0){
                turn *= -1;
                // Count the majority
                if (count(-1) > count(1)){
                    return -1;
                }
                return 1;
            }
        }
        return 0;
    }

    /*
    Checks if any of the directly adjacent or diagonal squares 
    of a specified square contains a specified color.
    */
    public boolean nearbySquaresContain(int target, int row, int col){
        for (int[] offset: offsets){
            int newRow = row + offset[0];
            int newCol = col + offset[1];
            if (inBounds(newRow, newCol) && grid[newRow][newCol] == target){
                return true;
            }
        }
        return false;
    }

    /*
    Returns a list of all the possible legal moves for the current player.
    Each legal move will be an integer array with length of 2 to contain the row and column.
    An ArrayList is used since the number of possible of legal moves changes with the position.
    */
    public ArrayList<Integer[]> getLegalMoves(){
        ArrayList<Integer[]> legalMoves = new ArrayList<Integer[]>();

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (isEmpty(i, j) && nearbySquaresContain(-turn, i, j)){
                    // Possible square identified, now iterate through rows, cols, and diagonals to check if its legal
                    for (int[] offset: offsets){
                        int newRow = i + offset[0];
                        int newCol = j + offset[1];
                        while (inBounds(newRow, newCol) && grid[newRow][newCol] == -turn){
                            newRow += offset[0];
                            newCol += offset[1];
                        }
                        if (inBounds(newRow, newCol) && grid[newRow][newCol] == turn){
                            if ((newRow == i && Math.abs(newCol - j) > 1) || // In the same row?
                                (newCol == j && Math.abs(newRow - i) > 1) || // In the same col?
                                (Math.abs(newCol - j) > 1 && Math.abs(newRow - i) > 1)){ //Diagonal?
                                    legalMoves.add(new Integer[]{i, j});
                                    break;  // Now consider the next square.
                            } 
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    /*
    Returns true if the specified move is legal, false otherwise.
    */
    public boolean isLegal(int row, int col){
        ArrayList<Integer[]> legals = this.getLegalMoves();
        Integer[] move = {row, col};
        for (Integer[] legalMove: legals){
            if (Arrays.equals(move, legalMove)){
                return true;
            }
        }
        return false;
    }

    /*
    Places a piece on the specified square if the move is legal.
    Flips the corresponding pieces that need to be flipped.
    Increments the turn counter and updates position history.
    */
    public void playMove(int row, int col){
        if (this.isLegal(row, col)){
            this.setPiece(turn, row, col);
            // Identify in what squares pieces need to be flipped.
            for(int[] offset: offsets){
                int newRow = row + offset[0];
                int newCol = col + offset[1];
                int count = 1;
                while (inBounds(newRow, newCol) && grid[newRow][newCol] == -turn){
                    newRow += offset[0];
                    newCol += offset[1];
                    count++;
                }
                if (inBounds(newRow, newCol) && grid[newRow][newCol] == turn){
                        // Flippable line identified, now backtrack, and flip all opponent coins
                        for (int i = 1; i < count; i++){
                            this.flip(newRow - offset[0]*i, newCol - offset[1]*i);
                        }
                }
            }
            turn *= -1;
            savePosition(grid);
            //System.out.println(this);
        }
    }

    public void playMove(Integer[] move) {
        playMove(move[0], move[1]);
    }

    /*
    Resets the board, and the turn counter.
    */
    public void reset(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                grid[i][j] = 0;
            }
        }

        grid[4][4] = -1;
        grid[5][5] = -1;
        grid[4][5] = 1;
        grid[5][4] = 1;

        turn = 1;
    }

    /*
    Undoes the previous move.
    */
    public void undo(){
        history.remove(history.size() - 1);
        setPosition(history.get(history.size() - 1));
        turn *= -1;
    }

    /*
    Returns a new board object with the same position.
    */
    public Board copy(){
        Board b = new Board();
        b.setPosition(grid);
        b.setTurn(turn);
        b.savePosition(grid);
        return b;
    }

    /*
    Returns a printable, string representation of the board.
    Solely for debugging purposes.
    */
    public String toString(){
        String out = "   ";

        for(int i = 0; i < 10; i++){
            out += (ANSI_RED + "| " + ANSI_YELLOW + i + " " + ANSI_RESET);
        }

        out += "\n   -----------------------------------------\n";
        for (int i = 0; i < 10; i++){
            out += ANSI_YELLOW + i + ANSI_RESET + "  ";
            for (int j = 0; j < 10; j++){
                
                out += "| ";

                if (grid[i][j] == 1){
                    out += AI;          // Black Piece
                }
                else if (grid[i][j] == -1){
                    out += player;      // White Piece
                }
                else{
                    out += emptySpace;
                }
            }
            out += "|";
            
            out += "\n   -----------------------------------------\n";
        }
        return out;
    }
}
