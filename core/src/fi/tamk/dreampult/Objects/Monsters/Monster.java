package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Clown on 22.3.2016.
 */
public class Monster {
    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion current;
    float stateTime;
    Vector2 position;
    float width;
    float height;

    public Monster() {}

    /**
     * Creates animation from sprite sheet.
     * @param FRAME_COLS
     * @param FRAME_ROWS
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
     * Updates animation.
     * @param delta
     */
    public void update(float delta) {
        stateTime += delta;
        current = animation.getKeyFrame(stateTime, true);
    }

    /**
     * Draws animation frame.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(current, position.x, position.y, width, height);
    }
}
