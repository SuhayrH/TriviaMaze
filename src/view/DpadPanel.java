/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * DpadPanel displays the directional pad used to navigate the maze.
 * It contains four arrow buttons (north, south, east, west) and a
 * center nub. When a button is clicked, it calls the provided move
 * callback with the direction string.
 *
 * @author Suhayr Hassan
 * @version 24 May 2026
 */
public class DpadPanel extends JPanel {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** D-pad icon size in pixels. */
    private static final int DPAD_ICON_SIZE = 55;

    /** D-pad button size in pixels. */
    private static final int DPAD_BUTTON_SIZE = 62;

    /** Up button image path. */
    private static final String DPAD_UP_IMAGE = "src/sprites/up.png";

    /** Down button image path. */
    private static final String DPAD_DOWN_IMAGE = "src/sprites/down.png";

    /** Left button image path. */
    private static final String DPAD_LEFT_IMAGE = "src/sprites/left.png";

    /** Right button image path. */
    private static final String DPAD_RIGHT_IMAGE = "src/sprites/right.png";

    /** Sand color for button background. */
    private static final Color SAND = new Color(232, 208, 160);

    /** Light sand color for hover state. */
    private static final Color SAND_LIGHT = new Color(250, 238, 200);

    /** Dark sand color for nub. */
    private static final Color SAND_DARK = new Color(212, 184, 112);

    /** Gold color for panel background. */
    private static final Color GOLD = new Color(240, 216, 152);

    /** Dark gold color for borders. */
    private static final Color GOLD_DARK = new Color(200, 160, 80);

    /** Callback invoked with the direction string when a button is clicked. */
    private final Consumer<String> myMoveCallback;

    /**
     * Constructs the DpadPanel with the given movement callback.
     *
     * @param theMoveCallback called with "north", "south", "east", or "west"
     *                        when the corresponding button is clicked
     */
    public DpadPanel(final Consumer<String> theMoveCallback) {
        super(new GridBagLayout());

        myMoveCallback = theMoveCallback;

        setBackground(GOLD);
        setBorder(BorderFactory.createLineBorder(GOLD_DARK, 3));

        buildLayout();
    }

    /**
     * Builds the d-pad button layout.
     */
    private void buildLayout() {
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(4, 4, 4, 4);

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(buildDpadButton(DPAD_UP_IMAGE, "north"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(buildDpadButton(DPAD_LEFT_IMAGE, "west"), constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        add(buildDpadButton(DPAD_RIGHT_IMAGE, "east"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(buildDpadButton(DPAD_DOWN_IMAGE, "south"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(buildNub(), constraints);
    }

    /**
     * Builds a single d-pad directional button.
     *
     * @param theImagePath the path to the button arrow image
     * @param theDirection the direction string passed to the move callback
     * @return the styled directional button
     */
    private JButton buildDpadButton(final String theImagePath,
                                    final String theDirection) {
        final ImageIcon icon = new ImageIcon(
                new ImageIcon(theImagePath).getImage().getScaledInstance(
                        DPAD_ICON_SIZE,
                        DPAD_ICON_SIZE,
                        Image.SCALE_FAST));

        final JButton button = new JButton(icon);

        button.setBackground(SAND);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(DPAD_BUTTON_SIZE, DPAD_BUTTON_SIZE));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> myMoveCallback.accept(theDirection));

        button.addMouseListener(new MouseAdapter() {

            /**
             * Highlights the button on hover.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseEntered(final MouseEvent theEvent) {
                button.setBackground(SAND_LIGHT);
            }

            /**
             * Restores the button background on exit.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseExited(final MouseEvent theEvent) {
                button.setBackground(SAND);
            }
        });

        return button;
    }

    /**
     * Builds the center nub of the d-pad.
     *
     * @return the nub panel
     */
    private JPanel buildNub() {
        final JPanel nub = new JPanel();

        nub.setBackground(SAND_DARK);
        nub.setPreferredSize(new Dimension(DPAD_BUTTON_SIZE, DPAD_BUTTON_SIZE));
        nub.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));

        return nub;
    }
}