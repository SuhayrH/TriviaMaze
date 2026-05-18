package model;
/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates trivia questions from the SQLite database.
 *
 * @author Roman Pavlyshyn
 * @version 10 May 2026
 */
public class QuestionFactory {
    /**
     * The SQLite database URL prefix.
     */
    private static final String DATABASE_URL_PREFIX = "jdbc:sqlite:";

    /**
     * SQL query for selecting a random question.
     */
    private static final String RANDOM_QUESTION_QUERY =
            "SELECT question_text, answer, question_type, "
            + "choice_a, choice_b, choice_c, choice_d "
            + "FROM questions ORDER BY RANDOM() LIMIT 1";

    /**
     * SQL query for selecting a random question by type.
     */
    private static final String QUESTION_BY_TYPE_QUERY =
            "SELECT question_text, answer, question_type, "
            + "choice_a, choice_b, choice_c, choice_d "
            + "FROM questions WHERE question_type = ? "
            + "ORDER BY RANDOM() LIMIT 1";

    /**
     * The multiple choice database type.
     */
    private static final String MULTIPLE_CHOICE_TYPE = "MULTIPLE_CHOICE";

    /**
     * The true or false database type.
     */
    private static final String TRUE_FALSE_TYPE = "TRUE_FALSE";

    /**
     * The short answer database type.
     */
    private static final String SHORT_ANSWER_TYPE = "SHORT_ANSWER";

    /**
     * The database path.
     */
    private final String myDbPath;

    /**
     * Creates a new question factory.
     *
     * @param theDbPath the path to the SQLite database
     */
    public QuestionFactory(final String theDbPath) {
        myDbPath = theDbPath;
    }

    /**
     * Gets a random question from the database.
     *
     * @return a random question
     */
    public Question getRandomQuestion() {
        return getQuestionFromQuery(RANDOM_QUESTION_QUERY);
    }

    /**
     * Gets a random question of a specific type from the database.
     *
     * @param theType the question type
     * @return a question of the requested type
     */
    public Question getQuestionByType(final String theType) {
        Question question = createFallbackQuestion();

        if (theType != null) {
            question = getQuestionFromTypeQuery(theType);
        }

        return question;
    }

    /**
     * Gets a question from the database using the given SQL query.
     *
     * @param theQuery the SQL query
     * @return the created question
     */
    private Question getQuestionFromQuery(final String theQuery) {
        Question question = createFallbackQuestion();

        try {
            final Connection connection =
                    DriverManager.getConnection(DATABASE_URL_PREFIX + myDbPath);
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(theQuery);

            if (resultSet.next()) {
                question = createQuestionFromResultSet(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println("Could not load question from database: "
                    + exception.getMessage());
        }

        return question;
    }

    /**
     * Gets a question from the database using a question type.
     *
     * @param theType the question type
     * @return the created question
     */
    private Question getQuestionFromTypeQuery(final String theType) {
        Question question = createFallbackQuestion();

        try {
            final Connection connection =
                    DriverManager.getConnection(DATABASE_URL_PREFIX + myDbPath);
            final PreparedStatement statement =
                    connection.prepareStatement(QUESTION_BY_TYPE_QUERY);

            statement.setString(1, theType);

            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                question = createQuestionFromResultSet(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (final Exception exception) {
            System.out.println("Could not load question from database: "
                    + exception.getMessage());
        }

        return question;
    }

    /**
     * Creates a question object from the current result set row.
     *
     * @param theResultSet the database result set
     * @return the created question
     * @throws Exception if the result set cannot be read
     */
    private Question createQuestionFromResultSet(final ResultSet theResultSet)
            throws Exception {
        final String questionText = theResultSet.getString("question_text");
        final String answer = theResultSet.getString("answer");
        final String questionType = theResultSet.getString("question_type");
        final List<String> choices = createChoices(theResultSet);

        return createQuestion(questionText, answer, questionType, choices);
    }

    /**
     * Creates the correct question object based on the database type.
     *
     * @param theQuestionText the question text
     * @param theAnswer the correct answer
     * @param theQuestionType the question type
     * @param theChoices the multiple choice options
     * @return the created question
     */
    private Question createQuestion(final String theQuestionText,
                                    final String theAnswer,
                                    final String theQuestionType,
                                    final List<String> theChoices) {
        Question question = new ShortAnswerQuestion(theQuestionText, theAnswer);

        if (MULTIPLE_CHOICE_TYPE.equalsIgnoreCase(theQuestionType)) {
            question = new MultipleChoiceQuestion(
                    theQuestionText,
                    theAnswer,
                    theChoices);
        } else if (TRUE_FALSE_TYPE.equalsIgnoreCase(theQuestionType)) {
            question = new TrueFalseQuestion(theQuestionText, theAnswer);
        } else if (SHORT_ANSWER_TYPE.equalsIgnoreCase(theQuestionType)) {
            question = new ShortAnswerQuestion(theQuestionText, theAnswer);
        }

        return question;
    }

    /**
     * Creates the answer choice list from the database choice columns.
     *
     * @param theResultSet the database result set
     * @return the answer choices
     * @throws Exception if the result set cannot be read
     */
    private List<String> createChoices(final ResultSet theResultSet)
            throws Exception {
        final List<String> choices = new ArrayList<String>();

        addChoice(choices, theResultSet.getString("choice_a"));
        addChoice(choices, theResultSet.getString("choice_b"));
        addChoice(choices, theResultSet.getString("choice_c"));
        addChoice(choices, theResultSet.getString("choice_d"));

        return choices;
    }

    /**
     * Adds a choice to the list if the choice exists.
     *
     * @param theChoices the choice list
     * @param theChoice the choice to add
     */
    private void addChoice(final List<String> theChoices,
                           final String theChoice) {
        if (theChoice != null && !theChoice.isEmpty()) {
            theChoices.add(theChoice);
        }
    }

    /**
     * Creates a fallback question if the database cannot be read.
     *
     * @return a fallback question
     */
    private Question createFallbackQuestion() {
        return new TrueFalseQuestion(
                "Java is an object-oriented programming language.",
                "True");
    }
}