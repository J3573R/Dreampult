package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
 */
public class Objects {

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
     * Initialize object position.
     * @param world Wolrd where to create object.
     * @param position Object position in world.
     * @param userdata Userdata for collision detection.
     */
    public void initalizePosition(World world, Vector2 position, String userdata) {
        this.position = position;
        body = createBodyDef(world);
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

    /**
     * @param world Create body to world and sets position.
     * @return Body of object.
     */
    public Body createBodyDef(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position.x + width / 2, position.y + height / 2);
        return world.createBody(bodyDef);
    }

    /**
     * Creates bodys fixture.
     * @param density Defines body density.
     */
    public void createBodyFixture(Float density) {
        FixtureDef def = new FixtureDef();
        def.density = density;
        def.isSensor = true;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((width - hitboxOffsetX) / 2, (height  - hitboxOffsetY) / 2, new Vector2(0, 0), 0);
        def.shape = polygonShape;
        this.body.createFixture(def);
        polygonShape.dispose();
    }
}
