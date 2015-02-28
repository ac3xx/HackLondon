package frontend;

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
    private Scanner scanner;
    private String input = "";
    private boolean hasRead = false;
    private Terminal gameTerminal;
    private LinkedList<String> codeLines = new LinkedList<String>();
    private int currentLine = 0;

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
        setResizable(false);

        GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Window myWindow = this;

        myDevice.setFullScreenWindow(myWindow);

        addKeyListener(this);

        // Disable focus traversal so tab is detected
        this.setFocusTraversalKeysEnabled(false);

        scanner = new Scanner(input);

        Timer timer = new Timer(1000/60, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        tick();
    }

    private void tick() {
        // Tick, runs the game
//        if (hasRead) {
            System.out.println("Input command: " + input);
//            gameTerminal.title += "\n";
//            gameTerminal.title += input;
//            input = "";
        gameTerminal.title = String.join("\n", codeLines);
            hasRead = false;


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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            hasRead = true;
            currentLine++;
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String curLine = codeLines.get(currentLine);
            if (curLine != null) {
                if (curLine.isEmpty()) {
                    codeLines.remove(currentLine);
                    currentLine--;
                } else {
                    curLine = curLine.substring(0, curLine.length() - 1);
                    codeLines.set(currentLine, curLine);
                }
            }
            gameTerminal.title =  gameTerminal.title.substring(0, gameTerminal.title.length() - 1);
        } else {
            String toAdd = "";
            if (!codeLines.isEmpty() || codeLines.toArray().length < (currentLine - 1)) {
                toAdd = codeLines.get(currentLine);
                System.out.println("To add " + toAdd);
            }
            if (toAdd.equals("")) {
                toAdd = "";
                codeLines.add(currentLine, "");
            }

            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                toAdd += "    ";
            } else {
                toAdd += e.getKeyChar();
            }

            codeLines.set(currentLine, toAdd);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
