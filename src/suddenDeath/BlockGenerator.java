package suddenDeath;

import game.interfaces.IDestroyable;
import game.interfaces.IUpdateable;
import game.model.BombermanMap;
import game.model.objects.FallingBlock;
import game.model.objects.GameObject;
import game.model.objects.SolidBlock;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BlockGenerator extends SuddenDeathGenerator implements IUpdateable {	
	
	private BombermanMap 			map;
	private float 					delta;
	private float 					counter;
	private int 					rows;
	private int 					cols;
	private int 					blockCount;
	private int 					blocksAdded;
	private FallingBlock[][] 		initialMatrix;
	private FallingBlock[][]		fallingBlocks;
	private ArrayList<FallingBlock> sortedBlocks;

	public BlockGenerator (BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map 			= map;
		this.rows 			= mapHeight - 2;
		this.cols 			= mapWidth - 2;
		this.blockCount 	= this.rows * this.cols;
		this.delta 			= (float)(suddenDeathTime - 1_000)  / blockCount;
		this.counter 		= 0;
		this.blocksAdded 	= 0;
		this.initialMatrix 	= new FallingBlock[rows][cols];
		this.fallingBlocks 	= new FallingBlock[map.getMapHeight()][map.getMapWidth()];
		this.sortedBlocks 	= new ArrayList<FallingBlock>();
		generateBlocks();
	}

	private void generateBlocks() {
		
		int i;
		for (i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix[i].length; j++) {
				initialMatrix[i][j] = new FallingBlock(j + 1, i + 1);
			}
		}
		
		i = 0;
		int k = 0, l = 0;
		int m = rows;
		int n = cols;
		
		while (k < m && l < n) {
	        
			/* first row from the remaining rows */
	        for (i = l; i < n; ++i) {
	            sortedBlocks.add(initialMatrix[k][i]);
	        }
	        k++;
	 
	        /* last column from the remaining columns */
	        for (i = k; i < m; ++i) {
	        	sortedBlocks.add(initialMatrix[i][n-1]);
	        }
	        n--;
	 
	        /* last row from the remaining rows */
	        if ( k < m) {
	            for (i = n-1; i >= l; --i) {
	            	sortedBlocks.add(initialMatrix[m-1][i]);
	            }
	            m--;
	        }
	 
	        /* first column from the remaining columns */
	        if (l < n) {
	            for (i = m-1; i >= k; --i) {
	            	sortedBlocks.add(initialMatrix[i][l]);
	            }
	            l++;    
	        }        
	    }	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		if (blocksAdded < blockCount) {
			
			counter += delta;
			if (counter >= this.delta) {
				counter = counter % this.delta;
				
				fallingBlocks[sortedBlocks.get(blocksAdded).getTileX()][sortedBlocks.get(blocksAdded).getTileY()] = sortedBlocks.get(blocksAdded);
				map.addGameObject(sortedBlocks.get(blocksAdded));
				
				blocksAdded++;
			}
		}
		
		for (int i = 0; i < this.fallingBlocks.length; i++) {
			
			for (int j = 0; j < this.fallingBlocks[i].length; j++) {
					
				if (this.fallingBlocks[i][j] != null) {
						
					this.fallingBlocks[i][j].update(container, game, delta);
					
					if (this.fallingBlocks[i][j].isDeadly()) {
						
						if (!this.map.isSolidBlock(i, j)){
							this.map.getBlocks()[i][j] = new SolidBlock(i, j);
						}
						
						for (GameObject go : this.map.getObjects()) {

                                if (go instanceof IDestroyable && go.getTileX() == i && go.getTileY() == j) {
                                    ((IDestroyable) go).destroy();
                                }
                        }
					}
				}	
			}
		}
	}	
}
