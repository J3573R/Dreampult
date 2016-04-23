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

    private BitmapFont normalFont;
    private BitmapFont headerFont;

    public BitmapFont font;
    public float x;
    public float y;

    public FontHandler() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Ubuntu-R.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        normalFont = generator.generateFont(parameter);

        parameter.size = 50;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 0;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;

        headerFont = generator.generateFont(parameter);

        generator.dispose();

        font = normalFont;
    }

    public BitmapFont getNormalFont() {
        return normalFont;
    }

    public BitmapFont getHeaderFont() {
        return headerFont;
    }

    public void GenerateFont(int size, Color color) {
        /*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Ubuntu-R.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        font = generator.generateFont(parameter);
        generator.dispose();*/
    }

    public void draw(SpriteBatch batch, String text, int x, int y) {
        normalFont.draw(batch, text, x, y);
    }

    public void draw(SpriteBatch batch, String text, int x, int y, Color fontColor) {
        font.setColor(fontColor);
        normalFont.draw(batch, text, x, y);
    }
}
