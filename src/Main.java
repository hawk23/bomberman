import org.newdawn.slick.SlickException;
import slick.extension.AppGameContainerFSCustom;
import game.BombermanGame;

/**
 * Created by Mario on 30.03.2015.
 */
public class Main {
    public static void main(String[] args) throws SlickException {
        AppGameContainerFSCustom container = new AppGameContainerFSCustom(new BombermanGame());
        container.start();
    }
}
