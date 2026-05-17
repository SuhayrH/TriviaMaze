package model;

/**
 * Represents a door between rooms in the Trivia model.Maze game.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public class Door {
    /**
     * Whether this door is permanently locked.
     */
    private boolean myLocked;

    /**
     * The trivia question assigned to this door.
     */
    private final Question myQuestion;

    /**
     * Creates a new unlocked door with the given question.
     *
     * @param theQuestion the trivia question for this door
     */
    public Door(final Question theQuestion) {
        myQuestion = theQuestion;
        myLocked = false;
    }

    /**
     * Checks whether this door is locked.
     *
     * @return true if the door is locked, false otherwise
     */
    public boolean isLocked() {
        return myLocked;
    }

    /**
     * Permanently locks this door.
     */
    public void lock() {
        myLocked = true;
    }

    /**
     * Returns the question assigned to this door.
     *
     * @return the door's trivia question
     */
    public Question getQuestion() {
        return myQuestion;
    }
}