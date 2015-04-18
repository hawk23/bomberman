package game.model;

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

    public GameObject(int posX, int posY)
    {
        setPosition(posX,posY);
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

    public int getPosX()
    {
        return tileX;
    }

    public void setPosX(int posX)
    {
        this.tileX = posX;
    }

    public int getPosY()
    {
        return tileY;
    }

    public void setPosY(int posY)
    {
    	this.tileY = posY;
    }

    public void setPosition(int x, int y)
    {
    	setPosX(x);
    	setPosY(y);
    }

    public boolean isCollides()
    {
        return collides;
    }

    public void setCollides(boolean collides)
    {
        this.collides = collides;
    }

    private void setImage(String path)
    {
        if(path == null)
        {
        	path = "/res/visuals/default.png";
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
