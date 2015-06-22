package suddenDeath;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import game.interfaces.IDestroyable;
import game.interfaces.IUpdateable;
import game.model.BombermanMap;
import game.model.objects.GameObject;
import game.model.objects.Driller;

public class DrillerGenerator extends SuddenDeathGenerator implements IUpdateable {
	
	private BombermanMap 	map;
	private float 			delta;
	private float 			counter;
	private int 			rows;
	private int 			cols;
	private int 			drillerCount;
	private int 			drillerAdded;
	private Driller[][] 	initialMatrix;
	private Driller[][] 	drillers;
	private ArrayList<Driller> sortedDrillers;
	
	public DrillerGenerator(BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map 			= map;
		this.rows 			= mapHeight - 2;
		this.cols 			= mapWidth - 2;
		this.drillerCount 	= this.rows * this.cols;
		this.delta 			= (float)suddenDeathTime / drillerCount;
		this.counter 		= 0;
		this.drillerAdded 	= 0;
		this.initialMatrix 	= new Driller[rows][cols];
		this.drillers 		= new Driller[map.getMapHeight()][map.getMapWidth()];
		this.sortedDrillers = new ArrayList<Driller>();
		generateDrillers();
	}

	private void generateDrillers() {
		
		int i;
		for (i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix[i].length; j++) {
				initialMatrix[i][j] = new Driller(j + 1, i + 1);
			}
		}
		
		i = 0;
		int k = 0, l = 0;
		int m = rows;
		int n = cols;
		
		while (k < m && l < n) {
	        
			/* first row from the remaining rows */
	        for (i = l; i < n; ++i) {
	            sortedDrillers.add(initialMatrix[k][i]);
	        }
	        k++;
	 
	        /* last column from the remaining columns */
	        for (i = k; i < m; ++i) {
	        	sortedDrillers.add(initialMatrix[i][n-1]);
	        }
	        n--;
	 
	        /* last row from the remaining rows */
	        if ( k < m) {
	            for (i = n-1; i >= l; --i) {
	            	sortedDrillers.add(initialMatrix[m-1][i]);
	            }
	            m--;
	        }
	 
	        /* first column from the remaining columns */
	        if (l < n) {
	            for (i = m-1; i >= k; --i) {
	            	sortedDrillers.add(initialMatrix[i][l]);
	            }
	            l++;    
	        }        
	    }	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		if (drillerAdded < drillerCount) {
			
			counter += delta;
			if (counter >= this.delta) {
				counter = counter % this.delta;
				
				drillers[sortedDrillers.get(drillerAdded).getTileX()][sortedDrillers.get(drillerAdded).getTileY()] = sortedDrillers.get(drillerAdded);
				map.addGameObject(sortedDrillers.get(drillerAdded));
				
				drillerAdded++;
			}
		}
		
		for (int i = 0; i < this.drillers.length; i++)
		{
			for (int j = 0; j < this.drillers[i].length; j++)
			{
				if (this.drillers[i][j] != null) {
					this.drillers[i][j].update(container, game, delta);
					
					if (this.map.getBlocks()[i][j] != null) {
						this.map.removeBlock(i, j);
					}
					
					if (this.drillers[i][j].isDeadly()) {
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
