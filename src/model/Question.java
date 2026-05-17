package model;

/**
 * Represents a trivia question in the Trivia model.Maze game.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public abstract class Question {
    /**
     * The text shown to the player.
     */
    private final String myQuestionText;

    /**
     * The correct answer for this question.
     */
    private final String myCorrectAnswer;

    /**
     * The category for this question.
     */
    private final String myCategory;

    /**
     * Creates a new question.
     *
     * @param theQuestionText the text shown to the player
     * @param theCorrectAnswer the correct answer
     * @param theCategory the question category
     */
    public Question(final String theQuestionText,
                    final String theCorrectAnswer,
                    final String theCategory) {
        myQuestionText = theQuestionText;
        myCorrectAnswer = theCorrectAnswer;
        myCategory = theCategory;
    }

    /**
     * Returns the question text.
     *
     * @return the question text
     */
    public String getQuestionText() {
        return myQuestionText;
    }

    /**
     * Returns the correct answer.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }

    /**
     * Checks whether the given answer is correct.
     *
     * @param theAnswer the player's answer
     * @return true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(final String theAnswer) {
        boolean isCorrect = false;

        if (theAnswer != null) {
            final String userAnswer = theAnswer.trim();
            final String correctAnswer = myCorrectAnswer.trim();

            isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
        }

        return isCorrect;
    }

    /**
     * Returns the question category.
     *
     * @return the question category
     */
    protected String getCategory() {
        return myCategory;
    }
}