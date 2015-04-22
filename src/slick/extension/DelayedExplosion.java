package slick.extension;

import org.newdawn.slick.particles.ParticleEmitter;

/**
 * Created by Roland Schreier on 22.04.2015.
 */
public class DelayedExplosion {
    private ParticleEmitter emitter;
    private int             delay;

    public DelayedExplosion(ParticleEmitter emitter, int delay) {
        setEmitter(emitter);
        setDelay(delay);
    }

    public ParticleEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(ParticleEmitter emitter) {
        this.emitter = emitter;
        emitter.setEnabled(false);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
