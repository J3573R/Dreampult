package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.GameLoop;

/**
 * Created by root on 25.2.2016.
 */
public class CollisionHandler implements ContactListener {
    GameLoop game;

    public CollisionHandler (GameLoop game) {
        this.game = game;
    }
    @Override
    public void beginContact(Contact contact) {
        String a = (String) contact.getFixtureA().getBody().getUserData();
        String b = (String) contact.getFixtureB().getBody().getUserData();

        if(a != null && b != null) {
            if((a.equalsIgnoreCase("player") && b.equalsIgnoreCase("cloud")) ||
               (a.equalsIgnoreCase("cloud") && b.equalsIgnoreCase("player"))) {
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                System.out.println(vel);
                vel = new Vector2(Math.abs(vel.x) * 1.5f, Math.abs(vel.y) * 1.5f + 1);
                game.player.torso.body.setLinearVelocity(vel);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
