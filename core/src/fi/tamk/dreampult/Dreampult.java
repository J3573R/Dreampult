package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import fi.tamk.dreampult.Handlers.AssetHandler;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();

	public SpriteBatch batch;
    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

	public Collection collection;

    public I18NBundle myBundle;

    Locale startLocale;

    Preferences prefs;

    Boolean finnish;

    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
        loadPreferences();

        language(startLocale);

		collection = new Collection();
		batch = new SpriteBatch();

        initCamera();

        assets.loadUi();
        assets.manager.finishLoading();

        savePreferences();

        setScreen(new TitleScreen(this, GameCamera, UserInterfaceCamera));
	}

    public void language(Locale locale) {
        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
    }

    public void MainMenu() {
        initCamera();
        collection.launch = false;
        collection.hidePauseMenu();
        collection.hideScoreScreen();
        setScreen(new TitleScreen(this, GameCamera, UserInterfaceCamera));
    }

	public void restart(int level) {
		collection.launch = false;
		collection.hidePauseMenu();
		collection.hideScoreScreen();
        initCamera();
        setScreen(new LoadingScreen(this, GameCamera, UserInterfaceCamera, level));
	}

    public void savePreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        prefs.putBoolean("finLanguage", finnish);

        prefs.flush();
    }

    public void loadPreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        boolean finLanguage = prefs.getBoolean("finLanguage");

        if(finLanguage) {
            startLocale = new Locale("fi", "FI");
            finnish = true;
        } else {
            startLocale = new Locale("en", "UK");
            finnish = false;
        }
    }

    public void initCamera(){
        GameCamera = new OrthographicCamera();
        GameCamera.setToOrtho(false, collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT);
        UserInterfaceCamera = new OrthographicCamera();
        UserInterfaceCamera.setToOrtho(false, 960, 540);
    }

	@Override
	public void render () {
		super.render();
	}
}
