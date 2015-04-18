package game.model;

/**
 * Created by Mario on 18.04.2015.
 */
public class FlameUp extends PowerUpItem
{
    int value = 1;

    public FlameUp(int posX, int posY) {
        super(posX, posY, Explosion.timer);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
