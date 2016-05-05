package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Tommi Hagelberg
 */
public class Turtle extends Objects {

    /**
     * Initiates Turtle Objects. Load assets and set default position & size.
     */
    public Turtle(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/turtle.png", Texture.class);
        width = 3;
        height = 2;
        create(4, 2);
    }
}
