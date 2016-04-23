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
 * Created by Clown on 21.4.2016.
 */
public class ShittingRainbow {

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion current;
    float stateTime;
    float width;
    float height;

    GameLoop loop;

    float timer = 0;

    public ShittingRainbow(GameLoop loop) {
        this.sheet = loop.assets.get("images/player/rainbow_frames.png", Texture.class);
        this.loop = loop;
        stateTime = 0;
        width = 4; 
        height = 1;
        create(2, 4);
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

    public void play() {
        timer = 3;
    }

    public void update(float delta) {
        if(timer > 0) {
            timer -= Gdx.graphics.getDeltaTime();
            current = animation.getKeyFrame(stateTime, true);
            stateTime += delta;
        }
    }
    public void draw(SpriteBatch batch) {
        if(timer > 0) {
            batch.draw(current,
                    loop.player.torso.body.getPosition().x - 4,
                    loop.player.torso.body.getPosition().y - 0.8f,
                    0, 0,
                    width, height,
                    1, 1,
                    0);
        }
    }


}
