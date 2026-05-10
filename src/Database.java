/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the SQLite database connection and sample trivia question data.
 *
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class Database {

    /**
     * The SQLite database connection URL.
     */
    private static final String DATABASE_URL = "jdbc:sqlite:trivia.db";

    /**
     * SQL statement used to create the questions table.
     */
    private static final String CREATE_QUESTIONS_TABLE = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question_text TEXT NOT NULL,
                answer TEXT NOT NULL,
                question_type TEXT NOT NULL,
                choice_a TEXT,
                choice_b TEXT,
                choice_c TEXT,
                choice_d TEXT
            );
            """;

    /**
     * SQL statement used to count existing questions.
     */
    private static final String COUNT_QUESTIONS =
            "SELECT COUNT(*) AS total FROM questions;";

    /**
     * SQL statement used to insert sample trivia questions.
     */
    private static final String INSERT_SAMPLE_QUESTIONS = """
            INSERT INTO questions
            (question_text, answer, question_type, choice_a, choice_b, choice_c, choice_d)
            VALUES
            ('What is the capital of Washington?', 'Olympia', 'SHORT_ANSWER',
                NULL, NULL, NULL, NULL),
            ('What language is used for this project?', 'Java', 'SHORT_ANSWER',
                NULL, NULL, NULL, NULL),
            ('What database system is used in this project?', 'SQLite', 'SHORT_ANSWER',
                NULL, NULL, NULL, NULL),
            ('What pattern separates model, view, and controller?', 'MVC', 'SHORT_ANSWER',
                NULL, NULL, NULL, NULL),
            ('What file extension is used for Java source files?', '.java', 'SHORT_ANSWER',
                NULL, NULL, NULL, NULL),

            ('Java is an object-oriented programming language.', 'True', 'TRUE_FALSE',
                NULL, NULL, NULL, NULL),
            ('SQLite requires a separate database server to run.', 'False', 'TRUE_FALSE',
                NULL, NULL, NULL, NULL),
            ('A locked door in Trivia Maze should stay locked permanently.', 'True',
                'TRUE_FALSE', NULL, NULL, NULL, NULL),
            ('The player wins Trivia Maze by reaching the exit room.', 'True',
                'TRUE_FALSE', NULL, NULL, NULL, NULL),
            ('A Java class can only have one method.', 'False', 'TRUE_FALSE',
                NULL, NULL, NULL, NULL),

            ('Which database are we using for this project?', 'SQLite', 'MULTIPLE_CHOICE',
                'MySQL', 'SQLite', 'MongoDB', 'Oracle'),
            ('Which design pattern is required for this project?', 'MVC', 'MULTIPLE_CHOICE',
                'Singleton only', 'MVC', 'Observer only', 'Adapter only'),
            ('Which keyword creates a class in Java?', 'class', 'MULTIPLE_CHOICE',
                'method', 'class', 'object', 'package'),
            ('Which file stores saved game data in this project?', 'savegame.txt',
                'MULTIPLE_CHOICE', 'README.md', 'savegame.txt', 'Question.java',
                'Door.java'),
            ('Which object blocks or allows movement between rooms?', 'Door',
                'MULTIPLE_CHOICE', 'Player', 'Door', 'Database', 'README');
            """;

    /**
     * Private constructor to prevent objects of this utility class.
     */
    private Database() {
        // Utility class should not be instantiated.
    }

    /**
     * Creates and returns a connection to the SQLite database.
     *
     * @return a database connection, or null if the connection fails
     */
    public static Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (final SQLException exception) {
            System.out.println("Database connection error: "
                    + exception.getMessage());
        }

        return connection;
    }

    /**
     * Initializes the database by creating the questions table and inserting
     * sample questions if the table is empty.
     */
    public static void init() {
        createQuestionsTable();
        insertSampleQuestions();

        System.out.println("Database setup finished.");
    }

    /**
     * Creates the questions table if the database connection works.
     */
    private static void createQuestionsTable() {
        try (Connection connection = connect()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(CREATE_QUESTIONS_TABLE);
                    System.out.println("Questions table checked or created.");
                }
            }
        } catch (final SQLException exception) {
            System.out.println("Error creating questions table: "
                    + exception.getMessage());
        }
    }

    /**
     * Inserts sample questions into the database if the table is empty.
     */
    private static void insertSampleQuestions() {
        if (!hasQuestions()) {
            try (Connection connection = connect()) {
                if (connection != null) {
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate(INSERT_SAMPLE_QUESTIONS);
                        System.out.println("Sample questions inserted.");
                    }
                }
            } catch (final SQLException exception) {
                System.out.println("Error inserting sample questions: "
                        + exception.getMessage());
            }
        }
    }

    /**
     * Checks whether the questions table already has questions.
     *
     * @return true if the questions table has at least one question
     */
    private static boolean hasQuestions() {
        boolean hasQuestions = false;

        try (Connection connection = connect()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet =
                             statement.executeQuery(COUNT_QUESTIONS)) {

                    if (resultSet.next()) {
                        hasQuestions = resultSet.getInt("total") > 0;
                    }
                }
            }
        } catch (final SQLException exception) {
            System.out.println("Error checking questions table: "
                    + exception.getMessage());
        }

        return hasQuestions;
    }
}



// /*
//  * TCSS 360 - Trivia Maze
//  * Iteration 1
//  */

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;

// /**
//  * The Database class manages the SQLite connection and creates the trivia
//  * questions table for the Trivia Maze project.
//  *
//  * @author Jinal
//  * @version 3 May 2026
//  */
// public final class Database {

//     /**
//      * The SQLite database connection URL.
//      */
//     private static final String DATABASE_URL = "jdbc:sqlite:trivia.db";

//     /**
//      * SQL statement used to create the questions table.
//      */
//     private static final String CREATE_QUESTIONS_TABLE = """
//             CREATE TABLE IF NOT EXISTS questions (
//                 id INTEGER PRIMARY KEY AUTOINCREMENT,
//                 question_text TEXT NOT NULL,
//                 answer TEXT NOT NULL,
//                 question_type TEXT NOT NULL,
//                 choice_a TEXT,
//                 choice_b TEXT,
//                 choice_c TEXT,
//                 choice_d TEXT
//             );
//             """;

