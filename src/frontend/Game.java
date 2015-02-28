package frontend;

import backend.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by James on 28/02/15.
 */
public class Game extends JFrame implements KeyListener, ActionListener {
    private Terminal gameTerminal;
    private GameEngine gameEngine;

    public Game() {
        start();
    }

    public void start() {
        setTitle("Simple Java 2D example");

        GridLayout layout  = new GridLayout(1, 2);
        layout.setHgap(2);
        setLayout(layout);

        gameTerminal = new Terminal();
        GamePanel gamePanel = new GamePanel();
        gameEngine = new GameEngine(gameTerminal, gamePanel);
        add(gameTerminal);
        add(gamePanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Window myWindow = this;

        myDevice.setFullScreenWindow(myWindow);

        addKeyListener(this);

        // Disable focus traversal so tab is detected
        this.setFocusTraversalKeysEnabled(false);

        Timer timer = new Timer(1000/60, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        tick();
    }

    private void tick() {
        // Tick, runs the game
//        gameTerminal.title = String.join("\n", gameEngine.getCodeLines());
        gameTerminal.repaint();
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isActionKey() || e.getKeyCode() == KeyEvent.VK_SHIFT) {
            return;
        }
        gameTerminal.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
