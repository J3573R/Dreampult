package fi.tamk.dreampult.Objects.Monsters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Clown on 16.4.2016.
 */
public class CowMonster extends Monster {

    public CowMonster(AssetManager assets) {
        this.sheet = assets.get("images/objects/enemies/cow.png", Texture.class);
        width = 3;
        height = 2;
        create(4, 2);
    }
}
