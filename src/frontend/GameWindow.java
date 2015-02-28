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
public class GameWindow extends JFrame implements KeyListener, ActionListener {
    private Terminal gameTerminal;
    private LinkedList<String> codeLines = new LinkedList<String>();
    public static GameWindow instance = new GameWindow();
    private int currentLine = 0;
    private GamePanel game;
    private GameEngine gameEngine;

    private GameWindow() {

    }

    public static GameWindow init() {
      instance.start();
      return instance;
    }

    public static GameWindow getInstance() {
      return instance;
    }

    public void start() {
        setTitle("Simple Java 2D example");

        GridLayout layout  = new GridLayout(1, 2);
        layout.setHgap(2);
        setLayout(layout);

        gameTerminal = new Terminal();
        game = new GamePanel();
        gameEngine = new GameEngine(gameTerminal, game);

        add(gameTerminal);
        add(game);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

//        System.out.println(Tile.GRASS.getTransitionWith(Tile.WATER, TileTransition.TOP_LEFT_CORNER));
    }

    public void actionPerformed(ActionEvent e) {
        tick();
    }

    private void tick() {
        // Tick, runs the game
//        gameTerminal.title = String.join("\n", gameEngine.getCodeLines());
        if (game.isInvalidated()) {
            game.repaint();
        }
        gameTerminal.repaint();
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if (e.isActionKey() || e.getKeyCode() == KeyEvent.VK_SHIFT) {
//            return;
//        }
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
////            hasRead = true;
//            currentLine++;
//        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//            String curLine = codeLines.get(currentLine);
//            if (curLine != null) {
//                if (curLine.isEmpty()) {
//                    codeLines.remove(currentLine);
//                    currentLine--;
//                } else {
//                    curLine = curLine.substring(0, curLine.length() - 1);
//                    codeLines.set(currentLine, curLine);
//                }
//            }
//            gameTerminal.title =  gameTerminal.title.substring(0, gameTerminal.title.length() - 1);
//        } else {
//            String toAdd = "";
//            if (!codeLines.isEmpty() || codeLines.toArray().length < (currentLine - 1)) {
//                toAdd = codeLines.get(currentLine);
//                System.out.println("To add " + toAdd);
//            }
//            if (toAdd.equals("")) {
//                toAdd = "";
//                codeLines.add(currentLine, "");
//            }
//
//            if (e.getKeyCode() == KeyEvent.VK_TAB) {
//                toAdd += "    ";
//            } else {
//                toAdd += e.getKeyChar();
//            }
//
//            codeLines.set(currentLine, toAdd);
//        }

        gameTerminal.keyPressed(e);
        tick();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

  public GamePanel getGamePanel() {
    return game;
  }
}
