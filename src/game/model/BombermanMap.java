package game.model;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.io.InputStream;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * BombermanMap wraps the TileMap-Class and contains the logic to:
 *      -detect collisions on Tiles
 *      -Render the TileMap so GameObjects are integrated in 2.5D
 *      -manages destroy
 *
 */
public class BombermanMap extends TiledMap{

    private static final int PLAYERS_QUANTITY=2;
    /**
     *  The TileMaps contain 3 Layer to seperate the Map for Rendering:
     *      Background: always behind players and items
     *      Blocks: - this is the same layer as the player and therefore has to be ordered by y-axis on rendering
     *              - can be divided into SolidBlocks and DestroyableBlocks (IDestroyable)
     *      Foreground: always on top of layers and items
     */
    private int layerBackground,layerForeground,layerBlocks;

    /**
     * The collissionMatrix contains all collidable Objects, like Blocks and Bombs
     * There can always only be 1 collidable Object at a position at a time.
     */
    private GameObject[][] collissionMatrix;

    /**
     *  The destructionMatrix contains all destroyable objects, like DestroyableBlocks, Players and Bombs.
     *  There can always only be PLAYER_QUANTITY+1 at a position at a time. 2 Players and a Bomb for example.
     */
    private GameObject[][][] destructionMatrix;

    public BombermanMap(String ref) throws SlickException {
        super(ref);
        init();
    }

    public BombermanMap(String ref, boolean loadTileSets) throws SlickException {
        super(ref, loadTileSets);
        init();
    }

    public BombermanMap(String ref, String tileSetsLocation) throws SlickException {
        super(ref, tileSetsLocation);
        init();
    }

    public BombermanMap(InputStream in) throws SlickException {
        super(in);
        init();
    }

    public BombermanMap(InputStream in, String tileSetsLocation) throws SlickException {
        super(in, tileSetsLocation);
        init();
    }

    private void init(){
        initLayers();
        initMatrixes();
    }

    /**
     * Initialises the collissionMatrix and the destructionMatrix based on the loaded TileMap
     *
     */
    private void initMatrixes(){
        int tileId;
        GameObject gameObject;
        collissionMatrix = new GameObject[this.getWidth()][this.getHeight()];
        destructionMatrix = new GameObject[this.getWidth()][this.getHeight()][PLAYERS_QUANTITY];

        for(int column=0;column<this.getWidth();column++) {
            for (int row = 0; row < this.getHeight(); row++) {
                if ((tileId=getTileId(column,row,layerBlocks))!=0){
                    if(getTileProperty(tileId,"destroyable","true").equalsIgnoreCase("true")) {
                        gameObject = new DestroyableBlock(column, row, this);
                        destructionMatrix[column][row][0] = gameObject;
                    }else{
                        gameObject = new SolidBlock(column, row);
                    }
                    collissionMatrix[column][row] = gameObject;
                }
            }
        }
    }

    private void initLayers(){
        layerBackground =   this.getLayerIndex("background");
        layerBlocks     =   this.getLayerIndex("blocks");
        layerForeground =   this.getLayerIndex("foreground");
    }

    public boolean removeBlock(int tileX, int tileY){
        if(getTileId(tileX,tileY,layerBlocks)!=0) {
            setTileId(tileX, tileY, layerBlocks, 0);
            collissionMatrix[tileX][tileY]=null;
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void renderedLine(int visualY, int mapY, int layer) {
        //TODO add logic to render items/players in front of Blocks
        super.renderedLine(visualY, mapY, layer);
    }

    public boolean isCollision(Point tilePosition){
        return isCollision(tilePosition.x,tilePosition.y);
    }

    public boolean isCollision(int tileX,int tileY){
        if(collissionMatrix[tileX][tileY]!=null)
            return true;
        else
            return false;
    }

    public Point pixelsToTile(int x, int y){
        Point point = new Point(x/getTileHeight(),y/getTileHeight());
        if(point.x>getWidth()) point.x=getWidth()-1;
        if(point.x<0)point.x=0;
        if(point.y>getHeight()) point.y=getHeight()-1;
        if(point.y<0)point.y=0;
        return point;
    }
}
