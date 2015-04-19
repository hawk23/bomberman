package game.model;

import game.event.ExplosionEvent;

import java.util.EventListener;

public interface ExplosionListener extends EventListener
{
    void exploded(ExplosionEvent e);
}
