package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Clown on 26.4.2016.
 */
public class Saves {
    Preferences prefs;

    public final int ENG = 1;
    public final int FIN = 2;

    public final int OFF = 0;
    public final int ON = 1;

    int stars;

    boolean level2;
    boolean level3;

    float level1Score;
    float level2Score;
    float level3Score;

    int lang;
    int sounds;

    boolean tier1;
    private boolean extraBounces;
    private boolean pyjamaProtection;

    boolean tier2;
    private boolean growBouncy;
    private boolean growSlippery;

    boolean tier3;
    private boolean boostLaunch;
    private boolean additionalLaunch;

    public Saves(){
        prefs = Gdx.app.getPreferences("Dreampult");

        stars = prefs.getInteger("stars", 0);

        level2 = prefs.getBoolean("level2", false);
        level3 = prefs.getBoolean("level3", false);

        getScores();

        lang = prefs.getInteger("lang", ENG);
        sounds = prefs.getInteger("sounds", ON);

        tier1 = prefs.getBoolean("tier1", false);
        extraBounces = prefs.getBoolean("extraBounces", false);
        pyjamaProtection = prefs.getBoolean("pyjamaProtection", false);

        tier2 = prefs.getBoolean("tier2", false);
        growBouncy = prefs.getBoolean("growBouncy", false);
        growSlippery = prefs.getBoolean("growSlippery", false);

        tier3 = prefs.getBoolean("tier3", false);
        boostLaunch = prefs.getBoolean("boostLaunch", false);
        additionalLaunch = prefs.getBoolean("additionalLaunch", false);
    }

    public void save() {
        prefs.putInteger("stars", stars);

        prefs.putBoolean("level2", level2);
        prefs.putBoolean("level3", level3);

        prefs.putFloat("level1Score", level1Score);
        prefs.putFloat("level2Score", level2Score);
        prefs.putFloat("level3Score", level3Score);

        prefs.putInteger("lang", lang);
        prefs.putInteger("sounds", sounds);

        prefs.putBoolean("tier1", tier1);
        prefs.putBoolean("extraBounces", extraBounces);
        prefs.putBoolean("pyjamaProtection", pyjamaProtection);

        prefs.putBoolean("tier2", tier2);
        prefs.putBoolean("growBouncy", growBouncy);
        prefs.putBoolean("growSlippery", growSlippery);

        prefs.putBoolean("tier3", tier3);
        prefs.putBoolean("boostLaunch", boostLaunch);
        prefs.putBoolean("additionalLaunch", additionalLaunch);

        prefs.flush();
    }

    public void reset() {
        stars = 30;

        level2 = false;
        level3 = false;

        level1Score = 0;
        level2Score = 0;
        level3Score = 0;

        tier1 = false;
        extraBounces = false;
        pyjamaProtection = false;

        tier2 = false;
        growBouncy = false;
        growSlippery = false;

        tier3 = false;
        boostLaunch = false;
        additionalLaunch = false;
        save();
    }

    public void resetTalents() {
        tier1 = false;
        extraBounces = false;
        pyjamaProtection = false;

        tier2 = false;
        growBouncy = false;
        growSlippery = false;

        tier3 = false;
        boostLaunch = false;
        additionalLaunch = false;
        save();
    }

    public int getStars() {
        return stars;
    }

    public void addStar() {
        stars++;
        save();
    }

    public void unlockTier(int number) {
        stars = stars - 10;

        if (number == 1) {
            setTier1(true);
        } else if (number == 2) {
            setTier2(true);
        } else if (number == 3) {
            setTier3(true);
        }

        save();
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getSounds() {
        return sounds;
    }

    public void setSounds(int sounds) {
        this.sounds = sounds;
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

    public boolean isTier1() {
        return tier1;
    }

    public void setTier1(boolean tier1) {
        this.tier1 = tier1;
    }

    public boolean isTier2() {
        return tier2;
    }

    public void setTier2(boolean tier2) {
        this.tier2 = tier2;
    }

    public boolean isTier3() {
        return tier3;
    }

    public void setTier3(boolean tier3) {
        this.tier3 = tier3;
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
        this.pyjamaProtection = false;
    }

    public boolean isPyjamaProtection() {
        return pyjamaProtection;
    }

    public void enablePyjamaProtection() {
        this.pyjamaProtection = true;
        this.extraBounces = false;
    }

    public void getScores() {
        level1Score = prefs.getFloat("level1Score", 0);
        level2Score = prefs.getFloat("level2Score", 0);
        level3Score = prefs.getFloat("level3Score", 0);
    }

    public float getLevel1Score() {
        return level1Score;
    }

    public void setLevel1Score(float level1Score) {
        this.level1Score = level1Score;
        save();
    }

    public float getLevel2Score() {
        return level2Score;
    }

    public void setLevel2Score(float level2Score) {
        this.level2Score = level2Score;
        save();
    }

    public float getLevel3Score() {
        return level3Score;
    }

    public void setLevel3Score(float level3Score) {
        this.level3Score = level3Score;
        save();
    }

    public float getScore(int level){
        float value = 0;
        switch (level){
            case 1:
                value = getLevel1Score();
                break;
            case 2:
                value = getLevel2Score();
                break;
            case 3:
                value = getLevel3Score();
                break;
        }
        return value;
    }

    public void setScore(int level, float score) {
        switch (level){
            case 1:
                setLevel1Score(score);
                break;
            case 2:
                setLevel2Score(score);
                break;
            case 3:
                setLevel3Score(score);
                break;
        }
    }

}
