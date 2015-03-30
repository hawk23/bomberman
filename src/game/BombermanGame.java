package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class BombermanGame extends StateBasedGame {

	public static final String GAME_NAME = "Test";
	
	public BombermanGame() {
		super(GAME_NAME);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
	}
}
