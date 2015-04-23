package game.model.objects;

public class FlameUp extends PowerUpItem
{
    private static final String flameUPImagePath	= "res/visuals/powerups/PUrange.png";
    int 						value 				= 1;

    public FlameUp(int posX, int posY) 
    {
        super(posX, posY, Explosion.timer, flameUPImagePath);
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
