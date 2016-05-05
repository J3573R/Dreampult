package fi.tamk.dreampult.Objects.Collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fi.tamk.dreampult.Collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Tommi Hagelberg
 */
public class Generator {
    AssetManager assets;

    ArrayList<Objects> objects = new ArrayList<Objects>();
    ArrayList<String> objectTypes = new ArrayList<String>();
    ArrayList<Vector2> reservedPositions;

    float interval;
    float increment;

    Vector2 rangeX;
    Vector2 rangeY;

    public float startPoint;

    float traveled;
    Random random;

    String type;

    /**
     * Initialises generator and set default values..
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
        this.reservedPositions = reservedPositions;
    }

    /**
     * @param reservedPositions Defines reservedPositions array for generation checks.
     */
    public void setReservedPositions(ArrayList<Vector2> reservedPositions){
        this.reservedPositions = reservedPositions;
    }

    /**
     * Generates new patch of objects. Deletes expired object according their viewport position or flagged for deletion.
     */
    public void update(World world, Vector2 playerPosition, Vector2 cameraPosition, Collection collection) {
        /**
         * Generates new objects.
         */
        if(startGeneration(playerPosition)) {
            if (traveled + interval < playerPosition.x) {

                reservedPositions.clear();
                traveled = playerPosition.x;
                float randomX = (random.nextInt((int) rangeX.x) + rangeX.y) + (cameraPosition.x + collection.SCREEN_WIDTH / 2);
                float randomY = (random.nextInt((int) rangeY.x) + rangeY.y);
                Vector2 position = new Vector2(randomX, randomY);

                /**
                 * Checks reserved positions before inserting.
                 */
                while(true) {

                    if(reservedPositions.contains(position)) {
                        randomX = (random.nextInt((int) rangeX.x) + rangeX.y) + (cameraPosition.x + collection.SCREEN_WIDTH / 2);
                        randomY = (random.nextInt((int) rangeY.x) + rangeY.y);
                        position.set(randomX, randomY);
                    } else {
                        Objects mon = parseType();
                        mon.initalizePosition(world, position, type);
                        objects.add(mon);
                        setInterval(interval + increment);
                        if(interval < 5) {
                            setInterval(5);
                        }
                        reservedPositions.add(position);

                        /**
                         * Adds reserved positions also around of object depending object size.
                         */
                        for(int i = 0; i < mon.width; i++){
                            float x = position.x + i;
                            for (int j = 0; j < mon.height; j++) {
                                float y = position.y + j;
                                reservedPositions.add(new Vector2(x, y));
                            }
                        }
                        break;
                    }
                }
            }
        }

        /**
         * Deletes objects if they are out of viewport or flagged for deletion.
         */
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

    /**
     * Draws all objects.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        for(Objects object : objects) {
            object.update(Gdx.graphics.getDeltaTime());
            object.draw(batch);
        }
    }

    /**
     * @return Returns correct type of object.
     */
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

    /**
     * @param playerPosition Player current position in world.
     * @return If setted interval is crossed returns true.
     */
    private boolean startGeneration(Vector2 playerPosition) {
        if(startPoint != 0) {
            if((int) playerPosition.x * 0.6f / 60 > startPoint) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * @param interval Changes generation interval.
     */
    public void setInterval(float interval) {
        this.interval = interval;
    }

    /**
     * Dispose all bodies from world and objects from array.
     * @param world
     */
    public void dispose(World world){
        Iterator<Objects> iterator = objects.iterator();
        reservedPositions.clear();
        while(iterator.hasNext()) {
            Objects object = iterator.next();
            world.destroyBody(object.body);
            iterator.remove();
        }
    }

}
