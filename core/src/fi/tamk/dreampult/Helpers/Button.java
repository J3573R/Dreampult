package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by Clown on 3.4.2016.
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

    public Button(FontHandler fontHandler, float x, float y, float width, float height, String text) {

        this.fontHandler = fontHandler;
        fontHandler.GenerateFont(32, Color.WHITE);
        buttonColor = Color.valueOf("008CBA");

        button = new Rectangle();
        button.set(x, y, width, height);
        this.buttonText = text;
        createText();
    }

    public void createText() {
        //GlyphLayout
        layout = new GlyphLayout();
        layout.setText(fontHandler.font, buttonText);
        float fontX = button.x + (button.width - layout.width) / 2;
        float fontY = button.y + (button.height + layout.height) / 2;
        buttonTextPosition = new Vector2(fontX, fontY);
    }

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

    public void drawImage(SpriteBatch batch) {
        batch.draw(buttonImage, button.getX(), button.getY(), button.getWidth(), button.getHeight());
        fontHandler.font.draw(batch, buttonText, buttonTextPosition.x, buttonTextPosition.y);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }

    public void setAlpha(float alpha) {
        buttonColor.set(Color.alpha(alpha));
    }

    public void setText(String text) {
        buttonText = text;
        createText();
    }
}
