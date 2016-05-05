package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Tommi Hagelberg
 */
public class Bed extends Objects {

    /**
     * Initiates Pig Objects. Load assets and set default position & size.
     */
    public Bed(AssetManager assets) {
        this.sheet = assets.get("images/objects/allies/bed.png", Texture.class);
        width = 3;
        height = 2;
        create(3, 3);
    }
}
