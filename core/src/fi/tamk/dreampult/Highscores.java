package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Clown on 24.4.2016.
 */
public class Highscores {
    private float level1Score;
    private float level2Score;
    private float level3Score;

    public Highscores() {
        getScores();
    }

    public void getScores() {
        Preferences preferences = Gdx.app.getPreferences("DreampultHighscores");
        level1Score = preferences.getFloat("level1Score", 0);
        level2Score = preferences.getFloat("level2Score", 0);
        level3Score = preferences.getFloat("level3Score", 0);
    }

    public void save() {
        Preferences preferences = Gdx.app.getPreferences("DreampultHighscores");

        preferences.putFloat("level1Score", level1Score);
        preferences.putFloat("level2Score", level2Score);
        preferences.putFloat("level3Score", level3Score);

        preferences.flush();
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
