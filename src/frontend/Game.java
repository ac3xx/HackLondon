package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Created by James on 28/02/15.
 */
public class Game extends JFrame implements KeyListener, ActionListener {
    private Scanner scanner;
    private String input = "";
    private boolean hasRead = false;
    private Terminal gameTerminal;

    public Game() {
        start();
    }

    public void start() {
        setTitle("Simple Java 2D example");

        GridLayout layout  = new GridLayout(1, 2);
        layout.setHgap(2);
        setLayout(layout);

        gameTerminal = new Terminal();
        GamePanel game = new GamePanel();
        add(gameTerminal);
        add(game);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Window myWindow = this;

        myDevice.setFullScreenWindow(myWindow);

        addKeyListener(this);

        scanner = new Scanner(input);

//        scanner.useDelimiter("\n");

//        tick();
        Timer timer = new Timer(1000/60, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        tick();
    }

    private void tick() {
        // Tick, runs the game
        if (hasRead) {
            System.out.println("Input command: " + input);
//            gameTerminal.title += "\n";
            gameTerminal.title += input;
            input = "";
            hasRead = false;


            gameTerminal.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        hasRead = true;
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            gameTerminal.title =  gameTerminal.title.substring(0, gameTerminal.title.length() - 1);
            input = input.substring(0, input.length() - 1);
        }
        input += e.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
