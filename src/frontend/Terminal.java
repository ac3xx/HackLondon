package frontend;

import backend.GameEngine;
import backend.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by James on 28/02/15.
 */
public class Terminal extends JPanel {
    public String title = "Terminal Panel\n------------\n";
  private TerminalListener listener;

  public Terminal() {
        setOpaque(true);
        setBackground(Color.BLACK);
    }

    private void doDrawing(Graphics g) {
        Font gameFont = Font.getFont("Arial");
        try {
            FileInputStream fontInput = new FileInputStream("src/Minecraftia-Regular.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, fontInput);
            gameFont = gameFont.deriveFont(20f);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFont);
        drawString(g, title, 25, 25);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

  public void setListener(TerminalListener listener) {
    this.listener = listener;
  }

  public void keyPressed(KeyEvent e) {
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
}
