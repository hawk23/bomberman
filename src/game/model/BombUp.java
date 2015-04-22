package game.model;

public class BombUp extends PowerUpItem
{
    private static final String	bombUPImagePath	= "res/visuals/powerups/PUbomb.png";
    int 						value			= 1;

    public BombUp(int posX, int posY)
    {
        super(posX, posY, Explosion.timer, bombUPImagePath);
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}
