package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Helpers.Button;

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
    Rectangle background;
    Vector2 backgroundPosition;

    Button title;
    Button scoreTitle;
    public Button restartButton;
    public Button mainMenuButton;
    public Button quitButton;
    public Button talentsButton;

    Texture shootTexture;

    public UserInterface(GameLoop loop) {
        this.loop = loop;
        shapeRenderer = new ShapeRenderer();

        createPauseButton();
        createSoundButton();
        createShootButton();

        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");

        middle = new Vector2(loop.collection.REAL_WIDTH / 2, loop.collection.REAL_HEIGHT / 2);
        //middle = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        createBackground();
        createPauseMenu();
        createScoreScreen();
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
//        /*batch.end();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.SKY);
//        shapeRenderer.circle(shootButton.x, shootButton.y, shootButton.radius);
//        shapeRenderer.end();
//        batch.begin();*/

            batch.draw(shootTexture, shootButton.x - shootButton.radius, shootButton.y - shootButton.radius, (shootButton.radius * 2), (shootButton.radius * 2));
        }
        batch.setProjectionMatrix(loop.GameCamera.combined);
    }

    public void drawPauseMenu(SpriteBatch batch) {
        if(loop.collection.isPauseMenu()) {
            batch.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            batch.end();
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(background.getX(), background.getY(), background.getWidth(), background.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
            batch.begin();
            title.draw(shapeRenderer, batch);
            restartButton.draw(shapeRenderer, batch);
            mainMenuButton.draw(shapeRenderer, batch);
            quitButton.draw(shapeRenderer, batch);
            batch.setProjectionMatrix(loop.GameCamera.combined);
        }
    }

    public void drawScoreScreen(SpriteBatch batch) {
        if(loop.collection.isScoreScreen()) {
            batch.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            batch.end();
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(background.getX(), background.getY(), background.getWidth(), background.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
            batch.begin();
            scoreTitle.draw(shapeRenderer, batch);
            restartButton.draw(shapeRenderer, batch);
            talentsButton.draw(shapeRenderer, batch);
            mainMenuButton.draw(shapeRenderer, batch);
            //quitButton.draw(shapeRenderer, batch);
            batch.setProjectionMatrix(loop.GameCamera.combined);
        }
    }

    private void createBackground() {
        background = new Rectangle();
        backgroundPosition = new Vector2(300, 300);
        background.set(middle.x - backgroundPosition.x / 2, middle.y - backgroundPosition.y / 2, backgroundPosition.x, backgroundPosition.y);
    }

    private void createPauseMenu() {
        float buttonWidth = 200;
        float buttonHeight = background.height / 6;
        title = new Button(middle.x - buttonWidth / 2, backgroundPosition.y + buttonHeight + 20, buttonWidth, buttonHeight, loop.game.myBundle.get("pause"));
        title.setAlpha(0f);
        restartButton = new Button(middle.x - buttonWidth / 2, backgroundPosition.y, buttonWidth, buttonHeight, loop.game.myBundle.get("restart"));
        mainMenuButton = new Button(middle.x - buttonWidth / 2, backgroundPosition.y - buttonHeight - 20, buttonWidth, buttonHeight, loop.game.myBundle.get("mainMenu"));
        quitButton = new Button(middle.x - buttonWidth / 2, (backgroundPosition.y - buttonHeight * 2) - 40, buttonWidth, buttonHeight, loop.game.myBundle.get("quit"));
    }

    public void createScoreScreen() {
        float buttonWidth = 200;
        float buttonHeight = background.height / 6;
        scoreTitle = new Button(middle.x - buttonWidth / 2, backgroundPosition.y + buttonHeight, buttonWidth, buttonHeight, loop.game.myBundle.get("woke"));
        scoreTitle.setAlpha(0f);
        talentsButton = new Button(middle.x - buttonWidth / 2, (backgroundPosition.y - buttonHeight * 2) - 40, buttonWidth, buttonHeight, "Talents");
    }

    private void createPauseButton() {
        pauseTexture = this.loop.assets.get("images/ui/pause_button.png");
        pauseButton = new Rectangle();
        pauseButton.set(880, 460, 80, 80);
    }

    private void createSoundButton() {
        soundOn = this.loop.assets.get("images/ui/soundOn.png", Texture.class);
        soundOff = this.loop.assets.get("images/ui/soundOff.png", Texture.class);
        soundButton = new Rectangle();
        soundButton.set(0, 460, 80, 80);
    }

    private void createShootButton() {
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
