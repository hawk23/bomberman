package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Mario on 30.03.2015.
 */
public class Map
{
    public static String    MAP_1               = "res/levels/level1.tmx";

    private TiledMap        tileMap             = null;

    public Map () {

    }

    public void init (String map) {
        try {
            this.tileMap = new TiledMap (map);
        }
        catch (SlickException ex) {
            // TODO
        }
    }

    public void render () {
        /*
        int background      = this.tileMap.getLayerIndex("solids");
        int explodables     = this.tileMap.getLayerIndex("explodable");

        this.tileMap.render(0,0, background);
        this.tileMap.render(0,0, explodables);
        */

        this.tileMap.render(0,0);
    }
}
