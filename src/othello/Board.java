package othello;

import java.util.ArrayList;

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

    public Board(){
        grid = new int[10][10];
        grid[4][4] = -1;
        grid[5][5] = -1;
        grid[4][5] = 1;
        grid[5][4] = 1;
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
    Checks if any of the directly adjacent or diagonal squares 
    of a specified square contains a specified color.
    */
    public boolean nearbySquaresContain(int target, int row, int col){
        int[][] offsets = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},   // Adjacent Squares
            {1, 1}, {1, -1}, {-1, -1}, {-1, 1}  // Diagonal Squares
        };

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
    Finds the squares that are adjacent or directly diagonal to an enemy piece of the current player.
    These are "possible squares" - pieces may only be placed in these squares, but not all of them are legal.
    The purpose is to isolate these squares for analysis in other methods (rather than analyze every square).
    */
    public ArrayList<Integer[]> possibleMoves(){
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (isEmpty(i, j) && nearbySquaresContain(-turn, i, j)){
                    Integer[] square = {i, j};
                    possibleMoves.add(square);
                }
            }
        }

        return possibleMoves;
    }

    /*
    Returns a list of all the possible legal moves for the current player.
    Each legal move will be an integer array with length of 2 to contain the row and column.
    An ArrayList is used since the number of possible of legal moves changes with the position.
    */
    public ArrayList<Integer[]> getLegalMoves(){
        ArrayList<Integer[]> legalMoves = possibleMoves();

        // Iterate through the possible moves and delete the moves that are illegal.

        return legalMoves;
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
