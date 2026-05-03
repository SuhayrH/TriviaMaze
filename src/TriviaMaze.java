import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Starts the Trivia Maze application.
 *
 * @author Roman Pavlyshyn
 * @version 2 May 2026
 */
public final class TriviaMaze {
    /**
     * The application window width.
     */
    private static final int FRAME_WIDTH = 700;

    /**
     * The application window height.
     */
    private static final int FRAME_HEIGHT = 500;

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createWindow();
            }
        });
    }

    /**
     * Creates and displays the main application window.
     */
    private static void createWindow() {
        final JFrame frame = new JFrame("Trivia Maze");
        final JLabel welcomeLabel = new JLabel(
                "Welcome to Trivia Maze!",
                SwingConstants.CENTER);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(createMenuBar(frame));
        frame.add(welcomeLabel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the menu bar for the application.
     *
     * @param theFrame the main application frame
     * @return the completed menu bar
     */
    private static JMenuBar createMenuBar(final JFrame theFrame) {
        final JMenuBar menuBar = new JMenuBar();

        menuBar.add(createHelpMenu(theFrame));

        return menuBar;
    }

    /**
     * Creates the Help menu.
     *
     * @param theFrame the main application frame
     * @return the Help menu
     */
    private static JMenu createHelpMenu(final JFrame theFrame) {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem instructionsItem = new JMenuItem("Game Play Instructions");
        final JMenuItem aboutItem = new JMenuItem("About");

        instructionsItem.addActionListener(theEvent -> showInstructions(theFrame));
        aboutItem.addActionListener(theEvent -> showAbout(theFrame));

        helpMenu.add(instructionsItem);
        helpMenu.add(aboutItem);

        return helpMenu;
    }

    /**
     * Displays the game play instructions.
     *
     * @param theFrame the parent frame
     */
    private static void showInstructions(final JFrame theFrame) {
        final String message = "Trivia Maze Game Play Instructions\n\n"
                + "Move through the maze using the available directions: "
                + "North, South, East, and West.\n\n"
                + "Each door has one trivia question attached to it. "
                + "To pass through a door, answer its question correctly.\n\n"
                + "Question types include multiple choice, true/false, "
                + "and short answer.\n\n"
                + "A correct answer allows you to move through the door. "
                + "An incorrect answer permanently locks the door.\n\n"
                + "You win by reaching the exit room. You lose if all valid "
                + "paths to the exit become blocked by locked doors.";

        JOptionPane.showMessageDialog(
                theFrame,
                message,
                "Game Play Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays information about the application.
     *
     * @param theFrame the parent frame
     */
    private static void showAbout(final JFrame theFrame) {
        final String message = "Trivia Maze\n"
                + "Version 1.0\n\n"
                + "Team Members:\n"
                + "Suhayr Hassan\n"
                + "Jinal Thummar\n"
                + "Roman Pavlyshyn";

        JOptionPane.showMessageDialog(
                theFrame,
                message,
                "About Trivia Maze",
                JOptionPane.INFORMATION_MESSAGE);
    }
}