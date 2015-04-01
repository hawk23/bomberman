package prototype;

import game.model.Bomb;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
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
    public static final String MAP_PATH = "res/levels/level1.tmx";

    public static final int UPDATES_PER_SECOND = 40;
    public static final int UPDATE_RATE = 1_000 / UPDATES_PER_SECOND;
    public static final int TARGET_FPS = 500;

    private TiledMap tileMap;
    private int tileLayerSolid,tileLayerExplodable;
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
        tileMap.render(0, 0);
        for(Renderable renderable : renderQue){
            renderable.draw(0,0);
        }
        graphics.drawString(infoString, 10, 25);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        tileMap = new TiledMap(MAP_PATH);
        tileLayerSolid = tileMap.getLayerIndex("solid");
        tileLayerExplodable = tileMap.getLayerIndex("explodable");
        BLOCK_SIZE=tileMap.getTileHeight();
        selector = new Selector(BLOCK_SIZE);

        renderQue=new ArrayList<Renderable>();
        renderQue.add(selector);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        Point point=pixelsToTile(newx,newy);

        selector.setPosition(newx, newy);
        infoString="("+newx+", "+newy+")=Tile("+point.x+","+point.y+")";
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        Point point=pixelsToTile(x, y);

        if(tileMap.getTileId(point.x, point.y, tileLayerSolid)==0)
            if(tileMap.getTileId(point.x,point.y,tileLayerExplodable)==0)
            {
                 Bomb bomb = new Bomb(point.x*BLOCK_SIZE,point.y*BLOCK_SIZE);
                 renderQue.add(bomb);
            }
    }

    public Point pixelsToTile(int x, int y){
        Point point = new Point(Math.round((x)/BLOCK_SIZE),
                Math.round((y)/BLOCK_SIZE));
        if(point.x>tileMap.getWidth())
            point.x=tileMap.getWidth()-1;
        if(point.y>tileMap.getWidth())
            point.y=tileMap.getWidth()-1;
        return point;
    }
}
