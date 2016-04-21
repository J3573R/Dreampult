package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.Handlers.*;
import fi.tamk.dreampult.Helpers.Popup;
import fi.tamk.dreampult.Maps.Map;
import fi.tamk.dreampult.Objects.HitEffect;
import fi.tamk.dreampult.Objects.Launching.Arrow;
import fi.tamk.dreampult.Objects.ShittingRainbow;
import fi.tamk.dreampult.Objects.Wall;
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
    GlyphLayout layout;

    public World world;
    public Player player;
    public Arrow arrow;
    public Wall ground;
    public Wall roof;
    public HitEffect hit;
    public HitEffect bounce;
    public ShittingRainbow shittingRainbow;

    public Box2DDebugRenderer debug;
    public WorldHandler worldHandler;
    public InputHandler inputHandler;
    public CollisionHandler collision;
    public Meter meter;
    public Catapult catapult;
    public Talents talents;
    public Popup tutorial;

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

    boolean ready;
    boolean initialized;

    /**
     * Initialize variables for render.
     * @param game
     */
    public GameLoop(Dreampult game, AssetManager assets) {
        ready = false;
        initialized = false;

        this.fontHandler = game.fontHandler;
        this.game = game;
        this.collection = game.collection;
        this.GameCamera = game.GameCamera;
        this.UserInterfaceCamera = game.UserInterfaceCamera;
        this.assets = assets;

        slept = game.myBundle.get("slept") + "0h 0min";
        loadPreferences();
    }

    public void init(){
        talents = new Talents();
        point = new Vector2();
        center = new Vector2();

        collision = new CollisionHandler(this);
        world = new World(new Vector2(0, -1f), true);
        world.setContactListener(collision);


        worldHandler = new WorldHandler(this);
        player = new Player(world, this);
        arrow = new Arrow(this);
        meter = new Meter(this);
        catapult = new Catapult(this);
        ground = new Wall(this);
        roof = new Wall(this);
        hit = new HitEffect(this, false);
        bounce = new HitEffect(this, true);
        ui = new UserInterface(this);
        shittingRainbow = new ShittingRainbow(this);
        debug = new Box2DDebugRenderer();
        layout = new GlyphLayout();

        tutorial = new Popup(fontHandler);
        tutorial.bg = game.assets.manager.get("images/ui/text_button_grey.png", Texture.class);

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
        endScreen = game.assets.manager.get("images/endScreen.png", Texture.class);
        initialized = true;
    }

    public void reset(Map map) {
        if(!initialized) {
            init();
        }
        this.map = map;
        map.initialize(this);

        game.GameCamera.position.set(8f, 4.5f, 0);
        game.GameCamera.update();

        catapult.reset();
        player.reset();
        secondLaunch = false;
        gliding = false;
        theEnd = false;

        arrow.reset();
        meter.reset();
        angle = catapult.spoonRotation;
        point.set(catapult.spoonPosition.x + 0.5f, catapult.spoonPosition.y + 2f);
        center.set(2, 0);

        rotatedX = (float) (Math.cos(angle) * (point.x - center.x) - Math.sin(angle) * (point.y - center.y) + center.x);
        rotatedY = (float) (Math.sin(angle) * (point.x - center.x) + Math.cos(angle) * (point.y - center.y) + center.y);

        //player.torso.body.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
        player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
        //player.setBodypartVelocity(zeroVel);
        //player.reset();

        if(talents.isAdditionalLaunch() && secondLaunch == false) {
            retry = 1;
        } else {
            retry = 0;
        }

        if(talents.isExtraBounces()) {
            bounces += 2;
        }

        tutorial.setText("Hold the button to begin!");
        tutorial.setPosition(collection.REAL_WIDTH / 2, collection.REAL_HEIGHT / 2);
        tutorial.show();
        ready = true;
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
            game.batch.setProjectionMatrix(GameCamera.combined);

            ground.body.setTransform(GameCamera.position.x, 0, 0);
            roof.body.setTransform(GameCamera.position.x, 50, 0);

            arrow.update();
            map.update();
            hit.update(Gdx.graphics.getDeltaTime());
            bounce.update(Gdx.graphics.getDeltaTime());
            shittingRainbow.update(Gdx.graphics.getDeltaTime());

            if(player.torso.body.getLinearVelocity().x > 25) {
                shittingRainbow.play();
            }

            if (player.torso.body.getLinearVelocity().x < 0) {
                player.torso.body.setLinearVelocity(0, player.torso.body.getLinearVelocity().y);
            }

            if(!collection.launch) {
                if(secondLaunch) {

                    //player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                }
                player.torso.body.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                //player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                player.setBodypartVelocity(zeroVel);
                //player.reset();

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
                        catapult.setPosition();
                        arrow.show();
                        map.clearMonsters(world);
                        meter.scale = 0;
                        secondLaunch = true;
                        rotatedX = player.torso.body.getPosition().x;
                        player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                        player.reset();
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

            shittingRainbow.draw(game.batch);

            meter.draw(game.batch);

            arrow.draw(game.batch);

            catapult.draw(game.batch);

            player.draw(game.batch);

            map.drawObjects(game.batch);

            if(hit.playing) {
                hit.draw(game.batch);
            }
            if(bounce.playing) {
                bounce.draw(game.batch);
            }


            game.batch.setProjectionMatrix(UserInterfaceCamera.combined);
            layout.setText(fontHandler.font, slept);
            fontHandler.draw(game.batch, slept, 940 / 2 - (int)layout.width / 2, 530);
            layout.setText(fontHandler.font, game.myBundle.get("bounces") + " " + bounces);
            fontHandler.draw(game.batch, game.myBundle.get("bounces") + " " + bounces, 940 / 2 - (int)layout.width / 2, 40);
            tutorial.draw(game.batch);
            ui.draw(game.batch);
            ui.drawPauseMenu(game.batch);
            ui.drawScoreScreen(game.batch);

            game.batch.setProjectionMatrix(GameCamera.combined);

            game.batch.end();
            //debug.render(world, GameCamera.combined);
        //System.out.println(Gdx.graphics.getFramesPerSecond());'
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

    public boolean isReady() {
        return ready;
    }

    @Override
    public void dispose() {
        map.dispose();
        System.gc();
    }
}
