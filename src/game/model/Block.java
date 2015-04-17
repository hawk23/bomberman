package game.model;

public abstract class Block extends GameObject
{
    public Block(int posX, int posY)
    {
    	super(posX, posY);
        this.setCollides(true);
    }
}
