package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Clown on 22.3.2016.
 */
public class Clock extends Objects {


    /**
     * Initiates Pig Objects. Load assets and set default position & size.
     */
    public Clock(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/clock.png", Texture.class);
        width = 2;
        height = 3;
        create(1, 1);
    }
}
