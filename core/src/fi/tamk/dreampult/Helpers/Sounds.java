package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.audio.Sound;
import fi.tamk.dreampult.Handlers.AssetHandler;

/**
 * Created by Clown on 26.4.2016.
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

    public void play(String sound) {
        switch (sound) {
            case "catapult":
                catapult.play(1.0f);
                break;
            case "moo":
                moo.play(1.0f);
                break;
            case "pig":
                pig.play(1.0f);
                break;
            case "alarm":
                alarm.play(1.0f);
                break;
            case "positive":
                positive.play(1.0f);
                break;
            case "negative":
                negative.play(1.0f);
                break;
            case "ground":
                ground.play(1.0f);
                break;
            case "star":
                star.play(1.0f);
                break;
        }
    }
}
