package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.3.2016.
 */
public class Clock extends Monster {


    /**
     * Initiates Pig Monster. Load assets and set default position & size.
     */
    public Clock(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/clock.png", Texture.class);
        width = 2;
        height = 3;
        create(1, 1);
    }
}
