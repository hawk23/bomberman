package game.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Mario and Albert
 */
public abstract class BombermanGameState extends BasicGameState
{
    public static int INTRO             = 1;
    public static int MAIN_MENU         = 2;
    public static int GAME_ROUND        = 3;
    public static int GAME_END			= 4;

    protected int id;

    public BombermanGameState (int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return this.id;
    } 
    
    /**
     * is called when entering a state
     */
    @Override
    public abstract void enter(GameContainer container, StateBasedGame game) throws SlickException;
    
    /**
     * is called when leaving a state
     */
    @Override
    public abstract void leave(GameContainer container, StateBasedGame game) throws SlickException;
}
