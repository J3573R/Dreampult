package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by DV6-6B20 on 22.2.2016.
 */
public class WorldHandler {

    GameLoop game;
    OrthographicCamera camera;

    float mapWidth;
    float mapHeight;

    float mapLeft;
    float mapRight;
    float mapBottom;
    float mapTop;

    float cameraHalfWidth;
    float cameraHalfHeight;

    float cameraLeft;
    float cameraRight;
    float cameraBottom;
    float cameraTop;

    /**
     * Initialize map and camera
     * @param gameLoop
     */
    public WorldHandler(GameLoop gameLoop) {
        game = gameLoop;
        camera = game.camera;

        // TODO : Tilemap based camera movement
        //MapProperties mapProperties = tiledMap.getProperties();

        //mapWidth = mapProperties.get("width", Integer.class);
        //mapHeight = mapProperties.get("height", Integer.class);

        mapWidth = game.WORLD_WIDTH;
        mapHeight = game.WORLD_HEIGHT;


        mapLeft = 0;
        mapRight = 0 + mapWidth;
        mapBottom = 0;
        mapTop = 0 + mapHeight;

        cameraHalfWidth = camera.viewportWidth * 0.5f;
        cameraHalfHeight = camera.viewportHeight * 0.5f;
    }

    /**
     * Move camera if not near edge of game world.
     */
    public void moveCamera() {
        float x = game.player.body.getPosition().x;
        float y = game.player.body.getPosition().y;

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

}
