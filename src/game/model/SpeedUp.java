package game.model;

public class SpeedUp extends PowerUpItem
{
    private static final String speedUPImagePath	= "res/visuals/powerups/PUspeed.png";
	private float 				value				= 0.2f;
	
	public SpeedUp(int posX, int posY)
	{
		super(posX, posY, speedUPImagePath);
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
