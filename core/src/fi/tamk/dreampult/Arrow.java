package fi.tamk.dreampult;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Clown on 22.2.2016.
 */
public class Arrow {
    GameLoop game;
    Texture img;
    Body body;
    float width = 1f;
    float height = 3f;
    float rotation;

    /**
     * Initialize arrow.
     * @param gameLoop
     */
    public Arrow(GameLoop gameLoop) {
        game = gameLoop;
        img = new Texture("arrow.png");
        rotation = 0;
        body = createBodyDef();
        createBodyFixture();
    }

    /**
     * Create arrows body definition.
     * @return
     */
    public Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0.5f, 0.5f);
        return  game.world.createBody(bodyDef);
    }

    /**
     * Create arrows body fixture.
     */
    public void createBodyFixture() {
        FixtureDef def = new FixtureDef();
        def.density = 0;
        def.isSensor = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width * 0.5f,height * 0.5f, new Vector2(0, 1.5f), rotation);
        def.shape = shape;
        body.createFixture(def);
        shape.dispose();
    }

    /**
     * Draw arrow position and rotation according body.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        body.setTransform(body.getPosition(), rotation);

        batch.draw(img, body.getPosition().x - 0.48f, body.getPosition().y, // Texture, x, y
                0.5f, 0, // Origin x, Origin y
                width, height, // Width, Height
                1, 1, // Scale X, Scale Y
                rotation * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
    }
}
