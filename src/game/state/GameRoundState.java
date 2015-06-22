package game.state;

import game.BombermanGame;
import game.PlayerStateScreen;
import game.config.GameRoundConfig;
import game.config.GameSettings;
import game.menu.Menu.Action;
import game.menu.EndMenu;
import game.menu.PauseMenu;
import game.model.BombermanMap;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

import java.util.ArrayList;

public class GameRoundState extends BombermanGameState
{
	// Sounds
	private static final String 	gameStartMusicPath 		= "res/sounds/round/startround.ogg";
	private Sound 					gameStartMusic;
	private static final String 	sirenSoundPath      	= "res/sounds/round/suddendeath.ogg";
	private Sound 					sirenSound;
	private static final String 	gameSoundPath      		= "res/sounds/round/gameSound2.wav";
	private Sound 					gameSound;

	
	private static enum RoundState {
		STARTING, PLAYING, PAUSED, ROUND_END
	}
	
	private RoundState actualState;
	
	// Timer
	private final int				STARTING_STATE_TIMER	= 3_000;
	private int						STARTING_STATE_TIME;
	private final int				SHOW_WINNER_TIME		= 500;
	private final int				END_TIMER				= 5_000;
	private int						END_TIME;
	private final int				SHOW_GO_TIMER			= 1_000;
	private int						SHOW_GO_TIME;
	private int						ROUND_TIME;
	private int						ROUND_TIME_LIMIT;
	private final int				SHOW_HURRY_TIMER		= 4_000;
	private int						SHOW_HURRY_TIME;
	private boolean					timeLimit;
	private boolean					showHurryUP;
	private boolean					timeLimitReached;
	private boolean					showCountdown;
	private boolean					setIndestructable;
	private boolean					startSuddenDeath;
	private boolean 				sirenplayed;
	private int 					suddenDeathTime;
	
	// Strings
	private final String			infoGO					= "BOMB !!!!";
	private final String			allDead					= "No Winner";
	private final String			hurryUP					= "HURRY UP !!!!";
	private String					startCounter;
	private String					winner;
	private String					countdown;
	
	// for gameRoundState
    private Image 					gameRoundStateBuffer  	= null;
    private Graphics				gameRoundStateGraphics	= null;
    private ArrayList<PlayerStateScreen> stateScreens       = new ArrayList<PlayerStateScreen>();
	 
	private GameRoundConfig 		gameRoundConfig			= null;
	private PauseMenu				menu					= null;
	private EndMenu					endMenu					= null;
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
    	
    	gameRoundStateBuffer	= new Image(160,480);
    	gameRoundStateGraphics	= gameRoundStateBuffer.getGraphics();

    	menu = new PauseMenu();
    	menu.init();
    	endMenu = new EndMenu();
    	endMenu.init();
    	
    	suddenDeathTime = GameSettings.SUDDEN_DEATH_TIME;
    	
    	loadMusic();
    	
