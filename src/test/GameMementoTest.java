/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import model.GameMemento;
import model.Maze;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the GameMemento class.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class GameMementoTest {

    /**
     * The text save file name.
     */
    private static final String TEXT_SAVE_FILE = "savegame.txt";

    /**
     * The binary save file name.
     */
    private static final String MAZE_SAVE_FILE = "savegame.dat";

    /**
     * Cleans up save files after each test.
     */
    @AfterEach
    public void tearDown() {
        new File(TEXT_SAVE_FILE).delete();
        new File(MAZE_SAVE_FILE).delete();
    }

    /**
     * Tests that save creates the text save file.
     */
    @Test
    public void testSaveCreatesFile() {
        GameMemento.save("test state");
        assertTrue(new File(TEXT_SAVE_FILE).exists(), "Save file should exist after save");
    }

    /**
     * Tests that load returns the saved state.
     */
    @Test
    public void testSaveAndLoad() {
        GameMemento.save("test state");
        final String loaded = GameMemento.load();
        assertEquals("test state", loaded, "Loaded state should match saved state");
    }

    /**
     * Tests that load returns empty string when no save file exists.
     */
    @Test
    public void testLoadReturnsEmptyWhenNoFile() {
        final String loaded = GameMemento.load();
        assertEquals("", loaded, "Load should return empty string when no file exists");
    }

    /**
     * Tests that save overwrites previous save.
     */
    @Test
    public void testSaveOverwritesPrevious() {
        GameMemento.save("first state");
        GameMemento.save("second state");
        final String loaded = GameMemento.load();
        assertEquals("second state", loaded, "Load should return most recent save");
    }

    /**
     * Tests that saveMaze returns true on success.
     */
    @Test
    public void testSaveMazeReturnsTrue() {
        final Maze maze = new Maze(4);
        assertTrue(GameMemento.saveMaze(maze), "saveMaze should return true on success");
    }

    /**
     * Tests that saveMaze creates the binary save file.
     */
    @Test
    public void testSaveMazeCreatesFile() {
        final Maze maze = new Maze(4);
        GameMemento.saveMaze(maze);
        assertTrue(new File(MAZE_SAVE_FILE).exists(),
                "Binary save file should exist after saveMaze");
    }

    /**
     * Tests that saveMaze returns false when maze is null.
     */
    @Test
    public void testSaveMazeReturnsFalseForNull() {
        assertFalse(GameMemento.saveMaze(null),
                "saveMaze should return false when maze is null");
    }

    /**
     * Tests that loadMaze returns null when no save file exists.
     */
    @Test
    public void testLoadMazeReturnsNullWhenNoFile() {
        assertNull(GameMemento.loadMaze(), "loadMaze should return null when no file exists");
    }

    /**
     * Tests that loadMaze returns a non-null maze after saving.
     */
    @Test
    public void testLoadMazeReturnsNonNull() {
        final Maze maze = new Maze(4);
        GameMemento.saveMaze(maze);
        assertNotNull(GameMemento.loadMaze(), "loadMaze should return a maze after saving");
    }

    /**
     * Tests that the loaded maze has the correct size.
     */
    @Test
    public void testLoadMazeHasCorrectSize() {
        final Maze maze = new Maze(4);
        GameMemento.saveMaze(maze);
        final Maze loaded = GameMemento.loadMaze();
        assertEquals(4, loaded.getSize(), "Loaded maze should have size 4");
    }

    /**
     * Tests that the loaded maze preserves the player position.
     */
    @Test
    public void testLoadMazePreservesPlayerPosition() {
        final Maze maze = new Maze(4);
        maze.setCurrentPosition(2, 3);
        GameMemento.saveMaze(maze);
        final Maze loaded = GameMemento.loadMaze();
        assertEquals(2, loaded.getCurrentRow(), "Loaded maze should preserve row");
        assertEquals(3, loaded.getCurrentCol(), "Loaded maze should preserve col");
    }

    /**
     * Tests that the loaded maze preserves visited room state.
     */
    @Test
    public void testLoadMazePreservesVisitedState() {
        final Maze maze = new Maze(4);
        maze.setCurrentPosition(1, 1);
        GameMemento.saveMaze(maze);
        final Maze loaded = GameMemento.loadMaze();
        assertTrue(loaded.getRoom(1, 1).isVisited(),
                "Loaded maze should preserve visited state");
    }
}