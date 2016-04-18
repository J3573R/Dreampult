package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fi.tamk.dreampult.Handlers.*;
import fi.tamk.dreampult.Maps.Map;
import fi.tamk.dreampult.Maps.Maps;
import fi.tamk.dreampult.Objects.Collision.Objects;
import fi.tamk.dreampult.Objects.HitEffect;
import fi.tamk.dreampult.Objects.Launching.Arrow;
import fi.tamk.dreampult.Objects.Ground;
import fi.tamk.dreampult.Objects.Launching.Catapult;
import fi.tamk.dreampult.Objects.Launching.Meter;
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
    public Meter meter;
    public Catapult catapult;
    public Talents talents;

    public UserInterface ui;

    public Map map;

    public boolean gliding;
    public int bounces;
    public int retry;
    public boolean secondLaunch;

    private double accumultator = 0;
    private float timestep = 1 / 60f;

    private float timer = 0;

    String slept;

    public Texture endScreen;
    public boolean theEnd;

    float angle;
    Vector2 point;
    Vector2 center;
    float rotatedX;
    float rotatedY;
    Vector2 zeroVel = new Vector2(0,0);

    /**
     * Initialize variables for render.
     * @param game
     */
    public GameLoop(Dreampult game, AssetManager assets, Map map) {

        fontHandler = new FontHandler(24);
        this.game = game;
        collection = game.collection;
        this.GameCamera = game.GameCamera;
        game.GameCamera.position.set(8f, 4.5f, 0);
        this.UserInterfaceCamera = game.UserInterfaceCamera;
        this.assets = assets;
        talents = new Talents();
        secondLaunch = false;
        point = new Vector2();
        center = new Vector2();

        world = new World(new Vector2(0, -1f), true);
        collision = new CollisionHandler(this);
        world.setContactListener(collision);

        worldHandler = new WorldHandler(this);

        player = new Player(world, this);
        arrow = new Arrow(this);
        meter = new Meter(this);
        catapult = new Catapult(this);
        ground = new Ground(this);
        hit = new HitEffect(this);

        gliding = false;

        this.map = map;
        map.initialize(this);
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
        debug = new Box2DDebugRenderer();

        ui = new UserInterface(this);
        slept = game.myBundle.get("slept") + "0h 0min";
        game.collection.start();

        endScreen = game.assets.manager.get("images/endScreen.png", Texture.class);
        theEnd = false;

        if(talents.isAdditionalLaunch() && secondLaunch == false) {
            retry = 1;
        } else {
            retry = 0;
        }

        if(talents.isExtraBounces()) {
            bounces += 2;
        }

        loadPreferences();
    }

    /**
     * Render the loop.
     * @param delta
     */
    @Override
    public void render(float delta) {
        if ( game.collection.isGameOn() ) {

            doPhysicsStep(delta);

            worldHandler.moveCamera();
            ground.body.setTransform(GameCamera.position.x, 0, 0);
            game.batch.setProjectionMatrix(GameCamera.combined);

            arrow.update();
            map.update();
            hit.update(Gdx.graphics.getDeltaTime());

            if (player.torso.body.getLinearVelocity().x < 0) {
                player.torso.body.setLinearVelocity(0, player.torso.body.getLinearVelocity().y);
            }

            if(!collection.launch) {
                angle = catapult.spoonRotation;
                point.set(catapult.spoonPosition.x + 0.5f, catapult.spoonPosition.y + 2f);
                center.set(2, 0);

                rotatedX = (float) (Math.cos(angle) * (point.x - center.x) - Math.sin(angle) * (point.y - center.y) + center.x);
                rotatedY = (float) (Math.sin(angle) * (point.x - center.x) + Math.cos(angle) * (point.y - center.y) + center.y);

                if(secondLaunch) {
                    rotatedX = player.torso.body.getPosition().x;
                }

                player.torso.body.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                player.setBodypartVelocity(zeroVel);

            } else {
                if(player.torso.body.getLinearVelocity().x < 0.1f) {
                    timer += delta;
                } else {
                    timer = 0;
                }

                if(gliding && talents.isPyjamaGlide()) {
                    if(Gdx.input.getY() < 270) {
                        Vector2 vel = player.torso.body.getLinearVelocity();
                        vel.set(vel.x, vel.y + 0.03f);
                        player.torso.body.setLinearVelocity(vel);
                    } else {
                        Vector2 vel = player.torso.body.getLinearVelocity();
                        vel.set(vel.x, vel.y - 0.1f);
                        player.torso.body.setLinearVelocity(vel);
                    }
                }

                inputHandler.timerTick();

                if(timer > 2f) {
                    if(retry <= 0){
                        collection.pause();
                        collection.showScoreScreen();
                    } else {
                        collection.launch = false;
                        catapult.reset();
                        arrow.show();
                        map.clearMonsert();
                        meter.scale = 0;
                        secondLaunch = true;
                        retry -= 1;
                    }

                }
            }
            int hour = (int) (player.torso.body.getPosition().x * 0.8f) / 60;
            int minutes = (int) (player.torso.body.getPosition().x * 0.8f) % 60;
            slept = game.myBundle.get("slept") + " " + hour + "h " + minutes + "min";
        } else {
            map.stopBackground();
        }

            // Draw stuff
            Gdx.gl.glClearColor(131 / 255f, 182 / 255f, 255 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();

            map.drawBackground(game.batch);

            meter.draw(game.batch);

            arrow.draw(game.batch);

            catapult.draw(game.batch);

            player.draw(game.batch);

            map.drawObjects(game.batch);

            if(hit.playing) {
                hit.draw(game.batch);
            }

            game.batch.setProjectionMatrix(UserInterfaceCamera.combined);
            fontHandler.draw(game.batch, slept, 900 / 2, 530);
            //fontHandler.drawShape(game.batch, "Bounces:" + bounces, 900 / 2, 20);
            fontHandler.draw(game.batch, game.myBundle.get("bounces") + " " + bounces, 900 / 2, 20);
            ui.draw(game.batch);
            ui.drawPauseMenu(game.batch);
            ui.drawScoreScreen(game.batch);

            game.batch.end();
            //debug.render(world, GameCamera.combined);
        //System.out.println(Gdx.graphics.getFramesPerSecond());
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

    public void savePreferences() {

    }

    public void loadPreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        boolean growBouncy = prefs.getBoolean("growBouncy");
        boolean growSlippery = prefs.getBoolean("growSlippery");
        boolean boostLaunch = prefs.getBoolean("boostLaunch");
        boolean additionalLaunch = prefs.getBoolean("additionalLaunch");
        boolean extraBounces = prefs.getBoolean("extraBounces");
        boolean pyjamaGlide = prefs.getBoolean("pyjamaGlide");

        if(growBouncy) {
            talents.enableGrowBouncy();
            System.out.println("Grow Bouncy enabled");

        } else if(growSlippery) {
            talents.enableGrowSlippery();
            System.out.println("Grow Slippery enabled");

        } else if(boostLaunch) {
            talents.enableBoostLaunch();
            System.out.println("Boost Launch enabled");

        } else if(additionalLaunch) {
            talents.enableAdditionalLaunch();
            System.out.println("Additional Launch enabled");

        } else if(extraBounces) {
            talents.enableExtraBounces();
            System.out.println("Extra Bounces enabled");

        } else if(pyjamaGlide) {
            talents.enablePyjamaGlide();
            System.out.println("Pyjama Glide enabled");
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        System.gc();
    }
}
