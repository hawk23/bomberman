package game.state;

import game.Map;
import game.model.InputConfiguration;
import game.model.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import slick.extension.AppGameContainerFSCustom;
import slick.extension.MovementManager_Arrow;

/**
 * Created by Mario on 30.03.2015.
 */
public class GameRoundState extends BombermanGameState
{
    private Map                         map                 = null;
    private Player                      player1             = null;
    private Player                      player2             = null;

    public GameRoundState () {
        super (BombermanGameState.GAME_ROUND);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.map = new Map();
        this.map.init(Map.MAP_1);

        // create players and define controls
        InputConfiguration          inputConfiguration1         = new InputConfiguration(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);
        MovementManager_Arrow       movementManager_arrow1      = new MovementManager_Arrow (gameContainer.getInput(), inputConfiguration1);

        this.player1                                            = new Player(map.getPlayer1(), map, (AppGameContainerFSCustom) gameContainer, stateBasedGame, Player.PLAYER_1, movementManager_arrow1);

        /*
        TODO: create player 2
        */
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.map.render();
        this.player1.render(gameContainer, graphics);
        // this.player2.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        this.player1.update(gameContainer, delta);
        // this.player2.update(gameContainer, delta);

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            stateBasedGame.enterState(BombermanGameState.MAIN_MENU);
        }
    }
}
