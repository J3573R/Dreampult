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

    public Texture logo;

    public Texture background;

    public boolean soundPressed;

    public Texture levelOne;
    public Texture levelTwo;
    public Texture levelTree;

    public Texture lockedLevelTwo;
    public Texture lockedLevelTree;

    public Texture soundOn;
    public Texture soundOff;

    public Texture finFlag;
    public Texture britFlag;

    boolean finLanguage;

    public Rectangle finRectangle;
    public Rectangle britRectangle;

    public Rectangle soundRectangle;

    public Rectangle firstLevelRectangle;
    public Rectangle secondLevelRectangle;
    public Rectangle thirdLevelRectangle;

    Locale finLocale;
    Locale engLocale;

    public TitleScreen(Dreampult game) {
        this.game = game;
        finLanguage = false;
        finLocale = new Locale("fi", "FI");
        engLocale = new Locale("en", "UK");
        //loadPreferences();
        game.loadPreferences();
        this.GameCamera = game.GameCamera;
        this.userInterfaceCamera = game.UserInterfaceCamera;

        logo = game.assets.manager.get("images/dreampult_logo.png", Texture.class);
        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        levelOne = game.assets.manager.get("images/title/level1_open.png", Texture.class);
        levelTwo = game.assets.manager.get("images/title/level2_open.png", Texture.class);
        levelTree = game.assets.manager.get("images/title/level3_open.png", Texture.class);

        lockedLevelTwo = game.assets.manager.get("images/title/level2_locked.png", Texture.class);
        lockedLevelTree = game.assets.manager.get("images/title/level3_locked.png", Texture.class);

        soundOn = game.assets.manager.get("images/ui/soundOn.png", Texture.class);
        soundOff = game.assets.manager.get("images/ui/soundOff.png", Texture.class);

        finFlag = game.assets.manager.get("images/finFlag.png", Texture.class);
        britFlag = game.assets.manager.get("images/britFlag.png", Texture.class);

        soundPressed = false;

        finRectangle = new Rectangle(760, 440, 100, 100);
        britRectangle = new Rectangle(860, 460, 100, 70);

        soundRectangle = new Rectangle(0, 440, 100, 100);

        firstLevelRectangle = new Rectangle(180, 180, 180, 120);
        secondLevelRectangle = new Rectangle(360, 180, 180, 120);
        thirdLevelRectangle = new Rectangle(540, 180, 180, 120);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //game.batch.setProjectionMatrix(GameCamera.combined);
        game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        Vector3 touchPoint = new Vector3();

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

            } else if (finRectangle.contains(touchPoint.x, touchPoint.y) && !finLanguage) {
                finLanguage = true;

                game.setLocale(finLocale);
                game.savePreferences();

            } else if (britRectangle.contains(touchPoint.x, touchPoint.y) && finLanguage) {
                finLanguage = false;

                game.setLocale(engLocale);
                game.savePreferences();

            } else if (((soundRectangle.contains(touchPoint.x, touchPoint.y))) && !soundPressed) {
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

        game.batch.draw(logo, 180, 300, 540, 240);

        game.batch.draw(levelOne, 180, 180, 180, 120);
        game.batch.draw(levelTwo, 360, 180, 180, 120);
        game.batch.draw(levelTree, 540, 180, 180, 120);


        if(soundPressed) {
            game.batch.draw(soundOff, soundRectangle.getX(), soundRectangle.getY(),
                            soundRectangle.getWidth(), soundRectangle.getHeight());
        } else {
            game.batch.draw(soundOn, soundRectangle.getX(), soundRectangle.getY(),
                            soundRectangle.getWidth(), soundRectangle.getHeight());
        }

        if(finLanguage) {
            game.batch.draw(finFlag, finRectangle.getX(), finRectangle.getY(), 100, 100);
            game.batch.draw(britFlag, britRectangle.getX(), britRectangle.getY(), 50, 20);
        } else {
            game.batch.draw(finFlag, finRectangle.getX(), finRectangle.getY(), 50, 50);
            game.batch.draw(britFlag, britRectangle.getX(), britRectangle.getY(), 100, 70);
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
        logo.dispose();
        background.dispose();
    }
}
