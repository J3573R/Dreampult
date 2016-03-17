package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    public TitleScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;

        fontCamera = fCamera;

        //game.assets.loadUi();
        //game.assets.manager.finishLoading();

        logo = game.assets.manager.get("images/dreampult_logo.png", Texture.class);
        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        font = new FontHandler();
        //font.text = "Press anything to start";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.justTouched()) {
            if (Gdx.input.isTouched()) {
                //game.assets.loadTestMap();
                //game.assets.manager.finishLoading();

                game.setScreen(new LevelSelection(game, camera, fontCamera));
            }
        }

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 16, 9);
        game.batch.draw(logo, 3, 4, 9, 4);

        game.batch.setProjectionMatrix(fontCamera.combined);

        font.draw(game.batch, "Press anything to start", 250, 250, Color.BLACK);

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
