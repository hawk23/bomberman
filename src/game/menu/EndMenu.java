package game.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class EndMenu extends Menu
{
	// MainLayer
	private int		mainLayerIndex;
	private int 	mainLayerSize	= 2;
	private String 	mainLayer_0		= "Restart";
	private String 	mainLayer_1		= "Main Menu";
	
	public EndMenu()
	{
		this.canvasWidth 		= AppGameContainerFSCustom.GAME_CANVAS_WIDTH;
		this.CanvasHeight 		= AppGameContainerFSCustom.GAME_CANVAS_HEIGHT;
	}
	
	@Override
	public void init() {
		reset();
	}
	
	@Override
	public void reset() 
	{
		actualAction	= Action.NO_ACTION;
		actualLayer		= Layer.MAIN_LAYER;
		resetMainLayer();
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

			default: break;
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
					actualAction = Action.RESTART_GAME;
					break;
				case 1: 
					actualAction = Action.LEAVE_GAME;
					break;

			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
	{
		switch (actualLayer)
		{
			case MAIN_LAYER:
				renderMainLayer(container, game, graphics);
				break;
				
			default: break;
		}
	}
	
	
	private void renderMainLayer(GameContainer container, StateBasedGame game, Graphics graphics)
	{
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
