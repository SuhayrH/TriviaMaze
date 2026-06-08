package test;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class.
 *
 * @author Suhayr Hassan
 * @version 7 June 2026
 */
public class PlayerTest {

    private Player myPlayer;

    @BeforeEach
    void setUp() {
        myPlayer = new Player("Warrior");
    }

    @Test
    void testInitialGamertag() {
        assertEquals("Warrior", myPlayer.getGamertag());
    }

    @Test
    void testInitialScore() {
        assertEquals(0, myPlayer.getScore());
    }

    @Test
    void testInitialPosition() {
        assertEquals(0, myPlayer.getCurrentRow());
        assertEquals(0, myPlayer.getCurrentCol());
    }

    @Test
    void testAddPoint() {
        myPlayer.addPoint();
        assertEquals(1, myPlayer.getScore());
    }

    @Test
    void testAddMultiplePoints() {
        myPlayer.addPoint();
        myPlayer.addPoint();
        myPlayer.addPoint();
        assertEquals(3, myPlayer.getScore());
    }

    @Test
    void testMoveNorth() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("North");
        assertEquals(1, myPlayer.getCurrentRow());
        assertEquals(2, myPlayer.getCurrentCol());
    }

    @Test
    void testMoveSouth() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("South");
        assertEquals(3, myPlayer.getCurrentRow());
        assertEquals(2, myPlayer.getCurrentCol());
    }

    @Test
    void testMoveEast() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("East");
        assertEquals(2, myPlayer.getCurrentRow());
        assertEquals(3, myPlayer.getCurrentCol());
    }

    @Test
    void testMoveWest() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("West");
        assertEquals(2, myPlayer.getCurrentRow());
        assertEquals(1, myPlayer.getCurrentCol());
    }

    @Test
    void testMoveNorthCaseInsensitive() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("north");
        assertEquals(1, myPlayer.getCurrentRow());
    }

    @Test
    void testSetPosition() {
        myPlayer.setPosition(3, 4);
        assertEquals(3, myPlayer.getCurrentRow());
        assertEquals(4, myPlayer.getCurrentCol());
    }

    @Test
    void testInvalidDirectionDoesNotMove() {
        myPlayer.setPosition(2, 2);
        myPlayer.move("diagonal");
        assertEquals(2, myPlayer.getCurrentRow());
        assertEquals(2, myPlayer.getCurrentCol());
    }

    @Test
    void testGamertagNotNull() {
        assertNotNull(myPlayer.getGamertag());
    }

    @Test
    void testGamertagNotEmpty() {
        assertFalse(myPlayer.getGamertag().isEmpty());
    }
}