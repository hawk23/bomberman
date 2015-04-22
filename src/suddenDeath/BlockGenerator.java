package suddenDeath;

import game.interfaces.IUpdateable;
import game.model.BombermanMap;
import game.model.FallingBlock;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BlockGenerator implements IUpdateable {	
	
	private BombermanMap map;
	private float delta;
	private float counter;
	private int rows;
	private int cols;
	private int blockCount;
	private int blocksAdded;
	private FallingBlock[][] matrix;
	private ArrayList<FallingBlock> blocks;
	
	public BlockGenerator (BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map = map;
		this.rows = mapHeight - 2;
		this.cols = mapWidth - 2;
		this.blockCount = this.rows * this.cols;
		this.delta = (float)(suddenDeathTime - 1_000)  / blockCount;
		this.counter = 0;
		this.blocksAdded = 0;
		this.matrix = new FallingBlock[rows][cols];
		this.blocks = new ArrayList<FallingBlock>();
		generateBlocks();
	}

	private void generateBlocks() {
		
		int i;
		for (i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new FallingBlock(j + 1, i + 1);
			}
		}
		
		i = 0;
		int k = 0, l = 0;
		int m = rows;
		int n = cols;
		
		while (k < m && l < n) {
	        
			/* first row from the remaining rows */
	        for (i = l; i < n; ++i) {
	            blocks.add(matrix[k][i]);
	        }
	        k++;
	 
	        /* last column from the remaining columns */
	        for (i = k; i < m; ++i) {
	        	blocks.add(matrix[i][n-1]);
	        }
	        n--;
	 
	        /* last row from the remaining rows */
	        if ( k < m) {
	            for (i = n-1; i >= l; --i) {
	            	blocks.add(matrix[m-1][i]);
	            }
	            m--;
	        }
	 
	        /* first column from the remaining columns */
	        if (l < n) {
	            for (i = m-1; i >= k; --i) {
	            	blocks.add(matrix[i][l]);
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
				
				map.addBlock(blocks.get(blocksAdded));
				
				blocksAdded++;
			}
		}
	}	
}
