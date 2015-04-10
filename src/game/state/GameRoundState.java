package game.state;

import game.Map;
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

/**
 * Created by Mario on 30.03.2015.
 */
public class GameRoundState extends BombermanGameState
{
    private Map                         map                 = null;
    private Player                      player1             = null;

    
    private Graphics map_graphics;
    private Image map_buffer;
    private int xOffset = 160;
    
    public GameRoundState () {
        super (BombermanGameState.GAME_ROUND);
        
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       	map_buffer = new Image(960, 960);
    	map_graphics = map_buffer.getGraphics();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        
    	resetGraphics();
    	
        this.map.render(container, map_graphics);
        this.player1.render(container, map_graphics);

        map_graphics.flush();
        
        graphics.drawImage(map_buffer, xOffset, 0);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        this.player1.update(container, delta);
        // this.player2.update(gameContainer, delta);

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
        	game.enterState(BombermanGameState.MAIN_MENU);
        }
    }
    
    private void resetGraphics() {
    	map_graphics.clear();
    	map_graphics.setBackground(Color.black);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	
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
    }

}
