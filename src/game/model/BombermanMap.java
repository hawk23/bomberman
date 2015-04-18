package game.model;

import game.config.GameRoundConfig;
import game.config.GameSettings;
import game.event.ExplosionEvent;
import game.input.InputManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

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
	Explosion[][]	        explosions;
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
        /**
         * Manage bombs
         */
		for (int i = 0; i < this.bombs.length; i++)
		{
			for (int j = 0; j < this.bombs[i].length; j++)
			{
				if(this.bombs[i][j] != null)
				{
                    if (this.bombs[i][j].isExploded())
                    {
                        // add explosion
                        Explosion explosion = new Explosion(bombs[i][j].getPosX(), bombs[i][j].getPosY(), bombs[i][j].getRange(), this.wrapper);
                        this.objects.add(explosion);
                        this.explosions[explosion.getPosX()][explosion.getPosY()] = explosion;

                        // remove bomb
                        this.objects.remove(this.bombs[i][j]);
                        this.bombs[i][j] = null;
                    }
                    else
                    {
                        this.bombs[i][j].update(container, game, delta);
                    }
				}
				
			}
		}

        /**
         * Manage explosion
         */
        for (int i = 0; i < this.explosions.length; i++)
        {
            for (int j = 0; j < this.explosions[i].length; j++)
            {
                if (this.explosions[i][j] != null)
                {
                    Explosion explosion =  this.explosions[i][j];

                    explosion.update(container, game, delta);

                    if (explosion.isFinished())
                    {
                        // remove explosion
                        this.explosions[i][j] = null;
                        this.objects.remove(explosion);
                    }
                    else
                    {
                        // check if anything is destroyed by current explosions
                        for (Point p : explosion.getFlamePositions())
                        {
                            // check if block is destroyed
                            if (this.wrapper.isDestroyable(p.x, p.y))
                            {
                                ((DestroyableBlock) this.wrapper.getBlockMatrix()[p.x][p.y]).destroy();
                            }
                        }
                    }
                }
            }
        }


        /**
         * Manage wrapper
         */
        this.wrapper.update(container, game, delta);

        /**
         * Manage players
         */
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
		this.bombs[bomb.tileX][bomb.tileY] = bomb;
		this.objects.add(bomb);
	}
}