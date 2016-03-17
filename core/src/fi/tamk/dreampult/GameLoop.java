package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.Handlers.*;
import fi.tamk.dreampult.Objects.Arrow;
import fi.tamk.dreampult.Objects.Ground;
import fi.tamk.dreampult.Objects.Meter;
import fi.tamk.dreampult.Objects.Player;

/**
 * Created by Clown on 22.2.2016.
 */
public class GameLoop extends ScreenAdapter {
    public Dreampult game;
    public Collection collection;
    public AssetManager assets;



    public World world;
    public Player player;
    public Arrow arrow;
    public Ground ground;

    public OrthographicCamera camera;
    public Box2DDebugRenderer debug;
    public WorldHandler worldHandler;
    public InputHandler inputHandler;
    public CollisionHandler collision;
    public BackgroundHandler bg;
    public BackgroundHandler bg2;
    public BackgroundHandler bg3;
    public Meter meter;

    public UserInterface ui;

    private double accumultator = 0;
    private float timestep = 1 / 60f;

    /**
     * Initialize variables for render.
     * @param game
     * @param camera
     */
    public GameLoop(Dreampult game, AssetManager assets, OrthographicCamera camera) {
        this.game = game;
        collection = game.collection;
        this.camera = camera;
        this.assets = assets;

        world = new World(new Vector2(0, -1f), true);
        collision = new CollisionHandler(this);
        world.setContactListener(collision);

        worldHandler = new WorldHandler(this);

        player = new Player(world, this);
        arrow = new Arrow(this);
        meter = new Meter(this);
        ground = new Ground(this);
        bg = new BackgroundHandler( this,
                                    this.assets.get("images/background/back_clouds.png", Texture.class),
                                    30,
                                    17);
        bg2 = new BackgroundHandler(this,
                                    this.assets.get("images/background/middle_clouds.png", Texture.class),
                                    25,
                                    14);
        bg3 = new BackgroundHandler(this,
                                    this.assets.get("images/background/front_clouds.png", Texture.class),
                                    16,
                                    9);
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
        debug = new Box2DDebugRenderer();

        ui = new UserInterface(this);
        game.collection.start();
    }

    /**
     * Render the loop.
     * @param delta
     */
    @Override
    public void render(float delta) {
        if ( game.collection.isGameOn() ) {
            /**
             * Do Stuff
             */
            doPhysicsStep(delta);
            worldHandler.moveCamera();
            game.batch.setProjectionMatrix(camera.combined);

            worldHandler.tiledMapRenderer.setView(camera);
            arrow.update();

            if (player.torso.body.getLinearVelocity().x < 0) {
                player.torso.body.setLinearVelocity(0, player.torso.body.getLinearVelocity().y);
            }

            if (player.torso.body.getPosition().x >= 5) {
                bg.setSpeed(player.torso.body.getLinearVelocity().x * 0.2f);
                bg2.setSpeed(player.torso.body.getLinearVelocity().x * 0.4f);
                bg3.setSpeed(player.torso.body.getLinearVelocity().x * 0.6f);
            }
        } else {
            bg.setSpeed(0);
            bg2.setSpeed(0);
            bg3.setSpeed(0);
        }

            /**
             * Draw stuff
             */
            Gdx.gl.glClearColor(131, 182, 255, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            worldHandler.tiledMapRenderer.render();

            game.batch.begin();

            bg.draw(game.batch);
            bg2.draw(game.batch);
            bg3.draw(game.batch);

            meter.draw(game.batch);

            arrow.draw(game.batch);

            player.draw(game.batch);

            ui.draw(game.batch);

            game.batch.end();
            debug.render(world, camera.combined);
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

    @Override
    public void dispose() {

    }
}
