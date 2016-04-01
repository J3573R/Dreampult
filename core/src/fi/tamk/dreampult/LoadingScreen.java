package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 15.3.2016.
 */
public class LoadingScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera fontCamera;

    public Texture background;

    public Texture blankButton;
    public Texture trueButton;
    public Texture falseButton;

    FontHandler font;

    String loading;

    boolean loaded;


    public LoadingScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        fontCamera = fCamera;

        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        blankButton = game.assets.manager.get("images/ui/blankButton.png", Texture.class);
        trueButton = game.assets.manager.get("images/ui/trueButton.png", Texture.class);
        falseButton = game.assets.manager.get("images/ui/falseButton.png", Texture.class);

        font = new FontHandler();

        loaded = false;

        loading = "Loading...";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(loaded) {
            loading = "Loaded!";
            if(Gdx.input.justTouched()) {
                game.setScreen(new GameLoop(game, game.assets.manager, camera));
            }
        }

        if(game.assets.manager.update()) {
            loaded = true;
        }

        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 16, 9);
        game.batch.setProjectionMatrix(fontCamera.combined);

        if(loaded) {
            game.batch.draw(trueButton, 960 / 4 - 50, 125, 200, 100);
            game.batch.draw(falseButton, 960 / 3 * 2 - 50, 125, 200, 100);
        } else {
            game.batch.draw(blankButton, 960 / 4 - 50, 125, 200, 100);
            game.batch.draw(blankButton, 960 / 3 * 2 - 50, 125, 200, 100);
        }

        GlyphLayout layout = new GlyphLayout(font.font, loading);

        font.font.draw(game.batch, layout, 960 / 2 - layout.width / 2, 400);

        layout.setText(font.font, "Sleep is for the weak");
        font.font.draw(game.batch, layout, 960 / 2 - layout.width / 2, 300);

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
