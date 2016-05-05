package fi.tamk.dreampult;

/**
 * @author Tommi Hagelberg
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

    /**
     * Constructs and sets default values.
     */
    public Collection() {
        gameIsOn = false;
        PauseMenu = false;
        talentScreen = false;
    }

    /**
     * Starts game.
     */
    public void start() {
        gameIsOn = true;
    }

    /**
     * Pauses game.
     */
    public void pause() {
        gameIsOn = false;
    }

    /**
     * @return Checks game state.
     */
    public boolean isGameOn() {
        return gameIsOn;
    }

    /**
     * @return Checks pause menu state.
     */
    public boolean isPauseMenu() {
        return PauseMenu;
    }

    /**
     * Shows pause menu.
     */
    public void showPauseMenu() {
        this.PauseMenu = true;
    }

    /**
     * Hides pause menu.
     */
    public void hidePauseMenu() {
        this.PauseMenu = false;
    }

    /**
     * @return Checks score screen state.
     */
    public boolean isScoreScreen() {return  ScoreScreen; }

    /**
     * Shows score screen.
     */
    public void showScoreScreen() {
        this.ScoreScreen = true;
    }

    /**
     * Hides score screen.
     */
    public void hideScoreScreen() {
        this.ScoreScreen = false;
    }

    /**
     * @return Checks talent screen state.
     */
    public boolean isTalentScreen() {
        return talentScreen;
    }

    /**
     * Shows talent screen.
     */
    public void showTalentScreen() {
        this.talentScreen = true;
    }

    /**
     * Hides talent screen.
     */
    public void hideTalentScreen() {
        this.talentScreen = false;
    }
}
