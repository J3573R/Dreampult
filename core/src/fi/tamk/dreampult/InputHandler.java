package fi.tamk.dreampult;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Clown on 22.2.2016.
 */
public class InputHandler extends InputAdapter {
    GameLoop game;
    public float point1;
    public float point2;
    float speed = 2f;

    /**
     * Initialize input handler.
     * @param gameLoop
     */
    public InputHandler(GameLoop gameLoop) {
        game = gameLoop;
    }

    /**
     * Change arrow position accord given coordinates.
     * @param touchX
     * @param touchY
     */
    private void changeArrow(float touchX, float touchY) {
        Vector3 transform = new Vector3(touchX, touchY, 0);
        game.camera.unproject(transform);
        touchX = transform.x;
        touchY = transform.y;

        float fixedX = game.player.body.getPosition().x;
        float fixedY = game.player.body.getPosition().y;

        point1 = (touchX - fixedX) * -1;
        point2 = (touchY - fixedY);

        if(checkDirection()) {
            game.arrow.rotation = MathUtils.atan2(point1, point2);
        }
    }

    /**
     * Check if direction is up or forward.
     * @return
     */
    private boolean checkDirection() {
        if(point1 < 0 && point2 > 0) {
            return true;
        }
        return false;
    }

    /**
     * Give player force when touch is released.
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 force = new Vector2((float)Math.abs(Math.sin(game.arrow.rotation)) * speed, (float)Math.abs(Math.cos(game.arrow.rotation)) * speed);
        game.player.body.applyLinearImpulse(force, game.player.body.getWorldCenter(), true);
        game.moveArrow = true;
        return true;
    }

    /**
     * Stop arrow when screen is touched.
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //changeArrow(screenX, screenY);
        game.moveArrow = false;
        return true;
    }
}
