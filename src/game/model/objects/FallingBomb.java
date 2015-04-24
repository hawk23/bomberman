package game.model.objects;

import game.config.GameSettings;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class FallingBomb extends GameObject {

	private static final String animationPath 	= "res/visuals/bomb/bomb.png";
	private int animationInterval = 50;
	private Animation animation;
	
	private int originalX;
	private int originalY;
	private int targetX;
	private int targetY;
	
	private float drawX;
	private float drawY;
	private float lastDrawX;
	private float lastDrawY;
	
	private boolean dropped;
	
	private float speed = 1.5f;
    private float movementInterpolation;
    
    public FallingBomb(int tileX, int tileY) {
		super(tileX, tileY);
		this.targetX = tileX * GameSettings.TILE_WIDTH;
		this.targetY = tileY * GameSettings.TILE_HEIGHT;
		this.originalX = this.targetX;
		this.originalY = this.targetY - 960;
		this.lastDrawX = drawX = this.originalX;
		this.lastDrawY = drawY = this.originalY;
		this.movementInterpolation = 0;
		this.dropped = false;
		loadAnimation();
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if (drawY >= 0f) {
	        float interpolate = ((AppGameContainerFSCustom) container).getRenderInterpolation();
	        
	        float x = (drawX - lastDrawX) * interpolate + lastDrawX;
	        float y = (drawY - lastDrawY) * interpolate + lastDrawY;
	        
	        animation.draw(x, y);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		float deltaInSecs = (float) delta * 0.001f;
        lastDrawX = drawX;
        lastDrawY = drawY;
        
        if (movementInterpolation <= 1) {
            movementInterpolation += speed * deltaInSecs;
            drawX = lerp(originalX, targetX, movementInterpolation);
            drawY = lerp(originalY, targetY, movementInterpolation);
        }
        else {
        	this.dropped = true;
        }
		
	}
	
    public static float lerp(float original, float target, float interpolationValue) {

        if (interpolationValue < 0)
            return original;

        return original + interpolationValue * (target - original);
    }
    
	private void loadAnimation() {

		try {
			SpriteSheet bombSheet 	= new SpriteSheet(animationPath, 64, 64);
	        animation			    = new Animation(bombSheet, animationInterval);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
    
    public boolean isDropped() {
    	return this.dropped;
    }
}
