package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * GameObject
 *   - Always collides
 *   - has tileX and tileY Position
 */
public class Block extends GameObject{

    private static final int TILE_SIZE = 64;
    private int tileX,tileY;



    public Block(int tileX, int tileY, int tileWidth, int tileHeight) {
        super(tileX*tileWidth, tileY*tileHeight);
        setTilePosition(tileX, tileY);
        this.setCollides(true);
    }

    public Block(int tileX, int tileY) {
        super(tileX*TILE_SIZE, tileY*TILE_SIZE);
        setTilePosition(tileX, tileY);
        this.setCollides(true);
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
    public void setTilePosition(int tileX, int tileY){
        setTileX(tileX);
        setTileY(tileY);
    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g) {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {

    }
}
