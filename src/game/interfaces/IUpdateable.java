package game.interfaces;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface IUpdateable
{
	public void update(GameContainer container, StateBasedGame game, int delta);
}
