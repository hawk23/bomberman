package slick.extension;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;


/**
 * 
 * @author Albert
 * 
 * fullscreen AppGameContainer
 */
public class AppGameContainerFSCustom extends AppGameContainer {

	public static final int UPDATES_PER_SECOND          = 40;
	public static final int UPDATE_RATE                 = 1_000 / UPDATES_PER_SECOND;
	public static final int TARGET_FPS                  = 100;
	public static final int GAME_CANVAS_WIDTH			= 1280;
	public static final int GAME_CANVAS_HEIGHT			= 960;
	
	// there will be a native back buffer depending on the actual aspect ratio of the used display
	// and a buffer for the game canvas - will always be 1280x960
	// native buffer depends on actual aspect ratio:
	// 4:3 --> 1280x960
	// 5:4 --> 1280x1024
	// 16:9 --> 1920x1080
	// 16:10 --> 1920x1200
	//
	// supported aspect ratios are:
	public static final int RATIO_4_3                   = 0;
	public static final int RATIO_5_4                   = 1;
	public static final int RATIO_16_9                  = 2;
	public static final int RATIO_16_10                 = 3;
	
	/**
	 * the aspect ratio of the used display
	 */
	private int actualRatio;
	/**
	 * back-buffer for the game canvas
	 */
	private Image buffer;
	/**
	 * graphics object for game canvas
	 */
	private Graphics buffer_graphics;
	/**
	 * the back-buffer depending on aspect ratio
	 */
	private Image buffer_native;
	/**
	 * graphics object of the native buffer image
	 */
	private Graphics buffer_native_graphics;
	/**
	 * image used to scale to actual Display Mode
	 */
	private Image tmp_buffer;
	
	/**
	 * should data be displayed? Key: F12
	 */
	private boolean     showGameLoopData    = false;
	
	// game update data
	private long        recordedUPS;
	private long        lastUPS;
	private long        ups;
	
	
	
