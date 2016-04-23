package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.dreampult.Handlers.AssetHandler;
import fi.tamk.dreampult.Handlers.FontHandler;
import fi.tamk.dreampult.Handlers.LocalizationHandler;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();
    public FontHandler fontHandler;
    public LocalizationHandler localizationHandler;

	public SpriteBatch batch;
    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

	public Collection collection;

    public I18NBundle myBundle;

    Locale startLocale;

    Preferences prefs;

    Boolean finnish;

    public TitleScreen titleScreen;
    public LoadingScreen loadingScreen;
    public GameLoop gameLoop;
    public TalentsScreen talentsScreen;

    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
        loadPreferences();

        localizationHandler = new LocalizationHandler(this, startLocale);

        //setLocale(startLocale);

        myBundle = localizationHandler.getBundle();

		collection = new Collection();
		batch = new SpriteBatch();

        GameCamera = new OrthographicCamera();
        GameCamera.setToOrtho(false, collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT);
        UserInterfaceCamera = new OrthographicCamera();
        UserInterfaceCamera.setToOrtho(false, 960, 540);

        assets.loadUi();
        assets.manager.finishLoading();

        fontHandler = new FontHandler();
        titleScreen = new TitleScreen(this);
        loadingScreen = new LoadingScreen(this);
        gameLoop = new GameLoop(this, assets.manager);
        talentsScreen = new TalentsScreen(gameLoop);

        setScreen(titleScreen);
	}

//    public void setLocale(Locale locale) {
//        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
//        savePreferences();
//    }

    public void setLocale(Locale locale) {
        localizationHandler.setLocale(locale);
        myBundle = localizationHandler.getBundle();
        savePreferences(finnish);
    }

    public void MainMenu() {
        collection.launch = false;
        collection.hidePauseMenu();
        collection.hideScoreScreen();
        setScreen(titleScreen);
    }

	public void restart(int level) {
		collection.launch = false;
		collection.hidePauseMenu();
		collection.hideScoreScreen();
        setScreen(loadingScreen);
        loadingScreen.reset(level);
        System.gc();
	}

    public void savePreferences(boolean finnish) {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        prefs.putBoolean("finLanguage", finnish);

        prefs.flush();
    }

    public void loadPreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        finnish = prefs.getBoolean("finLanguage");

        if(finnish = true) {
            startLocale = new Locale("fi", "FI");
        } else if(finnish = false){
            startLocale = new Locale("en", "UK");
        }
    }

	@Override
	public void render () {
        super.render();
        //Gdx.app.log("Java Heap", String.valueOf(Gdx.app.getJavaHeap()));
        //Gdx.app.log("Native Heap", String.valueOf(Gdx.app.getNativeHeap()));
        //Gdx.app.log("FPS:", String.valueOf(Gdx.graphics.getFramesPerSecond()));
    }

    public void setScreen(Screen screen) {
        super.setScreen(screen);
        System.gc();
    }


}
