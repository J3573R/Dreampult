package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Locale;

/**
 * Created by DV6-6B20 on 25.2.2016.
 */
public class TitleScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera GameCamera;

    public OrthographicCamera userInterfaceCamera;

    public Texture background;

    public boolean soundPressed;

    public Texture levelOne;
    public Texture levelTwo;
    public Texture levelTree;

    public Texture lockedLevelTwo;
    public Texture lockedLevelTree;

    public Texture soundOn;
    public Texture soundOff;

    public Texture finActive;
    public Texture britActive;

    public Rectangle flagRectangle;

    public Rectangle soundRectangle;

    public Rectangle firstLevelRectangle;
    public Rectangle secondLevelRectangle;
    public Rectangle thirdLevelRectangle;

    Vector3 touchPoint;

    public TitleScreen(Dreampult game) {
        this.game = game;

        this.GameCamera = game.GameCamera;
        this.userInterfaceCamera = game.UserInterfaceCamera;

        background = game.assets.manager.get("images/title/Menuscreen.png", Texture.class);

        levelOne = game.assets.manager.get("images/title/level1_open.png", Texture.class);
        levelTwo = game.assets.manager.get("images/title/level2_open.png", Texture.class);
        levelTree = game.assets.manager.get("images/title/level3_open.png", Texture.class);

        lockedLevelTwo = game.assets.manager.get("images/title/level2_locked.png", Texture.class);
        lockedLevelTree = game.assets.manager.get("images/title/level3_locked.png", Texture.class);

        soundOn = game.assets.manager.get("images/ui/soundOn.png", Texture.class);
        soundOff = game.assets.manager.get("images/ui/soundOff.png", Texture.class);

        finActive = game.assets.manager.get("images/finActive.png", Texture.class);
        britActive = game.assets.manager.get("images/britActive.png", Texture.class);

        soundPressed = false;

        flagRectangle = new Rectangle(840, 0, 120, 60);

        soundRectangle = new Rectangle(0, 0, 100, 100);

        firstLevelRectangle = new Rectangle(180, 80, 180, 120);
        secondLevelRectangle = new Rectangle(360, 80, 180, 120);
        thirdLevelRectangle = new Rectangle(540, 80, 180, 120);

        touchPoint = new Vector3();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //game.batch.setProjectionMatrix(GameCamera.combined);
        game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (firstLevelRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Level loading started");

                game.setScreen(game.loadingScreen);
                game.loadingScreen.reset(1);

            } else if(secondLevelRectangle.contains(touchPoint.x, touchPoint.y)){
                System.out.println("Level 2 loading started");

                game.setScreen(game.loadingScreen);
                game.loadingScreen.reset(2);

            } else if(thirdLevelRectangle.contains(touchPoint.x, touchPoint.y)){
                System.out.println("Level 3 loading started");

                game.setScreen(game.loadingScreen);
                game.loadingScreen.reset(3);

            } else if (flagRectangle.contains(touchPoint.x, touchPoint.y)) {
                game.localization.changeLang();
                game.loadingScreen.questionHandler.clearQuestions();
                game.loadingScreen.questionHandler.initializeQuestions();

            }  else if (((soundRectangle.contains(touchPoint.x, touchPoint.y))) && !soundPressed) {
                System.out.println("Sound button pressed");
                soundPressed = true;

            } else if (((soundRectangle.contains(touchPoint.x, touchPoint.y))) && soundPressed) {
                System.out.println("Sound button pressed");
                soundPressed = false;

            } else {
                System.out.println(touchPoint.x + " : " + touchPoint.y);
            }
        }

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 960, 540);

        game.batch.draw(levelOne, firstLevelRectangle.x, firstLevelRectangle.y, 180, 130);
        game.batch.draw(levelTwo, secondLevelRectangle.x, secondLevelRectangle.y, 180, 130);
        game.batch.draw(levelTree, thirdLevelRectangle.x, thirdLevelRectangle.y, 180, 130);


        if(soundPressed) {
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
        background.dispose();
    }
}
