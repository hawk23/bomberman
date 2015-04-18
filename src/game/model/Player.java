package game.model;

import game.config.GameSettings;
import game.config.PlayerConfig;
import game.debug.Debugger;
import game.event.ExplosionEvent;
import game.input.Direction;
import game.input.InputManager;

import java.awt.Point;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

/**
 * Created by Albert on 30.03.2015.
 */
public class Player extends GameObject implements IDestroyable, ExplosionListener
{
    private BombermanMap map;
    private InputManager inputManager;

    private int 	posX;
    private int 	posY;
    private int 	targetX;
    private int 	targetY;
    private int 	originalX;
    private int 	originalY;

    private float 	drawPosX;
    private float 	drawPosY;
    private float 	lastDrawPosX;
    private float 	lastDrawPosY;

    private Animation animation_actual;
    private Animation animation_up;
    private Animation animation_down;
    private Animation animation_left;
    private Animation animation_right;

    /**
     * speed of the player
     */
    private float speed;

    /**
     * settings for the bomb
     * TODO move to PlayerConfig ?!
     */
    private int bombTimer = 3000;
    private int bombRange = 1;
    private int bombLimit = 100;
    private int bombCount = 0;

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

    private PlayerConfig playerConfig;

    /**
     * @param shape        - is the tile representation of the player
     * @param map
     * @param playerID
     * @param inputManager
     * @throws SlickException
     */
    public Player(BombermanMap map, InputManager inputManager, PlayerConfig playerConfig, Point spawnPoint) throws SlickException
    {
        super((int) spawnPoint.getX() / map.getTileSize(), (int) spawnPoint.getY() / map.getTileSize());

        this.map			= map;
        this.inputManager	= inputManager;
        this.playerConfig	= playerConfig;
        this.collides		= true;
        
        this.speed = 1.7f;
        this.moving = false;
        this.movementDirection = Direction.DOWN;
        this.movementInterpolation = 0.0f;

        switch (playerConfig.getId())
        {
            case 0:
            case 1:
            case 2:
            case 3:
            	{

                Image[] up = {
                        new Image("res/visuals/classic/players/00/Bman_B_f00.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f01.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f02.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f03.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f04.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f05.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f06.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f07.png"),
                };
                Image[] down = {
                        new Image("res/visuals/classic/players/00/Bman_F_f00.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f01.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f02.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f03.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f04.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f05.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f06.png"),
                        new Image("res/visuals/classic/players/00/Bman_F_f07.png"),
                };
                Image[] left = {
                        new Image("res/visuals/classic/players/00/Bman_L_f00.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f01.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f02.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f03.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f04.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f05.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f06.png"),
                        new Image("res/visuals/classic/players/00/Bman_L_f07.png"),
                };
                Image[] right = {
                        new Image("res/visuals/classic/players/00/Bman_R_f00.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f01.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f02.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f03.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f04.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f05.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f06.png"),
                        new Image("res/visuals/classic/players/00/Bman_R_f07.png"),
                };


                int[] duration = {40, 40, 40, 40, 40, 40, 40, 40};

                animation_up = new Animation(up, duration, moving);
                animation_down = new Animation(down, duration, false);
                animation_left = new Animation(left, duration, false);
                animation_right = new Animation(right, duration, false);

                animation_actual = new Animation();
                
                break;
            }

            default:
                break;
        }

        image = animation_down.getImage(0);
        originalX = targetX =  posX = (int) spawnPoint.getX();
        originalY = targetY =  posY = (int) spawnPoint.getY();
        this.calculateDrawPosition(posX, posY);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        float interpolate = ((AppGameContainerFSCustom) container).getRenderInterpolation();
        image.draw((drawPosX - lastDrawPosX) * interpolate + lastDrawPosX, (drawPosY - lastDrawPosY) * interpolate + lastDrawPosY);
        Color tmp = g.getColor();
        g.setColor(Color.red);
        g.drawRect(drawPosX, drawPosY, image.getWidth(), image.getHeight());
        g.setColor(tmp);
        g.drawString("posX: " + posX + " origX: " + originalX + " targetX: " + targetX, posX, posY);
        g.drawString("posY: " + posY + " origY: " + originalY + " targetY: " + targetY, posX, posY +10);
        g.drawString("drawX: " + drawPosX + " drawY: " + drawPosY, posX, posY+20);
        
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        float deltaInSecs = (float) delta * 0.001f;
        lastDrawPosX = drawPosX;
        lastDrawPosY = drawPosY;

        this.inputManager.update();
        direction = this.inputManager.getDirection();

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
            if (checkOppositeMovement()) {
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
                    image = animation_up.getImage(0);
                    break;

                case DOWN:
                    image = animation_down.getImage(0);
                    break;

                case LEFT:
                    image = animation_left.getImage(0);
                    break;

                case RIGHT:
                    image = animation_right.getImage(0);
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
    public boolean destroy() {
        Debugger.log("die Bomberman, DIE!");
        return false;
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
}