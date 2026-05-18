/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

/**
 * Represents the Maze for the Trivia Maze game.
 *
 * @author Suhayr Hassan
 * @author Roman Pavlyshyn
 * @author Jinal Thummar
 * @version 16th May 2026
 */
public class Maze {

    /** The minimum allowed maze size. */
    private static final int MINIMUM_MAZE_SIZE = 4;

    /** The default database path for trivia questions. */
    private static final String DEFAULT_DB_PATH = "questions.db";

    /** The north direction. */
    private static final String NORTH = "north";

    /** The south direction. */
    private static final String SOUTH = "south";

    /** The east direction. */
    private static final String EAST = "east";

    /** The west direction. */
    private static final String WEST = "west";

    /** The grid of rooms in the maze. */
    private final Room[][] myRooms;

    /** The size of the maze. */
    private final int mySize;

    /** The factory used to create questions for doors. */
    private final QuestionFactory myQuestionFactory;

    /** The current row position of the player. */
    private int myCurrentRow;

    /** The current column position of the player. */
    private int myCurrentCol;

    /**
     * Constructs a Maze of the given size and initializes all rooms and doors.
     *
     * @param theSize the number of rows and columns in the maze
     */
    public Maze(final int theSize) {
        if (theSize < MINIMUM_MAZE_SIZE) {
            throw new IllegalArgumentException("Maze size must be at least 4.");
        }

        mySize = theSize;
        myRooms = new Room[theSize][theSize];
        myQuestionFactory = new QuestionFactory(DEFAULT_DB_PATH);
        myCurrentRow = 0;
        myCurrentCol = 0;

        initializeRooms();
        initializeDoors();
        getCurrentRoom().setVisited(true);
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
     * Connects neighboring rooms with shared doors.
     */
    private void initializeDoors() {
        initializeHorizontalDoors();
        initializeVerticalDoors();
    }

    /**
     * Connects rooms that are next to each other horizontally.
     */
    private void initializeHorizontalDoors() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize - 1; col++) {
                final Door door = new Door(myQuestionFactory.getRandomQuestion());

                myRooms[row][col].addDoor(EAST, door);
                myRooms[row][col + 1].addDoor(WEST, door);
            }
        }
    }

    /**
     * Connects rooms that are next to each other vertically.
     */
    private void initializeVerticalDoors() {
        for (int row = 0; row < mySize - 1; row++) {
            for (int col = 0; col < mySize; col++) {
                final Door door = new Door(myQuestionFactory.getRandomQuestion());

                myRooms[row][col].addDoor(SOUTH, door);
                myRooms[row + 1][col].addDoor(NORTH, door);
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
     * Sets the current player position in the maze.
     *
     * @param theRow the row to move the player to
     * @param theCol the column to move the player to
     */
    public void setCurrentPosition(final int theRow, final int theCol) {
        if (!isInBounds(theRow, theCol)) {
            throw new IllegalArgumentException("Player position is outside the maze.");
        }

        myCurrentRow = theRow;
        myCurrentCol = theCol;
        getCurrentRoom().setVisited(true);
    }

    /**
     * Returns the size of the maze.
     *
     * @return the maze size
     */
    public int getSize() {
        return mySize;
    }

    /**
     * Attempts to move the player in the given direction.
     * Movement succeeds only if the door in that direction exists and is not locked.
     *
     * @param theDirection the direction to move
     * @return true if the move was successful, false otherwise
     */
    public boolean move(final String theDirection) {
        boolean moved = false;
        final Room currentRoom = getCurrentRoom();

        if (currentRoom.hasDoor(theDirection) && !currentRoom.isDoorLocked(theDirection)) {
            final int newRow = myCurrentRow + getRowOffset(theDirection);
            final int newCol = myCurrentCol + getColOffset(theDirection);

            if (isInBounds(newRow, newCol)) {
                myCurrentRow = newRow;
                myCurrentCol = newCol;
                getCurrentRoom().setVisited(true);
                moved = true;
            }
        }

        return moved;
    }

    /**
     * Returns whether the player has reached the exit.
     *
     * @return true if the player is at the exit, false otherwise
     */
    public boolean isGameWon() {
        return myCurrentRow == mySize - 1 && myCurrentCol == mySize - 1;
    }

    /**
     * Returns whether the game is lost because no path exists to the exit.
     *
     * @return true if no path exists to the exit, false otherwise
     */
    public boolean isGameOver() {
        final boolean[][] visited = new boolean[mySize][mySize];

        return !canReachExit(myCurrentRow, myCurrentCol, visited);
    }

    /**
     * Returns the row offset for a given direction.
     *
     * @param theDirection the direction string
     * @return the row offset
     */
    private int getRowOffset(final String theDirection) {
        int offset = 0;

        if (NORTH.equalsIgnoreCase(theDirection)) {
            offset = -1;
        } else if (SOUTH.equalsIgnoreCase(theDirection)) {
            offset = 1;
        }

        return offset;
    }

    /**
     * Returns the column offset for a given direction.
     *
     * @param theDirection the direction string
     * @return the column offset
     */
    private int getColOffset(final String theDirection) {
        int offset = 0;

        if (WEST.equalsIgnoreCase(theDirection)) {
            offset = -1;
        } else if (EAST.equalsIgnoreCase(theDirection)) {
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
        return theRow >= 0 && theRow < mySize
                && theCol >= 0 && theCol < mySize;
    }

    /**
     * Recursively checks if the exit can be reached from the given position.
     *
     * @param theRow the current row being checked
     * @param theCol the current column being checked
     * @param theVisited the rooms already checked
     * @return true if the exit is reachable, false otherwise
     */
    private boolean canReachExit(final int theRow,
                                 final int theCol,
                                 final boolean[][] theVisited) {
        boolean canReach = false;

        if (theRow == mySize - 1 && theCol == mySize - 1) {
            canReach = true;
        } else {
            theVisited[theRow][theCol] = true;

            canReach = canReachDirection(theRow, theCol, NORTH, theVisited)
                    || canReachDirection(theRow, theCol, SOUTH, theVisited)
                    || canReachDirection(theRow, theCol, EAST, theVisited)
                    || canReachDirection(theRow, theCol, WEST, theVisited);
        }

        return canReach;
    }

    /**
     * Checks if the exit can be reached through one direction.
     *
     * @param theRow the current row
     * @param theCol the current column
     * @param theDirection the direction to check
     * @param theVisited the rooms already checked
     * @return true if the exit is reachable through that direction
     */
    private boolean canReachDirection(final int theRow,
                                      final int theCol,
                                      final String theDirection,
                                      final boolean[][] theVisited) {
        boolean canReach = false;

        final int newRow = theRow + getRowOffset(theDirection);
        final int newCol = theCol + getColOffset(theDirection);

        if (isInBounds(newRow, newCol) && !theVisited[newRow][newCol]) {
            final Room room = myRooms[theRow][theCol];

            if (room.hasDoor(theDirection) && !room.isDoorLocked(theDirection)) {
                canReach = canReachExit(newRow, newCol, theVisited);
            }
        }

        return canReach;
    }
}
