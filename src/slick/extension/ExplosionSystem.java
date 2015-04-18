package slick.extension;

import game.config.GameSettings;
import game.model.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.ParticleIO;

import java.awt.*;
import java.io.File;

/**
 * Created by MrMeister on 17.04.2015.
 */
public class ExplosionSystem extends ParticleSystem implements IRenderable, IUpdateable {

    private static final String particlePath=    "res/visuals/particles/explosion.png";
    private static final String particleConfig=  "res/visuals/particles/explosion.xml";

    private static final float directionSpread =20;

    private ParticleEmitter explosionEmitter;

    public ExplosionSystem() {
        super(particlePath,1500);

        File xmlFile = new File(particleConfig);
        try {
            explosionEmitter = ParticleIO.loadEmitter(xmlFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        explosionEmitter.setEnabled(false);
        this.setRemoveCompletedEmitters(true);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render();
    }

    public void addExplosion(Explosion explosion){
         for(FlamePoint flamePoint:explosion.getFlamePositions()) {
             ConfigurableEmitter e = ((ConfigurableEmitter)explosionEmitter).duplicate(); // copy initial emitter

             int offsetX=0, offsetY=0, emitterWidth=0, emitterHeight=0;
             float spread=0;
             float angle=0;

             //Use the timer of the explosion for the particle life
             int timer = explosion.getTimer();
             int range = timer/70;

             e.initialLife.setMin((float) timer - range);
             e.initialLife.setMax((float) timer + range);
             e.initialLife.setEnabled(true);

             switch(flamePoint.getDirection()){
                 case DOWN:
                     spread=directionSpread;
                     angle=180F;
                     offsetX= GameSettings.TILE_WIDTH/2;
                     break;

                 case RIGHT:
                     offsetY= GameSettings.TILE_HEIGHT/2;
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     spread=directionSpread;
                     angle=90F;
                     break;

                 case UP:
                     emitterWidth= GameSettings.TILE_WIDTH/2;
                     offsetY= GameSettings.TILE_HEIGHT;
                     offsetX= GameSettings.TILE_WIDTH/2;
                     angle=0;
                     break;

                 case LEFT:
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     spread=directionSpread;
                     offsetY= GameSettings.TILE_HEIGHT/2;
                     offsetX=GameSettings.TILE_WIDTH;
                     angle=-90F;
                     break;

                 case CENTER:
                     emitterWidth= GameSettings.TILE_WIDTH/2;
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     spread=360F;
                     angle=0;
                     offsetX= GameSettings.TILE_WIDTH/2;
                     offsetY= GameSettings.TILE_HEIGHT/2;
                     break;

                 default:
                     break;

             }

             e.yOffset.setMin(-emitterHeight/2);
             e.yOffset.setMax(emitterHeight / 2);
             e.xOffset.setMin(-emitterWidth / 2);
             e.xOffset.setMax(emitterWidth / 2);
             e.spread.setValue(spread);
             e.angularOffset.setValue(angle);

             e.setPosition(flamePoint.x * GameSettings.TILE_WIDTH + offsetX, flamePoint.y * GameSettings.TILE_HEIGHT + offsetY, false);
             this.addEmitter(e); // add to particle system for rendering and updating
             e.setEnabled(true); // let the explosion begin
         }
    }
}