package game.config;

import org.newdawn.slick.Image;

public class InputConfiguration {
    private int up      =   -1;
    private int down    =   -1;
    private int left    =   -1;
    private int right   =   -1;
    private int drop    =   -1;
    
    private int controllerID = -1;
    private Image image;

    public InputConfiguration () {

    }

    public InputConfiguration (int up, int down, int left, int right, int drop) {
        this.up     = up;
        this.down   = down;
        this.left   = left;
        this.right  = right;
        this.drop   = drop;
    }
    

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getDrop() {
        return drop;
    }

    public void setDrop(int drop) {
        this.drop = drop;
    }

	public int getControllerID() {
		return controllerID;
	}

	public void setControllerID(int controllerID) {
		this.controllerID = controllerID;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
