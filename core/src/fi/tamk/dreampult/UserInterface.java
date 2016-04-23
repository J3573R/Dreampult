package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.I18NBundle;
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

    Button title;
    Button scoreTitle;
    public Button resumeButton;
    public Button restartButton;
    public Button mainMenuButton;
    public Button quitButton;
    public Button talentsButton;
    public Button starButton;

    Texture shootTexture;

    Unlocks unlocks;

    I18NBundle bundle;

    public UserInterface(GameLoop loop) {
        this.loop = loop;
        shapeRenderer = new ShapeRenderer();
        bundle = loop.game.localization.myBundle;

        unlocks = new Unlocks();

        createPauseButton();
        createSoundButton();
        createShootButton();
        createStar();

        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");

        middle = new Vector2(loop.collection.REAL_WIDTH / 2, loop.collection.REAL_HEIGHT / 2);
        //middle = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        createBackground();
        createPauseMenu();
        createScoreScreen();
    }

    public void draw(SpriteBatch batch) {

        batch.draw(pauseTexture, pauseButton.getX(), pauseButton.getY(), pauseButton.getWidth(), pauseButton.getHeight());
        if(soundState) {
            batch.draw(soundOn, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        } else {
            batch.draw(soundOff, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        }

        starButton.drawImage(batch);

        if(!loop.collection.launch) {
            batch.draw(shootTexture, shootButton.x - shootButton.radius, shootButton.y - shootButton.radius, (shootButton.radius * 2), (shootButton.radius * 2));
        }
    }

    public void drawPauseMenu(SpriteBatch batch) {
        if(loop.collection.isPauseMenu()) {
            batch.end();
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(background.getX(), background.getY(), background.getWidth(), background.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
            batch.begin();
            title.drawShape(shapeRenderer, batch);
            restartButton.drawShape(shapeRenderer, batch);
            resumeButton.drawShape(shapeRenderer, batch);
            mainMenuButton.drawShape(shapeRenderer, batch);
            quitButton.drawShape(shapeRenderer, batch);
        }
    }

    public void drawScoreScreen(SpriteBatch batch) {
        if(loop.collection.isScoreScreen()) {
            batch.end();
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(loop.UserInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(background.getX(), background.getY(), background.getWidth(), background.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
            batch.begin();
            scoreTitle.drawShape(shapeRenderer, batch);
            talentsButton.drawShape(shapeRenderer, batch);
            restartButton.drawShape(shapeRenderer, batch);
            mainMenuButton.drawShape(shapeRenderer, batch);
            quitButton.drawShape(shapeRenderer, batch);
        }
    }

    private void createBackground() {
        background = new Rectangle();
        background.set(960 / 2 - 350 / 2, 560 / 2 - 300 / 2, 300, 350);
    }

    private void createPauseMenu() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float centeredX = background.width / 2 - buttonWidth / 2;
        title = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + background.height - buttonHeight,
                buttonWidth, buttonHeight,
                bundle.get("pause"));
        title.setAlpha(0f);

        resumeButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 9 * 7,
                buttonWidth,
                buttonHeight,
                bundle.get("resume"));

        restartButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 9 * 5,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("restart"));

        mainMenuButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 9 * 3,
                buttonWidth, buttonHeight,
                bundle.get("mainMenu"));

        quitButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 9,
                buttonWidth, buttonHeight,
                bundle.get("quit"));
    }

    public void createScoreScreen() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float centeredX = background.width / 2 - buttonWidth / 2;
        scoreTitle = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + background.height - buttonHeight,
                buttonWidth, buttonHeight,
                bundle.get("woke"));
        scoreTitle.setAlpha(0f);

        talentsButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 9 * 7,
                buttonWidth, buttonHeight,
                bundle.get("talents"));
    }

    private void createPauseButton() {
        pauseTexture = this.loop.assets.get("images/ui/pause.png");
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
        shootTexture = this.loop.assets.get("images/launching/Launch_button.png", Texture.class);
        shootButton = new Circle();
        shootButton.set(880, 80, 60);
    }

    public void shootButtonDown(){
        shootTexture = this.loop.assets.get("images/launching/Launch_button_down.png", Texture.class);
    }

    public void shootButtonUp() {
        shootTexture = this.loop.assets.get("images/launching/Launch_button.png", Texture.class);
    }

    private void createStar() {
        starButton = new Button(loop.fontHandler, 800, 460, 80, 80, String.valueOf(unlocks.getStars()));
        starButton.buttonImage = loop.assets.get("images/objects/allies/star.png", Texture.class);
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
