package slick.extension;

import game.model.Block;
import game.model.DestroyableBlock;
import game.model.GameObject;
import game.model.IRenderable;
import game.model.IUpdateable;
import game.model.SolidBlock;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class TiledMapWrapper extends TiledMap implements IUpdateable, IRenderable
{
    /**
     *  The TileMaps contain 3 Layer to seperate the Map for Rendering:
     *      Background: always behind players and items
     *      Blocks: - this is the same layer as the player and therefore has to be ordered by y-axis on rendering
     *              - can be divided into SolidBlocks and DestroyableBlocks (IDestroyable)
     *      Foreground: always on top of layers and items
     */
    private int layerBackground, layerForeground, layerBlocks;
    
    private Block[][] blockMatrix;
	
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
	   
    public Block[][] getBlockMatrix()
    {
    	return this.blockMatrix;
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
    }
    
    private void initBlockMatrix()
    {
        this.blockMatrix		= new Block[this.getWidth()][this.getHeight()];
        
        boolean		destroyable = false;
        int			tileId		= 0;
        GameObject	gameObject;
        
        for (int i = 0; i < this.blockMatrix.length; i++)
        {
        	for (int j = 0; j < this.blockMatrix[i].length; j++)
        	{
        		tileId = getTileId(i, j, this.layerBlocks);

        		if(tileId != 0)
        		{
        			destroyable = getTileProperty(tileId, "destroyable", "false").equalsIgnoreCase("true");
        			
        			if(destroyable)
        			{
                        gameObject = new DestroyableBlock(i, j);      				
        			}
        			else
        			{
                        gameObject = new SolidBlock(i, j);      				
        			}
        			
        			this.blockMatrix[i][j] = (Block) gameObject;
        		}
        	}
		}
    }

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
	{
		for (int i = 0; i < this.blockMatrix.length; i++)
		{
			for (int j = 0; j < blockMatrix.length; j++)
			{
				if(this.blockMatrix[i][j] instanceof DestroyableBlock)
				{
					if(((DestroyableBlock) this.blockMatrix[i][j]).getDestroy())
					{
						this.setTileId(i, j, this.layerBlocks, 0);
						this.blockMatrix[i][j] = null;
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g)
	{
		super.render(0, 0);
	}
}
