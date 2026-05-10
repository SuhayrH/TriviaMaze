/*
 * TCSS 360 - Trivia Maze
 * Iteration 2
 */

/**
 * Represents the maze used in the Trivia Maze game.
 *
 * @author Jinal Thummar
 * @version 10 May 2026
 */
public final class Maze {

    /**
     * The minimum supported maze size.
     */
    private static final int MINIMUM_MAZE_SIZE = 2;

    /**
     * The north direction.
     */
    private static final String NORTH = "North";

    /**
     * The south direction.
     */
    private static final String SOUTH = "South";

    /**
     * The east direction.
     */
    private static final String EAST = "East";

    /**
     * The west direction.
     */
    private static final String WEST = "West";

    /**
     * The rooms in this maze.
     */
    private final Room[][] myRooms;

    /**
     * The question factory used to create door questions.
     */
    private final QuestionFactory myQuestionFactory;

    /**
     * The size of the square maze.
     */
    private final int mySize;

    /**
     * The player's current row.
     */
    private int myCurrentRow;

    /**
     * The player's current column.
     */
    private int myCurrentColumn;

    /**
     * Creates a new maze.
     *
     * @param theSize the size of the square maze
     */
    public Maze(final int theSize) {
        if (theSize < MINIMUM_MAZE_SIZE) {
            throw new IllegalArgumentException("Maze size must be at least 2.");
        }

        mySize = theSize;
        myRooms = new Room[theSize][theSize];
        myQuestionFactory = new QuestionFactory();
        myCurrentRow = 0;
        myCurrentColumn = 0;

        initializeRooms();
        initializeDoors();

        getCurrentRoom().setVisited(true);
    }

    /**
     * Returns the current room.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return myRooms[myCurrentRow][myCurrentColumn];
    }

    /**
     * Returns a room from the maze.
     *
     * @param theRow the room row
     * @param theColumn the room column
     * @return the requested room
     */
    public Room getRoom(final int theRow,
                        final int theColumn) {
        return myRooms[theRow][theColumn];
    }

    /**
     * Returns the current row.
     *
     * @return the current row
     */
    public int getCurrentRow() {
        return myCurrentRow;
    }

    /**
     * Returns the current column.
     *
     * @return the current column
     */
    public int getCurrentColumn() {
        return myCurrentColumn;
    }

    /**
     * Gets the door in the requested direction from the current room.
     *
     * @param theDirection the movement direction
     * @return the matching door, or null if no door exists
     */
    public Door getCurrentDoor(final String theDirection) {
        return getCurrentRoom().getDoor(theDirection);
    }

    /**
     * Moves the player in the given direction if the move is valid.
     *
     * @param theDirection the movement direction
     * @return true if the player moved successfully
     */
    public boolean move(final String theDirection) {
        boolean moved = false;
        final Door door = getCurrentDoor(theDirection);

        if (door != null && !door.isLocked()) {
            updatePosition(theDirection);
            getCurrentRoom().setVisited(true);
            moved = true;
        }

        return moved;
    }

    /**
     * Checks whether the player has reached the exit.
     *
     * @return true if the game is won
     */
    public boolean isGameWon() {
        return myCurrentRow == mySize - 1 && myCurrentColumn == mySize - 1;
    }

    /**
     * Checks whether the game is over.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return isGameWon();
    }

    /**
     * Creates all rooms in the maze.
     */
    private void initializeRooms() {
        for (int row = 0; row < mySize; row++) {
            for (int column = 0; column < mySize; column++) {
                myRooms[row][column] = new Room(row, column);
            }
        }
    }

    /**
     * Creates doors for valid directions in every room.
     */
    private void initializeDoors() {
        for (int row = 0; row < mySize; row++) {
            for (int column = 0; column < mySize; column++) {
                addDoorsForRoom(row, column);
            }
        }
    }

    /**
     * Adds doors for one room based on maze boundaries.
     *
     * @param theRow the room row
     * @param theColumn the room column
     */
    private void addDoorsForRoom(final int theRow,
                                 final int theColumn) {
        final Room room = myRooms[theRow][theColumn];

        if (theRow > 0) {
            room.addDoor(NORTH, createDoor());
        }

        if (theRow < mySize - 1) {
            room.addDoor(SOUTH, createDoor());
        }

        if (theColumn < mySize - 1) {
            room.addDoor(EAST, createDoor());
        }

        if (theColumn > 0) {
            room.addDoor(WEST, createDoor());
        }
    }

    /**
     * Creates a door with a random database question.
     *
     * @return a new door
     */
    private Door createDoor() {
        return new Door(myQuestionFactory.getRandomQuestion());
    }

    /**
     * Updates the player's position.
     *
     * @param theDirection the movement direction
     */
    private void updatePosition(final String theDirection) {
        if (NORTH.equals(theDirection)) {
            myCurrentRow--;
        } else if (SOUTH.equals(theDirection)) {
            myCurrentRow++;
        } else if (EAST.equals(theDirection)) {
            myCurrentColumn++;
        } else if (WEST.equals(theDirection)) {
            myCurrentColumn--;
        }
    }
}


//Iteration - 1
// @author Roman Pavlyshyn


// public class Maze {
//     private Room[][] myRooms;
//     private int myCurrentRow;
//     private int myCurrentCol;
//     private int mySize;

//     public Maze(int size) {
//         mySize = size;
//         myRooms = new Room[size][size];
//         myCurrentRow = 0;
//         myCurrentCol = 0;
//         initializeRooms();
//     }

//     private void initializeRooms() {
//         for (int row = 0; row < mySize; row++) {
//             for (int col = 0; col < mySize; col++) {
//                 myRooms[row][col] = new Room(row, col);
//             }
//         }
//     }

//     public Room getCurrentRoom() {
//         return myRooms[myCurrentRow][myCurrentCol];
//     }

//     public boolean move(String direction) {
//         return false; // TODO: implement in future iteration
//     }

//     public boolean isGameOver() {
//         return false; // TODO: implement in future iteration
//     }

//     public boolean isGameWon() {
//         return false; // TODO: implement in future iteration
//     }

//     public Room getRoom(int row, int col) {
//         return myRooms[row][col];
//     }
// }