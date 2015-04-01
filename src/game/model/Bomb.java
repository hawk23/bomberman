package game.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Roland Schreier on 31.03.2015.
 */
public class Bomb extends GameObject implements IDestroyable {

    private static final String path="res/visuals/bomb/bomb.png";
    private static final int animationInteval=40;

    private SpriteSheet bombSheet;
    private Animation animationBurn;

    private int range=1;
    private float timer;

    public Bomb(int posX, int posY){
        setPosition(posX,posY);
        loadImage();
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public void draw(float v, float v1) {
        animationBurn.draw(posX+v,posY+v1);
    }

    private void loadImage(){
        try {
            bombSheet = new SpriteSheet(path,64,64);
            animationBurn = new Animation(bombSheet,animationInteval);
        }catch (SlickException e) {
            //TODO
        }
    }
}
