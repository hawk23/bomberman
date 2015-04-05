package game.input;

/**
 * Created by Mario on 30.03.2015.
 */
public class InputConfiguration {
    private int up;
    private int down;
    private int left;
    private int right;
    private int drop;

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
}
