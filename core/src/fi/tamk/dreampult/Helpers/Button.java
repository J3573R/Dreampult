package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * @author Tommi Hagelberg
 */
public class Button {

    FontHandler fontHandler;

    String buttonText;
    Vector2 buttonTextPosition;
    public Rectangle button;
    public Texture buttonImage;

    Color textColor;
    Color buttonColor;

    public GlyphLayout layout;

    /**
     * Initialize Button with default values.
     * @param fontHandler Saves font handler for later use.
     */
    public Button(FontHandler fontHandler){
        this.fontHandler = fontHandler;
        buttonColor = Color.valueOf("008CBA");
        button = new Rectangle();
    }

    /**
     * Initialize Button with specific values.
     * @param fontHandler Saves font handler for later use.
     * @param x Button X position.
     * @param y Button Y position.
     * @param width Button width.
     * @param height Button height.
     * @param text Button text.
     */
    public Button(FontHandler fontHandler, float x, float y, float width, float height, String text) {
        this.fontHandler = fontHandler;
        buttonColor = Color.valueOf("008CBA");
        button = new Rectangle();
        button.set(x, y, width, height);
        this.buttonText = text;
        createText();
    }

    /**
     * Transforms button with specific values.
     * @param x Button X position.
     * @param y Button Y position.
     * @param width Button width.
     * @param height Button height.
     * @param text Button text.
     */
    public void setButton(float x, float y, float width, float height, String text){
        button.set(x, y, width, height);
        this.buttonText = text;
        createText();
    }

    /**
     * Creates glyph layout from text to help center it inside button.
     */
    public void createText() {
        layout = new GlyphLayout();
        layout.setText(fontHandler.font, buttonText);
        float fontX = button.x + (button.width - layout.width) / 2;
        float fontY = button.y + (button.height + layout.height) / 2;
        buttonTextPosition = new Vector2(fontX, fontY);
    }

    /**
     * Draw button text inside of shape.
     * @param shapeRenderer Renderer for button shape.
     * @param batch Spritebatch for drawing text.
     */
    public void drawShape(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        batch.end();
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(buttonColor);
        shapeRenderer.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
        shapeRenderer.end();
        batch.begin();
        fontHandler.font.draw(batch, buttonText, buttonTextPosition.x, buttonTextPosition.y);
    }

    /**
     * Draw button text inside of texture.
     * @param batch Spritebatch for drawing.
     */
    public void drawImage(SpriteBatch batch) {
        batch.draw(buttonImage, button.getX(), button.getY(), button.getWidth(), button.getHeight());
        fontHandler.font.draw(batch, buttonText, buttonTextPosition.x, buttonTextPosition.y);
    }

    /**
     * @param textColor Changes text color.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    /**
     * @param alpha Changes buttons alpha channel.
     */
    public void setAlpha(float alpha) {
        buttonColor.set(Color.alpha(alpha));
    }

    /**
     * @param text Change buttons text and refresh glyph layout.
     */
    public void setText(String text) {
        buttonText = text;
        createText();
    }
}
