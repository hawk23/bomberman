package game.model;

/**
 * Created by Roland Schreier on 03.04.2015.
 */
public class SolidBlock extends Block {
    public SolidBlock(int tileX, int tileY, int tileWidth, int tileHeight) {
        super(tileX, tileY, tileWidth, tileHeight);
    }

    public SolidBlock(int tileX, int tileY) {
        super(tileX, tileY);
    }

    @Override
    public String toString() {
        return "SolidBlock ("+getTileX()+","+getTileY()+")";
    }
}
