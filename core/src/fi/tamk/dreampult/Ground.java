package fi.tamk.dreampult;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Clown on 22.2.2016.
 */
public class Ground {
    GameLoop game;
    Body body;

    /**
     * Initialize ground few pixels under the screen.
     * @param gameLoop
     */
    public Ground(GameLoop gameLoop) {
        this.game = gameLoop;
        body = createBodyDef();
        createBodyFixture();
    }

    private Body createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(game.WORLD_WIDTH / 2, -0.1f);
        return game.world.createBody(bodyDef);
    }

    private void createBodyFixture() {
        FixtureDef def = new FixtureDef();
        def.density = 0;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(game.WORLD_WIDTH / 2, 0.1f);
        def.shape = shape;
        body.createFixture(def);
        shape.dispose();
    }
}
