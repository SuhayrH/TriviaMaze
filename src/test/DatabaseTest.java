package test;

import model.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Unit tests for the Database class.
 *
 * @author Suhayr Hassan
 * @version 7 June 2026
 */
public class DatabaseTest {

    @BeforeEach
    void setUp() {
        Database.init();
    }

    @Test
    void testConnectionNotNull() {
        Connection connection = Database.connect();
        assertNotNull(connection, "Database connection should not be null");
    }

    @Test
    void testConnectionIsValid() {
        try (Connection connection = Database.connect()) {
            assertNotNull(connection);
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testQuestionsTableExists() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type='table' AND name='questions';")) {
            assertTrue(rs.next(), "Questions table should exist");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testQuestionsTableHasRows() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM questions;")) {
            assertTrue(rs.next());
            assertTrue(rs.getInt("total") > 0, "Questions table should have at least one row");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testQuestionsTableHasRequiredColumns() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT question_text, answer, question_type FROM questions LIMIT 1;")) {
            assertTrue(rs.next(), "Should be able to query required columns");
            assertNotNull(rs.getString("question_text"));
            assertNotNull(rs.getString("answer"));
            assertNotNull(rs.getString("question_type"));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testShortAnswerQuestionExists() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT COUNT(*) AS total FROM questions WHERE question_type='SHORT_ANSWER';")) {
            assertTrue(rs.next());
            assertTrue(rs.getInt("total") > 0, "Should have at least one SHORT_ANSWER question");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testTrueFalseQuestionExists() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT COUNT(*) AS total FROM questions WHERE question_type='TRUE_FALSE';")) {
            assertTrue(rs.next());
            assertTrue(rs.getInt("total") > 0, "Should have at least one TRUE_FALSE question");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testMultipleChoiceQuestionExists() {
        try (Connection connection = Database.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT COUNT(*) AS total FROM questions WHERE question_type='MULTIPLE_CHOICE';")) {
            assertTrue(rs.next());
            assertTrue(rs.getInt("total") > 0, "Should have at least one MULTIPLE_CHOICE question");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}