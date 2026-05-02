import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates trivia questions from the SQLite database.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class QuestionFactory {
    /**
     * SQL query for selecting a random question.
     */
    private static final String RANDOM_QUESTION_QUERY =
            "SELECT question_text, answer, type, options "
            + "FROM questions ORDER BY RANDOM() LIMIT 1";

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
        
        // Can be iterated upon once database is set up
        if (theType != null) {
            final String query = "SELECT question_text, answer, type, options "
                    + "FROM questions WHERE type = '" + theType
                    + "' ORDER BY RANDOM() LIMIT 1";

            question = getQuestionFromQuery(query);
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
                    DriverManager.getConnection("jdbc:sqlite:" + myDbPath);
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(theQuery);

            if (resultSet.next()) {
                final String questionText = resultSet.getString("question_text");
                final String answer = resultSet.getString("answer");
                final String type = resultSet.getString("type");
                final String options = resultSet.getString("options");

                question = createQuestion(questionText, answer, type, options);
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
     * Creates the correct question object based on the database type.
     *
     * @param theQuestionText the question text
     * @param theAnswer the correct answer
     * @param theType the question type
     * @param theOptions the multiple choice options
     * @return the created question
     */
    private Question createQuestion(final String theQuestionText,
                                    final String theAnswer,
                                    final String theType,
                                    final String theOptions) {
        Question question = new ShortAnswerQuestion(theQuestionText, theAnswer);

        if ("MCQ".equalsIgnoreCase(theType)) {
            final List<String> choices = createChoices(theOptions);
            question = new MultipleChoiceQuestion(theQuestionText, theAnswer, choices);
        } else if ("TrueFalse".equalsIgnoreCase(theType)) {
            question = new TrueFalseQuestion(theQuestionText, theAnswer);
        } else if ("ShortAnswer".equalsIgnoreCase(theType)) {
            question = new ShortAnswerQuestion(theQuestionText, theAnswer);
        }

        return question;
    }

    /**
     * Creates the answer choice list from the database options string.
     *
     * @param theOptions the pipe-delimited answer choices
     * @return the answer choices
     */
    private List<String> createChoices(final String theOptions) {
        List<String> choices = new ArrayList<String>();

        if (theOptions != null && !theOptions.isEmpty()) {
            choices = Arrays.asList(theOptions.split("\\|"));
        }

        return choices;
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