package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Tommi Hagelberg
 */
public class Star extends Objects {

    /**
     * Initiates Star Objects. Load assets and set default position & size.
     */
    public Star(AssetManager assets) {
        this.sheet = assets.get("images/objects/allies/star.png", Texture.class);
        width = 1;
        height = 1;
        create(1, 1);
    }
}
