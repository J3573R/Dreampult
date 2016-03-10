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
    GameLoop game;

    /**
     * Background texture.
     */
    Texture background;

    /**
     * First image position.
     */
    Vector2 position;

    /**
     * Calculate how many images we will need to make smooth scroll.
     */
    int imageAmount;

    /**
     * Image width and height in game world.
     */
    float imgWidth;
    float imgHeight;

    /**
     * Viewport offset from coordinates 0,0
     */
    float offset;

    /**
     * Initialize everything.
     * @param game GameLoop access.
     * @param background Background texture.
     * @param imgWidth Width in game world.
     * @param imgHeight Height in game world.
     */
    public BackgroundHandler(GameLoop game, Texture background, float imgWidth, float imgHeight){
        this.game = game;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;

        this.background = background;
        position = new Vector2(0, 0);
        imageAmount = (int) Math.ceil((double) (10 / imgWidth)) + 1;
    }

    /**
     * Drawing background.
     * @param batch
     * @param speed Speed of scroll effect.
     */
    public void draw(SpriteBatch batch, float speed) {
        // Move image according speed
        move(speed);
        // Update accord camera offset
        updateOffset();

        for(int i = 0; i < imageAmount; i++) {
            batch.draw(background,
                        position.x + (imgWidth * i) + offset, // Images X position counted from image amount
                        position.y,
                        imgWidth,
                        imgHeight);
        }
    }

    /**
     * Moving background position.
     * @param speed Speed of movement.
     */
    private void move(float speed) {
        for(int i = 0; i < imageAmount; i++) {
            if(position.x <= imgWidth * -1) {
                position.x = 0f;
            }
            position.set(position.x - speed * Gdx.graphics.getDeltaTime(), position.y);
        }
    }

    /**
     * Updating offset according camera position.
     */
    public void updateOffset() {
        offset = game.camera.position.x - 5f;
    }
}
