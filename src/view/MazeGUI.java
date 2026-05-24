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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import model.Database;
import model.Door;
import model.GameMemento;
import model.Maze;
import model.MultipleChoiceQuestion;
import model.Question;
import model.QuestionFactory;
import model.Room;
import model.TrueFalseQuestion;

/**
 * The main game panel for the Trivia Maze game.
 * This class displays the maze, character selection, question area,
 * answer input, score, menus, navigation controls, and hint support.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 24 May 2026
 */
public class MazeGUI extends JPanel {

    /**
     * Serial version UID for the Swing panel.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No selected character value.
     */
    private static final int NO_SELECTED_CHARACTER = -1;

    /**
     * Number of selectable characters.
     */
    private static final int CHARACTER_COUNT = 3;

    /**
     * Default maze size.
     */
    private static final int MAZE_SIZE = 4;

    /**
     * Points awarded per correct answer.
     */
    private static final int POINTS_PER_CORRECT_ANSWER = 100;

    /**
     * Small icon size for character selection.
     */
    private static final int CHARACTER_ICON_SIZE = 80;

    /**
     * Character icon size used in the maze grid.
     */
    private static final int GRID_CHARACTER_ICON_SIZE = 48;

    /**
     * D-pad icon size.
     */
    private static final int DPAD_ICON_SIZE = 55;

    /**
     * D-pad button size.
     */
    private static final int DPAD_BUTTON_SIZE = 62;

    /**
     * Character card width.
     */
    private static final int CHARACTER_CARD_WIDTH = 110;

    /**
     * Character card height.
     */
    private static final int CHARACTER_CARD_HEIGHT = 110;

    /**
     * Main window width.
     */
    private static final int WINDOW_WIDTH = 980;

    /**
     * Main window height.
     */
    private static final int WINDOW_HEIGHT = 780;

    /**
     * First character position used for hints.
     */
    private static final int FIRST_CHARACTER_INDEX = 0;

    /**
     * Sky blue color.
     */
    private static final Color SKY_BLUE = new Color(91, 163, 217);

    /**
     * Light sky color.
     */
    private static final Color SKY_LIGHT = new Color(135, 206, 235);

    /**
     * Sand color.
     */
    private static final Color SAND = new Color(232, 208, 160);

    /**
     * Light sand color.
     */
    private static final Color SAND_LIGHT = new Color(250, 238, 200);

    /**
     * Dark sand color.
     */
    private static final Color SAND_DARK = new Color(212, 184, 112);

    /**
     * Gold color.
     */
    private static final Color GOLD = new Color(240, 216, 152);

    /**
     * Dark gold color.
     */
    private static final Color GOLD_DARK = new Color(200, 160, 80);

    /**
     * Gold border color.
     */
    private static final Color GOLD_BORDER = new Color(232, 160, 32);

    /**
     * Brown color.
     */
    private static final Color BROWN = new Color(90, 58, 26);

    /**
     * Red border color.
     */
    private static final Color RED_BORDER = new Color(170, 32, 32);

    /**
     * Exit cell color.
     */
    private static final Color BLUE_CELL = new Color(251, 243, 170, 255);

    /**
     * Exit border color.
     */
    private static final Color BLUE_BORDER = new Color(32, 96, 192);

    /**
     * Green button color.
     */
    private static final Color GREEN_BUTTON = new Color(74, 138, 32);

    /**
     * Dark green button color.
     */
    private static final Color GREEN_BUTTON_DARK = new Color(58, 106, 24);

    /**
     * Dark text color.
     */
    private static final Color TEXT_DARK = new Color(58, 42, 16);

    /**
     * Medium text color.
     */
    private static final Color TEXT_MID = new Color(138, 106, 48);

    /**
     * Small monospaced font.
     */
    private static final Font MONO_SMALL = new Font("Monospaced", Font.PLAIN, 10);

    /**
     * Medium monospaced font.
     */
    private static final Font MONO_MEDIUM = new Font("Monospaced", Font.PLAIN, 12);

    /**
     * Bold monospaced font.
     */
    private static final Font MONO_BOLD = new Font("Monospaced", Font.BOLD, 12);

    /**
     * Large monospaced font.
     */
    private static final Font MONO_LARGE = new Font("Monospaced", Font.BOLD, 22);

    /**
     * Extra large monospaced font.
     */
    private static final Font MONO_EXTRA_LARGE = new Font("Monospaced", Font.BOLD, 16);

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
     * Maze model.
     */
    private Maze myMaze;

    /**
     * Maze size.
     */
    private final int mySize;

    /**
     * Currently selected character index.
     */
    private int mySelectedCharacter;

    /**
     * Whether the game has started.
     */
    private boolean myGameStarted;

    /**
     * Current selected door.
     */
    private Door myCurrentDoor;

    /**
     * Current movement direction.
     */
    private String myCurrentDirection;

    /**
     * Correct answer count.
     */
    private int myCorrectCount;

    /**
     * Whether a hint has already been used for the current question.
     */
    private boolean myHintUsedForQuestion;

    /**
     * Grid cell panels.
     */
    private final JPanel[][] myGridCells;

    /**
     * Character selection cards.
     */
    private final JPanel[] myCharacterCards;

    /**
     * Character icons for selection.
     */
    private final ImageIcon[] myCharacterIcons;

    /**
     * Character icons for maze grid.
     */
    private final ImageIcon[] myCharacterGridIcons;

    /**
     * Question text area.
     */
    private final JTextArea myQuestionText;

    /**
     * Answer input field.
     */
    private final JTextField myAnswerField;

    /**
     * Submit button.
     */
    private final JButton mySubmitButton;

    /**
     * Hint button.
     */
    private final JButton myHintButton;

    /**
     * Feedback label.
     */
    private final JLabel myFeedbackLabel;

    /**
     * Score label.
     */
    private final JLabel myScoreLabel;

