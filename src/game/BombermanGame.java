package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import custom_slick.AppGameContainer_FS_Custom;

public class BombermanGame extends StateBasedGame {

	public static final String GAME_NAME = "Test";
	
	public BombermanGame() {
		super(GAME_NAME);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer_FS_Custom container = new AppGameContainer_FS_Custom(new BombermanGame());
		container.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
	}

}
