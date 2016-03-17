package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import fi.tamk.dreampult.GameLoop;

/**
 * Created by DV6-6B20 on 10.3.2016.
 */
public class Clothes {
    GameLoop game;

    Texture leftSleeve;
    Texture rightSleeve;
    Texture shirt;

    public Clothes(GameLoop game) {
        this.game = game;

        leftSleeve = game.assets.get("images/player/pyjama/left_sleeve.png", Texture.class);
        rightSleeve = game.assets.get("images/player/pyjama/right_sleeve.png", Texture.class);
        shirt = game.assets.get("images/player/pyjama/shirt.png", Texture.class);
    }

    public void draw(SpriteBatch batch, Bodypart bodypart) {

        Texture img = leftSleeve;

        if (bodypart.body.getUserData() == "left arm") {
            img = leftSleeve;
        }

        else if (bodypart.body.getUserData() == "right arm") {
            img = rightSleeve;
        }

        else if (bodypart.body.getUserData() == "torso") {
            img = shirt;
        }

        else {
            return;
        }

        batch.draw(img, bodypart.body.getPosition().x - bodypart.width / 2, bodypart.body.getPosition().y - bodypart.height / 2, // Texture, x, y
                bodypart.width / 2, bodypart.height / 2, // Origin x, Origin y
                bodypart.width, bodypart.height, // Width, Height
                1, 1, // Scale X, Scale Y
                bodypart.body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
    }
}
