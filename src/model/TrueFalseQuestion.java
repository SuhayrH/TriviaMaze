package model;

/**
 * Represents a true or false trivia question.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class TrueFalseQuestion extends Question {

    /** Serialization version number. */
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new true or false question.
     *
     * @param theQuestionText the text shown to the player
     * @param theCorrectAnswer the correct answer
     */
    public TrueFalseQuestion(final String theQuestionText,
                             final String theCorrectAnswer) {
        super(theQuestionText, theCorrectAnswer, "TrueFalse");
    }
}