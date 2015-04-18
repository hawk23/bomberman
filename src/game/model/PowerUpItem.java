package game.model;

import game.config.GameSettings;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class PowerUpItem extends GameObject
{
    public PowerUpItem(int posX, int posY)
    {
        super(posX, posY);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        g.drawImage(this.image, this.tileX * GameSettings.TILE_WIDTH, this.tileY * GameSettings.TILE_HEIGHT);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
    {

    }
}
