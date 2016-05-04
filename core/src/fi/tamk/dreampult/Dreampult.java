package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Locale;

import fi.tamk.dreampult.Handlers.AssetHandler;
import fi.tamk.dreampult.Handlers.FontHandler;
import fi.tamk.dreampult.Handlers.Localization;
import fi.tamk.dreampult.Helpers.MusicPlayer;
import fi.tamk.dreampult.Helpers.Saves;
import fi.tamk.dreampult.Helpers.Sounds;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();
    public FontHandler fontHandler;
    public Localization localization;

	public SpriteBatch batch;
    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

	public Collection collection;

    public TitleScreen titleScreen;
    public LoadingScreen loadingScreen;
    public GameLoop gameLoop;
    public TalentsScreen talentsScreen;

    public Saves saves;
    public Sounds sounds;

    public MusicPlayer player;

    boolean splash;


    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
        localization = new Localization(assets, this);

        splash = true;

		collection = new Collection();
		batch = new SpriteBatch();
        saves = new Saves();

        GameCamera = new OrthographicCamera();
        GameCamera.setToOrtho(false, collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT);
        UserInterfaceCamera = new OrthographicCamera();
        UserInterfaceCamera.setToOrtho(false, 960, 540);

        assets.loadUi();
        assets.loadSoundEffects();
        assets.loadSplash();
        assets.loadMusic();
        assets.manager.finishLoading();

        sounds = new Sounds(assets);
        fontHandler = new FontHandler();
        titleScreen = new TitleScreen(this);
        loadingScreen = new LoadingScreen(this);
        gameLoop = new GameLoop(this, assets.manager);
        talentsScreen = new TalentsScreen(gameLoop);
        talentsScreen.init();
        player = new MusicPlayer(assets.manager, saves);

        setScreen(titleScreen);
	}

    public void MainMenu() {
        collection.launch = false;
        collection.hidePauseMenu();
        collection.hideScoreScreen();
        player.playLevel1();
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

	@Override
	public void render () {
        super.render();
        //System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    public void setScreen(Screen screen) {
        super.setScreen(screen);
        System.gc();
    }
}
