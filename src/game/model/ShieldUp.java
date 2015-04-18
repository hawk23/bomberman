package game.model;

public class ShieldUp extends PowerUpItem {

	int time = 10_000;
	
	public ShieldUp(int posX, int posY) {
		super(posX, posY);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
