package game.options;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public abstract class OptionScreen {

	protected BasicGameState state;
	protected ArrayList<OptionLayer> layers;
	protected int width;
	protected int height;
	protected int layerIndex;
	
	public OptionScreen(BasicGameState state, int width, int height) {
		
		this.state = state;
		this.width = width;
		this.height = height;
		layers = new ArrayList<OptionLayer>();
		
	}
	
	
	protected abstract void update(GameContainer container, StateBasedGame game, int delta);
	protected abstract void input(Input input);
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		if (getLayerCount() > 0 && layers.size() > layerIndex) {
			layers.get(layerIndex).render(container, game, g);		
		}
		
	}
	
	protected int getLayerCount() {
		return layers.size();
	}
	
	protected void addOptionLayer(OptionLayer layer) {
		layers.add(layer);
	}
	
	protected OptionLayer getActualLayer() {
		return layers.get(layerIndex);
	}
	
	protected boolean setLayerIndex(int index) {
		if (layers.size() > 0 && index >= 0 && index < layers.size()) {
			if (layers.get(index) != null) {
				layerIndex = index;
				return true;
			}
		}
		
		return false;
	}
	
	public void reset() {
		for (OptionLayer layer : layers) {
			layer.resetOptionLayer();
		}
		
		layerIndex = 0;
	}
	
}
