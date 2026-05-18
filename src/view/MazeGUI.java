package view;

/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 * Author: Suhayr Hassan
 */

import model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.w3c.dom.events.MouseEvent;

/**
 * The main game panel for the Trivia Maze game.
 * Features character selection, a grid-based maze map,
 * question/answer panel, and a d-pad for navigation.
 *
 * @author Suhayr Hassan
 * @version 16 May 2026
 */
public class MazeGUI extends JPanel {

    // ── Colors ───────────────────────────────────────────────────────────────
    private static final Color SKY_BLUE    = new Color(91,  163, 217);
    private static final Color SKY_LIGHT   = new Color(135, 206, 235);
    private static final Color SAND        = new Color(232, 208, 160);
    private static final Color SAND_LIGHT  = new Color(250, 238, 200);
    private static final Color SAND_DARK   = new Color(212, 184, 112);
    private static final Color GOLD        = new Color(240, 216, 152);
    private static final Color GOLD_DARK   = new Color(200, 160,  80);
    private static final Color GOLD_BORDER = new Color(232, 160,  32);
    private static final Color BROWN       = new Color( 90,  58,  26);
    private static final Color RED_BDR     = new Color(170,  32,  32);
    private static final Color BLUE_CELL   = new Color(251, 243, 170, 255);
    private static final Color BLUE_BDR    = new Color( 32,  96, 192);
    private static final Color GREEN_BTN   = new Color( 74, 138,  32);
    private static final Color GREEN_BTN2  = new Color( 58, 106,  24);
    private static final Color TEXT_DARK   = new Color( 58,  42,  16);
    private static final Color TEXT_MID    = new Color(138, 106,  48);

    // ── Fonts ────────────────────────────────────────────────────────────────
    private static final Font MONO_SM  = new Font("Monospaced", Font.PLAIN,  10);
    private static final Font MONO_MD  = new Font("Monospaced", Font.PLAIN,  12);
    private static final Font MONO_BLD = new Font("Monospaced", Font.BOLD,   12);
    private static final Font MONO_LG  = new Font("Monospaced", Font.BOLD,   22);
    private static final Font MONO_XL  = new Font("Monospaced", Font.BOLD,   16);

    // ── Characters ───────────────────────────────────────────────────────────
    private static final String[] CHAR_NAMES  = {"Warrior", "Mage", "Rogue"};
    private static final String[] CHAR_IMAGES = {
            "src/sprites/warrior.png",
            "src/sprites/mage.png",
            "src/sprites/rogue.png"
    };

    // ── D-pad image paths ────────────────────────────────────────────────────
    private static final String DPAD_UP    = "src/sprites/up.png";
    private static final String DPAD_DOWN  = "src/sprites/down.png";
    private static final String DPAD_LEFT  = "src/sprites/left.png";
    private static final String DPAD_RIGHT = "src/sprites/right.png";

    // ── Model ────────────────────────────────────────────────────────────────
    private Maze myMaze;
    private final int  mySize;

    // ── State ────────────────────────────────────────────────────────────────
    private int     mySelectedChar = 0;
    private boolean myGameStarted  = false;
    private Door    myCurrentDoor  = null;
    private String  myCurrentDir   = null;
    private int     myCorrectCount = 0;
    private int     myRoomsCount   = 1;

    // ── Grid cells ───────────────────────────────────────────────────────────
    private final JPanel[][] myGridCells;

    // ── Character select cards ───────────────────────────────────────────────
    private final JPanel[] myCharCards = new JPanel[3];

    // ── Cached scaled character icons ────────────────────────────────────────
    private final ImageIcon[] myCharIcons    = new ImageIcon[3];
    private final ImageIcon[] myCharIconsBig = new ImageIcon[3];

    // ── Question / answer components ─────────────────────────────────────────
    private final JTextArea  myQuestionText;
    private final JTextField myAnswerField;
    private final JButton    mySubmitButton;
    private final JLabel     myFeedbackLabel;

    // ── Score label ──────────────────────────────────────────────────────────
    private final JLabel myScoreLabel;

