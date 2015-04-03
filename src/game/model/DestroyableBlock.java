package game.model;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * Needs to know the parent BombermanMap to remove itself of the map
 *
 */
public class DestroyableBlock extends Block implements IDestroyable {

    private BombermanMap bombermanMap;

    public DestroyableBlock(int tileX, int tileY, int tileWidth, int tileHeight, BombermanMap bombermanMap) {
        super(tileX, tileY, tileWidth, tileHeight);
        setBombermanMap(bombermanMap);
    }

    public DestroyableBlock(int tileX, int tileY, BombermanMap bombermanMap) {
        super(tileX, tileY);
        setBombermanMap(bombermanMap);
    }

    @Override
    public boolean destroy() {
        return bombermanMap.removeBlock(getTileX(),getTileY());
    }

    private void setBombermanMap(BombermanMap bombermanMap) {
        this.bombermanMap = bombermanMap;
    }
}
