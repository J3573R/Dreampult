package fi.tamk.dreampult.Maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Collection;
import fi.tamk.dreampult.Handlers.BackgroundHandler;
import fi.tamk.dreampult.Objects.Collision.Generator;
import fi.tamk.dreampult.Objects.Collision.Objects;

import java.util.ArrayList;

/**
 * @author Tommi Hagelberg
 */
public class Maps {
    Collection collection;

    /**
     * Load correct map depending level.
     * @param level Integer to indicate level.
     * @param assets Assets for map.
     * @param collection Saves collection for later use.
     * @return Fully constructed map file.
     */
    public Map loadMap(int level, AssetManager assets, Collection collection) {
        this.collection = collection;
        if(level == 1) {
            return Level1(assets);
        } else if (level == 2) {
            return Level2(assets);
        } else if (level == 3) {
            return Level3(assets);
        }
        return Level1(assets);
    }

    /**
     * Construct level 1 map.
     * @param assets Assets for constructing map.
     * @return Fully constructed map.
     */
    private Map Level1(AssetManager assets){
        /**
         * Construct base map, background list and generator list.
         */
        Map level1 = new Map(1);
        ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
        ArrayList<Generator> objects = level1.getObjects();

        // Sets static background.
        level1.setStaticBackground(assets.get("images/background/level1/layer4.png", Texture.class));

        /**
         * Adds backgrounds to background list.
         */
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level1/layer3.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.3f, collection));
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level1/layer2.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.4f, collection));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level1/layer1.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.5f, collection));

        /**
         * Adds default generators.
         */
        bedGeneration(assets, objects);
        starGeneration(assets, objects);
        unicornGeneration(assets, objects);
        clockGeneration(assets, objects);

        /**
         * Add map specific objects.
         */
        objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(30, 10)));
        objects.add(new Generator(assets, "pig", 25, 0, new Vector2(15, 0), new Vector2(48, 35)));

        /**
         * Pass reservedPositions array for every generator in map.
         */
        for(Generator generator: objects) {
            generator.setReservedPositions(level1.reservedPositions);
        }

        return level1;

    }

    /**
     * Construct level 2 map.
     * @param assets Assets for constructing map.
     * @return Fully constructed map.
     */
    private Map Level2(AssetManager assets){
        /**
         * Construct base map, background list and generator list.
         */
        Map level1 = new Map(2);
        ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
        ArrayList<Generator> objects = level1.getObjects();

        // Sets static background.
        level1.setStaticBackground(assets.get("images/background/level2/layer4.png", Texture.class));

        /**
         * Adds backgrounds to background list.
         */
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer3.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.3f, collection));
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer2.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.4f, collection));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer1.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.5f, collection));

        /**
         * Adds default generators.
         */
        bedGeneration(assets, objects);
        starGeneration(assets, objects);
        unicornGeneration(assets, objects);
        clockGeneration(assets, objects);

        /**
         * Add map specific objects.
         */
        objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(25, 5)));
        objects.add(new Generator(assets, "pig", 25, 0, new Vector2(15, 0), new Vector2(48, 30)));

        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        Generator turtle = new Generator(assets, "turtle", 15, 2, new Vector2(1, 0), new Vector2(1, 0));
        turtle.startPoint = 3;
        objects.add(turtle);

        /**
         * Pass reservedPositions array for every generator in map.
         */
        for(Generator generator: objects) {
            generator.setReservedPositions(level1.reservedPositions);
        }

        return level1;
    }

    /**
     * Construct level 3 map.
     * @param assets Assets for constructing map.
     * @return Fully constructed map.
     */
    private Map Level3(AssetManager assets){
        /**
         * Construct base map, background list and generator list.
         */
        Map level1 = new Map(3);
        ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
        ArrayList<Generator> objects = level1.getObjects();

        // Sets static background.
        level1.setStaticBackground(assets.get("images/background/level3/layer4.png", Texture.class));

        /**
         * Adds backgrounds to background list.
         */
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer3.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.3f, collection));
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer2.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.4f, collection));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer1.png", Texture.class),
                collection.SCREEN_WIDTH, collection.SCREEN_HEIGHT, 0.5f, collection));

        /**
         * Adds default generators.
         */
        bedGeneration(assets, objects);
        starGeneration(assets, objects);
        unicornGeneration(assets, objects);
        clockGeneration(assets, objects);

        /**
         * Add map specific objects.
         */
        objects.add(new Generator(assets, "cow", 30, 0, new Vector2(15, 0), new Vector2(45, 30)));
        objects.add(new Generator(assets, "cow", 10, 0, new Vector2(15, 0), new Vector2(30, 10)));

        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        Generator turtle = new Generator(assets, "turtle", 15, 2, new Vector2(5, 0), new Vector2(1, 0));
        turtle.startPoint = 3;
        objects.add(turtle);

        /**
         * Pass reservedPositions array for every generator in map.
         */
        for(Generator generator: objects) {
            generator.setReservedPositions(level1.reservedPositions);
        }

        return level1;
    }

    /**
     * Default bed object generation pattern
     * @param assets Asset manager for loading texture.
     * @param generators Generator list where to add object generation processes.
     */
    public void bedGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "bed", 10, 3, new Vector2(1, 0), new Vector2(15, 0)));
        generators.add(new Generator(assets, "bed", 20, 3, new Vector2(5, 0), new Vector2(1, 0)));
    }

    /**
     * Default star object generation pattern
     * @param assets Asset manager for loading texture.
     * @param generators Generator list where to add object generation processes.
     */
    public void starGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "star", 30, 0, new Vector2(1, 0), new Vector2(45, 10)));
    }

    /**
     * Default unicorn object generation pattern
     * @param assets Asset manager for loading texture.
     * @param generators Generator list where to add object generation processes.
     */
    public void unicornGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "unicorn", 20, 0, new Vector2(1, 0), new Vector2(45, 20)));
    }

    /**
     * Default clock object generation pattern
     * @param assets Asset manager for loading texture.
     * @param generators Generator list where to add object generation processes.
     */
    public void clockGeneration(AssetManager assets, ArrayList<Generator> generators) {
        Generator clock = new Generator(assets, "clock", 33, -1, new Vector2(1, 1), new Vector2(1, 0));
        clock.startPoint = 8;
        generators.add(clock);
    }

}
