package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
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

    I18NBundle bundle;

    /**
     * Initialize input handler.
     * @param gameLoop
     */
    public InputHandler(GameLoop gameLoop) {
        loop = gameLoop;
        bundle = gameLoop.game.localization.myBundle;
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
            loop.player.setBodypartVelocity(new Vector2(0, 0));
            Vector2 vel = new Vector2(30, 30);
            loop.player.torso.body.setLinearVelocity(vel);
            loop.bounces -= 1;
        }

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        loop.UserInterfaceCamera.unproject(touchPos);

        if(!(loop.game.collection.isTalentScreen())) {

            if(loop.ui.pauseButton.contains(touchPos.x, touchPos.y)) {
                if(loop.game.collection.isGameOn()) {
                    loop.game.collection.pause();
                    loop.game.collection.showPauseMenu();
                    loop.tutorial.hide();
                } else {
                    loop.game.collection.start();
                    loop.game.collection.hidePauseMenu();
                    if(!loop.collection.launch) {
                        loop.tutorial.show();
                    }
                }
            }else if(loop.ui.soundButton.contains(touchPos.x, touchPos.y)) {
                loop.ui.toggleSound();
            }

            if(loop.ui.restartButton.button.contains(touchPos.x, touchPos.y)
                    && !loop.collection.isGameOn()
                    && (loop.collection.isPauseMenu() || loop.collection.isScoreScreen())) {
                loop.dispose();
                System.out.println(loop.map.getLevel());
                loop.game.restart(loop.map.getLevel());
            }

            if(loop.ui.mainMenuButton.button.contains(touchPos.x, touchPos.y)
                    && !loop.collection.isGameOn()
                    && (loop.collection.isPauseMenu() || loop.collection.isScoreScreen())) {
                loop.dispose();
                loop.game.MainMenu();
            }

            if(loop.ui.quitButton.button.contains(touchPos.x, touchPos.y)
                    && !loop.collection.isGameOn()
                    && (loop.collection.isPauseMenu() || loop.collection.isScoreScreen())) {
                loop.dispose();
                System.exit(0);
            }

            if(loop.ui.talentsButton.button.contains(touchPos.x, touchPos.y)
                    && !loop.collection.isGameOn()
                    && (loop.collection.isScoreScreen())) {
                loop.game.collection.showTalentScreen();
                loop.dispose();
                loop.game.setScreen(loop.game.talentsScreen);
            } else if (loop.ui.talentsButton.button.contains(touchPos.x, touchPos.y)
                    && !loop.collection.isGameOn()
                    && (loop.collection.isPauseMenu())) {
                        loop.game.collection.start();
                        loop.game.collection.hidePauseMenu();
                    if(!loop.collection.launch) {
                        loop.tutorial.show();
                }
            }
        }

       if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y)) {
            loop.meter.hide();
            loop.ui.shootButtonUp();
            loop.tutorial.hide();
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

        if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y) && !loop.collection.launch) {
            loop.ui.shootButtonDown();
            loop.tutorial.setText(bundle.get("tutorial2"));
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
