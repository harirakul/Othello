import othello.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        Board b = new Board();
        System.out.println(b);

        ArrayList<Integer[]> possible = b.possibleMoves();
        for (Integer[] move: possible){
            System.out.println(move[0] + " " + move[1]);
        }

        new GUI();
    }
}
