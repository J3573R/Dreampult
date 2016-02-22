package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by DV6-6B20 on 22.2.2016.
 */
public class WorldHandler {

    GameLoop game;
    OrthographicCamera camera;

    public WorldHandler(GameLoop gameLoop) {
        game = gameLoop;

        camera = game.camera;
    }

    public void moveCamera() {

        Vector2 pos = new Vector2(game.player.body.getPosition());

        //camera.position.set(game.player.body.getPosition().x,
               // game.player.body.getPosition().y, 0);



        System.out.println(camera.position.y);

        if(camera.position.y - camera.viewportHeight < 1) {
            pos.y = camera.position.y + camera.viewportHeight / 2 - 0.5f;
        }

        System.out.println(camera.position.y);

        camera.position.set(pos, 0);
        camera.update();
    }

}
