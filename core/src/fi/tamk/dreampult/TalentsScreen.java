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
import fi.tamk.dreampult.Helpers.Button;
import fi.tamk.dreampult.Helpers.Popup;
import fi.tamk.dreampult.Helpers.Saves;

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

    public Rectangle tierOneLock;
    public Rectangle tierTwoLock;
    public Rectangle tierThreeLock;

    Button resetButton;
    Button returnButton;

    Saves saves;

    boolean initialized;

    public Texture img;

    Vector3 touchPoint;

    public Texture emptyBox;

    public Texture lock;

    private int selected = 0;
    final private int BOUNCY = 1;
    final private int SLIP = 2;
    final private int BOOST = 3;
    final private int ADDITIONAL = 4;
    final private int EXTRA = 5;
    final private int BLOCK = 6;

    public Popup tutorial;

    public boolean purchase;
    public boolean notEnoughStars;
    public Button yesButton;
    public Button noButton;

    private int selectedLock = 0;
    final private int TIER_ONE = 1;
    final private int TIER_TWO = 2;
    final private int TIER_THREE = 3;

    public TalentsScreen(GameLoop game){
        loop = game;
        saves = game.game.saves;

        userInterfaceCamera = game.UserInterfaceCamera;
        font = loop.fontHandler;

        touchPoint = new Vector3();
        img = loop.assets.get("images/ui/text_button.png", Texture.class);

        purchase = false;
        notEnoughStars = false;
        initialized = false;
    }

    public void init() {
        saves = loop.game.saves;
        if(!initialized) {
            bouncyIcon = loop.assets.get("images/talents/bouncy1.png", Texture.class);
            catapultIcon = loop.assets.get("images/talents/catapult1.png", Texture.class);
            jumpsIcon = loop.assets.get("images/talents/jumps1.png", Texture.class);
            rainbowIcon = loop.assets.get("images/talents/rainbow1.png", Texture.class);
            shirtIcon = loop.assets.get("images/talents/shirt1.png", Texture.class);
            slipperyIcon = loop.assets.get("images/talents/slippery1.png", Texture.class);
            background = loop.assets.get("images/talents/talentScreen_bg.png", Texture.class);
            emptyBox = loop.assets.get("images/talents/box.png", Texture.class);
            lock = loop.assets.get("images/talents/lock.png", Texture.class);

            shapeRenderer = new ShapeRenderer();

            bouncyRectangle = new Rectangle(80, 10, 150, 150);
            slipperyRectangle = new Rectangle(250, 10, 150, 150);

            boostRectangle = new Rectangle(80, 170, 150, 150);
            launchRectangle = new Rectangle(250, 170, 150, 150);

            pyjamaRectangle = new Rectangle(80, 330, 150, 150);
            extraRectangle = new Rectangle(250, 330, 150, 150);

            tierOneLock = new Rectangle(80, 330, 320, 150);
            tierTwoLock = new Rectangle(80, 10, 320, 150);
            tierThreeLock = new Rectangle(80, 170, 320, 150);

            resetButton = new Button(loop.fontHandler, 520, 0, 260, 100, loop.game.localization.myBundle.get("reset"));
            returnButton = new Button(loop.fontHandler, 550, 150, 200, 100, loop.game.localization.myBundle.get("mainMenu"));

            resetButton.buttonImage = img;
            returnButton.buttonImage = img;

            tutorial = new Popup(loop.fontHandler);
            tutorial.bg = loop.assets.get("images/talents/box.png", Texture.class);
            tutorial.show();

            yesButton = new Button(loop.fontHandler, 450, 290, 150, 100, "Yes");
            noButton = new Button(loop.fontHandler, 720, 290, 150, 100, "No");

            yesButton.buttonImage = img;
            noButton.buttonImage = img;

            initialized = true;
        }
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            userInterfaceCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (!tutorial.returnState()) {

                if(saves.isTier2()) {
                    if (bouncyRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle One touched");

                        if (!saves.isGrowBouncy()) {
                            saves.enableGrowBouncy();
                            saves.save();
                            selected = BOUNCY;
                            System.out.println("Grow Bouncy enabled.");
                        } else {
                            selected = BOUNCY;
                        }

                    } else if (slipperyRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle Two touched");

                        if (!saves.isGrowSlippery()) {
                            saves.enableGrowSlippery();
                            saves.save();
                            selected = SLIP;
                            System.out.println("Grow Slippery enabled.");
                        } else {
                            selected = SLIP;
                        }
                    }
                } else if (!saves.isTier2()) {
                    if ((tierTwoLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() >= 10)) {
                        selectedLock = TIER_TWO;
                        purchase = true;
                        notEnoughStars = false;
                        System.out.println("Lock Two touched");
                    } else if ((tierTwoLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() < 10)){
                        System.out.println("Not enough stars!");
                        notEnoughStars = true;
                    }
                }

                if(saves.isTier3()) {
                    if (boostRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle Three touched");

                        if (!saves.isBoostLaunch()) {
                            saves.enableBoostLaunch();
                            saves.save();
                            selected = BOOST;
                            System.out.println("Boost Launch enabled.");
                        } else {
                            selected = BOOST;
                        }

                    } else if (launchRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle Four touched");

                        if (!saves.isAdditionalLaunch()) {
                            saves.enableAdditionalLaunch();
                            saves.save();
                            selected = ADDITIONAL;
                            System.out.println("Additional Launch enabled.");
                        } else {
                            selected = ADDITIONAL;
                        }
                    }
                } else {
                    if ((tierThreeLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() >= 10)) {
                        selectedLock = TIER_THREE;
                        purchase = true;
                        notEnoughStars = false;
                        System.out.println("Lock Three touched");
                    } else if ((tierThreeLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() < 10)){
                        System.out.println("Not enough stars!");
                        notEnoughStars = true;
                    }
                }

                if(saves.isTier1()) {
                    if (extraRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle Five touched");

                        if (!saves.isExtraBounces()) {
                            saves.enableExtraBounces();
                            saves.save();
                            selected = EXTRA;
                            System.out.println("Extra Bounces enabled.");
                        } else {
                            selected = EXTRA;
                        }

                    } else if (pyjamaRectangle.contains(touchPoint.x, touchPoint.y)) {
                        System.out.println("Rectangle Six touched");

                        if (!saves.isPyjamaProtection()) {
                            saves.enablePyjamaProtection();
                            saves.save();
                            selected = BLOCK;
                            System.out.println("Pyjama Protection enabled.");
                        } else {
                            selected = BLOCK;
                        }
                    }
                } else {
                    if ((tierOneLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() >= 10)) {
                        System.out.println("Lock One touched");
                            selectedLock = TIER_ONE;
                            purchase = true;
                            notEnoughStars = false;

                    } else if ((tierOneLock.contains(touchPoint.x, touchPoint.y)) && (saves.getStars() < 10)){
                        System.out.println("Not enough stars!");
                        notEnoughStars = true;
                    }
                }

                if (purchase) {
                    if (yesButton.button.contains(touchPoint.x, touchPoint.y)) {

                        if (selectedLock == TIER_ONE) {
                            saves.unlockTier(1);
                            System.out.println("Tier One unlocked.");
                            purchase = false;
                            selectedLock = 0;

                        } else if (selectedLock == TIER_TWO) {
                            saves.unlockTier(2);
                            System.out.println("Tier Two unlocked.");
                            purchase = false;
                            selectedLock = 0;

                        } else if (selectedLock == TIER_THREE) {
                            saves.unlockTier(3);
                            System.out.println("Tier Three unlocked.");
                            purchase = false;
                            selectedLock = 0;
                        }

                    } else if (noButton.button.contains(touchPoint.x, touchPoint.y)) {
                        purchase = false;
                        selectedLock = 0;
                    }
                }

                if (resetButton.button.contains(touchPoint.x, touchPoint.y)) {
                    System.out.println("Reset button pressed");
                    //resetProgress();
                    saves.resetTalents();
                    selected = 0;
                    selectedLock = 0;
                    purchase = false;
                    notEnoughStars = false;

                } else if (returnButton.button.contains(touchPoint.x, touchPoint.y)) {
                    loop.game.collection.hideTalentScreen();
                    //savePreferences();
                    saves.save();
                    selected = 0;
                    selectedLock = 0;
                    purchase = false;
                    notEnoughStars = false;
                    loop.game.MainMenu();
                }

            } else if (tutorial.returnState()) {
                tutorial.hide();
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

        if(!saves.isTier1()) {
            loop.game.batch.draw(lock, tierOneLock.getX(), tierOneLock.getY(), tierOneLock.getWidth(), tierOneLock.getHeight());
        } else {
            loop.game.batch.draw(jumpsIcon, extraRectangle.getX(), extraRectangle.getY(), extraRectangle.getWidth(), extraRectangle.getHeight());
            loop.game.batch.draw(shirtIcon, pyjamaRectangle.getX(), pyjamaRectangle.getY(), pyjamaRectangle.getWidth(), pyjamaRectangle.getHeight());
        }

        if(!saves.isTier2()) {
            loop.game.batch.draw(lock, tierTwoLock.getX(), tierTwoLock.getY(), tierTwoLock.getWidth(), tierTwoLock.getHeight());
        } else {
            loop.game.batch.draw(bouncyIcon, bouncyRectangle.getX(), bouncyRectangle.getY(), bouncyRectangle.getWidth(), bouncyRectangle.getHeight());
            loop.game.batch.draw(slipperyIcon, slipperyRectangle.getX(), slipperyRectangle.getY(), slipperyRectangle.getWidth(), slipperyRectangle.getHeight());
        }

        if(!saves.isTier3()) {
            loop.game.batch.draw(lock, tierThreeLock.getX(), tierThreeLock.getY(), tierThreeLock.getWidth(), tierThreeLock.getHeight());
        } else {
            loop.game.batch.draw(rainbowIcon, boostRectangle.getX(), boostRectangle.getY(), boostRectangle.getWidth(), boostRectangle.getHeight());
            loop.game.batch.draw(catapultIcon, launchRectangle.getX(), launchRectangle.getY(), launchRectangle.getWidth(), launchRectangle.getHeight());
        }

        resetButton.setText(loop.game.localization.myBundle.get("reset"));

        returnButton.setText(loop.game.localization.myBundle.get("mainMenu"));

        resetButton.drawImage(loop.game.batch);
        returnButton.drawImage(loop.game.batch);

        if(selected > 0) {
            loop.game.batch.draw(emptyBox, 450, 290, 425, 300);

            if(selected == BOUNCY) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("bounceDescription"), 465, 470);

            } else if (selected == SLIP) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("slipDescription"), 465, 470);

            } else if (selected == BOOST) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("boostDescription"), 465, 470);

            } else if (selected == ADDITIONAL) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("launchDescription"), 465, 500);

            } else if (selected == EXTRA) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("extraDescription"), 465, 470);

            } else if (selected == BLOCK) {
                font.draw(loop.game.batch, loop.game.localization.myBundle.get("glideDescription"), 465, 500);
            }
        }

        tutorial.setText(loop.game.localization.myBundle.get("talentTutorial"));
        tutorial.setPosition(loop.collection.REAL_WIDTH / 2, loop.collection.REAL_HEIGHT / 2);

        tutorial.draw(loop.game.batch);

        if (purchase) {
            loop.game.batch.draw(emptyBox, 450, 290, 425, 300);
            font.draw(loop.game.batch, loop.game.localization.myBundle.get("tierUnlock"), 465, 470);
            yesButton.setText(loop.game.localization.myBundle.get("positive"));
            noButton.setText(loop.game.localization.myBundle.get("negative"));
            yesButton.drawImage(loop.game.batch);
            noButton.drawImage(loop.game.batch);
        } else if (notEnoughStars) {
            loop.game.batch.draw(emptyBox, 450, 290, 425, 300);
            font.draw(loop.game.batch, loop.game.localization.myBundle.get("lackingStars"), 465, 470);
        }

        loop.game.batch.end();

        if(saves.isTier2()) {
            if(!saves.isGrowBouncy()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(bouncyRectangle.getX(), bouncyRectangle.getY(), bouncyRectangle.getWidth(), bouncyRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }

            if(!saves.isGrowSlippery()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(slipperyRectangle.getX(), slipperyRectangle.getY(), slipperyRectangle.getWidth(), slipperyRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }
        }

        if (saves.isTier3()) {
            if(!saves.isBoostLaunch()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(boostRectangle.getX(), boostRectangle.getY(), boostRectangle.getWidth(), boostRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }

            if(!saves.isAdditionalLaunch()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(launchRectangle.getX(), launchRectangle.getY(), launchRectangle.getWidth(), launchRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }
        }

        if (saves.isTier1()) {
            if(!saves.isExtraBounces()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(extraRectangle.getX(), extraRectangle.getY(), extraRectangle.getWidth(), extraRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }

            if(!saves.isPyjamaProtection()) {
                Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setProjectionMatrix(userInterfaceCamera.combined);
                shapeRenderer.setColor(0, 0, 0, 0.75f);
                shapeRenderer.rect(pyjamaRectangle.getX(), pyjamaRectangle.getY(), pyjamaRectangle.getWidth(), pyjamaRectangle.getHeight());
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.end();
            }
        }
    }

    @Override
    public void dispose() {

    }
}
