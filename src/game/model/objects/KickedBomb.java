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
        
        float x = (this.drawX - this.lastDrawX) * interpolate + this.lastDrawX;
        float y = (this.drawY - this.lastDrawY) * interpolate + this.lastDrawY;
	        
        this.bombAnimation.draw(x, y);
        if (this.time <= this.timer) {
        	this.fuzeBurn.draw(x + GameSettings.TILE_HEIGHT / 2, y + GameSettings.TILE_HEIGHT /2 - FUZE_POS_Y - this.burnDist, 3, this.burnDist);
        	this.effectSystem.render();
        }
        //g.drawRect(tileX * GameSettings.TILE_WIDTH, tileY * GameSettings.TILE_HEIGHT, 64, 64);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		float deltaInSecs = (float) delta * 0.001f;
		this.lastDrawX = this.drawX;
		this.lastDrawY = this.drawY;
        
		this.movementInterpolation += this.speed * deltaInSecs;
		this.drawX = lerp(this.originalX, this.targetX, this.movementInterpolation);
		this.drawY = lerp(this.originalY, this.targetY, this.movementInterpolation);
        
        if (this.movementInterpolation >= 1) {
        	this.waiting = true;
        }

        
        if (this.time <= this.timer) {
    		float timeLeft = this.timer - this.time;
    		this.burnDist = FUZE_HEIGHT * timeLeft /this.timer;

    		this.effectSystem.setPosition(this.drawX + GameSettings.TILE_HEIGHT /2, this.drawY + GameSettings.TILE_HEIGHT /2 - FUZE_POS_Y - this.burnDist);
    		this.effectSystem.update(delta);

    		this.time += delta;
    	}
        else {
        	setExploded();
        }
        
        this.bomb.update(container, game, delta);
        
	}
	
	public void moveOn() {
		
		this.originalX = this.tileX * GameSettings.TILE_WIDTH;
		this.originalY = this.tileY * GameSettings.TILE_HEIGHT;
		
		switch (this.direction) {
		
			case UP: 
				this.targetY = (this.tileY - 1) * GameSettings.TILE_HEIGHT;
				break;
			case LEFT: 
				this.targetX = (this.tileX - 1) * GameSettings.TILE_WIDTH;
				break;
			case DOWN: 
				this.targetY = (this.tileY + 1) * GameSettings.TILE_HEIGHT;
				break;
			case RIGHT: 
				this.targetX = (this.tileX + 1) * GameSettings.TILE_WIDTH;
				break;
			default: break;
		}
        
		this.tileX = this.targetX / GameSettings.TILE_WIDTH;
		this.tileY = this.targetY / GameSettings.TILE_HEIGHT;
        
		this.bomb.setTileX(this.tileX);
		this.bomb.setTileY(this.tileY);
		this.movementInterpolation = this.movementInterpolation % 1.0f;
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
        
		//swap
		tempX = this.originalX;
        this.originalX = this.targetX;
        this.targetX = tempX;
        tempY = this.originalY;
        this.originalY = this.targetY;
        this.targetY = tempY;
        
        this.tileX = this.originalX / GameSettings.TILE_WIDTH;
        this.tileY = this.originalY / GameSettings.TILE_HEIGHT;

        this.movementInterpolation = 1.0f - this.movementInterpolation;
	}
	
	public Bomb getBomb() {
		return this.bomb;
	}

	public boolean isWaiting() {
		return this.waiting;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public int getOriginalX() {
		return this.originalX;
	}

	public int getOriginalY() {
		return this.originalY;
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
