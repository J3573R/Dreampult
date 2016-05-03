package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.Handlers.*;
import fi.tamk.dreampult.Helpers.Popup;
import fi.tamk.dreampult.Helpers.Saves;
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
    public ShittingRainbow shittingMiniRainbow;

    public Box2DDebugRenderer debug;
    public WorldHandler worldHandler;
    public InputHandler inputHandler;
    public CollisionHandler collision;
    public Meter meter;
    public Catapult catapult;
    public Popup tutorial;

    public Saves saves;

    public UserInterface ui;

    public Map map;

    public boolean gliding;
    public int bounces;
    public boolean pyjamaOn;
    public int retry;
    public boolean secondLaunch;

    private double accumultator = 0;
    private float timestep = 1 / 60f;

    public float timer = 0;

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

        slept = game.localization.myBundle.get("slept") + "0h 0min";
    }

    public void init(){
        saves = game.saves;
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
        shittingMiniRainbow = new ShittingRainbow(this);
        shittingMiniRainbow.setSize(2, 0.5f);
        shittingMiniRainbow.manualRotation = true;
        debug = new Box2DDebugRenderer();
        layout = new GlyphLayout();

        tutorial = new Popup(fontHandler);
        tutorial.bg = game.assets.manager.get("images/talents/box.png", Texture.class);

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
        ui.changeLang();
        map.initialize(this);
        ui.refreshScore();
        ui.init();

        game.GameCamera.position.set(game.collection.SCREEN_WIDTH_CENTER, game.collection.SCREEN_HEIGHT_CENTER, 0);
        game.GameCamera.update();

        catapult.reset();
        player.reset();
        secondLaunch = false;
        gliding = false;
        theEnd = false;

        if(saves.isPyjamaProtection()) {
            pyjamaOn = true;
        } else {
            pyjamaOn = false;
        }

        arrow.reset();
        meter.reset();
        angle = catapult.spoonRotation;
        point.set(catapult.spoonPosition.x + 0.5f, catapult.spoonPosition.y + 2f);
        center.set(2, 0);

        rotatedX = (float) (Math.cos(angle) * (point.x - center.x) - Math.sin(angle) * (point.y - center.y) + center.x);
        rotatedY = (float) (Math.sin(angle) * (point.x - center.x) + Math.cos(angle) * (point.y - center.y) + center.y);

        player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);

        if(saves.isAdditionalLaunch() && secondLaunch == false) {
            retry = 1;
        } else {
            retry = 0;
        }

        if(saves.isExtraBounces()) {
            bounces += 2;
        }

        tutorial.setText(game.localization.myBundle.get("tutorial1"));
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
            roof.body.setTransform(GameCamera.position.x, 51, 0);

            arrow.update();
            map.update();
            hit.update(Gdx.graphics.getDeltaTime());
            bounce.update(Gdx.graphics.getDeltaTime());
            shittingRainbow.update(Gdx.graphics.getDeltaTime());
            shittingMiniRainbow.update(Gdx.graphics.getDeltaTime());

            if (player.torso.body.getLinearVelocity().x < 0) {
                player.torso.body.setLinearVelocity(0, player.torso.body.getLinearVelocity().y);
            }

            if(!collection.launch) {
                player.torso.body.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                player.setBodypartVelocity(zeroVel);
            } else {
                if(player.torso.body.getLinearVelocity().x < 0.1f && player.torso.body.getLinearVelocity().y < 0.1f) {
                    timer += Gdx.graphics.getDeltaTime();
                } else {
                    timer = 0;
                }

                if(gliding && inputHandler.timer > 0.5f) {
                    Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    UserInterfaceCamera.unproject(touchPos);

                    if(Gdx.input.getY() < 270) {

                        if(player.torso.body.getLinearVelocity().x > 5f) {
                            if(shittingRainbow.getTime() <= 0) {
                                shittingMiniRainbow.play();
                                shittingMiniRainbow.setRotation(player.torso.body.getLinearVelocity().y * 2);
                            } else {
                                System.out.println(shittingRainbow.getTime());
                                shittingMiniRainbow.stop();
                            }
                            float force = (touchPos.y / 100 - 2.5f);
                            player.torso.body.applyForceToCenter(force * -2, force * 2, true);
                            System.out.println(player.torso.body.getLinearVelocity());
                        }
                    } else {
                        if(shittingRainbow.getTime() <= 0) {
                            shittingMiniRainbow.setRotation(player.torso.body.getLinearVelocity().y * 2);
                            shittingMiniRainbow.play();
                        } else {
                            shittingMiniRainbow.stop();
                        }
                        float force = (touchPos.y / 100 - 2.5f);
                        player.torso.body.applyForceToCenter(force * -2, force * 2, true);
                    }


                } else {
                    shittingMiniRainbow.stop();
                }

                inputHandler.timerTick();

                if(timer > 3f) {
                    if(retry <= 0){
                        int hour = (int) (player.torso.body.getPosition().x * 0.6f) / 60;
                        if(map.getLevel() == 1 && hour >= 8) {
                            saves.setLevel2(true);
                            saves.save();
                        }
                        if(map.getLevel() == 2 && hour >= 8) {
                            saves.setLevel3(true);
                            saves.save();
                        }

                        if(player.torso.body.getPosition().x > saves.getScore(map.getLevel())) {
                            saves.setScore(map.getLevel(), player.torso.body.getPosition().x);
                            ui.refreshScore();
                        }
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
                        //player.setTransform(rotatedX, rotatedY, catapult.spoonRotation);
                        player.reset();
                        player.reCreate();
                        retry -= 1;
                    }

                }

            }
            int hour = (int) (player.torso.body.getPosition().x * 0.6f) / 60;
            int minutes = (int) (player.torso.body.getPosition().x * 0.6f) % 60;
            slept = game.localization.myBundle.get("slept") + " " + hour + "h " + minutes + "min";

        } else {
            map.stopBackground();
        }


            // Draw stuff
            Gdx.gl.glClearColor(131 / 255f, 182 / 255f, 255 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();

            map.drawBackground(game.batch);

            shittingRainbow.draw(game.batch);
            shittingMiniRainbow.draw(game.batch);

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
            layout.setText(fontHandler.font, game.localization.myBundle.get("bounces") + " " + bounces);
            fontHandler.draw(game.batch, game.localization.myBundle.get("bounces") + " " + bounces, 940 / 2 - (int)layout.width / 2, 40);
            tutorial.draw(game.batch);
            ui.draw(game.batch);
            ui.drawPauseMenu(game.batch);
            ui.drawScoreScreen(game.batch);

            game.batch.setProjectionMatrix(GameCamera.combined);

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

    public boolean isReady() {
        return ready;
    }

    @Override
    public void dispose() {
        bounces = 0;
        map.dispose();
        System.gc();
    }
}
