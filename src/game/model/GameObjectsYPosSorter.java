package game.model;

import game.config.GameSettings;

import java.util.Comparator;

/**
 * Created by Mario on 18.04.2015.
 */
public class GameObjectsYPosSorter implements Comparator<GameObject>
{
    @Override
    public int compare(GameObject o1, GameObject o2)
    {
        int o1yPos  = (o1.getTileY() * GameSettings.TILE_HEIGHT) + GameSettings.TILE_HEIGHT / 2;
        int o2yPos  = (o2.getTileY() * GameSettings.TILE_HEIGHT) + GameSettings.TILE_HEIGHT / 2;

        if (o1 instanceof Player) {
            o1yPos = (int) ((Player) o1).getDrawPosY() + o1.getImage().getHeight() - GameSettings.TILE_HEIGHT / 2;
        }
        if (o2 instanceof Player) {
            o2yPos = (int) ((Player) o2).getDrawPosY() + o2.getImage().getHeight() - GameSettings.TILE_HEIGHT / 2;
        }

        if (o1yPos < o2yPos) {
            return -1;
        }
        if (o1yPos > o2yPos) {
            return 1;
        }
        if (o1yPos == o2yPos) {
            return 0;
        }

        return 0;
    }
}
