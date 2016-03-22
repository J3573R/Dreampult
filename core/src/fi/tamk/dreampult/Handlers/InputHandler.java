package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.2.2016.
 */
public class InputHandler extends InputAdapter {
    GameLoop loop;
    public float point1;
    public float point2;

    /**
     * Initialize input handler.
     * @param gameLoop
     */
    public InputHandler(GameLoop gameLoop) {
        loop = gameLoop;
    }

    /**
     * Change arrow position accord given coordinates.
     * @param touchX
     * @param touchY
     */
    private void changeArrow(float touchX, float touchY) {
        Vector3 transform = new Vector3(touchX, touchY, 0);
        loop.camera.unproject(transform);
        touchX = transform.x;
        touchY = transform.y;

        float fixedX = loop.player.torso.body.getPosition().x;
        float fixedY = loop.player.torso.body.getPosition().y;

        point1 = (touchX - fixedX) * -1;
        point2 = (touchY - fixedY);

        if(checkDirection()) {
            loop.arrow.rotation = MathUtils.atan2(point1, point2);
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
        System.out.println(screenX + " : " + screenY);
        if(screenX >= 900 && screenY <= 65) {
            if (loop.game.collection.isGameOn()) {
                loop.game.collection.pause();

            } else {
                loop.game.collection.start();
            }

        } else if(loop.game.collection.isGameOn()) {
            loop.meter.hide();
            float speed = loop.meter.scale * 6;
            Vector2 force = new Vector2((float)Math.abs(Math.sin(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed,
                                        (float)Math.abs(Math.cos(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed);
            System.out.println(force);
            loop.player.torso.body.applyForceToCenter(force, true);
            //loop.player.torso.body.applyAngularImpulse(-0.5f, true);
            loop.arrow.start();
        }

        if(!loop.game.collection.isGameOn()) {
            if((screenX >= 418 && screenY <= 270) && (screenX <= 535 && screenY >= 228)) {
                loop.game.collection.start();

            } else if ((screenX >= 418 && screenY <= 333) && (screenX <= 535 && screenY >= 292)) {
                System.out.println("Reset button pressed");
            } else if ((screenX >= 418 && screenY <= 395) && (screenX <= 535 && screenY >= 355)) {
                System.out.println("Exit button pressed");
            } else if ((screenX >= 418 && screenY <= 210) && (screenX <= 466 && screenY >= 162)) {
                System.out.println("Sound button pressed");
            } else if ((screenX >= 489 && screenY <= 210) && (screenX <= 535 && screenY >= 162)) {
                System.out.println("Music button pressed");
            }
        }

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
        if(loop.game.collection.isGameOn()) {
            loop.meter.show();
            loop.arrow.pause();
        }

        return true;
    }

    /**
     * Do something when key is down.
     * @param keyCode
     * @return
     */
    public boolean keyDown(int keyCode) {
        if(keyCode == Input.Keys.SPACE) {
            loop.game.collection.pause();
        }
        return true;
    }

    /**
     * Do something when key is up.
     * @param keyCode
     * @return
     */
    public boolean keyUp(int keyCode) {
        if(keyCode == Input.Keys.SPACE) {
            loop.game.collection.start();
        }
        return true;
    }
}
