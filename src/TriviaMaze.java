/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

import javax.swing.SwingUtilities;
import view.MazeGUI;

/**
 * Entry point for the Trivia Maze application.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 24 May 2026
 */
public final class TriviaMaze {

    /**
     * Private constructor to prevent instantiation.
     */
    private TriviaMaze() {
    }

    /**
     * Starts the program.
     *
     * @param theArgs command line arguments
     */
    public static void main(final String[] theArgs) {
        SwingUtilities.invokeLater(MazeGUI::createAndShowGUI);
    }
}