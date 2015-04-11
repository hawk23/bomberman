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

    private SpriteSheet bombSheet;
    private Animation animationBurn;

    private int range;
    private float timer;

    private int renderCounter=0;

    private boolean isExploding=false;

    /**
     * Directions for the calculation of the blast, UP,LEFT,DOWN,RIGHT
     */
    private int blastDirection[][]=new int[][]{
            {1, 0, -1, 0},
            {0, 1, 0, -1}
    };

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
        if(!isExploding) {
            explode();
            return true;
        }else
            return false;
    }

    /**
     * Calculates the explosion.
     * spreads in each blastDirection for its range
     */
    private void explode(){
        isExploding=true;
        for(int direction=0;direction<blastDirection[0].length;direction++){
            for(int r=1;r<=range;r++){
                //Debugger.log("(" + getTileX() + blastDirection[0][direction] * r + "," + getTileY() + blastDirection[1][direction] * r + ")");
                /*If we hit a collision blast won't spread any longer in this direction*/
                if(bombermanMap.isCollision(getTileX() + blastDirection[0][direction]*r,getTileY()+blastDirection[1][direction]*r)) {
                    bombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY()+blastDirection[1][direction]*r);
                    break;
                }else
                    bombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY()+blastDirection[1][direction]*r);
            }
        }
        bombermanMap.removeGameObject(getTileX(), getTileY(), this);
    }


    // TODO: update und render methods from IUpdateable and IRenderable should be used instead of draw
    public void draw(float v, float v1) {
        //TODO change back, only for demonstrational of the fake zBuffering
        //animationBurn.draw(posX+v,posY+v1);
        animationBurn.draw(posX+v,posY+v1-64,64,190);
        //TODO implement update-Que, now counting render
        renderCounter++;
        if(renderCounter>10000)
            destroy();
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
