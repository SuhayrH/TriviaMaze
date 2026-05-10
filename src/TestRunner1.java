/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

/**
 * Runs simple manual tests for Iteration 2 database and maze integration.
 *
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class TestRunner1 {

    /**
     * The test maze size.
     */
    private static final int TEST_MAZE_SIZE = 4;

    /**
     * Private constructor to prevent instantiation.
     */
    private TestRunner1() {
        // Utility class should not be instantiated.
    }

    /**
     * Runs manual integration checks.
     *
     * @param theArgs command line arguments
     */
    public static void main(final String[] theArgs) {
        Database.init();

        final Maze maze = new Maze(TEST_MAZE_SIZE);
        final Room currentRoom = maze.getCurrentRoom();
        final Door eastDoor = currentRoom.getDoor("East");

        if (eastDoor != null) {
            final Question question = eastDoor.getQuestion();

            System.out.println("Current room: row=" + maze.getCurrentRow()
                    + ", column=" + maze.getCurrentColumn());
            System.out.println("East door question: " + question.getQuestionText());
            System.out.println("Correct answer: " + question.getCorrectAnswer());
            System.out.println("Question type: " + question.getCategory());
        } else {
            System.out.println("No east door was found.");
        }
    }
}