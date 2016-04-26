package fi.tamk.dreampult.Maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.BackgroundHandler;
import fi.tamk.dreampult.Objects.Collision.Generator;
import fi.tamk.dreampult.Objects.Collision.Objects;

import java.util.ArrayList;

/**
 * Created by Clown on 16.4.2016.
 */
public class Maps {

    public Map loadMap(int level, AssetManager assets) {
        if(level == 1) {
            return Level1(assets);
        } else if (level == 2) {
            return Level2(assets);
        } else if (level == 3) {
            return Level3(assets);
        }
        return Level1(assets);
    }

    private Map Level1(AssetManager assets){

            Map level1 = new Map(1);
            ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
            ArrayList<Generator> objects = level1.getObjects();

            level1.setStaticBackground(assets.get("images/background/level1/layer4.png", Texture.class));

            backgrounds.add(new BackgroundHandler(
                    assets.get("images/background/level1/layer3.png", Texture.class),
                    16, 9, 0.3f));
            backgrounds.add(new BackgroundHandler(
                    assets.get("images/background/level1/layer2.png", Texture.class),
                    16, 9, 0.4f));

            backgrounds.add(new BackgroundHandler(
                    assets.get("images/background/level1/layer1.png", Texture.class),
                    16, 9, 0.5f));

            bedGeneration(assets, objects);
            starGeneration(assets, objects);
            unicornGeneration(assets, objects);
            clockGeneration(assets, objects);

            objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(30, 10)));
            objects.add(new Generator(assets, "pig", 25, 0, new Vector2(15, 0), new Vector2(48, 35)));


            for(Generator generator: objects) {
                generator.setReservedPositions(level1.reservedPositions);
            }

            return level1;

    }

    private Map Level2(AssetManager assets){
        Map level1 = new Map(2);
        ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
        ArrayList<Generator> objects = level1.getObjects();

        level1.setStaticBackground(assets.get("images/background/level2/layer4.png", Texture.class));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer3.png", Texture.class),
                16, 9, 0.3f));
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer2.png", Texture.class),
                16, 9, 0.4f));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level2/layer1.png", Texture.class),
                16, 9, 0.5f));

        bedGeneration(assets, objects);
        starGeneration(assets, objects);
        unicornGeneration(assets, objects);
        clockGeneration(assets, objects);

        objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(25, 5)));
        objects.add(new Generator(assets, "pig", 25, 0, new Vector2(15, 0), new Vector2(48, 30)));

        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        Generator turtle = new Generator(assets, "turtle", 15, 2, new Vector2(1, 0), new Vector2(1, 0));
        turtle.startPoint = 3;
        objects.add(turtle);


        for(Generator generator: objects) {
            generator.setReservedPositions(level1.reservedPositions);
        }

        return level1;
    }

    private Map Level3(AssetManager assets){
        Map level1 = new Map(3);
        ArrayList<BackgroundHandler> backgrounds = level1.getBackgrounds();
        ArrayList<Generator> objects = level1.getObjects();

        level1.setStaticBackground(assets.get("images/background/level3/layer4.png", Texture.class));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer3.png", Texture.class),
                16, 9, 0.3f));
        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer2.png", Texture.class),
                16, 9, 0.4f));

        backgrounds.add(new BackgroundHandler(
                assets.get("images/background/level3/layer1.png", Texture.class),
                16, 9, 0.5f));

        bedGeneration(assets, objects);
        starGeneration(assets, objects);
        unicornGeneration(assets, objects);
        clockGeneration(assets, objects);

        objects.add(new Generator(assets, "cow", 30, 0, new Vector2(15, 0), new Vector2(48, 30)));
        objects.add(new Generator(assets, "cow", 10, 0, new Vector2(15, 0), new Vector2(30, 10)));

        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        Generator turtle = new Generator(assets, "turtle", 15, 2, new Vector2(5, 0), new Vector2(1, 0));
        turtle.startPoint = 3;
        objects.add(turtle);

        for(Generator generator: objects) {
            generator.setReservedPositions(level1.reservedPositions);
        }

        return level1;
    }

    public void bedGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "bed", 10, 3, new Vector2(1, 0), new Vector2(15, 0)));
        generators.add(new Generator(assets, "bed", 20, 3, new Vector2(5, 0), new Vector2(1, 0)));
    }

    public void starGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "star", 30, 0, new Vector2(1, 0), new Vector2(48, 10)));
    }

    public void unicornGeneration(AssetManager assets, ArrayList<Generator> generators) {
        generators.add(new Generator(assets, "unicorn", 20, 0, new Vector2(1, 0), new Vector2(48, 20)));
    }

    public void clockGeneration(AssetManager assets, ArrayList<Generator> generators) {
        Generator clock = new Generator(assets, "clock", 33, -1, new Vector2(1, 1), new Vector2(1, 0));
        clock.startPoint = 8;
        generators.add(clock);
    }

}
