package game.model;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * Needs to know the parent BombermanMap to remove itself of the map
 *
 */
public class DestroyableBlock extends Block implements IDestroyable {

    protected OldBombermanMap oldBombermanMap;

    public DestroyableBlock(int tileX, int tileY, int tileWidth, int tileHeight, OldBombermanMap oldBombermanMap) {
        super(tileX, tileY, tileWidth, tileHeight);
        setBombermanMap(oldBombermanMap);
    }

    public DestroyableBlock(int tileX, int tileY, OldBombermanMap oldBombermanMap) {
        super(tileX, tileY);
        setBombermanMap(oldBombermanMap);
    }

    @Override
    public boolean destroy() {
        return oldBombermanMap.removeBlock(getTileX(), getTileY(), this);
    }

    private void setBombermanMap(OldBombermanMap oldBombermanMap) {
        this.oldBombermanMap = oldBombermanMap;
    }

    @Override
    public String toString() {
        return "DestroyableBlock ("+getTileX()+","+getTileY()+")";
    }
}
