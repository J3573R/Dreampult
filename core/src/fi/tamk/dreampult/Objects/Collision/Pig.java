package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Tommi Hagelberg
 */
public class Pig extends Objects {


    /**
     * Initiates Pig Objects. Load assets and set default position & size.
     */
    public Pig(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/pig.png", Texture.class);
        position = new Vector2(3, 3);
        width = 2;
        height = 2;

        create(6, 2);
    }
}
