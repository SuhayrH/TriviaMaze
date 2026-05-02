/**
 * Represents a short answer trivia question.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class ShortAnswerQuestion extends Question {
    /**
     * Creates a new short answer question.
     *
     * @param theQuestionText the text shown to the player
     * @param theCorrectAnswer the correct answer
     */
    public ShortAnswerQuestion(final String theQuestionText,
                               final String theCorrectAnswer) {
        super(theQuestionText, theCorrectAnswer, "ShortAnswer");
    }
}