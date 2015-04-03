package game.model;

import org.newdawn.slick.Game;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.io.InputStream;
import java.util.*;

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
    private IDestroyable[][][] destructionMatrix;

    /**
     *  In the RenderQue all Renderable Objects that are not the TileMap are added.
     *  For example: players, bombs, items, explsoions
     *
     *  to reduce processing overhead on rendering the que gets parsed till the y-position is higher than the current line.
     *
     *  If a new Object is added to the RenderQue the whole Que gets sorted by the y-position of the containing Objects.
     *  TODO sorting on changing of player-position and explosions.
     *
     *  TODO maybe it is better to trade memory for processing time and add a static renderMatrix. ([height][width][PLAYER_QUANTITY+1])
     *      no need for sorting.
     */
    private java.util.List<RenderItem> renderQue;


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
        renderQue=new ArrayList<RenderItem>();
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
        destructionMatrix = new IDestroyable[this.getWidth()][this.getHeight()][PLAYERS_QUANTITY+1];

        for(int column=0;column<this.getWidth();column++) {
            for (int row = 0; row < this.getHeight(); row++) {
                if ((tileId=getTileId(column,row,layerBlocks))!=0){
                    if(getTileProperty(tileId,"destroyable","true").equalsIgnoreCase("true")) {
                        gameObject = new DestroyableBlock(column, row, this);
                        addToDestructionMatrix(column,row,(IDestroyable)gameObject);
                    }else{
                        gameObject = new SolidBlock(column, row);
                    }
                    addToCollisionMatrix(column,row,gameObject);
                }
            }
        }
    }

    private void initLayers(){
        layerBackground =   this.getLayerIndex("background");
        layerBlocks     =   this.getLayerIndex("blocks");
        layerForeground =   this.getLayerIndex("foreground");
    }

    public boolean removeBlock(int tileX, int tileY, GameObject object){
        boolean state=false;

        state=removeGameObject(tileX,tileY,object);

        if(getTileId(tileX,tileY,layerBlocks)!=0) {
            setTileId(tileX, tileY, layerBlocks, 0);
            removeFromCollisionMatrix(tileX,tileY);
            return state&&true;
        }else{
            return state;
        }
    }

    @Override
    protected void renderedLine(int visualY, int mapY, int layer) {
        //TODO
        int renderQueY=0,renderQueIndex=0;

        super.renderedLine(visualY, mapY, layer);
        while (renderQueY<=mapY && renderQueIndex<renderQue.size()) {
            RenderItem item = renderQue.get(renderQueIndex++);
            renderQueY=(item.getGameObject().getPosY())/this.tileHeight;
            if(renderQueY==mapY)
                item.getGameObject().draw(0,0);
        }
    }

    public boolean destroy(int tileX, int tileY){
        boolean state=false;
        for(int i=0;i<PLAYERS_QUANTITY+1;i++)
            if((tileX>0 && tileX<getWidth()) && (tileY>0 && tileY<getHeight()))
                if(destructionMatrix[tileX][tileY][i]!=null){
                    destructionMatrix[tileX][tileY][i].destroy();
                    state=state||true;
                }
        return state;
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

    public boolean addToDestructionMatrix(int tileX,int tileY, IDestroyable gameObject){
        for(int i=0;i<PLAYERS_QUANTITY+1;i++)
            if(destructionMatrix[tileX][tileY][i]==null){
                destructionMatrix[tileX][tileY][i]=gameObject;
                return true;
            }
        return false;
    }

    public boolean removeFromDestructionMatrix(int tileX,int tileY, IDestroyable gameObject){
        for(int i=0;i<PLAYERS_QUANTITY+1;i++)
            if(destructionMatrix[tileX][tileY][i]==gameObject){
                destructionMatrix[tileX][tileY][i]=null;
                return true;
            }
        return false;
    }

    public boolean addToCollisionMatrix(int tileX, int tileY, GameObject gameObject){
        if(collissionMatrix[tileX][tileY]==null) {
            collissionMatrix[tileX][tileY] = gameObject;
            return true;
        }else
            return false;
    }

    public boolean removeFromCollisionMatrix(int tileX, int tileY){
        if(collissionMatrix[tileX][tileY]==null)
            return false;
        else{
            collissionMatrix[tileX][tileY]=null;
            return true;
        }
    }

    public boolean removeFromCollisionMatrix(GameObject gameObject){
        for(int column=0;column<this.getWidth();column++) {
            for (int row = 0; row < this.getHeight(); row++) {
                if (collissionMatrix[column][row]==gameObject){
                    collissionMatrix[column][row]=null;
                    return true;
                }
            }
        }
        return false;
    }



    public void addToRenderQue(GameObject object,int zBuff){
        Debugger.log("Added to RenderQue: "+object.toString());
        renderQue.add(new RenderItem(object,zBuff));
        Collections.sort(renderQue);
    }

    public void addToRenderQue(GameObject object){
        addToRenderQue(object, renderQue.size());
    }

    public boolean removeFromRenderQue(GameObject gameObject){
        for(int i=0;i<renderQue.size();i++){
            if(renderQue.get(i).getGameObject()==gameObject){
                renderQue.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addGameObject(int tileX,int tileY, GameObject object){
        boolean state=false;
        addToRenderQue(object);
        if(object.isCollides()) {
            state=addToCollisionMatrix(tileX, tileY, object);
        }
        if (object instanceof IDestroyable){
            state=state&&addToDestructionMatrix(tileX, tileY,(IDestroyable) object);
        }
        return state;
    }

    public boolean removeGameObject(int tileX,int tileY, GameObject object){
        Debugger.log("Remove: "+object.toString());
        boolean state=false;
        if(object.isCollides()) {
            state=removeFromCollisionMatrix(tileX,tileY);
        }
        if (object instanceof IDestroyable){
            state=state&&removeFromDestructionMatrix(tileX,tileY,(IDestroyable)object);
        }
        removeFromRenderQue(object);
        return state;
    }

    public boolean addBomb(int tileX, int tileY,float timer, int range){
        if(!isCollision(tileX,tileY)){
            Bomb bomb=new Bomb(tileX,tileY,timer,range,this);
            return addGameObject(tileX,tileY,bomb);
        }else
            return false;
    }
}
