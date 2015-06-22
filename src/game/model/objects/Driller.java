package game.model.objects;

import game.config.GameSettings;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class Driller extends GameObject {

	private static final String 			crumblingSoundPath      = "res/sounds/driller/crumbling.ogg";
	private Sound 							crumblingSound;
	
	private static final String				soilSheetPath			= "res/visuals/driller/soilSheet.png";
    private static final String             drillerSheetPath 		= "";
    
    private static final int	            soilAnimationInterval	= 40;
    private static final int 				drillAnimationInterval	= 40;
    
    private SpriteSheet			            drillerSheet;
    private Animation			            drillerAnimation;   
    private SpriteSheet						soilSheet;
    private Animation						soilAnimation;
    
    private float							notDeadlyTime = 1_000;
	private boolean 						deadly;
	
	public Driller(int tileX, int tileY) {
		super(tileX, tileY);
		this.deadly = false;
		loadAnimation();
		loadSound();
		//setImage(spikeImagePath);
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
		
		if (!this.deadly) {
			if (!this.crumblingSound.playing()) {
				//crumblingSound.play();
			}
			this.soilAnimation.draw(this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
		}
		else {
			if (this.crumblingSound.playing()) {
				crumblingSound.stop();
			}
			this.image.draw(this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (this.notDeadlyTime >= 0) {
			this.notDeadlyTime -= delta;
		}
		else {
			this.deadly = true;
		}
	}
	
	private void loadAnimation()
    {
        try
        {
//        	drillerSheet		= new SpriteSheet(drillerSheetPath, 64, 64);
//        	drillerAnimation	= new Animation(drillerSheet, drillAnimationInterval);
        	soilSheet			= new SpriteSheet(soilSheetPath, 64, 64);
        	soilAnimation		= new Animation(soilSheet, soilAnimationInterval);

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
