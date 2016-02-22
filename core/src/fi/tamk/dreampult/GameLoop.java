package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Clown on 22.2.2016.
 */
public class GameLoop extends ScreenAdapter {
    public final int WORLD_WIDTH = 100;
    public final int WORLD_HEIGHT = 50;

    // Arrow direction setup
    final int UP = 1;
    final int DOWN = 2;
    int direction = UP;

    public World world;
    public Player player;
    public Arrow arrow;
    public Ground ground;

    Dreampult game;
    OrthographicCamera camera;
    Box2DDebugRenderer debug;
    WorldHandler worldHandler;
    InputHandler inputHandler;

    Texture background;
    TextureRegion fullBackground;

    private double accumultator = 0;
    private float timestep = 1 / 60f;
    public boolean moveArrow;

    /**
     * Initialize variables for render.
     * @param game
     * @param camera
     */
    public GameLoop(Dreampult game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;

        debug = new Box2DDebugRenderer();

        world = new World(new Vector2(0, -2f), true);
        player = new Player(world);
        arrow = new Arrow(this);
        moveArrow = true;
        ground = new Ground(this);
        worldHandler = new WorldHandler(this);
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        background = new Texture("Tausta.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        fullBackground = new TextureRegion(background);
        fullBackground.setRegion(0, 0, background.getWidth() * (background.getWidth() / WORLD_WIDTH), background.getHeight());
    }

    /**
     * Render the game.
     * @param delta
     */
    @Override
    public void render(float delta) {
        /**
         * Do Stuff
         */
        doPhysicsStep(delta);
        worldHandler.moveCamera();
        game.batch.setProjectionMatrix(camera.combined);
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
        game.batch.begin();
        game.batch.draw(fullBackground, 0, 0, 240, 5);
        arrow.draw(game.batch);
        player.draw(game.batch);
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


}
