package game.state;

import game.BombermanGame;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

/**
 * Created by Mario on 30.03.2015.
 */
public class MainMenuState extends BombermanGameState
{
    private static final int        NUMBERCHOICES           = 3;
    private static final int        START_GAME              = 0;
    private static final int        OPTIONS                 = 1;
    private static final int        QUIT                    = 2;

    private int                     playersChoice           = 0;
    private Font                    font;
    private TrueTypeFont            playerOptionsFont;
    private String[]                playerOptions;
    private boolean                 exit                    = false;
    private Color                   notChosen               = new Color(153, 204, 255);
    private int                     yPos                    = 400;
    private int                     xPos                    = 300;

    public MainMenuState () {
        super(BombermanGameState.MAIN_MENU);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.font                       = new Font("Arial", Font.BOLD, 40);
        this.playerOptionsFont          = new TrueTypeFont(this.font, true);

        this.playerOptions              = new String[NUMBERCHOICES];
        this.playerOptions[START_GAME]  = "Start Game";
        this.playerOptions[OPTIONS]     = "Options";
        this.playerOptions[QUIT]        = "Quit";


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        renderPlayersOptions();
        if (exit) {
            gameContainer.exit();
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (playersChoice == (NUMBERCHOICES - 1)) {
                playersChoice = 0;
            } else {
                playersChoice++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            if (playersChoice == 0) {
                playersChoice = NUMBERCHOICES - 1;
            } else {
                playersChoice--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (playersChoice) {
                case QUIT:
                    exit = true;
                    break;
                case START_GAME:
                    stateBasedGame.enterState(BombermanGameState.GAME_ROUND);
                    break;
            }
        }
    }

    private void renderPlayersOptions() {
        for (int i = 0; i < NUMBERCHOICES; i++) {
            if (playersChoice == i) {
                this.playerOptionsFont.drawString(this.xPos, i * 50 + this.yPos, playerOptions[i]);
            } else {
                this.playerOptionsFont.drawString(this.xPos, i * 50 + this.yPos, playerOptions[i], notChosen);
            }
        }
    }
}
