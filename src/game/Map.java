package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import slick.extension.AppGameContainerFSCustom;

/**
 * Created by Mario on 30.03.2015.
 */
public class Map
{
    public static String    MAP_1               = "res/levels/level1.tmx";
    public static final int TILE_SIZE			= 64;	
    
    private TiledMap        tileMap             = null;
    
    private Shape player1;
    private Shape player2;

    public Map () throws SlickException {
 
    }

    public void init (String map) {
        
    	
    	
    	try {
            this.tileMap = new TiledMap (map);

            player1 = new Rectangle(tileMap.getObjectX(0, 0), tileMap.getObjectY(0, 0), tileMap.getObjectWidth(0, 0), tileMap.getObjectHeight(0, 0));
            player2 = new Rectangle(tileMap.getObjectX(0, 1), tileMap.getObjectY(0, 1), tileMap.getObjectWidth(0, 1), tileMap.getObjectHeight(0, 1));
        }
        catch (SlickException ex) {
            // TODO
        }
    }

    public void render(GameContainer gameContainer, Graphics map_graphics) {
		tileMap.render(0, 0);
	}

    
    public boolean isBlocked(int x, int y) {
        // TODO: implement collissions

        return false;
    }

    public Shape getPlayer1() {
        return player1;
    }

    public Shape getPlayer2() {
        return player2;
    }
	
}