    /**
     * Constructs the maze GUI panel.
     *
     * @param theMaze the maze model
     */
    public MazeGUI(final Maze theMaze) {
        super(new BorderLayout());

        myMaze = theMaze;
        mySize = theMaze.getSize();
        mySelectedCharacter = NO_SELECTED_CHARACTER;
        myGameStarted = false;
        myCurrentDoor = null;
        myCurrentDirection = null;
        myCorrectCount = 0;
        myHintUsedForQuestion = false;
        myGridCells = new JPanel[mySize][mySize];
        myCharacterCards = new JPanel[CHARACTER_COUNT];
        myCharacterIcons = new ImageIcon[CHARACTER_COUNT];
        myCharacterGridIcons = new ImageIcon[CHARACTER_COUNT];

        setBackground(SKY_BLUE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        loadCharacterIcons();

        myQuestionText = buildQuestionArea();
        myAnswerField = buildAnswerField();
        mySubmitButton = buildSubmitButton();
        myHintButton = buildHintButton();

        myFeedbackLabel = new JLabel("> pick a character and press start");
        myFeedbackLabel.setFont(MONO_SMALL);
        myFeedbackLabel.setForeground(TEXT_MID);

        myScoreLabel = new JLabel("SCORE: 0000");
        myScoreLabel.setFont(MONO_BOLD);
        myScoreLabel.setForeground(BROWN);
        myScoreLabel.setBackground(GOLD);
        myScoreLabel.setOpaque(true);
        myScoreLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_DARK, 3),
                new EmptyBorder(2, 8, 2, 8)));

        add(buildMainPanel(), BorderLayout.CENTER);
        add(buildCharacterBar(), BorderLayout.SOUTH);

        updateGrid();
    }

    /**
     * Creates and displays the main game window.
     */
    public static void createAndShowGUI() {
        Database.init();

        final QuestionFactory factory = new QuestionFactory("trivia.db");
        final Maze maze = new Maze(MAZE_SIZE);
        maze.initializeDoors(factory);

        final JFrame frame = new JFrame("Trivia Maze");
        final MazeGUI gamePanel = new MazeGUI(maze);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.setBackground(SKY_BLUE);
        frame.setJMenuBar(buildMenuBar(frame, gamePanel));
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Builds the menu bar.
     *
     * @param theFrame the parent frame
     * @param theGamePanel the game panel
     * @return the menu bar
     */
    private static JMenuBar buildMenuBar(final JFrame theFrame,
                                         final MazeGUI theGamePanel) {
        final JMenuBar bar = new JMenuBar();
        final JMenu fileMenu = styledMenu("File");
        final JMenu helpMenu = styledMenu("Help");
        final JMenuItem saveItem = styledItem("Save Game");
        final JMenuItem loadItem = styledItem("Load Game");
        final JMenuItem exitItem = styledItem("Exit");
        final JMenuItem aboutItem = styledItem("About");
        final JMenuItem howItem = styledItem("Game Play Instructions");

        bar.setBackground(GOLD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, GOLD_DARK));

        saveItem.addActionListener(theEvent -> theGamePanel.saveGame(theFrame));
        loadItem.addActionListener(theEvent -> theGamePanel.loadGame(theFrame));
        exitItem.addActionListener(theEvent -> exitGame(theFrame));

        aboutItem.addActionListener(theEvent -> JOptionPane.showMessageDialog(theFrame,
                "Trivia Maze v1.0\nTCSS 360 - Spring 2026\n\n"
                        + "Suhayr Hassan\nJinal Thummar\nRoman Pavlyshyn",
                "About",
                JOptionPane.INFORMATION_MESSAGE));

        howItem.addActionListener(theEvent -> JOptionPane.showMessageDialog(theFrame,
                "1. Choose your character at the bottom.\n"
                        + "2. Press START GAME.\n"
                        + "3. Use the arrow pad to move between rooms.\n"
                        + "4. Answer trivia questions to unlock doors.\n"
                        + "5. Use HINT for help during a question.\n"
                        + "6. Wrong answer = door locked permanently.\n"
                        + "7. Reach the exit (*) to win.\n"
                        + "8. If all paths are blocked, game over.",
                "How to Play",
                JOptionPane.INFORMATION_MESSAGE));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        helpMenu.add(aboutItem);
        helpMenu.add(howItem);

        bar.add(fileMenu);
        bar.add(helpMenu);

        return bar;
    }

    /**
     * Builds a styled menu.
     *
     * @param theText the menu text
     * @return the styled menu
     */
    private static JMenu styledMenu(final String theText) {
        final JMenu menu = new JMenu(theText);
        menu.setFont(MONO_BOLD);
        menu.setForeground(BROWN);
        return menu;
    }

    /**
     * Builds a styled menu item.
     *
     * @param theText the menu item text
     * @return the styled menu item
     */
    private static JMenuItem styledItem(final String theText) {
        final JMenuItem item = new JMenuItem(theText);
        item.setFont(MONO_MEDIUM);
        item.setBackground(GOLD);
        item.setForeground(TEXT_DARK);
        return item;
    }

    /**
     * Exits the game after confirmation.
     *
     * @param theFrame the parent frame
     */
    private static void exitGame(final JFrame theFrame) {
        final int choice = JOptionPane.showConfirmDialog(theFrame,
                "Exit without saving?",
                "Exit",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            theFrame.dispose();
        }
    }

    /**
     * Entry point to launch the Trivia Maze GUI.
     *
     * @param theArgs command-line arguments
     */
    public static void main(final String[] theArgs) {
        SwingUtilities.invokeLater(MazeGUI::createAndShowGUI);
    }

    /**
     * Loads and scales character images.
     */
    private void loadCharacterIcons() {
        for (int i = 0; i < CHARACTER_COUNT; i++) {
            final ImageIcon rawIcon = new ImageIcon(CHARACTER_IMAGES[i]);

            myCharacterIcons[i] = new ImageIcon(rawIcon.getImage().getScaledInstance(
                    CHARACTER_ICON_SIZE,
                    CHARACTER_ICON_SIZE,
                    Image.SCALE_FAST));

            myCharacterGridIcons[i] = new ImageIcon(rawIcon.getImage().getScaledInstance(
                    GRID_CHARACTER_ICON_SIZE,
                    GRID_CHARACTER_ICON_SIZE,
                    Image.SCALE_FAST));
        }
    }

    /**
     * Builds the main panel.
     *
     * @return the main panel
     */
    private JPanel buildMainPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(SKY_BLUE);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(4, 4, 4, 4);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weightx = 0.65;
        constraints.weighty = 1.0;
        panel.add(buildMapPanel(), constraints);

        constraints.gridx = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.35;

        constraints.gridy = 0;
        constraints.weighty = 0.65;
        panel.add(buildQuestionPanel(), constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.35;
        panel.add(buildDpadPanel(), constraints);

        return panel;
    }

    /**
     * Builds the map panel.
     *
     * @return the map panel
     */
    private JPanel buildMapPanel() {
        final JPanel wrap = new JPanel(new BorderLayout(0, 8));
        final JPanel header = new JPanel(new BorderLayout());
        final JLabel title = new JLabel("TRIVIA MAZE");
        final JPanel grid = new JPanel(new GridLayout(mySize, mySize, 4, 4));

        wrap.setBackground(SKY_LIGHT);
        wrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SAND_DARK, 3),
                new EmptyBorder(10, 10, 10, 10)));

        header.setBackground(SKY_LIGHT);

        title.setFont(MONO_EXTRA_LARGE);
        title.setForeground(BROWN);

        header.add(title, BorderLayout.WEST);
        header.add(myScoreLabel, BorderLayout.EAST);
        wrap.add(header, BorderLayout.NORTH);

        grid.setBackground(SKY_LIGHT);

        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                final JPanel cell = new JPanel(new GridBagLayout());

                cell.setBackground(SAND);
                cell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
                myGridCells[row][col] = cell;
                grid.add(cell);
            }
        }

        wrap.add(grid, BorderLayout.CENTER);

        return wrap;
    }

    /**
     * Builds the question panel.
     *
     * @return the question panel
     */
    private JPanel buildQuestionPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        final JLabel questionTag = new JLabel("QUESTION");
        final JScrollPane scroll = new JScrollPane(myQuestionText);
        final JLabel answerTag = new JLabel("YOUR ANSWER");

        panel.setBackground(GOLD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_DARK, 3),
                new EmptyBorder(10, 12, 10, 12)));

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(4, 0, 4, 0);

        constraints.gridy = 0;
        questionTag.setFont(MONO_BOLD);
        questionTag.setForeground(TEXT_MID);
        panel.add(questionTag, constraints);

        constraints.gridy = 1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        scroll.setBorder(BorderFactory.createLineBorder(SAND_DARK, 2));
        scroll.getViewport().setBackground(SAND_LIGHT);
        panel.add(scroll, constraints);

        constraints.gridy = 2;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        answerTag.setFont(MONO_BOLD);
        answerTag.setForeground(TEXT_MID);
        panel.add(answerTag, constraints);

        constraints.gridy = 3;
        panel.add(myAnswerField, constraints);

        constraints.gridy = 4;
        panel.add(buildAnswerButtonPanel(), constraints);

        constraints.gridy = 5;
        panel.add(myFeedbackLabel, constraints);

        return panel;
    }

    /**
     * Builds the question text area.
     *
     * @return the question text area
     */
    private JTextArea buildQuestionArea() {
        final JTextArea textArea = new JTextArea(5, 20);

        textArea.setFont(MONO_MEDIUM);
        textArea.setForeground(TEXT_DARK);
        textArea.setBackground(SAND_LIGHT);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new EmptyBorder(6, 8, 6, 8));
        textArea.setText("Choose your character below, then press START to begin!");

        return textArea;
    }

    /**
     * Builds the answer field.
     *
     * @return the answer field
     */
    private JTextField buildAnswerField() {
        final JTextField textField = new JTextField();

        textField.setFont(MONO_MEDIUM);
        textField.setForeground(TEXT_DARK);
        textField.setBackground(SAND_LIGHT);
        textField.setCaretColor(BROWN);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_BORDER, 3),
                new EmptyBorder(5, 8, 5, 8)));
        textField.addActionListener(theEvent -> submitAnswer());

        return textField;
    }

    /**
     * Builds the submit button.
     *
     * @return the submit button
     */
    private JButton buildSubmitButton() {
        final JButton button = new JButton("[ SUBMIT ]");

        button.setFont(MONO_BOLD);
        button.setForeground(new Color(240, 248, 224));
        button.setBackground(GREEN_BUTTON);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(GREEN_BUTTON_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> submitAnswer());

        return button;
    }

    /**
     * Builds the hint button.
     *
     * @return the hint button
     */
    private JButton buildHintButton() {
        final JButton button = new JButton("[ HINT ]");

        button.setFont(MONO_BOLD);
        button.setForeground(new Color(240, 248, 224));
        button.setBackground(GOLD_BORDER);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> showHint());

        return button;
    }

    /**
     * Builds the panel containing the submit and hint buttons.
     *
     * @return the answer button panel
     */
    private JPanel buildAnswerButtonPanel() {
        final JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 6, 0));

        buttonPanel.setBackground(GOLD);
        buttonPanel.add(mySubmitButton);
        buttonPanel.add(myHintButton);

        return buttonPanel;
    }

    /**
     * Builds the d-pad panel.
     *
     * @return the d-pad panel
     */
    private JPanel buildDpadPanel() {
        final JPanel wrap = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        final JPanel nub = new JPanel();

        wrap.setBackground(GOLD);
        wrap.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 3));

        constraints.insets = new Insets(4, 4, 4, 4);

        constraints.gridx = 1;
        constraints.gridy = 0;
        wrap.add(buildDpadButton(DPAD_UP_IMAGE, "north"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        wrap.add(buildDpadButton(DPAD_LEFT_IMAGE, "west"), constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        wrap.add(buildDpadButton(DPAD_RIGHT_IMAGE, "east"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        wrap.add(buildDpadButton(DPAD_DOWN_IMAGE, "south"), constraints);

        nub.setBackground(SAND_DARK);
        nub.setPreferredSize(new Dimension(DPAD_BUTTON_SIZE, DPAD_BUTTON_SIZE));
        nub.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));

        constraints.gridx = 1;
        constraints.gridy = 1;
        wrap.add(nub, constraints);

        return wrap;
    }

    /**
     * Builds a d-pad button.
     *
     * @param theImagePath the button image path
     * @param theDirection the movement direction
     * @return the d-pad button
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
        button.addActionListener(theEvent -> handleMove(theDirection));

        button.addMouseListener(new MouseAdapter() {

            /**
             * Changes the button color when the mouse enters.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseEntered(final MouseEvent theEvent) {
                button.setBackground(SAND_LIGHT);
            }

            /**
             * Changes the button color when the mouse exits.
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
     * Builds the character selection bar.
     *
     * @return the character selection bar
     */
    private JPanel buildCharacterBar() {
        final JPanel bar = new JPanel(new BorderLayout(0, 6));
        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
        final JPanel cards = new JPanel(new GridLayout(1, CHARACTER_COUNT, 12, 0));
        final JButton startButton = buildStartButton();
        final JPanel buttonWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));

        bar.setBackground(GOLD);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_BORDER, 3),
                new EmptyBorder(10, 12, 10, 12)));

        title.setFont(MONO_BOLD);
        title.setForeground(BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        bar.add(title, BorderLayout.NORTH);

        cards.setBackground(GOLD);

        for (int i = 0; i < CHARACTER_COUNT; i++) {
            cards.add(buildCharacterCard(i));
        }

        bar.add(cards, BorderLayout.CENTER);

        buttonWrap.setBackground(GOLD);
        buttonWrap.add(startButton);
        bar.add(buttonWrap, BorderLayout.SOUTH);

        return bar;
    }

    /**
     * Builds the start button.
     *
     * @return the start button
     */
    private JButton buildStartButton() {
        final String buttonText = myGameStarted ? "[ RESTART ]" : "[ START GAME ]";
        final JButton startButton = new JButton(buttonText);

        startButton.setFont(MONO_BOLD);
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(GREEN_BUTTON);
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(GREEN_BUTTON_DARK, 3));
        startButton.setFocusPainted(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(theEvent -> startGame());

        return startButton;
    }

    /**
     * Builds a character card.
     *
     * @param theIndex the character index
     * @return the character card
     */
    private JPanel buildCharacterCard(final int theIndex) {
        final JPanel card = new JPanel(new GridBagLayout());
        final JLabel sprite = new JLabel(myCharacterIcons[theIndex]);
        final int index = theIndex;
        final MouseAdapter characterMouseListener = buildCharacterMouseListener(card, index);

        card.setBackground(theIndex == mySelectedCharacter ? SAND_LIGHT : SAND);
        card.setBorder(getCharacterCardBorder(theIndex));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(CHARACTER_CARD_WIDTH, CHARACTER_CARD_HEIGHT));

        sprite.setHorizontalAlignment(SwingConstants.CENTER);
        sprite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.add(sprite);
        card.addMouseListener(characterMouseListener);
        sprite.addMouseListener(characterMouseListener);

        myCharacterCards[theIndex] = card;

        return card;
    }

    /**
     * Builds a mouse listener for a character card.
     *
     * @param theCard the character card
     * @param theIndex the character index
     * @return the mouse listener
     */
    private MouseAdapter buildCharacterMouseListener(final JPanel theCard,
                                                    final int theIndex) {
        return new MouseAdapter() {

            /**
             * Selects the clicked character.
             *
             * @param theEvent the mouse event
             */
            @Override
            public void mouseClicked(final MouseEvent theEvent) {
                selectCharacter(theIndex);
            }

            /**
             * Highlights the character card.
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
             * Restores the character card border.
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
     * Gets the border for a character card.
     *
     * @param theIndex the character index
     * @return the border
     */
    private Border getCharacterCardBorder(final int theIndex) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        theIndex == mySelectedCharacter ? GOLD_BORDER : SAND_DARK, 3),
                new EmptyBorder(8, 8, 8, 8));
    }

    /**
     * Selects a character.
     *
     * @param theIndex the selected character index
     */
    private void selectCharacter(final int theIndex) {
        mySelectedCharacter = theIndex;
        refreshCharacterBar();
        updateGrid();
        setFeedback("selected " + CHARACTER_NAMES[mySelectedCharacter] + ".", TEXT_DARK);
        myQuestionText.setText("You selected " + CHARACTER_NAMES[mySelectedCharacter]
                + ".\n\nPress START GAME to begin.");
    }

    /**
     * Refreshes the character bar.
     */
    private void refreshCharacterBar() {
        final JPanel bar = (JPanel) getComponent(1);

        bar.removeAll();
        bar.add(buildCharacterBarContentTitle(), BorderLayout.NORTH);
        bar.add(buildCharacterCardPanel(), BorderLayout.CENTER);
        bar.add(buildStartButtonPanel(), BorderLayout.SOUTH);
        bar.revalidate();
        bar.repaint();
    }

    /**
     * Builds the character bar title.
     *
     * @return the title label
     */
    private JLabel buildCharacterBarContentTitle() {
        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");

        title.setFont(MONO_BOLD);
        title.setForeground(BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        return title;
    }

    /**
     * Builds the character card panel.
     *
     * @return the character card panel
     */
    private JPanel buildCharacterCardPanel() {
        final JPanel cards = new JPanel(new GridLayout(1, CHARACTER_COUNT, 12, 0));

        cards.setBackground(GOLD);

        for (int i = 0; i < CHARACTER_COUNT; i++) {
            cards.add(buildCharacterCard(i));
        }

        return cards;
    }

    /**
     * Builds the start button panel.
     *
     * @return the start button panel
     */
    private JPanel buildStartButtonPanel() {
        final JPanel buttonWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonWrap.setBackground(GOLD);
        buttonWrap.add(buildStartButton());

        return buttonWrap;
    }

    /**
     * Starts the game.
     */
    private void startGame() {
        if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
            myQuestionText.setText("Please choose a character before starting the game.");
            setFeedback("choose a character first.", RED_BORDER);
        } else {
            SoundManager.playStartGame();
            
            myGameStarted = true;
            myQuestionText.setText("You chose " + CHARACTER_NAMES[mySelectedCharacter]
                    + "!\n\nUse the arrow pad to move between rooms.\n"
                    + "Answer trivia questions correctly to unlock doors.");
            setFeedback("quest begins! press an arrow to move.", TEXT_DARK);
            refreshCharacterBar();
            updateGrid();
        }
    }

    /**
     * Handles a movement attempt.
     *
     * @param theDirection the movement direction
     */
    private void handleMove(final String theDirection) {
        if (!myGameStarted) {
            setFeedback("choose your character and press START first!", RED_BORDER);
        } else {
            handleStartedMove(theDirection);
        }
    }

    /**
     * Handles a movement attempt after the game has started.
     *
     * @param theDirection the movement direction
     */
    private void handleStartedMove(final String theDirection) {
        final Room current = myMaze.getCurrentRoom();

        if (!current.hasDoor(theDirection)) {
            setFeedback("no path to the " + theDirection + ".", TEXT_MID);
        } else if (current.isDoorLocked(theDirection)) {
            setFeedback("that door is permanently locked!", RED_BORDER);
        } else {
            myCurrentDoor = current.getDoor(theDirection);
            myCurrentDirection = theDirection;
            showCurrentQuestion();
        }
    }

    /**
     * Shows the current door question.
     */
    private void showCurrentQuestion() {
        final Question question = myCurrentDoor.getQuestion();

        myHintUsedForQuestion = false;
        myQuestionText.setText(question.getQuestionText());
        myAnswerField.setText("");
        myAnswerField.requestFocus();
        setFeedback("type your answer and press submit.", TEXT_MID);
    }

    /**
     * Shows a hint for the current question.
     */
    private void showHint() {
        if (!myGameStarted) {
            setFeedback("choose your character and press START first!", RED_BORDER);
        } else if (myCurrentDoor == null) {
            setFeedback("press an arrow to pick a question first.", TEXT_MID);
        } else if (myHintUsedForQuestion) {
            setFeedback("hint already used for this question.", TEXT_MID);
        } else {
            myHintUsedForQuestion = true;
             SoundManager.playHint();
            showHintForQuestion(myCurrentDoor.getQuestion());
        }
    }

    /**
     * Shows a hint based on the current question type.
     *
     * @param theQuestion the current question
     */
    private void showHintForQuestion(final Question theQuestion) {
        final String hintText;

        if (theQuestion instanceof MultipleChoiceQuestion) {
            hintText = buildMultipleChoiceHint((MultipleChoiceQuestion) theQuestion);
        } else if (theQuestion instanceof TrueFalseQuestion) {
            hintText = "Hint: answer True or False.";
        } else {
            hintText = buildShortAnswerHint(theQuestion.getCorrectAnswer());
        }

        myQuestionText.setText(myQuestionText.getText() + "\n\n" + hintText);
        setFeedback("hint used.", TEXT_MID);
    }

    /**
     * Builds a hint for a multiple choice question.
     *
     * @param theQuestion the multiple choice question
     * @return the hint text
     */
    private String buildMultipleChoiceHint(final MultipleChoiceQuestion theQuestion) {
        final StringBuilder hintText = new StringBuilder("Hint: choices are ");
        final List<String> choices = theQuestion.getChoices();

        if (choices.isEmpty()) {
            hintText.append("not available");
        } else {
            appendChoices(hintText, choices);
        }

        hintText.append(".");

        return hintText.toString();
    }

    /**
     * Appends multiple choice options to a hint.
     *
     * @param theHintText the hint text builder
     * @param theChoices the answer choices
     */
    private void appendChoices(final StringBuilder theHintText,
                               final List<String> theChoices) {
        for (int i = 0; i < theChoices.size(); i++) {
            if (i > 0) {
                theHintText.append(", ");
            }

            theHintText.append(theChoices.get(i));
        }
    }

    /**
     * Builds a hint for a short answer question.
     *
     * @param theAnswer the correct answer
     * @return the hint text
     */
    private String buildShortAnswerHint(final String theAnswer) {
        String hintText = "Hint: no hint is available for this answer.";

        if (theAnswer != null && !theAnswer.isEmpty()) {
            hintText = "Hint: starts with '" + theAnswer.charAt(FIRST_CHARACTER_INDEX)
                    + "' and has " + theAnswer.length() + " characters.";
        }

        return hintText;
    }

    /**
     * Submits the typed answer.
     */
    private void submitAnswer() {
        if (!myGameStarted) {
            setFeedback("choose your character and press START first!", RED_BORDER);
        } else if (myCurrentDoor == null) {
            setFeedback("press an arrow to pick a direction first.", TEXT_MID);
        } else if (myAnswerField.getText().trim().isEmpty()) {
            setFeedback("please type an answer first.", TEXT_MID);
        } else {
            checkSubmittedAnswer(myAnswerField.getText().trim());
        }
    }

    /**
     * Checks the submitted answer.
     *
     * @param theAnswer the submitted answer
     */
    private void checkSubmittedAnswer(final String theAnswer) {
        final Question question = myCurrentDoor.getQuestion();
        final Room current = myMaze.getCurrentRoom();

        if (question.checkAnswer(theAnswer)) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer(current, question);
        }
    }

    /**
     * Handles a correct answer.
     */
    private void handleCorrectAnswer() {
        SoundManager.playCorrectAnswer();

        myMaze.move(myCurrentDirection);
        myCorrectCount++;
        myScoreLabel.setText("SCORE: "
                + String.format("%04d", myCorrectCount * POINTS_PER_CORRECT_ANSWER));
        setFeedback("correct! moved " + myCurrentDirection + ".", GREEN_BUTTON);
        myQuestionText.setText("Correct!\n\nPress an arrow to move to the next room.");
        clearCurrentQuestion();
        updateGrid();
        checkGameState();
    }

    /**
     * Handles a wrong answer.
     *
     * @param theCurrentRoom the current room
     * @param theQuestion the current question
     */
    private void handleWrongAnswer(final Room theCurrentRoom,
                                   final Question theQuestion) {
        SoundManager.playWrongAnswer();

        theCurrentRoom.lockDoor(myCurrentDirection);
        setFeedback("wrong! door locked. answer was: "
                + theQuestion.getCorrectAnswer(), RED_BORDER);
        myQuestionText.setText("Wrong answer!\n\nThat door is now permanently locked.\n"
                + "Correct answer: " + theQuestion.getCorrectAnswer()
                + "\n\nTry a different direction.");
        clearCurrentQuestion();
        updateGrid();

        if (myMaze.isGameOver()) {
            SoundManager.playGameOver();

            JOptionPane.showMessageDialog(this,
                    "All paths are blocked. Your quest has failed!",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears the current question state.
     */
    private void clearCurrentQuestion() {
        myAnswerField.setText("");
        myCurrentDoor = null;
        myCurrentDirection = null;
        myHintUsedForQuestion = false;
    }

    /**
     * Sets feedback text and color.
     *
     * @param theText the feedback text
     * @param theColor the feedback color
     */
    private void setFeedback(final String theText,
                             final Color theColor) {
        myFeedbackLabel.setText("> " + theText);
        myFeedbackLabel.setForeground(theColor);
    }

    /**
     * Checks the game state for victory.
     */
    private void checkGameState() {
        if (myMaze.isGameWon()) {
            SoundManager.playWin();

            JOptionPane.showMessageDialog(this,
                    "You reached the exit! Quest complete!\nFinal Score: "
                            + String.format("%04d",
                            myCorrectCount * POINTS_PER_CORRECT_ANSWER),
                    "Victory!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Updates the visual maze grid.
     */
    private void updateGrid() {
        final int currentRow = myMaze.getCurrentRow();
        final int currentCol = myMaze.getCurrentCol();

        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                updateGridCell(row, col, currentRow, currentCol);
            }
        }
    }

    /**
     * Updates one grid cell.
     *
     * @param theRow the cell row
     * @param theCol the cell column
     * @param theCurrentRow the current player row
     * @param theCurrentCol the current player column
     */
    private void updateGridCell(final int theRow,
                                final int theCol,
                                final int theCurrentRow,
                                final int theCurrentCol) {
        final JPanel cell = myGridCells[theRow][theCol];
        final boolean isCurrent = theRow == theCurrentRow && theCol == theCurrentCol;
        final boolean isExit = theRow == mySize - 1 && theCol == mySize - 1;

        cell.removeAll();

        if (isCurrent) {
            updateCurrentCell(cell);
        } else if (isExit) {
            updateExitCell(cell);
        } else {
            updateEmptyCell(cell);
        }

        cell.revalidate();
        cell.repaint();
    }

    /**
     * Updates the current player cell.
     *
     * @param theCell the current cell
     */
    private void updateCurrentCell(final JPanel theCell) {
        theCell.setBackground(SAND_LIGHT);
        theCell.setBorder(BorderFactory.createLineBorder(GOLD_BORDER, 4));

        if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
            final JLabel placeholder = new JLabel("?");

            placeholder.setFont(MONO_LARGE);
            placeholder.setForeground(BROWN);
            theCell.add(placeholder);
        } else {
            final JLabel icon = new JLabel(myCharacterGridIcons[mySelectedCharacter]);

            theCell.add(icon);
        }
    }

    /**
     * Updates the exit cell.
     *
     * @param theCell the exit cell
     */
    private void updateExitCell(final JPanel theCell) {
        final JLabel icon = new JLabel("*");

        theCell.setBackground(BLUE_CELL);
        theCell.setBorder(BorderFactory.createLineBorder(BLUE_BORDER, 3));

        icon.setFont(MONO_LARGE);
        icon.setForeground(BLUE_BORDER);

        theCell.add(icon);
    }

    /**
     * Updates an empty cell.
     *
     * @param theCell the empty cell
     */
    private void updateEmptyCell(final JPanel theCell) {
        theCell.setBackground(SAND);
        theCell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
    }

    /**
     * Saves the current game.
     *
     * @param theFrame the parent frame
     */
    private void saveGame(final JFrame theFrame) {
        final boolean saved = GameMemento.saveMaze(myMaze);

        if (saved) {
            SoundManager.playSaveGame();

            JOptionPane.showMessageDialog(
                    theFrame,
                    "Game saved successfully.",
                    "Save Game",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    theFrame,
                    "Game could not be saved.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads a saved game.
     *
     * @param theFrame the parent frame
     */
    private void loadGame(final JFrame theFrame) {
        final Maze loadedMaze = GameMemento.loadMaze();

        if (loadedMaze == null) {
            JOptionPane.showMessageDialog(
                    theFrame,
                    "No saved game could be loaded.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (loadedMaze.getSize() != mySize) {
            JOptionPane.showMessageDialog(
                    theFrame,
                    "Saved maze size does not match this game window.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            myMaze = loadedMaze;
            SoundManager.playLoadGame();

            myCurrentDoor = null;
            myCurrentDirection = null;
            myHintUsedForQuestion = false;
            myGameStarted = true;

            if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
                mySelectedCharacter = 0;
            }

            myQuestionText.setText("Saved game loaded.\n\nUse the arrow pad to continue.");
            myAnswerField.setText("");
            setFeedback("saved game loaded.", TEXT_DARK);
            refreshCharacterBar();
            updateGrid();

            JOptionPane.showMessageDialog(
                    theFrame,
                    "Game loaded successfully.",
                    "Load Game",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}




// package view;

// /*
//  * Trivia Maze - TCSS 360
//  * Spring 2026
//  * Author: Suhayr Hassan
//  */

// import model.*;

// import java.awt.*;
// import java.awt.event.*;
// import javax.swing.*;
// import javax.swing.border.*;

// import org.w3c.dom.events.MouseEvent;

// /**
//  * The main game panel for the Trivia Maze game.
//  * Features character selection, a grid-based maze map,
//  * question/answer panel, and a d-pad for navigation.
//  *
//  * @author Suhayr Hassan
//  * @version 16 May 2026
//  */
// public class MazeGUI extends JPanel {

//     // ── Colors ───────────────────────────────────────────────────────────────
//     private static final Color SKY_BLUE    = new Color(91,  163, 217);
//     private static final Color SKY_LIGHT   = new Color(135, 206, 235);
//     private static final Color SAND        = new Color(232, 208, 160);
//     private static final Color SAND_LIGHT  = new Color(250, 238, 200);
//     private static final Color SAND_DARK   = new Color(212, 184, 112);
//     private static final Color GOLD        = new Color(240, 216, 152);
//     private static final Color GOLD_DARK   = new Color(200, 160,  80);
//     private static final Color GOLD_BORDER = new Color(232, 160,  32);
//     private static final Color BROWN       = new Color( 90,  58,  26);
//     private static final Color RED_BDR     = new Color(170,  32,  32);
//     private static final Color BLUE_CELL   = new Color(251, 243, 170, 255);
//     private static final Color BLUE_BDR    = new Color( 32,  96, 192);
//     private static final Color GREEN_BTN   = new Color( 74, 138,  32);
//     private static final Color GREEN_BTN2  = new Color( 58, 106,  24);
//     private static final Color TEXT_DARK   = new Color( 58,  42,  16);
//     private static final Color TEXT_MID    = new Color(138, 106,  48);

//     // ── Fonts ────────────────────────────────────────────────────────────────
//     private static final Font MONO_SM  = new Font("Monospaced", Font.PLAIN,  10);
//     private static final Font MONO_MD  = new Font("Monospaced", Font.PLAIN,  12);
//     private static final Font MONO_BLD = new Font("Monospaced", Font.BOLD,   12);
//     private static final Font MONO_LG  = new Font("Monospaced", Font.BOLD,   22);
//     private static final Font MONO_XL  = new Font("Monospaced", Font.BOLD,   16);

//     // ── Characters ───────────────────────────────────────────────────────────
//     private static final String[] CHAR_NAMES  = {"Warrior", "Mage", "Rogue"};
//     private static final String[] CHAR_IMAGES = {
//             "src/sprites/warrior.png",
//             "src/sprites/mage.png",
//             "src/sprites/rogue.png"
//     };

//     // ── D-pad image paths ────────────────────────────────────────────────────
//     private static final String DPAD_UP    = "src/sprites/up.png";
//     private static final String DPAD_DOWN  = "src/sprites/down.png";
//     private static final String DPAD_LEFT  = "src/sprites/left.png";
//     private static final String DPAD_RIGHT = "src/sprites/right.png";

//     // ── Model ────────────────────────────────────────────────────────────────
//     private Maze myMaze;
//     private final int  mySize;

//     // ── State ────────────────────────────────────────────────────────────────
//     private int     mySelectedChar = 0;
//     private boolean myGameStarted  = false;
//     private Door    myCurrentDoor  = null;
//     private String  myCurrentDir   = null;
//     private int     myCorrectCount = 0;
//     private int     myRoomsCount   = 1;

//     // ── Grid cells ───────────────────────────────────────────────────────────
//     private final JPanel[][] myGridCells;

//     // ── Character select cards ───────────────────────────────────────────────
//     private final JPanel[] myCharCards = new JPanel[3];

//     // ── Cached scaled character icons ────────────────────────────────────────
//     private final ImageIcon[] myCharIcons    = new ImageIcon[3];
//     private final ImageIcon[] myCharIconsBig = new ImageIcon[3];

//     // ── Question / answer components ─────────────────────────────────────────
//     private final JTextArea  myQuestionText;
//     private final JTextField myAnswerField;
//     private final JButton    mySubmitButton;
//     private final JLabel     myFeedbackLabel;

//     // ── Score label ──────────────────────────────────────────────────────────
//     private final JLabel myScoreLabel;

//     /**
//      * Constructs the MazeGUI panel.
//      *
//      * @param theMaze the Maze model to display
//      */
//     public MazeGUI(final Maze theMaze) {
//         super(new BorderLayout());
//         setBackground(SKY_BLUE);
//         setBorder(new EmptyBorder(10, 10, 10, 10));

//         myMaze      = theMaze;
//         mySize      = theMaze.getSize();
//         myGridCells = new JPanel[mySize][mySize];

//         loadCharacterIcons();

//         myQuestionText  = buildQuestionArea();
//         myAnswerField   = buildAnswerField();
//         mySubmitButton  = buildSubmitButton();
//         myFeedbackLabel = new JLabel("> pick a character and press start");
//         myFeedbackLabel.setFont(MONO_SM);
//         myFeedbackLabel.setForeground(TEXT_MID);

//         myScoreLabel = new JLabel("SCORE: 0000");
//         myScoreLabel.setFont(MONO_BLD);
//         myScoreLabel.setForeground(BROWN);
//         myScoreLabel.setBackground(GOLD);
//         myScoreLabel.setOpaque(true);
//         myScoreLabel.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(GOLD_DARK, 3),
//                 new EmptyBorder(2, 8, 2, 8)));

//         add(buildMainPanel(), BorderLayout.CENTER);
//         add(buildCharBar(),   BorderLayout.SOUTH);

//         updateGrid();
//     }

//     /**
//      * Loads and scales character images for use in cards and grid cells.
//      */
//     private void loadCharacterIcons() {
//         for (int i = 0; i < 3; i++) {
//             final ImageIcon raw = new ImageIcon(CHAR_IMAGES[i]);
//             myCharIcons[i] = new ImageIcon(
//                     raw.getImage().getScaledInstance(80, 80, Image.SCALE_FAST));
//             myCharIconsBig[i] = new ImageIcon(
//                     raw.getImage().getScaledInstance(48, 48, Image.SCALE_FAST));
//         }
//     }

//     // ── Top-level layout ─────────────────────────────────────────────────────

//     private JPanel buildMainPanel() {
//         final JPanel p = new JPanel(new GridBagLayout());
//         p.setBackground(SKY_BLUE);

//         final GridBagConstraints gbc = new GridBagConstraints();
//         gbc.fill   = GridBagConstraints.BOTH;
//         gbc.insets = new Insets(4, 4, 4, 4);

//         // Map takes left column, spans full height
//         gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 2;
//         gbc.weightx = 0.65; gbc.weighty = 1.0;
//         p.add(buildMapPanel(), gbc);

//         gbc.gridx = 1; gbc.gridheight = 1; gbc.weightx = 0.35;

//         // Question panel takes top right
//         gbc.gridy = 0; gbc.weighty = 0.65;
//         p.add(buildQuestionPanel(), gbc);

//         // Dpad takes bottom right
//         gbc.gridy = 1; gbc.weighty = 0.35;
//         p.add(buildDpadPanel(), gbc);

//         return p;
//     }

//     // ── Map panel ────────────────────────────────────────────────────────────

//     private JPanel buildMapPanel() {
//         final JPanel wrap = new JPanel(new BorderLayout(0, 8));
//         wrap.setBackground(SKY_LIGHT);
//         wrap.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(SAND_DARK, 3),
//                 new EmptyBorder(10, 10, 10, 10)));

//         final JPanel header = new JPanel(new BorderLayout());
//         header.setBackground(SKY_LIGHT);
//         final JLabel title = new JLabel("TRIVIA MAZE");
//         title.setFont(MONO_XL);
//         title.setForeground(BROWN);
//         header.add(title, BorderLayout.WEST);
//         header.add(myScoreLabel, BorderLayout.EAST);
//         wrap.add(header, BorderLayout.NORTH);

//         final JPanel grid = new JPanel(new GridLayout(mySize, mySize, 4, 4));
//         grid.setBackground(SKY_LIGHT);
//         for (int r = 0; r < mySize; r++) {
//             for (int c = 0; c < mySize; c++) {
//                 final JPanel cell = new JPanel(new GridBagLayout());
//                 cell.setBackground(SAND);
//                 cell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
//                 myGridCells[r][c] = cell;
//                 grid.add(cell);
//             }
//         }
//         wrap.add(grid, BorderLayout.CENTER);
//         return wrap;
//     }

//     // ── Question panel ───────────────────────────────────────────────────────

//     private JPanel buildQuestionPanel() {
//         final JPanel p = new JPanel(new GridBagLayout());
//         p.setBackground(GOLD);
//         p.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(GOLD_DARK, 3),
//                 new EmptyBorder(10, 12, 10, 12)));

//         final GridBagConstraints gbc = new GridBagConstraints();
//         gbc.fill    = GridBagConstraints.HORIZONTAL;
//         gbc.weightx = 1.0;
//         gbc.anchor  = GridBagConstraints.NORTHWEST;
//         gbc.insets  = new Insets(4, 0, 4, 0);

//         // Question label
//         gbc.gridy = 0;
//         final JLabel qTag = new JLabel("QUESTION");
//         qTag.setFont(MONO_BLD);
//         qTag.setForeground(TEXT_MID);
//         p.add(qTag, gbc);

//         // Question text area
//         gbc.gridy   = 1;
//         gbc.weighty = 1.0;
//         gbc.fill    = GridBagConstraints.BOTH;
//         final JScrollPane scroll = new JScrollPane(myQuestionText);
//         scroll.setBorder(BorderFactory.createLineBorder(SAND_DARK, 2));
//         scroll.getViewport().setBackground(SAND_LIGHT);
//         p.add(scroll, gbc);

//         // Answer label
//         gbc.gridy   = 2;
//         gbc.weighty = 0;
//         gbc.fill    = GridBagConstraints.HORIZONTAL;
//         final JLabel aTag = new JLabel("YOUR ANSWER");
//         aTag.setFont(MONO_BLD);
//         aTag.setForeground(TEXT_MID);
//         p.add(aTag, gbc);

//         // Answer field
//         gbc.gridy = 3;
//         p.add(myAnswerField, gbc);

//         // Submit button
//         gbc.gridy = 4;
//         p.add(mySubmitButton, gbc);

//         // Feedback label
//         gbc.gridy = 5;
//         p.add(myFeedbackLabel, gbc);

//         return p;
//     }

//     private JTextArea buildQuestionArea() {
//         final JTextArea ta = new JTextArea(5, 20);
//         ta.setFont(MONO_MD);
//         ta.setForeground(TEXT_DARK);
//         ta.setBackground(SAND_LIGHT);
//         ta.setEditable(false);
//         ta.setLineWrap(true);
//         ta.setWrapStyleWord(true);
//         ta.setBorder(new EmptyBorder(6, 8, 6, 8));
//         ta.setText("Choose your character below, then press START to begin!");
//         return ta;
//     }

//     private JTextField buildAnswerField() {
//         final JTextField tf = new JTextField();
//         tf.setFont(MONO_MD);
//         tf.setForeground(TEXT_DARK);
//         tf.setBackground(SAND_LIGHT);
//         tf.setCaretColor(BROWN);
//         tf.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(GOLD_BORDER, 3),
//                 new EmptyBorder(5, 8, 5, 8)));
//         tf.addActionListener(e -> submitAnswer());
//         return tf;
//     }

//     private JButton buildSubmitButton() {
//         final JButton btn = new JButton("[ SUBMIT ]");
//         btn.setFont(MONO_BLD);
//         btn.setForeground(new Color(240, 248, 224));
//         btn.setBackground(GREEN_BTN);
//         btn.setOpaque(true);
//         btn.setBorderPainted(true);
//         btn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
//         btn.setFocusPainted(false);
//         btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         btn.addActionListener(e -> submitAnswer());
//         return btn;
//     }

//     // ── D-pad ────────────────────────────────────────────────────────────────

//     private JPanel buildDpadPanel() {
//         final JPanel wrap = new JPanel(new GridBagLayout());
//         wrap.setBackground(GOLD);
//         wrap.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 3));

//         final GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(4, 4, 4, 4);

//         gbc.gridx = 1; gbc.gridy = 0; wrap.add(dpadBtn(DPAD_UP,    "north"), gbc);
//         gbc.gridx = 0; gbc.gridy = 1; wrap.add(dpadBtn(DPAD_LEFT,  "west"),  gbc);
//         gbc.gridx = 2; gbc.gridy = 1; wrap.add(dpadBtn(DPAD_RIGHT, "east"),  gbc);
//         gbc.gridx = 1; gbc.gridy = 2; wrap.add(dpadBtn(DPAD_DOWN,  "south"), gbc);

//         final JPanel nub = new JPanel();
//         nub.setBackground(SAND_DARK);
//         nub.setPreferredSize(new Dimension(62, 62));
//         nub.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));
//         gbc.gridx = 1; gbc.gridy = 1;
//         wrap.add(nub, gbc);

//         return wrap;
//     }

//     /**
//      * Creates a d-pad button using an image.
//      *
//      * @param theImagePath path to the arrow image
//      * @param theDir       direction string for movement
//      * @return the styled button
//      */
//     private JButton dpadBtn(final String theImagePath, final String theDir) {
//         final ImageIcon icon = new ImageIcon(
//                 new ImageIcon(theImagePath).getImage()
//                         .getScaledInstance(55, 55, Image.SCALE_FAST));
//         final JButton btn = new JButton(icon);
//         btn.setBackground(SAND);
//         btn.setOpaque(true);
//         btn.setContentAreaFilled(true);
//         btn.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));
//         btn.setFocusPainted(false);
//         btn.setPreferredSize(new Dimension(62, 62));
//         btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         btn.addActionListener(e -> handleMove(theDir));
//         btn.addMouseListener(new MouseAdapter() {
//             public void mouseEntered(final MouseEvent e) { btn.setBackground(SAND_LIGHT); }
//             public void mouseExited(final MouseEvent e)  { btn.setBackground(SAND); }
//         });
//         return btn;
//     }

//     // ── Character select bar ─────────────────────────────────────────────────

//     private JPanel buildCharBar() {
//         final JPanel bar = new JPanel(new BorderLayout(0, 6));
//         bar.setBackground(GOLD);
//         bar.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(GOLD_BORDER, 3),
//                 new EmptyBorder(10, 12, 10, 12)));

//         final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
//         title.setFont(MONO_BLD);
//         title.setForeground(BROWN);
//         title.setHorizontalAlignment(SwingConstants.CENTER);
//         bar.add(title, BorderLayout.NORTH);

//         final JPanel cards = new JPanel(new GridLayout(1, 3, 12, 0));
//         cards.setBackground(GOLD);
//         for (int i = 0; i < 3; i++) {
//             cards.add(buildCharCard(i));
//         }
//         bar.add(cards, BorderLayout.CENTER);

//         final JButton startBtn = new JButton("[ START GAME ]");
//         startBtn.setFont(MONO_BLD);
//         startBtn.setForeground(Color.WHITE);
//         startBtn.setBackground(GREEN_BTN);
//         startBtn.setOpaque(true);
//         startBtn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
//         startBtn.setFocusPainted(false);
//         startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         startBtn.addActionListener(e -> startGame());

//         final JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         btnWrap.setBackground(GOLD);
//         btnWrap.add(startBtn);
//         bar.add(btnWrap, BorderLayout.SOUTH);

//         return bar;
//     }

//     /**
//      * Builds a character card showing only the image.
//      *
//      * @param theIndex the character index
//      * @return the card panel
//      */
//     private JPanel buildCharCard(final int theIndex) {
//         final JPanel card = new JPanel(new GridBagLayout());
//         card.setBackground(theIndex == mySelectedChar ? SAND_LIGHT : SAND);
//         card.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(
//                         theIndex == mySelectedChar ? GOLD_BORDER : SAND_DARK, 3),
//                 new EmptyBorder(8, 8, 8, 8)));
//         card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         card.setPreferredSize(new Dimension(110, 110));

//         final JLabel sprite = new JLabel(myCharIcons[theIndex]);
//         sprite.setHorizontalAlignment(SwingConstants.CENTER);
//         card.add(sprite);

//         final int idx = theIndex;
//         card.addMouseListener(new MouseAdapter() {
//             public void mouseClicked(final MouseEvent e) {
//                 selectCharacter(idx);
//             }
//             public void mouseEntered(final MouseEvent e) {
//                 card.setBorder(BorderFactory.createCompoundBorder(
//                         BorderFactory.createLineBorder(GOLD_DARK, 3),
//                         new EmptyBorder(8, 8, 8, 8)));
//             }
//             public void mouseExited(final MouseEvent e) {
//                 card.setBorder(BorderFactory.createCompoundBorder(
//                         BorderFactory.createLineBorder(
//                                 idx == mySelectedChar ? GOLD_BORDER : SAND_DARK, 3),
//                         new EmptyBorder(8, 8, 8, 8)));
//             }
//         });

//         myCharCards[theIndex] = card;
//         return card;
//     }

//     /**
//      * Selects a character and refreshes the character bar.
//      *
//      * @param theIndex the index of the chosen character
//      */
//     private void selectCharacter(final int theIndex) {
//         mySelectedChar = theIndex;
//         refreshCharBar();
//         updateGrid();
//     }

//     /**
//      * Refreshes the character bar to show the updated selection.
//      */
//     private void refreshCharBar() {
//         final JPanel bar = (JPanel) getComponent(1);
//         bar.removeAll();

//         final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
//         title.setFont(MONO_BLD);
//         title.setForeground(BROWN);
//         title.setHorizontalAlignment(SwingConstants.CENTER);
//         bar.add(title, BorderLayout.NORTH);

//         final JPanel cards = new JPanel(new GridLayout(1, 3, 12, 0));
//         cards.setBackground(GOLD);
//         for (int i = 0; i < 3; i++) {
//             cards.add(buildCharCard(i));
//         }
//         bar.add(cards, BorderLayout.CENTER);

//         final JButton startBtn = new JButton(myGameStarted ? "[ RESTART ]" : "[ START GAME ]");
//         startBtn.setFont(MONO_BLD);
//         startBtn.setForeground(Color.WHITE);
//         startBtn.setBackground(GREEN_BTN);
//         startBtn.setOpaque(true);
//         startBtn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
//         startBtn.setFocusPainted(false);
//         startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//         startBtn.addActionListener(e -> startGame());

//         final JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         btnWrap.setBackground(GOLD);
//         btnWrap.add(startBtn);
//         bar.add(btnWrap, BorderLayout.SOUTH);

//         bar.revalidate();
//         bar.repaint();
//     }

//     /**
//      * Starts the game after character selection.
//      */
//     private void startGame() {
//         myGameStarted = true;
//         myQuestionText.setText("You chose " + CHAR_NAMES[mySelectedChar]
//                 + "!\n\nUse the arrow pad to move between rooms.\n"
//                 + "Answer trivia questions correctly to unlock doors.");
//         setFeedback("> quest begins! press an arrow to move.", TEXT_DARK);
//     }

//     // ── Game logic ───────────────────────────────────────────────────────────

//     /**
//      * Handles a move attempt in the given direction.
//      *
//      * @param theDirection the direction string
//      */
//     private void handleMove(final String theDirection) {
//         if (!myGameStarted) {
//             setFeedback("> choose your character and press START first!", RED_BDR);
//             return;
//         }

//         final Room current = myMaze.getCurrentRoom();

//         if (!current.hasDoor(theDirection)) {
//             setFeedback("> no path to the " + theDirection + ".", TEXT_MID);
//             return;
//         }
//         if (current.isDoorLocked(theDirection)) {
//             setFeedback("> that door is permanently locked!", RED_BDR);
//             return;
//         }

//         myCurrentDoor = current.getDoor(theDirection);
//         myCurrentDir  = theDirection;

//         final Question question = myCurrentDoor.getQuestion();
//         myQuestionText.setText(question.getQuestionText());
//         myAnswerField.setText("");
//         myAnswerField.requestFocus();
//         setFeedback("> type your answer and press submit.", TEXT_MID);
//     }

//     /**
//      * Submits the typed answer and evaluates it.
//      */
//     private void submitAnswer() {
//         if (!myGameStarted) {
//             setFeedback("> choose your character and press START first!", RED_BDR);
//             return;
//         }
//         if (myCurrentDoor == null) {
//             setFeedback("> press an arrow to pick a direction first.", TEXT_MID);
//             return;
//         }

//         final String answer = myAnswerField.getText().trim();
//         if (answer.isEmpty()) {
//             setFeedback("> please type an answer first.", TEXT_MID);
//             return;
//         }

//         final Question question = myCurrentDoor.getQuestion();
//         final Room     current  = myMaze.getCurrentRoom();

//         if (question.checkAnswer(answer)) {
//             myMaze.move(myCurrentDir);
//             myCorrectCount++;
//             myRoomsCount++;
//             myScoreLabel.setText("SCORE: " + String.format("%04d", myCorrectCount * 100));
//             setFeedback("> correct! moved " + myCurrentDir + ".", GREEN_BTN);
//             myQuestionText.setText("Correct!\n\nPress an arrow to move to the next room.");
//             myAnswerField.setText("");
//             myCurrentDoor = null;
//             myCurrentDir  = null;
//             updateGrid();
//             checkGameState();
//         } else {
//             current.lockDoor(myCurrentDir);
//             setFeedback("> wrong! door locked. answer was: " + question.getCorrectAnswer(), RED_BDR);
//             myQuestionText.setText("Wrong answer!\n\nThat door is now permanently locked.\n"
//                     + "Correct answer: " + question.getCorrectAnswer()
//                     + "\n\nTry a different direction.");
//             myAnswerField.setText("");
//             myCurrentDoor = null;
//             myCurrentDir  = null;
//             updateGrid();
//             if (myMaze.isGameOver()) {
//                 JOptionPane.showMessageDialog(this,
//                         "All paths are blocked. Your quest has failed!",
//                         "Game Over", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }

//     private void setFeedback(final String theText, final Color theColor) {
//         myFeedbackLabel.setText("> " + theText);
//         myFeedbackLabel.setForeground(theColor);
//     }

//     /**
//      * Checks whether the player has reached the exit.
//      */
//     private void checkGameState() {
//         if (myMaze.isGameWon()) {
//             JOptionPane.showMessageDialog(this,
//                     "You reached the exit! Quest complete!\nFinal Score: "
//                             + String.format("%04d", myCorrectCount * 100),
//                     "Victory!", JOptionPane.INFORMATION_MESSAGE);
//         }
//     }

//     /**
//      * Repaints the grid to reflect the current game state.
//      */
//     private void updateGrid() {
//         final int curRow = myMaze.getCurrentRow();
//         final int curCol = myMaze.getCurrentCol();

//         for (int r = 0; r < mySize; r++) {
//             for (int c = 0; c < mySize; c++) {
//                 final JPanel cell = myGridCells[r][c];
//                 cell.removeAll();

//                 final boolean isCurrent = (r == curRow && c == curCol);
//                 final boolean isExit    = (r == mySize - 1 && c == mySize - 1);

//                 if (isCurrent) {
//                     cell.setBackground(SAND_LIGHT);
//                     cell.setBorder(BorderFactory.createLineBorder(GOLD_BORDER, 4));
//                     final JLabel icon = new JLabel(myCharIconsBig[mySelectedChar]);
//                     cell.add(icon);
//                 } else if (isExit) {
//                     cell.setBackground(BLUE_CELL);
//                     cell.setBorder(BorderFactory.createLineBorder(BLUE_BDR, 3));
//                     final JLabel icon = new JLabel("★");
//                     icon.setFont(MONO_LG);
//                     icon.setForeground(BLUE_BDR);
//                     cell.add(icon);
//                 } else {
//                     cell.setBackground(SAND);
//                     cell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
//                 }

//                 cell.revalidate();
//                 cell.repaint();
//             }
//         }
//     }

//     // ── Static window builders ───────────────────────────────────────────────
//     /**
//      * Saves the current maze state.
//      *
//      * @param theFrame the parent frame for the message dialog
//      */
//     private void saveGame(final JFrame theFrame) {
//         final boolean saved = GameMemento.saveMaze(myMaze);

//         if (saved) {
//             JOptionPane.showMessageDialog(
//                     theFrame,
//                     "Game saved successfully.",
//                     "Save Game",
//                     JOptionPane.INFORMATION_MESSAGE);
//         } else {
//             JOptionPane.showMessageDialog(
//                     theFrame,
//                     "Game could not be saved.",
//                     "Save Error",
//                     JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     /**
//      * Loads a previously saved maze state.
//      *
//      * @param theFrame the parent frame for the message dialog
//      */
//     private void loadGame(final JFrame theFrame) {
//         final Maze loadedMaze = GameMemento.loadMaze();

//         if (loadedMaze == null) {
//             JOptionPane.showMessageDialog(
//                     theFrame,
//                     "No saved game could be loaded.",
//                     "Load Error",
//                     JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         if (loadedMaze.getSize() != mySize) {
//             JOptionPane.showMessageDialog(
//                     theFrame,
//                     "Saved maze size does not match this game window.",
//                     "Load Error",
//                     JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         myMaze = loadedMaze;
//         myCurrentDoor = null;
//         myCurrentDir = null;
//         myGameStarted = true;

//         myQuestionText.setText("Saved game loaded.\n\nUse the arrow pad to continue.");
//         myAnswerField.setText("");
//         setFeedback("> saved game loaded.", TEXT_DARK);
//         updateGrid();

//         JOptionPane.showMessageDialog(
//                 theFrame,
//                 "Game loaded successfully.",
//                 "Load Game",
//                 JOptionPane.INFORMATION_MESSAGE);
//     }
//     /**
//      * Creates and displays the main game window.
//      */
//     public static void createAndShowGUI() {
//         Database.init();
//         final QuestionFactory factory = new QuestionFactory("trivia.db");
//         final Maze maze = new Maze(4);
//         maze.initializeDoors(factory);

//         final JFrame frame = new JFrame("Trivia Maze");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setPreferredSize(new Dimension(980, 780));
//         frame.setBackground(SKY_BLUE);
//         final MazeGUI gamePanel = new MazeGUI(maze);
//         frame.setJMenuBar(buildMenuBar(frame, gamePanel));
//         frame.add(gamePanel, BorderLayout.CENTER);
//         frame.pack();
//         frame.setLocationRelativeTo(null);
//         frame.setVisible(true);
//     }

//     /**
//      * Builds the menu bar with File and Help menus.
//      *
//      * @param theFrame the parent frame
//      * @return the completed menu bar
//      */
//     private static JMenuBar buildMenuBar(final JFrame theFrame,
//                                      final MazeGUI theGamePanel) {
//         final JMenuBar bar = new JMenuBar();
//         bar.setBackground(GOLD);
//         bar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, GOLD_DARK));

//         final JMenu fileMenu = styledMenu("File");
//         final JMenuItem saveItem = styledItem("Save Game");
//         saveItem.addActionListener(e -> theGamePanel.saveGame(theFrame));
//         final JMenuItem loadItem = styledItem("Load Game");
//         loadItem.addActionListener(e -> theGamePanel.loadGame(theFrame));
//         final JMenuItem exitItem = styledItem("Exit");
//         exitItem.addActionListener(e -> {
//             final int choice = JOptionPane.showConfirmDialog(theFrame,
//                     "Exit without saving?", "Exit", JOptionPane.YES_NO_OPTION);
//             if (choice == JOptionPane.YES_OPTION) theFrame.dispose();
//         });
//         fileMenu.add(saveItem);
//         fileMenu.add(loadItem);
//         fileMenu.addSeparator();
//         fileMenu.add(exitItem);

//         final JMenu helpMenu = styledMenu("Help");
//         final JMenuItem aboutItem = styledItem("About");
//         aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(theFrame,
//                 "Trivia Maze  v1.0\nTCSS 360 - Spring 2026\n\n"
//                         + "Suhayr Hassan\nJinal Thummar\nRoman Pavlyshyn",
//                 "About", JOptionPane.INFORMATION_MESSAGE));
//         final JMenuItem howItem = styledItem("Game Play Instructions");
//         howItem.addActionListener(e -> JOptionPane.showMessageDialog(theFrame,
//                 "1. Choose your character at the bottom.\n"
//                         + "2. Press START GAME.\n"
//                         + "3. Use the arrow pad to move between rooms.\n"
//                         + "4. Answer trivia questions to unlock doors.\n"
//                         + "5. Wrong answer = door locked permanently.\n"
//                         + "6. Reach the exit (★) to win!\n"
//                         + "7. If all paths are blocked, game over.",
//                 "How to Play", JOptionPane.INFORMATION_MESSAGE));
//         helpMenu.add(aboutItem);
//         helpMenu.add(howItem);

//         bar.add(fileMenu);
//         bar.add(helpMenu);
//         return bar;
//     }

//     private static JMenu styledMenu(final String theText) {
//         final JMenu m = new JMenu(theText);
//         m.setFont(MONO_BLD);
//         m.setForeground(BROWN);
//         return m;
//     }

//     private static JMenuItem styledItem(final String theText) {
//         final JMenuItem item = new JMenuItem(theText);
//         item.setFont(MONO_MD);
//         item.setBackground(GOLD);
//         item.setForeground(TEXT_DARK);
//         return item;
//     }

//     /**
//      * Entry point to launch the Trivia Maze GUI.
//      *
//      * @param theArgs command-line arguments (not used)
//      */
//     public static void main(final String[] theArgs) {
//         SwingUtilities.invokeLater(MazeGUI::createAndShowGUI);
//     }
// }