	public AppGameContainerFSCustom(Game game) throws SlickException {
		super(game, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), true);
        maximumLogicInterval    = UPDATE_RATE;
		minimumLogicInterval    = UPDATE_RATE;
		targetFPS               = TARGET_FPS;
	}
	
	/**
	 * Update and render the game
	 * 
	 * @param delta The change in time since last update and render
	 * @throws SlickException Indicates an internal fault to the game.
	 */
	@Override
	protected void updateAndRender(int delta) throws SlickException {
		
		if (smoothDeltas) {
			if (getFPS() != 0) {
				delta = 1000 / getFPS();
			}
		}
		
		input.poll(width, height);
	
		Music.poll(delta);
		if (!paused) {
			storedDelta += delta;
			
			if (storedDelta >= minimumLogicInterval) {
				try {
					
					// check if F12 is pressed to toggle game-loop data
					if (getInput().isKeyPressed(Input.KEY_F12)) {
						if (showGameLoopData) {
							showGameLoopData = false;
						}
						else {
							showGameLoopData = true;
						}
					}
					
					if (maximumLogicInterval != 0) {
						long cycles = storedDelta / maximumLogicInterval;
						for (int i=0;i<cycles;i++) {
							game.update(this, (int) maximumLogicInterval);
							updateUPS();
						}
						
						int remainder = (int) (storedDelta % maximumLogicInterval);
						if (remainder > minimumLogicInterval) {
							game.update(this, (int) (remainder % maximumLogicInterval));
							updateUPS();
							storedDelta = 0;
						} else {
							storedDelta = remainder;
						}
					} else {
						game.update(this, (int) storedDelta);
						updateUPS();
						storedDelta = 0;
					}
					
				} catch (Throwable e) {
					Log.error(e);
					throw new SlickException("Game.update() failure - check the game code.");
				}
			}
		} else {
			game.update(this, 0);
		}
		
		if (hasFocus() || getAlwaysRender()) {
			if (clearEachFrame) {
				GL.glClear(SGL.GL_COLOR_BUFFER_BIT | SGL.GL_DEPTH_BUFFER_BIT);
			} 
			
			GL.glLoadIdentity();
			
			getGraphics().resetTransform();
			getGraphics().resetFont();
			getGraphics().resetLineWidth();
			getGraphics().setAntiAlias(false);
			
			resetNativeBuffer();
			resetGameBuffer();
			try {
				game.render(this, buffer_graphics);
			} catch (Throwable e) {
				Log.error(e);
				throw new SlickException("Game.render() failure - check the game code.");
			}
			buffer_graphics.flush();
			
			// draw the game canvas in the center of the native back buffer
			buffer_native_graphics.drawImage(buffer, (buffer_native.getWidth() - buffer.getWidth()) / 2, 
					(buffer_native.getHeight() - buffer.getHeight()) / 2);
			
			buffer_native_graphics.flush();
			
			// scale to actual screen resolution
			tmp_buffer = buffer_native.getScaledCopy(this.getWidth(), this.getHeight());
			
			getGraphics().drawImage(tmp_buffer, 0, 0);
			getGraphics().resetTransform();
			
			if (showGameLoopData) {
				Color tmp = getGraphics().getColor();
				getGraphics().setColor(Color.green);
				getGraphics().drawString("FPS: "+recordedFPS, 10, 10);
				getGraphics().drawString("UPS: "+recordedUPS, 10, 25);
				getGraphics().setColor(tmp);
			}	
			GL.flush();
		}
		
		if (targetFPS != -1) {
			Display.sync(targetFPS);
		}
	}
	
	@Override
	protected void setup() throws SlickException {
		super.setup();
		
		boolean ratio_supported = setAspectRatio(new Dimension(getWidth(), getHeight()));
		
		if (ratio_supported) {
			
			switch (actualRatio) {
			
				case RATIO_4_3:
					buffer_native = new Image(1280, 960);
					buffer_native_graphics = buffer_native.getGraphics();
					break;
					
				case RATIO_5_4:
					buffer_native = new Image(1280, 1024);
					buffer_native_graphics = buffer_native.getGraphics();
					break;
					
				case RATIO_16_9:
					buffer_native = new Image(1920, 1080);
					buffer_native_graphics = buffer_native.getGraphics();
					break;
					
				case RATIO_16_10:
					buffer_native = new Image(1920, 1200);
					buffer_native_graphics = buffer_native.getGraphics();
					break;
			}
			
			buffer_native.setFilter(Image.FILTER_NEAREST);
			resetNativeBuffer();
			
			
			// this will be the back buffer image we draw our game onto
			buffer              = new Image(GAME_CANVAS_WIDTH, GAME_CANVAS_HEIGHT);
			buffer_graphics     = buffer.getGraphics();
			resetGameBuffer();
		}
		else {
			// ratio is not supported
			// ToDo Error message
			exit();
		}
	}
	
	private void resetGameBuffer() {
		buffer_graphics.clear();
		buffer_graphics.setColor(Color.red);
		buffer_graphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());		
	}
	
	private void resetNativeBuffer() {
		buffer_native_graphics.clear();
		buffer_native_graphics.setColor(Color.black);
		buffer_native_graphics.fillRect(0, 0, buffer_native.getWidth(), buffer_native.getHeight());
	}
	
	/**
	 * checks if the used display resolution fits to the supported aspect ratios
	 * @param d = display resolution to to check
	 * @return boolean
	 */
	private boolean setAspectRatio(Dimension d) {
		boolean value = false;
		
		float ratio = (float)d.getWidth() / (float)d.getHeight();
		float ratio_4_3 = (float)4 / (float)3;
		float ratio_5_4 = (float)5 / (float)4;
		float ratio_16_9 = (float)16 / (float)9;
		float ratio_16_10 = (float)16 / (float)10;
		
		if (ratio == ratio_4_3) {
			actualRatio = RATIO_4_3;
			value = true;
		}
		else if (ratio == ratio_5_4) {
			actualRatio = RATIO_5_4;
			value = true;
		}
		else if (ratio == ratio_16_9) {
			actualRatio = RATIO_16_9;
			value = true;
		}
		else if (ratio == ratio_16_10) {
			actualRatio = RATIO_16_10;
			value = true;
		}
		
		return value;
	}
	
	/**
	 * update UPS counter
	 */
	private void updateUPS() {
		if (getTime() - lastUPS >= 1000) {
			lastUPS = getTime();
			recordedUPS = ups;
			ups = 0;
		}
		ups++;
	}
	
	/**
	 * Updated the FPS counter
	 */
	protected void updateFPS() {
		if (getTime() - lastFPS >= 1000) {
			lastFPS = getTime();
			recordedFPS = fps;
			fps = 0;
		}
		fps++;
	}
	
	/**
	 * 
	 * @return the interpolation value (0.0 to 1.0) for rendering
	 */
	public float getRenderInterpolation() {
		return (float)storedDelta / (float)UPDATE_RATE;
	}
}
