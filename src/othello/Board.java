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
    Increments the turn counter.
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
                    // } 
                }
            }
            turn *= -1;
            System.out.println(this);
        }
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
