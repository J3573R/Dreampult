package fi.tamk.dreampult.Handlers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fi.tamk.dreampult.Helpers.Question;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DV6-6B20 on 2.4.2016.
 */
public class QuestionHandler {

    ArrayList<Question> questions = new ArrayList();
    Question activeQuestion;

    public QuestionHandler(){
        questions.add(new Question("One of the phases of a dream is called REM", true));
        questions.add(new Question("Your pulse becomes faster during REM", true));
        questions.add(new Question("Things are stored in memory while sleeping", true));
        questions.add(new Question("Brain cell structure changes during sleep", true));
        questions.add(new Question("Sleeping too little has no negative effects", false));
        questions.add(new Question("A short nap can help remembering things", true));
        questions.add(new Question("A teen needs more sleep than an adult", true));
        questions.add(new Question("Caffeine can make falling asleep harder", true));
        questions.add(new Question("Sugar has no effect on sleeping", false));
        questions.add(new Question("Maintaining a sleep pattern is important", true));
        questions.add(new Question("Sleeping for 4 hours is enough for a teen", false));
        //"baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab" <-- Maximum length for a question
    }

    public Question anyItem() {
        Random random = new Random();
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }

    /*String questionOne;
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
    }*/
}
