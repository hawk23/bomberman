package game.input;

import game.config.InputConfiguration;
import org.newdawn.slick.Input;

/**
 * 
 * @author Albert
 * InputManager for the player objects - has an InputConfiguration (Keys for moving and key for dropping a bomb)
 */
public class InputManager {
	
	private Input input = null;
    private InputConfiguration inputConfiguration = null;
    private Direction direction = null;
	private Direction directionPressed = null;
	private int lastKeyPressed = -1;
	private boolean bombDrop = false;
    
    
	/**
	 * 
	 * @param input of slick game container and an input-configuration
	 */
	public InputManager(Input input, InputConfiguration inputConfiguration) {
		this.input = input;
        this.inputConfiguration = inputConfiguration;
	}
	
	/**
	 * this method should be called each update to get the currently pressed key
	 */
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
	
	/**
	 * 
	 * @return current pressed Direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @return true if drop key is pressed
	 */
	public boolean bombDrop() {
		return bombDrop;
	}
}