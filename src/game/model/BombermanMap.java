package game.model;

import game.config.GameRoundConfig;
import game.config.GameSettings;
import game.config.InputConfiguration;
import game.input.GamePadInputManager;
import game.input.InputManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import game.input.KeyboardInputManager;
import game.interfaces.IDestroyable;
import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;
import game.state.GameRoundState;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.ExplosionSystem;
import slick.extension.TiledMapWrapper;

public class BombermanMap implements IUpdateable, IRenderable
{
    private static final String powerUpSoundPath    = "/res/sounds/player/powerup.ogg";
    private Sound 			powerUpSound;
    
	TiledMapWrapper			wrapper;
	Player[]				players;
	Bomb[][]				bombs;
	Explosion[][]	        explosions;
    PowerUpItem[][]         powerups;
    Spike[][]				spikes;
	ArrayList<GameObject>	objects;
    ExplosionSystem         explosionSystem;
	SpikeGenerator			spikeGenerator;
	int						deadPlayers;
	private boolean			suddenDeath;
	
	public BombermanMap(GameRoundConfig config, GameContainer container) throws SlickException
	{
		this.wrapper 		= new TiledMapWrapper(config.getMapConfig().getPath());
		this.players 		= new Player[config.getCurrentPlayerConfigs().size()];
		this.bombs	 		= new Bomb[wrapper.getHeight()][wrapper.getWidth()];
		this.explosions		= new Explosion[wrapper.getHeight()][wrapper.getWidth()];
        this.powerups   	= new PowerUpItem[wrapper.getHeight()][wrapper.getWidth()];
        this.spikes			= new Spike[wrapper.getHeight()][wrapper.getWidth()];
        this.objects		= new ArrayList<GameObject>();
        this.explosionSystem= new ExplosionSystem();
        this.spikeGenerator	= new SpikeGenerator(this, GameRoundState.SUDDEN_DEATH_TIME, wrapper.getHeight(), wrapper.getWidth());
        this.suddenDeath	= false;
		this.deadPlayers	= 0;
        Controller[]    controllers     = ControllerEnvironment.getDefaultEnvironment().getControllers();
        int             keyboardsUsed   = 0;
        int             gamepadsUsed    = 0;
        
        loadSound();

    	// create players and define controls
		for (int i = 0; i < this.players.length; i++)
		{
            InputManager inputManager = null;

            if (config.getCurrentInputConfigs().size() > keyboardsUsed)
            {
                // Use Keyboard controls if possible
                inputManager    = new KeyboardInputManager(container.getInput(), config.getCurrentInputConfigs().get(keyboardsUsed));
                keyboardsUsed++;
            }
            else if (controllers.length > gamepadsUsed+1)
            {
                // Use Gamepads for the rest if possible
                inputManager    = new GamePadInputManager(container.getInput(),gamepadsUsed+1);
                gamepadsUsed++;
            }
            else
            {
                // Create empty input config if none of the above works
                inputManager    = new KeyboardInputManager(container.getInput(), new InputConfiguration());
            }

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

        explosionSystem.render(container, game, g);
        
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		/**
		 * Manage spikes
		 */
		if (suddenDeath) {
			
			spikeGenerator.update(container, game, delta);
			for (int i = 0; i < this.spikes.length; i++)
			{
				for (int j = 0; j < this.spikes[i].length; j++)
				{
					if (this.spikes[i][j] != null) {
						this.spikes[i][j].update(container, game, delta);
						
						if (this.wrapper.getBlockMatrix()[i][j] != null) {
							this.wrapper.removeBlock(i, j);
						}
						
						if (this.spikes[i][j].isDeadly()) {
							for (GameObject go : objects)
                            {
                                if (go instanceof IDestroyable && go.getTileX() == i && go.getTileY() == j)
                                {
                                    ((IDestroyable) go).destroy();
                                }
                            }
						}
					}
				}
			}
			
		}

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
                        Explosion explosion = new Explosion(bombs[i][j].getTileX(), bombs[i][j].getTileY(), bombs[i][j].getRange(), this.wrapper);
                        this.objects.add(explosion);
                        this.explosions[explosion.getTileX()][explosion.getTileY()] = explosion;

                        // add explosionSystem Particles
                        explosionSystem.addExplosion(explosion);

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

                                // drop powerup
                                handlePowerUp(p.x, p.y);
                            }

                            // check if objects are destroyed
                            for (GameObject go : objects)
                            {
                                if (go instanceof IDestroyable && go.getTileX() == p.x && go.getTileY() == p.y)
                                {
                                    ((IDestroyable) go).destroy();
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Manage power ups
         */
        for (int i = 0; i < this.powerups.length; i++)
        {
            for (int j = 0; j < this.powerups[i].length; j++)
            {
            	
                if (this.powerups[i][j] != null)
                {
                    PowerUpItem item = this.powerups[i][j];
                    item.update(container, game, delta);
                    
                    if (item.isDestroyed()) {
                    	this.powerups[i][j] = null;
                    	this.objects.remove(item);
                    }
                    else 
                    {
                        for (int k = 0; k < this.players.length; k++)
                        {
                            if (item.getTileX() == this.players[k].getTileX() &&
                                item.getTileY() == this.players[k].getTileY())
                            {
                                // consume power up
                                this.consumePowerUp(item, this.players[k]);

                                // remove
                                this.powerups[i][j] = null;
                                this.objects.remove(item);
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
            if (this.players[i].isDestroyed())
            {
                this.objects.remove(this.players[i]);
            }
            else
            {
                this.players[i].update(container, game, delta);
            }
		}

        /**
         * Calculate Particles of the ExplosionSystem
         */
        explosionSystem.update(container,game,delta);
	}

    private void consumePowerUp (PowerUpItem item, Player player)
    {
        if (item instanceof FlameUp)
        {
            FlameUp flameUp = (FlameUp) item;
            player.adjustBombRange(flameUp.getValue());
        }
        if (item instanceof BombUp)
        {
            BombUp bombUp = (BombUp) item;
            player.adjustBombLimit(bombUp.getValue());
        }
        if (item instanceof SpeedUp) 
        {
        	SpeedUp speedUp = (SpeedUp) item;
        	player.adjustSpeed(speedUp.getValue());
        }
        if (item instanceof ShieldUp) 
        {
        	ShieldUp shieldUp = (ShieldUp) item;
        	player.setShielded(shieldUp.getTime());
        }
        playSound(powerUpSound);
    }

    private void handlePowerUp (int tileX, int tileY)
    {
        int dropProb        = (int) (Math.random()*100 + 1);

        if (dropProb <= GameSettings.ITEM_PROBABILITY)
        {
            int             itemProb    = (int) (Math.random()*100 + 1);
            PowerUpItem     item        = null;

            if (itemProb >= 1 && itemProb <=25)
            {
                item                    = new BombUp(tileX, tileY);
            }
            else if (itemProb >= 26 && itemProb <= 50)
            {
                item                    = new FlameUp(tileX, tileY);
            }
            else if (itemProb >= 51 && itemProb <= 75)
            {
            	item                    = new SpeedUp(tileX, tileY);
            }
            else if (itemProb >= 76 && itemProb <= 100)
            {
            	item                    = new ShieldUp(tileX, tileY);
            }

            this.objects.add(item);
            this.powerups[tileX][tileY] = item;
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
	
	public int getNrDeadPlayer() 
	{
		return this.deadPlayers;
	}
	
	public void increaseNrDeadPlayer() 
	{
		this.deadPlayers++;
	}

	public int getNrPlayer() 
	{
		return this.players.length;
	}
	
	public int getTileSize()
	{
		return this.wrapper.getTileHeight();
	}

	public void addBomb(Bomb bomb)
	{
		this.bombs[bomb.getTileX()][bomb.getTileY()] = bomb;
		this.objects.add(bomb);
	}
	
	public void addSpike(Spike spike) {
		this.spikes[spike.getTileX()][spike.getTileY()] = spike;
		this.objects.add(spike);
	}
	
	public void startSuddenDeath() {
		this.suddenDeath = true;
	}

    public Player[] getPlayers() {
        return players;
    }
    
    private void loadSound ()
    {
        try
        {
            this.powerUpSound       = new Sound(powerUpSoundPath);
        }

        catch (SlickException e)
        {
            //TODO
        }
    }

    private void playSound(Sound sound)
    {
        sound.play();
    }
}