/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Runs manual checks for sample trivia question data.
 *
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class TestQuestionDataRunner {

    /**
     * SQL query used to count questions by type.
     */
    private static final String COUNT_BY_TYPE_QUERY =
            "SELECT question_type, COUNT(*) AS total "
            + "FROM questions GROUP BY question_type;";

    /**
     * Private constructor to prevent instantiation.
     */
    private TestQuestionDataRunner() {
        // Utility class should not be instantiated.
    }

    /**
     * Runs the manual database verification.
     *
     * @param theArgs command line arguments
     */
    public static void main(final String[] theArgs) {
        Database.init();
        printQuestionCounts();
    }

    /**
     * Prints the number of questions for each question type.
     */
    private static void printQuestionCounts() {
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(COUNT_BY_TYPE_QUERY)) {

                    while (resultSet.next()) {
                        final String questionType =
                                resultSet.getString("question_type");
                        final int totalQuestions = resultSet.getInt("total");

                        System.out.println(questionType + ": " + totalQuestions);
                    }
                }
            }
        } catch (final SQLException exception) {
            System.out.println("Error checking question data: "
                    + exception.getMessage());
        }
    }
}