package game;

import game.config.GameSettings;
import game.model.IRenderable;
import game.model.IUpdateable;
import game.model.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.text.DecimalFormat;

/**
 * Created by Mario on 18.04.2015.
 */
public class PlayerStateScreen implements IRenderable
{
    private Player              player;
    private Image               image;
    private Graphics            g;

    private int                 width       = 160;
    private int                 heigth      = 480;
    private int                 posX;
    private int                 posY;

    protected AngelCodeFont     font 			= BombermanGame.STEAMWRECK_FONT;
    protected AngelCodeFont     fontOutline 	= BombermanGame.STEAMWRECK_FONT_OL;
    protected AngelCodeFont     fontOutline2 	= BombermanGame.JAMES_FONT_OL;

    private SpriteSheet         pubomb;
    private SpriteSheet         puRange;
    private SpriteSheet         puShield;
    private SpriteSheet         puSpeed;

    private DecimalFormat       decimalFormat = new DecimalFormat("0.00");
    private Image		        playerStatsBackground	= null;

    public PlayerStateScreen (Player player, int posX, int posY) throws SlickException
    {
        this.player                 = player;
        this.posX                   = posX;
        this.posY                   = posY;

        this.image                  = new Image(this.width, this.heigth);
        this.g                      = image.getGraphics();

        this.playerStatsBackground 	= new Image("res/visuals/backgrounds/playerStats_background.png");

        this.pubomb                 = new SpriteSheet("res/visuals/powerups/PUbomb.png", GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
        this.puRange                = new SpriteSheet("res/visuals/powerups/PUrange.png", GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
        this.puShield               = new SpriteSheet("res/visuals/powerups/PUshield.png", GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
        this.puSpeed                = new SpriteSheet("res/visuals/powerups/PUspeed.png", GameSettings.TILE_WIDTH, GameSettings.TILE_HEIGHT);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        this.resetGraphics();

        this.g.drawImage(playerStatsBackground, 0, 0);

        this.player.getPlayerConfig().getImage().draw(0.0f,0.0f,0.625f);

        // draw icons
        this.g.drawImage(this.pubomb.getSprite(0,0),    5, 160);
        this.g.drawImage(this.puRange.getSprite(0,0),   5, 220);
        this.g.drawImage(this.puSpeed.getSprite(0,0),   5, 280);
        this.g.drawImage(this.puShield.getSprite(0,0),  5, 340);

        this.g.scale(0.5f, 0.5f);

        this.font.drawString(160, 320, Integer.toString(this.player.getBombLimit()));
        this.font.drawString(160, 440, Integer.toString(this.player.getBombRange()));
        this.font.drawString(160, 560, this.decimalFormat.format(this.player.getSpeed()));
        this.font.drawString(160, 680, Integer.toString(this.player.getShieldTimerSeconds()));

        this.g.flush();

        g.drawImage(this.image, this.posX, this.posY);
    }

    private void resetGraphics ()
    {
        this.g.setBackground(Color.black);
        this.g.clear();
    }
}