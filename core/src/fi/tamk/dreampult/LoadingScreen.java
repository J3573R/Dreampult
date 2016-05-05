package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import fi.tamk.dreampult.Helpers.ConfirmationText;
import fi.tamk.dreampult.Helpers.Question;
import fi.tamk.dreampult.Maps.Map;
import fi.tamk.dreampult.Maps.Maps;

/**
 * @author Kalle Heinonen
 */
public class LoadingScreen implements Screen {

    public Dreampult game;

    // Cameras used for drawing
    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

    // The background of the loading screen
    public Texture background;

    // Textures for the buttons while loading
    public Texture blankFalse;
    public Texture blankTrue;

    // Textures for the buttons when loading is done
    public Texture falseTexture;
    public Texture trueTexture;

    // Rectangles for the buttons
    public Rectangle truthRectangle;
    public Rectangle falseRectangle;

    // Buttons for answering
    Button truthButton;
    Button falseButton;

    // The text for the buttons
    String positiveAnswer;
    String negativeAnswer;

    FontHandler fontHandler;

    String loading;

    boolean loaded;

    QuestionHandler questionHandler;

    Question question;

    ShapeRenderer shapeRenderer;

    Map map;
    Maps maps;

    // GlyphLayout for the loading text
    GlyphLayout layout;

    int level;

    Vector3 touchPoint;

    ConfirmationText confirmationText;

    // Used for determining if the answer was correct or not
    public int answerInteger = 0;
    final int CORRECT = 1;
    final int INCORRECT = 2;

    /**
     * Creates the loading screen.
     *
     * @param game used for accessing the cameras, localization, and fontHandler
     */
    public LoadingScreen(Dreampult game) {
        this.game = game;
        this.GameCamera = game.GameCamera;
        this.UserInterfaceCamera = game.UserInterfaceCamera;
        maps = new Maps();
        this.fontHandler = game.fontHandler;

        loading = game.localization.myBundle.get("loading");
        layout = new GlyphLayout(fontHandler.font, loading);

        truthRectangle = new Rectangle(960 / 4 - 50, 125, 200, 100);
        falseRectangle = new Rectangle(960 / 3 * 2 - 50, 125, 200, 100);

        questionHandler = new QuestionHandler(game);

        shapeRenderer = new ShapeRenderer();

        truthButton = new Button(fontHandler);
        falseButton = new Button(fontHandler);

        touchPoint = new Vector3();

        confirmationText = new ConfirmationText(game);
    }

    /**
     * Resets the loading screen
     *
     * @param level the chosen level, used for drawing the correct background
     */
    public void reset(int level) {
        this.level = level;
        loaded = false;
        loading = game.localization.myBundle.get("loading");
        positiveAnswer = game.localization.myBundle.get("true");
        negativeAnswer = game.localization.myBundle.get("false");

        switch (level) {
            case 1:
                game.assets.loadLevel1();
                background = game.assets.manager.get("images/background/level1/layer4.png", Texture.class);
                break;
            case 2:
                game.assets.loadLevel2();
                background = game.assets.manager.get("images/background/level2/layer4.png", Texture.class);
                break;
            case 3:
                game.assets.loadLevel3();
                background = game.assets.manager.get("images/background/level3/layer4.png", Texture.class);
                break;
        }


        blankFalse = game.assets.manager.get("images/ui/blankFalse.png", Texture.class);
        blankTrue = game.assets.manager.get("images/ui/blankTrue.png", Texture.class);
        falseTexture = game.assets.manager.get("images/ui/falseTexture.png", Texture.class);
        trueTexture = game.assets.manager.get("images/ui/trueTexture.png", Texture.class);

        loading = game.localization.myBundle.get("loading");
        layout.setText(fontHandler.font, loading);

        question = questionHandler.anyItem();
        question.initializeLayout(fontHandler);
        truthButton.setButton(960 / 4 - 50, 125, 200, 100, positiveAnswer);
        falseButton.setButton(960 / 3 * 2 - 50, 125, 200, 100, negativeAnswer);
        truthButton.createText();
        falseButton.createText();

        truthButton.setTextColor(Color.BLACK);
        falseButton.setTextColor(Color.BLACK);

        truthButton.setAlpha(0f);
        falseButton.setAlpha(0f);

        game.GameCamera.position.set(game.collection.SCREEN_WIDTH_CENTER, game.collection.SCREEN_HEIGHT_CENTER, 0);
        game.GameCamera.update();
        game.UserInterfaceCamera.position.set(480, 270, 0);
        game.UserInterfaceCamera.update();

    }

