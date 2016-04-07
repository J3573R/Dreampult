package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Locale;

import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 25.2.2016.
 */
public class TitleScreen implements Screen {

    public Dreampult game;

    public OrthographicCamera camera;

    public OrthographicCamera userInterfaceCamera;

    public Texture logo;

    public Texture background;

    public FontHandler font;

    public boolean soundPressed;

    public Texture levelOne;
    public Texture lockedLevel;

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

    public TitleScreen(Dreampult gam, OrthographicCamera camera, OrthographicCamera fCamera) {
        game = gam;

        this.camera = camera;
        userInterfaceCamera = fCamera;

        logo = game.assets.manager.get("images/dreampult_logo.png", Texture.class);
        background = game.assets.manager.get("images/menu_tausta.png", Texture.class);

        levelOne = game.assets.manager.get("images/levelOne.png", Texture.class);
        lockedLevel = game.assets.manager.get("images/lockedLevel.png", Texture.class);

        soundOn = game.assets.manager.get("images/ui/soundOn.png", Texture.class);
        soundOff = game.assets.manager.get("images/ui/soundOff.png", Texture.class);

        finFlag = game.assets.manager.get("images/finFlag.png", Texture.class);
        britFlag = game.assets.manager.get("images/britFlag.png", Texture.class);

        font = new FontHandler();

        soundPressed = false;

        finLanguage = false;

        finRectangle = new Rectangle(760, 440, 100, 100);
        britRectangle = new Rectangle(860, 460, 100, 70);

        soundRectangle = new Rectangle(0, 440, 100, 100);

        firstLevelRectangle = new Rectangle(180, 180, 180, 120);
        secondLevelRectangle = new Rectangle(360, 180, 180, 120);
        thirdLevelRectangle = new Rectangle(540, 180, 180, 120);

        finLocale = new Locale("fi", "FI");
        engLocale = new Locale("en", "UK");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //game.batch.setProjectionMatrix(camera.combined);
        game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        Vector3 touchPoint = new Vector3();

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (firstLevelRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Level loading started");

                game.assets.loadTestMap();

                game.setScreen(new LoadingScreen(game, camera, userInterfaceCamera));

            } else if (finRectangle.contains(touchPoint.x, touchPoint.y) && !finLanguage) {
                finLanguage = true;
                game.language(finLocale);

            } else if (britRectangle.contains(touchPoint.x, touchPoint.y) && finLanguage) {
                finLanguage = false;
                game.language(engLocale);

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
        game.batch.draw(lockedLevel, 360, 180, 180, 120);
        game.batch.draw(lockedLevel, 540, 180, 180, 120);

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
