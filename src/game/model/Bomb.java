package game.model;

import game.config.GameSettings;
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
 */
public class Bomb extends GameObject implements IDestroyable
{
    private static final String path 				= "res/visuals/bomb/bomb.png";
    private static final int	animationInteval	= 40;

    private SpriteSheet			bombSheet;
    private Animation			animationBurn;
    private int					range;
    private int					timer;
    private boolean				isExploding			= false;
    private Player				player;

    /**
     * Directions for the calculation of the blast, UP,LEFT,DOWN,RIGHT
     */
    private int blastDirection[][] = {{1, 0, -1, 0}, {0, 1, 0, -1}};

    public Bomb(int tileX, int tileY, int bombRange, int bombTimer, Player player)
    {
        super(tileX, tileY);
        this.player = player;
        this.timer = bombTimer;
        this.range = bombRange;

        loadImage();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public boolean destroy()
    {
        if(!isExploding)
        {
            explode();
            return true;
        }
        else
            return false;
    }

    /**
     * Calculates the explosion.
     * spreads in each blastDirection for its range
     */
    private void explode()
    {
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
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        animationBurn.draw(tileX * GameSettings.TILE_HEIGHT, tileY * GameSettings.TILE_WIDTH);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
    	Debugger.log("BombTimer: " + timer);
        if(timer<=0)
            destroy();
        timer-=delta;
    }

    private void loadImage()
    {
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
