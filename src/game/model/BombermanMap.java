package game.model;

import game.config.GameRoundConfig;
import game.input.InputManager;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.TiledMapWrapper;

public class BombermanMap implements IUpdateable, IRenderable
{
	TiledMapWrapper			wrapper;
	Player[]				players;
	Bomb[][]				bombs;
	ArrayList<GameObject>	objects;
	
	public BombermanMap(GameRoundConfig config, GameContainer container) throws SlickException
	{
		this.wrapper = new TiledMapWrapper(config.getMapConfig().getPath());
		this.players = new Player[config.getCurrentPlayerConfigs().size()];
		this.bombs	 = new Bomb[wrapper.getHeight()][wrapper.getWidth()];

		this.objects = new ArrayList<GameObject>();
		
    	// create players and define controls
		for (int i = 0; i < this.players.length; i++)
		{
	        InputManager inputManager = new InputManager(container.getInput(), config.getCurrentInputConfigs().get(i));
			this.players[i] = new Player(this, inputManager, config.getCurrentPlayerConfigs().get(i), this.wrapper.getSpawnPoint(i));
			this.objects.add(this.players[i]);
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		wrapper.render(container, game, g);

		for (int i = 0; i < this.objects.size(); i++)
		{
			this.objects.get(i).render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		wrapper.update(container, game, delta);
		
		for (int i = 0; i < this.objects.size(); i++)
		{
			this.objects.get(i).update(container, game, delta);
		}
	}
	
	/**
	 * isBlocked is true if a solid block or a bomb already exists at (x,y)
	 * @param tileX
	 * @param tileY
	 * @return
	 */
	public boolean isBlocked(int tileX, int tileY)
	{
		return this.wrapper.isBlocked(tileX, tileY) || this.bombs[tileX][tileY] != null;
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

	public void addBomb(Bomb bomb)
	{
		this.bombs[bomb.tileX][bomb.tileY] = bomb;
		this.objects.add(bomb);
	}
}