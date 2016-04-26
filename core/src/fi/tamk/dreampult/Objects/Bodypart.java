package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import fi.tamk.dreampult.GameLoop;

/**
 * Created by DV6-6B20 on 10.3.2016.
 */
public class Bodypart {

    GameLoop game;
    public Body body;
    public float density;
    public Texture img;

    public float width;
    public float height;

    Clothes clothes;

    public Vector2 originalPosition;

    public Bodypart(GameLoop game) {
        this.game = game;

        clothes = new Clothes(game);

        density = 1f;
    }

    public void createBodypart(String userData, Vector2 position, float width, float height, boolean sensor, Texture img) {
        body = createBodyDef(position);
        body.setUserData(userData);
        createBodyFixture(body, 1f, width, height, sensor, 0);

        this.width = width;
        this.height = height;
        this.img = img;
        originalPosition = body.getPosition();
    }

    public void resetPosition() {
        body.getPosition().set(originalPosition);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    /**
     * Create players body definition.
     * @return
     */
    private Body createBodyDef(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.bullet = true;
        return game.world.createBody(bodyDef);
    }

    /**
     * Create players body fixture.
     */
    private void createBodyFixture(Body body, float density, float width, float height, boolean sensor, float radius) {
        FixtureDef def = new FixtureDef();
        def.density = density;
        def.friction = 7f;
        if(game.saves.isGrowSlippery()) {
            def.friction = 3f;
        }
        def.restitution = 0.3f;
        if(game.saves.isGrowBouncy()) {
            def.restitution = 0.8f;
        }
        def.isSensor = sensor;

        if(radius > 0) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(radius);
            def.shape = circleShape;
            this.body.createFixture(def);
            circleShape.dispose();
        } else {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2, height / 2, new Vector2(0, 0), 0);
            def.shape = polygonShape;
            this.body.createFixture(def);
            polygonShape.dispose();
        }
    }

    public void draw(SpriteBatch batch) {

        boolean flip = false;

        if(body.getUserData() == "head") {
            flip = true;
        }
            batch.draw(img, body.getPosition().x - width / 2, body.getPosition().y - height / 2, // Texture, x, y
                    width / 2, height / 2, // Origin x, Origin y
                    width, height, // Width, Height
                    1, 1, // Scale X, Scale Y
                    body.getAngle() * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    flip, flip); // flip x, flip y

        if(game.saves.isPyjamaGlide()){
            clothes.draw(batch, this);
        }
    }
}
