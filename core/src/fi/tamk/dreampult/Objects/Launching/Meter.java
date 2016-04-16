package fi.tamk.dreampult.Objects.Launching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.tamk.dreampult.GameLoop;
import org.w3c.dom.Text;

/**
 * Created by Clown on 24.2.2016.
 */
public class Meter {
    GameLoop game;
    final int UP = 1;
    final int DOWN = 2;
    int direction;
    boolean show;

    public TextureRegion meter;
    public TextureRegion meterColor;
    public float meterMax;
    public float scale;
    public float speed;

    /**
     * Initialize Meter variables and set default values
     */
    public Meter(GameLoop game) {
        this.game = game;
        Texture meter = game.assets.get("images/launching/meter.png", Texture.class);
        this.meter = new TextureRegion(meter);
        Texture meterColor = game.assets.get("images/launching/meterColor.png", Texture.class);
        this.meterColor = new TextureRegion(meterColor);
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
                    0 + game.player.torso.body.getPosition().x, 3f,    //PositionX, PositionY
                    0f, 0f,       //OriginX, OriginY
                    2, 2,       //Width, Height
                    0.5f, scale,       // ScaleX, ScaleY
                    0);         //Rotation
            batch.draw(meter,       //Texture
                    0 + game.player.torso.body.getPosition().x, 3f,    //PositionX, PositionY
                    0f, 0f,       //OriginX, OriginY
                    2, 2,       //Width, Height
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
