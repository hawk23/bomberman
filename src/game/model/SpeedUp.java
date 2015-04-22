package game.model;

import game.config.GameSettings;

public class SpeedUp extends PowerUpItem
{
    private static final String speedUPImagePath	= "res/visuals/powerups/PUspeed.png";
	private float 				value;
	
	public SpeedUp(int posX, int posY)
	{
		super(posX, posY, Explosion.timer, speedUPImagePath);
		this.value = GameSettings.SPEED_UP_VALUE;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

}
