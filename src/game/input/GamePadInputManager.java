package game.input;

import org.newdawn.slick.Input;

public class GamePadInputManager extends InputManager
{
    private int controllerID;

    public GamePadInputManager(Input input, int controllerID)
    {
        super(input);

        this.controllerID = controllerID;
    }

    @Override
    public void update()
    {
        direction = Direction.NO_DIRECTION;

        if (input.isControllerLeft(this.controllerID))
        {
            direction = Direction.LEFT;
        }
        if (input.isControllerRight(this.controllerID))
        {
            direction = Direction.RIGHT;
        }
        if (input.isControllerUp(this.controllerID))
        {
            direction = Direction.UP;
        }
        if (input.isControllerDown(this.controllerID))
        {
            direction = Direction.DOWN;
        }

        // Drop Key pressed?
        bombDrop = false;

        if (input.isControlPressed(5, this.controllerID))
        {
            bombDrop = true;
        }
    }
}
