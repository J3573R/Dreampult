package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by root on 25.2.2016.
 */
public class AssetHandler {
    public AssetManager manager = new AssetManager();

    public void loadTestMap() {
        loadPlayer();
        loadCatapult();
        loadMonsters();
        loadClothes();
        loadMap();
        loadAssets();
        loadTalents();
//        loadSoundEffects();
    }

    public void loadPlayer(){
        manager.load("images/player/body.png", Texture.class);
        manager.load("images/player/headwithball.png", Texture.class);
        manager.load("images/player/leg.png", Texture.class);
        manager.load("images/player/left_arm.png", Texture.class);
        manager.load("images/player/right_arm.png", Texture.class);
    }

    public void loadClothes() {
        manager.load("images/player/pyjama/left_sleeve.png", Texture.class);
        manager.load("images/player/pyjama/right_sleeve.png", Texture.class);
        manager.load("images/player/pyjama/shirt.png", Texture.class);
    }

    public void loadUi() {
        manager.load("images/dreampult_logo.png", Texture.class);
        manager.load("images/ui/pause_button.png", Texture.class);
        manager.load("images/ui/pause_menu.png", Texture.class);
        manager.load("images/menu_tausta.png", Texture.class);
        manager.load("images/levelOne.png", Texture.class);
        manager.load("images/lockedLevel.png", Texture.class);
        manager.load("images/ui/soundOff.png", Texture.class);
        manager.load("images/ui/soundOn.png", Texture.class);
        manager.load("images/ui/blankFalse.png", Texture.class);
        manager.load("images/ui/blankTrue.png", Texture.class);
        manager.load("images/ui/falseTexture.png", Texture.class);
        manager.load("images/ui/trueTexture.png", Texture.class);
        manager.load("images/ui/truePressed.png", Texture.class);
        manager.load("images/ui/falsePressed.png", Texture.class);
        manager.load("images/endScreen.png", Texture.class);
        manager.load("images/ui/shootButton.png", Texture.class);
        manager.load("images/finFlag.png", Texture.class);
        manager.load("images/britFlag.png", Texture.class);
//        manager.load("audio/soundEffects/positive.wav", Sound.class);
//        manager.load("audio/soundEffects/negative.wav", Sound.class);

    }
    public void loadMap() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/mappi.tmx", TiledMap.class);
    }

    public void loadAssets() {
        manager.load("images/launching/whiteArrow.png", Texture.class);
        manager.load("images/launching/meter.png", Texture.class);
        manager.load("images/launching/meterColor.png", Texture.class);
        manager.load("images/background/back_clouds.png", Texture.class);
        manager.load("images/background/middle_clouds.png", Texture.class);
        manager.load("images/background/front_clouds.png", Texture.class);
        manager.load("images/background/bg2.png", Texture.class);
        manager.load("images/player/hiteffect.png", Texture.class);
        manager.load("images/objects/star.png", Texture.class);
    }

    public void loadCatapult() {
        manager.load("images/launching/catapult/Catapult.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_wheel.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_spoon.png", Texture.class);
    }

    public void loadMonsters() {
        manager.load("images/objects/pigMonster/pigmonster_animationframes.png", Texture.class);
        manager.load("images/objects/bedmonster/bed.png", Texture.class);
        manager.load("images/objects/clock/alarmclock.png", Texture.class);
    }

    public void loadTalents() {
        manager.load("images/icon.png", Texture.class);
    }

    public void loadSoundEffects() {
        manager.load("audio/soundEffects/analogAlarm.wav", Sound.class);
        manager.load("audio/soundEffects/catapultLaunch2.wav", Sound.class);
        manager.load("audio/soundEffects/cowMoo.wav", Sound.class);
        manager.load("audio/soundEffects/hitGround.wav", Sound.class);
        manager.load("audio/soundEffects/pigOink.wav", Sound.class);
        manager.load("audio/soundEffects/victory1.wav", Sound.class);
        manager.load("audio/soundEffects/victory2.mp3", Sound.class);
    }
}
