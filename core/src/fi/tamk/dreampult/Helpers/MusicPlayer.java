package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Clown on 4.5.2016.
 */
public class MusicPlayer {
    Music playing;
    AssetManager manager;
    Saves saves;

    public MusicPlayer(AssetManager manager, Saves saves) {
        this.manager = manager;
        this.saves = saves;

        playing = manager.get("audio/Music/bensound-dance.mp3");
        playing.setLooping(true);

        toggle();
    }

    public void playLevel1(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-dance.mp3");
        playing.setLooping(true);
        toggle();
    }

    public void playLevel2(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-scifi.mp3");
        playing.setLooping(true);
        toggle();
    }

    public void playLevel3(){
        playing.stop();
        playing = manager.get("audio/Music/bensound-creepy.mp3");
        playing.setLooping(true);
        toggle();
    }

    public void toggle(){
        if(saves.getSounds() == saves.ON) {
            playing.play();
        } else {
            playing.stop();
        }
    }
}
