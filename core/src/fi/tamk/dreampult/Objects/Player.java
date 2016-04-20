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
    public Bodypart head;
    public Bodypart leftLeg;
    public Bodypart rightLeg;
    public Bodypart torso;
    public Bodypart leftArm;
    public Bodypart rightArm;

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

        head.createBodypart("head", new Vector2(1, 2.5f), width, width, true, imgHead);
        leftLeg.createBodypart("left leg", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeg);
        leftArm.createBodypart("left arm", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeftArm);
        rightLeg.createBodypart("right leg", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeg);
        rightArm.createBodypart("right arm", new Vector2(1, 2), limbWidth, limbHeight, true, imgRightArm);
        torso.createBodypart("torso", new Vector2(1, 2), width, height, false, imgBody);

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

    public void setTransform(float x, float y, float rotation) {
        head.body.setTransform(x , x, 0);
        torso.body.setTransform(x, y, rotation);
        rightArm.body.setTransform(x, y, 0);
        leftArm.body.setTransform(x, y, 0);
        rightLeg.body.setTransform(x, y, 0);
        leftLeg.body.setTransform(x, y, 0);
    }

    public void reset() {
        head.resetPosition();
        torso.resetPosition();
        rightArm.resetPosition();
        leftArm.resetPosition();
        rightLeg.resetPosition();
        rightLeg.resetPosition();
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
        jointDef.enableMotor = false;

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

    public void setBodypartVelocity(Vector2 vel) {
        rightArm.body.setLinearVelocity(vel);
        leftArm.body.setLinearVelocity(vel);
        rightLeg.body.setLinearVelocity(vel);
        leftLeg.body.setLinearVelocity(vel);
        head.body.setLinearVelocity(vel);

        rightArm.body.setAngularVelocity(0);
        leftArm.body.setAngularVelocity(0);
        rightLeg.body.setAngularVelocity(0);
        leftLeg.body.setAngularVelocity(0);
        head.body.setAngularVelocity(0);

        rightArm.body.setAngularDamping(0);
        leftArm.body.setAngularDamping(0);
        rightLeg.body.setAngularDamping(0);
        leftLeg.body.setAngularDamping(0);
        head.body.setAngularDamping(0);
    }
}
