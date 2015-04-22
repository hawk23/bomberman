import org.newdawn.slick.SlickException;
import slick.extension.AppGameContainerFSCustom;
import game.BombermanGame;

public class Main
{
    public static void main(String[] args) throws SlickException
    {
    	BombermanGame game = new BombermanGame();
        AppGameContainerFSCustom container = new AppGameContainerFSCustom(game);
        container.start();
    }
}
