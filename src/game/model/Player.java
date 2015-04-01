package game.model;

import game.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import slick.extension.AppGameContainerFSCustom;
import slick.extension.MovementManager;

/**
 * Created by Albert on 30.03.2015.
 */
public class Player extends GameObject implements IDestroyable
{
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    //......

    private     StateBasedGame              game;
    private     Map                         map;
    private     AppGameContainerFSCustom    gameContainer;
    private     MovementManager             movementManager;

    private float posX;
    private float posY;
    private float targetX;
    private float targetY;
    private float originalX;
    private float originalY;

    private Animation animation_actual;
    private Animation animation_up;
    private Animation animation_down;
    private Animation animation_left;
    private Animation animation_right;


    /**
     * for render interpolation
     */
    private float lastPosX;
    private float lastPosY;

    /**
     * speed of the player
     */
    private float speed;

    /**
     * actual image to render
     */
    private Image image;

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

    /**
     * the movement shape
     */
    private Shape shape;

    /**
     *
     * @param shape
     * @param map
     * @param game
     * @param playerID
     * @throws SlickException
     */
    public Player(Shape shape, Map map, AppGameContainerFSCustom gameContainer, StateBasedGame game, int playerID, MovementManager movementManager) throws SlickException {
        this.game                   = game;
        this.map                    = map;
        this.shape                  = shape;
        this.gameContainer          = gameContainer;
        this.movementManager        = movementManager;
        this.collides               = true;

        posX                        = lastPosX  = shape.getX();
        posY                        = lastPosY  = shape.getY();
        originalX                   = targetX   = posX;
        originalY                   = targetY   = posY;
        speed                       = 1.7f;
        moving                      = false;
        movementDirection           = Direction.DOWN;
        movementInterpolation       = 0.0f;

        switch(playerID) {

            // loading data from player_0
            case PLAYER_1: {

                Image[] up = {
                        new Image("res/visuals/classic/players/00/Bman_B_f01.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f02.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f03.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f04.png"),
                        new Image("res/visuals/classic/players/00/Bman_B_f00.png"),
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

            case PLAYER_2:
                break;

            default:
                break;
        }

        image = animation_down.getImage(0);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {

        float interpolate = this.gameContainer.getRenderInterpolation();

        g.setColor(Color.green);
        g.draw(shape);
        image.draw((posX - lastPosX) * interpolate + lastPosX, (posY - lastPosY) * interpolate + lastPosY - shape.getHeight() - 15);
    }

    public void update(GameContainer container, int delta) throws SlickException {

        float deltaInSecs = (float)delta * 0.001f;
        lastPosX = posX;
        lastPosY = posY;

        this.movementManager.update();
        direction = this.movementManager.getDirection();

        // stopped
        if (!moving) {

            movementInterpolation = 0.0f;

            switch(direction) {

                case UP:
                    originalY = posY;
                    targetY = originalY - 64f;
                    targetX = originalX = posX;
                    moving = true;
                    movementDirection = Direction.UP;
                    animation_actual = animation_up;
                    animation_actual.restart();
                    break;

                case DOWN:
                    originalY = posY;
                    targetY = originalY + 64f;
                    targetX = originalX = posX;
                    moving = true;
                    movementDirection = Direction.DOWN;
                    animation_actual = animation_down;
                    animation_actual.restart();
                    break;

                case LEFT:
                    originalX = posX;
                    targetX = originalX - 64f;
                    targetY = originalY = posY;
                    moving = true;
                    movementDirection = Direction.LEFT;
                    animation_actual = animation_left;
                    animation_actual.restart();
                    break;

                case RIGHT:
                    originalX = posX;
                    targetX = originalX + 64f;
                    targetY = originalY = posY;
                    moving = true;
                    movementDirection = Direction.RIGHT;
                    animation_actual = animation_right;
                    animation_actual.restart();
                    break;

                default:
                    break;
            }
        }


        // currently moving between tiles?
        if (moving) {
            movementInterpolation += speed * deltaInSecs;

            // move in opposite direction?
            if (checkOppositeMovement()) {
                float tempX, tempY;
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

                    switch(direction) {

                        case UP:
                            originalY = targetY;
                            targetY = originalY - 64f;
                            targetX = originalX = posX;
                            break;

                        case DOWN:
                            originalY = targetY;
                            targetY = originalY + 64f;
                            targetX = originalX = posX;
                            break;

                        case LEFT:
                            originalX = targetX;
                            targetX = originalX - 64f;
                            targetY = originalY = posY;
                            break;

                        case RIGHT:
                            originalX = targetX;
                            targetX = originalX + 64f;
                            targetY = originalY = posY;
                            break;

                        default:
                            break;
                    }

                    movementInterpolation = movementInterpolation % 1.0f;
                }
                // or stop?
                else {
                    movementInterpolation = 1.0f;
                    moving = false;
                }
            }

            //  check if target tile is blocked
            if (map.isBlocked((int)targetX, (int)targetY)) {
                targetX = originalX;
                targetY = originalY;
                moving = false;
            }

            // interpolate x and y coordinates
            posX = lerp(originalX, targetX, movementInterpolation);
            posY = lerp(originalY, targetY, movementInterpolation);

        }

        if (moving) {
            image = animation_actual.getCurrentFrame();
            animation_actual.update(delta);
        }
        else {
            switch(movementDirection) {

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

        if (movementInterpolation >= 0.5f) {
            shape.setLocation(targetX, targetY);
        }
    }

    /**
     * linear interpolation between two points
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
     * @return true if so false if not
     */
    private boolean checkOppositeMovement() {

        boolean value = false;

        if (moving && movementDirection != null && direction != null) {

            switch(movementDirection) {

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
        return false;
    }
}
