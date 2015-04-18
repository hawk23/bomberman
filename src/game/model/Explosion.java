package game.model;

import game.config.GameSettings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import slick.extension.TiledMapWrapper;

import java.awt.*;
import java.util.ArrayList;

public class Explosion extends GameObject implements IUpdateable, IRenderable
{
	private int                 range           = 1;
	private int                 timer; // in mili secs
    private int                 time            = 0;
    private boolean             finished        = false;
	private TiledMapWrapper     wrapper;
    private ArrayList<Point>    flamePositions  = null;

	public Explosion(int posX, int posY, int range, TiledMapWrapper wrapper)
	{
        super(posX, posY);

		this.range      = range;
		this.timer      = 500;
        this.wrapper    = wrapper;

        this.flamePositions = this.calculateFlamePosition();
	}

    private ArrayList<Point> calculateFlamePosition ()
    {
        ArrayList<Point> explodedTiles = new ArrayList<Point>();

        // add center position of explosion
        explodedTiles.add(new Point(this.getTileX(), this.getTileY()));

        boolean blockedLeft     = false;
        boolean blockedTop      = false;
        boolean blockedRight    = false;
        boolean blockedBottom   = false;

        for (int i = 1; i <= this.range; i++)
        {
            // add four rays.
            Point rayLeft       = new Point(this.getTileX() - i, this.getTileY());
            Point rayTop        = new Point(this.getTileX(), this.getTileY() - i);
            Point rayRight      = new Point(this.getTileX() + i, this.getTileY());
            Point rayBottom     = new Point(this.getTileX(), this.getTileY() + i);

            blockedLeft         = this.handleFlamePosition(blockedLeft,   rayLeft,    explodedTiles);
            blockedTop          = this.handleFlamePosition(blockedTop,    rayTop,     explodedTiles);
            blockedRight        = this.handleFlamePosition(blockedRight,  rayRight,   explodedTiles);
            blockedBottom       = this.handleFlamePosition(blockedBottom, rayBottom,  explodedTiles);
        }

        return explodedTiles;
    }

    /**
     * returns an booelean indication wheter the direction is blocked for further flames or not.
     */
    private boolean handleFlamePosition (boolean blocked, Point p, ArrayList<Point> explodedTiles)
    {
        if (!blocked)
        {
            // check if point should be added
            if (!this.wrapper.isSolid(p.x, p.y))
            {
                explodedTiles.add(p);
            }

            // check if flame ray should be blocked in this direction.
            if (this.wrapper.isSolid(p.x, p.y) || this.wrapper.isDestroyable(p.x, p.y))
            {
                blocked = true;
            }
        }

        return blocked;
    }

	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        for (Point p : this.flamePositions)
        {
            g.drawRect(p.x * GameSettings.TILE_WIDTH, p.y * GameSettings.TILE_HEIGHT, GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
        }
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
    {
        if (!finished)
        {
            time += delta;

            if(time >= timer)
            {
                finished = true;
            }
        }
    }

    public ArrayList<Point> getFlamePositions() {
        return flamePositions;
    }

    public boolean isFinished() {
        return finished;
    }
}
