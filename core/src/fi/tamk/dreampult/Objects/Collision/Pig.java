package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Clown on 22.3.2016.
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
        minSpawnTime = 0;
        maxSpawnTime = 1;
        maxSpawnHeight = 0;
        minSpawnHeight = 1;

        create(6, 2);
    }
}
