import javax.swing.SwingUtilities;

import othello.*;

public class Main {
    public static void main(String[] args){

        Board b = new Board();
        System.out.println(b);



        GUI game_window = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new GUI().setVisible(true);
            }
        });

    }
}
