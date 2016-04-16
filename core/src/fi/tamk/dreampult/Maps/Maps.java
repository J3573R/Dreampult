package fi.tamk.dreampult.Maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Dreampult;
import fi.tamk.dreampult.GameLoop;
import fi.tamk.dreampult.Handlers.AssetHandler;
import fi.tamk.dreampult.Handlers.BackgroundHandler;
import fi.tamk.dreampult.Objects.Monsters.Generator;

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

        objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        objects.add(new Generator(assets, "bed", 10, 2, new Vector2(1, 0), new Vector2(1, 0)));
        Generator clock = new Generator(assets, "clock", 33, -1, new Vector2(1, 1), new Vector2(1, 0));
        clock.startPoint = 8;
        objects.add(clock);

        return level1;
    }

    private Map Level2(AssetManager assets){
        Map level1 = new Map(1);
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

        objects.add(new Generator(assets, "pig", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        objects.add(new Generator(assets, "bed", 10, 2, new Vector2(1, 0), new Vector2(1, 0)));
        Generator clock = new Generator(assets, "clock", 33, -1, new Vector2(1, 1), new Vector2(1, 0));
        clock.startPoint = 8;
        objects.add(clock);

        return level1;
    }

    private Map Level3(AssetManager assets){
        Map level1 = new Map(1);
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

        objects.add(new Generator(assets, "bed", 10, 2, new Vector2(1, 0), new Vector2(1, 0)));
        objects.add(new Generator(assets, "turtle", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        objects.add(new Generator(assets, "cow", 15, 0, new Vector2(15, 0), new Vector2(20, 5)));
        Generator clock = new Generator(assets, "clock", 33, -1, new Vector2(1, 1), new Vector2(1, 0));
        clock.startPoint = 8;
        objects.add(clock);

        return level1;
    }
}
