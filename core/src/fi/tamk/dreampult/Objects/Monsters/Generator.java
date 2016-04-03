package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.GameLoop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Clown on 22.3.2016.
 */
public class Generator {
    GameLoop gameLoop;
    ArrayList<Monster> monsters = new ArrayList<Monster>();

    float interval;

    Vector2 rangeX;
    Vector2 rangeY;

    float traveled;
    Random random;

    String type;

    /**
     * Initialises generator.
     * @param gameLoop
     */
    public Generator(GameLoop gameLoop, String type, float interval, Vector2 rangeX, Vector2 rangeY) {
        random = new Random();

        this.gameLoop = gameLoop;
        this.interval = interval;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.type = type;

        traveled = 5f;
    }

    /**
     * Generates new patch of monsters.
     */
    public void update() {

        if(traveled + interval < gameLoop.player.torso.body.getPosition().x) {
            traveled = gameLoop.player.torso.body.getPosition().x;

            Monster mon = parseType();
            mon.initalizePosition(new Vector2((random.nextInt((int)rangeX.x) + rangeX.y) + (gameLoop.GameCamera.position.x + gameLoop.collection.SCREEN_WIDTH / 2),
                    random.nextInt((int)rangeY.x) + rangeY.y), type);

            monsters.add(mon);
        }

        Iterator<Monster> iterator = monsters.iterator();
        while(iterator.hasNext()) {
            Monster monster = iterator.next();
            if((monster.position.x + monster.width) < (gameLoop.GameCamera.position.x - gameLoop.collection.SCREEN_WIDTH / 2f)) {
                iterator.remove();
            }
        }
    }

    public void update(float addition) {

        if(traveled + interval < gameLoop.player.torso.body.getPosition().x) {
            traveled = gameLoop.player.torso.body.getPosition().x;

            Monster mon = parseType();
            mon.initalizePosition(new Vector2((random.nextInt((int)rangeX.x) + rangeX.y) + (gameLoop.GameCamera.position.x + gameLoop.collection.SCREEN_WIDTH / 2),
                    random.nextInt((int)rangeY.x) + rangeY.y), type);
            monsters.add(mon);
            setInterval(interval + addition);
        }

        Iterator<Monster> iterator = monsters.iterator();
        while(iterator.hasNext()) {
            Monster monster = iterator.next();
            if((monster.position.x + monster.width) < (gameLoop.GameCamera.position.x - gameLoop.collection.SCREEN_WIDTH / 2f)) {
                iterator.remove();
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
            PigMonster mon = new PigMonster(gameLoop);
            return mon;
        }

        if(type.equals("bed")) {
            BedMonster mon = new BedMonster(gameLoop);
            return mon;
        }

        if(type.equals("clock")) {
            Clock mon = new Clock(gameLoop);
            return mon;
        }

        return new PigMonster(gameLoop);
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

}
