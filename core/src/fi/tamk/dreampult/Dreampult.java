package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.tamk.dreampult.Handlers.AssetHandler;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();

	public SpriteBatch batch;
    public OrthographicCamera camera;
    public OrthographicCamera fontCamera;

	public Collection collection;

    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
		collection = new Collection();
		batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT);

        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, 960, 540);

        assets.loadUi();
        assets.manager.finishLoading();
        //setScreen(new GameLoop(this, assets.manager, camera));

		setScreen(new TitleScreen(this, camera, fontCamera));
	}

	@Override
	public void render () {
		super.render();
	}
}
