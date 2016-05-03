package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import fi.tamk.dreampult.GameLoop;

import java.util.Random;

/**
 * Created by DV6-6B20 on 10.3.2016.
 */
public class Clothes {
    GameLoop game;

    Texture leftSleeve;
    Texture rightSleeve;
    Texture shirt;

    boolean dropClothes;
    float dropVelocity;

    float posX;
    float posY;

    public Clothes(GameLoop game) {
        this.game = game;
        dropClothes = false;
        dropVelocity = 0;

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

        if(dropClothes) {
            posX = bodypart.body.getPosition().x - bodypart.width / 2;
            dropVelocity += Gdx.graphics.getDeltaTime() * 2;
            batch.draw(img,
                    posX - dropVelocity,
                    posY - dropVelocity, // Texture, x, y
                    bodypart.width / 2, bodypart.height / 2, // Origin x, Origin y
                    bodypart.width, bodypart.height, // Width, Height
                    1, 1, // Scale X, Scale Y
                    bodypart.body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    false, false); // flip x, flip y
        } else {
            posX = bodypart.body.getPosition().x - bodypart.width / 2;
            posY = bodypart.body.getPosition().y - bodypart.height / 2;
            batch.draw(img, posX, posY, // Texture, x, y
                    bodypart.width / 2, bodypart.height / 2, // Origin x, Origin y
                    bodypart.width, bodypart.height, // Width, Height
                    1, 1, // Scale X, Scale Y
                    bodypart.body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    false, false); // flip x, flip y
        }

    }

    public void setDropClothes(boolean drop) {
        dropClothes = drop;
    }
}
