package game.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class PauseMenu extends Menu
{
	// MainLayer
	private int mainLayerIndex;
	private int mainLayerSize 		= 3;
	private String mainLayer_0 		= "Resume";
	private String mainLayer_1 		= "Restart";
	private String mainLayer_2 		= "End Round";
	
	// ExitLayer
	private int exitLayerIndex;
	private int exitLayerSize 		= 2;
	private String exitLayerHeading = "End Round?";
	private String exitLayer_0 		= "Yes";
	private String exitLayer_1 		= "No";
	
	public PauseMenu() {
		this.canvasWidth 			= AppGameContainerFSCustom.GAME_CANVAS_WIDTH;
		this.canvasHeight 			= AppGameContainerFSCustom.GAME_CANVAS_HEIGHT;
	}
	
	@Override
	public void init() {
		reset();
	}
	
	@Override
	public void reset() {
		actualAction 	= Action.NO_ACTION;
		actualLayer 	= Layer.MAIN_LAYER;
		resetMainLayer();
		resetExitLayer();
	}
	
	private void resetExitLayer() {
		exitLayerIndex = 0;
	}

	private void resetMainLayer() {
		mainLayerIndex = 0;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		checkInput(container.getInput());
	}
	
	private void checkInput(Input input) {
		
		switch (actualLayer) {
		
			case MAIN_LAYER:
				mainLayer_checkInput(input);
				break;
			case EXIT_LAYER:
				exitLayer_checkInput(input);
				break;
			
			default: break;
		}
		
	}

	private void exitLayer_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
			if (exitLayerIndex - 1 >= 0) {
				exitLayerIndex--;
			}
		}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
			if (exitLayerIndex + 1 < exitLayerSize) {
				exitLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (exitLayerIndex) {
				
				case 0: 
					actualAction = Action.LEAVE_GAME;
					break;
				case 1: 
					actualLayer = Layer.MAIN_LAYER;
					resetExitLayer();
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			resetExitLayer();
		}
	}

	private void mainLayer_checkInput(Input input) {
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			if (mainLayerIndex - 1 >= 0) {
				mainLayerIndex--;
			}
		}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			if (mainLayerIndex + 1 < mainLayerSize) {
				mainLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (mainLayerIndex) {
				
				case 0: 
					actualAction = Action.RESUME_GAME;
					break;
				case 1: 
					actualAction = Action.RESTART_GAME;
					break;
				case 2: 
					actualLayer = Layer.EXIT_LAYER;
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualAction = Action.RESUME_GAME;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		switch (actualLayer) {
		
			case MAIN_LAYER:
				renderMainLayer(container, game, graphics);
				break;
			case EXIT_LAYER:
				renderExitLayer(container, game, graphics);
				break;
				
			default: break;
		}
	}
	
	private void renderExitLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		font.drawString((canvasWidth - font.getWidth(exitLayerHeading)) / 2, 450, exitLayerHeading);
		
		float scale = 0.7f;
		graphics.scale(scale, scale);
		
		if (exitLayerIndex == 0) {
			fontOutline.drawString((canvasWidth / 2 - fontOutline.getWidth(exitLayer_0) - 15) /scale, 570 /scale, exitLayer_0);
		}
		else {
			font.drawString((canvasWidth / 2 - font.getWidth(exitLayer_0) - 15) /scale, 570 /scale, exitLayer_0);
		}
		if (exitLayerIndex == 1) {
			fontOutline.drawString((canvasWidth / 2 + fontOutline.getWidth(exitLayer_1) - 15) /scale, 570 /scale, exitLayer_1);
		}
		else {
			font.drawString((canvasWidth / 2 + font.getWidth(exitLayer_1) - 15) /scale, 570 /scale, exitLayer_1);
		}
		
		graphics.resetTransform();	
	}

	private void renderMainLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		if (mainLayerIndex == 0) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_0)) / 2, 380, mainLayer_0);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_0)) / 2, 380, mainLayer_0);
		}
		if (mainLayerIndex == 1) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_1)) / 2, 480, mainLayer_1);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_1)) / 2, 480, mainLayer_1);
		}
		if (mainLayerIndex == 2) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_2)) / 2, 580, mainLayer_2);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_2)) / 2, 580, mainLayer_2);
		}
	}

	@Override
	public Action getActualAction() {
		return this.actualAction;
	}
	
	@Override
	public void setActualAction(Action actualAction) {
		this.actualAction = actualAction;
	}
}
