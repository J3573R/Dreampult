package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * @author Tommi Hagelberg
 */
public class MusicPlayer {
    Music playing;
    AssetManager manager;
    Saves saves;

    /**
     * Initialize music player. Saves asset manager for loading music and saves for sound state.
     * @param manager Gets sound files.
     * @param saves Gets sound state.
     */
    public MusicPlayer(AssetManager manager, Saves saves) {
        this.manager = manager;
        this.saves = saves;
        playing = manager.get("audio/Music/bensound-dance.mp3");
        playing.setLooping(true);
        toggle();
    }

    /**
     * Plays level 1 music if sound is on.
     */
    public void playLevel1(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-dance.mp3");
        toggle();
    }

    /**
     * Plays level 2 music if sound is on.
     */
    public void playLevel2(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-scifi.mp3");
        toggle();
    }

    /**
     * Plays level 3 music if sound is on.
     */
    public void playLevel3(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-creepy.mp3");
        toggle();
    }

    /**
     * Checks if sounds are toggled.
     */
    public void toggle(){
        if(saves.getSounds() == saves.ON) {
            playing.play();
        } else {
            playing.stop();
        }
    }
}
