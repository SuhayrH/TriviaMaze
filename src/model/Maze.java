package model;

/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 * Author: Suhayr Hassan
 */

/**
 * Represents the Maze for the Trivia Maze game.
 * The maze is a grid of rooms that the player navigates through
 * from the entrance (top-left) to the exit (bottom-right).
 * Adjacent rooms share the same Door object so locking one side
 * locks the other automatically.
 *
 * @author Suhayr Hassan
 * @version 17 May 2026
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
        mySize       = theSize;
        myRooms      = new Room[theSize][theSize];
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
     * Initializes doors between all adjacent rooms using questions from the factory.
     * Adjacent rooms share the same Door object so locking it from either side
     * affects both rooms.
     *
     * @param theFactory the QuestionFactory to get questions from
     */
    public void initializeDoors(final QuestionFactory theFactory) {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {

                // Share one Door object between vertically adjacent rooms
                if (row + 1 < mySize) {
                    final Door sharedVertical = new Door(theFactory.getRandomQuestion());
                    myRooms[row][col].addDoor("south", sharedVertical);
                    myRooms[row + 1][col].addDoor("north", sharedVertical);
                }

                // Share one Door object between horizontally adjacent rooms
                if (col + 1 < mySize) {
                    final Door sharedHorizontal = new Door(theFactory.getRandomQuestion());
                    myRooms[row][col].addDoor("east", sharedHorizontal);
                    myRooms[row][col + 1].addDoor("west", sharedHorizontal);
                }
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
     * @return the Room at the given position, or null if out of bounds
     */
    public Room getRoom(final int theRow, final int theCol) {
        if (!isInBounds(theRow, theCol)) {
            return null;
        }
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
     * Movement succeeds only if a door exists in that direction and is not locked.
     * Marks the destination room as visited on success.
     *
     * @param theDirection the direction to move ("north", "south", "east", "west")
     * @return true if the move was successful, false otherwise
     */
    public boolean move(final String theDirection) {
        final Room current = getCurrentRoom();

        if (!current.hasDoor(theDirection) || current.isDoorLocked(theDirection)) {
            return false;
        }

        final int newRow = myCurrentRow + getRowOffset(theDirection);
        final int newCol = myCurrentCol + getColOffset(theDirection);

        if (!isInBounds(newRow, newCol)) {
            return false;
        }

        myCurrentRow = newRow;
        myCurrentCol = newCol;
        myRooms[myCurrentRow][myCurrentCol].setVisited(true);
        return true;
    }

    /**
     * Returns whether the player has reached the exit (bottom-right room).
     *
     * @return true if the player is at the exit
     */
    public boolean isGameWon() {
        return myCurrentRow == mySize - 1 && myCurrentCol == mySize - 1;
    }

    /**
     * Returns whether the game is lost — no valid path exists from the
     * player's current position to the exit using only unlocked doors.
     * Uses depth-first search to determine reachability.
     *
     * @return true if no path exists to the exit
     */
    public boolean isGameOver() {
        final boolean[][] visited = new boolean[mySize][mySize];
        return !canReachExit(myCurrentRow, myCurrentCol, visited);
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
     * @return true if in bounds
     */
    private boolean isInBounds(final int theRow, final int theCol) {
        return theRow >= 0 && theRow < mySize
                && theCol >= 0 && theCol < mySize;
    }

    /**
     * Recursively checks if the exit can be reached from the given position
     * using only unlocked doors (depth-first search).
     *
     * @param theRow     the current row being checked
     * @param theCol     the current column being checked
     * @param theVisited 2D array tracking visited rooms during search
     * @return true if the exit is reachable
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

    /**
     * Returns a string representation of the maze.
     *
     * @return a string describing the maze grid
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Maze [").append(mySize).append("x").append(mySize).append("]\n");
        sb.append("Player at: (").append(myCurrentRow).append(", ")
                .append(myCurrentCol).append(")\n");
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                sb.append(myRooms[row][col].toString()).append("\n");
            }
        }
        return sb.toString();
    }
}