package model;

/*
 * TCSS 360 - Trivia Maze
 * Spring 2026
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Saves and loads game state for the Trivia Maze project.
 *
 * This class supports both the original text save format and full Maze
 * serialization for saving the actual game state.
 *
 * @author Jinal
 * @author Roman Pavlyshyn
 * @version 18 May 2026
 */
public final class GameMemento {

    /**
     * The text file name used by the original save/load test.
     */
    private static final String TEXT_SAVE_FILE_NAME = "savegame.txt";

    /**
     * The binary file name used for full serialized maze saves.
     */
    private static final String MAZE_SAVE_FILE_NAME = "savegame.dat";

    /**
     * Private constructor to prevent objects of this utility class.
     */
    private GameMemento() {
        // Utility class should not be instantiated.
    }

    /**
     * Saves the current game state to a text file.
     *
     * This method is kept so existing TestRunner code still works.
     *
     * @param theState the current game state to save
     */
    public static void save(final String theState) {
        try (FileWriter writer = new FileWriter(TEXT_SAVE_FILE_NAME)) {
            writer.write(theState);
            System.out.println("Game saved.");
        } catch (final IOException exception) {
            System.out.println("Save error: " + exception.getMessage());
        }
    }

    /**
     * Loads the saved text game state from a file.
     *
     * This method is kept so existing TestRunner code still works.
     *
     * @return the saved game state, or an empty string if no save file exists
     */
    public static String load() {
        final File saveFile = new File(TEXT_SAVE_FILE_NAME);
        final StringBuilder savedState = new StringBuilder();

        if (saveFile.exists()) {
            readSavedState(saveFile, savedState);
        } else {
            System.out.println("No saved game found.");
        }

        return savedState.toString().trim();
    }

    /**
     * Saves the full Maze object to a binary file.
     *
     * @param theMaze the Maze object to save
     * @return true if the save succeeded, false otherwise
     */
    public static boolean saveMaze(final Maze theMaze) {
        boolean saved = false;

        if (theMaze == null) {
            System.out.println("Save error: maze was null.");
            return false;
        }

        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(MAZE_SAVE_FILE_NAME))) {
            output.writeObject(theMaze);
            saved = true;
            System.out.println("Maze saved.");
        } catch (final IOException exception) {
            System.out.println("Maze save error: " + exception.getMessage());
        }

        return saved;
    }

    /**
     * Loads a full Maze object from the binary save file.
     *
     * @return the loaded Maze, or null if loading failed
     */
    public static Maze loadMaze() {
        Maze loadedMaze = null;
        final File saveFile = new File(MAZE_SAVE_FILE_NAME);

        if (!saveFile.exists()) {
            System.out.println("No maze save file found.");
            return null;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(saveFile))) {
            final Object savedObject = input.readObject();

            if (savedObject instanceof Maze) {
                loadedMaze = (Maze) savedObject;
                System.out.println("Maze loaded.");
            } else {
                System.out.println("Load error: saved file did not contain a Maze.");
            }
        } catch (final IOException exception) {
            System.out.println("Maze load error: " + exception.getMessage());
        } catch (final ClassNotFoundException exception) {
            System.out.println("Maze load error: saved class was not found.");
        }

        return loadedMaze;
    }

    /**
     * Reads the saved game state from the text save file.
     *
     * @param theSaveFile the save file to read
     * @param theSavedState the string builder used to store loaded data
     */
    private static void readSavedState(final File theSaveFile,
                                       final StringBuilder theSavedState) {
        try (Scanner scanner = new Scanner(theSaveFile)) {
            while (scanner.hasNextLine()) {
                theSavedState.append(scanner.nextLine());
                theSavedState.append(System.lineSeparator());
            }
            System.out.println("Game loaded.");
        } catch (final IOException exception) {
            System.out.println("Load error: " + exception.getMessage());
        }
    }
}