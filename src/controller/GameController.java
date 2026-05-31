/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package controller;

import java.util.List;

import model.Door;
import model.GameMemento;
import model.Maze;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TrueFalseQuestion;

/**
 * Controls gameplay actions between the Trivia Maze view and model.
 * This class helps separate GUI display code from model logic for MVC.
 *
 * @author Jinal Thummar
 * @version 31 May 2026
 */
public class GameController {

    /**
     * Value used when no character has been selected.
     */
    private static final int NO_SELECTED_CHARACTER = -1;

    /**
     * First character index used for short-answer hints.
     */
    private static final int FIRST_CHARACTER_INDEX = 0;

    /**
     * Message used when no hint is available.
     */
    private static final String NO_HINT_MESSAGE =
            "Hint: no hint is available for this answer.";

    /**
     * Message used for true or false questions.
     */
    private static final String TRUE_FALSE_HINT = "Hint: answer True or False.";

    /**
     * The maze model controlled by this controller.
     */
    private Maze myMaze;

    /**
     * The currently selected character index.
     */
    private int mySelectedCharacter;

    /**
     * Whether the game has started.
     */
    private boolean myGameStarted;

    /**
     * The current door selected by the player.
     */
    private Door myCurrentDoor;

    /**
     * The current direction selected by the player.
     */
    private String myCurrentDirection;

    /**
     * The number of correct answers.
     */
    private int myCorrectCount;

    /**
     * Whether a hint has been used for the current question.
     */
    private boolean myHintUsedForQuestion;

    /**
     * Constructs a new game controller.
     *
     * @param theMaze the maze model
     */
    public GameController(final Maze theMaze) {
        myMaze = theMaze;
        mySelectedCharacter = NO_SELECTED_CHARACTER;
        myGameStarted = false;
        myCurrentDoor = null;
        myCurrentDirection = null;
        myCorrectCount = 0;
        myHintUsedForQuestion = false;
    }

    /**
     * Gets the maze model.
     *
     * @return the maze model
     */
    public Maze getMaze() {
        return myMaze;
    }

    /**
     * Sets a loaded maze into the controller.
     *
     * @param theMaze the loaded maze model
     */
    public void loadMazeState(final Maze theMaze) {
        myMaze = theMaze;
        myGameStarted = true;
        myCurrentDoor = null;
        myCurrentDirection = null;
        myCorrectCount = 0;
        myHintUsedForQuestion = false;
    }

    /**
     * Resets the controller for a new maze.
     *
     * @param theMaze the new maze model
     */
    public void resetForNewMaze(final Maze theMaze) {
        myMaze = theMaze;
        myGameStarted = true;
        myCurrentDoor = null;
        myCurrentDirection = null;
        myCorrectCount = 0;
        myHintUsedForQuestion = false;
    }

    /**
     * Selects the player's character.
     *
     * @param theSelectedCharacter the selected character index
     */
    public void selectCharacter(final int theSelectedCharacter) {
        mySelectedCharacter = theSelectedCharacter;
    }

    /**
     * Gets the selected character index.
     *
     * @return the selected character index
     */
    public int getSelectedCharacter() {
        return mySelectedCharacter;
    }

    /**
     * Starts the game if a character has been selected.
     *
     * @return true if the game started
     */
    public boolean startGame() {
        final boolean started;

        if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
            started = false;
        } else {
            myGameStarted = true;
            started = true;
        }

