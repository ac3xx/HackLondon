package frontend;

import backend.GameEngine;
import backend.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
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
  private LinkedList<String> codeLines;
  private int currentLine;
  private int currentChar;

  public Terminal() {
    setOpaque(true);
    setBackground(Color.BLACK);
    codeLines = new LinkedList<String>();
    codeLines.add("Terminal Panel");
    codeLines.add("------------");
    this.setFocusTraversalKeysEnabled(false);
    currentLine = 0;
    currentChar = 0;
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
    StringBuilder sb = new StringBuilder();
    for (String s: codeLines) {
      sb.append(s + "\n");
    }
    drawString(g, sb.toString(), 25, 25);
    drawCursor(g);
    //g2d.draw(new Line2D.Double(25, 25, 25, 45));
  }

  private void drawString(Graphics g, String text, int x, int y) {
    g.setColor(Color.WHITE);
    for (String line : text.split("\n"))
      g.drawString(line, x, y += g.getFontMetrics().getHeight());
  }

  private void drawCursor(Graphics g) {
    StringBuilder sb = new StringBuilder();
//    for (int i = 0; i < currentLine; i++) {
//      sb.append("\n");
//    }
//    for (int i = 0; i < currentChar; i++) {
//      sb.append("  ");
//    }
    for (int i = 0; i < currentLine; i++) {
      sb.append("\n");
    }
    String thisLine = codeLines.get(currentLine);
    sb.append(thisLine.substring(0, currentChar));
    sb.append("|");
    g.setColor(Color.BLUE);
    drawString(g, sb.toString(), 25, 25);
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
    System.out.println("currentLine = " + currentLine + " currentChar = " + currentChar);
    String thisLine = codeLines.get(currentLine);
    String newLine;
    System.out.println("Key Code: " + e.getKeyCode());
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        currentChar = Math.max(currentChar - 1, 0); break;
      case KeyEvent.VK_RIGHT:
        currentChar++; break;
      case KeyEvent.VK_UP:
        currentLine = Math.max(currentLine-1, 0); currentChar = Math.max(currentChar, codeLines.get(currentLine).length()); break;
      case KeyEvent.VK_DOWN:
        currentLine = Math.min(currentLine+1, codeLines.size()-1); break;
      case KeyEvent.VK_BACK_SPACE:
        if (currentChar == 0) {
          if (currentLine != 0) {
            String previousLine = codeLines.get(currentLine-1);
            currentLine--;
            newLine = previousLine + thisLine;
            currentChar = codeLines.get(currentLine).length();
            codeLines.set(currentLine, newLine);
            codeLines.removeLast();
            for (int i = Math.min(currentLine+1, codeLines.size()-1); i < codeLines.size(); i++) {
              codeLines.set(i, codeLines.get(i + 1)); //shifts all the lower lines up
            }
            currentLine = Math.max(currentLine-1, 0);
          }
        } else {
          //System.out.println("currentChar = " + currentChar + " thisLine Max = " + Math.max(thisLine.length()-1, 0));
          newLine = thisLine.substring(0, currentChar-1) + thisLine.substring(currentChar, Math.max(thisLine.length(), 0));
          codeLines.set(currentLine, newLine);
          currentChar--;
        }
        break;
      case KeyEvent.VK_ENTER:
        String nextLine = thisLine.substring(currentChar, Math.max(thisLine.length(), 0));
        newLine = thisLine.substring(0, currentChar);
        codeLines.set(currentLine, newLine);
        currentLine++;
        if (currentLine == codeLines.size()) {
          codeLines.add(nextLine);
        }else {
          codeLines.set(currentLine, nextLine);
        }
        currentChar = 0;

        break;
    }


//    String curLine = codeLines.get(currentLine);
//    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//      currentLine++;
//      currentChar = 0;
//    } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//
//      if (currentChar == 0) {
//        currentLine--;
//        currentChar = codeLines.get(currentLine).length();
//      }
//
//    }else if (e.getKeyCode() == KeyEvent.VK_LEFT) else {



    }



//    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//      //hasRead = true;
//      currentLine++;
//    } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//      String curLine = codeLines.get(currentLine);
//      if (curLine != null) {
//        if (curLine.isEmpty()) {
//          codeLines.remove(currentLine);
//          currentLine--;
//        } else {
//          curLine = curLine.substring(0, curLine.length() - 1);
//          codeLines.set(currentLine, curLine);
//        }
//      }
//      this.title =  this.title.substring(0, this.title.length() - 1);
//    } else {
//      String toAdd = "";
//      if (!codeLines.isEmpty() || codeLines.size() < (currentLine - 1)) {
//        toAdd = codeLines.get(currentLine);
//        System.out.println("To add " + toAdd);
//      }
//      if (toAdd.equals("")) {
//        toAdd = "";
//        codeLines.add(currentLine, "");
//      }
//
//      if (e.getKeyCode() == KeyEvent.VK_TAB) {
//        toAdd += "    ";
//      } else {
//        toAdd += e.getKeyChar();
//      }
//
//      codeLines.set(currentLine, toAdd);
//      title += toAdd;
//    }
//
//    String line = codeLines.get(currentLine);
//    if (listener.isValidLine(line)) {
//      listener.setCodelines(codeLines);
//    }
//  }

}
