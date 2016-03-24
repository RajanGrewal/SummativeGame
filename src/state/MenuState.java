/**
 * Author: Rajan Grewal
 * Date: Jan 8th 2014
 * File: MenuState.java
 */
package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import main.Game;

/**
 * Menu environment class
 */
public class MenuState extends StateBase {

    BufferedImage background;
    
    private int menuSelection;
    private String[] menuItems;
    
    private Font titleFont;
    private Font messageFont;

    /**
     * Constructor
     * @param game Parent game 
     */
    public MenuState(Game game) {
        super(game);

        //init background
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/resources/background.gif"));
        } catch (IOException ex) {
            System.out.println("Unable to load cloud background");
        }

        //init menu
        menuSelection = 0;
        menuItems = new String[]{
            "Play",
            "Help",
            "Exit"
        };

        //init fonts
        titleFont = new Font("Century Gothic", Font.PLAIN, 40);
        messageFont = new Font("Arial", Font.PLAIN, 20);
    }

    @Override
    public void update() { } //nothing to do

    @Override
    public void draw(Graphics2D g) {

        //draw background
        g.drawImage(background, 0, 0, null);

        // draw title
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString("Summative Game", 225, 70);

        // draw menu options
        g.setFont(messageFont);
        for (int i = 0; i < menuItems.length; i++) {
            if (i == menuSelection) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuItems[i], 350, 250 + i * 30);
        }
        
        //draw footer
        g.setColor(Color.CYAN);
        g.drawString("By Rajan Grewal",630,580);
    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_ENTER: {
                switch (menuSelection) {
                    case 0:
                        game.setCurrentState(Game.GAME_STATE);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null,
                                "Controls\n"
                                + "----------------\n"
                                + "Right Arrow - Right\n"
                                + "Left Arrow - Left\n"
                                + "Space Bar - Jump\n"
                                + "\nObjective\n"
                                + "----------------\n"
                                + "Bump into the yellow block!",
                                "Help", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }

                break;
            }
            case KeyEvent.VK_UP: {
                menuSelection--;
                if (menuSelection == -1) {
                    menuSelection = menuItems.length - 1;
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                menuSelection++;
                if (menuSelection == menuItems.length) {
                    menuSelection = 0;
                }
                break;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }
}
