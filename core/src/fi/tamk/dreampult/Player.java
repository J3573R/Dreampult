package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * Created by Clown on 22.2.2016.
 */
public class Player {
    Texture img;
    Body body;
    World world;
    float width = 0.5f;
    float height = 1.5f;

    /**
     * Create player.
     * @param world
     */
    public Player(World world) {
        this.world = world;
        img = new Texture("./images/badlogic.jpg");

        body = createBodyDef();
        createBodyFixture(body, 2f, width, height, false);

        // TODO: Body joints tweaking
        /*
        Body LeftArm = createBodyDef();
        createBodyFixture(LeftArm, 0.2f, 0.3f, 1f,true);
        createLimb(LeftArm, 0, 0.5f, 0, 0.5f, false);

        Body RightArm = createBodyDef();
        createBodyFixture(RightArm, 0.4f, 0.2f, 0.9f,true);
        createLimb(RightArm, 0, 0.5f, 0, 0.5f, false);

        Body LeftLeg = createBodyDef();
        createBodyFixture(LeftLeg, 0.2f, 0.3f, 1f,true);
        createLimb(LeftLeg, 0, -0.5f, 0, 0.5f, true);

        Body RightLeg = createBodyDef();
        createBodyFixture(RightLeg, 0.4f, 0.2f, 0.9f,true);
        createLimb(RightLeg, 0, -0.5f, 0, 0.5f, true);
        */
    }

    /**
     * Create and attatch limb to body.
     * @param body Limb to attatch.
     * @param bodyOriginX X position from body to attatch.
     * @param bodyOriginY Y position from body to attatch.
     * @param limbOriginX X position from limb to attatch.
     * @param limbOriginY Y position from limb to attatch.
     * @param limit Rotation limit on or off.
     */
    private void createLimb(Body body, float bodyOriginX, float bodyOriginY, float limbOriginX, float limbOriginY,
                            boolean limit) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = this.body;
        jointDef.bodyB = body;
        jointDef.localAnchorA.set(bodyOriginX, bodyOriginY);
        jointDef.localAnchorB.set(limbOriginX, limbOriginY);
        jointDef.enableLimit = limit;
        jointDef.lowerAngle = -90 * MathUtils.degreesToRadians;
        jointDef.upperAngle = 90 * MathUtils.degreesToRadians;

        world.createJoint(jointDef);
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
    private void createBodyFixture(Body body, float density, float width, float height, boolean sensor) {
        FixtureDef def = new FixtureDef();
        def.density = density;
        def.friction = 5f;
        def.restitution = 0.5f;
        def.isSensor = sensor;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2, new Vector2(0, 0), 0);
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
        batch.draw(img, body.getPosition().x - width / 2, body.getPosition().y - height / 2, // Texture, x, y
                width * 0.5f, height * 0.5f, // Origin x, Origin y
                width, height, // Width, Height
                1, 1, // Scale X, Scale Y
                body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y
    }

}
