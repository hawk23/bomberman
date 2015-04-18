package game;

import game.model.IRenderable;
import game.model.IUpdateable;
import game.model.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Mario on 18.04.2015.
 */
public class PlayerStateScreen implements IRenderable
{
    private Player      player;
    private Image       image;
    private Graphics    g;

    private int         width       = 160;
    private int         heigth      = 480;
    private int         posX;
    private int         posY;

    protected AngelCodeFont font 			= BombermanGame.STEAMWRECK_FONT;
    protected AngelCodeFont fontOutline 	= BombermanGame.STEAMWRECK_FONT_OL;
    protected AngelCodeFont fontOutline2 	= BombermanGame.JAMES_FONT_OL;

    private Image		playerStatsBackground	= null;

    public PlayerStateScreen (Player player, int posX, int posY) throws SlickException
    {
        this.player                 = player;
        this.posX                   = posX;
        this.posY                   = posY;

        this.image                  = new Image(this.width, this.heigth);
        this.g                      = image.getGraphics();

        this.playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        this.resetGraphics();

        this.g.drawImage(playerStatsBackground, 0, 0);

        this.player.getPlayerConfig().getImage().draw(10.0f,10.0f,0.5f);

        this.g.scale(0.5f, 0.5f);

        this.font.drawString(20, 280, "Bombs: ");
        this.font.drawString(20, 380, "Dist: ");

        this.font.drawString(240, 280, Integer.toString(this.player.getBombLimit()));
        this.font.drawString(240, 380, Integer.toString(this.player.getBombRange()));
        
        this.g.flush();

        g.drawImage(this.image, this.posX, this.posY);
    }

    private void resetGraphics ()
    {
        this.g.setBackground(Color.black);
        this.g.clear();
    }
}