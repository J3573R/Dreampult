package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Clown on 15.3.2016.
 */
public class UserInterface {
    GameLoop loop;
    Texture pause;
    Texture pauseMenu;

    public UserInterface(GameLoop loop) {
        this.loop = loop;
        pause = this.loop.assets.get("images/ui/pause_button.png");
        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");
    }

    public void draw(SpriteBatch batch) {
        batch.draw(pause, loop.camera.position.x + 7, loop.camera.position.y + 3.5f, 1, 1);
        if(!loop.game.collection.isGameOn()) {
            batch.draw(pauseMenu, loop.camera.position.x - 2.25f, loop.camera.position.y - 3.15f, 4.5f, 6.31f);
        }
    }
}
