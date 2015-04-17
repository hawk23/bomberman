package game.state;

import game.config.GameRoundConfig;
import game.menu.Menu.Action;
import game.menu.PauseMenu;
import game.model.BombermanMap;

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
    
	private BombermanMap  			map                 	= null;
    
    public GameRoundState ()
    {
        super (BombermanGameState.GAME_ROUND);   
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException
    {
    	map_buffer 				= new Image(960, 960);
    	map_graphics 			= map_buffer.getGraphics();

    	menu = new PauseMenu();
    	menu.init();
    	
    	background 				= new Image("res/visuals/backgrounds/menuBackground.png");
    	playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");	
    }

	@Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
    	if (!this.paused)
    	{
    		resetGraphics();

    		this.map.render(container, game, this.map_graphics);
    		
            graphics.drawImage(map_buffer, xOffset, 0);
            graphics.drawImage(playerStatsBackground, 0, 0);
            graphics.drawImage(playerStatsBackground, xOffset + map.getWidth(), 0);
    	}
    	else
    	{
    		background.draw(0, 0);
    		menu.render(container, game, graphics);
    	}
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
        Input input = container.getInput();

        if (!this.paused)
        {
        	this.map.update(container, game, delta);
        	
        	if (input.isKeyPressed(Input.KEY_ESCAPE))
        	{
            	this.paused = true;
            	input.clearKeyPressedRecord();
            }
    	}
    	else
    	{
    		menu.update(container, game, delta);
    		
    		if (menu.getActualAction() != Action.NO_ACTION)
    		{
    			Action currentAction = menu.getActualAction();
    			menu.setActualAction(Action.NO_ACTION);
    			
    			switch (currentAction)
    			{
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
    
    private void resetGraphics()
    {
    	map_graphics.setBackground(Color.black);
    	map_graphics.clear();
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException
    {
    	paused = false;
    	menu.reset();

    	this.map = new BombermanMap(this.gameRoundConfig, container);
    }
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException
    {
    	container.getInput().clearKeyPressedRecord();
    }
	
	private void restart(GameContainer container, StateBasedGame game) throws SlickException
	{
		leave(container, game);
		enter(container, game);
	}

	public void setGameRoundConfig(GameRoundConfig gameRoundConfig) 
	{
		this.gameRoundConfig = gameRoundConfig;
	}
}
