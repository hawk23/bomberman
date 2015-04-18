package game.input;

import game.config.InputConfiguration;
import org.newdawn.slick.Input;

/**
 * 
 * @author Albert
 * InputManager for the player objects - has an InputConfiguration (Keys for moving and key for dropping a bomb)
 */
public abstract class InputManager {
	
	protected Input         input               = null;
    protected Direction     direction           = null;
    protected boolean       bombDrop            = false;
    
	/**
	 * 
	 * @param input of slick game container and an input-configuration
	 */
	public InputManager(Input input) {
		this.input = input;
	}
	
	/**
	 * this method should be called each update to get the currently pressed key
	 */
	public abstract void update();

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