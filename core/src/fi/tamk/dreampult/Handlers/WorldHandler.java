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
     * Initialize map and GameCamera
     * @param gameLoop
     */
    public WorldHandler(GameLoop gameLoop) {
        loop = gameLoop;
        camera = loop.GameCamera;

        mapLeft = 0;
        mapRight = 0 + WORLD_WIDTH;
        mapBottom = 0;
        mapTop = 0 + WORLD_HEIGHT;

        cameraHalfWidth = camera.viewportWidth * 0.5f;
        cameraHalfHeight = camera.viewportHeight * 0.5f;
    }

    /**
     * Move GameCamera if not near edge of loop world.
     */
    public void moveCamera() {
        if(loop.collection.launch) {
            float x = loop.player.torso.body.getPosition().x;
            float y = loop.player.torso.body.getPosition().y;

            cameraLeft = x - cameraHalfWidth;
            cameraRight = x + cameraHalfWidth;
            cameraBottom = y - cameraHalfHeight;
            cameraTop = y + cameraHalfHeight;

            if(cameraLeft > mapLeft) {
                camera.position.x = x;
            }

            if(cameraTop < mapTop && cameraBottom > mapBottom) {
                camera.position.y = y;
            }
        }
        camera.update();
    }

}
