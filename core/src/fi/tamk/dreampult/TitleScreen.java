package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 25.2.2016.
 */
public class TitleScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera userInterfaceCamera;

    public Texture logo;

    public Texture background;

    public FontHandler font;

    public Texture settings;
    public boolean settingsPressed;

    public Texture exit;

    public Texture levelOne;
    public Texture lockedLevel;

    public Texture soundOn;
    public Texture soundOff;

    public Texture finFlag;
    public Texture britFlag;

    boolean finLanguage;

    public Rectangle finRectangle;
    public Rectangle britRectangle;

    public TitleScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        userInterfaceCamera = fCamera;

        logo = game.assets.manager.get("images/dreampult_logo.png", Texture.class);
        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);
        settings = game.assets.manager.get("images/ui/settings_button.png", Texture.class);
        exit = game.assets.manager.get("images/ui/exitButton.png", Texture.class);
        levelOne = game.assets.manager.get("images/levelOne.png", Texture.class);
        lockedLevel = game.assets.manager.get("images/lockedLevel.png", Texture.class);

        soundOn = game.assets.manager.get("images/ui/soundOn.png", Texture.class);
        soundOff = game.assets.manager.get("images/ui/soundOff.png", Texture.class);

        finFlag = game.assets.manager.get("images/finFlag.png", Texture.class);
        britFlag = game.assets.manager.get("images/britFlag.png", Texture.class);

        font = new FontHandler();

        settingsPressed = false;

        finLanguage = true;

        finRectangle = new Rectangle(760, 440, 100, 100);
        britRectangle = new Rectangle(860, 460, 100, 70);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.batch.setProjectionMatrix(camera.combined);

        Vector3 touchPoint = new Vector3();

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(((touchPoint.x >= 0 && touchPoint.x <= 2) && (touchPoint.y >= 7 && touchPoint.y <= 9)) && (!settingsPressed)) {
                System.out.println("Sound button pressed");
                settingsPressed = true;

            } else if((touchPoint.x >= 3 && touchPoint.x <= 6) && (touchPoint.y >= 3 && touchPoint.y <= 5) && (!settingsPressed)) {
                System.out.println("Level loading started");

                game.assets.loadTestMap();

                game.setScreen(new LoadingScreen(game, camera, userInterfaceCamera));

            } else if(settingsPressed) {
                settingsPressed = false;

            } else {
                System.out.println(touchPoint.x + " : " + touchPoint.y);
            }
        }

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 16, 9);
        game.batch.draw(logo, 3, 5, 9, 4);
        //game.batch.draw(settings, 0, 7, 2, 2);

        game.batch.draw(levelOne, 3, 3, 3, 2);
        game.batch.draw(lockedLevel, 6, 3, 3, 2);
        game.batch.draw(lockedLevel, 9, 3, 3, 2);

        game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(finRectangle.contains(touchPoint.x, touchPoint.y)) {
                finLanguage = true;
            } else if(britRectangle.contains(touchPoint.x, touchPoint.y)) {
                finLanguage = false;
            }
        }

        if(settingsPressed) {
            game.batch.draw(soundOff, 0, 440, 100, 100);
        } else {
            game.batch.draw(soundOn, 0, 440, 100, 100);
        }

        if(finLanguage) {
            game.batch.draw(finFlag, 760, 440, 100, 100);
            game.batch.draw(britFlag, 860, 460, 50, 20);
        } else {
            game.batch.draw(finFlag, 760, 440, 50, 50);
            game.batch.draw(britFlag, 860, 460, 100, 70);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logo.dispose();
        background.dispose();
    }
}
