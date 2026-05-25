/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * RoomFlasher briefly flashes a maze grid cell a given color
 * to provide visual feedback for correct and incorrect answers.
 *
 * @author Suhayr Hassan
 * @version 24 May 2026
 */
public class RoomFlasher {

    /** Duration of the flash in milliseconds. */
    private static final int FLASH_DURATION = 400;

    /** Green color used for a correct answer flash. */
    public static final Color COLOR_CORRECT = new Color(100, 200, 100);

    /** Red color used for a wrong answer flash. */
    public static final Color COLOR_WRONG = new Color(200, 80, 80);

    /** The grid of room cell panels to flash. */
    private final JPanel[][] myGridCells;

    /**
     * Constructs a RoomFlasher for the given maze grid.
     *
     * @param theGridCells the 2D array of room cell panels from MazeGUI
     */
    public RoomFlasher(final JPanel[][] theGridCells) {
        myGridCells = theGridCells;
    }

    /**
     * Flashes the cell at the given row and column with the correct answer color.
     *
     * @param theRow the row of the cell to flash
     * @param theCol the column of the cell to flash
     */
    public void flashCorrect(final int theRow, final int theCol) {
        flash(theRow, theCol, COLOR_CORRECT);
    }

    /**
     * Flashes the cell at the given row and column with the wrong answer color.
     *
     * @param theRow the row of the cell to flash
     * @param theCol the column of the cell to flash
     */
    public void flashWrong(final int theRow, final int theCol) {
        flash(theRow, theCol, COLOR_WRONG);
    }

    /**
     * Flashes the cell at the given row and column with the specified color,
     * then reverts it to its original background color after the flash duration.
     *
     * @param theRow   the row of the cell to flash
     * @param theCol   the column of the cell to flash
     * @param theColor the color to flash
     */
    private void flash(final int theRow, final int theCol, final Color theColor) {
        final JPanel cell = myGridCells[theRow][theCol];
        final Color originalColor = cell.getBackground();

        cell.setBackground(theColor);
        cell.repaint();

        final Timer flashTimer = new Timer(FLASH_DURATION, theEvent -> {
            cell.setBackground(originalColor);
            cell.repaint();
        });

        flashTimer.setRepeats(false);
        flashTimer.start();
    }
}