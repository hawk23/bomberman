package slick.extension;

import game.interfaces.IDestroyable;
import game.interfaces.IRenderable;
import game.interfaces.IUpdateable;
import game.model.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Created by Roland Schreier on 22.04.2015.
 * TODO: currently not working
 */
public class NukeSystem extends GameObject{

    private static final String nukeConfig =  "res/visuals/particles/explosionSystem.xml";

    private ParticleSystem effectSystem;
    private ConfigurableEmitter flameEmitter,smokeEmitter,sparkEmitter;

    private float posX,posY;

    private float moveUp=0.5f;


    public NukeSystem(float posX, float posY) {
        super();

        System.out.println("added "+posX+","+posY);
        this.posX=posX;
        this.posY=posY;

        try {
            effectSystem = ParticleIO.loadConfiguredSystem(nukeConfig);
            flameEmitter = (ConfigurableEmitter)effectSystem.getEmitter(0);
            smokeEmitter = (ConfigurableEmitter)effectSystem.getEmitter(1);
            sparkEmitter = (ConfigurableEmitter)effectSystem.getEmitter(2);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

        flameEmitter.setPosition(posX,posY,false);
        smokeEmitter.setPosition(posX,posY,false);
        sparkEmitter.setPosition(posX, posY, false);

        flameEmitter.setEnabled(true);
        smokeEmitter.setEnabled(true);
        sparkEmitter.setEnabled(true);


        effectSystem.addEmitter(flameEmitter);
        effectSystem.addEmitter(smokeEmitter);
        effectSystem.addEmitter(sparkEmitter);
        effectSystem.setRemoveCompletedEmitters(true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        effectSystem.render();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        float y = flameEmitter.getY();

        flameEmitter.setPosition(posX,y+delta*moveUp);
        smokeEmitter.setPosition(posX,y+delta*moveUp);
        effectSystem.update(delta);
    }
}
