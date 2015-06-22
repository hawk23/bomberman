package slick.extension;

import game.config.GameSettings;
import game.interfaces.IDestroyable;
import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;
import game.model.FlamePoint;
import game.model.objects.Explosion;
import game.model.objects.GameObject;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import java.util.LinkedList;

/**
 * Created by Roland Schreier on 22.04.2015.
 */
public class CrackleSystem implements IUpdateable, IRenderable{

    private static final String explosionConfig =  "res/visuals/particles/crackle.xml";

    private ParticleSystem effectSystem;
    private ParticleEmitter crackleEmitter;

    public CrackleSystem() {
        try {
            effectSystem = ParticleIO.loadConfiguredSystem(explosionConfig);
            crackleEmitter = effectSystem.getEmitter(0);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        crackleEmitter.setEnabled(false);
        effectSystem.setRemoveCompletedEmitters(true);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        effectSystem.update(delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        effectSystem.render();
    }

    public void addCrackle(int tileX,int tileY){
        ConfigurableEmitter crackle = ((ConfigurableEmitter)crackleEmitter).duplicate(); // copy initial emitter

        crackle.setPosition(tileX*GameSettings.TILE_WIDTH+GameSettings.TILE_WIDTH/2, tileY*GameSettings.TILE_HEIGHT+GameSettings.TILE_HEIGHT/2, false);
        effectSystem.addEmitter(crackle); // add to particle system for rendering and updating
        crackle.setEnabled(true);
    }
}
