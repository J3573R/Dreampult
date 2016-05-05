package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Tommi Hagelberg
 */
public class Unicorn extends Objects {

    /**
     * Initiates Unicorn Objects. Load assets and set default position & size.
     */
    public Unicorn(AssetManager assets) {
        this.sheet = assets.get("images/objects/allies/unicorn.png", Texture.class);
        width = 3;
        height = 2;
        create(3, 2);
    }
}
