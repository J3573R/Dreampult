package fi.tamk.dreampult;

/**
 * Created by Clown on 15.3.2016.
 */
public class Collection {
    private boolean gameIsOn;
    private boolean PauseMenu;
    private boolean ScoreScreen;
    private boolean talentScreen;

    final public int SCREEN_WIDTH = 24;
    final public int SCREEN_HEIGHT = 14;

    final public int SCREEN_WIDTH_CENTER  = SCREEN_WIDTH / 2;
    final public int SCREEN_HEIGHT_CENTER = SCREEN_HEIGHT / 2;

    final public int REAL_WIDTH = 960;
    final public int REAL_HEIGHT = 540;

    public final int UP = 1;
    public final int DOWN = 2;

    public boolean launch = false;

    private boolean soundIsOn;

    public Collection() {
        gameIsOn = false;
        soundIsOn = true;
        PauseMenu = false;
        talentScreen = false;
    }

    public void start() {
        gameIsOn = true;
    }
    public void pause() {
        gameIsOn = false;
    }

    public void soundOn() {
        soundIsOn = true;
    }
    public void soundOff() {
        soundIsOn = false;
    }

    public boolean isGameOn() {
        return gameIsOn;
    }

    public boolean isSoundOn() {
        return soundIsOn;
    }

    public boolean isPauseMenu() {
        return PauseMenu;
    }

    public void showPauseMenu() {
        this.PauseMenu = true;
    }
    public void hidePauseMenu() {
        this.PauseMenu = false;
    }

    public boolean isScoreScreen() {return  ScoreScreen; }

    public void showScoreScreen() {
        this.ScoreScreen = true;
    }
    public void hideScoreScreen() {
        this.ScoreScreen = false;
    }

    public boolean isTalentScreen() {
        return talentScreen;
    }

    public void showTalentScreen() {
        this.talentScreen = true;
    }

    public void hideTalentScreen() {
        this.talentScreen = false;
    }
}
