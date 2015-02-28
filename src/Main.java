import frontend.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Created by James on 28/02/15.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                GameWindow sk = GameWindow.init();
                sk.setVisible(true);
            }
        });
    }
}
