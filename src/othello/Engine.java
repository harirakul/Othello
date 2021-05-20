package othello;

import java.util.ArrayList;

public class Engine {

    /*
    Makes an evaluation of the current position on a board,
    returning an integer representing the score.
    */
    public static int evaluate(Board board){
        int score = 0;
        int color = board.getTurn();

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                int piece = board.getPiece(i, j);

                // Adds one point if piece is the same color, subtract otherwise:
                score += piece * color; 

                // Adds ten points if the piece is on the side of the board.
                if (board.isSide(i, j)){
                    score += 10 * piece * color;
                }

                // Adds 25 points if the piece is in the corner.
                if (board.isCorner(i, j)){
                    score += 40 * piece * color;
                }
            }
        }

        //Adds 10 points for each legal move available.
        score += board.getLegalMoves().size() * 5;

        return score;
    }

    /*
    Returns a heuristic value for an inputted position, relative to the maximizing color.
    */
    public static int minimax(Board board, int depth, int maximizingColor){
        if (depth == 0){
            return evaluate(board);
        }

        Board b = board.copy();
        int value;

        if (maximizingColor == b.getTurn()){
            value = -1000;
            ArrayList<Integer[]> legals = b.getLegalMoves();
            for (int i = 0; i < legals.size(); i++){
                Integer[] move = legals.get(i);
                b.playMove(move);
                value = Math.max(value, minimax(b, depth - 1, -maximizingColor));
            }
            return value;
        }

        else {
            value = 1000;
            ArrayList<Integer[]> legals = b.getLegalMoves();
            for (int i = 0; i < legals.size(); i++){
                Integer[] move = legals.get(i);
                b.playMove(move);
                value = Math.min(value, minimax(b, depth - 1, -maximizingColor));
            }
            return value;
        }
    }

    /*
    Uses the minimax function to determine the best move for the computer.
    */
    public static Integer[] bestMove(Board board){
        Board b = board.copy();
        Integer[] bestMove = new Integer[2];
        int max = -10000;
        int maximizingColor = board.getTurn();

        ArrayList<Integer[]> legals = b.getLegalMoves();
        for (int i = 0; i < legals.size(); i++){
            Integer[] move = legals.get(i);
            b.playMove(move[0], move[1]);

            if (minimax(board, 4, maximizingColor) > max){
                bestMove[0] = move[0];
                bestMove[1] = move[1];
                max = b.count(maximizingColor);
            }

            b.undo();
        }

        return bestMove;
    }

    /*
    A decision-making strategy that seeks to make the move 
    which gives the opponent the fewest legal moves.
    */
    public static Integer[] limitOpponentOptions(Board board){
        Board b = board.copy();
        Integer[] bestMove = new Integer[2];
        int min = 100;

        ArrayList<Integer[]> legals = b.getLegalMoves();
        for (int i = 0; i < legals.size(); i++){
            Integer[] move = legals.get(i);
            b.playMove(move[0], move[1]);

            if (b.getLegalMoves().size() < min){
                bestMove[0] = move[0];
                bestMove[1] = move[1];
                min = b.getLegalMoves().size();
            }

            b.undo();
        }

        return bestMove;
    }

    /*
    A decision-making strategy that seeks to make the move 
    that maximizes the # of tiles the engine has.
    */
    public static Integer[] greedySelection(Board board){
        Board b = board.copy();
        Integer[] bestMove = new Integer[2];
        int max = 0;
        int maximizingColor = board.getTurn();

        ArrayList<Integer[]> legals = b.getLegalMoves();
        for (int i = 0; i < legals.size(); i++){
            Integer[] move = legals.get(i);
            b.playMove(move[0], move[1]);

            if (b.count(maximizingColor) > max){
                bestMove[0] = move[0];
                bestMove[1] = move[1];
                max = b.count(maximizingColor);
            }

            b.undo();
        }

        return bestMove;
    }
}
