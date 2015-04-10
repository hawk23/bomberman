package game.state;

import game.MainMenuScreen;
import game.Map;
import game.PauseMenuScreen;
import game.config.GameRoundConfig;
import game.config.InputConfiguration;
import game.input.InputManager;
import game.model.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

/**
 * Created by Mario on 30.03.2015.
 */
public class GameRoundState extends BombermanGameState
{
	private boolean 				paused				= false;
	private PauseMenuScreen 		pauseMenu			= null;
	private Image 					background			= null;
	
	private GameRoundConfig 		gameRoundConfig		= null;
    
	private Map                     map                 = null;
    private Player                  player1             = null;

    private Graphics 				map_graphics		= null;
    private Image 					map_buffer			= null;
    
    private final int 				xOffset 			= 160;
    
    public GameRoundState () {
        super (BombermanGameState.GAME_ROUND);
        
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       	map_buffer = new Image(960, 960);
    	map_graphics = map_buffer.getGraphics();
    	pauseMenu = new PauseMenuScreen(game, AppGameContainerFSCustom.GAME_CANVAS_WIDTH, 
        		AppGameContainerFSCustom.GAME_CANVAS_HEIGHT);
    	pauseMenu.init();
    	background = new Image("res/visuals/backgrounds/menuBackground.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        
    	if (!paused) {
    		resetGraphics();
            this.map.render(container, map_graphics);
            this.player1.render(container, map_graphics);
            map_graphics.flush();
            graphics.drawImage(map_buffer, xOffset, 0);
    	}
    	else {
    		background.draw(0, 0);
    		pauseMenu.render(container, game, graphics);
    	}
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (!paused) {
        	if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            	paused = true;
            }
        	
        	this.player1.update(container, delta);   
            
    	}
    	else {
    		pauseMenu.input(input);
    		pauseMenu.update(container, game, delta);
    		
            if (pauseMenu.getAction() != MainMenuScreen.NO_ACTION) {
            	
            	int currentAction = pauseMenu.getAction();
            	pauseMenu.setAction(MainMenuScreen.NO_ACTION);
            	
            	switch(currentAction) {
            		
    		        case PauseMenuScreen.GAME_RESUME:
    		        	paused = false;
    		        	break;
    		        
    		        case PauseMenuScreen.GAME_RESTART:
    		        	break;
    		        
    		        case PauseMenuScreen.GAME_ROUND_EXIT:
    		        	game.enterState(MAIN_MENU);
    		        	break;
    	        }
            	
            }
    	}
        

        
    }
    
    private void resetGraphics() {
    	map_graphics.clear();
    	map_graphics.setBackground(Color.black);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	
    	paused = false;
    	
    	this.map = new Map();
        this.map.init(Map.MAP_1);

        // create players and define controls
        InputConfiguration          inputConfiguration1         = new InputConfiguration(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);
        InputManager       			inputManager1      			= new InputManager(container.getInput(), inputConfiguration1);

        this.player1                                            = new Player(map.getPlayer1(), map, Player.PLAYER_1, inputManager1);


    }
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    	container.getInput().clearKeyPressedRecord();
    	pauseMenu.reset();
    }

	public void setGameRoundConfig(GameRoundConfig gameRoundConfig) {
		this.gameRoundConfig = gameRoundConfig;
	}

}
