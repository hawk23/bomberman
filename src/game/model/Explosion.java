package game.model;

import game.config.GameSettings;
import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.TiledMapWrapper;

import java.util.ArrayList;

public class Explosion extends GameObject implements IUpdateable, IRenderable
{
    private static final String explosionSoundPath = "res/sounds/bomb/explosion.ogg";
    public static final	int 	timer			= 500;
	private int                 range           = 1;
    private int                 time            = 0;
    private boolean             finished        = false;
	private TiledMapWrapper     wrapper;
    private ArrayList<FlamePoint>    flamePositions  = null;
    private Sound               explosionSound;

	public Explosion(int posX, int posY, int range, TiledMapWrapper wrapper)
	{
        super(posX, posY);

		this.range      = range;
        this.wrapper    = wrapper;

        this.flamePositions = this.calculateFlamePosition();

        loadSound();
        playSound(explosionSound);
	}

    private ArrayList<FlamePoint> calculateFlamePosition ()
    {
        ArrayList<FlamePoint> explodedTiles = new ArrayList<FlamePoint>();

        // add center position of explosion
        explodedTiles.add(new FlamePoint(this.getTileX(), this.getTileY(),FlameDirection.CENTER, 0));

        boolean blockedLeft     = false;
        boolean blockedTop      = false;
        boolean blockedRight    = false;
        boolean blockedBottom   = false;

        for (int i = 1; i <= this.range; i++)
        {
            // add four rays.
            FlamePoint rayLeft       = new FlamePoint(this.getTileX() - i, this.getTileY(), FlameDirection.LEFT,i);
            FlamePoint rayTop        = new FlamePoint(this.getTileX(), this.getTileY() - i, FlameDirection.UP,i);
            FlamePoint rayRight      = new FlamePoint(this.getTileX() + i, this.getTileY(), FlameDirection.RIGHT,i);
            FlamePoint rayBottom     = new FlamePoint(this.getTileX(), this.getTileY() + i, FlameDirection.DOWN,i);

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
    private boolean handleFlamePosition (boolean blocked, FlamePoint p, ArrayList<FlamePoint> explodedTiles)
    {
        if (!blocked)
        {   // check if point should be added
            if (!this.wrapper.isSolid(p.x, p.y))
            {
                explodedTiles.add(p);
            }else{
                //Make shure the last explosion before the solid is marked as _END
                for(int i=explodedTiles.size()-1;i>=0;i--){
                    FlamePoint flame = explodedTiles.get(i);

                    if(flame.getDirection()==p.getDirection()){
                        flame.setDirection(flame.makeDirectionEnd(p.getDirection()));
                        break;
                    }
                }
            }

            // check if flame ray should be blocked in this direction.
            if (this.wrapper.isSolid(p.x, p.y) || this.wrapper.isDestroyable(p.x, p.y) || p.getDistanceFromCenter()==this.range)
            {
                //Make an End Mark in this direction
                p.setDirection(p.makeDirectionEnd(p.getDirection()));
                blocked = true;
            }


        }

        return blocked;
    }

	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        //Rendering in ExplosionSystem in BombermanMap
        /*
        for (FlamePoint p : this.flamePositions)
        {
            if(p.getDirection()==p.makeDirectionEnd(p.getDirection()))
                g.setColor(Color.red);
            else
                g.setColor(Color.green);

            g.drawRect(p.x * GameSettings.TILE_WIDTH, p.y * GameSettings.TILE_HEIGHT, GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
        }*/
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

    public ArrayList<FlamePoint> getFlamePositions() {
        return flamePositions;
    }

    public boolean isFinished() {
        return finished;
    }

    private void loadSound ()
    {
        try
        {
            this.explosionSound = new Sound(explosionSoundPath);
        }

        catch (SlickException e)
        {
            //TODO
        }
    }

    private void playSound (Sound sound)
    {
        sound.play();
    }

    public int getTimer(){ return timer;}

}
