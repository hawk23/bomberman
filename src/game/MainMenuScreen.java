package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import game.config.MapConfig;
import game.options.Option;
import game.options.OptionLayer;
import game.options.OptionScreen;

public class MainMenuScreen extends OptionScreen {
	
	public static final int NO_ACTION = -1;
	public static final int GAME_EXIT = 0;
	public static final int GAME_START_PVP = 1;
	public static final int GAME_START_SP = 2;
	
	private int action = NO_ACTION;
	
	private MapConfig selectedMapConfig = null;
	
	public MainMenuScreen(StateBasedGame game, int width, int height) {
		super(game, width, height);
	}

	public void init() throws SlickException {
		
		// Layer 0: options- NEW GAME/CONTROLS/EXIT GAME
		OptionLayer layer_0 = new OptionLayer(this);
		
		Option option_0_0 = new Option("res/visuals/option_pics/option_newGame.png", "res/visuals/option_pics/option_newGame_selected.png");
		option_0_0.setCoordinates((width - option_0_0.getWidth()) / 2, 500);
		layer_0.addOption(option_0_0);
		
		Option option_0_1 = new Option("res/visuals/option_pics/option_controls.png", "res/visuals/option_pics/option_controls_selected.png");
		option_0_1.setCoordinates((width - option_0_1.getWidth()) / 2, 600);
		layer_0.addOption(option_0_1);
		
		Option option_0_2 = new Option("res/visuals/option_pics/option_exitGame.png", "res/visuals/option_pics/option_exitGame_selected.png");
		option_0_2.setCoordinates((width - option_0_2.getWidth()) / 2, 700);
		layer_0.addOption(option_0_2);
		
		layers.add(layer_0);
		layer_0.resetOptionLayer();
		
		// Layer 1: heading - Exit Game, options - YES/NO
		OptionLayer layer_1 = new OptionLayer(this);
		
		Image heading_1 = new Image("res/visuals/option_pics/heading_exitGame.png");
		layer_1.setHeading(heading_1, (width - heading_1.getWidth()) / 2, 500);
		
		Option option_1_0 = new Option("res/visuals/option_pics/option_yes.png", "res/visuals/option_pics/option_yes_selected.png");
		option_1_0.setCoordinates(((width - option_1_0.getWidth()) / 2) - 100, 600);
		layer_1.addOption(option_1_0);
		
		Option option_1_1 = new Option("res/visuals/option_pics/option_no.png", "res/visuals/option_pics/option_no_selected.png");
		option_1_1.setCoordinates(((width - option_1_1.getWidth()) / 2) + 100, 600);
		layer_1.addOption(option_1_1);

		layers.add(layer_1);
		layer_1.resetOptionLayer();
		
		// Layer 2: heading - Controls, Option - BACK
		
		OptionLayer layer_2 = new OptionLayer(this);
		
//		Image heading_2 = new Image("res/visuals/option_pics/heading_controls.png");
//		layer_2.setHeading(heading_2, (width - heading_2.getWidth()) / 2, 30);
		
		Option option_2_0 = new Option("res/visuals/option_pics/option_back.png", "res/visuals/option_pics/option_back_selected.png");
		option_2_0.setCoordinates((width - option_2_0.getWidth()) / 2, 700);
		layer_2.addOption(option_2_0);
		
		layers.add(layer_2);
		layer_2.resetOptionLayer();
		
		// Layer 3: options - Singleplayer / pvp / back
		OptionLayer layer_3 = new OptionLayer(this);
		
		Option option_3_0 = new Option("res/visuals/option_pics/option_singleplayer.png", "res/visuals/option_pics/option_singleplayer_selected.png");
		option_3_0.setCoordinates((width - option_3_0.getWidth()) / 2, 500);
		layer_3.addOption(option_3_0);
		
		Option option_3_1 = new Option("res/visuals/option_pics/option_versus.png", "res/visuals/option_pics/option_versus_selected.png");
		option_3_1.setCoordinates((width - option_3_1.getWidth()) / 2, 600);
		layer_3.addOption(option_3_1);
		
		Option option_3_2 = new Option("res/visuals/option_pics/option_back.png", "res/visuals/option_pics/option_back_selected.png");
		option_3_2.setCoordinates((width - option_3_2.getWidth()) / 2, 700);
		layer_3.addOption(option_3_2);
		
		layers.add(layer_3);
		layer_3.resetOptionLayer();
		
		// Layer 4: options - Map Selection
		OptionLayer layer_4 = new OptionLayer(this);
		
		Image heading_4 = new Image("res/visuals/option_pics/heading_mapselection.png");
		layer_4.setHeading(heading_4, (width - heading_4.getWidth()) / 2, 300);
		
		Option option_4_0 = new Option("res/visuals/option_pics/option_map1.png", "res/visuals/option_pics/option_map1_selected.png");
		option_4_0.setCoordinates(200, 500);
		layer_4.addOption(option_4_0);
		
		Option option_4_1 = new Option("res/visuals/option_pics/option_map2.png", "res/visuals/option_pics/option_map2_selected.png");
		option_4_1.setCoordinates(200, 600);
		layer_4.addOption(option_4_1);

		
		layers.add(layer_4);
		layer_4.resetOptionLayer();
		
		
		// start Layer = Layer 0
		setLayerIndex(0);
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) {
		
	}

