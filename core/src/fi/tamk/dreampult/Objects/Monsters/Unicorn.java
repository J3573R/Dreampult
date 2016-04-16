package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Clown on 16.4.2016.
 */
public class Unicorn extends Monster {
    public Unicorn(AssetManager assets) {
        this.sheet = assets.get("images/objects/allies/unicorn.png", Texture.class);
        width = 3;
        height = 2;
        create(3, 3);
    }
}
