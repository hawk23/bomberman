package game;

import game.config.MapConfig;
import game.config.PlayerConfig;
import game.state.GameRoundState;
import game.state.IntroState;
import game.state.MainMenuState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class BombermanGame extends StateBasedGame {

	public static final String GAME_NAME = "Bomberman";

    private ArrayList<PlayerConfig> playerConfigs   = new ArrayList<PlayerConfig>();
    private ArrayList<MapConfig>    mapConfigs      = new ArrayList<MapConfig>();

	public BombermanGame() throws SlickException
    {
		super(GAME_NAME);

        this.createConfig();
	}

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	// add the states to the game - first added will be first entered!
    	
    	// initial state
    	addState(new IntroState());
    	
    	// other states
        addState(new MainMenuState());
        addState(new GameRoundState());
    }

    private void createConfig () throws SlickException
    {
        this.createPlayerConfig();
        this.createMapConfig();
    }

    private void createPlayerConfig () throws SlickException
    {
        PlayerConfig player1 = new PlayerConfig();
        player1.setId(1);
        player1.setPath("res/visuals/players/0/spritesheet.png");
        player1.setImage("res/visuals/players/0/avatar.png");
        player1.setName("Hans");
        player1.setInitialBombLimit(1);
        player1.setInitialSpeed(100);

        PlayerConfig player2 = new PlayerConfig();
        player2.setId(2);
        player2.setPath("res/visuals/players/1/spritesheet.png");
        player2.setImage("res/visuals/players/1/avatar.png");
        player2.setName("Hubert");
        player2.setInitialBombLimit(1);
        player2.setInitialSpeed(100);

        this.playerConfigs.add(player1);
        this.playerConfigs.add(player2);
    }

    private void createMapConfig () throws SlickException
    {
        MapConfig mapConfig1 = new MapConfig();
        mapConfig1.setId(0);
        mapConfig1.setPath("res/levels/0/map.tmx");
        mapConfig1.setImage("res/levels/0/thumbnail.png");
        mapConfig1.setName("Map 1");

        MapConfig mapConfig2 = new MapConfig();
        mapConfig2.setId(1);
        mapConfig2.setPath("res/levels/1/map.tmx");
        mapConfig2.setImage("res/levels/1/thumbnail.png");
        mapConfig2.setName("Map 2");

        this.mapConfigs.add(mapConfig1);
        this.mapConfigs.add(mapConfig2);
    }

	public ArrayList<PlayerConfig> getPlayerConfigs() {
		return playerConfigs;
	}

	public ArrayList<MapConfig> getMapConfigs() {
		return mapConfigs;
	}
}
