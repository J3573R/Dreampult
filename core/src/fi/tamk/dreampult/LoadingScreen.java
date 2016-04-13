package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Handlers.FontHandler;
import fi.tamk.dreampult.Handlers.QuestionHandler;
import fi.tamk.dreampult.Helpers.Button;
import fi.tamk.dreampult.Helpers.Question;

/**
 * Created by DV6-6B20 on 15.3.2016.
 */
public class LoadingScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera fontCamera;

    public Texture background;

    public Texture blankFalse;
    public Texture blankTrue;

    public Texture falseTexture;
    public Texture trueTexture;

    public Rectangle truthRectangle;
    public Rectangle falseRectangle;

    FontHandler font;

    String loading;

    boolean loaded;

    QuestionHandler questionHandler;

    Question question;

    String positiveAnswer;
    String negativeAnswer;

    ShapeRenderer shapeRenderer;

    Button truthButton;
    Button falseButton;

//    Sound positiveSound;
//    Sound negativeSound;

    public LoadingScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;
        this.camera = camera;
        fontCamera = fCamera;

        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        blankFalse = game.assets.manager.get("images/ui/blankFalse.png", Texture.class);
        blankTrue = game.assets.manager.get("images/ui/blankTrue.png", Texture.class);

        falseTexture = game.assets.manager.get("images/ui/falseTexture.png", Texture.class);
        trueTexture = game.assets.manager.get("images/ui/trueTexture.png", Texture.class);

//        positiveSound = game.assets.manager.get("audio/soundEffects/positive.wav", Sound.class);
//        negativeSound= game.assets.manager.get("audio/soundEffects/negative.wav", Sound.class);

        font = new FontHandler(40);

        loaded = false;

        loading = game.myBundle.get("loading");

        truthRectangle = new Rectangle(960 / 4 - 50, 125, 200, 100);
        falseRectangle = new Rectangle(960 / 3 * 2 - 50, 125, 200, 100);

        questionHandler = new QuestionHandler(game);

        question = questionHandler.anyItem();

        positiveAnswer = game.myBundle.get("true");
        negativeAnswer = game.myBundle.get("false");

        shapeRenderer = new ShapeRenderer();

        truthButton = new Button(960 / 4 - 50, 125, 200, 100, positiveAnswer);
        truthButton.setAlpha(0f);
        truthButton.setTextColor(Color.BLACK);
        falseButton = new Button(960 / 3 * 2 - 50, 125, 200, 100, negativeAnswer);
        falseButton.setAlpha(0f);
        falseButton.setTextColor(Color.BLACK);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Vector3 touchPoint = new Vector3();

        if(loaded) {
            loading = game.myBundle.get("loaded");
            if(Gdx.input.justTouched()) {
                fontCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if (truthRectangle.contains(touchPoint.x, touchPoint.y)) {
                    //System.out.println("Truth chosen");
                    GameLoop gameLoop = new GameLoop(game, game.assets.manager, camera);
                    if(question.isTrue(true)){
//                        positiveSound.play();
                        gameLoop.bounces += 1;
                    } else {
//                        negativeSound.play();
                    }
                    game.setScreen(gameLoop);

                } else if (falseRectangle.contains(touchPoint.x, touchPoint.y)) {
                    GameLoop gameLoop = new GameLoop(game, game.assets.manager, camera);
                    if(question.isTrue(false)){
//                        positiveSound.play();
                        gameLoop.bounces += 1;
                    } else {
//                        negativeSound.play();
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
            game.batch.draw(trueTexture, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            truthButton.draw(shapeRenderer, game.batch);
            game.batch.draw(falseTexture, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
            falseButton.draw(shapeRenderer, game.batch);

        } else {
            game.batch.draw(blankTrue, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            game.batch.draw(blankFalse, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
        }

        GlyphLayout layout = new GlyphLayout(font.font, loading);

        game.batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(fontCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(960 / 2 - (layout.width / 2) * 1.1f,
                400 - (layout.height * 1.5f),
                layout.width * 1.1f,
                layout.height * 2);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        game.batch.begin();

        font.font.draw(game.batch, layout, 960 / 2 - layout.width / 2, 400);

        question.initializeLayout(font);

        game.batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(fontCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(960 / 2 - (question.layout.width / 2) * 1.05f,
                                        300 - (question.layout.height * 1.5f),
                                        question.layout.width * 1.05f,
                                        question.layout.height * 2);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        game.batch.begin();

        question.draw(game.batch);

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
