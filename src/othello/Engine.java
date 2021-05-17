package othello;

import java.util.ArrayList;

public class Engine {

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
