package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 30.3.2016.
 */
public class HitEffect {

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion current;
    float stateTime;
    Vector2 position;
    float width;
    float height;

    public boolean playing;

    public HitEffect(GameLoop loop) {
        this.sheet = loop.assets.get("images/player/hiteffect.png", Texture.class);
        stateTime = 0;
        width = 2;
        height = 2;
        position = new Vector2(0, 0);
        create(3, 1);
        playing = false;
    }

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

    public void play(Vector2 position) {
        this.position = position;
        stateTime = 0;
        playing = true;
    }

    public void update(float delta) {
        if(animation.isAnimationFinished(stateTime)) {
            playing = false;
        }
        current = animation.getKeyFrame(stateTime, false);
        stateTime += delta;
    }

    /**
     * Draws animation frame.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(current, position.x - 1f, position.y - 1f, width, height);
    }
}
