package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Clown on 24.2.2016.
 */
public class Meter {

    TextureRegion meter;
    TextureRegion meterColor;
    float meterMax;
    boolean show;
    public float scale;

    public float speed;

    final int UP = 1;
    final int DOWN = 2;
    int direction;

    /**
     * Initialize Meter variables and set default values
     */
    public Meter() {
        meter = new TextureRegion(new Texture("./images/meter.png"));
        meterColor = new TextureRegion(new Texture("./images/meterColor.png"));
        meterMax = 1.5f;
        speed = 3f;
        scale = 0;
        direction = UP;
        show = false;
    }

    /**
     * Draw meter and fill it accord time.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        if (show) {
            if (scale >= meterMax - 0.01f) {
                direction = DOWN;
            }
            if (scale <= 0.02) {
                direction = UP;
            }

            if(direction == UP) {
                scale += speed * Gdx.graphics.getDeltaTime();
            } else if (direction == DOWN) {
                scale -= speed * Gdx.graphics.getDeltaTime();
            }

            batch.draw(meterColor,       //Texture
                    0, 1.5f,    //PositionX, PositionY
                    0f, 0f,       //OriginX, OriginY
                    1, 1,       //Width, Height
                    0.5f, scale,       // ScaleX, ScaleY
                    0);         //Rotation
            batch.draw(meter,       //Texture
                    0, 1.5f,    //PositionX, PositionY
                    0f, 0f,       //OriginX, OriginY
                    1, 1,       //Width, Height
                    0.5f, meterMax,       // ScaleX, ScaleY
                    0);         //Rotation
        }
    }

    /**
     * Show meter.
     */
    public void show() {
        show = true;
    }

    /**
     * Hide meter.
     */
    public void hide() {
        show = false;
    }
}
