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

import model.Door;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Room class.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class RoomTest {

    /**
     * The room used in each test.
     */
    private Room myRoom;

    /**
     * A mock door used in each test.
     */
    private Door myDoor;

    /**
     * Sets up a fresh Room and Door before each test.
     */
    @BeforeEach
    public void setUp() {
        myRoom = new Room(2, 3);
        myDoor = new Door(null);
    }

    /**
     * Tests that getRow returns the correct row index.
     */
    @Test
    public void testGetRow() {
        assertEquals(2, myRoom.getRow(), "Row should be 2");
    }

    /**
     * Tests that getCol returns the correct column index.
     */
    @Test
    public void testGetCol() {
        assertEquals(3, myRoom.getCol(), "Col should be 3");
    }

    /**
     * Tests that a new room is not visited by default.
     */
    @Test
    public void testIsVisitedDefaultFalse() {
        assertFalse(myRoom.isVisited(), "New room should not be visited");
    }

    /**
     * Tests that setVisited marks the room as visited.
     */
    @Test
    public void testSetVisitedTrue() {
        myRoom.setVisited(true);
        assertTrue(myRoom.isVisited(), "Room should be visited after setVisited(true)");
    }

    /**
     * Tests that setVisited can mark the room as unvisited.
     */
    @Test
    public void testSetVisitedFalse() {
        myRoom.setVisited(true);
        myRoom.setVisited(false);
        assertFalse(myRoom.isVisited(), "Room should not be visited after setVisited(false)");
    }

    /**
     * Tests that hasDoor returns false when no door has been added.
     */
    @Test
    public void testHasDoorFalseWhenNoDoor() {
        assertFalse(myRoom.hasDoor("north"), "Room should not have a north door initially");
    }

    /**
     * Tests that hasDoor returns true after a door is added.
     */
    @Test
    public void testHasDoorTrueAfterAdd() {
        myRoom.addDoor("north", myDoor);
        assertTrue(myRoom.hasDoor("north"), "Room should have a north door after addDoor");
    }

    /**
     * Tests that addDoor is case-insensitive.
     */
    @Test
    public void testAddDoorCaseInsensitive() {
        myRoom.addDoor("NORTH", myDoor);
        assertTrue(myRoom.hasDoor("north"), "hasDoor should be case-insensitive");
    }

    /**
     * Tests that getDoor returns the correct door after adding.
     */
    @Test
    public void testGetDoorReturnsCorrectDoor() {
        myRoom.addDoor("south", myDoor);
        assertEquals(myDoor, myRoom.getDoor("south"), "getDoor should return the added door");
    }

    /**
     * Tests that getDoor returns null when no door exists in that direction.
     */
    @Test
    public void testGetDoorReturnsNullWhenMissing() {
        assertNull(myRoom.getDoor("east"), "getDoor should return null for missing direction");
    }

    /**
     * Tests that isDoorLocked returns true when no door exists.
     */
    @Test
    public void testIsDoorLockedTrueWhenNoDoor() {
        assertTrue(myRoom.isDoorLocked("west"), "Missing door should be treated as locked");
    }

    /**
     * Tests that isDoorLocked returns false for a newly added unlocked door.
     */
    @Test
    public void testIsDoorLockedFalseForNewDoor() {
        myRoom.addDoor("east", myDoor);
        assertFalse(myRoom.isDoorLocked("east"), "New door should not be locked");
    }

    /**
     * Tests that lockDoor permanently locks the door.
     */
    @Test
    public void testLockDoor() {
        myRoom.addDoor("north", myDoor);
        myRoom.lockDoor("north");
        assertTrue(myRoom.isDoorLocked("north"), "Door should be locked after lockDoor");
    }

    /**
     * Tests that lockDoor does nothing when no door exists in that direction.
     */
    @Test
    public void testLockDoorNoEffect() {
        myRoom.lockDoor("south");
        assertFalse(myRoom.hasDoor("south"), "No door should exist after lockDoor on missing direction");
    }

    /**
     * Tests that multiple doors can be added in different directions.
     */
    @Test
    public void testMultipleDoorsAdded() {
        final Door northDoor = new Door(null);
        final Door southDoor = new Door(null);

        myRoom.addDoor("north", northDoor);
        myRoom.addDoor("south", southDoor);

        assertTrue(myRoom.hasDoor("north"), "Room should have north door");
        assertTrue(myRoom.hasDoor("south"), "Room should have south door");
    }

    /**
     * Tests that toString contains the row and column.
     */
    @Test
    public void testToStringContainsPosition() {
        final String result = myRoom.toString();
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("2"), "toString should contain row 2");
        assertTrue(result.contains("3"), "toString should contain col 3");
    }
}
