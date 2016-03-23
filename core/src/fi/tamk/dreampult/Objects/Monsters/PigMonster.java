package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.3.2016.
 */
public class PigMonster extends Monster {

    /**
     * Initiates Pig Monster. Load assets and set default position & size.
     * @param gameLoop
     */
    public PigMonster(GameLoop gameLoop) {
        this.sheet = gameLoop.assets.get("images/objects/pigMonster/pigmonster_animationframes.png", Texture.class);
        position = new Vector2(3, 3);
        width = 2;
        height = 2;
        create(6, 2);
    }
}
