package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface IUpdateable {
	
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta);
}
