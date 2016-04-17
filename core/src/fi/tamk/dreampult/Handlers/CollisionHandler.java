package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.Objects.Collision.Objects;
import fi.tamk.dreampult.Objects.HitEffect;
import fi.tamk.dreampult.Unlocks;

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
            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("pig")) ||
               (a.equalsIgnoreCase("pig") && b.equalsIgnoreCase("torso"))) {
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                playAnimation(contact);
                vel.set(vel.x, -15);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("cow")) ||
                    (a.equalsIgnoreCase("cow") && b.equalsIgnoreCase("torso"))) {
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                playAnimation(contact);
                vel.set(vel.x, vel.y * -10);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("turtle")) ||
                    (a.equalsIgnoreCase("turtle") && b.equalsIgnoreCase("torso"))) {
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                playAnimation(contact);
                vel.set(vel.x / 100, vel.y / 100);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("unicorn")) ||
                    (a.equalsIgnoreCase("unicorn") && b.equalsIgnoreCase("torso"))) {
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                playAnimation(contact);
                vel.set(25, vel.y);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("bed")) ||
                    (a.equalsIgnoreCase("bed") && b.equalsIgnoreCase("torso"))) {
                playAnimation(contact);
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                vel.set(15, 15);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("clock")) ||
                    (a.equalsIgnoreCase("clock") && b.equalsIgnoreCase("torso"))) {
                game.retry = 0;
                Vector2 vel = new Vector2(0, 0);
                game.player.torso.body.setLinearVelocity(vel);
                game.player.head.body.setLinearVelocity(vel);
                game.player.leftArm.body.setLinearVelocity(vel);
                game.player.rightArm.body.setLinearVelocity(vel);
                game.player.leftLeg.body.setLinearVelocity(vel);
                game.player.rightLeg.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("star")) ||
                    (a.equalsIgnoreCase("star") && b.equalsIgnoreCase("torso"))) {
                Body star;
                Unlocks unlocks = new Unlocks();
                unlocks.addStar();
                unlocks.save();
                game.ui.starButton.setText(String.valueOf(unlocks.getStars()));
                if(a.equals("star")) {
                    star = contact.getFixtureA().getBody();
                } else  {
                    star = contact.getFixtureB().getBody();
                }
                star.setUserData("delete");
            }
        }
    }

    public void playAnimation(Contact contact) {
        String a = (String) contact.getFixtureA().getBody().getUserData();
        String b = (String) contact.getFixtureB().getBody().getUserData();

        if(a.equalsIgnoreCase("pig") || a.equalsIgnoreCase("bed")) {
            Body body = contact.getFixtureA().getBody();
            game.hit.play(new Vector2(body.getPosition()));
        }

        if(b.equalsIgnoreCase("pig") || b.equalsIgnoreCase("bed")) {
            Body body = contact.getFixtureB().getBody();
            game.hit.play(new Vector2(body.getPosition()));
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
