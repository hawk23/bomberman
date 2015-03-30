package game.state;

import game.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Mario on 30.03.2015.
 */
public class GameRoundState extends BombermanGameState
{
    private Map map = null;

    public GameRoundState () {
        super (BombermanGameState.GAME_ROUND);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.map = new Map();
        this.map.init(Map.MAP_1);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.map.render();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            stateBasedGame.enterState(BombermanGameState.MAIN_MENU);
        }
    }
}
