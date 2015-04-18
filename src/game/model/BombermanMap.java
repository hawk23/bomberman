package game.model;

import game.config.GameRoundConfig;
import game.config.GameSettings;
import game.input.InputManager;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.TiledMapWrapper;

public class BombermanMap implements IUpdateable, IRenderable, ExplosionListener
{
	TiledMapWrapper			wrapper;
	Player[]				players;
	Bomb[][]				bombs;
	Explosion[][]			explosions;
	ArrayList<GameObject>	objects;
	
	public BombermanMap(GameRoundConfig config, GameContainer container) throws SlickException
	{
		this.wrapper 	= new TiledMapWrapper(config.getMapConfig().getPath());
		this.players 	= new Player[config.getCurrentPlayerConfigs().size()];
		this.bombs	 	= new Bomb[wrapper.getHeight()][wrapper.getWidth()];
		this.explosions	= new Explosion[wrapper.getHeight()][wrapper.getWidth()];
		this.objects	= new ArrayList<GameObject>();
		
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

        Collections.sort(this.objects, new GameObjectsYPosSorter());

        for (GameObject gameObject : this.objects)
        {
            gameObject.render(container, game, g);
        }
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		wrapper.update(container, game, delta);
		
		for (int i = 0; i < this.bombs.length; i++)
		{
			for (int j = 0; j < this.bombs[i].length; j++)
			{
				if(this.bombs[i][j] != null)
				{
					this.bombs[i][j].update(container, game, delta);

                    if(this.bombs[i][j].isExploded())
                    {
                        this.explosions[i][j] = new Explosion(this.bombs[i][j].getBombRange());
                        // TODO remove bomb from game objects and bombs
                    }
				}
		    }
        }
		
		for (int i = 0; i < this.players.length; i++)
		{
			this.players[i].update(container, game, delta);
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
        bomb.addExplosionListener(this);
		this.bombs[bomb.tileX][bomb.tileY] = bomb;
		this.objects.add(bomb);
	}

    @Override
    public void exploded(Bomb bomb)
    {
        // TODO
    }
}