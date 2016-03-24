package fi.tamk.dreampult.Objects.Launching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;
import org.w3c.dom.Text;

/**
 * Created by root on 23.3.2016.
 */
public class Catapult {

    GameLoop gameLoop;

    Texture body;
    Texture wheel;
    public Texture spoon;
    public Vector2 spoonPosition = new Vector2(0.2f, 0.2f);
    public float spoonRotation;

    public Catapult(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        body = gameLoop.assets.get("images/launching/catapult/Catapult.png", Texture.class);
        wheel = gameLoop.assets.get("images/launching/catapult/Catapult_wheel.png", Texture.class);
        spoon = gameLoop.assets.get("images/launching/catapult/Catapult_spoon.png", Texture.class);
        spoonRotation = 0.2f;
    }

    public void draw(SpriteBatch batch) {

        batch.draw(spoon, spoonPosition.x, spoonPosition.y, // Texture, x, y
                2, 0, // Origin x, Origin y
                2, 2, // Width, Height
                1, 1, // Scale X, Scale Y
                spoonRotation * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                spoon.getWidth(), spoon.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
        //batch.draw(spoon, 0.2f, 0.2f, 2, 2);
        batch.draw(body, 1, 0, 2, 2);
        //batch.draw(, 1.5f, 0.5f, 1f, 1f);
        batch.draw(wheel, 1.5f, 0.5f, // Texture, x, y
                 0.5f, 0.5f, // Origin x, Origin y
                1f, 1f, // Width, Height
                1, 1, // Scale X, Scale Y
                spoonRotation * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                wheel.getWidth(), wheel.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
    }

    public void rotate() {
        spoonRotation -=  Gdx.graphics.getDeltaTime();
    }

}