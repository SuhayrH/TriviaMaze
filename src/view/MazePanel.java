/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Maze;

/**
 * Displays the maze grid, title, score label, and room info label.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class MazePanel extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The maze model.
     */
    private final Maze myMaze;

    /**
     * The maze grid size.
     */
    private final int mySize;

    /**
     * The grid cell panels.
     */
    private final JPanel[][] myGridCells;

    /**
     * The score label.
     */
    private final JLabel myScoreLabel;

    /**
     * The room info label.
     */
    private final JLabel myRoomInfoLabel;

    /**
     * Constructs the maze panel.
     *
     * @param theMaze the maze model
     * @param theScoreLabel the score label
     * @param theRoomInfoLabel the room info label
     */
    public MazePanel(final Maze theMaze,
                     final JLabel theScoreLabel,
                     final JLabel theRoomInfoLabel) {
        super(new BorderLayout(0, 8));

        myMaze = theMaze;
        mySize = theMaze.getSize();
        myGridCells = new JPanel[mySize][mySize];
        myScoreLabel = theScoreLabel;
        myRoomInfoLabel = theRoomInfoLabel;

        buildPanel();
    }

    /**
     * Builds the map panel layout.
     */
    private void buildPanel() {
        final JPanel header = new JPanel(new BorderLayout());
        final JLabel title = new JLabel("TRIVIA MAZE");
        final JPanel grid = new JPanel(new GridLayout(mySize, mySize, 4, 4));

        setBackground(GameColors.SKY_LIGHT);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.SAND_DARK, 3),
                new EmptyBorder(10, 10, 10, 10)));

        header.setBackground(GameColors.SKY_LIGHT);
        title.setFont(GameColors.MONO_EXTRA_LARGE);
        title.setForeground(GameColors.BROWN);

        header.add(title, BorderLayout.WEST);
        header.add(myRoomInfoLabel, BorderLayout.CENTER);
        header.add(myScoreLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        grid.setBackground(GameColors.SKY_LIGHT);

        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                final JPanel cell = new JPanel(new GridBagLayout());

                cell.setBackground(GameColors.SAND);
                cell.setBorder(BorderFactory.createLineBorder(GameColors.SAND_DARK, 3));
                myGridCells[row][col] = cell;
                grid.add(cell);
            }
        }

        add(grid, BorderLayout.CENTER);
    }

    /**
     * Returns the grid cell at the given row and column.
     *
     * @param theRow the row index
     * @param theCol the column index
     * @return the grid cell panel
     */
    public JPanel getCell(final int theRow, final int theCol) {
        return myGridCells[theRow][theCol];
    }

    /**
     * Returns the maze size.
     *
     * @return the size
     */
    public int getMazeSize() {
        return mySize;
    }
}