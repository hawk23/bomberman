package game.config;

import org.newdawn.slick.Image;

/**
 * Created by Mario on 10.04.2015.
 */
public class PlayerConfig extends GameObjectConfig
{
    private int     initialBombLimit;
    private float   initialSpeed;

    public PlayerConfig() {
        super();
    }

    public int getInitialBombLimit() {
        return initialBombLimit;
    }

    public void setInitialBombLimit(int initialBombLimit) {
        this.initialBombLimit = initialBombLimit;
    }

    public float getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(float initialSpeed) {
        this.initialSpeed = initialSpeed;
    }
}
