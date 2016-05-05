package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @author Kalle Heinonen
 */
public class Saves {
    Preferences prefs;

    // Values for the lang integer
    public final int ENG = 1;
    public final int FIN = 2;

    // Values for the sounds integer
    public final int OFF = 0;
    public final int ON = 1;

    // The amount of stars
    int stars;

    // The state of the unlockable levels
    boolean level2;
    boolean level3;

    // The current best score for each level
    float level1Score;
    float level2Score;
    float level3Score;

    // Integers used for changing language and sound state
    int lang;
    int sounds;

    // State of Talent Tier 1 and its Talents
    boolean tier1;
    private boolean extraBounces;
    private boolean pyjamaProtection;

    // State of Talent Tier 2 and its Talents
    boolean tier2;
    private boolean growBouncy;
    private boolean growSlippery;

    // State of Talent Tier 2 and its Talents
    boolean tier3;
    private boolean boostLaunch;
    private boolean additionalLaunch;

    /**
     * Fetches values stored in preferences.
     */
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

    /**
     * Saves the current values to preferences.
     */
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

    /**
     * Resets the values to their defaults.
     */
    public void reset() {
        stars = 0;

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

    /**
     * Resets only the states of the values present in Talents Screen.
     */
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

    /**
     * @return how many stars the player has
     */
    public int getStars() {
        return stars;
    }

    /**
     * Increases the amount of stars the player has.
     */
    public void addStar() {
        stars++;
        save();
    }

    /**
     * @param number unlocks specific Talent Tier indicated by number
     */
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

    /**
     * @return the current language
     */
    public int getLang() {
        return lang;
    }

    /**
     * @param lang changes the language
     */
    public void setLang(int lang) {
        this.lang = lang;
    }

    /**
     * @return the state of sound, either on or off
     */
    public int getSounds() {
        return sounds;
    }

    /**
     * @param sounds changes sound to on or off
     */
    public void setSounds(int sounds) {
        this.sounds = sounds;
    }

    /**
     * @param stars The new value for the amount of stars the player has
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * @return Returns the state of Level 2, unlocked or locked.
     */
    public boolean isLevel2() {
        return level2;
    }

    /**
     * @param level2 The value to set Level 2 state to
     */
    public void setLevel2(boolean level2) {
        this.level2 = level2;
    }

    /**
     * @return Returns the state of Level 3, unlocked or locked.
     */
    public boolean isLevel3() {
        return level3;
    }

    /**
     * @param level3 The value to set Level 3 state to
     */
    public void setLevel3(boolean level3) {
        this.level3 = level3;
    }

    /**
     * @return Returns the state of Talent Tier 1
     */
    public boolean isTier1() {
        return tier1;
    }

    /**
     * @param tier1 sets the state of Talent Tier 1 to either unlocked or locked
     */
    public void setTier1(boolean tier1) {
        this.tier1 = tier1;
    }

    /**
     * @return Returns the state of Talent Tier 2
     */
    public boolean isTier2() {
        return tier2;
    }

    /**
     * @param tier2 sets the state of Talent Tier 2 to either unlocked or locked
     */
    public void setTier2(boolean tier2) {
        this.tier2 = tier2;
    }

    /**
     * @return Returns the state of Talent Talent Tier 3
     */
    public boolean isTier3() {
        return tier3;
    }

    /**
     * @param tier3 sets the state of Talent Tier 3 to either unlocked or locked
     */
    public void setTier3(boolean tier3) {
        this.tier3 = tier3;
    }

    /**
     * @return the state of Grow Bouncy, either on or off
     */
    public boolean isGrowBouncy() {
        return growBouncy;
    }

    /**
     * Enables the Grow Bouncy talent, and disables the Grow Slippery talent.
     */
    public void enableGrowBouncy() {
        this.growBouncy = true;
        this.growSlippery = false;
    }

    /**
     * @return the state of Grow Slippery, either on or off
     */
    public boolean isGrowSlippery() {
        return growSlippery;
    }

    /**
     * Enables the Grow Slippery talent, and disables the Grow Bouncy talent.
     */
    public void enableGrowSlippery() {
        this.growSlippery = true;
        this.growBouncy = false;
    }

    /**
     * @return the state of Boost Launch, either on or off
     */
    public boolean isBoostLaunch() {
        return boostLaunch;
    }

    /**
     * Enables the Boost Launch talent, and disables the Additional Launch talent.
     */
    public void enableBoostLaunch() {
        this.boostLaunch = true;
        this.additionalLaunch = false;
    }

    /**
     * @return the state of Additional Launch, either on or off
     */
    public boolean isAdditionalLaunch() {
        return additionalLaunch;
    }

    /**
     * Enables the Additional Launch talent, and disables the Boost Launch talent.
     */
    public void enableAdditionalLaunch() {
        this.additionalLaunch = true;
        this.boostLaunch = false;
    }

    /**
     * @return the state of Extra Bounces, either on or off
     */
    public boolean isExtraBounces() {
        return extraBounces;
    }

    /**
     * Enables the Extra Bounces talent, and disables the Pyjama Protection talent.
     */
    public void enableExtraBounces() {
        this.extraBounces = true;
        this.pyjamaProtection = false;
    }

    /**
     * @return the state of Pyjama Protection, either on or off
     */
    public boolean isPyjamaProtection() {
        return pyjamaProtection;
    }

    /**
     * Enables the Pyjama Protection talent, and disables the Extra Bounces talent.
     */
    public void enablePyjamaProtection() {
        this.pyjamaProtection = true;
        this.extraBounces = false;
    }

    /**
     * Fetches the best score for each level from the preferences.
     */
    public void getScores() {
        level1Score = prefs.getFloat("level1Score", 0);
        level2Score = prefs.getFloat("level2Score", 0);
        level3Score = prefs.getFloat("level3Score", 0);
    }

    /**
     * @return the best score of Level 1
     */
    public float getLevel1Score() {
        return level1Score;
    }

    /**
     * @param level1Score the new best score for Level 1
     */
    public void setLevel1Score(float level1Score) {
        this.level1Score = level1Score;
        save();
    }

    /**
     * @return the best score of Level 2
     */
    public float getLevel2Score() {
        return level2Score;
    }

    /**
     * @param level2Score the new best score for Level 2
     */
    public void setLevel2Score(float level2Score) {
        this.level2Score = level2Score;
        save();
    }

    /**
     * @return the best score of Level 1
     */
    public float getLevel3Score() {
        return level3Score;
    }

    /**
     * @param level3Score the new best score for Level 3
     */
    public void setLevel3Score(float level3Score) {
        this.level3Score = level3Score;
        save();
    }

    /**
     * @param level Used to determine the current level
     * @return the best score for the correct level
     */
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

    /**
     * @param level the current level
     * @param score sets the new best score for the correct level
     */
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
