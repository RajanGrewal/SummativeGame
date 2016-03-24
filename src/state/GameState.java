/**
 * Author: Rajan Grewal
 * Date: Jan 8th 2014
 * File: GameState.java
 */
package state;

import entity.Block;
import entity.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import main.Game;

/**
 * Platform environment class
 */
public class GameState extends StateBase {

    private int height;
    private int width;
    private ArrayList<Block> solidBlocks;
    private ArrayList<Block> winningBlocks;
    private ArrayList<Block> deathBlocks;
    private Player player;
    
    /**
     * Constructor
     * @param game Parent game 
     */
    public GameState(Game game) {
        super(game);

        solidBlocks = new ArrayList<Block>();
        winningBlocks = new ArrayList<Block>();
        deathBlocks = new ArrayList<Block>();

        loadMap();
        loadPlayer();
    }

    @Override
    public void update() {
        player.update();

        for (Block b : deathBlocks) {
            if (b.getBounds().intersects(player.getBounds())) {
                loadPlayer();
                return;
            }
        }

        for (Block b : winningBlocks) {
            if (b.getBounds().intersects(player.getBounds())) {

                JOptionPane.showMessageDialog(null, "You Win!",
                        "Congratulations!",
                        JOptionPane.INFORMATION_MESSAGE);

                loadPlayer();
                game.setCurrentState(Game.MENU_STATE);
                return;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        int offsetX = -(int) player.getX() + Game.WIDTH / 2;
        int offsetY = -(int) player.getY() + Game.HEIGHT / 2;

        g.translate(offsetX, offsetY);

        player.draw(g);

        for (Block b : solidBlocks) {
            b.draw(g);
        }

        for (Block b : winningBlocks) {
            b.draw(g);
        }

        for (Block b : deathBlocks) {
            b.draw(g);
        }

        g.translate(-offsetX, -offsetY);
    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;
            case KeyEvent.VK_SPACE:
                player.jump();
                break;
        }
    }

    @Override
    public void keyReleased(int k) {
        switch (k) {
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
        }
    }

     /**
     * Create the player object
     */
    private void loadPlayer() {
        player = new Player(55, 35, this);
    }

    /**
     * Loads map from bitmap
     */
    private void loadMap() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/map.gif"));

            height = image.getHeight();
            width = image.getWidth();

            final int blackRGB = Color.BLACK.getRGB();
            final int yellowRGB = Color.YELLOW.getRGB();
            final int redRBG = Color.RED.getRGB();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int rgb = image.getRGB(x, y);
                    ArrayList<Block> blockList = null;

                    if (rgb == blackRGB) {
                        blockList = solidBlocks;
                    } else if (rgb == yellowRGB) {
                        blockList = winningBlocks;
                    } else if (rgb == redRBG) {
                        blockList = deathBlocks;
                    }

                    if (blockList != null) {
                    boolean success = true;
                    
                        for (Block b : blockList) {
                            if (b.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
                                success = false;
                                break;
                            }
                        }

                        if (success) {
                            System.out.println("Block at " + x + " " + y);
                            blockList.add(findBlock(image, x, y, rgb));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Unable to load map");
        }

    }

    /**
     * Finds a rectangle in a image
     * @param image Image source
     * @param x Rectangle X
     * @param y Rectangle Y
     * @param rgb Color
     * @return Rectangle occurrence 
     */
    private Block findBlock(BufferedImage image, int x, int y, int rgb) {
        int i = x;
        int j = y;

        int rectWidth = 0;
        int rectHeight = 0;

        while (image.getRGB(i, j) == rgb) {
            i++;
            rectWidth++;
        }

        i--; //caused by i++ in while loop

        while (image.getRGB(i, j) == rgb) {
            j++;
            rectHeight++;
        }

        return new Block(x, y, rectWidth, rectHeight, rgb);
    }
    
    
    /**
     * Where the point collides with a solid block
     * @param x X position
     * @param y Y position
     * @return If xy collides
     */
    public boolean intersectsSolidBlock(int x, int y) {
        for (Block b : solidBlocks) {
            if (b.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
                return true;
            }
        }
        return false;
    }
}
