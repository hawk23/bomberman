package prototype;

import game.debug.Debugger;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import slick.extension.ExplosionSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Created by Roland Schreier on 31.03.2015.
*/
public class BombTest extends BasicGame {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final String GAME_NAME = "BombTester";


    private ExplosionSystem explosionSystem;

    public BombTest(String title) {
        super(title);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BombTest(GAME_NAME));
            appgc.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BombTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        explosionSystem.render();
    }


    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        explosionSystem.update(delta);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        explosionSystem = new ExplosionSystem();
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    }

    @Override
    public void mousePressed(int button, int posX, int posY){
        Debugger.log("Added Explosion ("+posX+","+posY+")");
        explosionSystem.addExplosion(posX,posY);
    }
}
