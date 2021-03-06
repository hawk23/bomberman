package game.model.objects;

import game.config.GameSettings;
import game.interfaces.IDestroyable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class PowerUpItem extends GameObject implements IDestroyable
{
    private static final int	animationInterval	= 40;
    private SpriteSheet			bombSheet;
    private Animation			animationBurn;

    private boolean				destroyed			= false;
    private int 				timer;
    
    public PowerUpItem(int posX, int posY, int flameTime, String path)
    {
        super(posX, posY);
        this.timer = flameTime;
        loadAnimation(path);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
    	this.animationBurn.draw(this.tileX * GameSettings.TILE_HEIGHT, this.tileY * GameSettings.TILE_WIDTH);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
    	if (this.timer > 0) {
    		this.timer -= delta;
    	}
    }

	@Override
	public boolean destroy()
	{
		if (this.timer <= 0) {
			return this.destroyed = true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDestroyed() {
		return this.destroyed;
	}
	
    private void loadAnimation(String path)
    {
    	if(path == null)
    	{
    		path = "res/visuals/default.png";
    	}
        try
        {
        	this.bombSheet		= new SpriteSheet(path, 64, 64);
        	this.animationBurn	= new Animation(bombSheet, animationInterval);
        }
        catch (SlickException e)
        {
            //TODO
        }
    }
}
