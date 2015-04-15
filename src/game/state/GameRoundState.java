package game.state;

import game.config.GameRoundConfig;
import game.input.InputManager;
import game.menu.PauseMenu;
import game.menu.Menu.Action;
import game.model.BombermanMap;
import game.model.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameRoundState extends BombermanGameState
{
	private GameRoundConfig 		gameRoundConfig			= null;
	private boolean 				paused					= false;
	private PauseMenu				menu					= null;
	private Image 					background				= null;
	private Image					playerStatsBackground	= null;
    private Graphics 				map_graphics			= null;
    private Image 					map_buffer				= null;
    private final int 				xOffset 				= 160;
    
    
    
	private BombermanMap            map                 	= null;
    private Player                  player1             	= null;
    private Player                  player2             	= null;


    
    public GameRoundState () {
        super (BombermanGameState.GAME_ROUND);   
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       	   	
    	map_buffer 				= new Image(960, 960);
    	map_graphics 			= map_buffer.getGraphics();

    	menu = new PauseMenu();
    	menu.init();
    	
    	background 				= new Image("res/visuals/backgrounds/menuBackground.png");
    	playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");	
    }

	@Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        
    	if (!paused) {
    		resetGraphics();
            this.map.render(0,0,0,0,15,15,true);
            this.player1.render(container, game, map_graphics);
            
            graphics.drawImage(map_buffer, xOffset, 0);
            graphics.drawImage(playerStatsBackground, 0, 0);
            graphics.drawImage(playerStatsBackground, xOffset + map.getWidth() * 64, 0);

    	}
    	else {
    		background.draw(0, 0);
    		menu.render(container, game, graphics);
    	}
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (!paused) {
        	if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            	paused = true;
            	input.clearKeyPressedRecord();
            }
        	
        	this.player1.update(container, game, delta);
            
    	}
    	else {
    		menu.update(container, game, delta);
    		
    		if (menu.getActualAction() != Action.NO_ACTION) {
    			
    			Action currentAction = menu.getActualAction();
    			menu.setActualAction(Action.NO_ACTION);
    			
    			switch (currentAction) {
    				
	    			case RESUME_GAME:
	    				menu.reset();
	    				paused = false;
	    				break;
	    			case RESTART_GAME: 
	    				restart(container, game);
	    				break;
	    			case LEAVE_GAME: 
	    				game.enterState(MAIN_MENU);
	    				break;
    			
					default: break;
    			
    			}
    		}
    	}
    }
    
    private void resetGraphics() {
    	map_graphics.setBackground(Color.black);
    	map_graphics.clear();
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	
    	paused = false;
    	menu.reset();
    	

    	this.map = new BombermanMap(this.gameRoundConfig.getMapConfig().getPath());
        // create players and define controls
        InputManager    inputManager1   = new InputManager(container.getInput(), this.gameRoundConfig.getCurrentInputConfigs().get(0));
        InputManager    inputManager2   = new InputManager(container.getInput(), this.gameRoundConfig.getCurrentInputConfigs().get(1));
        this.player1                    = new Player(this.map.getPlayer1Shape(), this.map, 0, inputManager1, this.gameRoundConfig.getCurrentPlayerConfigs().get(0));
        //this.player2                    = new Player(this.map.getPlayer2Shape(), this.map, 1, inputManager2, this.gameRoundConfig.getCurrentPlayerConfigs().get(1));
    }
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    	container.getInput().clearKeyPressedRecord();
    }
	
	private void restart(GameContainer container, StateBasedGame game) throws SlickException {
		leave(container, game);
		enter(container, game);
	}

	public void setGameRoundConfig(GameRoundConfig gameRoundConfig) {
		this.gameRoundConfig = gameRoundConfig;
	}
}
