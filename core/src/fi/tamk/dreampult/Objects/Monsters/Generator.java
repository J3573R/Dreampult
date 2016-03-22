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
    // TODO: Vaatii hiukan hiomista. Tähän ei kallen tarvi koskea muutakuin jos oikeasti kiinnostaa. Jäi kesken ko tuli nälkä ja täs on vääntäny jo hetken.
    GameLoop gameLoop;
    ArrayList<PigMonster> monsters = new ArrayList<PigMonster>();
    int max;
    int min;

    float traveled;
    Random random;

    /**
     * Initialises generator.
     * @param gameLoop
     */
    public Generator(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        random = new Random();
        max = 3;
        traveled = 5;
    }

    /**
     * Generates new patch of monsters.
     */
    public void update() {
        if(traveled + 5 < gameLoop.player.torso.body.getPosition().x) {
            traveled += 10;
            for(int i = 0; i < max; i++) {
                //System.out.println("HERE");
                PigMonster mon = new PigMonster(gameLoop);
                mon.position = new Vector2((random.nextInt(15) + 1) + (gameLoop.camera.position.x + gameLoop.collection.SCREEN_WIDTH / 2),
                                           random.nextInt(50));
                monsters.add(mon);
            }
        }

        Iterator<PigMonster> iterator = monsters.iterator();
        while(iterator.hasNext()) {
            PigMonster monster = iterator.next();
            if((monster.position.x + monster.width) < (gameLoop.camera.position.x - gameLoop.collection.SCREEN_WIDTH / 2f)) {
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

}
