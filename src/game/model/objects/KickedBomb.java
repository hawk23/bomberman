package game.model.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;
import game.config.GameSettings;
import game.input.Direction;

public class KickedBomb extends Bomb {
	
	private Bomb bomb;
	private Direction direction;
	private float speed = 4.0f;
    private float movementInterpolation;
    private boolean waiting;
    private boolean stopped;
    
	private int originalX;
	private int originalY;
	private int targetX;
	private int targetY;
	
	private float drawX;
	private float drawY;
	private float lastDrawX;
	private float lastDrawY;

	public KickedBomb(Bomb bomb, Direction direction) {
		super(bomb.getTileX(), bomb.getTileY(), bomb.getBombRange(), bomb.getTimer());
		this.bomb = bomb;
		this.direction = direction;
		this.movementInterpolation = 0.0f;
		this.time = this.bomb.getTime();
		this.waiting = false;
		this.stopped = false;
		this.originalX = this.targetX = this.tileX * GameSettings.TILE_WIDTH;
		this.originalY = this.targetY = this.tileY * GameSettings.TILE_HEIGHT;
		this.lastDrawX = this.drawX = this.tileX * GameSettings.TILE_WIDTH;
		this.lastDrawY = this.drawY = this.tileY * GameSettings.TILE_HEIGHT;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

        float interpolate = ((AppGameContainerFSCustom) container).getRenderInterpolation();
        
        float x = (drawX - lastDrawX) * interpolate + lastDrawX;
        float y = (drawY - lastDrawY) * interpolate + lastDrawY;
	        
		bombAnimation.draw(x, y);
        if (time <= timer) {
    		fuzeBurn.draw(x + GameSettings.TILE_HEIGHT / 2, y + GameSettings.TILE_HEIGHT /2 - FUZE_POS_Y - burnDist, 3, burnDist);
            effectSystem.render();
        }
        //g.drawRect(tileX * GameSettings.TILE_WIDTH, tileY * GameSettings.TILE_HEIGHT, 64, 64);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		float deltaInSecs = (float) delta * 0.001f;
        lastDrawX = drawX;
        lastDrawY = drawY;
        
        movementInterpolation += speed * deltaInSecs;
        drawX = lerp(originalX, targetX, movementInterpolation);
        drawY = lerp(originalY, targetY, movementInterpolation);
        
        if (movementInterpolation >= 1) {
        	this.waiting = true;
        }

        
        if (time <= timer) {
    		float timeLeft = timer-time;
            burnDist=FUZE_HEIGHT * timeLeft/timer;

            effectSystem.setPosition(drawX + GameSettings.TILE_HEIGHT/2, drawY + GameSettings.TILE_HEIGHT/2 - FUZE_POS_Y - burnDist);
            effectSystem.update(delta);

            time += delta;
    	}
        else {
        	setExploded();
        }
        
        this.bomb.update(container, game, delta);
        
	}
	
	public void moveOn() {
		
		originalX = tileX * GameSettings.TILE_WIDTH;
		originalY = tileY * GameSettings.TILE_HEIGHT;
		
		switch (direction) {
		
			case UP: 
				targetY = (tileY - 1) * GameSettings.TILE_HEIGHT;
				break;
			case LEFT: 
				targetX = (tileX - 1) * GameSettings.TILE_WIDTH;
				break;
			case DOWN: 
				targetY = (tileY + 1) * GameSettings.TILE_HEIGHT;
				break;
			case RIGHT: 
				targetX = (tileX + 1) * GameSettings.TILE_WIDTH;
				break;
			default: break;
		}
        
		tileX = targetX / GameSettings.TILE_WIDTH;
        tileY = targetY / GameSettings.TILE_HEIGHT;
        
		this.bomb.setTileX(tileX);
		this.bomb.setTileY(tileY);
    	movementInterpolation = movementInterpolation % 1.0f;
		this.waiting = false;
	}
	
	public static float lerp(float original, float target, float interpolationValue) {

        if (interpolationValue < 0)
            return original;

        return original + interpolationValue * (target - original);
    }

	public void changeDirection(Direction direction) {
		this.direction = direction;
		int tempX, tempY;
        tempX = originalX;
        originalX = targetX;
        targetX = tempX;
        tempY = originalY;
        originalY = targetY;
        targetY = tempY;
        
        tileX = originalX / GameSettings.TILE_WIDTH;
        tileY = originalY / GameSettings.TILE_HEIGHT;

        movementInterpolation = 1.0f - movementInterpolation;
	}
	
	public Bomb getBomb() {
		return bomb;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getOriginalX() {
		return originalX;
	}

	public int getOriginalY() {
		return originalY;
	}
	
	public void setStopped() {
		this.stopped = true;
	}
	
	public boolean isStopped() {
		return this.stopped;
	}
	
	@Override
    public boolean destroy()
    {
        setExploded();
        return true;
    }
    
    public void setExploded()
    {
    	this.exploded = true;
    	this.destroyed = true;
        this.bomb.destroy();
    }

}
