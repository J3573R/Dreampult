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
        questionOne = "One of the phases of a dream is called REM";

        //"baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab" <-- Maximum length for a question

        questionTwo = "Your pulse becomes faster during REM";

        questionThree = "Things are stored in memory while sleeping";

        questionFour = "Brain cell structure changes during sleep";

        questionFive = "Sleeping too little has no negative effects";

        questionSix = "A short nap can help remembering things";

        questionSeven = "A teen needs more sleep than an adult";

        questionEight = "Caffeine can make falling asleep harder";

        questionNine = "Sugar has no effect on sleeping";

        questionTen = "Maintaining a sleep pattern is important";

        questionEleven = "Sleeping for 4 hours is enough for a teen";
    }

    public String randomQuestion() {
        int startNumber = 1;
        int endNumber = 11;
        int randomNumber = MathUtils.random(startNumber, endNumber);

        String returnQuestion = "moi";

        if(randomNumber == 1) {
            returnQuestion = questionOne;
        } else if(randomNumber == 2) {
            returnQuestion = questionTwo;
        } else if(randomNumber == 3) {
            returnQuestion = questionThree;
        } else if(randomNumber == 4) {
            returnQuestion = questionFour;
        } else if(randomNumber == 5) {
            returnQuestion = questionFive;
        } else if(randomNumber == 6) {
            returnQuestion = questionSix;
        } else if(randomNumber == 7) {
            returnQuestion = questionSeven;
        } else if(randomNumber == 8) {
            returnQuestion = questionEight;
        } else if(randomNumber == 9) {
            returnQuestion = questionNine;
        } else if(randomNumber == 10) {
            returnQuestion = questionTen;
        } else if(randomNumber == 11) {
            returnQuestion = questionEleven;
        }

        return returnQuestion;
    }
}
