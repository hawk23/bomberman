package game.model;

import game.debug.Debugger;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Roland Schreier on 31.03.2015.
 *
 * is collidable
 * is destroyable
 *
 * needs the BombermanMap to remove itself and calculate the explosion
 *
 */
public class Bomb extends GameObject implements IDestroyable
{

    private static final String path="res/visuals/bomb/bomb.png";
    private static final int animationInteval=40;

    private SpriteSheet bombSheet;
    private Animation animationBurn;

    private int range;
    private float timer;
    private boolean isExploding=false;

    private Player player;

    /**
     * Directions for the calculation of the blast, UP,LEFT,DOWN,RIGHT
     */
    private int blastDirection[][]=new int[][]{
            {1, 0, -1, 0},
            {0, 1, 0, -1}
    };

    public Bomb(int tileX, int tileY, Player player)
    {
        super(tileX, tileY);
        this.player=player;
        setTimer(player.getBombTimer());
        setRange(player.getBombRange());
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
//     */
    private void explode(){
//        isExploding=true;
//        for(int direction=0;direction<blastDirection[0].length;direction++){
//            for(int r=1;r<=range;r++){
//                //Debugger.log("(" + getTileX() + blastDirection[0][direction] * r + "," + getTileY() + blastDirection[1][direction] * r + ")");
//                /*If we hit a collision blast won't spread any longer in this direction*/
//                if(oldBombermanMap.isBlocked(getTileX() + blastDirection[0][direction] * r, getTileY() + blastDirection[1][direction] * r)) {
//                    oldBombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY() +blastDirection[1][direction]*r);
//                    break;
//                }else
//                    oldBombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY()+blastDirection[1][direction]*r);
//            }
//        }
//        player.removeBomb();
//        oldBombermanMap.removeGameObject(getTileX(), getTileY(), this);
    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g) {
        animationBurn.draw(posX,posY,64,64);
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Debugger.log("BombTimer: " + timer);
        if(timer<=0)
            destroy();
        timer-=delta;
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
