package slick.extension;
import game.model.Direction;
import game.model.InputConfiguration;
import org.newdawn.slick.Input;

/**
 * 
 * @author Albert
 * 
 * smooth movement manager for arrow keys (up, down, left, right)
 */
public class MovementManager_Arrow extends MovementManager {

	private Direction direction;
	private Direction directionPressed;
	private int lastKeyPressed = -1;
	
	public MovementManager_Arrow(Input input, InputConfiguration inputConfiguration) {
		super(input, inputConfiguration);
	}

	public void update() {
		
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
	}

	public Direction getDirection() {
		return direction;
	}
}
