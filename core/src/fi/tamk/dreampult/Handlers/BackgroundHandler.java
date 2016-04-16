package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by root on 9.3.2016.
 */
public class BackgroundHandler {

    /**
     * Background texture.
     */
    Texture background;

    /**
     * First image position.
     */
    Vector2 position;

    /**
     * Speed of scroll effect.
     */
    float speed;

    /**
     * Stabilize speed of scroll effect so backgrounds doesnt scroll in sync.
     */
    float speedStabilizer;

    /**
     * Calculate how many images we will need to make smooth scroll.
     */
    int imageAmount;

    /**
     * Image width and height in loop world.
     */
    float imgWidth;
    float imgHeight;

    /**
     * Viewport offset from coordinates 0,0
     */
    float offset;

    /**
     * Initialize everything.
     * @param background Background texture.
     * @param imgWidth Width in loop world.
     * @param imgHeight Height in loop world.
     */
    public BackgroundHandler(Texture background, float imgWidth, float imgHeight, float speedStabilizer){
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.speedStabilizer = speedStabilizer;

        this.background = background;
        position = new Vector2(0, 0);
        imageAmount = (int) Math.ceil((double) (16 / imgWidth)) + 1;
    }

    /**
     * Drawing background.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
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
     */
    public void move() {
        for(int i = 0; i < imageAmount; i++) {
            if(position.x <= imgWidth * -1) {
                position.x = 0f;
            }
            position.set(position.x - (speed * speedStabilizer) * Gdx.graphics.getDeltaTime(), position.y);
        }
    }

    /**
     * Updating offset according GameCamera position.
     */
    public void updateOffset(float cameraX) {
        offset = cameraX - (16f / 2f);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
