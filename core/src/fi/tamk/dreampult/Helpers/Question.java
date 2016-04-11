package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by Clown on 7.4.2016.
 */
public class Question {
    public String text;
    boolean answer;
    public GlyphLayout layout;
    FontHandler font;

    public Question(String text, boolean answer) {
        this.text = text;
        this.answer = answer;
    }

    public boolean isTrue(boolean input){
       if(answer == input) {
           return true;
       } else {
           return false;
       }
    }

    public void initializeLayout(FontHandler fontHandler){
        layout = new GlyphLayout(fontHandler.font, text);
        font = fontHandler;
    }

    public void draw(SpriteBatch batch){
        //GlyphLayout layout = new GlyphLayout(fontHandler.font, text);
        font.font.draw(batch, text, 960 / 2 - layout.width / 2, 300);
    }
}
