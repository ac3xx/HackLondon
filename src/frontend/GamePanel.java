package frontend;

import backend.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by James on 28/02/15.
 */
public class GamePanel extends JPanel {
    private GamePanelListener listener;
    private HashMap<TileLocation, String> tiles = new HashMap<TileLocation, String>();
    private HashMap<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    private int imgWidth = 0;
    private int imgHeight = 0;
    private boolean isInvalidated = false;
    private int wTiles = 0, hTiles = 0;

  public GamePanel() {
        setOpaque(true);
        setBackground(Color.WHITE);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mouseClick(x, y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void mouseClick(int x, int y) {
        int xElem = (int) Math.floor(((double) x / ((double)imgWidth * (double)wTiles)) * (double)wTiles);
        int yElem = (int) Math.floor(((double) y / ((double)imgHeight * (double)hTiles)) * (double)hTiles);


    }

    public void turnOffTheLight() {
        String fileName = Tile.values()[0].getFileName();
        for (int i = 0; i < wTiles; i++) {
          for (int j = 0; j < hTiles; j++) {
            setTile(new TileLocation(i, j), "rock");
          }
        }
        isInvalidated = true;
    }

    public void turnOnTheLight() {
        String fileName = Tile.values()[1].getFileName();
        for (int i = 0; i < wTiles; i++) {
            for (int j = 0; j < hTiles; j++) {
                setTile(new TileLocation(i, j), "grass");
            }
        }
        isInvalidated = true;
    }


    private void setup(Graphics2D g2d) {
        // Temporary init
        BufferedImage grass = null;
        try {
            grass = ImageIO.read(new File("res/rock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgWidth = grass.getWidth();
        imgHeight = grass.getHeight();

        wTiles = (int) Math.ceil((g2d.getClipBounds().getWidth() / imgWidth));
        hTiles = (int) Math.ceil((g2d.getClipBounds().getHeight() / imgHeight));

        for (int i = 0; i < wTiles; i++) {
            for (int j = 0; j < hTiles; j++) {
                TileLocation loc = new TileLocation(i, j);
                tiles.put(loc, "rock");
            }
        }
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (tiles.isEmpty()) {
            setup(g2d);
        }


        int wTiles = (int) Math.ceil((g2d.getClipBounds().getWidth() / imgWidth));
        int hTiles = (int) Math.ceil((g2d.getClipBounds().getHeight() / imgHeight));

        for (int i = 0; i < wTiles; i++) {
            for (int j = 0; j < hTiles; j++) {
                String imgFile = tiles.get(new TileLocation(i, j));
                BufferedImage img = null;
                if (imageCache.containsKey(imgFile) && imageCache.get(imgFile) != null) {
                    img = imageCache.get(imgFile);
                } else {
                    try {
                        String imgName = "res/" + imgFile + ".png";
                        img = ImageIO.read(new File(imgName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageCache.put(imgFile, img);
                }

                g2d.drawImage(img, i * img.getWidth(), j * img.getHeight(), null);
            }
        }

        isInvalidated = false;
    }

    public boolean isInvalidated() {
        return isInvalidated;
    }

    public void setTile(TileLocation loc, String imageName) {
        tiles.put(loc, imageName);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

  public void setListener(GamePanelListener listener) {
    this.listener = listener;
  }
}
