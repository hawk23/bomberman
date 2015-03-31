package slick.extension;

import game.model.Direction;
import game.model.InputConfiguration;
import org.newdawn.slick.Input;

/**
 * 
 * @author Albert
 * abstract movement-manager
 */
public abstract class MovementManager {
	
	protected Input input;
    protected InputConfiguration inputConfiguration;
	
	/**
	 * 
	 * @param input of slick game container
	 */
	public MovementManager(Input input, InputConfiguration inputConfiguration) {
		this.input = input;
        this.inputConfiguration = inputConfiguration;
	}
	
	/**
	 * this method should be called each update to get the current pressed key
	 */
	public abstract void update();
	
	/**
	 * 
	 * @return current pressed Direction
	 */
	public abstract Direction getDirection();
}
