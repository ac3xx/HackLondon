package frontend;

import backend.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
}
