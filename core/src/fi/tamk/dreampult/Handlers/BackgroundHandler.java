package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Collection;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
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

    /*
     * Collection for getting screen widht and height.
     */
    Collection collection;

    /**
     * Initialize everything.
     * @param background Background texture.
     * @param imgWidth Width in loop world.
     * @param imgHeight Height in loop world.
     * @param speedStabilizer Creates difference between movement speed of backgrounds.
     * @param collection Provides screen width, height and center for both of them.
     */
    public BackgroundHandler(Texture background, float imgWidth, float imgHeight, float speedStabilizer, Collection collection){
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.speedStabilizer = speedStabilizer;
        this.collection = collection;
        this.background = background;
        position = new Vector2(0, 0);
        imageAmount = (int) Math.ceil((double) (collection.SCREEN_WIDTH_CENTER / imgWidth)) + 1;
    }

    /**
     * Reset background position to start.
     */
    public void reset() {
        position.set(0, 0);
        offset = 0;
    }

    /**
     * Drawing background.
     * @param batch Sprite batch to draw at.
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
     * Moves background position according player speed and speed stabilizer.
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
     * Updating background offset according GameCamera position.
     */
    public void updateOffset(float cameraX) {
        offset = cameraX - collection.SCREEN_WIDTH_CENTER;
    }

    /**
     * Set speed of movement.
     * @param speed Set default movemement speed of background.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
