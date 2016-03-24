/**
 * Author: Rajan Grewal
 * Date: Jan 8th 2014
 * File: Game.java
 */
package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import state.*;

/**
 * Game Window class
 */
public class Game extends JFrame implements KeyListener {
    
    private static final long TARGETTIME = 1000 / 60;
    
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public static final int MENU_STATE = 0;
    public static final int GAME_STATE = 1;
    
    private StateBase[] gameStates;
    private int currentState;

    /**
     * Constructor
     */
    private Game() {
        super("Gucci Mane Game");
        
        currentState =  MENU_STATE;
        
        gameStates = new StateBase[]
        {
          new MenuState(this), //0
          new GameState(this), //1
        };
        
        //init frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT ));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setResizable(false);
        pack();
        setVisible(true);
    }
    
    /**
     * Games main loop
     */
    public void run() {
        long start;
        long elapsed;
        long wait;
        
        //create graphics
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        addKeyListener(this); //hook key presses
        
        while (true) {
            start = System.nanoTime();

            //state logic
            gameStates[currentState].update();
            gameStates[currentState].draw(g);
            
            //draw to screen
            Graphics screen = getGraphics();
            screen.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
            screen.dispose();
            
            //get elapsed
            elapsed = System.nanoTime() - start;

            wait = TARGETTIME - elapsed / 1000000;
 
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
                break;  //graceful termination
            }
        }
    }

    /**
     * Gets the games current state
     * @return Current state
     */
    public int getCurrentState()
    {
        return currentState;
    }
    
    /**
     * Sets the games current state
     * @param state State value
     */
    public void setCurrentState(int state)
    {
        currentState = state;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
         gameStates[currentState].keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameStates[currentState].keyReleased(e.getKeyCode());
    }
    
    /**
     * Program Entrypoint
     * @param args
     */
    public static void main(String[] args) {
        new Game().run();
    }
}