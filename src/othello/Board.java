package othello;

// -1 represents a white piece
// 1 represents a black piece

public class Board{
    private int[][] grid;
    private int turn = 1; //Black pieces play first

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
        String out = "-----------------------------------------\n";
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                out += "| ";
                if (grid[i][j] == 1){
                    out += "B ";
                }
                else if (grid[i][j] == -1){
                    out += "W ";
                }
                else{
                    out += "  ";
                }
            }
            out += "|";
            out += "\n-----------------------------------------\n";
        }
        return out;
    }
}
