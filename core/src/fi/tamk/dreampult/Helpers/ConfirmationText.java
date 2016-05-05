package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import fi.tamk.dreampult.Dreampult;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * Created by DV6-6B20 on 4.5.2016.
 */
public class ConfirmationText {

        Dreampult game;
        FontHandler fontHandler;
        GlyphLayout correctAnswerLayout;
        GlyphLayout incorrectAnswerLayout;
        public float timer;
        int location;
        int round;

        public ConfirmationText(Dreampult game) {
            this.game = game;
            this.fontHandler = game.fontHandler;

            location = game.collection.REAL_HEIGHT / 2;


            correctAnswerLayout = new GlyphLayout(fontHandler.font, "loading");
            incorrectAnswerLayout = new GlyphLayout(fontHandler.font, "loading");

            timer = 1;
            round = 0;
        }

        public void drawText(boolean text, boolean direction) {
            if (text && direction) {
                    fontHandler.draw(game.batch,
                            game.localization.myBundle.get("correctAnswer"),
                            game.collection.REAL_WIDTH / 2 - (int)correctAnswerLayout.width / 2, location, Color.WHITE);
                    System.out.println("Location: " + location);
                    location += 2;

            } else if (!text && !direction) {
                    fontHandler.draw(game.batch,
                            game.localization.myBundle.get("incorrectAnswer"),
                            game.collection.REAL_WIDTH / 2 - (int)incorrectAnswerLayout.width / 2, location, Color.WHITE);
                    location -= 2;
            }
            //game.batch.end();
            timer -= Gdx.graphics.getDeltaTime();
            System.out.println(timer);
            round++;
            timeOut();

        }

        public boolean timeOut() {
            boolean end = false;

            if (timer > 0) {
                end = false;
            } else if (timer <= 0) {
                end = true;

                game.setScreen(game.gameLoop);
                game.collection.start();
                game.loadingScreen.answerInteger = 0;
            }

            return end;
        }
    }
