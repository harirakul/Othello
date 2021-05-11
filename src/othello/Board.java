package othello;

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

    public int getPiece(int row, int col){
        return grid[row][col];
    }

    public void setPiece(int pieceColor, int row, int col){
        if (grid[row][col] == 0){
            grid[row][col] = pieceColor;
        }
    }

    public String toString(){
        // For debugging purposes
        String row_designation = "ABCDEFGHIJ";
        int index = 0;

        for(int i = 1; i < 10; i++){
            System.out.print( ANSI_YELLOW + "| " + ANSI_RED + i + " ");
        }

        System.out.print(ANSI_YELLOW + "| " + ANSI_RED + "10" + ANSI_YELLOW + "|" + ANSI_RESET);
        System.out.println();
        String out = "-----------------------------------------\n";
        for (int i = 0; i < 10; i++){
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
            
            out += "\n-----------------------------------------\n";
        }
        return out;
    }
}
