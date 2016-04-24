package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Handlers.FontHandler;
import com.badlogic.gdx.utils.I18NBundle;
import fi.tamk.dreampult.Helpers.Button;

/**
 * Created by DV6-6B20 on 12.4.2016.
 */
public class TalentsScreen extends ScreenAdapter {
    public GameLoop loop;
    public OrthographicCamera userInterfaceCamera;
    public FontHandler font;

    public Texture bouncyIcon;
    public Texture catapultIcon;
    public Texture jumpsIcon;
    public Texture rainbowIcon;
    public Texture shirtIcon;
    public Texture slipperyIcon;
    public Texture background;

    public ShapeRenderer shapeRenderer;

    public Rectangle bouncyRectangle;
    public Rectangle slipperyRectangle;
    public Rectangle boostRectangle;
    public Rectangle launchRectangle;
    public Rectangle extraRectangle;
    public Rectangle pyjamaRectangle;

    Button resetButton;
    Button returnButton;

    Talents talents;

    boolean initialized;

    public Texture img;

    Vector3 touchPoint;

    public Texture emptyBox;

    boolean talentSelected;
    boolean bounceSelected;
    boolean slipSelected;
    boolean boostSelected;
    boolean additionalSelected;
    boolean extraSelected;
    boolean glideSelected;

    I18NBundle bundle;

    public TalentsScreen(GameLoop game){
        loop = game;

        userInterfaceCamera = game.UserInterfaceCamera;
        font = loop.fontHandler;

        touchPoint = new Vector3();
        img = loop.assets.get("images/ui/text_button.png", Texture.class);

        talentSelected = false;
        bounceSelected = false;
        slipSelected = false;
        boostSelected = false;
        additionalSelected = false;
        extraSelected = false;
        glideSelected = false;
        initialized = false;
    }

