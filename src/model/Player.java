package model;

/**
 * Represents the player in the Trivia model.Maze game.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class Player {
    /**
     * The player's displayed game name.
     */
    private final String myGamertag;

    /**
     * The player's current score.
     */
    private int myScore;

    /**
     * The player's current row in the maze.
     */
    private int myCurrentRow;

    /**
     * The player's current column in the maze.
     */
    private int myCurrentCol;

    /**
     * Creates a new player at the maze entrance.
     *
     * @param theGamertag the player's displayed game name
     */
    public Player(final String theGamertag) {
        myGamertag = theGamertag;
        myScore = 0;
        myCurrentRow = 0;
        myCurrentCol = 0;
    }

    /**
     * Returns the player's displayed game name.
     *
     * @return the player's displayed game name
     */
    public String getGamertag() {
        return myGamertag;
    }

    /**
     * Returns the player's current score.
     *
     * @return the player's current score
     */
    public int getScore() {
        return myScore;
    }

    /**
     * Returns the player's current row.
     *
     * @return the player's current row
     */
    public int getCurrentRow() {
        return myCurrentRow;
    }

    /**
     * Returns the player's current column.
     *
     * @return the player's current column
     */
    public int getCurrentCol() {
        return myCurrentCol;
    }

    /**
     * Adds one point to the player's score.
     */
    public void addPoint() {
        myScore++;
    }

    /**
     * Moves the player one space in the given direction.
     *
     * @param theDirection the direction to move
     */
    public void move(final String theDirection) {
        if ("North".equalsIgnoreCase(theDirection)) {
            myCurrentRow--;
        } else if ("South".equalsIgnoreCase(theDirection)) {
            myCurrentRow++;
        } else if ("East".equalsIgnoreCase(theDirection)) {
            myCurrentCol++;
        } else if ("West".equalsIgnoreCase(theDirection)) {
            myCurrentCol--;
        }
    }

    /**
     * Sets the player's current maze position.
     *
     * @param theRow the new row
     * @param theCol the new column
     */
    public void setPosition(final int theRow, final int theCol) {
        myCurrentRow = theRow;
        myCurrentCol = theCol;
    }
}