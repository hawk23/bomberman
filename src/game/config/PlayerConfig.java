package game.config;

public class PlayerConfig extends GameObjectConfig
{
    private int     initialBombLimit;
    private float   initialSpeedUp;
    private int 	initialBombTimer;
    private int 	initialBombRange;

    public PlayerConfig() {
        super();
    }

    public int getInitialBombLimit() {
        return initialBombLimit;
    }

    public void setInitialBombLimit(int initialBombLimit) {
        this.initialBombLimit = initialBombLimit;
    }

    public float getInitialSpeedUp() {
        return initialSpeedUp;
    }

    public void setInitialSpeedUp(float initialSpeed) {
        this.initialSpeedUp = initialSpeed;
    }

	public int getInitialBombTimer() {
		return initialBombTimer;
	}

	public void setInitialBombTimer(int initialBombTimer) {
		this.initialBombTimer = initialBombTimer;
	}

	public int getInitialBombRange() {
		return initialBombRange;
	}

	public void setInitialBombRange(int initialBombRange) {
		this.initialBombRange = initialBombRange;
	}
}
