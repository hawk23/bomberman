package game.model;

import game.config.GameSettings;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class PowerUpItem extends GameObject implements IDestroyable
{
    private static final int	animationInteval	= 40;
    private SpriteSheet			bombSheet;
    private Animation			animationBurn;

    private boolean			destroyed			= false;
    private int 			timer;
    
    public PowerUpItem(int posX, int posY, int flameTime)
    {
        super(posX, posY);
        timer = flameTime;
        loadImage(null);
    }
    
    public PowerUpItem(int posX, int posY, String path)
    {
        super(posX, posY);
        loadImage(path);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        //g.drawImage(this.image, this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
        animationBurn.draw(tileX * GameSettings.TILE_HEIGHT, tileY * GameSettings.TILE_WIDTH);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
    	if (timer > 0) {
        	timer -= delta;
    	}
    }

	@Override
	public boolean destroy()
	{
		if (timer <= 0) {
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
	
    private void loadImage(String path)
    {
    	if(path == null)
    	{
    		path = "/res/visuals/default.png";
    	}
        try
        {
            bombSheet		= new SpriteSheet(path, 64, 64);
            animationBurn	= new Animation(bombSheet, animationInteval);
        }
        catch (SlickException e)
        {
            //TODO
        }
    }
}
