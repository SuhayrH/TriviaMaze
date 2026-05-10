/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

/**
 * Represents the Maze for the Trivia Maze game.
 * @author Suhayr Hassan
 * @version 10 May 2026
 */
public class Maze {

    /** The grid of rooms in the maze. */
    private final Room[][] myRooms;

    /** The current row position of the player. */
    private int myCurrentRow;

    /** The current column position of the player. */
    private int myCurrentCol;

    /** The size (width and height) of the maze. */
    private final int mySize;

    /**
     * Constructs a Maze of the given size and initializes all rooms.
     *
     * @param theSize the number of rows and columns in the maze
     */
    public Maze(final int theSize) {
        mySize = theSize;
        myRooms = new Room[theSize][theSize];
        myCurrentRow = 0;
        myCurrentCol = 0;
        initializeRooms();
    }

    /**
     * Initializes all rooms in the maze grid.
     */
    private void initializeRooms() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
    }

    /**
     * Returns the room at the player's current position.
     *
     * @return the current Room
     */
    public Room getCurrentRoom() {
        return myRooms[myCurrentRow][myCurrentCol];
    }

    /**
     * Returns the room at the specified grid position.
     *
     * @param theRow the row index
     * @param theCol the column index
     * @return the Room at the given position
     */
    public Room getRoom(final int theRow, final int theCol) {
        return myRooms[theRow][theCol];
    }

    /**
     * Returns the current row of the player.
     *
     * @return the current row index
     */
    public int getCurrentRow() {
        return myCurrentRow;
    }

    /**
     * Returns the current column of the player.
     *
     * @return the current column index
     */
    public int getCurrentCol() {
        return myCurrentCol;
    }

    /**
     * Returns the size of the maze.
     *
     * @return the maze size (number of rows/columns)
     */
    public int getSize() {
        return mySize;
    }

    /**
     * Attempts to move the player in the given direction.
     * Movement succeeds only if the door in that direction exists
     * and is not locked.
     *
     * @param theDirection the direction to move ("north", "south", "east", "west")
     * @return true if the move was successful, false otherwise
     */
    public boolean move(final String theDirection) {
        final Room currentRoom = getCurrentRoom();

        if (!currentRoom.hasDoor(theDirection) || currentRoom.isDoorLocked(theDirection)) {
            return false;
        }

        final int newRow = myCurrentRow + getRowOffset(theDirection);
        final int newCol = myCurrentCol + getColOffset(theDirection);

        if (!isInBounds(newRow, newCol)) {
            return false;
        }

        myCurrentRow = newRow;
        myCurrentCol = newCol;
        return true;
    }

    /**
     * Returns the row offset for a given direction.
     *
     * @param theDirection the direction string
     * @return the row delta (-1, 0, or 1)
     */
    private int getRowOffset(final String theDirection) {
        int offset = 0;
        if ("north".equalsIgnoreCase(theDirection)) {
            offset = -1;
        } else if ("south".equalsIgnoreCase(theDirection)) {
            offset = 1;
        }
        return offset;
    }

    /**
     * Returns the column offset for a given direction.
     *
     * @param theDirection the direction string
     * @return the column delta (-1, 0, or 1)
     */
    private int getColOffset(final String theDirection) {
        int offset = 0;
        if ("west".equalsIgnoreCase(theDirection)) {
            offset = -1;
        } else if ("east".equalsIgnoreCase(theDirection)) {
            offset = 1;
        }
        return offset;
    }

    /**
     * Checks whether the given grid position is within maze bounds.
     *
     * @param theRow the row to check
     * @param theCol the column to check
     * @return true if in bounds, false otherwise
     */
    private boolean isInBounds(final int theRow, final int theCol) {
        return theRow >= 0 && theRow < mySize && theCol >= 0 && theCol < mySize;
    }

    /**
     * Returns whether the player has reached the exit (bottom-right room).
     *
     * @return true if the player is at the exit, false otherwise
     */
    public boolean isGameWon() {
        return myCurrentRow == mySize - 1 && myCurrentCol == mySize - 1;
    }

    /**
     * Returns whether the game is lost — i.e., there is no valid path
     * from the player's current position to the exit using unlocked doors.
     * Uses depth-first search to determine reachability.
     *
     * @return true if no path exists to the exit, false otherwise
     */
    public boolean isGameOver() {
        final boolean[][] visited = new boolean[mySize][mySize];
        return !canReachExit(myCurrentRow, myCurrentCol, visited);
    }

    /**
     * Recursively checks if the exit can be reached from the given position
     * using only unlocked doors (depth-first search).
     *
     * @param theRow     the current row being checked
     * @param theCol     the current column being checked
     * @param theVisited 2D array tracking visited rooms
     * @return true if the exit is reachable, false otherwise
     */
    private boolean canReachExit(final int theRow, final int theCol,
                                 final boolean[][] theVisited) {
        if (theRow == mySize - 1 && theCol == mySize - 1) {
            return true;
        }

        theVisited[theRow][theCol] = true;

        final String[] directions = {"north", "south", "east", "west"};

        for (final String dir : directions) {
            final int newRow = theRow + getRowOffset(dir);
            final int newCol = theCol + getColOffset(dir);

            if (!isInBounds(newRow, newCol) || theVisited[newRow][newCol]) {
                continue;
            }

            final Room room = myRooms[theRow][theCol];
            if (room.hasDoor(dir) && !room.isDoorLocked(dir)) {
                if (canReachExit(newRow, newCol, theVisited)) {
                    return true;
                }
            }
        }

        return false;
    }
}