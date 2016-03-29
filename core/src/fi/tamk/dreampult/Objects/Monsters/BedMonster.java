package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.3.2016.
 */
public class BedMonster extends Monster {


    /**
     * Initiates Pig Monster. Load assets and set default position & size.
     * @param gameLoop
     */
    public BedMonster(GameLoop gameLoop) {
        super(gameLoop);
        this.sheet = gameLoop.assets.get("images/objects/bedmonster/bed.png", Texture.class);
        width = 3;
        height = 2;
        create(3, 3);
    }
}
