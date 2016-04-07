package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Handlers.FontHandler;
import fi.tamk.dreampult.Handlers.QuestionHandler;
import fi.tamk.dreampult.Helpers.Question;

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

    public Rectangle truthRectangle;
    public Rectangle falseRectangle;

    FontHandler font;

    String loading;

    boolean loaded;

    QuestionHandler questionHandler;

    Question question;

    public boolean questionAnswer; //To be implemented later on

    public LoadingScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        fontCamera = fCamera;

        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        blankButton = game.assets.manager.get("images/ui/blankButton.png", Texture.class);
        trueButton = game.assets.manager.get("images/ui/trueButton.png", Texture.class);
        falseButton = game.assets.manager.get("images/ui/falseButton.png", Texture.class);

        font = new FontHandler(40);

        loaded = false;

        loading = "Loading...";

        truthRectangle = new Rectangle(960 / 4 - 50, 125, 200, 100);
        falseRectangle = new Rectangle(960 / 3 * 2 - 50, 125, 200, 100);

        questionHandler = new QuestionHandler();

        question = questionHandler.anyItem();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Vector3 touchPoint = new Vector3();

        if(loaded) {
            loading = "Loaded!";
            if(Gdx.input.justTouched()) {
                fontCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if (truthRectangle.contains(touchPoint.x, touchPoint.y)) {
                    //System.out.println("Truth chosen");
                    GameLoop gameLoop = new GameLoop(game, game.assets.manager, camera);
                    if(question.isTrue(true)){

                        gameLoop.bounces += 1;
                    }
                    game.setScreen(gameLoop);

                } else if (falseRectangle.contains(touchPoint.x, touchPoint.y)) {
                    GameLoop gameLoop = new GameLoop(game, game.assets.manager, camera);
                    if(question.isTrue(false)){
                        gameLoop.bounces += 1;
                    }
                    game.setScreen(gameLoop);
                } else {
                    System.out.println(touchPoint.x + " : " + touchPoint.y);
                    System.out.println(question);
                }
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
            game.batch.draw(trueButton, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            game.batch.draw(falseButton, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
        } else {
            game.batch.draw(blankButton, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            game.batch.draw(blankButton, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
        }

        GlyphLayout layout = new GlyphLayout(font.font, loading);

        font.font.draw(game.batch, layout, 960 / 2 - layout.width / 2, 400);

        question.draw(game.batch, font);

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
