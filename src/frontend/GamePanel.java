package frontend;

import javax.swing.*;
import java.awt.*;

/**
 * Created by James on 28/02/15.
 */
public class GamePanel extends JPanel {
    public GamePanel() {
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.drawString("Sup bitches", 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
