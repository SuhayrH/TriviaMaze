import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:trivia.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            return null;
        }
    }

    public static void init() {
        System.out.println("SQLite connected");
    }
}