package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a model.Room in the Trivia model.Maze game.
 * @author Suhayr Hassan
 * @author Roman Pavlyshyn
 * @version 17 May 2026
 */
public class Room implements Serializable {

    /** Serialization version number. */
    private static final long serialVersionUID = 1L;

    /** The row position of this room in the maze grid. */
    private final int myRow;

    /** The column position of this room in the maze grid. */
    private final int myCol;

    /** The doors in this room, keyed by direction (north, south, east, west). */
    private final Map<String, Door> myDoors;

    /** Whether the player has visited this room. */
    private boolean myVisited;

    /**
     * Constructs a model.Room at the specified grid position with no doors and unvisited.
     *
     * @param theRow the row index of this room in the maze
     * @param theCol the column index of this room in the maze
     */
    public Room(final int theRow, final int theCol) {
        myRow = theRow;
        myCol = theCol;
        myDoors = new HashMap<>();
        myVisited = false;
    }

    /**
     * Returns the row index of this room.
     *
     * @return the row index
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Returns the column index of this room.
     *
     * @return the column index
     */
    public int getCol() {
        return myCol;
    }

    /**
     * Returns whether this room has been visited by the player.
     *
     * @return true if visited, false otherwise
     */
    public boolean isVisited() {
        return myVisited;
    }

    /**
     * Sets the visited state of this room.
     *
     * @param theVisited true to mark as visited, false otherwise
     */
    public void setVisited(final boolean theVisited) {
        myVisited = theVisited;
    }

    /**
     * Adds a door to this room in the specified direction.
     *
     * @param theDirection the direction of the door ("north", "south", "east", "west")
     * @param theDoor      the model.Door object to add
     */
    public void addDoor(final String theDirection, final Door theDoor) {
        myDoors.put(theDirection.toLowerCase(), theDoor);
    }

    /**
     * Returns the model.Door in the specified direction, or null if none exists.
     *
     * @param theDirection the direction to check
     * @return the model.Door in that direction, or null
     */
    public Door getDoor(final String theDirection) {
        return myDoors.get(theDirection.toLowerCase());
    }

    /**
     * Returns whether this room has a door in the specified direction.
     *
     * @param theDirection the direction to check
     * @return true if a door exists in that direction, false otherwise
     */
    public boolean hasDoor(final String theDirection) {
        return myDoors.containsKey(theDirection.toLowerCase());
    }

    /**
     * Returns whether the door in the specified direction is locked.
     * Returns true if no door exists in that direction.
     *
     * @param theDirection the direction to check
     * @return true if the door is locked or does not exist, false if open
     */
    public boolean isDoorLocked(final String theDirection) {
        final Door door = myDoors.get(theDirection.toLowerCase());
        if (door == null) {
            return true;
        }
        return door.isLocked();
    }

    /**
     * Locks the door in the specified direction permanently.
     * Does nothing if no door exists in that direction.
     *
     * @param theDirection the direction of the door to lock
     */
    public void lockDoor(final String theDirection) {
        final Door door = myDoors.get(theDirection.toLowerCase());
        if (door != null) {
            door.lock();
        }
    }

    /**
     * Returns a string representation of this room showing its position,
     * visited state, and available doors.
     *
     * @return a string describing this room
     */
    @Override
    public String toString() {
        return "model.Room[" + myRow + "][" + myCol + "] visited=" + myVisited
                + " doors=" + myDoors.keySet();
    }
}