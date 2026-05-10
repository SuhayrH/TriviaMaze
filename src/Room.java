/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Represents one room in the Trivia Maze.
 *
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class Room {

    /**
     * The row position of this room.
     */
    private final int myRow;

    /**
     * The column position of this room.
     */
    private final int myColumn;

    /**
     * The doors available from this room.
     */
    private final Map<String, Door> myDoors;

    /**
     * Whether the player has visited this room.
     */
    private boolean myVisited;

    /**
     * Creates a new room.
     *
     * @param theRow the row position
     * @param theColumn the column position
     */
    public Room(final int theRow, final int theColumn) {
        myRow = theRow;
        myColumn = theColumn;
        myDoors = new HashMap<String, Door>();
        myVisited = false;
    }

    /**
     * Adds a door in the given direction.
     *
     * @param theDirection the direction for the door
     * @param theDoor the door to add
     */
    public void addDoor(final String theDirection,
                        final Door theDoor) {
        myDoors.put(theDirection, theDoor);
    }

    /**
     * Gets the door in the given direction.
     *
     * @param theDirection the direction to check
     * @return the matching door, or null if no door exists
     */
    public Door getDoor(final String theDirection) {
        return myDoors.get(theDirection);
    }

    /**
     * Checks whether this room has a door in the given direction.
     *
     * @param theDirection the direction to check
     * @return true if a door exists in the direction
     */
    public boolean hasDoor(final String theDirection) {
        return myDoors.containsKey(theDirection);
    }

    /**
     * Returns this room's row.
     *
     * @return the room row
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Returns this room's column.
     *
     * @return the room column
     */
    public int getColumn() {
        return myColumn;
    }

    /**
     * Checks whether this room has been visited.
     *
     * @return true if the room has been visited
     */
    public boolean isVisited() {
        return myVisited;
    }

    /**
     * Updates whether this room has been visited.
     *
     * @param theVisited true if the room has been visited
     */
    public void setVisited(final boolean theVisited) {
        myVisited = theVisited;
    }
}


// Iteration-1
// @author Roman Pavlyshyn


// import java.util.HashMap;
// import java.util.Map;

// public class Room {
//     private int myRow;
//     private int myCol;
//     private Map<String, Door> myDoors;
//     private boolean myVisited;

//     public Room(int row, int col) {
//         myRow = row;
//         myCol = col;
//         myDoors = new HashMap<>();
//         myVisited = false;
//     }

//     public Door getDoor(String direction) {
//         return myDoors.get(direction);
//     }

//     public boolean isVisited() {
//         return myVisited;
//     }

//     public void setVisited(boolean visited) {
//         myVisited = visited;
//     }
// }