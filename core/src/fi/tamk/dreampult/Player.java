package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Clown on 22.2.2016.
 */
public class Player {
    Texture img;
    Body body;
    Array<Body> LeftLimbs;
    Array<Body> RightLimbs;
    World world;
    float width = 0.3f;
    float height = 1f;

    float limbWidth = width / 2;
    float limbHeight = height / 2;

    /**
     * Create player.
     * @param world
     */
    public Player(World world) {
        this.world = world;
        img = new Texture("./images/badlogic.jpg");
        LeftLimbs = new Array<Body>();
        RightLimbs = new Array<Body>();

        body = createBodyDef();
        createBodyFixture(body, 2f, width, height, false, 0);

        // TODO: Body joints tweaking


        Body LeftArm = createBodyDef();
        createBodyFixture(LeftArm, 1f, limbWidth, limbHeight,true, 0);
        createLimb(LeftArm,
                   0, width,  // Body Origin X & Y
                   0, limbHeight / 2 + 0.05f, // Limb Origin X & Y
                   false); // Limit rotation

        Body RightArm = createBodyDef();
        createBodyFixture(RightArm, 1f, limbWidth, limbHeight,true, 0);
        createLimb(RightArm,
                0, width, // Body Origin X & Y
                0, limbHeight / 2,  // Limb Origin X & Y
                false);  // Limit rotation

        Body LeftLeg = createBodyDef();
        createBodyFixture(LeftLeg, 1f, limbWidth, limbHeight,true, 0);
        createLimb(LeftLeg,
                0, height / 2 * -1 + 0.1f, // Body Origin X & Y
                0, limbHeight / 2  + 0.05f, // Limb Origin X & Y
                true);  // Limit rotation

        Body RightLeg = createBodyDef();
        createBodyFixture(RightLeg, 1f, limbWidth, limbHeight,true, 0);
        createLimb(RightLeg,
                0, height / 2 * -1 + 0.1f, // Body Origin X & Y
                0, limbHeight / 2, // Limb Origin X & Y
                true);  // Limit rotation*/

        LeftLimbs.add(LeftArm);
        LeftLimbs.add(LeftLeg);
        RightLimbs.add(RightArm);
        RightLimbs.add(RightLeg);

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
    private void createBodyFixture(Body body, float density, float width, float height, boolean sensor, float radius) {
        FixtureDef def = new FixtureDef();
        def.density = density;
        def.friction = 5f;
        def.restitution = 0.5f;
        def.isSensor = sensor;

        if(radius > 0) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(radius);
            def.shape = circleShape;
            body.createFixture(def);
            circleShape.dispose();
        } else {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2, height / 2, new Vector2(0, 0), 0);
            def.shape = polygonShape;
            body.createFixture(def);
            polygonShape.dispose();
        }


    }


    /**
     * Draw player position and rotation according body.
     * @param batch
     */
    public void draw(SpriteBatch batch) {

        for (Body b : RightLimbs) {
            batch.draw(img, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                    limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                    limbWidth, limbHeight, // Width, Height
                    1, 1, // Scale X, Scale Y
                    b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    false, false); // flip x, flip y
        }
        batch.draw(img, body.getPosition().x - width / 2, body.getPosition().y - height / 2, // Texture, x, y
                width * 0.5f, height * 0.5f, // Origin x, Origin y
                width, height, // Width, Height
                1, 1, // Scale X, Scale Y
                body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y

        for (Body b : LeftLimbs) {
            batch.draw(img, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                    limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                    limbWidth, limbHeight, // Width, Height
                    1, 1, // Scale X, Scale Y
                    b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    false, false); // flip x, flip y
        }
    }

}
