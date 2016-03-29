package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.3.2016.
 */
public class Monster {
    GameLoop loop;

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion current;
    float stateTime;
    Vector2 position;
    float width;
    float height;
    Body body;

    float hitboxOffsetX = 0;
    float hitboxOffsetY = 0;

    //public Monster() {}

    public Monster(GameLoop loop) {
        this.loop = loop;
    }

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

    public void initalizePosition(Vector2 position, String userdata) {
        this.position = position;
        body = createBodyDef();
        body.setUserData(userdata);
        createBodyFixture(1f);
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

    public Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position.x + width / 2, position.y + height / 2);
        return loop.world.createBody(bodyDef);
    }

    public void createBodyFixture(Float density) {
        FixtureDef def = new FixtureDef();
        def.density = density;
        def.isSensor = true;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((width - hitboxOffsetX) / 2, (height  - hitboxOffsetY) / 2, new Vector2(0, 0), 0);
        def.shape = polygonShape;
        this.body.createFixture(def);
    }
}
