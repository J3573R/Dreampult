package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by root on 14.3.2016.
 */
public class FontHandler {

    public BitmapFont font;
    //public String text;
    public int x;
    public int y;

    public FontHandler() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Ubuntu-R.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        //x = 250;
        //y = 250;
    }

    public void draw(SpriteBatch batch, String text, int x, int y) {
        font.draw(batch, text, x, y);
    }

    public void draw(SpriteBatch batch, String text, int x, int y, Color fontColor) {
        font.setColor(fontColor);

        font.draw(batch, text, x, y);
    }
}
