/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Runs backend tests for the Trivia Maze project.
 *
 * @author Jinal Thummar
 * @version 17 May 2026
 */
public final class TestRunner {

    /**
     * The default maze size used for testing.
     */
    private static final int TEST_MAZE_SIZE = 4;

    /**
     * The row value saved during the test.
     */
    private static final int SAVED_TEST_ROW = 1;

    /**
     * The column value saved during the test.
     */
    private static final int SAVED_TEST_COL = 2;

    /**
     * The row value used after saving to prove loading restores state.
     */
    private static final int CHANGED_TEST_ROW = 0;

    /**
     * The column value used after saving to prove loading restores state.
     */
    private static final int CHANGED_TEST_COL = 0;

    /**
     * The first saved value index.
     */
    private static final int SAVED_ROW_INDEX = 0;

    /**
     * The second saved value index.
     */
    private static final int SAVED_COL_INDEX = 1;

    /**
     * The expected number of saved values.
     */
    private static final int EXPECTED_SAVE_PARTS = 2;

    /**
     * The separator used in saved game data.
     */
    private static final String SAVE_SEPARATOR = ",";

    /**
     * The save file used by GameMemento.
     */
    private static final String SAVE_FILE_NAME = "savegame.txt";

    /**
     * Private constructor to prevent instantiation.
     */
    private TestRunner() {
    }

    /**
     * Runs the test program.
     *
     * @param theArgs command line arguments
     */
    public static void main(final String[] theArgs) {
        Database.init();
        testGameMementoRestoresMazePosition();
    }

    /**
     * Tests that GameMemento can save and restore a Maze player position.
     */
    private static void testGameMementoRestoresMazePosition() {
        final Maze maze = new Maze(TEST_MAZE_SIZE);

        maze.setCurrentPosition(SAVED_TEST_ROW, SAVED_TEST_COL);
        final String savedState = createSaveState(maze);

        GameMemento.save(savedState);

        maze.setCurrentPosition(CHANGED_TEST_ROW, CHANGED_TEST_COL);

        final String loadedState = GameMemento.load();
        restoreMazePosition(maze, loadedState);

        final boolean positionRestored = maze.getCurrentRow() == SAVED_TEST_ROW
                && maze.getCurrentCol() == SAVED_TEST_COL;
        final boolean saveFileExists = Files.exists(Path.of(SAVE_FILE_NAME));

        System.out.println("Saved state: " + savedState);
        System.out.println("Loaded state: " + loadedState);
        System.out.println("Restored row: " + maze.getCurrentRow());
        System.out.println("Restored column: " + maze.getCurrentCol());
        System.out.println("Save file exists: " + saveFileExists);

        if (positionRestored && saveFileExists) {
            System.out.println("GameMemento real game state test passed.");
        } else {
            System.out.println("GameMemento real game state test failed.");
        }
    }

    /**
     * Creates a save state string from the current maze position.
     *
     * @param theMaze the maze being saved
     * @return the saved game state text
     */
    private static String createSaveState(final Maze theMaze) {
        return theMaze.getCurrentRow()
                + SAVE_SEPARATOR
                + theMaze.getCurrentCol();
    }

    /**
     * Restores the maze position from loaded saved game text.
     *
     * @param theMaze the maze being restored
     * @param theLoadedState the loaded saved game state text
     */
    private static void restoreMazePosition(final Maze theMaze,
                                            final String theLoadedState) {
        final String[] parts = theLoadedState.split(SAVE_SEPARATOR);

        if (parts.length != EXPECTED_SAVE_PARTS) {
            throw new IllegalArgumentException("Saved game format is invalid.");
        }

        final int savedRow = Integer.parseInt(parts[SAVED_ROW_INDEX]);
        final int savedCol = Integer.parseInt(parts[SAVED_COL_INDEX]);

        theMaze.setCurrentPosition(savedRow, savedCol);
    }
}