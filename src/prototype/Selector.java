package prototype;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;

/**
 * Created by Roland Schreier on 28.03.2015.
 */
public class Selector implements Renderable {

    private static final String PATH ="res/visuals/selector.png";
    private int BLOCK_SIZE;

    private Image image;
    private float posX,posY;


    public Selector(int BLOCK_SIZE) {
        this.BLOCK_SIZE=BLOCK_SIZE;
        try {
            image = new Image(PATH);
        }catch (SlickException e) {
            //TODO
        }
    }

    public void setPosition(float xPixel, float yPixel){
        this.posX=Math.round((xPixel-BLOCK_SIZE/2)/BLOCK_SIZE)*BLOCK_SIZE;
        this.posY=Math.round((yPixel-BLOCK_SIZE/2)/BLOCK_SIZE)*BLOCK_SIZE;
    }

    @Override
    public void draw(float v, float v1) {
        image.draw(posX+v,posY+v1,BLOCK_SIZE,BLOCK_SIZE);
    }

}
