package game.model;

/**
 * Created by Mario on 18.04.2015.
 */
public class BombUp extends PowerUpItem
{
    int value = 1;

    public BombUp(int posX, int posY) {
        super(posX, posY);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
