package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Clown on 22.2.2016.
 */
public class Player {
    Texture img;
    Body body;
    World world;
    float size = 1f;

    public Player(World world) {
        this.world = world;
        img = new Texture("badlogic.jpg");

        body = createBodyDef();
        createBodyFixture();
    }

    private Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 5);
        return world.createBody(bodyDef);
    }

    private void createBodyFixture() {
        FixtureDef def = new FixtureDef();
        def.density = 1f;
        def.friction = 0.2f;
        def.restitution = 0.2f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size / 2);

        def.shape = shape;
        body.createFixture(def);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img,
                    body.getPosition().x - size / 2,
                    body.getPosition().y - size / 2,
                    size, size);
    }

}