        return started;
    }

    /**
     * Checks whether the game has started.
     *
     * @return true if the game has started
     */
    public boolean isGameStarted() {
        return myGameStarted;
    }

    /**
     * Chooses a door for the requested direction.
     *
     * @param theDirection the selected movement direction
     * @return the selected door, or null if the direction is invalid
     */
    public Door chooseDoor(final String theDirection) {
        Door selectedDoor = null;

        if (myMaze.getCurrentRoom().hasDoor(theDirection)
                && !myMaze.getCurrentRoom().isDoorLocked(theDirection)) {
            selectedDoor = myMaze.getCurrentRoom().getDoor(theDirection);
            myCurrentDoor = selectedDoor;
            myCurrentDirection = theDirection;
            myHintUsedForQuestion = false;
        }

        return selectedDoor;
    }

    /**
     * Checks whether the current room has a door in a direction.
     *
     * @param theDirection the movement direction
     * @return true if the room has a door in that direction
     */
    public boolean currentRoomHasDoor(final String theDirection) {
        return myMaze.getCurrentRoom().hasDoor(theDirection);
    }

    /**
     * Checks whether the current room door is locked.
     *
     * @param theDirection the movement direction
     * @return true if the door exists and is locked
     */
    public boolean isCurrentRoomDoorLocked(final String theDirection) {
        final boolean locked;

        if (myMaze.getCurrentRoom().hasDoor(theDirection)) {
            locked = myMaze.getCurrentRoom().isDoorLocked(theDirection);
        } else {
            locked = false;
        }

        return locked;
    }

    /**
     * Gets the current question.
     *
     * @return the current question, or null if no question is selected
     */
    public Question getCurrentQuestion() {
        Question question = null;

        if (myCurrentDoor != null) {
            question = myCurrentDoor.getQuestion();
        }

        return question;
    }

    /**
     * Checks whether a question is currently selected.
     *
     * @return true if a question is currently selected
     */
    public boolean hasCurrentQuestion() {
        return myCurrentDoor != null;
    }

    /**
     * Checks a submitted answer for the current question.
     *
     * @param theAnswer the submitted answer
     * @return true if the answer is correct
     */
    public boolean checkAnswer(final String theAnswer) {
        boolean correct = false;

        if (myCurrentDoor != null) {
            correct = myCurrentDoor.getQuestion().checkAnswer(theAnswer);
        }

        return correct;
    }

    /**
     * Moves the player through the selected door.
     *
     * @return true if the movement succeeded
     */
    public boolean moveThroughCurrentDoor() {
        boolean moved = false;

        if (myCurrentDirection != null) {
            moved = myMaze.move(myCurrentDirection);

            if (moved) {
                myCorrectCount++;
            }
        }

        return moved;
    }

    /**
     * Locks the currently selected door.
     */
    public void lockCurrentDoor() {
        if (myCurrentDirection != null) {
            myMaze.getCurrentRoom().lockDoor(myCurrentDirection);
        }
    }

    /**
     * Gets the current direction.
     *
     * @return the current direction
     */
    public String getCurrentDirection() {
        return myCurrentDirection;
    }

    /**
     * Gets the current correct answer.
     *
     * @return the current correct answer
     */
    public String getCurrentCorrectAnswer() {
        String answer = "";

        if (myCurrentDoor != null) {
            answer = myCurrentDoor.getQuestion().getCorrectAnswer();
        }

        return answer;
    }

    /**
     * Gets the correct answer count.
     *
     * @return the correct answer count
     */
    public int getCorrectCount() {
        return myCorrectCount;
    }

    /**
     * Clears the current question state.
     */
    public void clearCurrentQuestion() {
        myCurrentDoor = null;
        myCurrentDirection = null;
        myHintUsedForQuestion = false;
    }

    /**
     * Checks whether a hint has already been used for the current question.
     *
     * @return true if a hint has already been used
     */
    public boolean isHintUsedForQuestion() {
        return myHintUsedForQuestion;
    }

    /**
     * Builds and uses a hint for the current question.
     *
     * @return the hint text
     */
    public String useHint() {
        String hintText = NO_HINT_MESSAGE;
        final Question question = getCurrentQuestion();

        if (question != null && !myHintUsedForQuestion) {
            myHintUsedForQuestion = true;
            hintText = buildHintForQuestion(question);
        }

        return hintText;
    }

    /**
     * Saves the current game.
     *
     * @return true if the game saved successfully
     */
    public boolean saveGame() {
        return GameMemento.saveMaze(myMaze);
    }

    /**
     * Loads the saved game.
     *
     * @return the loaded maze, or null if no maze could be loaded
     */
    public Maze loadGame() {
        return GameMemento.loadMaze();
    }

    /**
     * Checks whether the game is won.
     *
     * @return true if the game is won
     */
    public boolean isGameWon() {
        return myMaze.isGameWon();
    }

    /**
     * Checks whether the game is over.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return myMaze.isGameOver();
    }

    /**
     * Builds a hint for the given question.
     *
     * @param theQuestion the question
     * @return the hint text
     */
    private String buildHintForQuestion(final Question theQuestion) {
        final String hintText;

        if (theQuestion instanceof MultipleChoiceQuestion) {
            hintText = buildMultipleChoiceHint((MultipleChoiceQuestion) theQuestion);
        } else if (theQuestion instanceof TrueFalseQuestion) {
            hintText = TRUE_FALSE_HINT;
        } else {
            hintText = buildShortAnswerHint(theQuestion.getCorrectAnswer());
        }

        return hintText;
    }

    /**
     * Builds a multiple choice hint.
     *
     * @param theQuestion the multiple choice question
     * @return the hint text
     */
    private String buildMultipleChoiceHint(final MultipleChoiceQuestion theQuestion) {
        final StringBuilder hintText = new StringBuilder("Hint: choices are ");
        final List<String> choices = theQuestion.getChoices();

        if (choices.isEmpty()) {
            hintText.append("not available");
        } else {
            appendChoices(hintText, choices);
        }

        hintText.append(".");

        return hintText.toString();
    }

    /**
     * Appends answer choices to a hint.
     *
     * @param theHintText the hint text builder
     * @param theChoices the answer choices
     */
    private void appendChoices(final StringBuilder theHintText,
                               final List<String> theChoices) {
        for (int i = 0; i < theChoices.size(); i++) {
            if (i > 0) {
                theHintText.append(", ");
            }

            theHintText.append(theChoices.get(i));
        }
    }

    /**
     * Builds a short-answer hint.
     *
     * @param theAnswer the correct answer
     * @return the hint text
     */
    private String buildShortAnswerHint(final String theAnswer) {
        String hintText = NO_HINT_MESSAGE;

        if (theAnswer != null && !theAnswer.isEmpty()) {
            hintText = "Hint: starts with '" + theAnswer.charAt(FIRST_CHARACTER_INDEX)
                    + "' and has " + theAnswer.length() + " characters.";
        }

        return hintText;
    }
}