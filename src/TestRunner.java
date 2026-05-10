/*
 * TCSS 360 - Trivia Maze
 * Iteration 1
 */

/**
 * The TestRunner class is used temporarily to test Database,
 * GameMemento, and basic project behavior.
 *
 * @author Jinal Thummar
 * @version 3 May 2026
 */
public final class TestRunner {

    /**
     * Private constructor to prevent instantiation.
     */
    private TestRunner() {
    }

    /**
     * Runs temporary tests for the Trivia Maze project.
     *
     * @param theArgs command line arguments
     */
    public static void main(final String[] theArgs) {
        testGameMemento();
        testDatabase();
    }

    /**
     * Tests the GameMemento save and load methods.
     */
    private static void testGameMemento() {
        final String testGameState = "Player location: row=0, col=0; score=0";

        GameMemento.save(testGameState);

        final String loadedGameState = GameMemento.load();

        System.out.println("Saved state: " + testGameState);
        System.out.println("Loaded state: " + loadedGameState);

        if (testGameState.equals(loadedGameState)) {
            System.out.println("GameMemento test passed.");
        } else {
            System.out.println("GameMemento test failed.");
        }
    }

    /**
     * Tests the Database initialization method.
     */
    private static void testDatabase() {
        Database.init();

        System.out.println("Database test finished.");
    }
}