    /**
     * Constructs the MazeGUI panel.
     *
     * @param theMaze the Maze model to display
     */
    public MazeGUI(final Maze theMaze) {
        super(new BorderLayout());
        setBackground(SKY_BLUE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        myMaze      = theMaze;
        mySize      = theMaze.getSize();
        myGridCells = new JPanel[mySize][mySize];

        loadCharacterIcons();

        myQuestionText  = buildQuestionArea();
        myAnswerField   = buildAnswerField();
        mySubmitButton  = buildSubmitButton();
        myFeedbackLabel = new JLabel("> pick a character and press start");
        myFeedbackLabel.setFont(MONO_SM);
        myFeedbackLabel.setForeground(TEXT_MID);

        myScoreLabel = new JLabel("SCORE: 0000");
        myScoreLabel.setFont(MONO_BLD);
        myScoreLabel.setForeground(BROWN);
        myScoreLabel.setBackground(GOLD);
        myScoreLabel.setOpaque(true);
        myScoreLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_DARK, 3),
                new EmptyBorder(2, 8, 2, 8)));

        add(buildMainPanel(), BorderLayout.CENTER);
        add(buildCharBar(),   BorderLayout.SOUTH);

        updateGrid();
    }

    /**
     * Loads and scales character images for use in cards and grid cells.
     */
    private void loadCharacterIcons() {
        for (int i = 0; i < 3; i++) {
            final ImageIcon raw = new ImageIcon(CHAR_IMAGES[i]);
            myCharIcons[i] = new ImageIcon(
                    raw.getImage().getScaledInstance(80, 80, Image.SCALE_FAST));
            myCharIconsBig[i] = new ImageIcon(
                    raw.getImage().getScaledInstance(48, 48, Image.SCALE_FAST));
        }
    }

    // ── Top-level layout ─────────────────────────────────────────────────────

    private JPanel buildMainPanel() {
        final JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(SKY_BLUE);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill   = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);

        // Map takes left column, spans full height
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 2;
        gbc.weightx = 0.65; gbc.weighty = 1.0;
        p.add(buildMapPanel(), gbc);

        gbc.gridx = 1; gbc.gridheight = 1; gbc.weightx = 0.35;

        // Question panel takes top right
        gbc.gridy = 0; gbc.weighty = 0.65;
        p.add(buildQuestionPanel(), gbc);

        // Dpad takes bottom right
        gbc.gridy = 1; gbc.weighty = 0.35;
        p.add(buildDpadPanel(), gbc);

        return p;
    }

    // ── Map panel ────────────────────────────────────────────────────────────

    private JPanel buildMapPanel() {
        final JPanel wrap = new JPanel(new BorderLayout(0, 8));
        wrap.setBackground(SKY_LIGHT);
        wrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SAND_DARK, 3),
                new EmptyBorder(10, 10, 10, 10)));

        final JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SKY_LIGHT);
        final JLabel title = new JLabel("TRIVIA MAZE");
        title.setFont(MONO_XL);
        title.setForeground(BROWN);
        header.add(title, BorderLayout.WEST);
        header.add(myScoreLabel, BorderLayout.EAST);
        wrap.add(header, BorderLayout.NORTH);

        final JPanel grid = new JPanel(new GridLayout(mySize, mySize, 4, 4));
        grid.setBackground(SKY_LIGHT);
        for (int r = 0; r < mySize; r++) {
            for (int c = 0; c < mySize; c++) {
                final JPanel cell = new JPanel(new GridBagLayout());
                cell.setBackground(SAND);
                cell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
                myGridCells[r][c] = cell;
                grid.add(cell);
            }
        }
        wrap.add(grid, BorderLayout.CENTER);
        return wrap;
    }

    // ── Question panel ───────────────────────────────────────────────────────

    private JPanel buildQuestionPanel() {
        final JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(GOLD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_DARK, 3),
                new EmptyBorder(10, 12, 10, 12)));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor  = GridBagConstraints.NORTHWEST;
        gbc.insets  = new Insets(4, 0, 4, 0);

        // Question label
        gbc.gridy = 0;
        final JLabel qTag = new JLabel("QUESTION");
        qTag.setFont(MONO_BLD);
        qTag.setForeground(TEXT_MID);
        p.add(qTag, gbc);

        // Question text area
        gbc.gridy   = 1;
        gbc.weighty = 1.0;
        gbc.fill    = GridBagConstraints.BOTH;
        final JScrollPane scroll = new JScrollPane(myQuestionText);
        scroll.setBorder(BorderFactory.createLineBorder(SAND_DARK, 2));
        scroll.getViewport().setBackground(SAND_LIGHT);
        p.add(scroll, gbc);

        // Answer label
        gbc.gridy   = 2;
        gbc.weighty = 0;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        final JLabel aTag = new JLabel("YOUR ANSWER");
        aTag.setFont(MONO_BLD);
        aTag.setForeground(TEXT_MID);
        p.add(aTag, gbc);

        // Answer field
        gbc.gridy = 3;
        p.add(myAnswerField, gbc);

        // Submit button
        gbc.gridy = 4;
        p.add(mySubmitButton, gbc);

        // Feedback label
        gbc.gridy = 5;
        p.add(myFeedbackLabel, gbc);

        return p;
    }

    private JTextArea buildQuestionArea() {
        final JTextArea ta = new JTextArea(5, 20);
        ta.setFont(MONO_MD);
        ta.setForeground(TEXT_DARK);
        ta.setBackground(SAND_LIGHT);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new EmptyBorder(6, 8, 6, 8));
        ta.setText("Choose your character below, then press START to begin!");
        return ta;
    }

    private JTextField buildAnswerField() {
        final JTextField tf = new JTextField();
        tf.setFont(MONO_MD);
        tf.setForeground(TEXT_DARK);
        tf.setBackground(SAND_LIGHT);
        tf.setCaretColor(BROWN);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_BORDER, 3),
                new EmptyBorder(5, 8, 5, 8)));
        tf.addActionListener(e -> submitAnswer());
        return tf;
    }

    private JButton buildSubmitButton() {
        final JButton btn = new JButton("[ SUBMIT ]");
        btn.setFont(MONO_BLD);
        btn.setForeground(new Color(240, 248, 224));
        btn.setBackground(GREEN_BTN);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> submitAnswer());
        return btn;
    }

    // ── D-pad ────────────────────────────────────────────────────────────────

    private JPanel buildDpadPanel() {
        final JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setBackground(GOLD);
        wrap.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 3));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 1; gbc.gridy = 0; wrap.add(dpadBtn(DPAD_UP,    "north"), gbc);
        gbc.gridx = 0; gbc.gridy = 1; wrap.add(dpadBtn(DPAD_LEFT,  "west"),  gbc);
        gbc.gridx = 2; gbc.gridy = 1; wrap.add(dpadBtn(DPAD_RIGHT, "east"),  gbc);
        gbc.gridx = 1; gbc.gridy = 2; wrap.add(dpadBtn(DPAD_DOWN,  "south"), gbc);

        final JPanel nub = new JPanel();
        nub.setBackground(SAND_DARK);
        nub.setPreferredSize(new Dimension(62, 62));
        nub.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));
        gbc.gridx = 1; gbc.gridy = 1;
        wrap.add(nub, gbc);

        return wrap;
    }

    /**
     * Creates a d-pad button using an image.
     *
     * @param theImagePath path to the arrow image
     * @param theDir       direction string for movement
     * @return the styled button
     */
    private JButton dpadBtn(final String theImagePath, final String theDir) {
        final ImageIcon icon = new ImageIcon(
                new ImageIcon(theImagePath).getImage()
                        .getScaledInstance(55, 55, Image.SCALE_FAST));
        final JButton btn = new JButton(icon);
        btn.setBackground(SAND);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorder(BorderFactory.createLineBorder(GOLD_DARK, 2));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(62, 62));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> handleMove(theDir));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(final MouseEvent e) { btn.setBackground(SAND_LIGHT); }
            public void mouseExited(final MouseEvent e)  { btn.setBackground(SAND); }
        });
        return btn;
    }

    // ── Character select bar ─────────────────────────────────────────────────

    private JPanel buildCharBar() {
        final JPanel bar = new JPanel(new BorderLayout(0, 6));
        bar.setBackground(GOLD);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_BORDER, 3),
                new EmptyBorder(10, 12, 10, 12)));

        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
        title.setFont(MONO_BLD);
        title.setForeground(BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        bar.add(title, BorderLayout.NORTH);

        final JPanel cards = new JPanel(new GridLayout(1, 3, 12, 0));
        cards.setBackground(GOLD);
        for (int i = 0; i < 3; i++) {
            cards.add(buildCharCard(i));
        }
        bar.add(cards, BorderLayout.CENTER);

        final JButton startBtn = new JButton("[ START GAME ]");
        startBtn.setFont(MONO_BLD);
        startBtn.setForeground(Color.WHITE);
        startBtn.setBackground(GREEN_BTN);
        startBtn.setOpaque(true);
        startBtn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
        startBtn.setFocusPainted(false);
        startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startBtn.addActionListener(e -> startGame());

        final JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrap.setBackground(GOLD);
        btnWrap.add(startBtn);
        bar.add(btnWrap, BorderLayout.SOUTH);

        return bar;
    }

    /**
     * Builds a character card showing only the image.
     *
     * @param theIndex the character index
     * @return the card panel
     */
    private JPanel buildCharCard(final int theIndex) {
        final JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(theIndex == mySelectedChar ? SAND_LIGHT : SAND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        theIndex == mySelectedChar ? GOLD_BORDER : SAND_DARK, 3),
                new EmptyBorder(8, 8, 8, 8)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(110, 110));

        final JLabel sprite = new JLabel(myCharIcons[theIndex]);
        sprite.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(sprite);

        final int idx = theIndex;
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                selectCharacter(idx);
            }
            public void mouseEntered(final MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GOLD_DARK, 3),
                        new EmptyBorder(8, 8, 8, 8)));
            }
            public void mouseExited(final MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                idx == mySelectedChar ? GOLD_BORDER : SAND_DARK, 3),
                        new EmptyBorder(8, 8, 8, 8)));
            }
        });

        myCharCards[theIndex] = card;
        return card;
    }

    /**
     * Selects a character and refreshes the character bar.
     *
     * @param theIndex the index of the chosen character
     */
    private void selectCharacter(final int theIndex) {
        mySelectedChar = theIndex;
        refreshCharBar();
        updateGrid();
    }

    /**
     * Refreshes the character bar to show the updated selection.
     */
    private void refreshCharBar() {
        final JPanel bar = (JPanel) getComponent(1);
        bar.removeAll();

        final JLabel title = new JLabel("CHOOSE YOUR CHARACTER");
        title.setFont(MONO_BLD);
        title.setForeground(BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        bar.add(title, BorderLayout.NORTH);

        final JPanel cards = new JPanel(new GridLayout(1, 3, 12, 0));
        cards.setBackground(GOLD);
        for (int i = 0; i < 3; i++) {
            cards.add(buildCharCard(i));
        }
        bar.add(cards, BorderLayout.CENTER);

        final JButton startBtn = new JButton(myGameStarted ? "[ RESTART ]" : "[ START GAME ]");
        startBtn.setFont(MONO_BLD);
        startBtn.setForeground(Color.WHITE);
        startBtn.setBackground(GREEN_BTN);
        startBtn.setOpaque(true);
        startBtn.setBorder(BorderFactory.createLineBorder(GREEN_BTN2, 3));
        startBtn.setFocusPainted(false);
        startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startBtn.addActionListener(e -> startGame());

        final JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrap.setBackground(GOLD);
        btnWrap.add(startBtn);
        bar.add(btnWrap, BorderLayout.SOUTH);

        bar.revalidate();
        bar.repaint();
    }

    /**
     * Starts the game after character selection.
     */
    private void startGame() {
        myGameStarted = true;
        myQuestionText.setText("You chose " + CHAR_NAMES[mySelectedChar]
                + "!\n\nUse the arrow pad to move between rooms.\n"
                + "Answer trivia questions correctly to unlock doors.");
        setFeedback("> quest begins! press an arrow to move.", TEXT_DARK);
    }

    // ── Game logic ───────────────────────────────────────────────────────────

    /**
     * Handles a move attempt in the given direction.
     *
     * @param theDirection the direction string
     */
    private void handleMove(final String theDirection) {
        if (!myGameStarted) {
            setFeedback("> choose your character and press START first!", RED_BDR);
            return;
        }

        final Room current = myMaze.getCurrentRoom();

        if (!current.hasDoor(theDirection)) {
            setFeedback("> no path to the " + theDirection + ".", TEXT_MID);
            return;
        }
        if (current.isDoorLocked(theDirection)) {
            setFeedback("> that door is permanently locked!", RED_BDR);
            return;
        }

        myCurrentDoor = current.getDoor(theDirection);
        myCurrentDir  = theDirection;

        final Question question = myCurrentDoor.getQuestion();
        myQuestionText.setText(question.getQuestionText());
        myAnswerField.setText("");
        myAnswerField.requestFocus();
        setFeedback("> type your answer and press submit.", TEXT_MID);
    }

    /**
     * Submits the typed answer and evaluates it.
     */
    private void submitAnswer() {
        if (!myGameStarted) {
            setFeedback("> choose your character and press START first!", RED_BDR);
            return;
        }
        if (myCurrentDoor == null) {
            setFeedback("> press an arrow to pick a direction first.", TEXT_MID);
            return;
        }

        final String answer = myAnswerField.getText().trim();
        if (answer.isEmpty()) {
            setFeedback("> please type an answer first.", TEXT_MID);
            return;
        }

        final Question question = myCurrentDoor.getQuestion();
        final Room     current  = myMaze.getCurrentRoom();

        if (question.checkAnswer(answer)) {
            myMaze.move(myCurrentDir);
            myCorrectCount++;
            myRoomsCount++;
            myScoreLabel.setText("SCORE: " + String.format("%04d", myCorrectCount * 100));
            setFeedback("> correct! moved " + myCurrentDir + ".", GREEN_BTN);
            myQuestionText.setText("Correct!\n\nPress an arrow to move to the next room.");
            myAnswerField.setText("");
            myCurrentDoor = null;
            myCurrentDir  = null;
            updateGrid();
            checkGameState();
        } else {
            current.lockDoor(myCurrentDir);
            setFeedback("> wrong! door locked. answer was: " + question.getCorrectAnswer(), RED_BDR);
            myQuestionText.setText("Wrong answer!\n\nThat door is now permanently locked.\n"
                    + "Correct answer: " + question.getCorrectAnswer()
                    + "\n\nTry a different direction.");
            myAnswerField.setText("");
            myCurrentDoor = null;
            myCurrentDir  = null;
            updateGrid();
            if (myMaze.isGameOver()) {
                JOptionPane.showMessageDialog(this,
                        "All paths are blocked. Your quest has failed!",
                        "Game Over", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setFeedback(final String theText, final Color theColor) {
        myFeedbackLabel.setText("> " + theText);
        myFeedbackLabel.setForeground(theColor);
    }

    /**
     * Checks whether the player has reached the exit.
     */
    private void checkGameState() {
        if (myMaze.isGameWon()) {
            JOptionPane.showMessageDialog(this,
                    "You reached the exit! Quest complete!\nFinal Score: "
                            + String.format("%04d", myCorrectCount * 100),
                    "Victory!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Repaints the grid to reflect the current game state.
     */
    private void updateGrid() {
        final int curRow = myMaze.getCurrentRow();
        final int curCol = myMaze.getCurrentCol();

        for (int r = 0; r < mySize; r++) {
            for (int c = 0; c < mySize; c++) {
                final JPanel cell = myGridCells[r][c];
                cell.removeAll();

                final boolean isCurrent = (r == curRow && c == curCol);
                final boolean isExit    = (r == mySize - 1 && c == mySize - 1);

                if (isCurrent) {
                    cell.setBackground(SAND_LIGHT);
                    cell.setBorder(BorderFactory.createLineBorder(GOLD_BORDER, 4));
                    final JLabel icon = new JLabel(myCharIconsBig[mySelectedChar]);
                    cell.add(icon);
                } else if (isExit) {
                    cell.setBackground(BLUE_CELL);
                    cell.setBorder(BorderFactory.createLineBorder(BLUE_BDR, 3));
                    final JLabel icon = new JLabel("★");
                    icon.setFont(MONO_LG);
                    icon.setForeground(BLUE_BDR);
                    cell.add(icon);
                } else {
                    cell.setBackground(SAND);
                    cell.setBorder(BorderFactory.createLineBorder(SAND_DARK, 3));
                }

                cell.revalidate();
                cell.repaint();
            }
        }
    }

    // ── Static window builders ───────────────────────────────────────────────
    /**
     * Saves the current maze state.
     *
     * @param theFrame the parent frame for the message dialog
     */
    private void saveGame(final JFrame theFrame) {
        final boolean saved = GameMemento.saveMaze(myMaze);

        if (saved) {
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
     * Loads a previously saved maze state.
     *
     * @param theFrame the parent frame for the message dialog
     */
    private void loadGame(final JFrame theFrame) {
        final Maze loadedMaze = GameMemento.loadMaze();

        if (loadedMaze == null) {
            JOptionPane.showMessageDialog(
                    theFrame,
                    "No saved game could be loaded.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loadedMaze.getSize() != mySize) {
            JOptionPane.showMessageDialog(
                    theFrame,
                    "Saved maze size does not match this game window.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        myMaze = loadedMaze;
        myCurrentDoor = null;
        myCurrentDir = null;
        myGameStarted = true;

        myQuestionText.setText("Saved game loaded.\n\nUse the arrow pad to continue.");
        myAnswerField.setText("");
        setFeedback("> saved game loaded.", TEXT_DARK);
        updateGrid();

        JOptionPane.showMessageDialog(
                theFrame,
                "Game loaded successfully.",
                "Load Game",
                JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Creates and displays the main game window.
     */
    public static void createAndShowGUI() {
        Database.init();
        final QuestionFactory factory = new QuestionFactory("trivia.db");
        final Maze maze = new Maze(4);
        maze.initializeDoors(factory);

        final JFrame frame = new JFrame("Trivia Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(980, 780));
        frame.setBackground(SKY_BLUE);
        final MazeGUI gamePanel = new MazeGUI(maze);
        frame.setJMenuBar(buildMenuBar(frame, gamePanel));
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Builds the menu bar with File and Help menus.
     *
     * @param theFrame the parent frame
     * @return the completed menu bar
     */
    private static JMenuBar buildMenuBar(final JFrame theFrame,
                                     final MazeGUI theGamePanel) {
        final JMenuBar bar = new JMenuBar();
        bar.setBackground(GOLD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, GOLD_DARK));

        final JMenu fileMenu = styledMenu("File");
        final JMenuItem saveItem = styledItem("Save Game");
        saveItem.addActionListener(e -> theGamePanel.saveGame(theFrame));
        final JMenuItem loadItem = styledItem("Load Game");
        loadItem.addActionListener(e -> theGamePanel.loadGame(theFrame));
        final JMenuItem exitItem = styledItem("Exit");
        exitItem.addActionListener(e -> {
            final int choice = JOptionPane.showConfirmDialog(theFrame,
                    "Exit without saving?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) theFrame.dispose();
        });
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        final JMenu helpMenu = styledMenu("Help");
        final JMenuItem aboutItem = styledItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(theFrame,
                "Trivia Maze  v1.0\nTCSS 360 - Spring 2026\n\n"
                        + "Suhayr Hassan\nJinal Thummar\nRoman Pavlyshyn",
                "About", JOptionPane.INFORMATION_MESSAGE));
        final JMenuItem howItem = styledItem("Game Play Instructions");
        howItem.addActionListener(e -> JOptionPane.showMessageDialog(theFrame,
                "1. Choose your character at the bottom.\n"
                        + "2. Press START GAME.\n"
                        + "3. Use the arrow pad to move between rooms.\n"
                        + "4. Answer trivia questions to unlock doors.\n"
                        + "5. Wrong answer = door locked permanently.\n"
                        + "6. Reach the exit (★) to win!\n"
                        + "7. If all paths are blocked, game over.",
                "How to Play", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        helpMenu.add(howItem);

        bar.add(fileMenu);
        bar.add(helpMenu);
        return bar;
    }

    private static JMenu styledMenu(final String theText) {
        final JMenu m = new JMenu(theText);
        m.setFont(MONO_BLD);
        m.setForeground(BROWN);
        return m;
    }

    private static JMenuItem styledItem(final String theText) {
        final JMenuItem item = new JMenuItem(theText);
        item.setFont(MONO_MD);
        item.setBackground(GOLD);
        item.setForeground(TEXT_DARK);
        return item;
    }

    /**
     * Entry point to launch the Trivia Maze GUI.
     *
     * @param theArgs command-line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        SwingUtilities.invokeLater(MazeGUI::createAndShowGUI);
    }
}