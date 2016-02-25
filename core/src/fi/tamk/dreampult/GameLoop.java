package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.Handlers.CollisionHandler;
import fi.tamk.dreampult.Handlers.InputHandler;
import fi.tamk.dreampult.Handlers.WorldHandler;
import fi.tamk.dreampult.Objects.Arrow;
import fi.tamk.dreampult.Objects.Ground;
import fi.tamk.dreampult.Objects.Meter;
import fi.tamk.dreampult.Objects.Player;

/**
 * Created by Clown on 22.2.2016.
 */
public class GameLoop extends ScreenAdapter {
    public boolean GAME_ON;

    public AssetManager assets;

    // Arrow direction setup
    public final int UP = 1;
    public final int DOWN = 2;
    public int direction = UP;

    public World world;
    public Player player;
    public Arrow arrow;
    public Ground ground;

    public Dreampult game;
    public OrthographicCamera camera;
    public Box2DDebugRenderer debug;
    public WorldHandler worldHandler;
    public InputHandler inputHandler;
    public CollisionHandler collision;
    public Meter meter;

    public Texture background;
    public TextureRegion fullBackground;

    private double accumultator = 0;
    private float timestep = 1 / 60f;
    public boolean moveArrow;



    /**
     * Initialize variables for render.
     * @param game
     * @param camera
     */
    public GameLoop(Dreampult game, AssetManager assets, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.assets = assets;

        world = new World(new Vector2(0, -2f), true);
        collision = new CollisionHandler(this);
        world.setContactListener(collision);

        worldHandler = new WorldHandler(this);

        player = new Player(world, this);
        arrow = new Arrow(this);
        meter = new Meter(this);
        ground = new Ground(this);

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
        debug = new Box2DDebugRenderer();

        moveArrow = true;
        GAME_ON = true;
    }

    /**
     * Render the game.
     * @param delta
     */
    @Override
    public void render(float delta) {
        if ( GAME_ON ) {
            /**
             * Do Stuff
             */
            doPhysicsStep(delta);
            worldHandler.moveCamera();
            game.batch.setProjectionMatrix(camera.combined);

            worldHandler.tiledMapRenderer.setView(camera);


            if(moveArrow) {

                if(arrow.rotation > 0) {
                    direction = DOWN;
                }

                if(arrow.rotation < -1.6) {
                    direction = UP;
                }

                if(direction == DOWN) {
                    arrow.rotation -= Gdx.graphics.getDeltaTime();
                }
                if(direction == UP) {
                    arrow.rotation += Gdx.graphics.getDeltaTime();
                }

            }

            /**
             * Draw stuff
             */
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            worldHandler.tiledMapRenderer.render();

            game.batch.begin();

            meter.draw(game.batch);

            arrow.draw(game.batch);

            player.draw(game.batch);

            game.batch.end();
            debug.render(world, camera.combined);
        }



    }

    /**
     * Simulating all physic steps regardless fps.
     * @param delta
     */
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
