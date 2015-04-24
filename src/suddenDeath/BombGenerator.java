package suddenDeath;

import java.util.ArrayList;

import game.model.BombermanMap;
import game.model.objects.Bomb;
import game.model.objects.FallingBomb;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

public class BombGenerator extends SuddenDeathGenerator {

	private BombermanMap 			map;
	private int 					secTimer;
	private int 					bombTimer;
	private int 					suddenDeathTime;
	private int 					rows;
	private int 					cols;
	private ArrayList<Point>       	points;
	private ArrayList<FallingBomb>  bombs;
	
	public BombGenerator(BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		this.map = map;
		this.rows = mapHeight - 2;
		this.cols = mapWidth - 2;
		this.secTimer = 0;
		this.bombTimer = 1_000;
		this.suddenDeathTime = suddenDeathTime;
		this.points = new ArrayList<Point>();
		this.bombs  = new ArrayList<FallingBomb>();
		generatePoints();	
	}
	
	private void generatePoints() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				points.add(new Point(j + 1, i + 1));
			}
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		suddenDeathTime -= delta;
		if (suddenDeathTime <= 10_000) {
			bombTimer = 100;
		}
			
		secTimer += delta;
		if (secTimer >= bombTimer) {
			secTimer %= bombTimer;
			
			if (suddenDeathTime >= 0) {
				boolean bombAdded = false;
				while (!bombAdded) {
					int rand = (int) (Math.random() * points.size());
					if (!this.map.isBlocked((int)points.get(rand).getX(), (int)points.get(rand).getY())) {
						FallingBomb bomb = new FallingBomb((int)points.get(rand).getX(), (int)points.get(rand).getY());
						bombs.add(bomb);
						this.map.addGameObject(bomb);
						bombAdded = true;
					}	
				}
			}
		} 	
		
		for (FallingBomb bomb : bombs) {
			bomb.update(container, game, delta);
			if (bomb.isDropped()) {
				if (this.map.getBombs()[bomb.getTileX()][bomb.getTileY()] == null) {
					this.map.addBomb(new Bomb(bomb.getTileX(), bomb.getTileY(), 1, 1_500));
				}
			}
		}
		
		for (int i = bombs.size() - 1; i >= 0; i--) {
			if (bombs.get(i).isDropped()) {
				this.map.getObjects().remove(bombs.get(i));
				bombs.remove(i);
			}
		}
			
	}

}
