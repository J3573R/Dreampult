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

    Texture soundOn;
    Texture mutedSound;

    public UserInterface(GameLoop loop) {
        this.loop = loop;

        pause = this.loop.assets.get("images/ui/pause_button.png");
        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");

        soundOn = this.loop.assets.get("images/ui/soundOn.png", Texture.class);
        mutedSound = this.loop.assets.get("images/ui/mutedSound.png", Texture.class);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(pause, loop.camera.position.x + 7, loop.camera.position.y + 3.5f, 1, 1);
        if(!loop.game.collection.isGameOn()) {
            batch.draw(pauseMenu, loop.camera.position.x - 2.25f, loop.camera.position.y - 3.15f, 4.5f, 6.31f);
        }

        if(loop.game.collection.isSoundOn()) {
            batch.draw(soundOn, loop.camera.position.x - 8, loop.camera.position.y + 3.5f, 1, 1);
        } else {
            batch.draw(mutedSound, loop.camera.position.x - 8, loop.camera.position.y + 3.5f, 1, 1);
        }

    }
}
