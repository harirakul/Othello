import othello.*;

public class Main {
    public static void main(String[] args){

        Board b = new Board();
        System.out.println(b);

        b.playMove(3, 4);
        // b.playMove(3, 3);

        new GUI();
    }
}