    public void init() {
        if(!initialized) {
            bouncyIcon = loop.assets.get("images/talents/bouncy1.png", Texture.class);
            catapultIcon = loop.assets.get("images/talents/catapult1.png", Texture.class);
            jumpsIcon = loop.assets.get("images/talents/jumps1.png", Texture.class);
            rainbowIcon = loop.assets.get("images/talents/rainbow1.png", Texture.class);
            shirtIcon = loop.assets.get("images/talents/shirt1.png", Texture.class);
            slipperyIcon = loop.assets.get("images/talents/slippery1.png", Texture.class);
            background = loop.assets.get("images/talents/talentScreen_bg.png", Texture.class);
            emptyBox = loop.assets.get("images/talents/box.png", Texture.class);

            shapeRenderer = new ShapeRenderer();

            bouncyRectangle = new Rectangle(80, 10, 150, 150);
            slipperyRectangle = new Rectangle(250, 10, 150, 150);

            boostRectangle = new Rectangle(80, 170, 150, 150);
            launchRectangle = new Rectangle(250, 170, 150, 150);

            pyjamaRectangle = new Rectangle(80, 330, 150, 150);
            extraRectangle = new Rectangle(250, 330, 150, 150);

            resetButton = new Button(loop.fontHandler, 520, 0, 260, 100, loop.game.localization.myBundle.get("reset"));
            returnButton = new Button(loop.fontHandler, 550, 150, 200, 100, loop.game.localization.myBundle.get("mainMenu"));

            resetButton.buttonImage = img;
            returnButton.buttonImage = img;

            talents = loop.game.talents;
            initialized = true;
        }
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (bouncyRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle One touched");

                if(!talents.isGrowBouncy()) {
                    talents.enableGrowBouncy();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = true;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                    System.out.println("Grow Bouncy enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = true;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                }

            } else if (slipperyRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Two touched");

                if(!talents.isGrowSlippery()) {
                    talents.enableGrowSlippery();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = true;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                    System.out.println("Grow Slippery enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = true;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                }

            } else if (boostRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Three touched");

                if(!talents.isBoostLaunch()) {
                    talents.enableBoostLaunch();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = true;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                    System.out.println("Boost Launch enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = true;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = false;
                }

            } else if (launchRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Four touched");

                if(!talents.isAdditionalLaunch()) {
                    talents.enableAdditionalLaunch();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = true;
                    extraSelected = false;
                    glideSelected = false;
                    System.out.println("Additional Launch enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = true;
                    extraSelected = false;
                    glideSelected = false;
                }

            } else if (extraRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Five touched");

                if(!talents.isExtraBounces()) {
                    talents.enableExtraBounces();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = true;
                    glideSelected = false;
                    System.out.println("Extra Bounces enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = true;
                    glideSelected = false;
                }

            } else if (pyjamaRectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Six touched");

                if(!talents.isPyjamaGlide()) {
                    talents.enablePyjamaGlide();
                    talents.save();
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = true;
                    System.out.println("Pyjama Glide enabled.");
                } else {
                    talentSelected = true;
                    bounceSelected = false;
                    slipSelected = false;
                    boostSelected = false;
                    additionalSelected = false;
                    extraSelected = false;
                    glideSelected = true;
                }

            } else if (resetButton.button.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Reset button pressed");
                //resetProgress();
                talents.reset();
                talentSelected = false;
                bounceSelected = false;
                slipSelected = false;
                boostSelected = false;
                additionalSelected = false;
                extraSelected = false;
                glideSelected = false;

            } else if (returnButton.button.contains(touchPoint.x, touchPoint.y)) {
                loop.game.collection.hideTalentScreen();
                //savePreferences();
                talents.save();
                talentSelected = false;
                bounceSelected = false;
                slipSelected = false;
                boostSelected = false;
                additionalSelected = false;
                extraSelected = false;
                glideSelected = false;
                loop.game.MainMenu();
            }

        }

        loop.game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        Gdx.gl.glClearColor(255 / 255f, 182 / 255f, 193 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, 480, 540);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        loop.game.batch.begin();

        loop.game.batch.draw(background, 0, 0, 960, 540);

        loop.game.batch.draw(bouncyIcon, bouncyRectangle.getX(), bouncyRectangle.getY(), bouncyRectangle.getWidth(), bouncyRectangle.getHeight());
        loop.game.batch.draw(slipperyIcon, slipperyRectangle.getX(), slipperyRectangle.getY(), slipperyRectangle.getWidth(), slipperyRectangle.getHeight());

        loop.game.batch.draw(rainbowIcon, boostRectangle.getX(), boostRectangle.getY(), boostRectangle.getWidth(), boostRectangle.getHeight());
        loop.game.batch.draw(catapultIcon, launchRectangle.getX(), launchRectangle.getY(), launchRectangle.getWidth(), launchRectangle.getHeight());

        loop.game.batch.draw(jumpsIcon, extraRectangle.getX(), extraRectangle.getY(), extraRectangle.getWidth(), extraRectangle.getHeight());
        loop.game.batch.draw(shirtIcon, pyjamaRectangle.getX(), pyjamaRectangle.getY(), pyjamaRectangle.getWidth(), pyjamaRectangle.getHeight());

        resetButton.setText(loop.game.localization.myBundle.get("reset"));

        returnButton.setText(loop.game.localization.myBundle.get("mainMenu"));

        resetButton.drawImage(loop.game.batch);
        returnButton.drawImage(loop.game.batch);

        if(talentSelected) {
            loop.game.batch.draw(emptyBox, 450, 290, 425, 300);

            if(bounceSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("bounceDescription"), 465, 470);

            } else if (slipSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("slipDescription"), 465, 470);

            } else if (boostSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("boostDescription"), 465, 470);

            } else if (additionalSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("launchDescription"), 465, 500);

            } else if (extraSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("extraDescription"), 465, 470);

            } else if (glideSelected) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("glideDescription"), 465, 500);
            }
        }

        loop.game.batch.end();

        if(!talents.isGrowBouncy()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(bouncyRectangle.getX(), bouncyRectangle.getY(), bouncyRectangle.getWidth(), bouncyRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isGrowSlippery()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(slipperyRectangle.getX(), slipperyRectangle.getY(), slipperyRectangle.getWidth(), slipperyRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isBoostLaunch()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(boostRectangle.getX(), boostRectangle.getY(), boostRectangle.getWidth(), boostRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isAdditionalLaunch()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(launchRectangle.getX(), launchRectangle.getY(), launchRectangle.getWidth(), launchRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isExtraBounces()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(extraRectangle.getX(), extraRectangle.getY(), extraRectangle.getWidth(), extraRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isPyjamaGlide()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(pyjamaRectangle.getX(), pyjamaRectangle.getY(), pyjamaRectangle.getWidth(), pyjamaRectangle.getHeight());
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {

    }
}
