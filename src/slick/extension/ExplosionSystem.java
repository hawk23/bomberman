package slick.extension;

import game.config.GameSettings;
import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;
import game.model.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.ParticleIO;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Created by MrMeister on 17.04.2015.
 */
public class ExplosionSystem implements IRenderable, IUpdateable {


    private static final String explosionConfig =  "res/visuals/particles/explosionSystem.xml";
    private static final float directionSpread =75;

    private static final int delay=200;

    private LinkedList<DelayedExplosion> explosionQue;
    private LinkedList<GameObject> objects;

    private ParticleSystem effectSystem;
    private ParticleEmitter flameEmitter,smokeEmitter,sparkEmitter;

    public ExplosionSystem() {
        try {
            effectSystem = ParticleIO.loadConfiguredSystem(explosionConfig);
            flameEmitter = effectSystem.getEmitter(0);
            smokeEmitter = effectSystem.getEmitter(1);
            sparkEmitter = effectSystem.getEmitter(2);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        flameEmitter.setEnabled(false);
        smokeEmitter.setEnabled(false);
        sparkEmitter.setEnabled(false);
        effectSystem.setRemoveCompletedEmitters(true);

        explosionQue = new LinkedList<DelayedExplosion>();
        objects = new LinkedList<GameObject>();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

        for(DelayedExplosion exp:explosionQue){
            exp.setDelay(exp.getDelay()-delta);
            if(exp.getDelay()<=0)
                exp.getEmitter().setEnabled(true);
        }

        for(GameObject obj:objects){
            obj.update(container,game,delta);
        }
        effectSystem.update(delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        for(GameObject obj:objects){
            obj.render(container,game,g);
        }

        effectSystem.render();
    }

    public void addExplosion(Explosion explosion){
         for(FlamePoint flamePoint:explosion.getFlamePositions()) {
             ConfigurableEmitter eFlame = ((ConfigurableEmitter)flameEmitter).duplicate(); // copy initial emitter
             ConfigurableEmitter eSmoke = ((ConfigurableEmitter)smokeEmitter).duplicate(); // copy initial emitter
             ConfigurableEmitter eSpark = ((ConfigurableEmitter)sparkEmitter).duplicate(); // copy initial emitter

             ConfigurableEmitter emitters[]={eFlame,eSmoke};

             int offsetX=0, offsetY=0, emitterWidth=0, emitterHeight=0;
             float spread=0;
             float angle=0;

             //Use the timer of the explosion for the particle life
             int timer = explosion.getTimer();
             int range = timer/70;


             offsetX= GameSettings.TILE_WIDTH/2;
             offsetY= GameSettings.TILE_HEIGHT/2;

             switch(flamePoint.getDirection()){
                 case DOWN:
                     spread=directionSpread;
                     angle=180F;
                     emitterWidth= GameSettings.TILE_WIDTH/3;
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     break;

                 case RIGHT:
                     emitterWidth= GameSettings.TILE_WIDTH/2;
                     emitterHeight= GameSettings.TILE_HEIGHT/3;
                     spread=directionSpread;
                     angle=90F;
                     break;

                 case UP:
                     emitterWidth= GameSettings.TILE_WIDTH/3;
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     spread=directionSpread;
                     angle=0;
                     break;

                 case LEFT:
                     spread=directionSpread;
                     emitterWidth= GameSettings.TILE_WIDTH/2;
                     emitterHeight= GameSettings.TILE_HEIGHT/3;
                     angle=-90F;
                     break;

                 case CENTER:
                     emitterWidth= GameSettings.TILE_WIDTH/2;
                     emitterHeight= GameSettings.TILE_HEIGHT/2;
                     spread=360F;
                     angle=0;
                     offsetX= GameSettings.TILE_WIDTH/2;
                     offsetY= GameSettings.TILE_HEIGHT/2;
                     eSpark.setPosition(flamePoint.x * GameSettings.TILE_WIDTH + offsetX, flamePoint.y * GameSettings.TILE_HEIGHT + offsetY, false);
                     effectSystem.addEmitter(eSpark);
                     eSpark.setEnabled(true);

                     break;

                 default:
                     break;

             }

             for(ConfigurableEmitter emitter:emitters){

                 emitter.yOffset.setMin(-emitterHeight / 2);
                 emitter.yOffset.setMax(emitterHeight / 2);
                 emitter.xOffset.setMin(-emitterWidth / 2);
                 emitter.xOffset.setMax(emitterWidth / 2);
                 emitter.spread.setValue(spread);
                 emitter.angularOffset.setValue(angle);

                 emitter.setPosition(flamePoint.x * GameSettings.TILE_WIDTH + offsetX, flamePoint.y * GameSettings.TILE_HEIGHT + offsetY, false);
                 effectSystem.addEmitter(emitter); // add to particle system for rendering and updating
                 this.explosionQue.add(new DelayedExplosion(emitter,flamePoint.getDistanceFromCenter()*delay));
             }
         }
    }
}