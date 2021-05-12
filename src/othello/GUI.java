package othello;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class GUI extends JFrame{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    public GUI()
    {
        this.setTitle("*Othello*");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);

    }

    public void drawBoard(Graphics g)
    {
        Graphics2D gfx = (Graphics2D) g;

        int V_dRow = 0;
        int H_dCol = 0;
        for(int i = 0; i < 10; i++)
        {
            gfx.drawLine(V_dRow, 0, V_dRow, HEIGHT);
            V_dRow = V_dRow + WIDTH / 10;
        }
        for(int i = 0; i < 10; i++)
        {
            gfx.drawLine(0, H_dCol, WIDTH, H_dCol);
            H_dCol = H_dCol + HEIGHT / 10;
            
        }
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        drawBoard(g);
    }
    
}
