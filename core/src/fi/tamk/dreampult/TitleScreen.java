package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import fi.tamk.dreampult.Helpers.Button;
import fi.tamk.dreampult.Helpers.Saves;

import java.util.Locale;

/**
 * @author Kalle Heinonen
 */
public class TitleScreen implements Screen {

    public Dreampult game;

    // Cameras used for drawing
    public OrthographicCamera GameCamera;
    public OrthographicCamera userInterfaceCamera;

    public Texture background;

    public boolean soundPressed;

    // Textures for the level icons
    public Texture levelOne;
    public Texture levelTwo;
    public Texture levelTree;

    // Rectangles for the level icons
    public Rectangle firstLevelRectangle;
    public Rectangle secondLevelRectangle;
    public Rectangle thirdLevelRectangle;

    // States of the sound button
    public Texture soundOn;
    public Texture soundOff;

    // The rectangle for the sound button
    public Rectangle soundRectangle;

    // States of the language flags
    public Texture finActive;
    public Texture britActive;

    // The rectangle for the language flags
    public Rectangle flagRectangle;

    // Timer used for drawing the splash screen
    float splashTimer;

    // Variants of the splash screen
    public Texture splashEng;
    public Texture splashFin;

    // Button for accessing talent screen
    public Button talentButton;

    // Button for resetting progress
    public Button resetProgress;

    Saves saves;

    Vector3 touchPoint;

    boolean drawingStarted = false;

    /**
     * Creates the title screen.
     *
     * @param game used for accessing the cameras, localization, and saves of Dreampult
     */
    public TitleScreen(Dreampult game) {
        this.game = game;

        this.GameCamera = game.GameCamera;
        this.userInterfaceCamera = game.UserInterfaceCamera;

        background = game.assets.manager.get("images/title/Menuscreen.png", Texture.class);

        saves = game.saves;

        levelOne = game.assets.manager.get("images/title/level1_open.png", Texture.class);

        // Checks the state of Level 2 and 3
        if(saves.isLevel2()) {
            levelTwo = game.assets.manager.get("images/title/level2_open.png", Texture.class);
        } else {
            levelTwo = game.assets.manager.get("images/title/level2_locked.png", Texture.class);
        }

        if(saves.isLevel3()) {
            levelTree = game.assets.manager.get("images/title/level3_open.png", Texture.class);
        } else {
            levelTree = game.assets.manager.get("images/title/level3_locked.png", Texture.class);
        }

        soundOn = game.assets.manager.get("images/ui/soundOn.png", Texture.class);
        soundOff = game.assets.manager.get("images/ui/soundOff.png", Texture.class);

        finActive = game.assets.manager.get("images/finActive.png", Texture.class);
        britActive = game.assets.manager.get("images/britActive.png", Texture.class);

        splashEng = game.assets.manager.get("images/splashEng.png", Texture.class);
        splashFin = game.assets.manager.get("images/splashFin.png", Texture.class);

        soundPressed = false;

        flagRectangle = new Rectangle(840, 0, 120, 60);

        soundRectangle = new Rectangle(0, 0, 100, 100);

        firstLevelRectangle = new Rectangle(960 / 5, 80, 180, 120);
        secondLevelRectangle = new Rectangle(960 / 5 * 2, 80, 180, 120);
        thirdLevelRectangle = new Rectangle(960 / 5 * 3, 80, 180, 120);

        talentButton = new Button(game.fontHandler, 960 / 2 - 150 / 2, 20, 150, 50, game.localization.myBundle.get("talents"));
        talentButton.setText(game.localization.myBundle.get("talents"));
        talentButton.buttonImage = game.assets.manager.get("images/ui/text_button.png", Texture.class);

        resetProgress = new Button(game.fontHandler, 960 - 280, 540 - 50, 280, 50, game.localization.myBundle.get("talents"));
        resetProgress.setText(game.localization.myBundle.get("reset"));
        resetProgress.buttonImage = game.assets.manager.get("images/ui/text_button.png", Texture.class);

        touchPoint = new Vector3();
        splashTimer = 5;
    }

    @Override
    /**
     * Refreshes the state of the levels as soon as title screen is shown.
     */
    public void show() {
        refreshLevels();
    }

    /**
     * Checks the status of the Levels, and changes the drawn images if needed.
     */
    public void refreshLevels(){
        if(saves.isLevel2()) {
            levelTwo = game.assets.manager.get("images/title/level2_open.png", Texture.class);
        } else {
            levelTwo = game.assets.manager.get("images/title/level2_locked.png", Texture.class);
        }

        if(saves.isLevel3()) {
            levelTree = game.assets.manager.get("images/title/level3_open.png", Texture.class);
        } else {
            levelTree = game.assets.manager.get("images/title/level3_locked.png", Texture.class);
        }
    }

