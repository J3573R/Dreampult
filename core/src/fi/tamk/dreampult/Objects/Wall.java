package fi.tamk.dreampult.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import fi.tamk.dreampult.GameLoop;

/**
 * @author Tommi Hagelberg
 */
public class Wall {
    GameLoop game;

    public Body body;

    /**
     * Initialize ground few pixels under the screen.
     * @param gameLoop
     */
    public Wall(GameLoop gameLoop) {
        this.game = gameLoop;
        body = createBodyDef();
        body.setUserData("ground");
        createBodyFixture();
    }

    /**
     * @return Body of wall.
     */
    private Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(game.collection.SCREEN_WIDTH / 2, 0f);
        return game.world.createBody(bodyDef);
    }

    /**
     * Creates fixture for body.
     */
    private void createBodyFixture() {
        FixtureDef def = new FixtureDef();
        def.density = 0;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(game.collection.SCREEN_WIDTH / 2, 0.1f);
        def.shape = shape;
        body.createFixture(def);
        shape.dispose();
    }
}
