package game.model;

import game.event.ExplosionEvent;

import java.util.EventListener;

/**
 * Created by Mario on 18.04.2015.
 */
public interface ExplosionListener extends EventListener
{
    void exploded(ExplosionEvent e);
}
