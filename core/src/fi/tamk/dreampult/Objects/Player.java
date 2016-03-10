package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by Clown on 22.2.2016.
 */
public class Player {
    GameLoop game;
    World world;

    Texture img;
    Texture imgBody;
    Texture imgLeg;
    Texture imgLeftArm;
    Texture imgRightArm;
    Texture imgHead;

    Array<Body> LeftLimbs;
    Array<Body> RightLimbs;

    float width = 0.3f;
    float height = 1f;

    float limbWidth = width / 1.5f;
    float limbHeight = height / 1.5f;

    public Body body;
    public Body head;

    /**
     * Create player.
     * @param world
     */
    public Player(World world, GameLoop game) {
        this.world = world;
        this.game = game;

        img = game.assets.get("./images/badlogic.jpg", Texture.class);
        imgBody = game.assets.get("./images/body.png", Texture.class);
        imgLeg = game.assets.get("./images/leg.png", Texture.class);
        imgLeftArm = game.assets.get("./images/left_arm.png", Texture.class);
        imgRightArm = game.assets.get("./images/right_arm.png", Texture.class);
        imgHead = game.assets.get("./images/headwithball.png", Texture.class);

        LeftLimbs = new Array<Body>();
        RightLimbs = new Array<Body>();

        body = createBodyDef();
        body.setUserData("player");
        createBodyFixture(body, 5f, width, height, false, 0);

        // TODO: Body joints tweaking

        head = createBodyDef();
        head.setUserData("head");
        createBodyFixture(head, 1f, width, width, true, 0);
        createLimb(head,
                    0, height / 2 - 0.05f, // Body Origin X & Y
                    0, width / 2, // Limb Origin X & Y
                    true); // Limit rotation

        Body LeftArm = createBodyDef();
        LeftArm.setUserData("arm");
        createBodyFixture(LeftArm, 1f, limbWidth, limbHeight, true, 0);
        createLimb(LeftArm,
                   0, height / 2 - 0.1f,  // Body Origin X & Y
                   0, limbHeight / 2 + 0.05f, // Limb Origin X & Y
                   false); // Limit rotation

        Body RightArm = createBodyDef();
        RightArm.setUserData("arm");
        createBodyFixture(RightArm, 1f, limbWidth, limbHeight,true, 0);
        createLimb(RightArm,
                0, height / 2 - 0.1f, // Body Origin X & Y
                0, limbHeight / 2,  // Limb Origin X & Y
                false);  // Limit rotation

        Body LeftLeg = createBodyDef();
        LeftLeg.setUserData("leg");
        createBodyFixture(LeftLeg, 1f, limbWidth, limbHeight,true, 0);
        createLimb(LeftLeg,
                0, height / 2 * -1 + 0.1f, // Body Origin X & Y
                0, limbHeight / 2  + 0.05f, // Limb Origin X & Y
                true);  // Limit rotation

        Body RightLeg = createBodyDef();
        RightLeg.setUserData("leg");
        createBodyFixture(RightLeg, 1f, limbWidth, limbHeight,true, 0);
        createLimb(RightLeg,
                0.1f, height / 2 * -1 + 0.1f, // Body Origin X & Y
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

        if (body.getUserData() == "head") {
            System.out.println("It works");
            jointDef.lowerAngle = 150 * MathUtils.degreesToRadians;
            jointDef.upperAngle = 210 * MathUtils.degreesToRadians;
        } else {
            jointDef.lowerAngle = -90 * MathUtils.degreesToRadians;
            jointDef.upperAngle = 90 * MathUtils.degreesToRadians;
        }

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
        def.restitution = 0.8f;
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
            if(b.getUserData() == "leg") {
                batch.draw(imgLeg, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                        limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                        limbWidth, limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        imgLeg.getWidth(), imgLeg.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            } else if (b.getUserData() == "arm") {
                batch.draw(imgRightArm, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                        limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                        limbWidth, limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        imgRightArm.getWidth(), imgRightArm.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            }
        }
        batch.draw(imgBody, body.getPosition().x - width / 2, body.getPosition().y - height / 2, // Texture, x, y
                width * 0.5f, height * 0.5f, // Origin x, Origin y
                width, height, // Width, Height
                1, 1, // Scale X, Scale Y
                body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                imgBody.getWidth(), imgBody.getHeight(), // srcWidth, srcHeight
                false, false); // flip x, flip y

        batch.draw(imgHead, head.getPosition().x - width / 2, head.getPosition().y - width / 2, // Texture, x, y
                width / 2, width / 2, // Origin x, Origin y
                width, width, // Width, Height
                1, 1, // Scale X, Scale Y
                head.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                1, 1, // srcX, srcY
                imgHead.getWidth(), imgHead.getHeight(), // srcWidth, srcHeight
                true, true); // flip x, flip y

        for (Body b : LeftLimbs) {
            if(b.getUserData() == "leg") {
                batch.draw(imgLeg, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                        limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                        limbWidth, limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        imgLeg.getWidth(), imgLeg.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            } else if(b.getUserData() == "arm") {
                batch.draw(imgLeftArm, b.getPosition().x - limbWidth / 2, b.getPosition().y - limbHeight / 2, // Texture, x, y
                        limbWidth / 2, limbHeight / 2, // Origin x, Origin y
                        limbWidth, limbHeight, // Width, Height
                        1, 1, // Scale X, Scale Y
                        b.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                        1, 1, // srcX, srcY
                        imgLeftArm.getWidth(), imgLeftArm.getHeight(), // srcWidth, srcHeight
                        false, false); // flip x, flip y
            }
        }
    }

}
