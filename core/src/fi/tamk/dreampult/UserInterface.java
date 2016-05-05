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
import fi.tamk.dreampult.Helpers.Saves;

/**
 * @author Kalle Heinonen & Tommi Hagelberg
 */
public class UserInterface {
    GameLoop loop;

    Texture pauseTexture;
    public Rectangle pauseButton;

    Texture pauseMenu;

    Texture soundOn;
    Texture soundOff;
    public Rectangle soundButton;

    public Circle shootButton;

    Vector2 middle;


    ShapeRenderer shapeRenderer;
    Rectangle background;

    float buttonWidth;
    float buttonHeight;
    float centeredX;

    Button title;
    Button scoreTitle;
    Button score;
    public Button resumeButton;
    public Button restartButton;
    public Button mainMenuButton;
    public Button quitButton;
    public Button talentsButton;
    public Button starButton;

    Texture shootTexture;

    Saves saves;

    /**
     * Creates all the menus and buttons.
     *
     * @param loop used to access GameLoop's saves, AssetManager, and fontHandler
     */
    public UserInterface(GameLoop loop) {
        this.loop = loop;
        shapeRenderer = new ShapeRenderer();
        score = new Button(loop.fontHandler);

        saves = loop.game.saves;

        createPauseButton();
        createSoundButton();
        createShootButton();
        createStar();

        pauseMenu = this.loop.assets.get("images/ui/pause_menu.png");

        middle = new Vector2(loop.collection.REAL_WIDTH / 2, loop.collection.REAL_HEIGHT / 2);

        createBackground();

        buttonWidth = 200;
        buttonHeight = 50;
        centeredX = background.width / 2 - buttonWidth / 2;

        createPauseMenu();
        createScoreScreen();
    }

    /**
     * Initializes the value of stars
     */
    public void init(){
        starButton.setText(String.valueOf(saves.getStars()));
    }

    /**
     * Refreshes the best score of the current level
     */
    public void refreshScore(){
        float highscore = saves.getScore(loop.map.getLevel());
        int hour = (int) (highscore * 0.6f) / 60;
        int minutes = (int) (highscore * 0.6f) % 60;
        String slept = hour + "h " + minutes + "min";
        score.setButton(background.x + centeredX,
                background.y + (background.height - buttonHeight) / 10 * 8 + 5,
                buttonWidth,
                buttonHeight,
                loop.game.localization.myBundle.get("highscore") + "\n" + slept);
        score.setAlpha(0);
    }

    /**
     * Changes the text of the buttons to match the current language
     */
    public void changeLang(){
        resumeButton.setText(loop.game.localization.myBundle.get("resume"));
        restartButton.setText(loop.game.localization.myBundle.get("restart"));
        mainMenuButton.setText(loop.game.localization.myBundle.get("mainMenu"));
        quitButton.setText(loop.game.localization.myBundle.get("quit"));
        talentsButton.setText(loop.game.localization.myBundle.get("talents"));

        scoreTitle.setText(loop.game.localization.myBundle.get("woke"));
        title.setText(loop.game.localization.myBundle.get("pause"));
    }

    /**
     * Draws all the menus and buttons
     *
     * @param batch SpriteBatch used for drawing
     */
    public void draw(SpriteBatch batch) {

        batch.draw(pauseTexture, pauseButton.getX(), pauseButton.getY(), pauseButton.getWidth(), pauseButton.getHeight());
        if(saves.getSounds() == saves.ON) {
            batch.draw(soundOn, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        } else {
            batch.draw(soundOff, soundButton.getX(), soundButton.getY(), soundButton.getWidth(), soundButton.getHeight());
        }

        starButton.drawImage(batch);

        if(!loop.collection.launch) {
            batch.draw(shootTexture, shootButton.x - shootButton.radius, shootButton.y - shootButton.radius, (shootButton.radius * 2), (shootButton.radius * 2));
        }
    }

    /**
     * Draws the pause menu and its buttons
     *
     * @param batch SpriteBatch used for drawing
     */
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
            score.drawShape(shapeRenderer, batch);
            restartButton.drawShape(shapeRenderer, batch);
            resumeButton.drawShape(shapeRenderer, batch);
            mainMenuButton.drawShape(shapeRenderer, batch);
            quitButton.drawShape(shapeRenderer, batch);
        }
    }

    /**
     * Draws the score screen and its buttons
     *
     * @param batch SpriteBatch used for drawing
     */
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
            score.drawShape(shapeRenderer, batch);
            talentsButton.drawShape(shapeRenderer, batch);
            restartButton.drawShape(shapeRenderer, batch);
            mainMenuButton.drawShape(shapeRenderer, batch);
            quitButton.drawShape(shapeRenderer, batch);
        }
    }

    /**
     * Creates the background rectangle used by pause menu and score screen
     */
    private void createBackground() {
        background = new Rectangle();
        background.set(960 / 2 - 350 / 2, 560 / 2 - 400 / 2, 350, 400);
    }

    /**
     * Creates the pause menu and its buttons
     */
    private void createPauseMenu() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float centeredX = background.width / 2 - buttonWidth / 2;
        title = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + background.height - buttonHeight,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("pause"));
        title.setAlpha(0f);

        resumeButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 10 * 6 + 5,
                buttonWidth,
                buttonHeight,
                loop.game.localization.myBundle.get("resume"));

        restartButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 10 * 4 + 5,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("restart"));

        mainMenuButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 10 * 2 + 5,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("mainMenu"));

        quitButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + 5,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("quit"));
    }

    /**
     * Creates the score screen and its buttons
     */
    public void createScoreScreen() {
        float buttonWidth = 200;
        float buttonHeight = 50;
        float centeredX = background.width / 2 - buttonWidth / 2;
        scoreTitle = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + background.height - buttonHeight,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("woke"));
        scoreTitle.setAlpha(0f);

        talentsButton = new Button(loop.fontHandler,
                background.x + centeredX,
                background.y + (background.height - buttonHeight) / 10 * 6 + 5,
                buttonWidth, buttonHeight,
                loop.game.localization.myBundle.get("talents"));
    }

    /**
     * Creates the pause button
     */
    private void createPauseButton() {
        pauseTexture = this.loop.assets.get("images/ui/pause.png");
        pauseButton = new Rectangle();
        pauseButton.set(880, 460, 80, 80);
    }

    /**
     * Creates the sound button
     */
    private void createSoundButton() {
        soundOn = this.loop.assets.get("images/ui/soundOn.png", Texture.class);
        soundOff = this.loop.assets.get("images/ui/soundOff.png", Texture.class);
        soundButton = new Rectangle();
        soundButton.set(0, 460, 80, 80);
    }

    /**
     * Creates the shoot button
     */
    private void createShootButton() {
        shootTexture = this.loop.assets.get("images/launching/Launch_button.png", Texture.class);
        shootButton = new Circle();
        shootButton.set(880, 80, 60);
    }

    /**
     * Changes the texture used by the shoot button
     */
    public void shootButtonDown(){
        shootTexture = this.loop.assets.get("images/launching/Launch_button_down.png", Texture.class);
    }

    /**
     * Changes the texture used by the shoot button
     */
    public void shootButtonUp() {
        shootTexture = this.loop.assets.get("images/launching/Launch_button.png", Texture.class);
    }

    /**
     * Creates the button used to show the amount of stars the player has
     */
    private void createStar() {
        starButton = new Button(loop.fontHandler, 800, 460, 80, 80, String.valueOf(saves.getStars()));
        starButton.buttonImage = loop.assets.get("images/objects/allies/star.png", Texture.class);
    }
}
