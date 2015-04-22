package slick.extension;

import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;
import game.model.Block;
import game.model.DestroyableBlock;
import game.model.GameObject;
import game.model.SolidBlock;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class TiledMapWrapper extends TiledMap implements IUpdateable, IRenderable
{

    private int layerBackground, layerForeground, layerBlocks;
    private Block[][] blockMatrix;

	private CrackleSystem crackle;
	
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
		crackle = new CrackleSystem();
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
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		for (int i = 0; i < this.blockMatrix.length; i++)
		{
			for (int j = 0; j < blockMatrix.length; j++)
			{
				if(this.blockMatrix[i][j] instanceof DestroyableBlock)
				{
					if(((DestroyableBlock) this.blockMatrix[i][j]).isDestroyed())
					{
						removeBlock(i, j);
					}
				}
			}
		}
		crackle.update(container,game,delta);
	}
	
	public void removeBlock(int i, int j) {
		this.setTileId(i, j, this.layerBlocks, 0);
		this.blockMatrix[i][j] = null;
		crackle.addCrackle(i,j);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		super.render(0, 0);
		crackle.render(container,game,g);
	}
	
	
	public boolean isBlocked(int tileX, int tileY)
	{
		if(this.blockMatrix[tileX][tileY] == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

    public boolean isDestroyable (int tileX, int tileY)
    {
        if (this.blockMatrix[tileX][tileY] instanceof DestroyableBlock)
        {
            return true;
        }

        return false;
    }

    public boolean isSolid (int tileX, int tileY)
    {
        if (this.blockMatrix[tileX][tileY] instanceof SolidBlock)
        {
            return true;
        }

        return false;
    }
}
