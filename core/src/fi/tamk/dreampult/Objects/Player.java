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
    Bodypart head;
    Bodypart leftLeg;
    Bodypart rightLeg;
    public Bodypart torso;
    Bodypart leftArm;
    Bodypart rightArm;

    float width = 0.5f;
    float height = 1f;

    float limbWidth = width / 1.5f;
    float limbHeight = height / 1.5f;

    // TODO: Se helvetin katapulttianimaatio pitäis kans tehdä.

    /**
     * Create player.
     * @param world
     */
    public Player(World world, GameLoop game) {
        this.world = world;
        this.game = game;

        head = new Bodypart(game);
        leftLeg = new Bodypart(game);
        leftArm = new Bodypart(game);
        rightLeg = new Bodypart(game);
        rightArm = new Bodypart(game);
        torso = new Bodypart(game);

        Texture imgBody = game.assets.get("images/player/body.png", Texture.class);
        Texture imgLeg = game.assets.get("images/player/leg.png", Texture.class);
        Texture imgLeftArm = game.assets.get("images/player/left_arm.png", Texture.class);
        Texture imgRightArm = game.assets.get("images/player/right_arm.png", Texture.class);
        Texture imgHead = game.assets.get("images/player/headwithball.png", Texture.class);

        torso.density = 0.1f;

        head.createBodypart("head", width, width, true, imgHead);
        leftLeg.createBodypart("left leg", limbWidth, limbHeight, true, imgLeg);
        leftArm.createBodypart("left arm", limbWidth, limbHeight, true, imgLeftArm);
        rightLeg.createBodypart("right leg", limbWidth, limbHeight, true, imgLeg);
        rightArm.createBodypart("right arm", limbWidth, limbHeight, true, imgRightArm);
        torso.createBodypart("torso", width, height, false, imgBody);

        connectBodypart(torso, leftArm,
                0, height / 2 - 0.1f,
                0, limbHeight / 2 + 0.05f,
                false);

        connectBodypart(torso, rightArm,
                0, height / 2 - 0.1f,
                0, limbHeight / 2,
                false);

        connectBodypart(torso, leftLeg,
                0, height / 2 * -1 + 0.1f,
                0, limbHeight / 2 + 0.05f,
                true);

        connectBodypart(torso, rightLeg,
                0, height / 2 * -1 + 0.1f,
                0, limbHeight / 2,
                true);

        connectBodypart(torso, head,
                0, height / 2 - 0.05f,
                0, width / 2,
                true);
    }

    /**
     * Create and attatch limb to body.
     * @param bodyOriginX X position from body to attatch.
     * @param bodyOriginY Y position from body to attatch.
     * @param limbOriginX X position from limb to attatch.
     * @param limbOriginY Y position from limb to attatch.
     * @param limit Rotation limit on or off.
     */
    private void connectBodypart(Bodypart bodypartOne, Bodypart bodypartTwo, float bodyOriginX, float bodyOriginY, float limbOriginX, float limbOriginY,
                            boolean limit) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = bodypartOne.body;
        jointDef.bodyB = bodypartTwo.body;
        jointDef.localAnchorA.set(bodyOriginX, bodyOriginY);
        jointDef.localAnchorB.set(limbOriginX, limbOriginY);
        jointDef.enableLimit = limit;

        if ((bodypartOne.body.getUserData() == "head") ||
                (bodypartTwo.body.getUserData() == "head")) {
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
        def.restitution = 0.9f;
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

        rightArm.draw(batch);
        rightLeg.draw(batch);
        torso.draw(batch);
        head.draw(batch);
        leftArm.draw(batch);
        leftLeg.draw(batch);
    }

}
