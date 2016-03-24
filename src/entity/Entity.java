/**
 * Author: Rajan Grewal
 * Date: Jan 10th 2014
 * File: Entity.java
 */
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Base class for all map entities
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;

    /**
     * Constructor
     * @param x X position
     * @param y Y position
     * @param width Width
     * @param height Height
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Updates logic
     */
    public abstract void update();
    
    /**
     * Drawing logic
     * @param g Games graphics
     */
    public abstract void draw(Graphics2D g);

    /**
     * Gets bounds
     * @return Y bounds
     */
    public Rectangle getBounds()
    {
        return new Rectangle((int)x,(int)y,width,height);
    }
    
        /**
     * Gets X position
     * @return X position
     */
    public float getX() {
        return x;
    }
    
    /**
     * Gets Y position
     * @return Y position
     */
    public float getY() {
        return y;
    }
}
