package fi.tamk.dreampult.Objects.Launching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
 */
public class Arrow {
    GameLoop game;
    Texture img;

    public Body body;
    public float width = 1f;
    public float height = 3f;
    public float rotation;
    public int direction;
    public boolean moving;

    boolean show;

    /**
     * Initialize arrow and set default values.
     * @param gameLoop Saves game loop for later use.
     */
    public Arrow(GameLoop gameLoop) {
        game = gameLoop;
        direction = game.game.collection.UP;
        img = game.assets.get("images/launching/whiteArrow.png", Texture.class);
        show = true;
        rotation = 0;
        body = createBodyDef();
        createBodyFixture();
        start();
    }

    /**
     * Reset arrow position for relaunch.
     */
    public void reset() {
        rotation = 0;
        show = true;
    }

    /**
     * Create arrows body definition. Used for getting arrow rotation.
     * Bad code, could've done with Vector2.
     * @return Arrows body.
     */
    public Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(2.5f, 1f);
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
     * @param batch Spritebatch for drawing.
     */
    public void draw(SpriteBatch batch) {
        if (show) {
            body.setTransform(body.getPosition(), rotation);
            batch.draw(img,
                    body.getPosition().x - 0.48f + game.player.torso.body.getPosition().x, body.getPosition().y, // Texture, x, y
                    0.5f, 0, // Origin x, Origin y
                    width, height, // Width, Height
                    1, 1, // Scale X, Scale Y
                    rotation * MathUtils.radiansToDegrees,    // Rotation
                    1, 1, // srcX, srcY
                    img.getWidth(), img.getHeight(), // srcWidth, srcHeight
                    false, false); // flip x, flip y
        }
    }

    /**
     * Updates arrow moving direction.
     */
    public void update() {
        if (moving) {
            if (rotation > 0) {
                direction = game.collection.DOWN;
            }

            if (rotation < -1.6) {
                direction = game.collection.UP;
            }

            if (direction == game.collection.DOWN) {
                rotation -= Gdx.graphics.getDeltaTime();
            }
            if (direction == game.collection.UP) {
                rotation += Gdx.graphics.getDeltaTime();
            }
        }
    }

    /**
     * Starts arrow movement.
     */
    public void start() {
        moving = true;
    }

    /**
     * Stops arrow movement.
     */
     public void pause() {
         moving = false;
     }

    /**
     * Shows arrow.
     */
    public void show() {
        show = true;
    }

    /**
     * Hides arrow.
     */
    public void hide() {
        show = false;
    }
}
