package game.model.objects;

import game.config.GameSettings;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class Spike extends GameObject {

	private static final String 			crumblingSoundPath      = "res/sounds/spike/crumbling.ogg";
	private Sound 							crumblingSound;
	private static final String				soilImagePath			= "res/visuals/spike/soilSheet.png";
    private static final String             spikeImagePath 			= "res/visuals/spike/spike.png";
    private static final int	            soilAnimationInterval	= 100;
    private static final int 				spikeAnimationInterval	= 40;
    private SpriteSheet			            spikeSheet;
    private Animation			            spikeAnimation;
    private SpriteSheet						soilSheet;
    private Animation						soilAnimation;
    private float							notDeadlyTime = 1_000;
	private boolean 						deadly;
	
	public Spike(int tileX, int tileY) {
		super(tileX, tileY);
		this.deadly = false;
		loadAnimation();
		loadSound();
		setImage(spikeImagePath);
	}
	
	private void loadSound() {
		try {
			this.crumblingSound = new Sound(crumblingSoundPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		if (!deadly) {
			if (!crumblingSound.playing()) {
				//crumblingSound.play();
			}
			if (soilAnimation != null) {
				soilAnimation.draw(this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
			}
		}
		else {
			if (spikeAnimation == null) {
				g.drawImage(image, this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT - GameSettings.TILE_HEIGHT);
			}
			else {
				spikeAnimation.draw(this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
			}
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
//        	spikeSheet		= new SpriteSheet(spikeImagePath, 64, 64);
//          spikeAnimation	= new Animation(spikeSheet, spikeAnimationInterval);
        	soilSheet		= new SpriteSheet(soilImagePath, 64, 64);
        	soilAnimation	= new Animation(soilSheet, soilAnimationInterval);
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
