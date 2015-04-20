package game.model;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import game.interfaces.IUpdateable;

public class SpikeGenerator implements IUpdateable {
	
	private BombermanMap map;
	private float delta;
	private float counter;
	private int rows;
	private int cols;
	private int spikeCount;
	private int spikesAdded;
	private Spike[][] matrix;
	private ArrayList<Spike> spikes;
	
	public SpikeGenerator(BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map = map;
		this.rows = mapHeight - 2;
		this.cols = mapWidth - 2;
		this.spikeCount = this.rows * this.cols;
		this.delta = (float)suddenDeathTime / spikeCount;
		this.counter = 0;
		this.spikesAdded = 0;
		this.matrix = new Spike[rows][cols];
		this.spikes = new ArrayList<Spike>();
		generateSpikes();
	}

	private void generateSpikes() {
		
		int i;
		for (i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new Spike(j + 1, i + 1);
			}
		}
		
		i = 0;
		int k = 0, l = 0;
		int m = rows;
		int n = cols;
		
		while (k < m && l < n) {
	        
			/* first row from the remaining rows */
	        for (i = l; i < n; ++i) {
	            spikes.add(matrix[k][i]);
	        }
	        k++;
	 
	        /* last column from the remaining columns */
	        for (i = k; i < m; ++i) {
	        	spikes.add(matrix[i][n-1]);
	        }
	        n--;
	 
	        /* last row from the remaining rows */
	        if ( k < m) {
	            for (i = n-1; i >= l; --i) {
	            	spikes.add(matrix[m-1][i]);
	            }
	            m--;
	        }
	 
	        /* first column from the remaining columns */
	        if (l < n) {
	            for (i = m-1; i >= k; --i) {
	            	spikes.add(matrix[i][l]);
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
				
				map.addSpike(spikes.get(spikesAdded));
				
				spikesAdded++;
			}
		}
	}	
}
