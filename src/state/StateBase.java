/**
 * Author: Rajan Grewal
 * Date: Jan 8th 2014
 * File: StateBase.java
 */
package state;

import main.Game;

/**
 * Base class for all state environments
 */
public abstract class StateBase {

    protected Game game;
    
    /**
     * Constructor
     * @param game The state parent
     */
    public StateBase(Game game)
    {
        this.game = game;
    }
    
    /**
     * Updates logic
     */
    public abstract void update();

    /**
     * Drawing logic
     * @param g Games graphics
     */
    public abstract void draw(java.awt.Graphics2D g);

    /**
     * Key pressed event
     * @param k Pressed key code
     */
    public abstract void keyPressed(int k);

    /**
     * Key released event
     * @param k Released key code
     */
    public abstract void keyReleased(int k);
}