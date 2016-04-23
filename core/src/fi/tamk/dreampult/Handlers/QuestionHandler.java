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

        initializeQuestions();

        //"baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab" <-- Maximum length for a question
    }

    public void initializeQuestions() {
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
        questions.add(new Question(game.localization.myBundle.get("questionOneTwelve"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneThirteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneFourteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneFifteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneSixteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneSeventeen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneEighteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionOneNineteen"), false));
        questions.add(new Question(game.localization.myBundle.get("questionOneTwenty"), false));

        questions.add(new Question(game.localization.myBundle.get("questionTwoOne"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoTwo"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoThree"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoFour"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoFive"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoSix"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoSeven"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoEight"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoNine"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoTen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoEleven"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoTwelve"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoThirteen"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoFourteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoFifteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoSixteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoSeventeen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoEighteen"), false));
        questions.add(new Question(game.localization.myBundle.get("questionTwoNineteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionTwoTwenty"), true));

        questions.add(new Question(game.localization.myBundle.get("questionThreeOne"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeTwo"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeThree"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeFour"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeFive"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeSix"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeSeven"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeEight"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeNine"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeTen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeEleven"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeTwelve"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeThirteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeFourteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeFifteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeSixteen"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeSeventeen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeEighteen"), true));
        questions.add(new Question(game.localization.myBundle.get("questionThreeNineteen"), false));
        questions.add(new Question(game.localization.myBundle.get("questionThreeTwenty"), false));
    }

    public void clearQuestions() {
        questions.clear();
    }

    public Question anyItem() {
        Random random = new Random();
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }
}
