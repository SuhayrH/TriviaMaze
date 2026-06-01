/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Displays the directional pad used to navigate the maze.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class DpadPanel extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * D-pad icon size.
     */
    private static final int DPAD_ICON_SIZE = 55;

    /**
     * D-pad button size.
     */
    private static final int DPAD_BUTTON_SIZE = 62;

    /**
     * Up button image path.
     */
    private static final String DPAD_UP_IMAGE = "src/sprites/up.png";

    /**
     * Down button image path.
     */
    private static final String DPAD_DOWN_IMAGE = "src/sprites/down.png";

    /**
     * Left button image path.
     */
    private static final String DPAD_LEFT_IMAGE = "src/sprites/left.png";

    /**
     * Right button image path.
     */
    private static final String DPAD_RIGHT_IMAGE = "src/sprites/right.png";

    /**
     * North direction constant.
     */
    private static final String NORTH = "north";

    /**
     * South direction constant.
     */
    private static final String SOUTH = "south";

    /**
     * East direction constant.
     */
    private static final String EAST = "east";

    /**
     * West direction constant.
     */
    private static final String WEST = "west";

    /**
     * North movement button.
     */
    private JButton myNorthButton;

    /**
     * South movement button.
     */
    private JButton mySouthButton;

    /**
     * East movement button.
     */
    private JButton myEastButton;

    /**
     * West movement button.
     */
    private JButton myWestButton;

    /**
     * Constructs the d-pad panel.
     *
     * @param theMoveAction the action to run when a direction is pressed
     */
    public DpadPanel(final java.util.function.Consumer<String> theMoveAction) {
        super(new GridBagLayout());

        buildPanel(theMoveAction);
    }

    /**
     * Builds and lays out the d-pad buttons.
     *
     * @param theMoveAction the action to run on direction press
     */
    private void buildPanel(final java.util.function.Consumer<String> theMoveAction) {
        final GridBagConstraints constraints = new GridBagConstraints();
        final JPanel nub = new JPanel();

        setBackground(GameColors.GOLD);
        setBorder(BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3));

        myNorthButton = buildDpadButton(DPAD_UP_IMAGE, NORTH, theMoveAction);
        mySouthButton = buildDpadButton(DPAD_DOWN_IMAGE, SOUTH, theMoveAction);
        myEastButton = buildDpadButton(DPAD_RIGHT_IMAGE, EAST, theMoveAction);
        myWestButton = buildDpadButton(DPAD_LEFT_IMAGE, WEST, theMoveAction);

        constraints.insets = new Insets(4, 4, 4, 4);

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(myNorthButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(myWestButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        add(myEastButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(mySouthButton, constraints);

        nub.setBackground(GameColors.SAND_DARK);
        nub.setPreferredSize(new Dimension(DPAD_BUTTON_SIZE, DPAD_BUTTON_SIZE));
        nub.setBorder(BorderFactory.createLineBorder(GameColors.GOLD_DARK, 2));

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(nub, constraints);
    }

    /**
     * Builds a single d-pad button.
     *
     * @param theImagePath the image path for the button icon
     * @param theDirection the direction this button triggers
     * @param theMoveAction the action to run with the direction
     * @return the styled d-pad button
     */
    private JButton buildDpadButton(final String theImagePath,
                                    final String theDirection,
                                    final java.util.function.Consumer<String> theMoveAction) {
        final ImageIcon icon = new ImageIcon(
                new ImageIcon(theImagePath).getImage().getScaledInstance(
                        DPAD_ICON_SIZE,
                        DPAD_ICON_SIZE,
                        Image.SCALE_FAST));
        final JButton button = new JButton(icon);

        button.setBackground(GameColors.SAND);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(GameColors.GOLD_DARK, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(DPAD_BUTTON_SIZE, DPAD_BUTTON_SIZE));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> theMoveAction.accept(theDirection));

        button.addMouseListener(new MouseAdapter() {

            /**
             * Changes the button color when the mouse enters.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseEntered(final MouseEvent theEvent) {
                button.setBackground(GameColors.SAND_LIGHT);
            }

            /**
             * Changes the button color when the mouse exits.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseExited(final MouseEvent theEvent) {
                button.setBackground(GameColors.SAND);
            }
        });

        return button;
    }

    /**
     * Returns the north button.
     *
     * @return the north button
     */
    public JButton getNorthButton() {
        return myNorthButton;
    }

    /**
     * Returns the south button.
     *
     * @return the south button
     */
    public JButton getSouthButton() {
        return mySouthButton;
    }

    /**
     * Returns the east button.
     *
     * @return the east button
     */
    public JButton getEastButton() {
        return myEastButton;
    }

    /**
     * Returns the west button.
     *
     * @return the west button
     */
    public JButton getWestButton() {
        return myWestButton;
    }
}