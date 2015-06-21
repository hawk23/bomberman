package game.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class IntroState extends BombermanGameState
{
	private static final String 	introSoundPath      	= "res/sounds/menu/first.wav";
	private Sound introSound;
	
	/**
	 * timer should be set to 0 and be incremented in update() by delta
	 */
	private int timer;
	/**
	 * the time in ms the IntroState should be displayed
	 */
	private int time;
	private boolean leaveIntro;
	
	private Image background;
	
    public IntroState () {
        super(BombermanGameState.INTRO);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	background = new Image("res/visuals/backgrounds/intro.png");
    	loadMusic();
    }

    private void loadMusic() {
    	try
		{
			this.introSound	= new Sound(introSoundPath);
		}
		catch (SlickException e)
		{
			//TODO
		}
		
	}

	@Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
    	background.draw((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - background.getWidth()) / 2,
    			(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - background.getHeight()) / 2); 
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
    	
//    	if (leaveIntro) {
//    		game.enterState(BombermanGameState.MAIN_MENU);
//    	}
    	if (!introSound.playing()) {
    		game.enterState(BombermanGameState.MAIN_MENU);
    	}
    }

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		timer = 0;
		time = 5_000;
		leaveIntro = false;	
		introSound.play(1.0f, 0.4f);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		container.getInput().clearKeyPressedRecord();
		
	}

}