    @Override
    /**
     * Loads the assets shared by all levels.
     */
    public void show() {
        game.assets.loadObjects();
    }

    @Override
    public void render(float delta) {
        if(loaded) {

            if(game.gameLoop.ready) {
                loading = game.localization.myBundle.get("loaded");
                layout.setText(fontHandler.font, loading);

                // Checks what the player answered, and compares the answer to the correct answer
                if (Gdx.input.justTouched()) {
                    UserInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                    if (truthRectangle.contains(touchPoint.x, touchPoint.y)) {
                        if (question.isTrue(true)) {
                            game.gameLoop.bounces += 1;
                            game.sounds.play("positive");
                            answerInteger = CORRECT;
                        } else {
                            game.sounds.play("negative");
                            answerInteger = INCORRECT;
                        }

                    } else if (falseRectangle.contains(touchPoint.x, touchPoint.y)) {
                        if (question.isTrue(false)) {
                            game.gameLoop.bounces += 1;
                            game.sounds.play("positive");
                            answerInteger = CORRECT;
                        } else {
                            game.sounds.play("negative");
                            answerInteger = INCORRECT;
                        }

                    }
                }
            }
        } else {
            // Loads the next level
            if(game.assets.manager.update()) {
                this.map = maps.loadMap(level, game.assets.manager, game.collection);
                game.gameLoop.reset(map);
                game.talentsScreen.init();
                if(game.gameLoop.ready) {
                    loaded = true;
                }
            }
        }

        game.batch.setProjectionMatrix(GameCamera.combined);

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, game.collection.SCREEN_WIDTH, game.collection.SCREEN_HEIGHT);
        game.batch.setProjectionMatrix(UserInterfaceCamera.combined);

        // Draws the buttons based on the status of loading
        if(loaded) {
            game.batch.draw(trueTexture, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            truthButton.drawShape(shapeRenderer, game.batch);
            game.batch.draw(falseTexture, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
            falseButton.drawShape(shapeRenderer, game.batch);

        // If the loading isn't done yet, draws the buttons as blanks
        } else {
            game.batch.draw(blankTrue, truthRectangle.getX(), truthRectangle.getY(), truthRectangle.getWidth(), truthRectangle.getHeight());
            game.batch.draw(blankFalse, falseRectangle.getX(), falseRectangle.getY(), falseRectangle.getWidth(), falseRectangle.getHeight());
        }

        layout.setText(fontHandler.font, loading);

        game.batch.end();

        // Draws a background for the loading text, making it easier to read
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(UserInterfaceCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(960 / 2 - (layout.width / 2) * 1.1f,
                400 - (layout.height * 1.5f),
                layout.width * 1.1f,
                layout.height * 2);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        game.batch.begin();

        // Draws the loading text
        fontHandler.font.draw(game.batch, layout, 960 / 2 - layout.width / 2, 400);

        game.batch.end();

        // Draws a background for the question, making it easier to read
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(UserInterfaceCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(960 / 2 - (question.layout.width / 2) * 1.05f,
                                        300 - (question.layout.height * 1.5f),
                                        question.layout.width * 1.05f,
                                        question.layout.height * 2);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        game.batch.begin();

        // Draws the question
        question.draw(game.batch);

        // Draws the correct confirmationText
        if (answerInteger == CORRECT) {
            confirmationText.drawText(true, true);
        } else if (answerInteger == INCORRECT) {
            confirmationText.drawText(false, false);
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

    }
}
