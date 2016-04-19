package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import fi.tamk.dreampult.Collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Clown on 22.3.2016.
 */
public class Generator {
    AssetManager assets;

    ArrayList<Objects> objects = new ArrayList<Objects>();

    float interval;
    float increment;

    Vector2 rangeX;
    Vector2 rangeY;

    public float startPoint;

    float traveled;
    Random random;

    String type;

    /**
     * Initialises generator.
     */
    public Generator(AssetManager assets, String type, float interval, float increment, Vector2 rangeX, Vector2 rangeY) {
        this.assets = assets;
        random = new Random();

        this.interval = interval;
        this.increment = increment;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.type = type;

        traveled = 5f;
        startPoint = 0;
    }

    /**
     * Generates new patch of objects.
     */
    public void update(World world, Vector2 playerPosition, Vector2 cameraPosition, Collection collection) {
        if(startGeneration(playerPosition)) {
            if(traveled + interval < playerPosition.x) {
                traveled = playerPosition.x;

                Objects mon = parseType();
                mon.initalizePosition(world, new Vector2((random.nextInt((int)rangeX.x) + rangeX.y) + (cameraPosition.x + collection.SCREEN_WIDTH / 2),
                        random.nextInt((int)rangeY.x) + rangeY.y), type);

                objects.add(mon);
                setInterval(interval + increment);
            }

            Iterator<Objects> iterator = objects.iterator();
            while(iterator.hasNext()) {
                Objects object = iterator.next();
                if((object.position.x + object.width) < (cameraPosition.x - collection.SCREEN_WIDTH / 2f)) {
                    world.destroyBody(object.body);
                    iterator.remove();
                } else if (object.body.getUserData().equals("delete")) {
                    world.destroyBody(object.body);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Draws objects.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        for(Objects object : objects) {
            object.update(Gdx.graphics.getDeltaTime());
            object.draw(batch);
        }
    }

    private Objects parseType(){
        if(type.equals("pig")) {
            Pig object = new Pig(assets);
            return object;
        }

        if(type.equals("bed")) {
            Bed object = new Bed(assets);
            return object;
        }

        if(type.equals("clock")) {
            Clock object = new Clock(assets);
            return object;
        }

        if(type.equals("cow")) {
            Cow object = new Cow(assets);
            return object;
        }

        if(type.equals("turtle")) {
            Turtle object = new Turtle(assets);
            return object;
        }

        if(type.equals("unicorn")) {
            Unicorn object = new Unicorn(assets);
            return object;
        }

        if(type.equals("star")) {
            Star object = new Star(assets);
            return object;
        }
        return new Pig(assets);
    }

    private boolean startGeneration(Vector2 playerPosition) {
        if(startPoint != 0) {
            if((int) playerPosition.x * 0.8f / 60 > startPoint) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public void clearMonster(){
        objects = new ArrayList<Objects>();
    }

    public void dispose(World world){
        System.out.println("DISPOSED");
        for ( Objects object : objects){
            world.destroyBody(object.body);
        }
    }

}
