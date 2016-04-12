package fi.tamk.dreampult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fi.tamk.dreampult.Helpers.Button;

/**
 * Created by DV6-6B20 on 12.4.2016.
 */
public class TalentsScreen extends ScreenAdapter {
    public GameLoop loop;
    public OrthographicCamera userInterfaceCamera;

    public Texture talentIcon;

    public ShapeRenderer shapeRenderer;

    public Rectangle RectangleOne;
    public Rectangle RectangleTwo;
    public Rectangle RectangleThree;
    public Rectangle RectangleFour;
    public Rectangle RectangleFive;
    public Rectangle RectangleSix;

    Button resetButton;
    Button returnButton;

    Talents talents;

    public TalentsScreen(GameLoop game, OrthographicCamera uiCamera){
        loop = game;
        talentIcon = loop.assets.get("images/icon.png", Texture.class);
        shapeRenderer = new ShapeRenderer();
        userInterfaceCamera = uiCamera;

        RectangleOne = new Rectangle(120, 0, 100, 100);
        RectangleTwo = new Rectangle(240, 0, 100, 100);

        RectangleThree = new Rectangle(120, 200, 100, 100);
        RectangleFour = new Rectangle(240, 200, 100, 100);

        RectangleFive = new Rectangle(120, 400, 100, 100);
        RectangleSix = new Rectangle(240, 400, 100, 100);

        resetButton = new Button(550, 0, 200, 100, "Reset");
        returnButton = new Button(760, 0, 200, 100, "Main Menu");

        talents = game.talents;

        loadPreferences();
    }

    @Override
    public void render(float delta) {

        Vector3 touchPoint = new Vector3();

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (RectangleOne.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle One touched");

                if(!talents.isGrowBouncy()) {
                    talents.enableGrowBouncy();
                    System.out.println("Grow Bouncy enabled.");
                }

            } else if (RectangleTwo.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Two touched");

                if(!talents.isGrowSlippery()) {
                    talents.enableGrowSlippery();
                    System.out.println("Grow Slippery enabled.");
                }

            } else if (RectangleThree.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Three touched");

                if(!talents.isBoostLaunch()) {
                    talents.enableBoostLaunch();
                    System.out.println("Boost Launch enabled.");
                }

            } else if (RectangleFour.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Four touched");

                if(!talents.isAdditionalLaunch()) {
                    talents.enableAdditionalLaunch();
                    System.out.println("Additional Launch enabled.");
                }

            } else if (RectangleFive.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Five touched");

                if(!talents.isExtraBounces()) {
                    talents.enableExtraBounces();
                    System.out.println("Extra Bounces enabled.");
                }

            } else if (RectangleSix.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Rectangle Six touched");

                if(!talents.isPyjamaGlide()) {
                    talents.enablePyjamaGlide();
                    System.out.println("Pyjama Glide enabled.");
                }

            } else if (resetButton.button.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("Reset button pressed");
                resetProgress();

            } else if (returnButton.button.contains(touchPoint.x, touchPoint.y)) {
                savePreferences();
                loop.game.MainMenu();
            }

        }

        loop.game.batch.setProjectionMatrix(userInterfaceCamera.combined);

        Gdx.gl.glClearColor(255/255f, 182/255f, 193/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, 480, 540);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.end();

        loop.game.batch.begin();

        loop.game.batch.draw(talentIcon, 120, 0, 100, 100);
        loop.game.batch.draw(talentIcon, 240, 0, 100, 100);

        loop.game.batch.draw(talentIcon, 120, 200, 100, 100);
        loop.game.batch.draw(talentIcon, 240, 200, 100, 100);

        loop.game.batch.draw(talentIcon, 120, 400, 100, 100);
        loop.game.batch.draw(talentIcon, 240, 400, 100, 100);

        resetButton.draw(shapeRenderer, loop.game.batch);
        returnButton.draw(shapeRenderer, loop.game.batch);

        loop.game.batch.end();

        if(!talents.isGrowBouncy()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(120, 0, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isGrowSlippery()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(240, 0, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isBoostLaunch()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(120, 200, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isAdditionalLaunch()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(240, 200, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isExtraBounces()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(120, 400, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }

        if(!talents.isPyjamaGlide()) {
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
            shapeRenderer.setColor(0, 0, 0, 0.75f);
            shapeRenderer.rect(240, 400, 100, 100);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.end();
        }
    }

    public void savePreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putBoolean("growBouncy", talents.isGrowBouncy());
        prefs.putBoolean("growSlippery", talents.isGrowSlippery());
        prefs.putBoolean("boostLaunch", talents.isBoostLaunch());
        prefs.putBoolean("additionalLaunch", talents.isAdditionalLaunch());
        prefs.putBoolean("extraBounces", talents.isExtraBounces());
        prefs.putBoolean("pyjamaGlide", talents.isPyjamaGlide());
        prefs.flush();
    }

    public void loadPreferences() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        boolean growBouncy = prefs.getBoolean("growBouncy");
        boolean growSlippery = prefs.getBoolean("growSlippery");
        boolean boostLaunch = prefs.getBoolean("boostLaunch");
        boolean additionalLaunch = prefs.getBoolean("additionalLaunch");
        boolean extraBounces = prefs.getBoolean("extraBounces");
        boolean pyjamaGlide = prefs.getBoolean("pyjamaGlide");

        if(growBouncy) {
            talents.enableGrowBouncy();

        } else if(growSlippery) {
            talents.enableGrowSlippery();

        } else if(boostLaunch) {
            talents.enableBoostLaunch();

        } else if(additionalLaunch) {
            talents.enableAdditionalLaunch();

        } else if(extraBounces) {
            talents.enableExtraBounces();

        } else if(pyjamaGlide) {
            talents.enablePyjamaGlide();
        }
    }

    public void resetProgress() {
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        prefs.putBoolean("growBouncy", false);
        prefs.putBoolean("growSlippery", false);
        prefs.putBoolean("boostLaunch", false);
        prefs.putBoolean("additionalLaunch", false);
        prefs.putBoolean("extraBounces", false);
        prefs.putBoolean("pyjamaGlide", false);
        prefs.flush();

        talents.resetTalents();
    }

    @Override
    public void dispose() {

    }
}
