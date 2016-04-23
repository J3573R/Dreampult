package fi.tamk.dreampult.Handlers;


import fi.tamk.dreampult.Dreampult;
import fi.tamk.dreampult.Helpers.Question;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DV6-6B20 on 2.4.2016.
 */
public class QuestionHandler {

    ArrayList<Question> questions = new ArrayList();
    Dreampult game;

    public QuestionHandler(Dreampult game){
        this.game = game;

        questions.add(new Question(game.localization.myBundle.get("questionOneOne"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneTwo"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneThree"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneFour"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneFive"), false));
        questions.add(new Question(game.localization.myBundle.get("questionOneSix"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneSeven"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneEight"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneNine"), false));
        questions.add(new Question(game.localization.myBundle.get("questionOneTen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneEleven"), false));
//        questions.add(new Question("One of the phases of a dream is called REM", true));
//        questions.add(new Question("Your pulse becomes faster during REM", true));
//        questions.add(new Question("Things are stored in memory while sleeping", true));
//        questions.add(new Question("Brain cell structure changes during sleep", true));
//        questions.add(new Question("Sleeping too little has no negative effects", false));
//        questions.add(new Question("A short nap can help remembering things", true));
//        questions.add(new Question("A teen needs more sleep than an adult", true));
//        questions.add(new Question("Caffeine can make falling asleep harder", true));
//        questions.add(new Question("Sugar has no effect on sleeping", false));
//        questions.add(new Question("Maintaining a sleep pattern is important", true));
//        questions.add(new Question("Sleeping for 4 hours is enough for a teen", false));
        //"baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab" <-- Maximum length for a question
    }

    public Question anyItem() {
        Random random = new Random();
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }
}
