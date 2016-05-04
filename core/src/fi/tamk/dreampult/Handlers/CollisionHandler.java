package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.Helpers.Saves;
import fi.tamk.dreampult.Objects.Collision.Objects;
import fi.tamk.dreampult.Objects.HitEffect;

/**
 * Created by Clown on 25.2.2016.
 */
public class CollisionHandler implements ContactListener {
    GameLoop game;
    Saves saves;

    public CollisionHandler (GameLoop game) {
        this.game = game;
        saves = game.game.saves;
    }
    @Override
    public void beginContact(Contact contact) {
        String a = (String) contact.getFixtureA().getBody().getUserData();
        String b = (String) contact.getFixtureB().getBody().getUserData();

        if(a != null && b != null) {
            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("pig")) ||
               (a.equalsIgnoreCase("pig") && b.equalsIgnoreCase("torso"))) {
                if(game.pyjamaOn) {
                    bounce(contact);
                    game.pyjamaOn = false;
                    game.player.dropClothes();
                } else {
                    playAnimation(contact);
                    game.shittingRainbow.stop();
                    game.game.sounds.play("pig");
                    Vector2 vel = game.player.torso.body.getLinearVelocity();
                    game.player.setBodypartVelocity(new Vector2(0, 0));
                    vel.set(15, -15);
                    game.player.torso.body.setLinearVelocity(vel);
                }
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("cow")) ||
                    (a.equalsIgnoreCase("cow") && b.equalsIgnoreCase("torso"))) {
                if(game.pyjamaOn) {
                    bounce(contact);
                    game.pyjamaOn = false;
                    game.player.dropClothes();
                } else {
                    playAnimation(contact);
                    game.shittingRainbow.stop();
                    game.game.sounds.play("moo");
                    Vector2 vel = game.player.torso.body.getLinearVelocity();
                    game.player.torso.body.setAngularVelocity(-360);
                    vel.set(vel.x, -20);
                    game.player.torso.body.setLinearVelocity(vel);
                }
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("turtle")) ||
                    (a.equalsIgnoreCase("turtle") && b.equalsIgnoreCase("torso"))) {
                if(game.pyjamaOn) {
                    bounce(contact);
                    game.pyjamaOn = false;
                    game.player.dropClothes();
                } else {
                    playAnimation(contact);
                    game.shittingRainbow.stop();
                    Vector2 vel = game.player.torso.body.getLinearVelocity();
                    vel.set(vel.x / 100, vel.y / 100);
                    game.player.torso.body.setAngularVelocity(180);
                    game.player.torso.body.setLinearVelocity(vel);
                }
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("unicorn")) ||
                    (a.equalsIgnoreCase("unicorn") && b.equalsIgnoreCase("torso"))) {
                playAnimation(contact);
                game.shittingRainbow.play();
                game.player.setBodypartVelocity(new Vector2(0, 0));
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                vel.set(70, 10);
                game.player.torso.body.setLinearVelocity(vel);
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("bed")) ||
                    (a.equalsIgnoreCase("bed") && b.equalsIgnoreCase("torso"))) {
                bounce(contact);
                /*playAnimation(contact);
                game.game.sounds.play("ground");
                game.player.setBodypartVelocity(new Vector2(0, 0));
                Vector2 vel = game.player.torso.body.getLinearVelocity();
                vel.set(30, 30);
                game.player.torso.body.setLinearVelocity(vel);*/
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("clock")) ||
                    (a.equalsIgnoreCase("clock") && b.equalsIgnoreCase("torso"))) {
                if(game.pyjamaOn) {
                    bounce(contact);
                    game.pyjamaOn = false;
                    game.player.dropClothes();
                } else {
                    playAnimation(contact);
                    game.shittingRainbow.stop();
                    game.retry = 0;
                    game.game.sounds.play("alarm");
                    Gdx.input.vibrate(3000);
                    Vector2 vel = new Vector2(0, 0);
                    game.player.torso.body.setLinearVelocity(vel);
                    game.player.head.body.setLinearVelocity(vel);
                    game.player.leftArm.body.setLinearVelocity(vel);
                    game.player.rightArm.body.setLinearVelocity(vel);
                    game.player.leftLeg.body.setLinearVelocity(vel);
                    game.player.rightLeg.body.setLinearVelocity(vel);
                }
            }

            if((a.equalsIgnoreCase("torso") && b.equalsIgnoreCase("star")) ||
                    (a.equalsIgnoreCase("star") && b.equalsIgnoreCase("torso"))) {
                Body star;
                saves.addStar();
                game.ui.starButton.setText(String.valueOf(saves.getStars()));
                game.game.sounds.play("star");
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
        System.out.println(a);
        System.out.println(b);


        if(a.equalsIgnoreCase("pig") || a.equalsIgnoreCase("clock")
                || a.equalsIgnoreCase("cow") || a.equalsIgnoreCase("turtle")) {
            Body body = contact.getFixtureA().getBody();
            game.hit.play(new Vector2(body.getPosition()));
        }

        if(b.equalsIgnoreCase("pig") || b.equalsIgnoreCase("bed")
                || b.equalsIgnoreCase("cow") || b.equalsIgnoreCase("turtle")) {
            Body body = contact.getFixtureB().getBody();
            game.hit.play(new Vector2(body.getPosition()));
        }

        if(a.equalsIgnoreCase("bed") || a.equalsIgnoreCase("unicorn")) {
            Body body = contact.getFixtureA().getBody();
            game.bounce.play(new Vector2(body.getPosition()));
        }

        if(b.equalsIgnoreCase("bed") || b.equalsIgnoreCase("unicorn")) {
            Body body = contact.getFixtureB().getBody();
            game.bounce.play(new Vector2(body.getPosition()));
        }
    }

    public void bounce(Contact contact){
        playAnimation(contact);
        game.game.sounds.play("ground");
        game.player.setBodypartVelocity(new Vector2(0, 0));
        Vector2 vel = game.player.torso.body.getLinearVelocity();
        vel.set(30, 30);
        game.player.torso.body.setLinearVelocity(vel);
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
