package frontend;

import backend.GameEngine;
import backend.GameObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private GameEngine engine;
    private HashMap<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    private int imgWidth = 0;
    private int imgHeight = 0;
    private boolean isInvalidated = false;
    private int wTiles = 0, hTiles = 0;
    private boolean light = false;
    private static final String DEFAULT_TEXTURE = "water";
    private HashMap<TileLocation, GameObject> tiles;
    private boolean setup;

  public GamePanel(GameEngine engine) {
        this.engine = engine;
        engine.setGamePanel(this);
        setOpaque(true);
        setBackground(Color.WHITE);
        tiles = engine.getTiles();

    }

    public void turnOffTheLight() {
        light = false;
        isInvalidated = true;
    }

    public void turnOnTheLight() {
        light = true;
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

//        System.out.println(grass);

        wTiles = (int) Math.ceil((g2d.getClipBounds().getWidth() / imgWidth));
        hTiles = (int) Math.ceil((g2d.getClipBounds().getHeight() / imgHeight));

    }

    private void doDrawing(Graphics g) {
        if (!light) return;
        Graphics2D g2d = (Graphics2D) g;

        if (!setup) {
            setup(g2d);
        }


//        System.out.println("00 - " + tiles.get(new TileLocation(1,1)));

        int wTiles = (int) Math.ceil((g2d.getClipBounds().getWidth() / imgWidth));
        int hTiles = (int) Math.ceil((g2d.getClipBounds().getHeight() / imgHeight));

        for (int i = 0; i < wTiles; i++) {
            for (int j = 0; j < hTiles; j++) {
                GameObject object = tiles.get(new TileLocation(i, j));
                String imgFile;
                if (object != null) {
                  System.out.println("Not null");
                  imgFile = tiles.get(new TileLocation(i, j)).getTextureName();
                } else {
                  imgFile = DEFAULT_TEXTURE;
                }
                BufferedImage img = null;
                if (imageCache.containsKey(imgFile) && imageCache.get(imgFile) != null) {
//                    System.out.println("file " + imgFile + " exists");
                    img = imageCache.get(imgFile);
                } else {
                    try {
                        String imgName = "res/" + imgFile + ".png";
//                        System.out.println(imgName);
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
        setup = true;
    }

    public boolean isInvalidated() {
        return isInvalidated;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
