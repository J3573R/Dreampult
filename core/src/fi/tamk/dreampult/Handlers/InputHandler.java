package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
 */
public class InputHandler extends InputAdapter {
    GameLoop loop;

    /**
     * Timer for checking if touch is quick or hold.
     */
    boolean timerOn;
    public float timer;

    /**
     * Localization for drawing text.
     */
    I18NBundle bundle;

    /**
     * Initialize input handler.
     * @param gameLoop Saves game loop for later use.
     */
    public InputHandler(GameLoop gameLoop) {
        loop = gameLoop;
        bundle = gameLoop.game.localization.myBundle;
        timer = 0f;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        loop.UserInterfaceCamera.unproject(touchPos);

        /**
         * Players quick touch on gameloop causes character to bounce.
         */
        if(loop.game.collection.isGameOn() && loop.game.collection.launch && timer < 0.5f && loop.bounces > 0 && touchPos.y < 450) {
            loop.player.setBodypartVelocity(new Vector2(0, 0));
            Vector2 vel = new Vector2(30, 30);
            loop.player.torso.body.setLinearVelocity(vel);
            loop.bounces -= 1;
        }


        if(!(loop.game.collection.isTalentScreen())) {

            if(loop.ui.pauseButton.contains(touchPos.x, touchPos.y)) {
                /**
                 * Show and hide pause menu.
                 */
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
            } else if(loop.ui.soundButton.contains(touchPos.x, touchPos.y)) {

                /**
                 * Toggle sounds.
                 */
                if(loop.saves.getSounds() == loop.saves.ON) {
                    loop.saves.setSounds(loop.saves.OFF);
                } else {
                    loop.saves.setSounds(loop.saves.ON);
                }

                loop.saves.save();
                loop.game.player.toggle();

            } else if(!loop.collection.isGameOn() &&
                    (loop.collection.isPauseMenu() || loop.collection.isScoreScreen())){

                if(loop.ui.restartButton.button.contains(touchPos.x, touchPos.y)) {
                    /**
                     * Restarts game loop.
                     */
                    loop.levelUnlock = 0;
                    loop.dispose();
                    System.out.println(loop.map.getLevel());
                    loop.game.restart(loop.map.getLevel());
                } else if(loop.ui.mainMenuButton.button.contains(touchPos.x, touchPos.y)) {
                    /**
                     * Returns to main menu.
                     */
                    loop.levelUnlock = 0;
                    loop.dispose();
                    loop.game.MainMenu();
                } else if(loop.ui.quitButton.button.contains(touchPos.x, touchPos.y)) {
                    /**
                     * Exits the application.
                     */
                    loop.levelUnlock = 0;
                    loop.dispose();
                    System.exit(0);
                } else if(loop.collection.isPauseMenu() && loop.ui.resumeButton.button.contains(touchPos.x, touchPos.y)) {
                    /**
                     * Resume game.
                     */
                    loop.game.collection.start();
                    loop.game.collection.hidePauseMenu();
                    if(!loop.collection.launch) {
                        loop.tutorial.show();
                    }
                } else if(loop.collection.isScoreScreen()) {
                    if(loop.ui.talentsButton.button.contains(touchPos.x, touchPos.y)) {
                        /**
                         * Switchs to talents screen.
                         */
                        loop.levelUnlock = 0;
                        loop.dispose();
                        loop.game.talentsScreen.refreshStarButton();
                        loop.game.collection.showTalentScreen();
                        loop.game.setScreen(loop.game.talentsScreen);
                    }
                }
            }
        }

        /**
         * Launches player.
         */
       if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y)) {
            loop.meter.hide();
            loop.ui.shootButtonUp();
            loop.tutorial.hide();
            float speed = loop.meter.scale * 15;
            if(loop.saves.isBoostLaunch()) {
                speed *= 2;
                loop.shittingRainbow.play();
            }
            Vector2 force = new Vector2((float)Math.abs(Math.sin(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed,
                                        (float)Math.abs(Math.cos(loop.arrow.rotation)) * MathUtils.radiansToDegrees * speed);
            if((force.x > 0 || force.y > 0) && !loop.collection.launch) {
                loop.collection.launch = true;
                loop.player.torso.body.applyForceToCenter(force, true);
                loop.arrow.hide();
                loop.meter.hide();
                loop.game.sounds.play("catapult");
            }
            loop.arrow.start();
        }

        /**
         * Reset touch down timers and gliding.
         */
        loop.gliding = false;
        timer = 0;
        timerOn = false;

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        loop.UserInterfaceCamera.unproject(touchPos);

        /**
         * Stops arrow changing position and shows power meter if launch button is hold down. Changes tutorial text.
         */
        if(loop.game.collection.isGameOn() && loop.ui.shootButton.contains(touchPos.x, touchPos.y) && !loop.collection.launch) {
            loop.ui.shootButtonDown();
            loop.tutorial.setText(loop.game.localization.myBundle.get("tutorial2"));
            loop.meter.show();
            loop.arrow.pause();
        }

        /**
         * Start timer for gliding.
         */
        loop.gliding = true;
        timerOn = true;

        return true;
    }

    /**
     * Adds gliding timer time if timer is on.
     */
    public void timerTick() {
        if(timerOn) {
            timer += Gdx.graphics.getDeltaTime();
        }
    }
}
