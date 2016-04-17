package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Clown on 7.4.2016.
 */
public class Talents {

    private boolean growBouncy = false;
    private boolean growSlippery = false;

    private boolean boostLaunch = false;
    private boolean additionalLaunch = false;

    private boolean extraBounces = false;
    private boolean pyjamaGlide = false;

    public Talents() {
        Preferences prefs = Gdx.app.getPreferences("Dreampult_Talents");
        if(prefs.getBoolean("growBouncy", false)) {
            enableGrowBouncy();
        } else if (prefs.getBoolean("growSlippery", false)) {
            enableGrowSlippery();
        }

        if(prefs.getBoolean("boostLaunch", false)) {
            enableBoostLaunch();
        } else if (prefs.getBoolean("additionalLaunch", false)) {
            enableAdditionalLaunch();
        }

        if(prefs.getBoolean("extraBounces", false)) {
            enableExtraBounces();
        } else if (prefs.getBoolean("pyjamaGlide", false)) {
            enablePyjamaGlide();
        }
    }

    public void save() {
        Preferences prefs = Gdx.app.getPreferences("Dreampult_Talents");
        prefs.putBoolean("growBouncy", isGrowBouncy());
        prefs.putBoolean("growSlippery", isGrowSlippery());
        prefs.putBoolean("boostLaunch", isBoostLaunch());
        prefs.putBoolean("additionalLaunch", isAdditionalLaunch());
        prefs.putBoolean("extraBounces", isExtraBounces());
        prefs.putBoolean("pyjamaGlide", isPyjamaGlide());
        prefs.flush();
    }

    public void reset() {
        this.growBouncy = false;
        this.growSlippery = false;

        this.boostLaunch = false;
        this.additionalLaunch = false;

        this.extraBounces = false;
        this.pyjamaGlide = false;
        save();
    }

    public boolean isGrowBouncy() {
        return growBouncy;
    }

    public void enableGrowBouncy() {
        this.growBouncy = true;
        this.growSlippery = false;
    }

    public boolean isGrowSlippery() {
        return growSlippery;
    }

    public void enableGrowSlippery() {
        this.growSlippery = true;
        this.growBouncy = false;
    }

    public boolean isBoostLaunch() {
        return boostLaunch;
    }

    public void enableBoostLaunch() {
        this.boostLaunch = true;
        this.additionalLaunch = false;
    }

    public boolean isAdditionalLaunch() {
        return additionalLaunch;
    }

    public void enableAdditionalLaunch() {
        this.additionalLaunch = true;
        this.boostLaunch = false;
    }

    public boolean isExtraBounces() {
        return extraBounces;
    }

    public void enableExtraBounces() {
        this.extraBounces = true;
        this.pyjamaGlide = false;
    }

    public boolean isPyjamaGlide() {
        return pyjamaGlide;
    }

    public void enablePyjamaGlide() {
        this.pyjamaGlide = true;
        this.extraBounces = false;
    }
}
