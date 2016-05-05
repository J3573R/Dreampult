package fi.tamk.dreampult.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import fi.tamk.dreampult.Dreampult;
import fi.tamk.dreampult.Handlers.FontHandler;

/**
 * @author Kalle Heinonen
 */
public class ConfirmationText {

    Dreampult game;
    FontHandler fontHandler;

    // Layouts for the text variants
    GlyphLayout correctAnswerLayout;
    GlyphLayout incorrectAnswerLayout;

    // Timer for drawing the text
    public float timer;

    // Location of the text on the Y-axis
    int location;

    /**
     * Creates a moving batch of text to show the player if they answered correctly.
     *
     * @param game used for accessing collection and fontHandler
     */
    public ConfirmationText(Dreampult game) {
        this.game = game;
        this.fontHandler = game.fontHandler;

        location = game.collection.REAL_HEIGHT / 2;

        correctAnswerLayout = new GlyphLayout(fontHandler.font, "loading");
        incorrectAnswerLayout = new GlyphLayout(fontHandler.font, "loading");

        timer = 1;
    }

    /**
     * Draws a batch of text based on whether the player answered the question correctly or not.
     *
     * @param text decides whether "Correct" or "Incorrect" is drawn
     * @param direction decides the movement direction for the text, up or down
     */
    public void drawText(boolean text, boolean direction) {
        if (timer > 0) {

        if (text && direction) {
            fontHandler.draw(game.batch,
                    game.localization.myBundle.get("correctAnswer"),
                    game.collection.REAL_WIDTH / 2 - (int)correctAnswerLayout.width / 2, location, Color.WHITE);
            location += 2;

        } else if (!text && !direction) {
            fontHandler.draw(game.batch,
                    game.localization.myBundle.get("incorrectAnswer"),
                    game.collection.REAL_WIDTH / 2 - (int)incorrectAnswerLayout.width / 2, location, Color.WHITE);
            location -= 2;
        }

        timer -= Gdx.graphics.getDeltaTime();
        timeOut();
        }
    }

    /**
     * Checks the timer, and changes the screen when the timer ends.
     */
    public void timeOut() {
        if (timer <= 0) {
            game.setScreen(game.gameLoop);
            game.collection.start();
            game.loadingScreen.answerInteger = 0;
        }
    }
}
