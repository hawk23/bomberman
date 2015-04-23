package game.model.objects;

import game.interfaces.IDestroyable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class DestroyableBlock extends Block implements IDestroyable
{
	private boolean destroyed;
	
    public DestroyableBlock(int posX, int posY)
    {
        super(posX, posY);
        
        this.destroyed = false;
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		// no operation here
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean destroy()
	{
		return this.destroyed = true;
	}

	@Override
	public boolean isDestroyed() {
		return this.destroyed;
	}
}
