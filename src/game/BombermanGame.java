package game;

import game.config.GameRoundConfig;
import game.config.InputConfiguration;
import game.config.MapConfig;
import game.config.PlayerConfig;
import game.state.GameRoundState;
import game.state.IntroState;
import game.state.MainMenuState;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class BombermanGame extends StateBasedGame {

	public static final String GAME_NAME = "Bomberman";
	
	public static AngelCodeFont STEAMPUNK_FONT;
	public static AngelCodeFont STEAMPUNK_FONT_OL;
	public static AngelCodeFont STEAMPUNK_FONT_MED;
	public static AngelCodeFont STEAMPUNK_FONT_OL_MED;
	
	public static AngelCodeFont STEAMPUNK2_FONT;
	public static AngelCodeFont STEAMPUNK2_FONT_OL;
	
	public static AngelCodeFont JAMES_FONT;
	public static AngelCodeFont JAMES_FONT_OL;
	
	public static AngelCodeFont STEAMWRECK_FONT;
	public static AngelCodeFont STEAMWRECK_FONT_OL;
	
	public static AngelCodeFont OCR_FONT_BIG;
	public static AngelCodeFont OCR_FONT_OL_BIG;
	public static AngelCodeFont OCR_FONT_MED;
	public static AngelCodeFont OCR_FONT_OL_MED;
	
	public static AngelCodeFont BASKERVILLE_FONT_BIG;
	public static AngelCodeFont BASKERVILLE_FONT_OL_BIG;
	
	private GameRoundConfig					defaultGameRoundConfig;
    private ArrayList<PlayerConfig>         playerConfigs           = new ArrayList<PlayerConfig>();
    private ArrayList<MapConfig>            mapConfigs              = new ArrayList<MapConfig>();
    private ArrayList<InputConfiguration>   inputConfigurations     = new ArrayList<InputConfiguration>();

	public BombermanGame() throws SlickException
    {
		super(GAME_NAME);
	}

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	// add the states to the game - first added will be first entered!
    	
    	// initial state
    	addState(new IntroState());
    	
    	// other states
        addState(new MainMenuState());
        addState(new GameRoundState());
        
        doInitialization();
    }
    
    private void doInitialization() throws SlickException {
    	createConfig();
    	loadFonts();
    	loadDefaultGameRoundConfig();
    }
    
	private void loadFonts() throws SlickException  {
		STEAMPUNK_FONT = new AngelCodeFont("res/fonts/steampunk.fnt", new Image("res/fonts/steampunk.png"));
		STEAMPUNK_FONT_OL = new AngelCodeFont("res/fonts/steampunkOutline.fnt", new Image("res/fonts/steampunkOutline.png"));
		
		STEAMPUNK_FONT_MED = new AngelCodeFont("res/fonts/steam_80.fnt", new Image("res/fonts/steam_80.png"));
		STEAMPUNK_FONT_OL_MED = new AngelCodeFont("res/fonts/steam_ol_80.fnt", new Image("res/fonts/steam_ol_80.png"));
		
		STEAMPUNK2_FONT = new AngelCodeFont("res/fonts/steampunk2.fnt", new Image("res/fonts/steampunk2.png"));
		STEAMPUNK2_FONT_OL = new AngelCodeFont("res/fonts/steampunk2_ol.fnt", new Image("res/fonts/steampunk2_ol.png"));
		
		JAMES_FONT = new AngelCodeFont("res/fonts/james.fnt", new Image("res/fonts/james.png"));
		JAMES_FONT_OL = new AngelCodeFont("res/fonts/james_ol.fnt", new Image("res/fonts/james_ol.png"));
		
		STEAMWRECK_FONT = new AngelCodeFont("res/fonts/steamwreck.fnt", new Image("res/fonts/steamwreck.png"));
		STEAMWRECK_FONT_OL = new AngelCodeFont("res/fonts/steamwreck_ol.fnt", new Image("res/fonts/steamwreck_ol.png"));
		
		OCR_FONT_BIG = new AngelCodeFont("res/fonts/OCR_80.fnt", new Image("res/fonts/OCR_80.png"));
		OCR_FONT_OL_BIG = new AngelCodeFont("res/fonts/OCR_ol_80.fnt", new Image("res/fonts/OCR_ol_80.png"));
		
		OCR_FONT_MED = new AngelCodeFont("res/fonts/OCR_60.fnt", new Image("res/fonts/OCR_60.png"));
		OCR_FONT_OL_MED = new AngelCodeFont("res/fonts/OCR_ol_60.fnt", new Image("res/fonts/OCR_ol_60.png"));
		
		BASKERVILLE_FONT_BIG = new AngelCodeFont("res/fonts/Baskerville_80.fnt", new Image("res/fonts/Baskerville_80.png"));
		BASKERVILLE_FONT_OL_BIG = new AngelCodeFont("res/fonts/Baskerville_ol_80.fnt", new Image("res/fonts/Baskerville_ol_80.png"));
    }

    private void createConfig () throws SlickException
    {
        this.createPlayerConfig();
        this.createMapConfig();
        this.createInputConfig();
    }

    private void createPlayerConfig () throws SlickException
    {
        PlayerConfig player1 = new PlayerConfig();
        player1.setId(0);
        player1.setPath("res/visuals/players/0/spritesheet.png");
        try {
        	player1.setImage(new Image("res/visuals/players/0/avatar.png"));   
        } catch (RuntimeException e) {
        	player1.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        player1.setName("Fred");
        player1.setInitialBombLimit(2);
        player1.setInitialSpeed(2);
        player1.setInitialBombTimer(2000);
        player1.setInitialBombRange(1);

        PlayerConfig player2 = new PlayerConfig();
        player2.setId(1);
        player2.setPath("res/visuals/players/1/spritesheet.png");
        try {
        	player2.setImage(new Image("res/visuals/players/1/avatar.png"));   
        } catch (RuntimeException e) {
        	player2.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        player2.setName("Hodenkobold");
        player2.setInitialBombLimit(1);
        player2.setInitialSpeed(2.4f);
        player2.setInitialBombTimer(2000);
        player2.setInitialBombRange(1);

        this.playerConfigs.add(player1);
        this.playerConfigs.add(player2);
    }

    private void createMapConfig () throws SlickException
    {
        MapConfig mapConfig1 = new MapConfig();
        mapConfig1.setId(0);
        mapConfig1.setPath("res/levels/0/map.tmx");
        
        try
        {
        	mapConfig1.setImage(new Image("res/levels/0/thumbnail.png"));
        }
        catch (RuntimeException e)
        {
        	mapConfig1.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        
        mapConfig1.setName("Map 1");

        MapConfig mapConfig2 = new MapConfig();
        mapConfig2.setId(1);
        mapConfig2.setPath("res/levels/1/map.tmx");
        
        try
        {
        	mapConfig2.setImage(new Image("res/levels/1/thumbnail.png"));
        }
        catch (RuntimeException e)
        {
        	mapConfig2.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        
        mapConfig2.setName("Map 2");
        
        MapConfig mapConfig3 = new MapConfig();
        mapConfig3.setId(2);
        mapConfig3.setPath("res/levels/2/map.tmx");
        
        try
        {
        	mapConfig3.setImage(new Image("res/levels/2/thumbnail.png"));
        }
        catch (RuntimeException e)
        {
        	mapConfig3.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        
        mapConfig3.setName("Map 3");
        
        MapConfig mapConfig4 = new MapConfig();
        mapConfig4.setId(3);
        mapConfig4.setPath("res/levels/3/map.tmx");
        
        try
        {
        	mapConfig4.setImage(new Image("res/levels/3/thumbnail.png"));
        }
        catch (RuntimeException e)
        {
        	mapConfig4.setImage(new Image("res/visuals/default_thumbnail.png"));
        }
        
        mapConfig4.setName("Map 4");
        
        this.mapConfigs.add(mapConfig1);
        this.mapConfigs.add(mapConfig2);
        this.mapConfigs.add(mapConfig3);
        this.mapConfigs.add(mapConfig4);
    }

    private void createInputConfig () throws SlickException
    {
        InputConfiguration inputConfiguration1 = new InputConfiguration(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_LCONTROL);
        InputConfiguration inputConfiguration2 = new InputConfiguration(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);

        this.inputConfigurations.add(inputConfiguration1);
        this.inputConfigurations.add(inputConfiguration2);
    }
    
    private void loadDefaultGameRoundConfig() {
    	
    	defaultGameRoundConfig = new GameRoundConfig();
    	
    	MapConfig defaultMap 								= getMapConfigs().get(0);

        PlayerConfig defaultPlayer1 						= getPlayerConfigs().get(0);
    	PlayerConfig defaultPlayer2 						= getPlayerConfigs().get(1);

        ArrayList<PlayerConfig> defaultPlayerConfigs 		= new ArrayList<PlayerConfig>();
    	defaultPlayerConfigs.add(defaultPlayer1);
    	defaultPlayerConfigs.add(defaultPlayer2);

        InputConfiguration defaultInput1 					= getInputConfigurations().get(0);
    	InputConfiguration defaultInput2 					= getInputConfigurations().get(1);

    	ArrayList<InputConfiguration> defaultInputConfigs 	= new ArrayList<InputConfiguration>();
    	defaultInputConfigs.add(defaultInput1);
    	defaultInputConfigs.add(defaultInput2);
    	
    	defaultGameRoundConfig.setMapConfig(defaultMap);
    	defaultGameRoundConfig.setCurrentPlayerConfigs(defaultPlayerConfigs);
    	defaultGameRoundConfig.setCurrentInputConfigs(defaultInputConfigs);
    	defaultGameRoundConfig.setTimeLimit(5);
		
	}

	public ArrayList<PlayerConfig> getPlayerConfigs() {
		return playerConfigs;
	}

	public ArrayList<MapConfig> getMapConfigs() {
		return mapConfigs;
	}

	public ArrayList<InputConfiguration> getInputConfigurations() {
		return inputConfigurations;
	}

	public GameRoundConfig getDefaultGameRoundConfig() {
		return defaultGameRoundConfig;
	}

	public void exitGame() {
		getContainer().exit();
	}
}
