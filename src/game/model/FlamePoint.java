package game.model;

import java.awt.*;

public class FlamePoint extends Point {

    private FlameDirection direction;

    public FlamePoint(int tileX, int tileY, FlameDirection direction) {
        super(tileX, tileY);
        this.direction = direction;
    }

    public FlameDirection getDirection() {
        return direction;
    }

    public void setDirection(FlameDirection direction) {
        this.direction = direction;
    }
}
