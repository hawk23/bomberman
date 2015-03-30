package game.model;

/**
 * Created by Mario on 30.03.2015.
 */
public class Player extends GameObject implements IDestroyable {
    protected InputConfiguration inputConfiguration;

    public Player () {
        this.collides       = true;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        this.inputConfiguration = inputConfiguration;
    }
}
