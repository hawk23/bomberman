package game.state;

import org.newdawn.slick.state.BasicGameState;

/**
 * Created by Mario on 30.03.2015.
 */
public abstract class BombermanGameState extends BasicGameState
{
    public static int INTRO             = 1;
    public static int MAIN_MENU         = 2;
    public static int GAME_ROUND        = 3;

    protected int id;

    public BombermanGameState (int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return this.id;
    }
}
