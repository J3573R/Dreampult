package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.tamk.dreampult.Handlers.AssetHandler;

public class Dreampult extends Game {
    public AssetHandler assets = new AssetHandler();

	public SpriteBatch batch;
    public OrthographicCamera camera;

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
        //assets.loadTestMap();
        //assets.manager.finishLoading();
        //setScreen(new GameLoop(this, assets.manager, camera));

		setScreen(new LevelSelection(this, camera));
	}

	@Override
	public void render () {
		super.render();
	}
}
