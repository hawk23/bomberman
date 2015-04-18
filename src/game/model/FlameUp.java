package game.model;

public class FlameUp extends PowerUpItem
{
    private static final String flameUP = "res/visuals/bomb/flameUP.png";
	
    int value = 1;

    public FlameUp(int posX, int posY)
    {
        super(posX, posY);
//        setImage(flameUP);
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
