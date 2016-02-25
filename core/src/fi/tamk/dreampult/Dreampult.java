package fi.tamk.dreampult;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dreampult extends Game {
    public final int SCREEN_WIDTH = 10;
    public final int SCREEN_HEIGHT = 5;

	public SpriteBatch batch;
    public OrthographicCamera camera;

    /**
     * Create and initialize Screen.
     */
	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		setScreen(new GameLoop(this, camera));
		//setScreen(new LevelSelection(this, camera));
	}

	@Override
	public void render () {
		super.render();
	}
}
