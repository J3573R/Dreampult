package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

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
    Meter meter = new Meter();

    Texture background;
    TextureRegion fullBackground;

    private double accumultator = 0;
    private float timestep = 1 / 60f;
    public boolean moveArrow;

    /**
     * The size of the map is 1000 x 500 tiles.
     */
    int TILES_AMOUNT_WIDTH = 1000;
    int TILES_AMOUNT_HEIGHT = 500;

    /**
     * The size of a single tile is 16 x 16.
     */
    int TILE_WIDTH = 16;
    int TILE_HEIGHT = 16;

    /**
     * The tile map used by the game.
     */
    private TiledMap tiledMap;

    /**
     * Used for rendering the tile map.
     */
    private TiledMapRenderer tiledMapRenderer;

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
        meter = new Meter();
        player = new Player(world);
        arrow = new Arrow(this);
        moveArrow = true;
        ground = new Ground(this);
        worldHandler = new WorldHandler(this);
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        background = new Texture("./images/Tausta.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        fullBackground = new TextureRegion(background);
        fullBackground.setRegion(0, 0, background.getWidth() * (background.getWidth() / WORLD_WIDTH), background.getHeight());

        tiledMap = new TmxMapLoader().load("./maps/mappi.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/100f);

        transformWallsToBodies("object-maa", "wall");
        transformWallsToBodies("object-paprika", "collectable");


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

        tiledMapRenderer.setView(camera);


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

        tiledMapRenderer.render();

        game.batch.begin();

        meter.draw(game.batch);

        //game.batch.draw(fullBackground, 0, 0, 240, 5);

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

    /**
     * Takes a tiledmap layer and transforms it to a box2D Body.
     * @param layer The layer to transform.
     * @param userData The userData of the layer.
     */
    private void transformWallsToBodies(String layer, String userData) {
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);

        MapObjects mapObjects = collisionObjectLayer.getObjects();

        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            Rectangle rectangle = scaleRect(tmp, 1 / 100f);

            createStaticBody(rectangle, userData);
        }
    }

    /**
     * Creates a static body, and sets it userData.
     * @param rect The rectangle to use as the basis.
     * @param userData The userData to give the rectangle.
     */
    public void createStaticBody(Rectangle rect, String userData) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width / 2 , height / 2 );

        wall.createFixture(groundBox, 0.0f);
    }

    /**
     * Scales a rectangle to the desired scale.
     * @param r The rectangle to scale.
     * @param scale Used for scaling the rectangle.
     * @return Returns a properly scaled rectangle.
     */
    private Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x         = r.x * scale;
        rectangle.y         = r.y * scale;
        rectangle.width     = r.width * scale;
        rectangle.height    = r.height * scale;
        return rectangle;
    }

}
