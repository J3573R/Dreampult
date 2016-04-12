package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.TalentsScreen;

/**
 * Created by Clown on 22.2.2016.
 */
public class InputHandler extends InputAdapter {
    GameLoop loop;
    public float point1;
    public float point2;

    boolean timerOn;
    float timer;

    /**
     * Initialize input handler.
     * @param gameLoop
     */
    public InputHandler(GameLoop gameLoop) {
        loop = gameLoop;
        timer = 0f;
    }

    /**
     * Change arrow position accord given coordinates.
     * @param touchX
     * @param touchY
     */
    private void changeArrow(float touchX, float touchY) {
        Vector3 transform = new Vector3(touchX, touchY, 0);
        loop.GameCamera.unproject(transform);
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

        if(loop.game.collection.isGameOn() && loop.game.collection.launch && timer < 0.5f && loop.bounces > 0) {
            System.out.println("BOUNCE");
            //Vector2 force = loop.player.torso.body.getLinearVelocity();
            Vector2 force = new Vector2(15, 15);
            loop.player.torso.body.setLinearVelocity(force);
            loop.bounces -= 1;
        }

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        loop.UserInterfaceCamera.unproject(touchPos);

        if(loop.ui.pauseButton.contains(touchPos.x, touchPos.y)) {
            if(loop.game.collection.isGameOn()) {
                loop.game.collection.pause();
                loop.game.collection.showPauseMenu();
            } else {
                loop.game.collection.start();
                loop.game.collection.hidePauseMenu();
            }
        }else if(loop.ui.soundButton.contains(touchPos.x, touchPos.y)) {
            loop.ui.toggleSound();
        }

        if(loop.ui.restartButton.button.contains(touchPos.x, touchPos.y) && !loop.collection.isGameOn()) {
            loop.game.restart();
        }

        if(loop.ui.mainMenuButton.button.contains(touchPos.x, touchPos.y) && !loop.collection.isGameOn()) {
            loop.game.MainMenu();
        }

        if(loop.ui.quitButton.button.contains(touchPos.x, touchPos.y) && !loop.collection.isGameOn()) {
            //Gdx.app.exit();
            loop.game.setScreen(new TalentsScreen(loop, loop.UserInterfaceCamera));
        }


       if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y)) {
            loop.meter.hide();
            float speed = loop.meter.scale * 15;
            if(loop.talents.isBoostLaunch()) {
                speed *= 2;
            }
            Vector2 force = new Vector2((float)Math.abs(Math.sin(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed,
                                        (float)Math.abs(Math.cos(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed);
           System.out.println(force);
            if((force.x > 0 || force.y > 0) && !loop.collection.launch) {
                loop.collection.launch = true;
                loop.player.torso.body.applyForceToCenter(force, true);
                loop.arrow.hide();
                loop.meter.hide();
            }
            loop.arrow.start();
        }

        loop.gliding = false;
        timer = 0;
        timerOn = false;

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
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        loop.UserInterfaceCamera.unproject(touchPos);

        if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y)) {
            loop.meter.show();
            loop.arrow.pause();
        }

        loop.gliding = true;
        timerOn = true;

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

    public void timerTick() {
        if(timerOn) {
            timer += Gdx.graphics.getDeltaTime();
        }
    }
}
