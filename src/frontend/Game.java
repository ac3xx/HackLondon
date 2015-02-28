package frontend;

import javax.swing.*;
import java.awt.*;

/**
 * Created by James on 28/02/15.
 */
public class Game extends JFrame {
    public Game() {
        start();
    }

    public void start() {
        setTitle("Simple Java 2D example");

        GridLayout layout  = new GridLayout(1, 2);
        layout.setHgap(2);
        setLayout(layout);

        Terminal terminal = new Terminal();
        GamePanel game = new GamePanel();
        add(terminal);
        add(game);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Window myWindow = this;

        try {
            myDevice.setFullScreenWindow(myWindow);
        } finally {
//            myDevice.setFullScreenWindow(null);
        }
    }
}
