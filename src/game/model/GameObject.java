package game.model;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Mario on 30.03.2015.
 *
 * edited by Roland on 01.04.2015
 *      implements Renderable + draw-method
 *      setPosition
 *      setImage
 *      Constructor
 */
public class GameObject implements Renderable{
    protected int         id;
    protected int         posX;
    protected int         posY;
    protected boolean     collides;
    protected Image image;

    public GameObject(){
        setPosition(0,0);
        setImage(null);
    }

    public GameObject(int posX, int posY) {
        setPosition(posX,posY);
        setImage(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) { this.posY = posY; }

    public void setPosition(int x, int y){ setPosX(x); setPosY(y);}

    public boolean isCollides() {
        return collides;
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    @Override
    public void draw(float v, float v1) { image.draw(posX+v,posY+v1,1); }

    private void setImage(String path){
        if(path==null)
            path="/res/visuals/default.png";
        try {
            image = new Image(path);
        }catch (SlickException e) {
            //TODO
        }
    }
}
