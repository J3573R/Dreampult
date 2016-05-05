package fi.tamk.dreampult.Maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.Handlers.BackgroundHandler;
import fi.tamk.dreampult.Objects.Collision.Generator;
import fi.tamk.dreampult.Objects.Collision.Objects;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Tommi Hagelberg
 */
public class Map {
    GameLoop loop;
    int level;
    Vector2 cameraPosition;

    private ArrayList<Generator> objects;
    private ArrayList<BackgroundHandler> backgrounds;
    public ArrayList<Vector2> reservedPositions = new ArrayList<Vector2>();

    private Texture staticBackground;

    /**
     * Constructs map from level.
     * @param level Integer to indicate level.
     */
    public Map(int level) {
        this.level = level;
        cameraPosition = new Vector2();
        objects = new ArrayList<Generator>();
        backgrounds = new ArrayList<BackgroundHandler>();
    }

    /**
     * Initialize game loop before map starts running. This is here because map has to load before game loop.
     * @param loop Gives game loop for later use.
     */
    public void initialize(GameLoop loop) {
        this.loop = loop;
    }

    /**
     * Update map objects and backgrounds.
     */
    public void update(){
        cameraPosition.set(loop.GameCamera.position.x, loop.GameCamera.position.y);
        for ( Generator object : objects ) {
            object.update(
                    loop.world,
                    loop.player.torso.body.getPosition(),
                    cameraPosition,
                    loop.collection
            );
        }
        for ( BackgroundHandler background : backgrounds) {
            if(loop.player.torso.body.getPosition().x >= loop.collection.SCREEN_WIDTH_CENTER) {
                background.setSpeed(loop.player.torso.body.getLinearVelocity().x);
                background.move();
                background.updateOffset(loop.GameCamera.position.x);
            }
        }
    }

    /**
     * Draws maps objects.
     * @param batch Spritebatch for drawing.
     */
    public void drawObjects(SpriteBatch batch){
        for ( Generator object : objects) {
            object.draw(batch);
        }
    }

    /**
     * Draws background according player / camera position.
     * @param batch Spritebatch for drawing.
     */
    public void drawBackground(SpriteBatch batch) {
        batch.draw(staticBackground,
                loop.GameCamera.position.x - loop.collection.SCREEN_WIDTH / 2,
                loop.GameCamera.position.y - loop.collection.SCREEN_HEIGHT / 2,
                loop.collection.SCREEN_WIDTH,
                loop.collection.SCREEN_HEIGHT);
        for ( BackgroundHandler background : backgrounds){
            background.draw(batch);
        }
    }

    /**
     * Clears monsters from list.
     * @param world
     */
    public void clearMonsters(World world){
        for ( Generator object : objects) {
            object.dispose(world);
        }
    }

    /**
     * Stops background. Used when game paused and additional launch.
     */
    public void stopBackground(){
        for ( BackgroundHandler background : backgrounds) {
            background.setSpeed(0);
        }
    }

    /**
     * @return Gives full list of the map objects.
     */
    public ArrayList<Generator> getObjects() {
        return objects;
    }

    /**
     * @param objects Sets full list of objects.
     */
    public void setObjects(ArrayList<Generator> objects) {
        this.objects = objects;
    }

    /**
     * @return Give full list of backgrounds.
     */
    public ArrayList<BackgroundHandler> getBackgrounds() {
        return backgrounds;
    }

    /**
     * @param staticBackground Sets static background for map.
     */
    public void setStaticBackground(Texture staticBackground) {
        this.staticBackground = staticBackground;
        this.staticBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    /**
     * @return Gives current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Dispose objects bodies from world.
     */
    public void dispose() {
        for (Generator object : objects) {
            object.dispose(loop.world);
        }
    }
}
