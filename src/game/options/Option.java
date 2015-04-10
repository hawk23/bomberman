package game.options;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Option {

	private Image defaultImage;
	private Image selectedImage;
	private int posX;
	private int posY;
	private int width;
	private int height;
	private boolean selected;
	
	public Option(String path_defaultImage, String path_selectedImage) throws SlickException {
		
		defaultImage = new Image(path_defaultImage);
		selectedImage = new Image(path_selectedImage);
		width = defaultImage.getWidth();
		height = defaultImage.getHeight();		
		
	}
	
	public Image getRenderImage() {
		
		if (selected) {
			return selectedImage;
		}
		else {
			return defaultImage;
		}
	}

	public void setCoordinates(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
