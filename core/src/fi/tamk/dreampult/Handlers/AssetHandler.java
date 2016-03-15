package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
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
        loadClothes();
        loadUi();
        loadMap();
        loadAssets();
    }

    public void loadPlayer(){
        manager.load("./images/player/body.png", Texture.class);
        manager.load("./images/player/headwithball.png", Texture.class);
        manager.load("./images/player/leg.png", Texture.class);
        manager.load("./images/player/left_arm.png", Texture.class);
        manager.load("./images/player/right_arm.png", Texture.class);
    }

    public void loadClothes() {
        manager.load("./images/player/pyjama/left_sleeve.png", Texture.class);
        manager.load("./images/player/pyjama/right_sleeve.png", Texture.class);
        manager.load("./images/player/pyjama/shirt.png", Texture.class);
    }

    public void loadUi() {
        manager.load("./images/dreampult_logo.png", Texture.class);
        manager.load("./images/ui/pause_button.png", Texture.class);
        manager.load("./images/ui/pause_menu.png", Texture.class);
    }
    public void loadMap() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("./maps/mappi.tmx", TiledMap.class);
    }

    public void loadAssets() {
        manager.load("./images/arrow.png", Texture.class);
        manager.load("./images/meter.png", Texture.class);
        manager.load("./images/meterColor.png", Texture.class);
        manager.load("./images/background/country-platform-back.png", Texture.class);
        manager.load("./images/background/country-platform-forest.png", Texture.class);
    }
}
