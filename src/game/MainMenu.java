package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class MainMenu {
	
	private int canvasWidth;
	private int CanvasHeight;
	
	// Layer 0 - MainLayer
	private String[] layer_0 = new String[3];
	private String option_0_0 = "New Game";
	private String option_0_1 = "Controls";
	private String option_0_2 = "Exit Game";
	
	
	public static enum Layer {
		MAIN_LAYER
	}
	
	public MainMenu() {
		canvasWidth = AppGameContainerFSCustom.GAME_CANVAS_WIDTH;
		CanvasHeight = AppGameContainerFSCustom.GAME_CANVAS_HEIGHT;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		renderLayer0(container, game, graphics);
	}
	
	private void renderLayer0(GameContainer container, StateBasedGame game, Graphics graphics) {
		
//		BombermanGame.OCR_FONT_OL_MED.drawString((canvasWidth - BombermanGame.OCR_FONT_OL_MED.getWidth(option_0_0)) / 2, 380, option_0_0);
//		BombermanGame.OCR_FONT_MED.drawString((canvasWidth - BombermanGame.OCR_FONT_MED.getWidth(option_0_1)) / 2, 480, option_0_1);
//		BombermanGame.OCR_FONT_MED.drawString((canvasWidth - BombermanGame.OCR_FONT_MED.getWidth(option_0_2)) / 2, 580, option_0_2);
		
//		BombermanGame.BASKERVILLE_FONT_OL_BIG.drawString((canvasWidth - BombermanGame.BASKERVILLE_FONT_OL_BIG.getWidth(option_0_0)) / 2, 380, option_0_0);
//		BombermanGame.BASKERVILLE_FONT_BIG.drawString((canvasWidth - BombermanGame.BASKERVILLE_FONT_BIG.getWidth(option_0_1)) / 2, 480, option_0_1);
//		BombermanGame.BASKERVILLE_FONT_BIG.drawString((canvasWidth - BombermanGame.BASKERVILLE_FONT_BIG.getWidth(option_0_2)) / 2, 580, option_0_2);
		
		BombermanGame.STEAMPUNK_FONT_OL_MED.drawString((canvasWidth - BombermanGame.STEAMPUNK_FONT_OL_MED.getWidth(option_0_0)) / 2, 380, option_0_0);
		BombermanGame.STEAMPUNK_FONT_MED.drawString((canvasWidth - BombermanGame.STEAMPUNK_FONT_MED.getWidth(option_0_1)) / 2, 480, option_0_1);
		BombermanGame.STEAMPUNK_FONT_MED.drawString((canvasWidth - BombermanGame.STEAMPUNK_FONT_MED.getWidth(option_0_2)) / 2, 580, option_0_2);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}
}
