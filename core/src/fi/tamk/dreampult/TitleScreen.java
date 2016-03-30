package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 25.2.2016.
 */
public class TitleScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera fontCamera;

    public Texture logo;

    public Texture background;

    public FontHandler font;

    public Texture settings;

    public Texture exit;

    public Texture levelOne;
    public Texture lockedLevel;

    public TitleScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        fontCamera = fCamera;

        //game.assets.loadUi();
        //game.assets.manager.finishLoading();

        logo = game.assets.manager.get("images/dreampult_logo.png", Texture.class);
        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);
        settings = game.assets.manager.get("images/ui/settings_button.png", Texture.class);
        exit = game.assets.manager.get("images/ui/exitButton.png", Texture.class);
        levelOne = game.assets.manager.get("images/levelOne.png", Texture.class);
        lockedLevel = game.assets.manager.get("images/lockedLevel.png", Texture.class);

        font = new FontHandler();
        //font.text = "Press anything to start";

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

            if((touchPoint.x >= 0 && touchPoint.x <= 1) && (touchPoint.y >= 8 && touchPoint.y <= 9)) {
                System.out.println("Settings button pressed");

            } else if((touchPoint.x >= 15 && touchPoint.x <= 16) && (touchPoint.y >= 8 && touchPoint.y <= 9)) {
                System.out.println("Exit button pressed");

            } else if((touchPoint.x >= 3 && touchPoint.x <= 6) && (touchPoint.y >= 3 && touchPoint.y <= 5)) {
                System.out.println("Level loading started");

                game.assets.loadTestMap();
                //game.assets.manager.finishLoading();

                game.setScreen(new LoadingScreen(game, camera, fontCamera));
                //game.setScreen(new GameLoop(game, game.assets.manager, GameCamera));

            } else {
                System.out.println(touchPoint.x + " : " + touchPoint.y);
            }
        }

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 16, 9);
        game.batch.draw(logo, 3, 5, 9, 4);
        game.batch.draw(settings, 0, 8, 1, 1);
        game.batch.draw(exit, 15, 8, 1, 1);

        game.batch.draw(levelOne, 3, 3, 3, 2);
        game.batch.draw(lockedLevel, 6, 3, 3, 2);
        game.batch.draw(lockedLevel, 9, 3, 3, 2);
        game.batch.draw(lockedLevel, 3, 1, 3, 2);
        game.batch.draw(lockedLevel, 6, 1, 3, 2);
        game.batch.draw(lockedLevel, 9, 1, 3, 2);

//      game.batch.setProjectionMatrix(UserInterfaceCamera.combined);

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
