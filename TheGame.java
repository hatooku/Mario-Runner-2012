/**
 * @(#)TheGame.java
 *
 *
 * @author
 * @version 1.00 2012/5/14
 */
import javax.swing.JFrame;
public class TheGame {

    /**
     * Creates a new instance of <code>TheGame</code>.
     */
    public TheGame() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame j1 = new JFrame();
        j1.setSize(800,600);
        j1.setTitle("Mario Runner");
        j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game_Canvas g1 = new Game_Canvas();

        j1.add(g1);
        j1.setVisible(true);
    }
}
