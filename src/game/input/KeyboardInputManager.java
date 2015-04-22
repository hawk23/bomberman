package game.input;

import game.config.InputConfiguration;
import org.newdawn.slick.Input;

public class KeyboardInputManager extends InputManager
{
    private InputConfiguration  inputConfiguration  = null;
    private Direction           directionPressed    = null;
    private int                 lastKeyPressed      = -1;

    public KeyboardInputManager(Input input, InputConfiguration inputConfiguration)
    {
        super(input);

        this.inputConfiguration = inputConfiguration;
    }

    @Override
    public void update() {
        // Direction ?
        if (input.isKeyPressed(inputConfiguration.getUp())) {
            lastKeyPressed = inputConfiguration.getUp();
            directionPressed = Direction.UP;
        }
        if (input.isKeyPressed(inputConfiguration.getDown())) {
            lastKeyPressed = inputConfiguration.getDown();
            directionPressed = Direction.DOWN;
        }
        if (input.isKeyPressed(inputConfiguration.getLeft())) {
            lastKeyPressed = inputConfiguration.getLeft();
            directionPressed = Direction.LEFT;
        }
        if (input.isKeyPressed(inputConfiguration.getRight())) {
            lastKeyPressed = inputConfiguration.getRight();
            directionPressed = Direction.RIGHT;
        }

        if (!input.isKeyDown(inputConfiguration.getUp()) || !input.isKeyDown(inputConfiguration.getDown())
                || !input.isKeyDown(inputConfiguration.getLeft()) || !input.isKeyDown(inputConfiguration.getRight())) {
            direction = Direction.NO_DIRECTION;

            if (lastKeyPressed != -1 && input.isKeyDown(lastKeyPressed)) {
                direction = directionPressed;
            }
            else {

                if (input.isKeyDown(inputConfiguration.getUp())) {
                    direction = Direction.UP;
                }
                else if (input.isKeyDown(inputConfiguration.getDown())) {
                    direction = Direction.DOWN;
                }
                else if (input.isKeyDown(inputConfiguration.getLeft())) {
                    direction = Direction.LEFT;
                }
                else if (input.isKeyDown(inputConfiguration.getRight())) {
                    direction = Direction.RIGHT;
                }

            }
        }

        // Drop Key pressed?
        bombDrop = false;

        if (input.isKeyPressed(inputConfiguration.getDrop())) {
            bombDrop = true;
        }
    }
}
