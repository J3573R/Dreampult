package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Clown on 22.2.2016.
 */
public class Player {
    Texture img;
    Body body;
    World world;
    float size = 0.5f;

    /**
     * Create player.
     * @param world
     */
    public Player(World world) {
        this.world = world;
        img = new Texture("badlogic.jpg");

        body = createBodyDef();
        createBodyFixture();
    }

    /**
     * Create players body definition.
     * @return
     */
    private Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.5f, 0.5f);
        bodyDef.bullet = true;
        return world.createBody(bodyDef);
    }

    /**
     * Create players body fixture.
     */
    private void createBodyFixture() {
        FixtureDef def = new FixtureDef();
        def.density = 2f;
        def.friction = 5f;
        def.restitution = 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size / 2, new Vector2(0, 0), 0);
        def.shape = shape;
        body.createFixture(def);
        shape.dispose();
    }

    /**
     * Draw player position and rotation according body.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        //System.out.println(body.getLinearVelocity().toString());
        batch.draw(img, body.getPosition().x - size / 2, body.getPosition().y - size / 2, // Texture, x, y
                size * 0.5f, size * 0.5f, // Origin x, Origin y
                size, size, // Width, Height
                1, 1, // Scale X, Scale Y
                body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
    }

}