	@Override
	public void input(Input input) {
		
		// VK_ESCAPE, VK_ENTER, VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			
			switch(layerIndex) {
				
				case 0: getActualLayer().lastOption(); break;
				
				case 1: break;
				
				case 2: break;
				
				case 3: getActualLayer().lastOption(); break;
				
				case 4: getActualLayer().lastOption(); break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_DOWN)) {
				
			switch(layerIndex) {
			
				case 0: getActualLayer().nextOption(); break;
				
				case 1: break;
				
				case 2: break;
				
				case 3: getActualLayer().nextOption(); break;
				
				case 4: getActualLayer().nextOption(); break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			
			switch(layerIndex) {
			
				case 0: break;
				
				case 1: getActualLayer().lastOption(); break;
				
				case 2: break;
				
				case 3: break;
				
				case 4: break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			
			switch(layerIndex) {
			
				case 0: break;
				
				case 1: getActualLayer().nextOption(); break;
				
				case 2: break;
				
				case 3: break;
				
				case 4: break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			
			switch(layerIndex) {
			
				// Layer 0
				case 0: {
					
					switch(getActualLayer().getOptionIndex()) {
					
						// new Game
						case 0:
							layers.get(3).resetOptionLayer();
							setLayerIndex(3);
							break;
						
						// controls
						case 1: 
							layers.get(2).resetOptionLayer();
							setLayerIndex(2);
							break;
						
						// exit Game
						case 2: 
							layers.get(1).resetOptionLayer();
							setLayerIndex(1);
							break;
					}
					break;
				}
				
				// Layer 1
				case 1: {
					
					switch(getActualLayer().getOptionIndex()) {
					
						// Yes
						case 0:
							action = GAME_EXIT;
							break;
						
						// No
						case 1: 
							//layers.get(0).resetOptionLayer();
							setLayerIndex(0);
							break;
						
					}
					break;
				}
				
				// Layer 2
				case 2: {
					
					switch(getActualLayer().getOptionIndex()) {
					
						// back
						case 0: 
							//layers.get(0).resetOptionLayer();
							setLayerIndex(0);
							break;
						
					}
					break;
				}
				
				// Layer 3
				case 3: {
					
					switch(getActualLayer().getOptionIndex()) {
					
						// singleplayer
						case 0: 
							break;
						
						// pvp
						case 1: 
							setLayerIndex(4);
							break;
						
						// back
						case 2:
							//layers.get(0).resetOptionLayer();
							setLayerIndex(0);
							break;
					}
					break;
				}
				
				// Layer 4
				case 4: {
				
					switch(getActualLayer().getOptionIndex()) {
					
						// map 0
						case 0: 
							selectedMapConfig = ((BombermanGame)game).getMapConfigs().get(0);
							action = GAME_START_PVP;
							break;
						
						// map 1
						case 1: 
							selectedMapConfig = ((BombermanGame)game).getMapConfigs().get(1);
							action = GAME_START_PVP;
							break;
						

					}
					
					break;
				}
				
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			
			switch(layerIndex) {
			
				case 0: 
					layers.get(1).resetOptionLayer();
					setLayerIndex(1);
					break;
				
				case 1: 
					setLayerIndex(0);
					break;
				
				case 2: 
					setLayerIndex(0);
					break;
				
				case 3: 
					setLayerIndex(0);
					break;
					
				case 4: 
					setLayerIndex(3);
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

	public MapConfig getSelectedMapConfig() {
		return selectedMapConfig;
	}

	public void setSelectedMapConfig(MapConfig selectedMapConfig) {
		this.selectedMapConfig = selectedMapConfig;
	}
	
	@Override
	public void reset() {
		selectedMapConfig = null;
		super.reset();
	}

}
