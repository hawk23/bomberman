package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.TiledMapWrapper;

public class BombermanMap implements IUpdateable, IRenderable
{
	TiledMapWrapper	wrapper;
	Player[]		players;
	
	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g)
	{
		wrapper.render(container, stateBasedGame, g);
	}
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
