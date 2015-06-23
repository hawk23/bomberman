package game.model.objects;

import game.config.GameSettings;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class FallingBlock extends Block {

	private static final String fallingBlock   	= "res/sounds/block/drop.ogg";
	private static final String blockImagePath 	= "res/visuals/blocks/tile_solid_01.png";
	
	private Sound drop;
	
	private int originalX;
	private int originalY;
	private int targetX;
	private int targetY;
	
	private float drawX;
	private float drawY;
	private float lastDrawX;
	private float lastDrawY;
	
	private boolean deadly;
	private boolean dropped;
	
	private float speed = 2f;
    /**
     * interpolation value for the movement between two points
     */
    private float movementInterpolation;
    
	public FallingBlock(int tileX, int tileY) {
		super(tileX, tileY);
		this.targetX = tileX * GameSettings.TILE_WIDTH;
		this.targetY = tileY * GameSettings.TILE_HEIGHT;
		this.originalX = this.targetX;
		this.originalY = this.targetY - 960;
		this.lastDrawX = drawX = this.originalX;
		this.lastDrawY = drawY = this.originalY;
		this.movementInterpolation = 0;
		this.deadly = false;
		this.dropped = false;
		setImage(blockImagePath);
		loadSound();
	}

	private void loadSound() {
		try {
			this.drop = new Sound(fallingBlock);
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		if (this.drawY >= 0f) {
	        float interpolate = ((AppGameContainerFSCustom) container).getRenderInterpolation();
	        
	        float x = (this.drawX - this.lastDrawX) * interpolate + this.lastDrawX;
	        float y = (this.drawY - this.lastDrawY) * interpolate + this.lastDrawY;
	        
	        g.drawImage(this.image, x, y);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		float deltaInSecs = (float) delta * 0.001f;
		this.lastDrawX = this.drawX;
		this.lastDrawY = this.drawY;
        
        if (this.movementInterpolation <= 1) {
        	this.movementInterpolation += this.speed * deltaInSecs;
        	this.drawX = lerp(this.originalX, this.targetX, this.movementInterpolation);
        	this.drawY = lerp(this.originalY, this.targetY, this.movementInterpolation);
        }
        else {
        	this.deadly = true;
        }
        
        if (this.deadly && !this.dropped) {
        	this.drop.play(1.5f, 0.3f);
        	this.dropped = true;
        }
	}
	
    public static float lerp(float original, float target, float interpolationValue) {

        if (interpolationValue < 0)
            return original;

        return original + interpolationValue * (target - original);
    }
    
    public boolean isDeadly() {
    	return this.deadly;
    }

}
