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
import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Created by James on 28/02/15.
 */
public class Terminal extends JPanel {
  private TerminalListener listener;
  private LinkedList<String> codeLines;
  private GameEngine engine;
  private int currentLine;
  private int currentChar;
  private String code;
  private int blink;
  private int maxLineLength;
  private Timer longTimer;
  private Timer shortTimer; //TODO: implements hint timers

  public Terminal(GameEngine engine) {
    setOpaque(true);
    setBackground(Color.BLACK);
    this.setFocusTraversalKeysEnabled(false);
    currentLine = 2;
    currentChar = 0;
    blink = 0;
    engine.setTerminal(this);
    this.engine = engine;
    }

  private void doDrawing(Graphics g) {
    Font gameFont = Font.getFont("Arial");
    try {
        FileInputStream fontInput = new FileInputStream("src/LisaTerminalPaperRaw.ttf");
        gameFont = Font.createFont(Font.TRUETYPE_FONT, fontInput);
        gameFont = gameFont.deriveFont(17f);
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
    maxLineLength = (int) (g2d.getClipBounds().getWidth() / g.getFontMetrics().charWidth(' '));
    //System.out.println("maxlinelength = " + maxLineLength);
    StringBuilder sb = new StringBuilder();
    for (String s: codeLines) {
      for (int i = 0; i <= (int) s.length()/maxLineLength; i++) {
        //System.out.println("Append, s.length/maxll = " + s.length()/maxLineLength+ " i = " + i);
        sb.append(s.substring(i*maxLineLength, Math.min(s.length(), ((i+1)*maxLineLength))));
        sb.append("\n");
      }
    }
    drawString(g, sb.toString(), 25, 25);
    if (blink < 30) {
      drawCursor(g); //TODO: sort this fucker out
    }
    blink = (blink + 1) % 60;
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
    sb.append(thisLine.substring(0, Math.min(currentChar, codeLines.get(currentLine).length())));
    sb.append("|");
    g.setColor(Color.WHITE);
    drawString(g, sb.toString(), 25, 25);
  }

  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      doDrawing(g);
  }
  

  public void keyPressed(KeyEvent e) {
//    System.out.println("currentLine = " + currentLine + " currentChar = " + currentChar);
    String thisLine = codeLines.get(currentLine);
    String newLine;
//    System.out.println("Key Code: " + e.getKeyCode());
    char c = e.getKeyChar();
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        if (e.isControlDown()) {
          currentChar = 0;
        } else {
          if (currentChar == 0) {
            currentLine = Math.max(currentLine - 1, 0);
            currentChar = codeLines.get(currentLine).length();
          } else {
            currentChar--;
          }
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (e.isControlDown()) {
          currentChar = codeLines.get(currentLine).length();
        } else {
          if (currentChar == codeLines.get(currentLine).length()) {
            if (currentLine < codeLines.size() - 1) {
              currentLine++;
              startOfLine();
            }
          } else {
            currentChar++;
          }
        }
        break;
        case KeyEvent.VK_UP:
        currentLine = Math.max(currentLine - 1, 0);
        currentChar = Math.min(currentChar, codeLines.get(currentLine).length()-1);
        break;
      case KeyEvent.VK_DOWN:
        currentLine = Math.min(currentLine + 1, codeLines.size() - 1);
        currentChar = Math.min(codeLines.get(currentLine).length(), currentChar);
        endOfLine();
        break;
      case KeyEvent.VK_BACK_SPACE:
        if (currentChar == 0) {
          if (currentLine != 0) {
            String previousLine = codeLines.get(currentLine - 1);
            currentLine--;
            newLine = previousLine + thisLine;
            currentChar = codeLines.get(currentLine).length();
            codeLines.set(currentLine, newLine);
            codeLines.remove(currentLine + 1);
//            currentLine = Math.max(currentLine-1, 0);
          }
        } else {
          //System.out.println("currentChar = " + currentChar + " thisLine Max = " + Math.max(thisLine.length()-1, 0));
          newLine = thisLine.substring(0, currentChar - 1) + thisLine.substring(currentChar, Math.max(thisLine.length(), 0));
          codeLines.set(currentLine, newLine);
          currentChar--;
        }
        engine.updatedLine(currentLine);
        break;
      case KeyEvent.VK_ENTER:
        String nextLine = thisLine.substring(currentChar, Math.max(thisLine.length(), 0));
        newLine = thisLine.substring(0, currentChar);
        codeLines.set(currentLine, newLine);
        currentLine++;
        codeLines.add(currentLine, nextLine);
        currentChar = 0;
        break;
      case KeyEvent.VK_TAB:
        newLine = thisLine.substring(0, currentChar) + "  " + thisLine.substring(currentChar, thisLine.length());
        codeLines.set(currentLine, newLine);
          currentChar += 2;
        break;
        case KeyEvent.VK_SHIFT:
            break;
        case KeyEvent.VK_META:
            break;
      default:
        //TODO: Check if line should overflow
        if (!e.isControlDown() && !e.isAltDown()) {
          newLine = thisLine.substring(0, currentChar) + c
                  + thisLine.substring(Math.min(currentChar, thisLine.length()), thisLine.length());
          codeLines.set(currentLine, newLine);
          currentChar++;
          engine.updatedLine(currentLine);
        }
        break;
    }
  }


  private void endOfLine() {
    currentChar = Math.max(codeLines.get(currentLine).length(), currentChar);
  }

  private void startOfLine() {
    currentChar = 0;
  }

  public LinkedList<String> getCodeLines() {
    return codeLines;
  }

  public void setCode(LinkedList<String> newCode) {
      this.codeLines = newCode;
      currentLine = newCode.size()-1;
      currentChar = newCode.get(newCode.size()-1).length();
  }

  public void addCodeLine(String s) {
    codeLines.add(s);
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
