/*
 * TCSS 360 - Trivia Maze
 * Iteration 1
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The GameMemento class saves and loads a basic game state for the
 * Trivia Maze project.
 *
 * @author Jinal
 * @version 3 May 2026
 */
public final class GameMemento {

    /**
     * The file name used to store saved game data.
     */
    private static final String SAVE_FILE_NAME = "savegame.txt";

    /**
     * Private constructor to prevent objects of this utility class.
     */
    private GameMemento() {
        // Utility class should not be instantiated.
    }

    /**
     * Saves the current game state to a file.
     *
     * @param theState the current game state to save
     */
    public static void save(final String theState) {
        try (FileWriter writer = new FileWriter(SAVE_FILE_NAME)) {
            writer.write(theState);
            System.out.println("Game saved.");
        } catch (final IOException exception) {
            System.out.println("Save error: " + exception.getMessage());
        }
    }

    /**
     * Loads the saved game state from a file.
     *
     * @return the saved game state, or an empty string if no save file exists
     */
    public static String load() {
        final File saveFile = new File(SAVE_FILE_NAME);
        final StringBuilder savedState = new StringBuilder();

        if (saveFile.exists()) {
            readSavedState(saveFile, savedState);
        } else {
            System.out.println("No saved game found.");
        }

        return savedState.toString().trim();
    }

    /**
     * Reads the saved game state from the save file.
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