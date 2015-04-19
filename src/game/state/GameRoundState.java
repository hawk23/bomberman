package game.state;

import game.PlayerStateScreen;
import game.config.GameRoundConfig;
import game.menu.Menu.Action;
import game.menu.EndMenu;
import game.menu.PauseMenu;
import game.model.BombermanMap;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;


public class GameRoundState extends BombermanGameState
{
	private static final String 	gameStartMusicPath = "res/sounds/round/startround.ogg";

    private ArrayList<PlayerStateScreen> stateScreens       = new ArrayList<PlayerStateScreen>();
	private GameRoundConfig 		gameRoundConfig			= null;
	private boolean 				paused					= false;
	private boolean 				gameRoundEnd			= false;
	private PauseMenu				menu					= null;
	private EndMenu					endMenu					= null;
	private Image 					background				= null;
	private Image					playerStatsBackground	= null;
    private Graphics 				map_graphics			= null;
    private Image 					map_buffer				= null;
    private final int 				xOffset 				= 160;
	private BombermanMap  			map                 	= null;
	private	int						startTime;
	private	int						timer;
    
	private Music 					gameStartMusic;
    
    public GameRoundState ()
    {
        super (BombermanGameState.GAME_ROUND);

		loadMusic();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException
    {
    	map_buffer 				= new Image(960, 960);
    	map_graphics 			= map_buffer.getGraphics();

    	menu = new PauseMenu();
    	menu.init();
    	endMenu = new EndMenu();
    	endMenu.init();
    	
    	background 				= new Image("res/visuals/backgrounds/menuBackground.png");
    	playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");	
    }

	@Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		if (gameRoundEnd) {
			background.draw(0, 0);
			endMenu.render(container, game, graphics);
			
		}
		else {
	    	if (!this.paused)
	    	{
	    		resetGraphics();

	    		this.map.render(container, game, this.map_graphics);
                graphics.drawImage(map_buffer, xOffset, 0);

                graphics.drawImage(playerStatsBackground, 0, 0);
                graphics.drawImage(playerStatsBackground, xOffset + map.getWidth(), 0);

                for (PlayerStateScreen screen : this.stateScreens)
                {
                    screen.render(container, game, graphics);
                }
            }
	    	else
	    	{
	    		background.draw(0, 0);
	    		menu.render(container, game, graphics);
	    	}
		}

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
        Input input = container.getInput();

        if (this.map.getNrDeadPlayer() >= this.map.getNrPlayer() - 1)
        {
        	if(startTime >= timer || input.isKeyPressed(Input.KEY_ESCAPE))
        	{
        		this.gameRoundEnd = true;
        	}
        	
        	startTime += delta;
        }
        
        if (gameRoundEnd)
        {
        	endMenu.update(container, game, delta);
        	
        	if (endMenu.getActualAction() != Action.NO_ACTION)
    		{
    			Action currentAction = endMenu.getActualAction();
    			endMenu.setActualAction(Action.NO_ACTION);
    			
    			switch (currentAction)
    			{
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
        else
        {
            if (!this.paused)
            {
            	this.map.update(container, game, delta);

				if (!gameStartMusic.playing())
				resumeMusic(gameStartMusic);

            	if (input.isKeyPressed(Input.KEY_ESCAPE))
            	{
                	this.paused = true;
                	input.clearKeyPressedRecord();
                }
        	}
        	else
        	{
        		menu.update(container, game, delta);
				pauseMusic(gameStartMusic);
        		
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
    	gameRoundEnd = false;
    	menu.reset();
    	endMenu.reset();
        startTime	= 0;
        timer		= 5000;
    	this.map = new BombermanMap(this.gameRoundConfig, container);
		playMusic (gameStartMusic);

        // create player state screens
        for (int i = 0; i < this.map.getPlayers().length; i++)
        {
            int                     posX        = (i % 2) * 1120;
            int                     posY        = (i / 2) * 480;

            PlayerStateScreen       screen      = new PlayerStateScreen(this.map.getPlayers()[i], posX, posY);
            this.stateScreens.add(screen);
        }

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

	private void loadMusic()
	{
		try
		{
			this.gameStartMusic = new Music(gameStartMusicPath);
		}

		catch (SlickException e)
		{
			//TODO
		}
	}
	
	private void playMusic (Music music)
	{
		music.play();
	}

	private void pauseMusic (Music music)
	{
		music.pause();
	}

	private void resumeMusic (Music music)
	{
		music.resume();
	}
}
