/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.Maze;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Maze class.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class MazeTest {

    /**
     * The maze used in each test.
     */
    private Maze myMaze;

    /**
     * Sets up a fresh Maze before each test.
     */
    @BeforeEach
    public void setUp() {
        myMaze = new Maze(4);
    }

    /**
     * Tests that the maze size is correct.
     */
    @Test
    public void testGetSize() {
        assertEquals(4, myMaze.getSize(), "Maze size should be 4");
    }

    /**
     * Tests that the player starts at row 0.
     */
    @Test
    public void testInitialRowIsZero() {
        assertEquals(0, myMaze.getCurrentRow(), "Initial row should be 0");
    }

    /**
     * Tests that the player starts at column 0.
     */
    @Test
    public void testInitialColIsZero() {
        assertEquals(0, myMaze.getCurrentCol(), "Initial col should be 0");
    }

    /**
     * Tests that the starting room is visited.
     */
    @Test
    public void testStartingRoomIsVisited() {
        assertTrue(myMaze.getCurrentRoom().isVisited(),
                "Starting room should be visited");
    }

    /**
     * Tests that getCurrentRoom returns a non-null room.
     */
    @Test
    public void testGetCurrentRoomNotNull() {
        assertNotNull(myMaze.getCurrentRoom(), "Current room should not be null");
    }

    /**
     * Tests that getRoom returns the correct room.
     */
    @Test
    public void testGetRoom() {
        final Room room = myMaze.getRoom(2, 3);
        assertNotNull(room, "Room at (2,3) should not be null");
        assertEquals(2, room.getRow(), "Room row should be 2");
        assertEquals(3, room.getCol(), "Room col should be 3");
    }

    /**
     * Tests that the game is not won at the start.
     */
    @Test
    public void testIsGameWonFalseAtStart() {
        assertFalse(myMaze.isGameWon(), "Game should not be won at start");
    }

    /**
     * Tests that the game is not over at the start.
     */
    @Test
    public void testIsGameOverFalseAtStart() {
        assertFalse(myMaze.isGameOver(), "Game should not be over at start");
    }

    /**
     * Tests that hasPathToExit returns true at the start.
     */
    @Test
    public void testHasPathToExitTrueAtStart() {
        assertTrue(myMaze.hasPathToExit(), "Path to exit should exist at start");
    }

    /**
     * Tests that move returns false when the door is locked.
     */
    @Test
    public void testMoveFailsWhenDoorLocked() {
        myMaze.getCurrentRoom().lockDoor("south");
        assertFalse(myMaze.move("south"), "Move should fail when door is locked");
    }

    /**
     * Tests that move returns false in a direction with no door.
     */
    @Test
    public void testMoveFailsWithNoDoor() {
        assertFalse(myMaze.move("north"), "Move should fail when no door exists");
    }

    /**
     * Tests that move south succeeds from the starting room.
     */
    @Test
    public void testMoveSouthSucceeds() {
        assertTrue(myMaze.move("south"), "Move south should succeed from start");
    }

    /**
     * Tests that move east succeeds from the starting room.
     */
    @Test
    public void testMoveEastSucceeds() {
        assertTrue(myMaze.move("east"), "Move east should succeed from start");
    }

    /**
     * Tests that moving south updates the current row.
     */
    @Test
    public void testMoveSouthUpdatesRow() {
        myMaze.move("south");
        assertEquals(1, myMaze.getCurrentRow(), "Row should be 1 after moving south");
    }

    /**
     * Tests that moving east updates the current column.
     */
    @Test
    public void testMoveEastUpdatesCol() {
        myMaze.move("east");
        assertEquals(1, myMaze.getCurrentCol(), "Col should be 1 after moving east");
    }

    /**
     * Tests that the new room is marked visited after moving.
     */
    @Test
    public void testMoveMarksNewRoomVisited() {
        myMaze.move("south");
        assertTrue(myMaze.getCurrentRoom().isVisited(),
                "New room should be marked visited after moving");
    }

    /**
     * Tests that setCurrentPosition updates the player position.
     */
    @Test
    public void testSetCurrentPosition() {
        myMaze.setCurrentPosition(2, 3);
        assertEquals(2, myMaze.getCurrentRow(), "Row should be 2");
        assertEquals(3, myMaze.getCurrentCol(), "Col should be 3");
    }

    /**
     * Tests that setCurrentPosition marks the room as visited.
     */
    @Test
    public void testSetCurrentPositionMarksVisited() {
        myMaze.setCurrentPosition(2, 3);
        assertTrue(myMaze.getRoom(2, 3).isVisited(),
                "Room should be visited after setCurrentPosition");
    }

    /**
     * Tests that setCurrentPosition throws for out-of-bounds position.
     */
    @Test
    public void testSetCurrentPositionOutOfBoundsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> myMaze.setCurrentPosition(10, 10),
                "Should throw for out-of-bounds position");
    }

    /**
     * Tests that creating a maze smaller than 4 throws an exception.
     */
    @Test
    public void testMazeTooSmallThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Maze(3),
                "Should throw for maze size less than 4");
    }

    /**
     * Tests that isGameWon returns true when player reaches the exit.
     */
    @Test
    public void testIsGameWonAtExit() {
        myMaze.setCurrentPosition(3, 3);
        assertTrue(myMaze.isGameWon(), "Game should be won at exit (3,3)");
    }

    /**
     * Tests that isGameLost returns false at the start.
     */
    @Test
    public void testIsGameLostFalseAtStart() {
        assertFalse(myMaze.isGameLost(), "Game should not be lost at start");
    }
}