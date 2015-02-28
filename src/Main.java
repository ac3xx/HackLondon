import frontend.Game;
import javax.swing.SwingUtilities;

/**
 * Created by James on 28/02/15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Game sk = new Game();
                sk.setVisible(true);
            }
        });
    }
}
