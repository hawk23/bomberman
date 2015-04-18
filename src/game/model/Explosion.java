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
		this.timer      = 2000;
        this.wrapper    = wrapper;

        this.flamePositions = this.calculateFlamePosition();
	}

    private ArrayList<Point> calculateFlamePosition ()
    {
        ArrayList<Point> explodedTiles = new ArrayList<Point>();

        if (!this.finished)
        {
            // add center position of explosion
            explodedTiles.add(new Point(this.getPosX(), this.getPosY()));

            boolean blockedLeft     = false;
            boolean blockedTop      = false;
            boolean blockedRight    = false;
            boolean blockedBottom   = false;

            for (int i = 1; i <= this.range; i++)
            {
                // add four rays.
                Point   rayLeft     = new Point(this.getPosX()-i, this.getPosY());
                Point   rayTop      = new Point(this.getPosX(), this.getPosY()-i);
                Point   rayRight    = new Point(this.getPosX()+i, this.getPosY());
                Point   rayBottom   = new Point(this.getPosX(), this.getPosY()+i);

                if (!this.wrapper.isBlocked(rayLeft.x, rayLeft.y) && !blockedLeft)
                {
                    explodedTiles.add(rayLeft);
                }
                else
                {
                    blockedLeft = true;
                }

                if (!this.wrapper.isBlocked(rayTop.x, rayTop.y) && !blockedTop)
                {
                    explodedTiles.add(rayTop);
                }
                else
                {
                    blockedTop = true;
                }

                if (!this.wrapper.isBlocked(rayRight.x, rayRight.y) && !blockedRight)
                {
                    explodedTiles.add(rayRight);
                }
                else
                {
                    blockedRight = true;
                }

                if (!this.wrapper.isBlocked(rayBottom.x, rayBottom.y) && !blockedBottom)
                {
                    explodedTiles.add(rayBottom);
                }
                else
                {
                    blockedBottom = true;
                }
            }
        }


        return explodedTiles;
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
