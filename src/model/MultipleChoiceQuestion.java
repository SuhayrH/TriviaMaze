package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multiple choice trivia question.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class MultipleChoiceQuestion extends Question {
    /** Serialization version number. */
    private static final long serialVersionUID = 1L;
    /**
     * The answer choices for this question.
     */
    private final List<String> myChoices;

    /**
     * Creates a new multiple choice question.
     *
     * @param theQuestionText the text shown to the player
     * @param theCorrectAnswer the correct answer
     * @param theChoices the answer choices
     */
    public MultipleChoiceQuestion(final String theQuestionText,
                                  final String theCorrectAnswer,
                                  final List<String> theChoices) {
        super(theQuestionText, theCorrectAnswer, "MCQ");
        myChoices = new ArrayList<String>(theChoices);
    }

    /**
     * Returns a copy of the answer choices.
     *
     * @return the answer choices
     */
    public List<String> getChoices() {
        return new ArrayList<String>(myChoices);
    }
}