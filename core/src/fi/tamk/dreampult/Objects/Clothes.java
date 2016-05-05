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
 * @author Kalle Heinonen & Tommi Hagelberg
 */
public class Clothes {
    GameLoop game;

    // Textures for the clothes
    Texture leftSleeve;
    Texture rightSleeve;
    Texture shirt;

    boolean dropClothes;
    float dropVelocity;

    float posX;
    float posY;

    /**
     * Creates the clothes for the player.
     *
     * @param game used to gain access to the AssetManager GameLoop uses
     */
    public Clothes(GameLoop game) {
        this.game = game;
        dropClothes = false;
        dropVelocity = 0;

        leftSleeve = game.assets.get("images/player/pyjama/left_sleeve.png", Texture.class);
        rightSleeve = game.assets.get("images/player/pyjama/right_sleeve.png", Texture.class);
        shirt = game.assets.get("images/player/pyjama/shirt.png", Texture.class);
    }

    /**
     * Draws the clothes.
     *
     * @param batch SpriteBatch used for drawing
     * @param bodypart the Bodypart to draw
     */
    public void draw(SpriteBatch batch, Bodypart bodypart) {

        // Initializes the value of texture img
        Texture img = leftSleeve;

        // Draws the correct texture
        if (bodypart.body.getUserData() == "left arm") {
            img = leftSleeve;
        }

        else if (bodypart.body.getUserData() == "right arm") {
            img = rightSleeve;
        }

        else if (bodypart.body.getUserData() == "torso") {
            img = shirt;
        }

        // Makes the clothes drop off the player
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


    /**
     * @param drop changes whether the clothes will drop or not
     */
    public void setDropClothes(boolean drop) {
        dropClothes = drop;
    }
}
