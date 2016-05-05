package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * @author Tommi Hagelberg
 */
public class Popup {
    ShapeRenderer renderer;

    public Texture bg;
    private GlyphLayout layout;
    private FontHandler fontHandler;

    private Vector2 texturePosition;
    private Vector2 fontPosition;
    private float width;
    private float height;

    boolean visible = false;

    /**
     * Constructs popup with default values.
     * @param fontHandler Saves font handler for later use.
     */
    public Popup(FontHandler fontHandler) {
        init();
        this.fontHandler = fontHandler;
    }

    /**
     * Initialize default values.
     */
    private void init() {
        renderer = new ShapeRenderer();
        layout = new GlyphLayout();
        texturePosition = new Vector2();
        fontPosition = new Vector2();
    }

    /**
     * Set position of texture and text.
     * @param x Popup X position.
     * @param y Popup Y position.
     */
    public void setPosition(float x, float y) {
        texturePosition.set(x, y);
        setFontPosition();
    }

    /**
     * Set position of texture and text.
     * @param position Popup X and Y position.
     */
    public void setPosition(Vector2 position) {
        this.texturePosition.set(position);
        setFontPosition();
    }

    /**
     * Set position of font.
     */
    private void setFontPosition() {
        float x = texturePosition.x + (width - layout.width) / 2;
        float y = texturePosition.y + (height + layout.height) / 2;
        fontPosition.set(x, y);
    }

    /**
     * Set popup size little bigger than text.
     * @param width Background width.
     * @param height Background height.
     */
    public void setSize(float width, float height){
        this.width = width + 50;
        this.height = height + 50;
    }

    /**
     * @param text Set new text and adjust background according the text.
     */
    public void setText(String text) {
        layout.setText(fontHandler.font, text);
        setSize(layout.width, layout.height);
        setPosition(texturePosition.x, texturePosition.y);
    }

    /**
     * Draws text and background.
     * @param batch Spritebatch for drawing.
     */
    public void draw(SpriteBatch batch) {
        if(visible) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 0.8f);
            batch.draw(bg, texturePosition.x - width / 2, texturePosition.y, width, height);
            fontHandler.font.draw(batch, layout, fontPosition.x - width / 2,  fontPosition.y);
            batch.setColor(c.r, c.g, c.b, 1f);
        }
    }

    /**
     * Shows popup.
     */
    public void show(){
        visible = true;
    }

    /**
     * Hides popup.
     */
    public void hide(){
        visible = false;
    }

    /**
     * @return Tells if popup is visible or not.
     */
    public boolean returnState() {
        return visible;
    }
}
