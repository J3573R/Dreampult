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
        manager.load("./images/body.png", Texture.class);
        manager.load("./images/headwithball.png", Texture.class);
        manager.load("./images/leg.png", Texture.class);
        manager.load("./images/left_arm.png", Texture.class);
        manager.load("./images/right_arm.png", Texture.class);
        manager.load("./images/badlogic.jpg", Texture.class);
        manager.load("./images/left_sleeve.png", Texture.class);
        manager.load("./images/right_sleeve.png", Texture.class);
        manager.load("./images/shirt.png", Texture.class);
        manager.load("./images/arrow.png", Texture.class);
        manager.load("./images/meter.png", Texture.class);
        manager.load("./images/meterColor.png", Texture.class);
        manager.load("./images/background/country-platform-back.png", Texture.class);
        manager.load("./images/background/country-platform-forest.png", Texture.class);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("./maps/mappi.tmx", TiledMap.class);
    }
}