//     /**
//      * SQL statement used to count existing questions.
//      */
//     private static final String COUNT_QUESTIONS = "SELECT COUNT(*) AS total FROM questions;";

//     /**
//      * SQL statement used to insert sample trivia questions.
//      */
//     private static final String INSERT_SAMPLE_QUESTIONS = """
//             INSERT INTO questions
//             (question_text, answer, question_type, choice_a, choice_b, choice_c, choice_d)
//             VALUES
//             ('What is the capital of Washington?', 'Olympia', 'SHORT_ANSWER',
//                 NULL, NULL, NULL, NULL),
//             ('Java is an object-oriented programming language.', 'True', 'TRUE_FALSE',
//                 NULL, NULL, NULL, NULL),
//             ('Which database are we using for this project?', 'SQLite', 'MULTIPLE_CHOICE',
//                 'MySQL', 'SQLite', 'MongoDB', 'Oracle');
//             """;

//     /**
//      * Private constructor to prevent objects of this utility class.
//      */
//     private Database() {
//         // Utility class should not be instantiated.
//     }

//     /**
//      * Creates and returns a connection to the SQLite database.
//      *
//      * @return a database connection, or null if the connection fails
//      */
//     public static Connection connect() {
//         Connection connection = null;

//         try {
//             connection = DriverManager.getConnection(DATABASE_URL);
//         } catch (final SQLException exception) {
//             System.out.println("Database connection error: " + exception.getMessage());
//         }

//         return connection;
//     }

//     /**
//      * Initializes the database by creating the questions table and inserting
//      * sample questions if possible.
//      */
//     public static void init() {
//         createQuestionsTable();
//         insertSampleQuestions();

//         System.out.println("Database setup finished.");
//     }

//     /**
//      * Creates the questions table if the database connection works.
//      */
//     private static void createQuestionsTable() {
//         try (Connection connection = connect()) {
//             if (connection != null) {
//                 try (Statement statement = connection.createStatement()) {
//                     statement.execute(CREATE_QUESTIONS_TABLE);
//                     System.out.println("Questions table checked or created.");
//                 }
//             }
//         } catch (final SQLException exception) {
//             System.out.println("Error creating questions table: " + exception.getMessage());
//         }
//     }

//     /**
//      * Inserts sample questions into the database if the table is empty.
//      */
//     private static void insertSampleQuestions() {
//         if (!hasQuestions()) {
//             try (Connection connection = connect()) {
//                 if (connection != null) {
//                     try (Statement statement = connection.createStatement()) {
//                         statement.executeUpdate(INSERT_SAMPLE_QUESTIONS);
//                         System.out.println("Sample questions inserted.");
//                     }
//                 }
//             } catch (final SQLException exception) {
//                 System.out.println("Error inserting sample questions: "
//                                    + exception.getMessage());
//             }
//         }
//     }

//     /**
//      * Checks whether the questions table already has questions.
//      *
//      * @return true if the questions table has at least one question
//      */
//     private static boolean hasQuestions() {
//         boolean hasQuestions = false;

//         try (Connection connection = connect()) {
//             if (connection != null) {
//                 try (Statement statement = connection.createStatement();
//                      ResultSet resultSet = statement.executeQuery(COUNT_QUESTIONS)) {

//                     if (resultSet.next()) {
//                         hasQuestions = resultSet.getInt("total") > 0;
//                     }
//                 }
//             }
//         } catch (final SQLException exception) {
//             System.out.println("Error checking questions table: " + exception.getMessage());
//         }

//         return hasQuestions;
//     }
// }