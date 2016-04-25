package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by Clown on 21.4.2016.
 */
public class Popup {
    ShapeRenderer renderer;

    public Texture bg;
    private String text;
    private GlyphLayout layout;
    private FontHandler fontHandler;

    private Vector2 texturePosition;
    private Vector2 fontPosition;
    private float width;
    private float height;

    boolean visible = false;

    public Popup(FontHandler fontHandler) {
        init();
        this.fontHandler = fontHandler;
    }

    public Popup(FontHandler fontHandler, String text, float x, float y) {
        init();
        this.fontHandler = fontHandler;
        setPosition(x, y);
        setText(text);
    }

    public Popup(FontHandler fontHandler, String text, Vector2 position) {
        init();
        this.fontHandler = fontHandler;
        setPosition(position);
        setText(text);
    }

    private void init() {
        renderer = new ShapeRenderer();
        layout = new GlyphLayout();
        texturePosition = new Vector2();
        fontPosition = new Vector2();
    }

    public void setPosition(float x, float y) {
        texturePosition.set(x, y);
        setFontPosition();
    }

    public void setPosition(Vector2 position) {
        this.texturePosition.set(position);
        setFontPosition();
    }

    private void setFontPosition() {
        float x = texturePosition.x + (width - layout.width) / 2;
        float y = texturePosition.y + (height + layout.height) / 2;
        fontPosition.set(x, y);
    }

    public void setSize(float width, float height){
        this.width = width + 50;
        this.height = height + 50;
    }

    public void setText(String text) {
        this.text = text;
        layout.setText(fontHandler.font, text);
        setSize(layout.width, layout.height);
        setPosition(texturePosition.x, texturePosition.y);
    }

    public void draw(SpriteBatch batch) {
        if(visible) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 0.8f);
            batch.draw(bg, texturePosition.x - width / 2, texturePosition.y, width, height);
            fontHandler.font.draw(batch, layout, fontPosition.x - width / 2,  fontPosition.y);
            batch.setColor(c.r, c.g, c.b, 1f);
        }
    }

    public void show(){
        visible = true;
    }

    public void hide(){
        visible = false;
    }
}