    @Override
    public void render(float delta) {

        // Draws the splash screen
        if(splashTimer > 0) {
            if(Gdx.input.justTouched() ) {
                splashTimer = 0;
            }
            drawSplash(game.batch);
        } else {
            game.batch.setProjectionMatrix(userInterfaceCamera.combined);

            if(Gdx.input.justTouched()) {
                userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                // Resets the loading screen and makes it load the correct level
                // Also resets the questions so that they have the correct language
                if (firstLevelRectangle.contains(touchPoint.x, touchPoint.y)) {

                    game.loadingScreen.questionHandler.clearQuestions();
                    game.loadingScreen.questionHandler.initializeQuestions();
                    game.player.playLevel1();
                    game.setScreen(game.loadingScreen);
                    game.loadingScreen.reset(1);

                } else if(secondLevelRectangle.contains(touchPoint.x, touchPoint.y) && saves.isLevel2()){

                    game.loadingScreen.questionHandler.clearQuestions();
                    game.loadingScreen.questionHandler.initializeQuestions();
                    game.player.playLevel2();
                    game.setScreen(game.loadingScreen);
                    game.loadingScreen.reset(2);

                } else if(thirdLevelRectangle.contains(touchPoint.x, touchPoint.y) && saves.isLevel3()){

                    game.loadingScreen.questionHandler.clearQuestions();
                    game.loadingScreen.questionHandler.initializeQuestions();
                    game.player.playLevel3();
                    game.setScreen(game.loadingScreen);
                    game.loadingScreen.reset(3);

                // Changes the language
                } else if (flagRectangle.contains(touchPoint.x, touchPoint.y)) {
                    game.localization.changeLang();
                    talentButton.setText(game.localization.myBundle.get("talents"));
                    resetProgress.setText(game.localization.myBundle.get("reset"));

                // Changes the state of the sound
                }  else if (((soundRectangle.contains(touchPoint.x, touchPoint.y)))) {
                    if(saves.getSounds() == saves.ON) {
                        saves.setSounds(saves.OFF);
                    } else {
                        saves.setSounds(saves.ON);
                    }
                    saves.save();
                    game.player.toggle();

                // Takes the player to the Talent Screen
                } else if(talentButton.button.contains(touchPoint.x, touchPoint.y)){
                    game.collection.showTalentScreen();
                    game.setScreen(game.talentsScreen);

                // Resets progress
                } else if(resetProgress.button.contains(touchPoint.x, touchPoint.y)) {
                    game.saves.reset();
                    refreshLevels();
                }
            }

            Gdx.gl.glClearColor(0, 0.2f, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();

            game.batch.draw(background, 0, 0, 960, 540);

            game.batch.draw(levelOne, firstLevelRectangle.x, firstLevelRectangle.y, 180, 130);
            game.batch.draw(levelTwo, secondLevelRectangle.x, secondLevelRectangle.y, 180, 130);
            game.batch.draw(levelTree, thirdLevelRectangle.x, thirdLevelRectangle.y, 180, 130);

            talentButton.drawImage(game.batch);
            resetProgress.drawImage(game.batch);

            if(saves.getSounds() == saves.OFF) {
                game.batch.draw(soundOff, soundRectangle.getX(), soundRectangle.getY(),
                        soundRectangle.getWidth(), soundRectangle.getHeight());
            } else {
                game.batch.draw(soundOn, soundRectangle.getX(), soundRectangle.getY(),
                        soundRectangle.getWidth(), soundRectangle.getHeight());
            }

            if(game.localization.getLang().getLanguage().contains("fi")) {
                game.batch.draw(finActive, flagRectangle.getX(), flagRectangle.getY(), flagRectangle.getWidth(), flagRectangle.getHeight());
            } else {
                game.batch.draw(britActive, flagRectangle.getX(), flagRectangle.getY(), flagRectangle.getWidth(), flagRectangle.getHeight());
            }

            game.batch.end();
        }
        drawingStarted = true;
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

    /**
     * Draws the correct splash screen based on the current language
     * @param batch
     */
    public void drawSplash(SpriteBatch batch) {
        batch.setProjectionMatrix(userInterfaceCamera.combined);

        batch.begin();
        if(game.localization.lang.equals("fin")) {
            batch.draw(splashFin, 0, 0, 960, 540);
        } else {
            batch.draw(splashEng, 0, 0, 960, 540);
        }
        batch.end();
        if(game.assets.manager.update() && drawingStarted) {
            splashTimer -= Gdx.graphics.getDeltaTime();
        }
    }
}
