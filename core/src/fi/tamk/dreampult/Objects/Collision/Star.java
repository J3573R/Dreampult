package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Clown on 17.4.2016.
 */
public class Star extends Objects {

    public Star(AssetManager assets) {
        this.sheet = assets.get("images/objects/allies/star.png", Texture.class);
        width = 1;
        height = 1;
        create(1, 1);
    }
}
