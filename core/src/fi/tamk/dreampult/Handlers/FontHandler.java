package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * @author Tommi Hagelberg
 */
public class FontHandler {

    /**
     * Font we use throughout our game.
     */
    public BitmapFont font;

    public BitmapFont confirmationFont;

    /**
     * Generates default font for use.
     */
    public FontHandler() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Ubuntu-R.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);

        parameter.size = 96;
        confirmationFont = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     * Draw text at given coordinates.
     * @param batch SpriteBatch for drawing.
     * @param text Text to draw.
     * @param x Coordinate x.
     * @param y Coordinate y.
     */
    public void draw(SpriteBatch batch, String text, int x, int y) {
        font.draw(batch, text, x, y);
    }

    /**
     * Draw confirmation text at given coordinates at given color.
     * @param batch SpriteBatch for drawing.
     * @param text Text to draw.
     * @param x Coordinate x.
     * @param y Coordinate y.
     * @param fontColor Color of the font.
     */
    public void drawConfirmation(SpriteBatch batch, String text, int x, int y, Color fontColor) {
        confirmationFont.setColor(fontColor);
        confirmationFont.draw(batch, text, x, y);
    }


}
