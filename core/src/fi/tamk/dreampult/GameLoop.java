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
import fi.tamk.dreampult.Objects.HitEffect;
import fi.tamk.dreampult.Objects.Launching.Arrow;
import fi.tamk.dreampult.Objects.Ground;
import fi.tamk.dreampult.Objects.Launching.Catapult;
import fi.tamk.dreampult.Objects.Launching.Meter;
import fi.tamk.dreampult.Objects.Monsters.Generator;
import fi.tamk.dreampult.Objects.Player;

/**
 * Created by Clown on 22.2.2016.
 */
public class GameLoop extends ScreenAdapter {
    public Dreampult game;
    public Collection collection;
    public AssetManager assets;

    public OrthographicCamera GameCamera;
    public OrthographicCamera UserInterfaceCamera;

    FontHandler fontHandler;


    public World world;
    public Player player;
    public Arrow arrow;
    public Ground ground;
    public HitEffect hit;

    public Box2DDebugRenderer debug;
    public WorldHandler worldHandler;
    public InputHandler inputHandler;
    public CollisionHandler collision;
    public Texture background;
    public BackgroundHandler bg;
    public BackgroundHandler bg2;
    public BackgroundHandler bg3;
    public Meter meter;
    public Catapult catapult;

    public UserInterface ui;

    public Generator pigMonsters;
    public Generator bedMonsters;

    private double accumultator = 0;
    private float timestep = 1 / 60f;

    private float timer = 0;

    String slept;

    public Texture endScreen;
    public boolean theEnd;

    /**
     * Initialize variables for render.
     * @param game
     * @param GameCamera
     */
    public GameLoop(Dreampult game, AssetManager assets, OrthographicCamera GameCamera) {
        fontHandler = new FontHandler(24);

        this.game = game;
        collection = game.collection;
        this.GameCamera = game.GameCamera;
        this.UserInterfaceCamera = game.UserInterfaceCamera;
        this.assets = assets;

        world = new World(new Vector2(0, -1f), true);
        collision = new CollisionHandler(this);
        world.setContactListener(collision);

        worldHandler = new WorldHandler(this);

        player = new Player(world, this);
        background = assets.get("images/background/bg2.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        pigMonsters = new Generator(this, "pig", 5, new Vector2(15, 0), new Vector2(20, 5));
        bedMonsters = new Generator(this, "bed", 20, new Vector2(1, 0), new Vector2(1, 0));
        arrow = new Arrow(this);
        meter = new Meter(this);
        catapult = new Catapult(this);
        ground = new Ground(this);
        hit = new HitEffect(this);

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
        ui.createPauseMenu();
        slept = "Slept: 0h 0min";
        game.collection.start();

        endScreen = game.assets.manager.get("images/endScreen.png", Texture.class);
        theEnd = false;
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
            ground.body.setTransform(GameCamera.position.x, 0, 0);
            game.batch.setProjectionMatrix(GameCamera.combined);

            arrow.update();
            pigMonsters.update();
            bedMonsters.update();
            hit.update(Gdx.graphics.getDeltaTime());

            if (player.torso.body.getLinearVelocity().x < 0) {
                player.torso.body.setLinearVelocity(0, player.torso.body.getLinearVelocity().y);
            }

            if (player.torso.body.getPosition().x >= 8) {
                bg.setSpeed(player.torso.body.getLinearVelocity().x * 0.2f);
                bg2.setSpeed(player.torso.body.getLinearVelocity().x * 0.4f);
                bg3.setSpeed(player.torso.body.getLinearVelocity().x * 0.6f);
            }

            if(!collection.launch) {
                float angle = catapult.spoonRotation;
                Vector2 point = new Vector2(catapult.spoonPosition.x + 0.5f, catapult.spoonPosition.y + 2f);
                Vector2 center = new Vector2(2, 0);
                float rotatedX = (float) (Math.cos(angle) * (point.x - center.x) - Math.sin(angle) * (point.y - center.y) + center.x);
                float rotatedY = (float) (Math.sin(angle) * (point.x - center.x) + Math.cos(angle) * (point.y - center.y) + center.y);

                player.torso.body.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
            } else {
                if(player.torso.body.getLinearVelocity().x < 0.1f) {
                    timer += delta;
                } else {
                    timer = 0;
                }

                if(timer > 1f) {
                    collection.pause();
                }
            }

            slept = "Slept: " + (int) player.torso.body.getPosition().x / 60 +
                    "h " + (int) player.torso.body.getPosition().x % 60 + "min";
        } else {
            bg.setSpeed(0);
            bg2.setSpeed(0);
            bg3.setSpeed(0);
        }

            /**
             * Draw stuff
             */
            Gdx.gl.glClearColor(131/255f, 182/255f, 255/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();

            game.batch.draw(background,
                            GameCamera.position.x - collection.SCREEN_WIDTH / 2,
                            GameCamera.position.y - collection.SCREEN_HEIGHT / 2 - 0.5f,
                            collection.SCREEN_WIDTH,
                            collection.SCREEN_HEIGHT);
            bg.draw(game.batch);
            bg2.draw(game.batch);
            bg3.draw(game.batch);

            meter.draw(game.batch);

            arrow.draw(game.batch);

            catapult.draw(game.batch);

            player.draw(game.batch);

            pigMonsters.draw(game.batch);
            bedMonsters.draw(game.batch);

            if(hit.playing) {
                hit.draw(game.batch);
            }

            game.batch.setProjectionMatrix(UserInterfaceCamera.combined);
            fontHandler.draw(game.batch, slept, 900 / 2, 530);
            //game.batch.setProjectionMatrix(GameCamera.combined);
            ui.draw(game.batch);
            ui.drawPauseMenu(game.batch);

            //if(theEnd) {
            //    game.batch.draw(endScreen, 0, 0, 16, 9);

            //    game.batch.setProjectionMatrix(fontCamera.combined);
                //fontHandler.draw(game.batch, slept, 900/ 2, 530); <-- Proper coordinates needed
            //}

            game.batch.end();
            debug.render(world, GameCamera.combined);
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
