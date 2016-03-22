package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 15.3.2016.
 */
public class LevelSelection implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera fontCamera;

    public Texture background;

    FontHandler font;

    // TODO: Tämä koodi tavalla tai toisella pitäis siirtää main menuun
    public LevelSelection(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        fontCamera = fCamera;

        //game.assets.loadTestMap();
        //game.assets.manager.finishLoading();

        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        font = new FontHandler();
        //font.text = "Select stage";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.justTouched()) {
            if (Gdx.input.isTouched()) {
                game.assets.loadTestMap();
                game.assets.manager.finishLoading();

                game.setScreen(new GameLoop(game, game.assets.manager, camera));
            }
        }

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 16, 9);

        game.batch.setProjectionMatrix(fontCamera.combined);

        font.draw(game.batch, "Select stage", 250, 400, Color.WHITE);
        font.draw(game.batch, "Prev.", 0, 200, Color.BLACK);
        font.draw(game.batch, "Next", 800, 200, Color.BLACK);
        font.draw(game.batch, "Stage 1", 250, 200, Color.BLACK);

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

    }
}
