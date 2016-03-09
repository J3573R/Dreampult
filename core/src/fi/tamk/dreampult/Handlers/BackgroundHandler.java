package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

import java.util.ArrayList;

/**
 * Created by root on 9.3.2016.
 */
public class BackgroundHandler {
    // TODO: Must finish. Not yet commented.
    GameLoop game;
    Texture background;
    Vector2 position;
    int imageAmount;

    float imgWidth;
    float imgHeight;

    float offset;

    public BackgroundHandler(GameLoop game, Texture background, float imgWidth, float imgHeight){
        this.game = game;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;

        this.background = background;
        position = new Vector2(0, 0);
        imageAmount = (int) Math.ceil((double) (10 / imgWidth)) + 1;
    }

    public void draw(SpriteBatch batch, float speed) {
        move(speed);
        updateOffset();
        for(int i = 0; i < imageAmount; i++) {
            batch.draw(background, position.x + (imgWidth * i) + offset, position.y, imgWidth, imgHeight);
        }
    }

    private void move(float speed) {
        for(int i = 0; i < imageAmount; i++) {
            if(position.x <= imgWidth * -1) {
                position.x = 0f;
            }
            position.set(position.x - speed * Gdx.graphics.getDeltaTime(), position.y);
        }
    }

    public void updateOffset() {
        offset = game.camera.position.x - 5f;
    }
}
