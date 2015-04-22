package game.model;

import java.awt.*;

public class FlamePoint extends Point {

    private FlameDirection direction;
    private int distanceFromCenter;

    public FlamePoint(int tileX, int tileY, FlameDirection direction, int distanceFromCenter) {
        super(tileX, tileY);
        this.direction = direction;
        this.distanceFromCenter=distanceFromCenter;
    }

    public FlameDirection getDirection() {
        return direction;
    }

    public void setDirection(FlameDirection direction) {
        this.direction = direction;
    }

    public int getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setDistanceFromCenter(int distanceFromCenter) {
        this.distanceFromCenter = distanceFromCenter;
    }
}
