package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Clown on 17.4.2016.
 */
public class Unlocks {
    int stars = 0;
    boolean level2 = false;
    boolean level3 = false;

    public Unlocks() {
        Preferences prefs = Gdx.app.getPreferences("Dreampult_Unlocks");
        stars = prefs.getInteger("stars", 0);
        level2 = prefs.getBoolean("level2", false);
        level3 = prefs.getBoolean("level3", false);
    }

    public void save() {
        Preferences prefs = Gdx.app.getPreferences("Dreampult_Unlocks");
        prefs.putInteger("stars", stars);
        prefs.putBoolean("level2", level2);
        prefs.putBoolean("level3", level3);
        prefs.flush();
    }

    public void reset() {
        stars = 0;
        level2 = false;
        level3 = false;
        save();
    }

    public void addStar() {
        System.out.println(stars);
        stars++;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isLevel2() {
        return level2;
    }

    public void setLevel2(boolean level2) {
        this.level2 = level2;
    }

    public boolean isLevel3() {
        return level3;
    }

    public void setLevel3(boolean level3) {
        this.level3 = level3;
    }
}
