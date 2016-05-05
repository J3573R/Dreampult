package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * @author Kalle Heinonen
 */
public class AssetHandler {

    // The AssetManager used by the game
    public AssetManager manager = new AssetManager();

    /**
     * Loads the assets shared by the levels.
     */
    public void loadObjects() {
        loadPlayer();
        loadCatapult();
        loadClothes();
        loadAssets();
    }

    /**
     * Loads the splash screen variants.
     */
    public void loadSplash() {
        manager.load("images/splashEng.png", Texture.class);
        manager.load("images/splashFin.png", Texture.class);
    }

    /**
     * Loads the music.
     */
    public void loadMusic(){
        manager.load("audio/Music/bensound-scifi.mp3", Music.class);
        manager.load("audio/Music/bensound-dance.mp3", Music.class);
        manager.load("audio/Music/bensound-creepy.mp3", Music.class);
    }

    /**
     * Loads the images for drawing the player.
     */
    public void loadPlayer(){
        manager.load("images/player/body.png", Texture.class);
        manager.load("images/player/headwithball.png", Texture.class);
        manager.load("images/player/leg.png", Texture.class);
        manager.load("images/player/left_arm.png", Texture.class);
        manager.load("images/player/right_arm.png", Texture.class);
    }

    /**
     * Loads the clothes used for a talent.
     */
    public void loadClothes() {
        manager.load("images/player/pyjama/left_sleeve.png", Texture.class);
        manager.load("images/player/pyjama/right_sleeve.png", Texture.class);
        manager.load("images/player/pyjama/shirt.png", Texture.class);
    }

    /**
     * Loads the images used for the user interface.
     */
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
        manager.load("images/objects/allies/star.png", Texture.class);
        loadTalents();
    }

    /**
     * Loads the I18NBundle for localization.
     *
     * @param locale Locale that is assigned as the default one
     */
    public void loadLocalization(Locale locale) {
        unloadLocale();
        I18NBundleLoader.I18NBundleParameter param = new I18NBundleLoader.I18NBundleParameter();
        param.locale.setDefault(locale);
        manager.load("MyBundle/", I18NBundle.class, param);
    }

    /**
     * Unloads the I18NBundle.
     */
    public void unloadLocale() {
        if(manager.isLoaded("MyBundle/")) {
            manager.unload("MyBundle/");
        }
    }

    /**
     * Loads the assets shared by all three levels.
     */
    public void loadAssets() {
        manager.load("images/launching/whiteArrow.png", Texture.class);
        manager.load("images/launching/meter.png", Texture.class);
        manager.load("images/launching/meterColor.png", Texture.class);
        manager.load("images/player/hiteffect.png", Texture.class);
        manager.load("images/player/hiteffect2.png", Texture.class);
        manager.load("images/player/rainbow_frames.png", Texture.class);
        manager.load("images/player/glide_animation.png", Texture.class);
        manager.load("images/objects/allies/unicorn.png", Texture.class);
    }

    /**
     * Loads the images for drawing the catapult.
     */
    public void loadCatapult() {
        manager.load("images/launching/catapult/Catapult.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_wheel.png", Texture.class);
        manager.load("images/launching/catapult/Catapult_spoon.png", Texture.class);
    }

    /**
     * Loads the assets used by the first level.
     */
    public void loadLevel1(){
        manager.load("images/background/level1/layer1.png", Texture.class);
        manager.load("images/background/level1/layer2.png", Texture.class);
        manager.load("images/background/level1/layer3.png", Texture.class);
        manager.load("images/background/level1/layer4.png", Texture.class);

        manager.load("images/objects/enemies/pig.png", Texture.class);
        manager.load("images/objects/enemies/clock.png", Texture.class);

        manager.load("images/objects/allies/bed.png", Texture.class);
    }

    /**
     * Loads the assets used by the second level.
     */
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

    /**
     * Loads the assets used by the third level.
     */
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

    /**
     * Loads the assets used by the Talent Screen.
     */
    public void loadTalents() {
        manager.load("images/talents/catapult1.png", Texture.class);
        manager.load("images/talents/jumps1.png", Texture.class);
        manager.load("images/talents/bouncy1.png", Texture.class);
        manager.load("images/talents/rainbow1.png", Texture.class);
        manager.load("images/talents/shirt1.png", Texture.class);
        manager.load("images/talents/slippery1.png", Texture.class);
        manager.load("images/talents/talentScreen_bg.png", Texture.class);
        manager.load("images/talents/box.png", Texture.class);
        manager.load("images/talents/TalentLock.png", Texture.class);
    }

    /**
     * Loads the sound effects.
     */
    public void loadSoundEffects() {
        manager.load("audio/AnalogAlarm.wav", Sound.class);
        manager.load("audio/CatapultLaunch2.wav", Sound.class);
        manager.load("audio/CowMoo.wav", Sound.class);
        manager.load("audio/HitGround.wav", Sound.class);
        manager.load("audio/PigOink.wav", Sound.class);
        manager.load("audio/Positive.wav", Sound.class);
        manager.load("audio/Negative.wav", Sound.class);
        manager.load("audio/Victory2.mp3", Sound.class);
    }
}
