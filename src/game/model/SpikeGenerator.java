package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import game.interfaces.IUpdateable;

public class SpikeGenerator implements IUpdateable {
	
	private BombermanMap map;
	private float delta;
	private float counter;
	private final int lines = 13;
	private final int rows	= 13;
	private int spikesToGenerate;
	private int x, y;
	
	public SpikeGenerator(BombermanMap map, int suddenDeathTime) {
		this.map = map;
		this.spikesToGenerate = this.lines * this.rows;
		this.delta = (float)suddenDeathTime / spikesToGenerate;
		this.counter = 0;
		this.x = 1;
		this.y = 1;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		counter += delta;
		if (counter >= this.delta) {
			counter = counter % this.delta;
			generateSpike();
		}
	}

	private void generateSpike() {
			
//		Spike spike = new Spike(this.x, this.y);
//		this.map.addSpike(spike);
		
	}
	
	
}
