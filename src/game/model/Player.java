package game.model;

import game.config.GameSettings;
import game.config.PlayerConfig;
import game.event.ExplosionEvent;
import game.input.Direction;
import game.input.InputManager;
import game.interfaces.IDestroyable;

import java.awt.Point;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class Player extends GameObject implements IDestroyable, ExplosionListener
{
    private static final String deathSoundPath      = "/res/sounds/player/death.ogg";
    private static final String shieldImagePath 	= "res/visuals/shield/shieldmap.png";
    
    private BombermanMap map;
    private InputManager inputManager;
    private PlayerConfig playerConfig;

    private Sound 		deathSound;

    private int 		posX;
    private int 		posY;
    private int 		targetX;
    private int 		targetY;
    private int 		originalX;
    private int 		originalY;

    private float 		drawPosX;
    private float 		drawPosY;
    private float 		lastDrawPosX;
    private float 		lastDrawPosY;
    
    private Image		stopDown;
    private Image		stopRight;
    private Image		stopUp;
    private Image		stopLeft;

    private int 		animationInterval;
    private int 		dyingAnimationInterval = 50;
    private int			shieldAnimationInterval = 50;
    
    private Animation 	animation_actual;
    private Animation 	animation_up;
    private Animation 	animation_down;
    private Animation 	animation_left;
    private Animation 	animation_right;
    private Animation	animation_die;
    private Animation	animation_shielded;

    private float 		speed;
    private int 		bombTimer;
    private int 		bombRange;
    private int 		bombLimit;
    private int 		bombCount;

    private boolean		shielded;
    private int 		shieldTimer;
    private boolean		indestructable;
    private boolean 	destroyed;
    private boolean		dead;
    private boolean		dying;
    private int			dyingTimer;
    
    /**
     * interpolation value for the movement between two points
     */
    private float movementInterpolation;

    /**
     * actual press - direction of input
     */
    private Direction direction;

    /**
     * actual movement direction
     */
    private Direction movementDirection;

    /**
     * does the player move between tiles?
     */
    private boolean moving;

    public Player(BombermanMap map, InputManager inputManager, PlayerConfig playerConfig, Point spawnPoint) throws SlickException
    {
        super((int) spawnPoint.getX() / map.getTileSize(), (int) spawnPoint.getY() / map.getTileSize());

        this.map					= map;
        this.inputManager			= inputManager;
        this.playerConfig			= playerConfig;
        
        this.speed 					= this.playerConfig.getInitialSpeed();
        this.bombLimit 				= this.playerConfig.getInitialBombLimit();
        this.bombRange 				= this.playerConfig.getInitialBombRange();
        this.bombTimer 				= this.playerConfig.getInitialBombTimer();
        this.bombCount 				= 0;
        this.moving 				= false;
        this.shielded				= false;
        this.dying					= false;
        this.dead					= false;
        this.destroyed				= false;
        this.indestructable			= false;
        this.movementDirection 		= Direction.DOWN;
        this.movementInterpolation 	= 0.0f;
        
        adjustAnimationSpeed();
        loadVisuals(this.playerConfig.getPath());
        loadSound();

        originalX = targetX =  posX = (int) spawnPoint.getX();
        originalY = targetY =  posY = (int) spawnPoint.getY();
        this.calculateDrawPosition(posX, posY);
        lastDrawPosX = drawPosX;
        lastDrawPosY = drawPosY;
    }

    private void loadVisuals(String path) throws SlickException {
		Image spriteSheet 		= new Image(path);
		
		SpriteSheet animDown 	= new SpriteSheet(spriteSheet.getSubImage(0, 0, 640, 128), 64, 128);
		SpriteSheet animRight 	= new SpriteSheet(spriteSheet.getSubImage(0, 128, 640, 128), 64, 128);
		SpriteSheet animUp 		= new SpriteSheet(spriteSheet.getSubImage(0, 256, 640, 128), 64, 128);
		SpriteSheet animLeft 	= new SpriteSheet(spriteSheet.getSubImage(0, 384, 640, 128), 64, 128);
		SpriteSheet animDie 	= new SpriteSheet(spriteSheet.getSubImage(0, 512, 640, 128), 64, 128);
		
		this.animation_down 	= new Animation(animDown, animationInterval);
		this.animation_down.setAutoUpdate(false);
		this.animation_right	= new Animation(animRight, animationInterval);
		this.animation_right.setAutoUpdate(false);
		this.animation_up		= new Animation(animUp, animationInterval);
		this.animation_up.setAutoUpdate(false);
		this.animation_left		= new Animation(animLeft, animationInterval);
		this.animation_left.setAutoUpdate(false);
		this.animation_die		= new Animation(animDie, dyingAnimationInterval);
		this.animation_die.setAutoUpdate(false);
		this.animation_actual	= new Animation();
		
        SpriteSheet shield		= new SpriteSheet(shieldImagePath, 64, 128);
        this.animation_shielded	= new Animation(shield, shieldAnimationInterval);
		
		this.stopDown			= spriteSheet.getSubImage(0, 640, 64, 128);
		this.stopRight			= spriteSheet.getSubImage(64, 640, 64, 128);
		this.stopUp				= spriteSheet.getSubImage(128, 640, 64, 128);
		this.stopLeft			= spriteSheet.getSubImage(192, 640, 64, 128);
		
		this.image				= this.stopDown;	
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        float interpolate = ((AppGameContainerFSCustom) container).getRenderInterpolation();
        
        float x = (drawPosX - lastDrawPosX) * interpolate + lastDrawPosX;
        float y = (drawPosY - lastDrawPosY) * interpolate + lastDrawPosY;
        
        image.draw(x, y);
        
        if (shielded) {
        	animation_shielded.draw(x, y);
        }

            
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        float deltaInSecs = (float) delta * 0.001f;
        lastDrawPosX = drawPosX;
        lastDrawPosY = drawPosY;

        
        if (dying) 
        {
        	if (dyingTimer <= 0) {
        		dead = true;
        		destroy();
        	}
        	else {
        		dyingTimer -= delta;
        		image = animation_actual.getCurrentFrame();
        		animation_actual.update(delta);
        	}
        }
        else // alive!
        {
        	this.inputManager.update();
            direction = this.inputManager.getDirection();

            if (shielded){
            	
            	if (shieldTimer <= 0) {
            		shielded = false;
            	}
            	else {
            		shieldTimer -= delta;
            	}
            }
            
            
            // stopped
            if (!moving) {

                movementInterpolation = 0.0f;

                switch (direction) {

                    case UP:
                    	originalY = posY;
                    	targetY = originalY - GameSettings.TILE_HEIGHT;
                    	targetX = originalX = posX;
                    	moving = true;
                    	movementDirection = Direction.UP;
                    	animation_actual = animation_up;
                    	animation_actual.restart();
                        break;

                    case DOWN:
                        originalY = posY;
                        targetY = originalY + GameSettings.TILE_HEIGHT;
                        targetX = originalX = posX;
                        moving = true;
                        movementDirection = Direction.DOWN;
                        animation_actual = animation_down;
                        animation_actual.restart();
                        break;

                    case LEFT:
                        originalX = posX;
                        targetX = originalX - GameSettings.TILE_WIDTH;
                        targetY = originalY = posY;
                        moving = true;
                        movementDirection = Direction.LEFT;
                        animation_actual = animation_left;
                        animation_actual.restart();
                        break;

                    case RIGHT:
                        originalX = posX;
                        targetX = originalX + GameSettings.TILE_WIDTH;
                        targetY = originalY = posY;
                        moving = true;
                        movementDirection = Direction.RIGHT;
                        animation_actual = animation_right;
                        animation_actual.restart();
                        break;

                    default:
                        break;
                }
                
                if (isBlocked(targetX, targetY)) {
                	targetX = originalX;
                	targetY = originalY;
                	moving = false;
                }
            }

            // currently moving between tiles?
            if (moving) {
                movementInterpolation += speed * deltaInSecs;

                // move in opposite direction?
                if (checkOppositeMovement() && !isBlocked(originalX, originalY)) {
                    int tempX, tempY;
                    tempX = originalX;
                    originalX = targetX;
                    targetX = tempX;
                    tempY = originalY;
                    originalY = targetY;
                    targetY = tempY;

                    movementInterpolation = 1.0f - movementInterpolation;
                    movementDirection = direction;

                    switch (movementDirection) {
                        case UP:
                            animation_actual = animation_up;
                            animation_actual.restart();
                            break;

                        case DOWN:
                            animation_actual = animation_down;
                            animation_actual.restart();
                            break;

                        case LEFT:
                            animation_actual = animation_left;
                            animation_actual.restart();
                            break;

                        case RIGHT:
                            animation_actual = animation_right;
                            animation_actual.restart();
                            break;

                        default:
                            break;
                    }
                }


                // target - tile reached?
                if (movementInterpolation >= 1) {

                    // flawless movement?
                    if (movementDirection == direction) {

                        switch (direction) {

                            case UP:
                                originalY = targetY;
                                targetY = originalY - GameSettings.TILE_HEIGHT;
                                targetX = originalX;
                                break;

                            case DOWN:
                                originalY = targetY;
                                targetY = originalY + GameSettings.TILE_HEIGHT;
                                targetX = originalX;
                                break;

                            case LEFT:
                                originalX = targetX;
                                targetX = originalX - GameSettings.TILE_WIDTH;
                                targetY = originalY;
                                break;

                            case RIGHT:
                                originalX = targetX;
                                targetX = originalX + GameSettings.TILE_WIDTH;
                                targetY = originalY;
                                break;

                            default:
                                break;
                        }
                        
                        if (isBlocked(targetX, targetY)) {
                        	targetX = originalX;
                        	targetY = originalY;
                        	movementInterpolation = 1.0f;
                        	moving = false;
                        }
                        else {
                        	movementInterpolation = movementInterpolation % 1.0f;
                        }  
                    }
                    // or stop?
                    else {
                        movementInterpolation = 1.0f;
                        moving = false;
                    }
                }

                // interpolate x and y coordinates
                calculateDrawPosition(lerp(originalX, targetX, movementInterpolation), lerp(originalY, targetY, movementInterpolation));
            }

            if (moving) {
                image = animation_actual.getCurrentFrame();
                animation_actual.update(delta);
            } else {
                switch (movementDirection) {

                    case UP:
                        image = stopUp;
                        break;

                    case DOWN:
                        image = stopDown;
                        break;

                    case LEFT:
                        image = stopLeft;
                        break;

                    case RIGHT:
                        image = stopRight;
                        break;

                    default:
                        break;
                }
            }

            if (movementInterpolation >= 0.5f)
            {
                posX = targetX;
                posY = targetY;
                tileX = posX / this.map.getTileSize();
                tileY = posY / this.map.getTileSize();
            }

            if (inputManager.bombDrop()) {
               addBomb();
            }
        }      
    }

    /**
     * linear interpolation between two points
     *
     * @param original
     * @param target
     * @param interpolationValue
     * @return the interpolated point between original and target
     */
    public static float lerp(float original, float target, float interpolationValue) {

        if (interpolationValue < 0)
            return original;

        return original + interpolationValue * (target - original);
    }

    /**
     * checks if a direction-change to opposite direction occurred
     *
     * @return true if so false if not
     */
    private boolean checkOppositeMovement() {

        boolean value = false;

        if (moving && movementDirection != null && direction != null) {

            switch (movementDirection) {

                case UP:
                    if (direction == Direction.DOWN) {
                        value = true;
                    }
                    break;

                case DOWN:
                    if (direction == Direction.UP) {
                        value = true;
                    }
                    break;

                case LEFT:
                    if (direction == Direction.RIGHT) {
                        value = true;
                    }
                    break;

                case RIGHT:
                    if (direction == Direction.LEFT) {
                        value = true;
                    }
                    break;

                default:
                    break;
            }
        }

        return value;
    }

    @Override
    public boolean destroy()
    {
    	if(!indestructable) {
    		
    		if (!destroyed) {
            	
        		if (!shielded && !dying) {
            		
            		dying = true;
            		playSound(deathSound);
            		this.animation_actual = this.animation_die;
            		this.animation_actual.restart();
            		this.dyingTimer = this.animation_actual.getFrameCount() * dyingAnimationInterval;
            	}
            	
            	if (dead) {
        		     this.map.increaseNrDeadPlayer();
        		     return this.destroyed = true;
        		}
        	}
    	}
    	return this.destroyed;
    }

    public int getBombTimer() {
        return bombTimer;
    }

    public void setBombTimer(int bombTimer) {
        this.bombTimer = bombTimer;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public void reduceBombCounter()
    {
        bombCount--;
        
        if (bombCount < 0)
        	bombCount = 0;
    }

    public void addBomb()
    {
        if (bombCount < bombLimit)
        {
        	if(!(this.map.isBlocked(tileX, tileY)))
        	{
        		Bomb bomb = new Bomb(tileX, tileY, bombRange, bombTimer);
                bomb.addListener(this);

        		this.map.addBomb(bomb);
        		this.bombCount++;
        	}
        }
    }
    
    private boolean isBlocked(float x, float y)
    {
    	if (map.isBlocked((int) x / map.getTileSize(), (int) y / map.getTileSize()))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    private void calculateDrawPosition(float x, float y) {
    	
    	float xGab = (this.image.getWidth() - GameSettings.TILE_WIDTH) /2; 
    	drawPosX = x - xGab;
    	
    	float ySpace = 10f;
    	
    	float yGab = this.image.getHeight() - GameSettings.TILE_HEIGHT;
    	drawPosY = y - yGab - ySpace;
    }

    public float getDrawPosX() {
        return drawPosX;
    }

    public void setDrawPosX(float drawPosX) {
        this.drawPosX = drawPosX;
    }

    public float getDrawPosY() {
        return drawPosY;
    }

    public void setDrawPosY(float drawPosY) {
        this.drawPosY = drawPosY;
    }

    @Override
    public void exploded(ExplosionEvent e)
    {
        this.reduceBombCounter();
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    private void adjustAnimationSpeed() {
    	float perc = this.speed / 2;
    	this.animationInterval = (int) (50 / perc);
    }
    
    public void adjustSpeed (float value) {
    	this.speed += value;
    	adjustAnimationSpeed();
    }

    public void adjustBombRange (int value)
    {
        this.bombRange += value;
    }

    public void adjustBombLimit(int value)
    {
        this.bombLimit += value;
    }
    
    public void setShielded(int timer) {
    	this.shieldTimer = timer;
    	this.shielded = true;
    }

    private void loadSound ()
    {
        try
        {
            this.deathSound         = new Sound(deathSoundPath);
        }

        catch (SlickException e)
        {
            //TODO
        }
    }

    private void playSound(Sound sound)
    {
        sound.play();
    }

    public PlayerConfig getPlayerConfig()
    {
        return playerConfig;
    }

    public int getBombCount()
    {
        return bombCount;
    }

    public int getBombLimit()
    {
        return bombLimit;
    }

	public boolean isShielded() {
		return shielded;
	}

    public int getShieldTimerSeconds ()
    {
        return this.shieldTimer / 1000;
    }

    public float getSpeed()
    {
        return speed;
    }

	public boolean isDying() {
		return dying;
	}

	public void setIndestructable() {
		this.indestructable = true;
	}
}