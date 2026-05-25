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
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * CharacterPanel displays the character selection bar at the bottom of the
 * Trivia Maze game window. It shows three selectable character cards and
 * a Start/Restart button. Callbacks are used to notify MazeGUI when a
 * character is selected or the start button is clicked.
 *
 * @author Suhayr Hassan
 * @version 24 May 2026
 */
public class CharacterPanel extends JPanel {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Number of selectable characters. */
    private static final int CHARACTER_COUNT = 3;

    /** Icon size for character selection cards. */
    private static final int CHARACTER_ICON_SIZE = 80;

    /** Character card width. */
    private static final int CHARACTER_CARD_WIDTH = 110;

    /** Character card height. */
    private static final int CHARACTER_CARD_HEIGHT = 110;

    /** Character names. */
    private static final String[] CHARACTER_NAMES = {"Warrior", "Mage", "Rogue"};

    /** Character image paths. */
    private static final String[] CHARACTER_IMAGES = {
            "src/sprites/warrior.png",
            "src/sprites/mage.png",
            "src/sprites/rogue.png"
    };

    /** Sand color for unselected cards. */
    private static final Color SAND = new Color(232, 208, 160);

    /** Light sand color for selected card. */
    private static final Color SAND_LIGHT = new Color(250, 238, 200);

    /** Dark sand color for card borders. */
    private static final Color SAND_DARK = new Color(212, 184, 112);

    /** Gold color for panel background. */
    private static final Color GOLD = new Color(240, 216, 152);

    /** Dark gold color for borders. */
    private static final Color GOLD_DARK = new Color(200, 160, 80);

    /** Gold border color for selected card. */
    private static final Color GOLD_BORDER = new Color(232, 160, 32);

    /** Brown color for text. */
    private static final Color BROWN = new Color(90, 58, 26);

    /** Green button color. */
    private static final Color GREEN_BUTTON = new Color(74, 138, 32);

    /** Dark green button color. */
    private static final Color GREEN_BUTTON_DARK = new Color(58, 106, 24);

    /** Bold monospaced font. */
    private static final Font MONO_BOLD = new Font("Monospaced", Font.BOLD, 12);

    /** Currently selected character index. */
    private int mySelectedCharacter;

    /** Whether the game has started (affects button label). */
    private boolean myGameStarted;

    /** Character card panels. */
    private final JPanel[] myCharacterCards;

    /** Scaled character icons for the selection cards. */
    private final ImageIcon[] myCharacterIcons;

    /** Callback invoked with the selected character index when a card is clicked. */
    private final IntConsumer myOnCharacterSelected;

    /** Callback invoked when the start/restart button is clicked. */
    private final Runnable myOnStartClicked;

    /**
     * Constructs the CharacterPanel.
     *
     * @param theSelectedCharacter  the initially selected character index (-1 for none)
     * @param theGameStarted        whether the game has already started
     * @param theOnCharacterSelected callback invoked with character index on selection
     * @param theOnStartClicked     callback invoked when start/restart is clicked
     */
    public CharacterPanel(final int theSelectedCharacter,
                          final boolean theGameStarted,
                          final IntConsumer theOnCharacterSelected,
                          final Runnable theOnStartClicked) {
        super(new BorderLayout(0, 6));

        mySelectedCharacter = theSelectedCharacter;
        myGameStarted = theGameStarted;
        myOnCharacterSelected = theOnCharacterSelected;
        myOnStartClicked = theOnStartClicked;
        myCharacterCards = new JPanel[CHARACTER_COUNT];
        myCharacterIcons = new ImageIcon[CHARACTER_COUNT];

        setBackground(GOLD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_BORDER, 3),
                new EmptyBorder(10, 12, 10, 12)));

        loadCharacterIcons();
        buildLayout();
    }

    /**
     * Updates the selected character and refreshes the panel.
     *
     * @param theIndex the newly selected character index
     */
    public void setSelectedCharacter(final int theIndex) {
        mySelectedCharacter = theIndex;
        refresh();
    }

    /**
     * Updates whether the game has started and refreshes the start button label.
     *
     * @param theGameStarted true if the game has started
     */
    public void setGameStarted(final boolean theGameStarted) {
        myGameStarted = theGameStarted;
        refresh();
    }

    /**
     * Returns the name of the currently selected character.
     *
     * @return the character name, or null if none selected
     */
    public String getSelectedCharacterName() {
        if (mySelectedCharacter < 0 || mySelectedCharacter >= CHARACTER_COUNT) {
            return null;
        }
        return CHARACTER_NAMES[mySelectedCharacter];
    }

    /**
     * Loads and scales character icons for use in the selection cards.
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
     * Builds the full panel layout with title, cards, and start button.
     */
    private void buildLayout() {
        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");

        title.setFont(MONO_BOLD);
        title.setForeground(BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        add(buildCardPanel(), BorderLayout.CENTER);
        add(buildStartButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Builds the panel containing the three character cards.
     *
     * @return the card panel
     */
    private JPanel buildCardPanel() {
        final JPanel cards = new JPanel(new GridLayout(1, CHARACTER_COUNT, 12, 0));

        cards.setBackground(GOLD);

        for (int i = 0; i < CHARACTER_COUNT; i++) {
            cards.add(buildCharacterCard(i));
        }

        return cards;
    }

    /**
     * Builds a single character selection card.
     *
     * @param theIndex the character index
     * @return the character card panel
     */
    private JPanel buildCharacterCard(final int theIndex) {
        final JPanel card = new JPanel(new GridBagLayout());
        final JLabel sprite = new JLabel(myCharacterIcons[theIndex]);
        final MouseAdapter listener = buildCardMouseListener(card, theIndex);

        card.setBackground(theIndex == mySelectedCharacter ? SAND_LIGHT : SAND);
        card.setBorder(getCardBorder(theIndex));
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
     * @param theCard  the card panel
     * @param theIndex the character index
     * @return the mouse adapter
     */
    private MouseAdapter buildCardMouseListener(final JPanel theCard,
                                                final int theIndex) {
        return new MouseAdapter() {

            /**
             * Selects the character when the card is clicked.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseClicked(final MouseEvent theEvent) {
                myOnCharacterSelected.accept(theIndex);
            }

            /**
             * Highlights the card on hover.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseEntered(final MouseEvent theEvent) {
                theCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GOLD_DARK, 3),
                        new EmptyBorder(8, 8, 8, 8)));
            }

            /**
             * Restores the card border on exit.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseExited(final MouseEvent theEvent) {
                theCard.setBorder(getCardBorder(theIndex));
            }
        };
    }

    /**
     * Returns the appropriate border for a character card based on selection state.
     *
     * @param theIndex the character index
     * @return the card border
     */
    private Border getCardBorder(final int theIndex) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        theIndex == mySelectedCharacter ? GOLD_BORDER : SAND_DARK, 3),
                new EmptyBorder(8, 8, 8, 8));
    }

    /**
     * Builds the start/restart button wrapped in a flow panel.
     *
     * @return the button panel
     */
    private JPanel buildStartButtonPanel() {
        final JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final String label = myGameStarted ? "[ RESTART ]" : "[ START GAME ]";
        final JButton button = new JButton(label);

        button.setFont(MONO_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(GREEN_BUTTON);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(GREEN_BUTTON_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> myOnStartClicked.run());

        wrap.setBackground(GOLD);
        wrap.add(button);

        return wrap;
    }

    /**
     * Refreshes the panel by rebuilding its contents.
     */
    private void refresh() {
        removeAll();
        buildLayout();
        revalidate();
        repaint();
    }
}