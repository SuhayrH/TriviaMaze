/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.Door;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Door class.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class DoorTest {

    /**
     * The door used in each test.
     */
    private Door myDoor;

    /**
     * Sets up a fresh Door before each test.
     */
    @BeforeEach
    public void setUp() {
        myDoor = new Door(null);
    }

    /**
     * Tests that a new door is not locked by default.
     */
    @Test
    public void testNewDoorIsUnlocked() {
        assertFalse(myDoor.isLocked(), "New door should not be locked");
    }

    /**
     * Tests that lock() permanently locks the door.
     */
    @Test
    public void testLockDoor() {
        myDoor.lock();
        assertTrue(myDoor.isLocked(), "Door should be locked after lock()");
    }

    /**
     * Tests that calling lock() multiple times keeps the door locked.
     */
    @Test
    public void testLockDoorMultipleTimes() {
        myDoor.lock();
        myDoor.lock();
        assertTrue(myDoor.isLocked(), "Door should remain locked after multiple lock() calls");
    }

    /**
     * Tests that getQuestion returns null when no question is assigned.
     */
    @Test
    public void testGetQuestionReturnsNull() {
        assertNull(myDoor.getQuestion(), "getQuestion should return null when no question assigned");
    }

    /**
     * Tests that isLocked returns false before locking.
     */
    @Test
    public void testIsLockedFalseBeforeLock() {
        assertFalse(myDoor.isLocked(), "isLocked should return false before lock() is called");
    }

    /**
     * Tests that isLocked returns true after locking.
     */
    @Test
    public void testIsLockedTrueAfterLock() {
        myDoor.lock();
        assertTrue(myDoor.isLocked(), "isLocked should return true after lock() is called");
    }
}