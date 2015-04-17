package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SolidBlock extends Block
{
    public SolidBlock(int posX, int posY) 
    {
        super(posX, posY);
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
}
