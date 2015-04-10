package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import game.options.Option;
import game.options.OptionLayer;
import game.options.OptionScreen;

public class PauseMenuScreen extends OptionScreen {

	public static final int NO_ACTION = -1;
	public static final int GAME_ROUND_EXIT = 0;
	public static final int GAME_RESTART = 1;
	public static final int GAME_RESUME = 2;
	
	private int action = NO_ACTION;
	
	public PauseMenuScreen(StateBasedGame game, int width, int height) {
		super(game, width, height);
	}
	
	public void init() throws SlickException {
		
		// Layer 0: 
		OptionLayer layer_0 = new OptionLayer(this);
		
		Image heading_0 = new Image("res/visuals/option_pics/heading_pause.png");
		layer_0.setHeading(heading_0, (width - heading_0.getWidth()) / 2, 300);
		
		Option option_0_0 = new Option("res/visuals/option_pics/option_resume.png", "res/visuals/option_pics/option_resume_selected.png");
		option_0_0.setCoordinates((width - option_0_0.getWidth()) / 2, 500);
		layer_0.addOption(option_0_0);
		
		Option option_0_1 = new Option("res/visuals/option_pics/option_restart.png", "res/visuals/option_pics/option_restart_selected.png");
		option_0_1.setCoordinates((width - option_0_1.getWidth()) / 2, 600);
		layer_0.addOption(option_0_1);

		Option option_0_2 = new Option("res/visuals/option_pics/option_backToMenu.png", "res/visuals/option_pics/option_backToMenu_selected.png");
		option_0_2.setCoordinates((width - option_0_2.getWidth()) / 2, 700);
		layer_0.addOption(option_0_2);
		
		layers.add(layer_0);
		layer_0.resetOptionLayer();
		
		// start Layer = Layer 0
		setLayerIndex(0);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
	}	

	@Override
	public void input(Input input) {
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			
			switch(layerIndex) {
				
				case 0: getActualLayer().lastOption(); break;	

			}
		}
		
		if (input.isKeyPressed(Input.KEY_DOWN)) {
				
			switch(layerIndex) {
			
				case 0: getActualLayer().nextOption(); break;

			}
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			
			switch(layerIndex) {
			
				case 0: break;
	
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			
			switch(layerIndex) {
			
				case 0: break;

			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			
			switch(layerIndex) {
			
				// Layer 0
				case 0: {
					
					switch(getActualLayer().getOptionIndex()) {
					
						case 0:
							action = GAME_RESUME;
							break;
						
						case 1: 
							action = GAME_RESTART;
							break;
						
						case 2: 
							action = GAME_ROUND_EXIT;
							break;
					}
					break;
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			
			switch(layerIndex) {
			
				case 0: 
					action = GAME_RESUME;
					break;	
			}
		}	
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
	
}
