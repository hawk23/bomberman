package game.model;

import game.config.GameSettings;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class Spike extends GameObject {

    private static final String             spikeImagePath 		= "res/visuals/spike/spikeSheet.png";
    private static final int	            animationInterval	= 40;
    private SpriteSheet			            spikeSheet;
    private Animation			            animation;
    private float							notDeadlyTime = 1_000;
	private boolean 						deadly;
	
	public Spike(int tileX, int tileY) {
		super(tileX, tileY);
		this.deadly = false;
		loadAnimation();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		if (animation == null) {
			g.drawImage(image, this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
		}
		else {
			animation.draw(this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (notDeadlyTime >= 0) {
			notDeadlyTime -= delta;
		}
		else {
			deadly = true;
		}
	}
	
	private void loadAnimation()
    {
        try
        {
        	spikeSheet		= new SpriteSheet(spikeImagePath, 64, 64);
            animation		= new Animation(spikeSheet, animationInterval);
        }
        catch (SlickException e)
        {
            //TODO
        }
    }
	
	public boolean isDeadly() {
		return this.deadly;
	}

}