    	background 				= new Image("res/visuals/backgrounds/menuBackground.png");
    	playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");	
    }

	@Override
    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
        switch (actualState) {
        
	        case STARTING: 	render_STATE_STARTING(container, game, graphics); break;
	        case PLAYING: 	render_STATE_PLAYING(container, game, graphics); break;
	        case PAUSED: 	render_STATE_PAUSED(container, game, graphics); break;
	        case ROUND_END: render_STATE_ROUND_END(container, game, graphics); break;
	    
	    }
        
    }

    private void render_STATE_ROUND_END(GameContainer container, StateBasedGame game, Graphics graphics) {
		background.draw(0, 0);
		endMenu.render(container, game, graphics);	
	}

	private void render_STATE_PAUSED(GameContainer container, StateBasedGame game, Graphics graphics) {
		background.draw(0, 0);
		menu.render(container, game, graphics);
	}

	private void render_STATE_PLAYING(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		resetGraphics();
		
		this.map.render(container, game, this.map_graphics);
		
        graphics.drawImage(playerStatsBackground, 0, 0);
        graphics.drawImage(playerStatsBackground, xOffset + map.getWidth(), 0);
        
        for (PlayerStateScreen screen : this.stateScreens) {
            screen.render(container, game, graphics);
        }
        
        graphics.drawImage(map_buffer, xOffset, 0);

        if ((SHOW_GO_TIME < SHOW_GO_TIMER) && actualState == RoundState.PLAYING) {
    		BombermanGame.STEAMWRECK_FONT_RED.drawString((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - BombermanGame.STEAMWRECK_FONT_RED.getWidth(infoGO)) /2, 
    				(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - BombermanGame.STEAMWRECK_FONT_RED.getHeight(infoGO)) /2 - 20, 
    				infoGO);
        } 
        
        if (END_TIME >= SHOW_WINNER_TIME) {
    		BombermanGame.STEAMWRECK_FONT_RED.drawString((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - BombermanGame.STEAMWRECK_FONT_RED.getWidth(winner)) /2, 
    				(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - BombermanGame.STEAMWRECK_FONT_RED.getHeight(winner)) /2 - 20, 
    				winner);
        }
        
        if (showHurryUP) {
    		BombermanGame.STEAMWRECK_FONT_RED.drawString((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - BombermanGame.STEAMWRECK_FONT_RED.getWidth(hurryUP)) /2, 
    				(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - BombermanGame.STEAMWRECK_FONT_RED.getHeight(hurryUP)) /2 - 20, 
    				hurryUP);
        }
        
        if (showCountdown && !showHurryUP) {
    		BombermanGame.STEAMWRECK_FONT_RED.drawString((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - BombermanGame.STEAMWRECK_FONT_RED.getWidth(countdown)) /2, 
    				(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - BombermanGame.STEAMWRECK_FONT_RED.getHeight(countdown)) /2 - 20, 
    				countdown);
        }
        
        
	}

	private void render_STATE_STARTING(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		render_STATE_PLAYING(container, game, graphics);
		
		BombermanGame.STEAMWRECK_FONT_RED.drawString((AppGameContainerFSCustom.GAME_CANVAS_WIDTH - BombermanGame.STEAMWRECK_FONT_RED.getWidth(startCounter)) /2 , 
		(AppGameContainerFSCustom.GAME_CANVAS_HEIGHT - BombermanGame.STEAMWRECK_FONT_RED.getHeight(startCounter)) /2 - 20, 
		startCounter);
	}

	@Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {       
        switch (actualState) {
        
	        case STARTING: 	update_STATE_STARTING(container, game, delta); break;
	        case PLAYING: 	update_STATE_PLAYING(container, game, delta); break;
	        case PAUSED: 	update_STATE_PAUSED(container, game, delta); break;
	        case ROUND_END: update_STATE_ROUND_END(container, game, delta); break;
        
        }               
    }
    
    private void update_STATE_ROUND_END(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	
    	endMenu.update(container, game, delta);
        	
    	if (endMenu.getActualAction() != Action.NO_ACTION) {
	    	Action currentAction = endMenu.getActualAction();
	    	endMenu.setActualAction(Action.NO_ACTION);
    			
	    	switch (currentAction) {
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

	private void update_STATE_PAUSED(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		menu.update(container, game, delta);
		
		if (menu.getActualAction() != Action.NO_ACTION) {
			Action currentAction = menu.getActualAction();
			menu.setActualAction(Action.NO_ACTION);
			
			switch (currentAction) {
			
    			case RESUME_GAME:
    				menu.reset();
    				actualState = RoundState.PLAYING;
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

	private void update_STATE_PLAYING(GameContainer container, StateBasedGame game, int delta) {
		Input input = container.getInput();
		
		ROUND_TIME += delta;
		
		if (timeLimit && ROUND_TIME >= ROUND_TIME_LIMIT) {
			timeLimitReached = true;
		}
		
		if (showCountdown) {
			countdown = "" + (((int)((ROUND_TIME_LIMIT - ROUND_TIME) / 1_000)) + 1);
		}
    	
		if (!(SHOW_GO_TIME >= SHOW_GO_TIMER)) {
			SHOW_GO_TIME += delta;
		}

		if (!setIndestructable && END_TIME >= SHOW_WINNER_TIME) {
			
			int alive = 0;
			for (int i = 0; i < this.map.getPlayers().length; i++)
				if (!this.map.getPlayers()[i].isDying()) {
					this.map.getPlayers()[i].setIndestructable();
					alive++;
				}
			
			if (alive > 1) {
				winner = "Time Limit Reached";
			}
			else if (this.map.getPlayers().length > 0 && !this.map.getPlayers()[0].isDying()) {	
				this.map.getPlayers()[0].setWinner();
				winner = "Player 1 wins!";
        	}
        	else if (this.map.getPlayers().length > 1 && !this.map.getPlayers()[1].isDying()) {
        		this.map.getPlayers()[1].setWinner();
        		winner = "Player 2 wins!";
        	}
        	else if (this.map.getPlayers().length > 2 && !this.map.getPlayers()[2].isDying()) {
        		this.map.getPlayers()[2].setWinner();
        		winner = "Player 3 wins!";
        	}
        	else if (this.map.getPlayers().length > 3 &&!this.map.getPlayers()[3].isDying()) {
        		this.map.getPlayers()[3].setWinner();
        		winner = "Player 4 wins!";
        	}
        	else {
        		winner = allDead;
        	}
			
			setIndestructable = true;
		}
		
		if ((timeLimit && (ROUND_TIME >= (ROUND_TIME_LIMIT - suddenDeathTime - SHOW_HURRY_TIMER))) && (SHOW_HURRY_TIME <= SHOW_HURRY_TIMER)) {
			showHurryUP = true;
			showCountdown = true;
			SHOW_HURRY_TIME += delta;
			if (!this.sirenplayed) {
				this.sirenSound.play();
				this.sirenplayed = true;
				gameSound.loop(1.0f, 0.6f);
			}
			
		}
		else {
			showHurryUP = false;
		}
		
		
		if (timeLimit && !startSuddenDeath && (ROUND_TIME >= (ROUND_TIME_LIMIT - suddenDeathTime))) {
			this.map.startSuddenDeath();
			startSuddenDeath = true;
		}
		
    	if (timeLimitReached || this.map.getNrDeadPlayer() >= this.map.getNrPlayer() - 1) {
        	
    		if (showHurryUP) {
    			showHurryUP = false;
    		}
    		
    		if (showCountdown) {
    			showCountdown = false;
    		}
			
        	if(END_TIME >= END_TIMER) {
        		actualState = RoundState.ROUND_END;
        		input.clearKeyPressedRecord();
            	if (gameSound.playing()) {
            		gameSound.stop();
            	}
            	
            	if (sirenSound.playing()) {
            		sirenSound.stop();
            	}
            	if (gameStartMusic.playing()) {
            		gameStartMusic.stop();
            	}
        	}
        	else {
        		END_TIME += delta;
        	}
    	}
    	
    	if (input.isKeyPressed(Input.KEY_ESCAPE)) {
        	actualState = RoundState.PAUSED;
        	input.clearKeyPressedRecord();
        }
    	
    	this.map.update(container, game, delta);
	}

	private void update_STATE_STARTING(GameContainer container, StateBasedGame game, int delta) {
		
		if (STARTING_STATE_TIME >= STARTING_STATE_TIMER) {
			container.getInput().clearKeyPressedRecord();
			actualState = RoundState.PLAYING;
		}
		else {
			startCounter = "" + (((int)((STARTING_STATE_TIMER - STARTING_STATE_TIME) / 1_000)) + 1);
			STARTING_STATE_TIME += delta;
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
    	this.map = new BombermanMap(this.gameRoundConfig, container);
    	this.stateScreens = new ArrayList<PlayerStateScreen>();
    	
    	// create player state screens
    	for (int i = 0; i < this.map.getPlayers().length; i++)
    	{
    		int                     posX        = (i % 2) * 1120;
    		int                     posY        = (i / 2) * 480;
	
    		PlayerStateScreen       screen      = new PlayerStateScreen(this.map.getPlayers()[i], posX, posY, 
    				this.gameRoundStateGraphics, this.gameRoundStateBuffer);
    		this.stateScreens.add(screen);
    	}
    	
    	menu.reset();
    	endMenu.reset();
    	
    	// reset Time
    	STARTING_STATE_TIME = 0;
    	END_TIME			= 0;
    	SHOW_GO_TIME		= 0;
    	SHOW_HURRY_TIME		= 0;
    	ROUND_TIME			= 0;
    	ROUND_TIME_LIMIT	= this.gameRoundConfig.getTimeLimit() * 60 * 1_000;
    	if (ROUND_TIME_LIMIT == 0) {
    		timeLimit = false;
    	}
    	else {
    		timeLimit = true;
    	}
    	
    	showHurryUP 		= false;
    	timeLimitReached 	= false;
    	showCountdown 		= false;
    	setIndestructable 	= false;
    	startSuddenDeath	= false;
    	sirenplayed			= false;
    	
    	startCounter 	= "";
    	winner 			= "";
    	countdown		= "";
    	
    	actualState = RoundState.STARTING;
    	gameStartMusic.play(1.0f, 1.2f);
    	 	
    }
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException
    {
    	gameSound.stop();
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
			this.gameStartMusic = new Sound(gameStartMusicPath);
			this.sirenSound		= new Sound(sirenSoundPath);
			this.gameSound		= new Sound(gameSoundPath);
		}
		catch (SlickException e)
		{
			//TODO
		}
	}
}
