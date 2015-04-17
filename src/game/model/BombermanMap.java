package game.model;

import game.config.GameRoundConfig;
import game.input.InputManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.TiledMapWrapper;

public class BombermanMap implements IUpdateable, IRenderable
{
	TiledMapWrapper	wrapper;
	Player[]		players;
	
	public BombermanMap(GameRoundConfig config, GameContainer container) throws SlickException
	{
		this.wrapper = new TiledMapWrapper(config.getMapConfig().getPath());
		this.players = new Player[config.getCurrentPlayerConfigs().size()];

    	// create players and define controls
		for (int i = 0; i < this.players.length; i++)
		{
	        InputManager inputManager = new InputManager(container.getInput(), config.getCurrentInputConfigs().get(i));
			this.players[i] = new Player(this, inputManager, config.getCurrentPlayerConfigs().get(i), this.wrapper.getSpawnPoint(i)); 	
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		wrapper.render(container, game, g);

		for (int i = 0; i < this.players.length; i++)
		{
			this.players[i].render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		wrapper.update(container, game, delta);

		for (int i = 0; i < this.players.length; i++)
		{
			this.players[i].update(container, game, delta);
		}
	}
	
	public int getWidth() 
	{
		return this.wrapper.getWidth() * this.wrapper.getTileWidth();
	}

	public int getHeight() 
	{
		return this.wrapper.getHeight() * this.wrapper.getTileHeight();
	}
	
	public int getTileSize()
	{
		return this.wrapper.getTileHeight();
	}
}