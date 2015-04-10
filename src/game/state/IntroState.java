package game.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Mario on 30.03.2015.
 */
public class IntroState extends BombermanGameState
{
	/**
	 * timer should be set to 0 and be incremented in update() by delta
	 */
	private int timer;
	/**
	 * the time in ms the IntroState should be displayed
	 */
	private int time;
	private boolean leaveIntro;
	
    public IntroState () {
        super(BombermanGameState.INTRO);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
    	
    	graphics.setColor(Color.black);
    	graphics.drawString("intro", 50, 50);
      	
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	
    	Input input = container.getInput();
    	if (input.isKeyPressed(Input.KEY_ESCAPE)) {
    		leaveIntro = true;
    	}
    	
    	// increment timer by delta
    	timer += delta;
    	if (timer >= time) {
    		leaveIntro = true;
    	}
    	
    	if (leaveIntro) {
    		game.enterState(BombermanGameState.MAIN_MENU);
    	}
    }

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		timer = 0;
		time = 5_000;
		leaveIntro = false;
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		container.getInput().clearKeyPressedRecord();
	}

}
