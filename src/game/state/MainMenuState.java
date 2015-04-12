package game.state;

import game.BombermanGame;
import game.MainMenuScreen;
import game.config.GameRoundConfig;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;


/**
 * Created by Mario on 30.03.2015.
 */
public class MainMenuState extends BombermanGameState
{
		
	MainMenuScreen mainMenu 		= null;
	Image background				= null;
	

    public MainMenuState () {
        super(BombermanGameState.MAIN_MENU);    
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	mainMenu = new MainMenuScreen(game, AppGameContainerFSCustom.GAME_CANVAS_WIDTH, 
        		AppGameContainerFSCustom.GAME_CANVAS_HEIGHT);
    	mainMenu.init();
    	background = new Image("res/visuals/backgrounds/menuBackground.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
    	background.draw(0, 0);
    	mainMenu.render(container, game, graphics); 
    	
    	((BombermanGame)game).getFont().drawString(300, 100, "abc test abc");
    	((BombermanGame)game).getFontOutline().drawString(300, 200, "< map 1 >");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        mainMenu.input(input);
        mainMenu.update(container, game, delta);
        
        if (mainMenu.getAction() != MainMenuScreen.NO_ACTION) {
        	
        	int currentAction = mainMenu.getAction();
        	mainMenu.setAction(MainMenuScreen.NO_ACTION);
        	
        	switch(currentAction) {
        		
		        case MainMenuScreen.GAME_EXIT: 
		        	container.exit();
		        	break;
		        
		        case MainMenuScreen.GAME_START_PVP: 
		        	((GameRoundState)game.getState(GAME_ROUND)).setGameRoundConfig(createConfig(game));
		        	game.enterState(GAME_ROUND);
		        	break;
	        }
        	
        }
        
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    	mainMenu.reset();
    	container.getInput().clearKeyPressedRecord();
    }
    
    private GameRoundConfig createConfig(StateBasedGame game) {
    	
    	GameRoundConfig config = new GameRoundConfig();
    	config.setCurrentPlayerConfigs(((BombermanGame)game).getPlayerConfigs());
    	config.setCurrentInputConfigs(((BombermanGame)game).getInputConfigurations());
    	config.setMapConfig(mainMenu.getSelectedMapConfig());
    	config.setTimeLimit(1_000 * 60 * 5);
    	return config;
    }
}
