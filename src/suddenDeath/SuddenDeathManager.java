package suddenDeath;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import game.interfaces.IUpdateable;
import game.model.BombermanMap;

public class SuddenDeathManager implements IUpdateable {

	private static SuddenDeathManager instance = null;
	private SuddenDeathGenerator generator;
	
	private SuddenDeathManager() {}
	
	public static SuddenDeathManager getInstance() {
		if (instance == null) {
			return new SuddenDeathManager();
		}
		
		return instance;
	}
	
	public void generateSuddenDeath(BombermanMap map, int suddenDeathTime, int mapWidth, int mapHeight) {
		
		int rand = (int) (Math.random()*100 + 1);
		
		if(1 <= rand && rand <= 50) {
			this.generator = new BlockGenerator(map, suddenDeathTime, mapWidth, mapHeight);
		}	
		if(51 <= rand && rand <= 100) {
			this.generator = new SpikeGenerator(map, suddenDeathTime, mapWidth, mapHeight);
		}	

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (this.generator != null) {
			this.generator.update(container, game, delta);	
		}		
	}
}
