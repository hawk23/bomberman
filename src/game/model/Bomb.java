package game.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Roland Schreier on 31.03.2015.
 *
 * is collidable
 * is destroyable
 *
 * needs the BombermanMap to remove itself and calculate the explosion
 *
 * TODO: add update-Interface so the time can be decreased on every main update-loop
 *       add updateQue to the BombermanMap and call the BombermanMap in the Game class
 *       add the logic for the explosion
 */
public class Bomb extends DestroyableBlock{

    private static final String path="res/visuals/bomb/bomb.png";
    private static final int animationInteval=40;

    private BombermanMap bombermanMap;

    private SpriteSheet bombSheet;
    private Animation animationBurn;

    private int range=1;
    private float timer;

    public Bomb(int tileX, int tileY, int tileWidth, int tileHeight, float timer, int range, BombermanMap bombermanMap) {
        super(tileX, tileY, tileWidth, tileHeight, bombermanMap);
        setTimer(timer);
        setRange(range);
        loadImage();
    }

    public Bomb(int tileX, int tileY, float timer, int range,BombermanMap bombermanMap) {
        super(tileX, tileY, bombermanMap);
        setTimer(timer);
        setRange(range);
        loadImage();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public void draw(float v, float v1) {
        //TODO change back, only for demonstrational of the fake zBuffering
        //animationBurn.draw(posX+v,posY+v1);
        animationBurn.draw(posX+v,posY+v1-64,64,190);
    }

    private void loadImage(){
        try {
            bombSheet = new SpriteSheet(path,64,64);
            animationBurn = new Animation(bombSheet,animationInteval);
        }catch (SlickException e) {
            //TODO
        }
    }


    @Override
    public String toString() {
        return "Bomb ("+getTileX()+","+getTileY()+"): Timer:"+getTimer()+", Range:"+getRange();
    }
}
