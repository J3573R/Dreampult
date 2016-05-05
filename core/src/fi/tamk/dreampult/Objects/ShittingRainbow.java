package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
 */
public class ShittingRainbow {

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion current;
    float stateTime;
    float width;
    float height;
    float originX;
    float originY;

    public boolean manualRotation = false;
    float rotation;

    GameLoop loop;

    float timer = 0;

    /**
     * Construction of rainbow and setting default values.
     * @param loop Saves game loop for later use.
     */
    public ShittingRainbow(GameLoop loop) {
        this.sheet = loop.assets.get("images/player/rainbow_frames.png", Texture.class);
        this.loop = loop;
        stateTime = 0;
        width = 4;
        height = 1;
        originX = 4;
        originY = 0.5f;
        rotation = 0;
        create(2, 4);
    }
    public ShittingRainbow(GameLoop loop, Texture sheet, int width, int height, int cols, int rows) {
        this.sheet = sheet;
        this.loop = loop;
        stateTime = 0;
        this.width = width;
        this.height = height;
        originX = 4;
        originY = height / 2;
        rotation = 0;
        create(cols, rows);
    }

    /**
     * Sets size and origin of raindow.
     * @param width Rainbow width.
     * @param height Rainbow height.
     */
    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
        this.originX = width;
        this.originY = height / 2;
    }

    /**
     * @param rotation Sets rotation of rainbow.
     */
    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    /**
     * Creates animation from sprite sheet.
     * @param FRAME_COLS Animation frames per column.
     * @param FRAME_ROWS Animation frames per row.
     */
    public void create(int FRAME_COLS, int FRAME_ROWS) {
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.1f, frames);
        stateTime = 0f;
        current = animation.getKeyFrame(stateTime, true);
    }

    /**
     * Plays animation.
     */
    public void play() {
        timer = 3;
    }

    /**
     * Stops animation.
     */
    public void stop(){
        timer = 0;
    }

    /**
     * Update animation frames.
     * @param delta Deltatime from last drawn frame.
     */
    public void update(float delta) {
        if(timer > 0) {
            timer -= delta;
            current = animation.getKeyFrame(stateTime, true);
            stateTime += delta;
        }
    }

    /**
     * Draws the shitting rainbow effect.
     * @param batch Spritebatch for drawing.
     */
    public void draw(SpriteBatch batch) {
        if(timer > 0) {
            if(!manualRotation) {
                rotation = loop.player.torso.body.getLinearVelocity().y * 3;
            }

            batch.draw(current,
                    loop.player.torso.body.getPosition().x - originX,
                    loop.player.torso.body.getPosition().y - originY - 0.3f,
                    originX, originY,
                    width, height,
                    1, 1,
                    rotation);
        }
    }

    /**
     * @return Gives animation remaining time.
     */
    public float getTime(){
        return timer;
    }

}
