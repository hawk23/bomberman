package slick.extension;

import game.model.IRenderable;
import game.model.IUpdateable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.ParticleIO;

import java.io.File;

/**
 * Created by MrMeister on 17.04.2015.
 */
public class ExplosionSystem extends ParticleSystem { //TODO implements IRenderable, IUpdateable {

    private static final String particlePath=    "res/visuals/particles/explosion.png";
    private static final String particleConfig=  "res/visuals/particles/explosion.xml";

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
    public void update( int delta) {
        super.update(delta);
    }

    @Override
    public void render() {
        super.render();
    }

    public void addExplosion(int posX, int posY){
        ConfigurableEmitter e = ((ConfigurableEmitter)explosionEmitter).duplicate(); // copy initial emitter
        e.setEnabled(true);
        e.setPosition(posX,posY);
        this.addEmitter(e); // add to particle system for rendering and updating
    }
}
