package prototype;

import game.model.Bomb;
import game.model.BombermanMap;
import game.model.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Roland Schreier on 31.03.2015.
 */
public class BombTest extends BasicGame {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static final String GAME_NAME = "BombTester";
    public static final String MAP_PATH = "res/levels/0/map.tmx";

    public static final int UPDATES_PER_SECOND = 40;
    public static final int UPDATE_RATE = 1_000 / UPDATES_PER_SECOND;
    public static final int TARGET_FPS = 500;

    private BombermanMap map;
    private int BLOCK_SIZE=64;

    private List<Renderable> renderQue;
    private Selector selector;

    private String infoString="";

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
        map.render(0,0,0,0,20,20,true);
        for(Renderable renderable : renderQue){
            renderable.draw(0,0);
        }
        graphics.drawString(infoString, 10, 25);
    }


    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        map.update(gameContainer,null,i);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        map = new BombermanMap(MAP_PATH);
        BLOCK_SIZE=map.getTileHeight();
        selector = new Selector(BLOCK_SIZE);

        renderQue=new ArrayList<Renderable>();
        renderQue.add(selector);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        Point point=map.pixelsToTile(newx,newy);

        selector.setPosition(newx, newy);
        infoString="("+newx+", "+newy+")=("+point.x+","+point.y+")";
    }

    @Override
    public void mousePressed(int button, int x, int y){
        Point point=map.pixelsToTile(x,y);
        try {
            map.addBomb(point.x, point.y, new Player(null, map, 0, null, null));
        }catch(SlickException e){
        }
        //map.destroy(point.x,point.y);

    }
}
