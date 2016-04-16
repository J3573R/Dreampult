package fi.tamk.dreampult.Objects.Monsters;

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
 * Created by Clown on 22.3.2016.
 */
public class Generator {
    AssetManager assets;

    ArrayList<Monster> monsters = new ArrayList<Monster>();

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
     * Generates new patch of monsters.
     */
    public void update(World world, Vector2 playerPosition, Vector2 cameraPosition, Collection collection) {
        if(startGeneration(playerPosition)) {
            if(traveled + interval < playerPosition.x) {
                traveled = playerPosition.x;

                Monster mon = parseType();
                mon.initalizePosition(world, new Vector2((random.nextInt((int)rangeX.x) + rangeX.y) + (cameraPosition.x + collection.SCREEN_WIDTH / 2),
                        random.nextInt((int)rangeY.x) + rangeY.y), type);

                monsters.add(mon);
                setInterval(interval + increment);
            }

            Iterator<Monster> iterator = monsters.iterator();
            while(iterator.hasNext()) {
                Monster monster = iterator.next();
                if((monster.position.x + monster.width) < (cameraPosition.x - collection.SCREEN_WIDTH / 2f)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Draws monsters.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        for(Monster monster : monsters) {
            monster.update(Gdx.graphics.getDeltaTime());
            monster.draw(batch);
        }
    }

    private Monster parseType(){
        if(type.equals("pig")) {
            PigMonster mon = new PigMonster(assets);
            return mon;
        }

        if(type.equals("bed")) {
            BedMonster mon = new BedMonster(assets);
            return mon;
        }

        if(type.equals("clock")) {
            Clock mon = new Clock(assets);
            return mon;
        }

        if(type.equals("cow")) {
            CowMonster mon = new CowMonster(assets);
            return mon;
        }

        if(type.equals("turtle")) {
            TurtleMonster mon = new TurtleMonster(assets);
            return mon;
        }

        if(type.equals("unicorn")) {
            Unicorn mon = new Unicorn(assets);
            return mon;
        }

        return new PigMonster(assets);
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
        monsters = new ArrayList<Monster>();
    }

}
