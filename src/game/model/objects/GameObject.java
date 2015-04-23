package game.model.objects;

import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class GameObject implements IRenderable, IUpdateable
{
    protected int       id;
    
    protected int       tileX;
    protected int       tileY;
    
    protected boolean   collides;
    protected Image		image;

    public GameObject()
    {
        setPosition(0,0);
        setImage(null);
    }

    public GameObject(int tileX, int tileY)
    {
        setPosition(tileX,tileY);
        setImage(null);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getTileX()
    {
        return tileX;
    }

    public void setTileX(int tileX)
    {
        this.tileX = tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public void setTileY(int tileY)
    {
    	this.tileY = tileY;
    }

    public void setPosition(int x, int y)
    {
    	setTileX(x);
    	setTileY(y);
    }

    protected void setImage(String path)
    {
        if(path == null)
        {
        	path = "res/visuals/default.png";
        }
        try
        {
        	this.image = new Image(path);
        }
        catch (SlickException e)
        {
            // TODO
        }
    }

    public Image getImage()
    {
        return this.image;
    }
}
