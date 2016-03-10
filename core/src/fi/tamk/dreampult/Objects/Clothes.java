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
    Player player;

    Texture leftSleeve;
    Texture rightSleeve;
    Texture shirt;

    Array<Body> LeftLimbs;
    Array<Body> RightLimbs;

    public Clothes(Player player, GameLoop game) {
        this.game = game;
        this.player = player;

        LeftLimbs = player.LeftLimbs;
        RightLimbs = player.RightLimbs;

        leftSleeve = game.assets.get("./images/left_sleeve.png", Texture.class);
        rightSleeve = game.assets.get("./images/right_sleeve.png", Texture.class);
        shirt = game.assets.get("./images/shirt.png", Texture.class);
    }

    public void draw(SpriteBatch batch) {

        for (Body b : RightLimbs) {
            if (b.getUserData() == "arm") {
                batch.draw(rightSleeve, b.getPosition().x - player.limbWidth / 2, b.getPosition().y - player.limbHeight / 2, // Texture, x, y
                        player.limbWidth / 2, player.limbHeight / 2, // Origin x, Origin y
                        player.limbWidth, player.limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        rightSleeve.getWidth(), rightSleeve.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            }
        }
        batch.draw(shirt, player.body.getPosition().x - player.width / 2, player.body.getPosition().y - player.height / 2, // Texture, x, y
                player.width * 0.5f, player.height * 0.5f, // Origin x, Origin y
                player.width, player.height, // Width, Height
                1, 1, // Scale X, Scale Y
                player.body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                shirt.getWidth(), shirt.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y

        for (Body b : LeftLimbs) {
            if(b.getUserData() == "arm") {
                batch.draw(leftSleeve, b.getPosition().x - player.limbWidth / 2, b.getPosition().y - player.limbHeight / 2, // Texture, x, y
                        player.limbWidth / 2, player.limbHeight / 2, // Origin x, Origin y
                        player.limbWidth, player.limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        leftSleeve.getWidth(), leftSleeve.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            }
        }
    }
}
