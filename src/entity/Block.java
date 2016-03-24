/**
 * Author: Rajan Grewal
 * Date: Jan 10th 2014
 * File: Block.java
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Block entity class
 */
public class Block extends Entity {

    private Color color;
    
    /**
     * Constructor
     * @param x X position
     * @param y Y position
     * @param width Width
     * @param height Height
     * @param rgb Color
     */
    public Block(float x,float y,int width,int height,int rgb)
    {
        super(x,y,width,height);
        color = new Color(rgb);
    }
    
    @Override
    public void update() { } //nothing to do
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int)x ,(int)y, width, height);
    }
}
