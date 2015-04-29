package game.model.objects;

import game.config.GameSettings;
import game.event.ExplosionEvent;
import game.interfaces.IDestroyable;
import game.model.ExplosionListener;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.event.EventListenerList;

public class Bomb extends GameObject implements IDestroyable
{
    private static final String             BOMB_IMAGE_PATH     = "res/visuals/bomb/bomb.png";
    private static final String             BURN_IMAGE_PATH     = "res/visuals/bomb/burn.png";
    private static final String             EXPLOSION_CONFIG    = "res/visuals/particles/bombSparks.xml";
    private static final int                ANIMATION_INTERVAL = 40;
    protected static final int              FUZE_POS_Y = 13;
    protected static final int              FUZE_HEIGHT = 13;
    protected SpriteSheet			        bombSheet;
    protected Animation                     bombAnimation;
    protected Animation                     fuzeBurn;
    
    protected int					        range;
    protected int					        timer;
    protected int					        time;
    protected boolean				        exploded;
    protected boolean						destroyed;
    private EventListenerList               listeners           = new EventListenerList();

    protected ParticleSystem  				effectSystem;
    protected ParticleEmitter 				flameEmitter;

    protected float 						burnDist = FUZE_HEIGHT;

    
    public Bomb(int tileX, int tileY, int bombRange, int bombTimer)
    {
        super(tileX, tileY);
        this.timer		            = bombTimer;
        this.range		            = bombRange;
        this.time		            = 0;
        this.exploded	            = false;
        this.destroyed				= false;
        loadAnimation();
        loadParticleSystem();
    }

    public int getRange()
    {
        return this.range;
    }

    public void setRange(int range) 
    {
        this.range = range;
    }

    public int getTimer() 
    {
        return this.timer;
    }

    public void setTimer(int timer) 
    {
        this.timer = timer;
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
    	this.bombAnimation.draw(this.tileX * GameSettings.TILE_HEIGHT, this.tileY * GameSettings.TILE_WIDTH);
    	this.fuzeBurn.draw(this.tileX * GameSettings.TILE_HEIGHT + GameSettings.TILE_HEIGHT /2, 
    			this.tileY * GameSettings.TILE_WIDTH+GameSettings.TILE_HEIGHT /2 - FUZE_POS_Y - this.burnDist, 
    			3, 
    			this.burnDist);
    	this.effectSystem.render();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
    	if (this.time <= this.timer) {
    		float timeLeft = this.timer - this.time;
    		this.burnDist = FUZE_HEIGHT * timeLeft /this.timer;

    		this.effectSystem.setPosition(this.tileX * GameSettings.TILE_HEIGHT + GameSettings.TILE_HEIGHT /2, 
    				this.tileY * GameSettings.TILE_WIDTH + GameSettings.TILE_HEIGHT /2 -FUZE_POS_Y - this.burnDist);
    		this.effectSystem.update(delta);

    		this.time += delta;
    	}
    	else if (!this.exploded) {
    		setExploded();
    	}   
    }
    
	@Override
	public boolean isDestroyed() {
		return this.destroyed;
	}
	
	@Override
    public boolean destroy()
    {
        setExploded();
        return true;
    }
    
    public void setExploded()
    {
    	this.exploded = true;
    	this.destroyed = true;
        this.notifyExploded();
    }
    
    public boolean isExploded()
    {
    	return this.exploded;
    }

    public int getBombRange()
    {
    	return this.range;
    }
    
    private void loadAnimation()
    {
        try
        {
        	this.bombSheet		   	= new SpriteSheet(BOMB_IMAGE_PATH, 64, 64);
        	this.bombAnimation      = new Animation(this.bombSheet, ANIMATION_INTERVAL);
            SpriteSheet sheet		= new SpriteSheet(BURN_IMAGE_PATH, 3, 11);
            this.fuzeBurn           = new Animation(sheet, ANIMATION_INTERVAL);
        }
        catch (SlickException e)
        {
            //TODO
        }
    }

    private void loadParticleSystem(){
        try {
        	this.effectSystem = ParticleIO.loadConfiguredSystem(EXPLOSION_CONFIG);
        	this.flameEmitter = this.effectSystem.getEmitter(0);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        this.effectSystem.setPosition(this.tileX * GameSettings.TILE_HEIGHT + GameSettings.TILE_HEIGHT /2, 
        		this.tileY * GameSettings.TILE_WIDTH + GameSettings.TILE_HEIGHT /2 - FUZE_POS_Y - this.burnDist);
    }

    public void addListener(ExplosionListener listener)
    {
    	this.listeners.add(ExplosionListener.class, listener);
    }

    public void removeListener(ExplosionListener listener)
    {
    	this.listeners.remove(ExplosionListener.class, listener);
    }

    protected synchronized void notifyExploded ()
    {
        ExplosionEvent e = new ExplosionEvent(this, this);

        for (ExplosionListener l : this.listeners.getListeners(ExplosionListener.class))
        {
            l.exploded(e);
        }
    }

	public int getTime() {
		return this.time;
	}


}