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
        return range;
    }

    public void setRange(int range) 
    {
        this.range = range;
    }

    public int getTimer() 
    {
        return timer;
    }

    public void setTimer(int timer) 
    {
        this.timer = timer;
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        bombAnimation.draw(tileX * GameSettings.TILE_HEIGHT, tileY * GameSettings.TILE_WIDTH);
        fuzeBurn.draw(tileX * GameSettings.TILE_HEIGHT + GameSettings.TILE_HEIGHT / 2, tileY * GameSettings.TILE_WIDTH+GameSettings.TILE_HEIGHT/2-FUZE_POS_Y-burnDist,3,burnDist);
        effectSystem.render();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
    	if (time <= timer) {
    		float timeLeft = timer-time;
            burnDist=FUZE_HEIGHT * timeLeft/timer;

            effectSystem.setPosition(tileX * GameSettings.TILE_HEIGHT+GameSettings.TILE_HEIGHT/2, tileY * GameSettings.TILE_WIDTH+GameSettings.TILE_HEIGHT/2-FUZE_POS_Y-burnDist);
            effectSystem.update(delta);

            time += delta;
    	}
    	else if (!exploded) {
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
            bombSheet		    = new SpriteSheet(BOMB_IMAGE_PATH, 64, 64);
            bombAnimation       = new Animation(bombSheet, ANIMATION_INTERVAL);
            SpriteSheet sheet	= new SpriteSheet(BURN_IMAGE_PATH, 3, 11);
            fuzeBurn            = new Animation(sheet, ANIMATION_INTERVAL);
        }
        catch (SlickException e)
        {
            //TODO
        }
    }

    private void loadParticleSystem(){
        try {
            effectSystem = ParticleIO.loadConfiguredSystem(EXPLOSION_CONFIG);
            flameEmitter = effectSystem.getEmitter(0);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        effectSystem.setPosition(tileX * GameSettings.TILE_HEIGHT+GameSettings.TILE_HEIGHT/2, tileY * GameSettings.TILE_WIDTH+GameSettings.TILE_HEIGHT/2-FUZE_POS_Y-burnDist);
    }

    public void addListener(ExplosionListener listener)
    {
        listeners.add(ExplosionListener.class, listener);
    }

    public void removeListener(ExplosionListener listener)
    {
        listeners.remove(ExplosionListener.class, listener);
    }

    protected synchronized void notifyExploded ()
    {
        ExplosionEvent e = new ExplosionEvent(this, this);

        for (ExplosionListener l : listeners.getListeners(ExplosionListener.class))
        {
            l.exploded(e);
        }
    }

	public int getTime() {
		return time;
	}


}