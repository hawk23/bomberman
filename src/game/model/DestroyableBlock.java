package game.model;

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
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g)
	{
		// no operation here
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean destroy()
	{
		return this.destroyed = true;
	}
	
	public boolean getDestroy()
	{
		return this.destroyed;
	}
}
