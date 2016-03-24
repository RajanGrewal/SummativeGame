/**
 * Author: Rajan Grewal
 * Date: Jan 8th 2014
 * File: Player.java
 */
package entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import state.GameState;

/**
 * Player entity class
 */
public class Player extends Entity {

    private static final float HORIZONTAL_VELOCITY = 4.60f;
    private static final float FRICTION = 0.860f;
    private static final float GRAVITY = 0.560f;
    private static final float MAX_VELOCITY = 10f;
    
    private GameState gs;
    private float xa, ya;
    private int jumpCount;
    private boolean right, left;

    /**
     * Constructor
     * @param x X position
     * @param y Y position
     * @param gs Parent state
     */
    public Player(int x,int y,GameState gs) {
        super(x,y,32,32);
        this.gs = gs;
    }

    @Override
    public void update() {

        if (right) { //move right
            xa = HORIZONTAL_VELOCITY;
        }

        if (left) { //move left
            xa = -HORIZONTAL_VELOCITY;
        }

        ya += GRAVITY; //apply gravity

        if (ya > MAX_VELOCITY) { //cap velocity
            ya = MAX_VELOCITY;
        }
        
        //checking intersections

        if (xa != 0) { //horizontal movement
            if (xa < 0) {//movement towards the left
                for (int i = 0; i > xa; i--) {
                    for (int j = 0; j < height; j++) {
                        if (gs.intersectsSolidBlock((int) x + i, (int) y + j)) {
                            xa = i + 1;
                        }
                    }
                }
            } else if (xa > 0) {  //movement towards the right
                for (int i = 0; i < xa; i++) {
                    for (int j = 0; j < height; j++) {
                        if (gs.intersectsSolidBlock((int) x + i + width, (int) y + j)) {
                            xa = i;
                        }
                    }
                }
            }
        }

        if (ya != 0) { //vertical movement
            if (ya < 0) { //up movement
                for (int i = 0; i > ya; i--) {
                    for (int j = 0; j < width; j++) {
                        if (gs.intersectsSolidBlock((int) x + j, (int) y + i)) {
                            ya = i + 1;
                        }
                    }
                }
            } else if (ya > 0) {  //down movement
                for (int i = 0; i < ya; i++) {
                    for (int j = 0; j < width; j++) {
                        if (gs.intersectsSolidBlock((int) x + j, (int) y + i + height)) {
                            ya = i;
                            jumpCount = 0; //reset jumps
                        }
                    }
                }
            }
        }

        //increment positions
        x += xa;
        y += ya;

        xa *= FRICTION; //apply friction
    }

    @Override
    public void draw(java.awt.Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, width, height);
    }

    /**
     * Tries to jump
     */
    public void jump() {
        if (jumpCount < 2) { //double jump
            ya = -15; //give upwards velocity
            jumpCount++;
        }
    }

    /**
     * Sets left movement
     * @param left value
     */
    public void setLeft(boolean left) {
        this.left = left;
    }
    
    /**
     * Sets right movement
     * @param right value
     */
    public void setRight(boolean right) {
        this.right = right;
    }
}