package game.options;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;


public class OptionLayer {

	private OptionScreen optionScreen;
	private ArrayList<Option> options;
	private boolean visible;
	private int optionIndex;
	private Image heading;
	private int headingPosX;
	private int headingPosY;
	
	public OptionLayer(OptionScreen optionScreen) {
		
		options = new ArrayList<Option>();
		this.optionScreen = optionScreen;
		optionIndex = 0;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		if (heading != null) {
			heading.draw(headingPosX, headingPosY);
		}
		
		for (Option o : options) {
			if (o != null) {
				o.getRenderImage().draw(o.getPosX(), o.getPosY());
			}	
		}
		
	}
	
	public void addOption(Option option) {
		options.add(option);
	}
	
	public void nextOption() {
		if (getSize() > 0 && optionIndex + 1 < getSize()) {
			optionIndex++;
			for (Option o : options) {
				if (o != null) {
					o.setSelected(false);
				}
			}
			if (options.get(optionIndex) != null) {
				options.get(optionIndex).setSelected(true);
			}
		}
	}
	
	public void lastOption() {
		if (getSize() > 0 && optionIndex - 1 >= 0) {
			optionIndex--;
			for (Option o : options) {
				if (o != null) {
					o.setSelected(false);
				}
			}
			if (options.get(optionIndex) != null) {
				options.get(optionIndex).setSelected(true);
			}
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
	
	private int getSize() {
		return options.size();
	}
	
	public void resetOptionLayer() {
		optionIndex = 0;
		if (getSize() > 0) {
			for (Option o : options) {
				if (o != null) {
					o.setSelected(false);
				}
			}
			if (options.get(optionIndex) != null) {
				options.get(optionIndex).setSelected(true);
			}
		}
	}
	
	public Option getOption(int index) {
		return options.get(index);
	}

	public void setHeading(Image heading, int posX, int posY) {
		this.heading = heading;
		this.headingPosX = posX;
		this.headingPosY = posY;
	}

	public int getOptionIndex() {
		return optionIndex;
	}
}
