package game.menu;

import game.BombermanGame;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Menu
{
	protected AngelCodeFont font 			= BombermanGame.STEAMWRECK_FONT;
	protected AngelCodeFont fontOutline 	= BombermanGame.STEAMWRECK_FONT_OL;
	protected AngelCodeFont fontOutline2 	= BombermanGame.SUBWAY_FONT;
	
	protected int canvasWidth;
	protected int canvasHeight;
	
	protected Layer actualLayer;
	
	public static enum Layer
	{
		MAIN_LAYER,
		EXIT_LAYER,
		CONTROLS_LAYER,
		SETTINGS_LAYER_1,
		AVATAR_LAYER
	}
	
	protected Action actualAction;
	
	public static enum Action
	{
		EXIT_GAME,
		START_GAME_ROUND,
		NO_ACTION,
		RESUME_GAME,
		RESTART_GAME,
		LEAVE_GAME
	}
	
	public abstract void init();
	public abstract void reset();
	public abstract void update(GameContainer container, StateBasedGame game, int delta);
	public abstract void render(GameContainer container, StateBasedGame game, Graphics graphics);
	public abstract Action getActualAction();
	public abstract void setActualAction(Action actualAction);
}
