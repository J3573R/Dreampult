package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Tommi Hagelberg
 */
public class Cow extends Objects {

    /**
     * Initiates Cow Objects. Load assets and set default position & size.
     */
    public Cow(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/cow.png", Texture.class);
        width = 3;
        height = 2;
        create(4, 2);
    }
}
