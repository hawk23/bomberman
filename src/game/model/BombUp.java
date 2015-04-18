package game.model;

public class BombUp extends PowerUpItem
{
    private static final String     bombUPImagePath	= "res/visuals/bomb/bombUp.png";
    int 							value			= 1;

    public BombUp(int posX, int posY)
    {
        super(posX, posY);
        setImage(bombUP);
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
