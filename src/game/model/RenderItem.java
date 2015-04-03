package game.model;

import org.newdawn.slick.Game;

/**
 * Created by Roland Schreier on 03.04.2015.
 *
 * HelperClass used in the RenderQue to achieve consistend y-sorting
 * The renderQue contains all GameObjects (except the tileMap and their blocks) for rendering
 *
 * by rendering the GameMap line by line a fake zBuffer can be achieved so that objects with a bigger than normal height are rendered over objects behind them
 */
public class RenderItem implements Comparable<RenderItem>{


    private GameObject gameObject;
    private int zBuffer;

    public RenderItem(GameObject gameObject, int zBuffer) {
        this.gameObject = gameObject;
        this.zBuffer = zBuffer;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public int getzBuffer() {
        return zBuffer;
    }

    public void setzBuffer(int zBuffer) {
        this.zBuffer = zBuffer;
    }

    @Override
    public int compareTo(RenderItem item) {
        if(this.getGameObject().getPosY() == item.getGameObject().getPosY())
            return this.getzBuffer()-item.getzBuffer();
        else
            return this.getGameObject().getPosY() - item.getGameObject().getPosY();
    }
}
