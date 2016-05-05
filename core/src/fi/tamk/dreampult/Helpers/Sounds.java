package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.audio.Sound;
import fi.tamk.dreampult.Handlers.AssetHandler;

/**
 * @author Tommi Hagelberg
 */
public class Sounds {

    public Sound catapult;
    public Sound moo;
    public Sound pig;
    public Sound alarm;
    public Sound positive;
    public Sound star;
    public Sound negative;
    public Sound ground;

    /**
     * Loads all sound effects for the quick use.
     * @param manager Asset manager for loading sounds.
     */
    public Sounds(AssetHandler manager){
        catapult = manager.manager.get("audio/CatapultLaunch2.wav", Sound.class);
        moo = manager.manager.get("audio/CowMoo.wav", Sound.class);
        pig = manager.manager.get("audio/PigOink.wav", Sound.class);
        alarm = manager.manager.get("audio/AnalogAlarm.wav", Sound.class);
        positive = manager.manager.get("audio/Victory2.mp3", Sound.class);
        star = manager.manager.get("audio/Positive.wav", Sound.class);
        negative = manager.manager.get("audio/Negative.wav", Sound.class);
        ground = manager.manager.get("audio/HitGround.wav", Sound.class);
    }

    /**
     * Plays sound according identifier.
     * @param sound Name of the sound file.
     */
    public void play(String sound) {

        if(sound.equals("catapult")) {
            catapult.play(1.0f);
        } else if(sound.equals("moo")) {
            moo.play(1.0f);
        } else if(sound.equals("pig")) {
            pig.play(1.0f);
        } else if(sound.equals("alarm")) {
            alarm.play(1.0f);
        } else if(sound.equals("positive")) {
            positive.play(1.0f);
        } else if(sound.equals("negative")) {
            negative.play(1.0f);
        } else if(sound.equals("ground")) {
            ground.play(1.0f);
        } else if(sound.equals("star")) {
            star.play(1.0f);
        }

    }
}
