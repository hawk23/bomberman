package game.state;

import game.MainMenuScreen;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;


/**
 * Created by Mario on 30.03.2015.
 */
public class MainMenuState extends BombermanGameState
{
	MainMenuScreen mainMenu = null;

    public MainMenuState () {
        super(BombermanGameState.MAIN_MENU);    
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	mainMenu = new MainMenuScreen(this, AppGameContainerFSCustom.GAME_CANVAS_WIDTH, 
        		AppGameContainerFSCustom.GAME_CANVAS_HEIGHT);
    	mainMenu.init();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
    	mainMenu.render(container, game, graphics); 
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        mainMenu.input(input);
        mainMenu.update(container, game, delta);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    	mainMenu.reset();
    	container.getInput().clearKeyPressedRecord();
    }
}
