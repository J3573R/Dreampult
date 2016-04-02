package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by DV6-6B20 on 2.4.2016.
 */
public class QuestionHandler {

    String questionOne;
    String questionTwo;
    String questionThree;
    String questionFour;
    String questionFive;
    String questionSix;
    String questionSeven;
    String questionEight;
    String questionNine;
    String questionTen;
    String questionEleven;

    public QuestionHandler() {
        questionOne = "Sleep is for the weak";

        questionTwo = "Sleep is not for the weak";
    }

    public String randomQuestion() {
        int startNumber = 1;
        int endNumber = 2;
        int randomNumber = MathUtils.random(startNumber, endNumber);

        String returnQuestion = "moi";

        if(randomNumber == 1) {
            returnQuestion = questionOne;
        } else if (randomNumber == 2) {
            returnQuestion = questionTwo;
        }

        return returnQuestion;
    }
}
