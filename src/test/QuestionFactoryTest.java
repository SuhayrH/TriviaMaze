/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.MultipleChoiceQuestion;
import model.Question;
import model.QuestionFactory;
import model.ShortAnswerQuestion;
import model.TrueFalseQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the QuestionFactory class.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class QuestionFactoryTest {

    /**
     * The question factory used in each test.
     */
    private QuestionFactory myFactory;

    /**
     * Sets up a fresh QuestionFactory before each test.
     */
    @BeforeEach
    public void setUp() {
        myFactory = new QuestionFactory("trivia.db");
    }

    /**
     * Tests that getRandomQuestion returns a non-null question.
     */
    @Test
    public void testGetRandomQuestionNotNull() {
        final Question question = myFactory.getRandomQuestion();
        assertNotNull(question, "Random question should not be null");
    }

    /**
     * Tests that getRandomQuestion returns a question with non-null text.
     */
    @Test
    public void testGetRandomQuestionHasText() {
        final Question question = myFactory.getRandomQuestion();
        assertNotNull(question.getQuestionText(),
                "Question text should not be null");
    }

    /**
     * Tests that getRandomQuestion returns a question with non-null answer.
     */
    @Test
    public void testGetRandomQuestionHasAnswer() {
        final Question question = myFactory.getRandomQuestion();
        assertNotNull(question.getCorrectAnswer(),
                "Correct answer should not be null");
    }

    /**
     * Tests that getRandomQuestion returns a Question subclass instance.
     */
    @Test
    public void testGetRandomQuestionIsQuestionSubclass() {
        final Question question = myFactory.getRandomQuestion();
        assertTrue(
                question instanceof TrueFalseQuestion
                        || question instanceof MultipleChoiceQuestion
                        || question instanceof ShortAnswerQuestion,
                "Question should be a known subclass");
    }

    /**
     * Tests that getQuestionByType returns a non-null question for MULTIPLE_CHOICE.
     */
    @Test
    public void testGetMultipleChoiceQuestionNotNull() {
        final Question question = myFactory.getQuestionByType("MULTIPLE_CHOICE");
        assertNotNull(question, "Multiple choice question should not be null");
    }

    /**
     * Tests that getQuestionByType returns a MultipleChoiceQuestion for MULTIPLE_CHOICE.
     */
    @Test
    public void testGetMultipleChoiceQuestionIsCorrectType() {
        final Question question = myFactory.getQuestionByType("MULTIPLE_CHOICE");
        assertTrue(question instanceof MultipleChoiceQuestion,
                "Question should be a MultipleChoiceQuestion");
    }

    /**
     * Tests that getQuestionByType returns a non-null question for TRUE_FALSE.
     */
    @Test
    public void testGetTrueFalseQuestionNotNull() {
        final Question question = myFactory.getQuestionByType("TRUE_FALSE");
        assertNotNull(question, "True/false question should not be null");
    }

    /**
     * Tests that getQuestionByType returns a TrueFalseQuestion for TRUE_FALSE.
     */
    @Test
    public void testGetTrueFalseQuestionIsCorrectType() {
        final Question question = myFactory.getQuestionByType("TRUE_FALSE");
        assertTrue(question instanceof TrueFalseQuestion,
                "Question should be a TrueFalseQuestion");
    }

    /**
     * Tests that getQuestionByType returns a non-null question for SHORT_ANSWER.
     */
    @Test
    public void testGetShortAnswerQuestionNotNull() {
        final Question question = myFactory.getQuestionByType("SHORT_ANSWER");
        assertNotNull(question, "Short answer question should not be null");
    }

    /**
     * Tests that getQuestionByType returns a ShortAnswerQuestion for SHORT_ANSWER.
     */
    @Test
    public void testGetShortAnswerQuestionIsCorrectType() {
        final Question question = myFactory.getQuestionByType("SHORT_ANSWER");
        assertTrue(question instanceof ShortAnswerQuestion,
                "Question should be a ShortAnswerQuestion");
    }

    /**
     * Tests that getQuestionByType returns a fallback question for null type.
     */
    @Test
    public void testGetQuestionByTypeNullReturnsFallback() {
        final Question question = myFactory.getQuestionByType(null);
        assertNotNull(question, "Null type should return fallback question");
    }

    /**
     * Tests that getQuestionByType returns a fallback for an unknown type.
     */
    @Test
    public void testGetQuestionByTypeUnknownReturnsFallback() {
        final Question question = myFactory.getQuestionByType("UNKNOWN_TYPE");
        assertNotNull(question, "Unknown type should return fallback question");
    }
}