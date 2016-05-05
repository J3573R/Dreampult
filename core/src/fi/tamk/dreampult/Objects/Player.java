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
 * @author Kalle Heinonen & Tommi Hagelberg
 */
public class Player {
    GameLoop game;
    World world;

    // Textures for the body and limbs
    Texture imgBody;
    Texture imgLeg;
    Texture imgLeftArm;
    Texture imgRightArm;
    Texture imgHead;

    // Bodyparts for the body to use
    public Bodypart head;
    public Bodypart leftLeg;
    public Bodypart rightLeg;
    public Bodypart torso;
    public Bodypart leftArm;
    public Bodypart rightArm;

    // Width and height of the body
    float width = 0.5f;
    float height = 1f;

    // Width and height of the limbs
    float limbWidth = width / 1.5f;
    float limbHeight = height / 1.5f;

    /**
     * Creates the player.
     *
     * @param world used for creating and destroying the limbs
     * @param game used to gain access to AssetManager and passed on to Bodypart
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

        imgBody = game.assets.get("images/player/body.png", Texture.class);
        imgLeg = game.assets.get("images/player/leg.png", Texture.class);
        imgLeftArm = game.assets.get("images/player/left_arm.png", Texture.class);
        imgRightArm = game.assets.get("images/player/right_arm.png", Texture.class);
        imgHead = game.assets.get("images/player/headwithball.png", Texture.class);

        torso.density = 0.1f;

        // Creates all the Bodyparts
        head.createBodypart("head", new Vector2(1, 2.5f), width, width, true, imgHead);
        leftLeg.createBodypart("left leg", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeg);
        leftArm.createBodypart("left arm", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeftArm);
        rightLeg.createBodypart("right leg", new Vector2(1, 2), limbWidth, limbHeight, true, imgLeg);
        rightArm.createBodypart("right arm", new Vector2(1, 2), limbWidth, limbHeight, true, imgRightArm);
        torso.createBodypart("torso", new Vector2(1, 2), width, height, false, imgBody);

        // Connects all the Bodyparts to each other
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
     * Moves and rotates the Bodyparts properly
     *
     * @param x the new location on the X-axis
     * @param y the new location on the Y-axis
     * @param rotation the new amount of rotation for the Bodyparts
     */
    public void setTransform(float x, float y, float rotation) {
        head.body.setTransform(x , x, 0);
        torso.body.setTransform(x, y, rotation);
        rightArm.body.setTransform(x, y, 0);
        leftArm.body.setTransform(x, y, 0);
        rightLeg.body.setTransform(x, y, 0);
        leftLeg.body.setTransform(x, y, 0);
    }

    /**
     * Resets the position of the Bodyparts
     */
    public void reset() {
        head.resetPosition(this.torso.body.getPosition());
        torso.resetPosition(this.torso.body.getPosition());
        rightArm.resetPosition(this.torso.body.getPosition());
        leftArm.resetPosition(this.torso.body.getPosition());
        rightLeg.resetPosition(this.torso.body.getPosition());
        rightLeg.resetPosition(this.torso.body.getPosition());
    }

    /**
     * Destroys and recreates all the Bodyparts.
     */
    public void reCreate(){
        Vector2 torsoPos = torso.body.getPosition();
        world.destroyBody(head.body);
        world.destroyBody(leftLeg.body);
        world.destroyBody(leftArm.body);
        world.destroyBody(rightLeg.body);
        world.destroyBody(rightArm.body);
        world.destroyBody(torso.body);

        head = new Bodypart(game);
        leftLeg = new Bodypart(game);
        leftArm = new Bodypart(game);
        rightLeg = new Bodypart(game);
        rightArm = new Bodypart(game);
        torso = new Bodypart(game);

        torso.density = 0.1f;

        head.createBodypart("head", torsoPos.x, torsoPos.y + 0.5f, width, width, true, imgHead);
        leftLeg.createBodypart("left leg", torsoPos, limbWidth, limbHeight, true, imgLeg);
        leftArm.createBodypart("left arm", torsoPos, limbWidth, limbHeight, true, imgLeftArm);
        rightLeg.createBodypart("right leg", torsoPos, limbWidth, limbHeight, true, imgLeg);
        rightArm.createBodypart("right arm", torsoPos, limbWidth, limbHeight, true, imgRightArm);
        torso.createBodypart("torso", torsoPos, width, height, false, imgBody);

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
     * Create and attach limb to body.
     *
     * @param bodyOriginX X position from body to attach.
     * @param bodyOriginY Y position from body to attach.
     * @param limbOriginX X position from limb to attach.
     * @param limbOriginY Y position from limb to attach.
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
     * Draws the Bodyparts.
     *
     * @param batch SpriteBatch used for drawing
     */
    public void draw(SpriteBatch batch) {
        rightArm.draw(batch);
        rightLeg.draw(batch);
        torso.draw(batch);
        head.draw(batch);
        leftArm.draw(batch);
        leftLeg.draw(batch);
    }

    /**
     * Sets the velocity of the Bodyparts.
     *
     * @param vel the value the velocity is set to
     */
    public void setBodypartVelocity(Vector2 vel) {

        rightArm.body.setAngularVelocity(0);
        leftArm.body.setAngularVelocity(0);
        rightLeg.body.setAngularVelocity(0);
        leftLeg.body.setAngularVelocity(0);
        head.body.setAngularVelocity(0);

        rightArm.body.setLinearVelocity(vel);
        leftArm.body.setLinearVelocity(vel);
        rightLeg.body.setLinearVelocity(vel);
        leftLeg.body.setLinearVelocity(vel);
        head.body.setLinearVelocity(vel);
    }

    /**
     * Drops the clothes off the player's body when hit (Only if talent Pyjama Protection is on)
     */
    public void dropClothes() {
        rightArm.clothes.setDropClothes(true);
        rightLeg.clothes.setDropClothes(true);
        torso.clothes.setDropClothes(true);
        head.clothes.setDropClothes(true);
        leftArm.clothes.setDropClothes(true);
        leftLeg.clothes.setDropClothes(true);
    }
}
