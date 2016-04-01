package fi.tamk.dreampult;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Clown on 15.3.2016.
 */
public class UserInterface {
    GameLoop loop;

    Texture pauseTexture;
    public Rectangle pauseButton;

    Texture pauseMenu;

    Texture soundOn;
    Texture soundOff;
    public Rectangle soundButton;
    boolean soundState = true;

    public Circle shootButton;

    Vector2 middle;


    ShapeRenderer shapeRenderer;
    public Rectangle bg;

    Texture shootTexture;

    public UserInterface(GameLoop loop) {
        this.loop = loop;

        createPauseButton();
        createSoundButton();
        createShootButton();

        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");



        middle = new Vector2(loop.collection.REAL_WIDTH / 2, loop.collection.REAL_HEIGHT / 2);
    }

    public void draw(SpriteBatch batch) {
        batch.setProjectionMatrix(loop.UserInterfaceCamera.combined);
        batch.draw(pauseTexture, pauseButton.getX(), pauseButton.getY(), pauseButton.getWidth(), pauseButton.getHeight());
        if(soundState) {
            batch.draw(soundOn, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        } else {
            batch.draw(soundOff, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        }

        if(!loop.collection.launch) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.circle(shootButton.x, shootButton.y, shootButton.radius);
        shapeRenderer.end();
        batch.begin();

            batch.draw(shootTexture, shootButton.x - shootButton.radius, shootButton.y - shootButton.radius, (shootButton.radius * 2), (shootButton.radius * 2));
        }
        batch.setProjectionMatrix(loop.GameCamera.combined);
    }

    public void drawPauseMenu(SpriteBatch batch) {
        if(loop.collection.isPauseMenu()) {
            batch.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.SKY);
            shapeRenderer.rect(bg.getX(), bg.getY(), bg.getWidth(), bg.getHeight());
            shapeRenderer.end();
            batch.begin();
            batch.setProjectionMatrix(loop.GameCamera.combined);
        }
    }

    public void createPauseMenu() {
        shapeRenderer = new ShapeRenderer();
        bg = new Rectangle();
        Vector2 bgSize = new Vector2(200, 200);
        bg.set(middle.x - bgSize.x / 2, middle.y - bgSize.y / 2, bgSize.x, bgSize.y);
    }

    public void createPauseButton() {
        pauseTexture = this.loop.assets.get("images/ui/pause_button.png");
        pauseButton = new Rectangle();
        pauseButton.set(880, 460, 80, 80);
    }

    public void createSoundButton() {
        soundOn = this.loop.assets.get("images/ui/soundOn.png", Texture.class);
        soundOff = this.loop.assets.get("images/ui/soundOff.png", Texture.class);
        soundButton = new Rectangle();
        soundButton.set(0, 460, 80, 80);
    }

    public void createShootButton() {
        shootTexture = this.loop.assets.get("images/ui/shootButton.png", Texture.class);
        shootButton = new Circle();
        shootButton.set(880, 80, 60);
    }

    public void toggleSound() {
        if(soundState) {
            soundState = false;
        } else {
            soundState = true;
        }
    }

    public boolean isSoundOn(){ return soundState; }
}
