package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Clown on 22.2.2016.
 */
public class GameLoop extends ScreenAdapter {
    public final float WORLD_WIDTH = 1000;
    public final float WORLD_HEIGHT = 500;

    Dreampult game;
    OrthographicCamera camera;
    public World world;
    public Player player;
    Box2DDebugRenderer debug;
    Ground ground;

    private double accumultator = 0;
    private float timestep = 1 / 60f;

    public GameLoop(Dreampult game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;

        debug = new Box2DDebugRenderer();

        world = new World(new Vector2(0, -10f), true);
        player = new Player(world);
        ground = new Ground(this);

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debug.render(world, camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        doPhysicsStep(delta);
    }

    private void doPhysicsStep(float delta) {
        float frameTime = delta;

        if(delta > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumultator += frameTime;

        while (accumultator >= timestep) {
            world.step(timestep, 8, 3);
            accumultator -= timestep;
        }
    }


}
