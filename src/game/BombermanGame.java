package game;

import game.state.GameRoundState;
import game.state.IntroState;
import game.state.MainMenuState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BombermanGame extends StateBasedGame {

	public static final String GAME_NAME = "Bomberman";

	public BombermanGame() {
		super(GAME_NAME);
	}

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	// add the states to the game - first added will be first entered!
    	
    	// initial state
    	addState(new IntroState());
    	
    	// other states
        addState(new MainMenuState());
        addState(new GameRoundState());
    }
}
