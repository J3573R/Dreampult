package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.tamk.dreampult.Handlers.AssetHandler;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();

	public SpriteBatch batch;
    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

	public Collection collection;

    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
		collection = new Collection();
		batch = new SpriteBatch();

        GameCamera = new OrthographicCamera();
        GameCamera.setToOrtho(false, collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT);

        UserInterfaceCamera = new OrthographicCamera();
        UserInterfaceCamera.setToOrtho(false, 960, 540);

        assets.loadUi();
        assets.manager.finishLoading();
        //setScreen(new GameLoop(this, assets.manager, GameCamera));

		setScreen(new TitleScreen(this, GameCamera, UserInterfaceCamera));
	}

    public void MainMenu() {
        collection.launch = false;
        collection.hidePauseMenu();
        collection.hideScoreScreen();
        setScreen(new TitleScreen(this, GameCamera, UserInterfaceCamera));
    }

	public void restart() {
		collection.launch = false;
		collection.hidePauseMenu();
		collection.hideScoreScreen();
		setScreen( new GameLoop(this, assets.manager, GameCamera));
	}

	@Override
	public void render () {
		super.render();
	}
}
