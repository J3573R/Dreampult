package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * @author Kalle Heinonen
 */
public class Question {

    public String text;
    boolean answer;

    public GlyphLayout layout;
    FontHandler font;

    /**
     * Sets the text and answer for a question
     *
     * @param text The text to draw with Draw()
     * @param answer The correct answer to the question (True/False)
     */
    public Question(String text, boolean answer) {
        this.text = text;
        this.answer = answer;
    }

    /**
     * Compares the given answer to correct answer
     *
     * @param input The answer given by the user
     * @return Returns true if the answers match, otherwise returns false
     */
    public boolean isTrue(boolean input){
       if(answer == input) {
           return true;
       } else {
           return false;
       }
    }

    /**
     * Initializes the layout for the question
     *
     * @param fontHandler
     */
    public void initializeLayout(FontHandler fontHandler){
        layout = new GlyphLayout(fontHandler.font, text);
        font = fontHandler;
    }

    /**
     * Draws the question
     *
     * @param batch The SpriteBatch used for drawing
     */
    public void draw(SpriteBatch batch){
        //GlyphLayout layout = new GlyphLayout(fontHandler.font, text);
        font.font.draw(batch, text, 960 / 2 - layout.width / 2, 300);
    }
}
