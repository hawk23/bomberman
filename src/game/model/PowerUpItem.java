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
	
    public PowerUpItem(int posX, int posY)
    {
        super(posX, posY);
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

    }

	@Override
	public boolean destroy()
	{
		return true;
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
