package fi.tamk.dreampult;

/**
 * Created by Clown on 15.3.2016.
 */
public class Collection {
    private boolean gameIsOn;

    final public int SCREEN_WIDTH = 16;
    final public int SCREEN_HEIGHT = 9;

    public final int UP = 1;
    public final int DOWN = 2;

    public boolean launch = false;

    private boolean soundIsOn;

    public Collection() {
        gameIsOn = false;
        soundIsOn = true;
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
}
