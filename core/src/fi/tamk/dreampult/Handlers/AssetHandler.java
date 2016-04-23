package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by root on 25.2.2016.
 */
public class AssetHandler {
    public AssetManager manager = new AssetManager();

    public void loadObjects() {
        loadPlayer();
        loadCatapult();
        loadClothes();
        loadAssets();
        loadTalents();
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
        manager.load("images/ui/pause.png", Texture.class);
        manager.load("images/ui/pause_button.png", Texture.class);
        manager.load("images/ui/pause_menu.png", Texture.class);
        manager.load("images/title/Menuscreen.png", Texture.class);
        manager.load("images/title/level1_open.png", Texture.class);
        manager.load("images/title/level2_open.png", Texture.class);
        manager.load("images/title/level2_locked.png", Texture.class);
        manager.load("images/title/level3_open.png", Texture.class);
        manager.load("images/title/level3_locked.png", Texture.class);
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
        manager.load("images/launching/Launch_button.png", Texture.class);
        manager.load("images/launching/Launch_button_down.png", Texture.class);
        manager.load("images/ui/text_button.png", Texture.class);
        manager.load("images/ui/text_button_grey.png", Texture.class);
        manager.load("images/finActive.png", Texture.class);
        manager.load("images/britActive.png", Texture.class);
        manager.load("images/background/level1/layer4.png", Texture.class);
        manager.load("images/background/level2/layer4.png", Texture.class);
        manager.load("images/background/level3/layer4.png", Texture.class);


//        manager.load("audio/soundEffects/positive.wav", Sound.class);
//        manager.load("audio/soundEffects/negative.wav", Sound.class);
    }

    public void loadLocalization(Locale locale) {
        unloadLocale();
        I18NBundleLoader.I18NBundleParameter param = new I18NBundleLoader.I18NBundleParameter();
        param.locale.setDefault(locale);
        manager.load("MyBundle/", I18NBundle.class, param);
    }

    public void unloadLocale() {
        if(manager.isLoaded("MyBundle/")) {
            manager.unload("MyBundle/");
        }
    }

    public void loadAssets() {
        manager.load("images/launching/whiteArrow.png", Texture.class);
        manager.load("images/launching/meter.png", Texture.class);
        manager.load("images/launching/meterColor.png", Texture.class);
        manager.load("images/player/hiteffect.png", Texture.class);
        manager.load("images/player/hiteffect2.png", Texture.class);
        manager.load("images/player/rainbow_frames.png", Texture.class);
        manager.load("images/objects/allies/star.png", Texture.class);
        manager.load("images/objects/allies/unicorn.png", Texture.class);
    }

    public void loadCatapult() {
        manager.load("images/launching/catapult/Catapult.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_wheel.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_spoon.png", Texture.class);
    }


    public void loadLevel1(){
        manager.load("images/background/level1/layer1.png", Texture.class);
        manager.load("images/background/level1/layer2.png", Texture.class);
        manager.load("images/background/level1/layer3.png", Texture.class);
        manager.load("images/background/level1/layer4.png", Texture.class);

        manager.load("images/objects/enemies/pig.png", Texture.class);
        manager.load("images/objects/enemies/clock.png", Texture.class);

        manager.load("images/objects/allies/bed.png", Texture.class);
    }

    public void loadLevel2(){
        manager.load("images/background/level2/layer1.png", Texture.class);
        manager.load("images/background/level2/layer2.png", Texture.class);
        manager.load("images/background/level2/layer3.png", Texture.class);
        manager.load("images/background/level2/layer4.png", Texture.class);

        manager.load("images/objects/enemies/pig.png", Texture.class);
        manager.load("images/objects/enemies/turtle.png", Texture.class);
        manager.load("images/objects/enemies/clock.png", Texture.class);

        manager.load("images/objects/allies/unicorn.png", Texture.class);
        manager.load("images/objects/allies/bed.png", Texture.class);
    }

    public void loadLevel3(){
        manager.load("images/background/level3/layer1.png", Texture.class);
        manager.load("images/background/level3/layer2.png", Texture.class);
        manager.load("images/background/level3/layer3.png", Texture.class);
        manager.load("images/background/level3/layer4.png", Texture.class);

        manager.load("images/objects/enemies/pig.png", Texture.class);
        manager.load("images/objects/enemies/turtle.png", Texture.class);
        manager.load("images/objects/enemies/cow.png", Texture.class);
        manager.load("images/objects/enemies/clock.png", Texture.class);

        manager.load("images/objects/allies/unicorn.png", Texture.class);
        manager.load("images/objects/allies/bed.png", Texture.class);
    }

    public void loadTalents() {
        manager.load("images/talents/catapult1.png", Texture.class);
        manager.load("images/talents/jumps1.png", Texture.class);
        manager.load("images/talents/bouncy1.png", Texture.class);
        manager.load("images/talents/rainbow1.png", Texture.class);
        manager.load("images/talents/shirt1.png", Texture.class);
        manager.load("images/talents/slippery1.png", Texture.class);
        manager.load("images/talents/talentScreen_bg.png", Texture.class);
        manager.load("images/talents/box.png", Texture.class);
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
