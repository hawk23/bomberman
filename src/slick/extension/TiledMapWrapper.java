package slick.extension;

import game.model.Block;
import game.model.DestroyableBlock;
import game.model.GameObject;
import game.model.IDestroyable;
import game.model.RenderItem;
import game.model.SolidBlock;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class TiledMapWrapper extends TiledMap
{
    /**
     *  The TileMaps contain 3 Layer to seperate the Map for Rendering:
     *      Background: always behind players and items
     *      Blocks: - this is the same layer as the player and therefore has to be ordered by y-axis on rendering
     *              - can be divided into SolidBlocks and DestroyableBlocks (IDestroyable)
     *      Foreground: always on top of layers and items
     */
    private int layerBackground, layerForeground, layerBlocks, layerSpawnPoints;
    
    private static final int NR_BLOCKS = 15;
    private Block[][] blocks;
    
	
	public TiledMapWrapper(String ref) throws SlickException
	{
		super(ref);
		init();
	}

	public Point getSpawnPoint(int id)
	{
		Point spawnPoint = new Point();
		
		spawnPoint.x = this.getObjectX(0, id);
		spawnPoint.y = this.getObjectY(0, id);

		return spawnPoint;
	}
	
    private void init()
    {
        initLayers();
        initBlockMatrix();
    }

    private void initLayers()
    {
        this.layerBackground 	= this.getLayerIndex("background");
        this.layerBlocks     	= this.getLayerIndex("blocks");
        this.layerForeground 	= this.getLayerIndex("foreground");
        this.layerSpawnPoints	= this.getLayerIndex("spawnpoints");
    }
    

    /**
     * Initialises the collissionMatrix and the destructionMatrix based on the loaded TileMap
     *
     */
//    private void initMatrix()
//    {
//        int tileId;
//        GameObject gameObject;
//        collissionMatrix = new GameObject[this.getWidth()][this.getHeight()];
//        destructionMatrix = new IDestroyable[this.getWidth()][this.getHeight()][PLAYERS_QUANTITY+1];
//
//        for(int column=0;column<this.getWidth();column++) {
//            for (int row = 0; row < this.getHeight(); row++) {
//                if ((tileId=getTileId(column,row,layerBlocks))!=0){
//                    if(getTileProperty(tileId,"destroyable","true").equalsIgnoreCase("true")) {
//                        gameObject = new DestroyableBlock(column, row, this);
//                        addToDestructionMatrix(column,row,(IDestroyable)gameObject);
//                    }else{
//                        gameObject = new SolidBlock(column, row);
//                    }
//                    addToCollisionMatrix(column,row,gameObject);
//                }
//            }
//        }
//    }
    
    private void initBlockMatrix()
    {
        this.blocks = new Block[NR_BLOCKS][NR_BLOCKS];
    }
}
