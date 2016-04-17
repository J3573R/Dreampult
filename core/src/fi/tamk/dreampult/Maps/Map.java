package fi.tamk.dreampult.Maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.Handlers.BackgroundHandler;
import fi.tamk.dreampult.Objects.Collision.Generator;
import fi.tamk.dreampult.Objects.Collision.Objects;

import java.util.ArrayList;

/**
 * Created by Clown on 16.4.2016.
 */
public class Map {
    GameLoop loop;
    int level;

    private ArrayList<Generator> objects;
    private ArrayList<BackgroundHandler> backgrounds;

    private Texture staticBackground;

    public Map(int level) {
        this.level = level;
        objects = new ArrayList<Generator>();
        backgrounds = new ArrayList<BackgroundHandler>();
    }

    public void initialize(GameLoop loop) {
        this.loop = loop;
    }

    public void update(){
        for ( Generator object : objects ) {
            object.update(
                    loop.world,
                    loop.player.torso.body.getPosition(),
                    new Vector2(loop.GameCamera.position.x,
                    loop.GameCamera.position.y),
                    loop.collection
            );
        }
        for ( BackgroundHandler background : backgrounds) {
            if(loop.player.torso.body.getPosition().x >= 8) {
                background.setSpeed(loop.player.torso.body.getLinearVelocity().x);
                background.move();
                background.updateOffset(loop.GameCamera.position.x);
            }
        }
    }

    public void drawObjects(SpriteBatch batch){
        for ( Generator object : objects) {
            object.draw(batch);
        }
    }

    public void drawBackground(SpriteBatch batch) {
        batch.draw(staticBackground,
                loop.GameCamera.position.x - loop.collection.SCREEN_WIDTH / 2,
                loop.GameCamera.position.y - loop.collection.SCREEN_HEIGHT / 2 - 0.5f,
                loop.collection.SCREEN_WIDTH,
                loop.collection.SCREEN_HEIGHT);
        for ( BackgroundHandler background : backgrounds){
            background.draw(batch);
        }
    }

    public void clearMonsert(){
        for ( Generator object : objects) {
            object.clearMonster();
        }
    }

    public void stopBackground(){
        for ( BackgroundHandler background : backgrounds) {
            background.setSpeed(0);
        }
    }

    public ArrayList<Generator> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Generator> objects) {
        this.objects = objects;
    }

    public ArrayList<BackgroundHandler> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(ArrayList<BackgroundHandler> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public Texture getStaticBackground() {
        return staticBackground;
    }

    public void setStaticBackground(Texture staticBackground) {
        this.staticBackground = staticBackground;
        this.staticBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public int getLevel() {
        return level;
    }

    public void dispose() {
        for (Generator object : objects) {
            object.dispose(loop.world);
        }
    }
}
