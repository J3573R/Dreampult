package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by DV6-6B20 on 22.2.2016.
 */
public class WorldHandler {

    GameLoop loop;
    OrthographicCamera camera;

    /**
     * World width and height.
     */
    public final int WORLD_WIDTH = 100;
    public final int WORLD_HEIGHT = 50;

    /**
     * Map bounds.
     */
    float mapLeft;
    float mapRight;
    float mapBottom;
    float mapTop;

    /**
     * Half of viewport.
     */
    float cameraHalfWidth;
    float cameraHalfHeight;

    /**
     * Viewport bounds.
     */
    float cameraLeft;
    float cameraRight;
    float cameraBottom;
    float cameraTop;

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
     * The tile map used by the loop.
     */
    private TiledMap tiledMap;

    /**
     * Used for rendering the tile map.
     */
    public TiledMapRenderer tiledMapRenderer;

    /**
     * Initialize map and camera
     * @param gameLoop
     */
    public WorldHandler(GameLoop gameLoop) {
        loop = gameLoop;
        camera = loop.camera;

        mapLeft = 0;
        mapRight = 0 + WORLD_WIDTH;
        mapBottom = 0;
        mapTop = 0 + WORLD_HEIGHT;

        cameraHalfWidth = camera.viewportWidth * 0.5f;
        cameraHalfHeight = camera.viewportHeight * 0.5f;

        createTiledMap();
    }

    /**
     * Initialize Tiled map and create bodies of object layer walls.
     */
    public void createTiledMap() {
        tiledMap = loop.assets.get("./maps/mappi.tmx", TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/100f);
        transformWallsToBodies("object-maa", "wall");
        transformWallsToBodies("object-paprika", "collectable");
        transformWallsToObjects("BounceObject", "cloud");
    }

    /**
     * Move camera if not near edge of loop world.
     */
    public void moveCamera() {
        float x = loop.player.torso.body.getPosition().x;
        float y = loop.player.torso.body.getPosition().y;

        cameraLeft = x - cameraHalfWidth;
        cameraRight = x + cameraHalfWidth;
        cameraBottom = y - cameraHalfHeight;
        cameraTop = y + cameraHalfHeight;

        if(cameraLeft > mapLeft && cameraRight < mapRight) {
            camera.position.x = x;
        }

        if(cameraTop < mapTop && cameraBottom > mapBottom) {
            camera.position.y = y;
        }

        camera.update();
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

    private void transformWallsToObjects(String layer, String userData) {
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);

        MapObjects mapObjects = collisionObjectLayer.getObjects();

        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            Rectangle rectangle = scaleRect(tmp, 1 / 100f);

            createObject(rectangle, userData);
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

        Body wall = loop.world.createBody(myBodyDef);

        wall.setUserData(userData);

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width / 2 , height / 2 );

        wall.createFixture(groundBox, 0.0f);
    }

    public void createObject(Rectangle rect, String userData) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = loop.world.createBody(myBodyDef);

        wall.setUserData(userData);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.isSensor = true;

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width / 2 , height / 2 );

        fixtureDef.shape = groundBox;

        wall.createFixture(fixtureDef);
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
