
/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates trivia questions from the SQLite database.
 *
 * @author Roman Pavlyshyn
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class QuestionFactory {

    /**
     * SQL query for selecting a random question.
     */
    private static final String RANDOM_QUESTION_QUERY =
            "SELECT question_text, answer, question_type, choice_a, choice_b, "
            + "choice_c, choice_d FROM questions ORDER BY RANDOM() LIMIT 1";

    /**
     * The multiple choice database question type.
     */
    private static final String MULTIPLE_CHOICE_TYPE = "MULTIPLE_CHOICE";

    /**
     * The true or false database question type.
     */
    private static final String TRUE_FALSE_TYPE = "TRUE_FALSE";

    /**
     * The short answer database question type.
     */
    private static final String SHORT_ANSWER_TYPE = "SHORT_ANSWER";

    /**
     * Creates a new question factory.
     */
    public QuestionFactory() {
        Database.init();
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
     * @param theQuestionType the question type
     * @return a question of the requested type
     */
    public Question getQuestionByType(final String theQuestionType) {
        Question question = createFallbackQuestion();

        if (theQuestionType != null && !theQuestionType.isBlank()) {
            final String query = "SELECT question_text, answer, question_type, "
                    + "choice_a, choice_b, choice_c, choice_d FROM questions "
                    + "WHERE question_type = '" + theQuestionType
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

        try (Connection connection = Database.connect()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(theQuery)) {

                    if (resultSet.next()) {
                        final String questionText =
                                resultSet.getString("question_text");
                        final String answer = resultSet.getString("answer");
                        final String questionType =
                                resultSet.getString("question_type");
                        final String choiceA = resultSet.getString("choice_a");
                        final String choiceB = resultSet.getString("choice_b");
                        final String choiceC = resultSet.getString("choice_c");
                        final String choiceD = resultSet.getString("choice_d");

                        question = createQuestion(
                                questionText,
                                answer,
                                questionType,
                                choiceA,
                                choiceB,
                                choiceC,
                                choiceD);
                    }
                }
            }
        } catch (final SQLException exception) {
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
     * @param theQuestionType the question type
     * @param theChoiceA the first multiple choice option
     * @param theChoiceB the second multiple choice option
     * @param theChoiceC the third multiple choice option
     * @param theChoiceD the fourth multiple choice option
     * @return the created question
     */
    private Question createQuestion(final String theQuestionText,
                                    final String theAnswer,
                                    final String theQuestionType,
                                    final String theChoiceA,
                                    final String theChoiceB,
                                    final String theChoiceC,
                                    final String theChoiceD) {
        Question question = new ShortAnswerQuestion(theQuestionText, theAnswer);

        if (MULTIPLE_CHOICE_TYPE.equalsIgnoreCase(theQuestionType)) {
            final List<String> choices = createChoices(
                    theChoiceA,
                    theChoiceB,
                    theChoiceC,
                    theChoiceD);
            question = new MultipleChoiceQuestion(theQuestionText, theAnswer, choices);
        } else if (TRUE_FALSE_TYPE.equalsIgnoreCase(theQuestionType)) {
            question = new TrueFalseQuestion(theQuestionText, theAnswer);
        } else if (SHORT_ANSWER_TYPE.equalsIgnoreCase(theQuestionType)) {
            question = new ShortAnswerQuestion(theQuestionText, theAnswer);
        }

        return question;
    }

    /**
     * Creates the answer choice list from database choice columns.
     *
     * @param theChoiceA the first answer choice
     * @param theChoiceB the second answer choice
     * @param theChoiceC the third answer choice
     * @param theChoiceD the fourth answer choice
     * @return the answer choices
     */
    private List<String> createChoices(final String theChoiceA,
                                       final String theChoiceB,
                                       final String theChoiceC,
                                       final String theChoiceD) {
        final List<String> choices = new ArrayList<String>();

        addChoice(choices, theChoiceA);
        addChoice(choices, theChoiceB);
        addChoice(choices, theChoiceC);
        addChoice(choices, theChoiceD);

        return choices;
    }

    /**
     * Adds a choice to the list when the choice exists.
     *
     * @param theChoices the answer choice list
     * @param theChoice the answer choice to add
     */
    private void addChoice(final List<String> theChoices,
                           final String theChoice) {
        if (theChoice != null && !theChoice.isBlank()) {
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

// /*
//  * TCSS 360 - Trivia Maze
//  * Iteration 1
//  */
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// /**
//  * Creates trivia questions from the SQLite database.
//  *
//  * @author Roman Pavlyshyn
//  * @version 2 May 2026
//  */
// public class QuestionFactory {
//     /**
//      * SQL query for selecting a random question.
//      */
//     private static final String RANDOM_QUESTION_QUERY =
//             "SELECT question_text, answer, type, options "
//             + "FROM questions ORDER BY RANDOM() LIMIT 1";

//     /**
//      * The database path.
//      */
//     private final String myDbPath;

//     /**
//      * Creates a new question factory.
//      *
//      * @param theDbPath the path to the SQLite database
//      */
//     public QuestionFactory(final String theDbPath) {
//         myDbPath = theDbPath;
//     }

//     /**
//      * Gets a random question from the database.
//      *
//      * @return a random question
//      */
//     public Question getRandomQuestion() {
//         return getQuestionFromQuery(RANDOM_QUESTION_QUERY);
//     }

//     /**
//      * Gets a random question of a specific type from the database.
//      *
//      * @param theType the question type
//      * @return a question of the requested type
//      */
//     public Question getQuestionByType(final String theType) {
//         Question question = createFallbackQuestion();
        
//         // Can be iterated upon once database is set up
//         if (theType != null) {
//             final String query = "SELECT question_text, answer, type, options "
//                     + "FROM questions WHERE type = '" + theType
//                     + "' ORDER BY RANDOM() LIMIT 1";

//             question = getQuestionFromQuery(query);
//         }

//         return question;
//     }

//     /**
//      * Gets a question from the database using the given SQL query.
//      *
//      * @param theQuery the SQL query
//      * @return the created question
//      */
//     private Question getQuestionFromQuery(final String theQuery) {
//         Question question = createFallbackQuestion();

//         try {
//             final Connection connection =
//                     DriverManager.getConnection("jdbc:sqlite:" + myDbPath);
//             final Statement statement = connection.createStatement();
//             final ResultSet resultSet = statement.executeQuery(theQuery);

//             if (resultSet.next()) {
//                 final String questionText = resultSet.getString("question_text");
//                 final String answer = resultSet.getString("answer");
//                 final String type = resultSet.getString("type");
//                 final String options = resultSet.getString("options");

//                 question = createQuestion(questionText, answer, type, options);
//             }

//             resultSet.close();
//             statement.close();
//             connection.close();
//         } catch (final Exception exception) {
//             System.out.println("Could not load question from database: "
//                     + exception.getMessage());
//         }

//         return question;
//     }

//     /**
//      * Creates the correct question object based on the database type.
//      *
//      * @param theQuestionText the question text
//      * @param theAnswer the correct answer
//      * @param theType the question type
//      * @param theOptions the multiple choice options
//      * @return the created question
//      */
//     private Question createQuestion(final String theQuestionText,
//                                     final String theAnswer,
//                                     final String theType,
//                                     final String theOptions) {
//         Question question = new ShortAnswerQuestion(theQuestionText, theAnswer);

//         if ("MCQ".equalsIgnoreCase(theType)) {
//             final List<String> choices = createChoices(theOptions);
//             question = new MultipleChoiceQuestion(theQuestionText, theAnswer, choices);
//         } else if ("TrueFalse".equalsIgnoreCase(theType)) {
//             question = new TrueFalseQuestion(theQuestionText, theAnswer);
//         } else if ("ShortAnswer".equalsIgnoreCase(theType)) {
//             question = new ShortAnswerQuestion(theQuestionText, theAnswer);
//         }

//         return question;
//     }

//     /**
//      * Creates the answer choice list from the database options string.
//      *
//      * @param theOptions the pipe-delimited answer choices
//      * @return the answer choices
//      */
//     private List<String> createChoices(final String theOptions) {
//         List<String> choices = new ArrayList<String>();

//         if (theOptions != null && !theOptions.isEmpty()) {
//             choices = Arrays.asList(theOptions.split("\\|"));
//         }

//         return choices;
//     }

//     /**
//      * Creates a fallback question if the database cannot be read.
//      *
//      * @return a fallback question
//      */
//     private Question createFallbackQuestion() {
//         return new TrueFalseQuestion(
//                 "Java is an object-oriented programming language.",
//                 "True");
//     }
// }
