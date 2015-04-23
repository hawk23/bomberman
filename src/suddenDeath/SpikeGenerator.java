package suddenDeath;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import game.interfaces.IDestroyable;
import game.interfaces.IUpdateable;
import game.model.BombermanMap;
import game.model.objects.GameObject;
import game.model.objects.Spike;

public class SpikeGenerator extends SuddenDeathGenerator implements IUpdateable {
	
	private BombermanMap 	map;
	private float 			delta;
	private float 			counter;
	private int 			rows;
	private int 			cols;
	private int 			spikeCount;
	private int 			spikesAdded;
	private Spike[][] 		initialMatrix;
	private Spike[][] 		spikes;
	private ArrayList<Spike> sortedSpikes;
	
	public SpikeGenerator(BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map 			= map;
		this.rows 			= mapHeight - 2;
		this.cols 			= mapWidth - 2;
		this.spikeCount 	= this.rows * this.cols;
		this.delta 			= (float)suddenDeathTime / spikeCount;
		this.counter 		= 0;
		this.spikesAdded 	= 0;
		this.initialMatrix 	= new Spike[rows][cols];
		this.spikes 		= new Spike[map.getMapHeight()][map.getMapWidth()];
		this.sortedSpikes 	= new ArrayList<Spike>();
		generateSpikes();
	}

	private void generateSpikes() {
		
		int i;
		for (i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix[i].length; j++) {
				initialMatrix[i][j] = new Spike(j + 1, i + 1);
			}
		}
		
		i = 0;
		int k = 0, l = 0;
		int m = rows;
		int n = cols;
		
		while (k < m && l < n) {
	        
			/* first row from the remaining rows */
	        for (i = l; i < n; ++i) {
	            sortedSpikes.add(initialMatrix[k][i]);
	        }
	        k++;
	 
	        /* last column from the remaining columns */
	        for (i = k; i < m; ++i) {
	        	sortedSpikes.add(initialMatrix[i][n-1]);
	        }
	        n--;
	 
	        /* last row from the remaining rows */
	        if ( k < m) {
	            for (i = n-1; i >= l; --i) {
	            	sortedSpikes.add(initialMatrix[m-1][i]);
	            }
	            m--;
	        }
	 
	        /* first column from the remaining columns */
	        if (l < n) {
	            for (i = m-1; i >= k; --i) {
	            	sortedSpikes.add(initialMatrix[i][l]);
	            }
	            l++;    
	        }        
	    }	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		if (spikesAdded < spikeCount) {
			
			counter += delta;
			if (counter >= this.delta) {
				counter = counter % this.delta;
				
				spikes[sortedSpikes.get(spikesAdded).getTileX()][sortedSpikes.get(spikesAdded).getTileY()] = sortedSpikes.get(spikesAdded);
				map.addGameObject(sortedSpikes.get(spikesAdded));
				
				spikesAdded++;
			}
		}
		
		for (int i = 0; i < this.spikes.length; i++)
		{
			for (int j = 0; j < this.spikes[i].length; j++)
			{
				if (this.spikes[i][j] != null) {
					this.spikes[i][j].update(container, game, delta);
					
					if (this.map.getBlocks()[i][j] != null) {
						this.map.removeBlock(i, j);
					}
					
					if (this.spikes[i][j].isDeadly()) {
						for (GameObject go : this.map.getObjects())
                        {
                            if (go instanceof IDestroyable && go.getTileX() == i && go.getTileY() == j)
                            {
                                ((IDestroyable) go).destroy();
                            }
                        }
					}
				}
			}
		}
	}	
}
