package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by DV6-6B20 on 25.2.2016.
 */
public class LevelSelection implements Screen {

    Dreampult game;

    OrthographicCamera camera;

    Texture img;

    public LevelSelection(Dreampult gam, OrthographicCamera camera) {
        game = gam;
        this.camera = camera;

        img = new Texture("./images/badlogic.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.justTouched()) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameLoop(game, camera));
            }
        }

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
