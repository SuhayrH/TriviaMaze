/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Displays the character selection bar at the bottom of the game window.
 * Allows the player to pick a character and start the game.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class CharacterPanel extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No selected character sentinel value.
     */
    private static final int NO_SELECTED_CHARACTER = -1;

    /**
     * Number of selectable characters.
     */
    private static final int CHARACTER_COUNT = 3;

    /**
     * Character icon size for the selection bar.
     */
    private static final int CHARACTER_ICON_SIZE = 80;

    /**
     * Character card width.
     */
    private static final int CHARACTER_CARD_WIDTH = 110;

    /**
     * Character card height.
     */
    private static final int CHARACTER_CARD_HEIGHT = 110;

    /**
     * Character names.
     */
    private static final String[] CHARACTER_NAMES = {"Warrior", "Mage", "Rogue"};

    /**
     * Character image paths.
     */
    private static final String[] CHARACTER_IMAGES = {
            "src/sprites/warrior.png",
            "src/sprites/mage.png",
            "src/sprites/rogue.png"
    };

    /**
     * The currently selected character index.
     */
    private int mySelectedCharacter;

    /**
     * Whether the game has started.
     */
    private boolean myGameStarted;

    /**
     * The character selection cards.
     */
    private final JPanel[] myCharacterCards;

    /**
     * The character icons.
     */
    private final ImageIcon[] myCharacterIcons;

    /**
     * The action to run when a character is selected.
     */
    private final java.util.function.Consumer<Integer> mySelectAction;

    /**
     * The action to run when start is pressed.
     */
    private final Runnable myStartAction;

    /**
     * Constructs the character panel.
     *
     * @param theSelectAction the action to run on character selection
     * @param theStartAction the action to run on start
     */
    public CharacterPanel(final java.util.function.Consumer<Integer> theSelectAction,
                          final Runnable theStartAction) {
        super(new BorderLayout(0, 6));

        mySelectedCharacter = NO_SELECTED_CHARACTER;
        myGameStarted = false;
        myCharacterCards = new JPanel[CHARACTER_COUNT];
        myCharacterIcons = new ImageIcon[CHARACTER_COUNT];
        mySelectAction = theSelectAction;
        myStartAction = theStartAction;

        loadCharacterIcons();
        buildPanel();
    }

    /**
     * Loads and scales character icons.
     */
    private void loadCharacterIcons() {
        for (int i = 0; i < CHARACTER_COUNT; i++) {
            final ImageIcon rawIcon = new ImageIcon(CHARACTER_IMAGES[i]);

            myCharacterIcons[i] = new ImageIcon(rawIcon.getImage().getScaledInstance(
                    CHARACTER_ICON_SIZE,
                    CHARACTER_ICON_SIZE,
                    Image.SCALE_FAST));
        }
    }

    /**
     * Builds and lays out the panel.
     */
    private void buildPanel() {
        setBackground(GameColors.GOLD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.GOLD_BORDER, 3),
                new EmptyBorder(10, 12, 10, 12)));

        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
        title.setFont(GameColors.MONO_BOLD);
        title.setForeground(GameColors.BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        add(buildCharacterCardPanel(), BorderLayout.CENTER);
        add(buildStartButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Builds the character card panel.
     *
     * @return the panel containing all character cards
     */
    private JPanel buildCharacterCardPanel() {
        final JPanel cards = new JPanel(new GridLayout(1, CHARACTER_COUNT, 12, 0));

        cards.setBackground(GameColors.GOLD);

        for (int i = 0; i < CHARACTER_COUNT; i++) {
            cards.add(buildCharacterCard(i));
        }

        return cards;
    }

    /**
     * Builds a single character card.
     *
     * @param theIndex the character index
     * @return the character card panel
     */
    private JPanel buildCharacterCard(final int theIndex) {
        final JPanel card = new JPanel(new GridBagLayout());
        final JLabel sprite = new JLabel(myCharacterIcons[theIndex]);
        final MouseAdapter listener = buildCharacterMouseListener(card, theIndex);

        card.setBackground(theIndex == mySelectedCharacter ? GameColors.SAND_LIGHT : GameColors.SAND);
        card.setBorder(getCharacterCardBorder(theIndex));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(CHARACTER_CARD_WIDTH, CHARACTER_CARD_HEIGHT));

        sprite.setHorizontalAlignment(SwingConstants.CENTER);
        sprite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.add(sprite);
        card.addMouseListener(listener);
        sprite.addMouseListener(listener);

        myCharacterCards[theIndex] = card;

        return card;
    }

    /**
     * Builds a mouse listener for a character card.
     *
     * @param theCard the card panel
     * @param theIndex the character index
     * @return the mouse listener
     */
    private MouseAdapter buildCharacterMouseListener(final JPanel theCard,
                                                     final int theIndex) {
        return new MouseAdapter() {

            /**
             * Selects the character on click.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseClicked(final MouseEvent theEvent) {
                mySelectedCharacter = theIndex;
                mySelectAction.accept(theIndex);
                refresh();
            }

            /**
             * Highlights the card on hover.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseEntered(final MouseEvent theEvent) {
                theCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3),
                        new EmptyBorder(8, 8, 8, 8)));
            }

            /**
             * Restores the card border on exit.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseExited(final MouseEvent theEvent) {
                theCard.setBorder(getCharacterCardBorder(theIndex));
            }
        };
    }

    /**
     * Returns the border for a character card.
     *
     * @param theIndex the character index
     * @return the border
     */
    private Border getCharacterCardBorder(final int theIndex) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        theIndex == mySelectedCharacter
                                ? GameColors.GOLD_BORDER
                                : GameColors.SAND_DARK, 3),
                new EmptyBorder(8, 8, 8, 8));
    }

    /**
     * Builds the start button panel.
     *
     * @return the start button panel
     */
    private JPanel buildStartButtonPanel() {
        final JPanel buttonWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonWrap.setBackground(GameColors.GOLD);
        buttonWrap.add(buildStartButton());

        return buttonWrap;
    }

    /**
     * Builds the start button.
     *
     * @return the start button
     */
    private JButton buildStartButton() {
        final String buttonText = myGameStarted ? "[ RESTART ]" : "[ START GAME ]";
        final JButton button = new JButton(buttonText);

        button.setFont(GameColors.MONO_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(GameColors.GREEN_BUTTON);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(GameColors.GREEN_BUTTON_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> myStartAction.run());

        return button;
    }

    /**
     * Refreshes the character bar to reflect current selection state.
     */
    public void refresh() {
        removeAll();
        buildPanel();
        revalidate();
        repaint();
    }

    /**
     * Sets the game started state and refreshes the bar.
     *
     * @param theGameStarted whether the game has started
     */
    public void setGameStarted(final boolean theGameStarted) {
        myGameStarted = theGameStarted;
        refresh();
    }

    /**
     * Sets the selected character index.
     *
     * @param theIndex the selected character index
     */
    public void setSelectedCharacter(final int theIndex) {
        mySelectedCharacter = theIndex;
    }

    /**
     * Returns the character name for the given index.
     *
     * @param theIndex the character index
     * @return the character name
     */
    public static String getCharacterName(final int theIndex) {
        return CHARACTER_NAMES[theIndex];
    }
}