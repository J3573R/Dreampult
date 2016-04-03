package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.graphics.Texture;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.3.2016.
 */
public class Clock extends Monster {


    /**
     * Initiates Pig Monster. Load assets and set default position & size.
     * @param gameLoop
     */
    public Clock(GameLoop gameLoop) {
        super(gameLoop);
        this.sheet = gameLoop.assets.get("images/objects/clock/alarmclock.png", Texture.class);
        width = 2;
        height = 3;
        create(1, 1);
    }
}
