/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import controller.GameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import model.Database;
import model.Maze;
import model.Question;
import model.QuestionFactory;
import model.Room;

/**
 * The main game panel for the Trivia Maze game.
 * Wires together MazePanel, QuestionPanel, DpadPanel, and CharacterPanel.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class MazeGUI extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * No selected character sentinel value.
     */
    private static final int NO_SELECTED_CHARACTER = -1;

    /**
     * Default maze size.
     */
    private static final int MAZE_SIZE = 4;

    /**
     * Points awarded per correct answer.
     */
    private static final int POINTS_PER_CORRECT_ANSWER = 100;

    /**
     * Character icon size used in the maze grid.
     */
    private static final int GRID_CHARACTER_ICON_SIZE = 48;

    /**
     * Main window width.
     */
    private static final int WINDOW_WIDTH = 980;

    /**
     * Main window height.
     */
    private static final int WINDOW_HEIGHT = 780;

    /**
     * Locked room background color.
     */
    private static final Color LOCKED_ROOM = new Color(200, 80, 80);

    /**
     * Locked room border color.
     */
    private static final Color LOCKED_ROOM_BORDER = new Color(140, 30, 30);

    /**
     * Character image paths.
     */
    private static final String[] CHARACTER_IMAGES = {
            "src/sprites/warrior.png",
            "src/sprites/mage.png",
            "src/sprites/rogue.png"
    };

    /**
     * North direction.
     */
    private static final String NORTH = "north";

    /**
     * South direction.
     */
    private static final String SOUTH = "south";

    /**
     * East direction.
     */
    private static final String EAST = "east";

    /**
     * West direction.
     */
    private static final String WEST = "west";

    /**
     * The maze model.
     */
    private Maze myMaze;

    /**
     * The game controller.
     */
    private final GameController myController;

    /**
     * The maze size.
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
     * Whether the current game session has ended.
     */
    private boolean myGameEnded;

    /**
     * Correct answer count.
     */
    private int myCorrectCount;

    /**
     * Character icons for maze grid.
     */
    private final ImageIcon[] myCharacterGridIcons;

    /**
     * The score label.
     */
    private final JLabel myScoreLabel;

    /**
     * The room info label.
     */
    private final JLabel myRoomInfoLabel;

    /**
     * The maze grid panel.
     */
    private final MazePanel myMazePanel;

    /**
     * The question panel.
     */
    private final QuestionPanel myQuestionPanel;

    /**
     * The d-pad panel.
     */
    private final DpadPanel myDpadPanel;

    /**
     * The character selection panel.
     */
    private final CharacterPanel myCharacterPanel;

    /**
     * Constructs the maze GUI panel.
     *
     * @param theMaze the maze model
     */
    public MazeGUI(final Maze theMaze) {
        super(new BorderLayout());

        myMaze = theMaze;
        myController = new GameController(theMaze);
        mySize = theMaze.getSize();
        mySelectedCharacter = NO_SELECTED_CHARACTER;
        myGameStarted = false;
        myGameEnded = false;
        myCorrectCount = 0;
        myCharacterGridIcons = new ImageIcon[CHARACTER_IMAGES.length];

        setBackground(GameColors.SKY_BLUE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        loadCharacterGridIcons();

        myScoreLabel = buildScoreLabel();
        myRoomInfoLabel = buildRoomInfoLabel();

        myMazePanel = new MazePanel(myMaze, myScoreLabel, myRoomInfoLabel);
        myQuestionPanel = new QuestionPanel(this::submitAnswer, this::showHint);
        myDpadPanel = new DpadPanel(this::handleMove);
        myCharacterPanel = new CharacterPanel(this::selectCharacter, this::startGame);

        add(buildMainPanel(), BorderLayout.CENTER);
        add(myCharacterPanel, BorderLayout.SOUTH);

        updateGrid();
    }

    /**
     * Loads and scales character icons for the maze grid.
     */
    private void loadCharacterGridIcons() {
        for (int i = 0; i < CHARACTER_IMAGES.length; i++) {
            final ImageIcon rawIcon = new ImageIcon(CHARACTER_IMAGES[i]);

            myCharacterGridIcons[i] = new ImageIcon(rawIcon.getImage().getScaledInstance(
                    GRID_CHARACTER_ICON_SIZE,
                    GRID_CHARACTER_ICON_SIZE,
                    Image.SCALE_FAST));
        }
    }

    /**
     * Builds the score label.
     *
     * @return the score label
     */
    private JLabel buildScoreLabel() {
        final JLabel label = new JLabel("SCORE: 0000");

        label.setFont(GameColors.MONO_BOLD);
        label.setForeground(GameColors.BROWN);
        label.setBackground(GameColors.GOLD);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3),
                new EmptyBorder(2, 8, 2, 8)));

        return label;
    }

    /**
     * Builds the room info label.
     *
     * @return the room info label
     */
    private JLabel buildRoomInfoLabel() {
        final JLabel label = new JLabel("ROOM: (1, 1) | VISITED: YES");

        label.setFont(GameColors.MONO_BOLD);
        label.setForeground(GameColors.BROWN);
        label.setBackground(GameColors.GOLD);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3),
                new EmptyBorder(2, 8, 2, 8)));

        return label;
    }

    /**
     * Builds the main panel containing the maze, question, and d-pad panels.
     *
     * @return the main panel
     */
    private JPanel buildMainPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(GameColors.SKY_BLUE);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(4, 4, 4, 4);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weightx = 0.65;
        constraints.weighty = 1.0;
        panel.add(myMazePanel, constraints);

        constraints.gridx = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.35;

        constraints.gridy = 0;
        constraints.weighty = 0.65;
        panel.add(myQuestionPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.35;
        panel.add(myDpadPanel, constraints);

        return panel;
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
        frame.setBackground(GameColors.SKY_BLUE);
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

        bar.setBackground(GameColors.GOLD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, GameColors.GOLD_DARK));

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

        menu.setFont(GameColors.MONO_BOLD);
        menu.setForeground(GameColors.BROWN);

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

        item.setFont(GameColors.MONO_MEDIUM);
        item.setBackground(GameColors.GOLD);
        item.setForeground(GameColors.TEXT_DARK);

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
     * Selects a character.
     *
     * @param theIndex the selected character index
     */
    private void selectCharacter(final int theIndex) {
        mySelectedCharacter = theIndex;
        myController.selectCharacter(theIndex);
        myCharacterPanel.setSelectedCharacter(theIndex);
        myCharacterPanel.refresh();
        updateGrid();
        setFeedback("selected " + CharacterPanel.getCharacterName(theIndex) + ".",
                GameColors.TEXT_DARK);
        myQuestionPanel.getQuestionText().setText("You selected "
                + CharacterPanel.getCharacterName(theIndex)
                + ".\n\nPress START GAME to begin.");
    }

    /**
     * Starts the game.
     */
    private void startGame() {
        if (!myController.startGame()) {
            myQuestionPanel.getQuestionText().setText(
                    "Please choose a character before starting the game.");
            setFeedback("choose a character first.", GameColors.RED_BORDER);
        } else if (myGameEnded) {
            resetGame();
        } else {
            SoundManager.playStartGame();
            myGameStarted = true;
            myGameEnded = false;
            myQuestionPanel.getQuestionText().setText("You chose "
                    + CharacterPanel.getCharacterName(mySelectedCharacter)
                    + "!\n\nUse the arrow pad to move between rooms.\n"
                    + "Answer trivia questions correctly to unlock doors.");
            setFeedback("quest begins! press an arrow to move.", GameColors.TEXT_DARK);
            myCharacterPanel.setGameStarted(true);
            updateGrid();
        }
    }

    /**
     * Handles a movement attempt.
     *
     * @param theDirection the movement direction
     */
    private void handleMove(final String theDirection) {
        if (myGameEnded) {
            setFeedback("game is over. start a new game or exit.", GameColors.RED_BORDER);
        } else if (!myGameStarted) {
            setFeedback("choose your character and press START first!", GameColors.RED_BORDER);
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
        if (!myController.currentRoomHasDoor(theDirection)) {
            setFeedback("no path to the " + theDirection + ".", GameColors.TEXT_MID);
        } else if (myController.isCurrentRoomDoorLocked(theDirection)) {
            setFeedback("that door is permanently locked!", GameColors.RED_BORDER);
        } else {
            myController.chooseDoor(theDirection);
            showCurrentQuestion();
        }
    }

    /**
     * Shows the current door question.
     */
    private void showCurrentQuestion() {
        final Question question = myController.getCurrentQuestion();

        if (question != null) {
            myQuestionPanel.getQuestionText().setText(question.getQuestionText());
            myQuestionPanel.getAnswerField().setText("");
            myQuestionPanel.getAnswerField().requestFocus();
            setFeedback("type your answer and press submit.", GameColors.TEXT_MID);
        }
    }

    /**
     * Shows a hint for the current question.
     */
    private void showHint() {
        if (myGameEnded) {
            setFeedback("game is over. start a new game or exit.", GameColors.RED_BORDER);
        } else if (!myController.isGameStarted()) {
            setFeedback("choose your character and press START first!", GameColors.RED_BORDER);
        } else if (!myController.hasCurrentQuestion()) {
            setFeedback("press an arrow to pick a question first.", GameColors.TEXT_MID);
        } else if (myController.isHintUsedForQuestion()) {
            setFeedback("hint already used for this question.", GameColors.TEXT_MID);
        } else {
            SoundManager.playHint();
            myQuestionPanel.getQuestionText().setText(
                    myQuestionPanel.getQuestionText().getText()
                            + "\n\n" + myController.useHint());
            setFeedback("hint used.", GameColors.TEXT_MID);
        }
    }

    /**
     * Submits the typed answer.
     */
    private void submitAnswer() {
        if (myGameEnded) {
            setFeedback("game is over. start a new game or exit.", GameColors.RED_BORDER);
        } else if (!myGameStarted) {
            setFeedback("choose your character and press START first!", GameColors.RED_BORDER);
        } else if (!myController.hasCurrentQuestion()) {
            setFeedback("press an arrow to pick a direction first.", GameColors.TEXT_MID);
        } else if (myQuestionPanel.getAnswerField().getText().trim().isEmpty()) {
            setFeedback("please type an answer first.", GameColors.TEXT_MID);
        } else {
            checkSubmittedAnswer(myQuestionPanel.getAnswerField().getText().trim());
        }
    }

    /**
     * Checks the submitted answer.
     *
     * @param theAnswer the submitted answer
     */
    private void checkSubmittedAnswer(final String theAnswer) {
        if (myController.checkAnswer(theAnswer)) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer();
        }
    }

    /**
     * Handles a correct answer.
     */
    private void handleCorrectAnswer() {
        final String direction = myController.getCurrentDirection();

        SoundManager.playCorrectAnswer();
        myController.moveThroughCurrentDoor();
        myCorrectCount = myController.getCorrectCount();
        myScoreLabel.setText("SCORE: "
                + String.format("%04d", myCorrectCount * POINTS_PER_CORRECT_ANSWER));
        setFeedback("correct! moved " + direction + ".", GameColors.GREEN_BUTTON);
        myQuestionPanel.getQuestionText().setText(
                "Correct!\n\nPress an arrow to move to the next room.");
        clearCurrentQuestion();
        updateGrid();
        checkGameState();
    }

    /**
     * Handles a wrong answer.
     */
    private void handleWrongAnswer() {
        final String correctAnswer = myController.getCurrentCorrectAnswer();

        SoundManager.playWrongAnswer();
        myController.lockCurrentDoor();
        setFeedback("wrong! door locked. answer was: " + correctAnswer, GameColors.RED_BORDER);
        myQuestionPanel.getQuestionText().setText(
                "Wrong answer!\n\nThat door is now permanently locked.\n"
                        + "Correct answer: " + correctAnswer
                        + "\n\nTry a different direction.");
        clearCurrentQuestion();
        updateGrid();

        if (myController.isGameOver()) {
            SoundManager.playGameOver();
            endGame(false);
        }
    }

    /**
     * Clears the current question state.
     */
    private void clearCurrentQuestion() {
        myQuestionPanel.getAnswerField().setText("");
        myController.clearCurrentQuestion();
    }

    /**
     * Sets feedback text and color.
     *
     * @param theText the feedback text
     * @param theColor the feedback color
     */
    private void setFeedback(final String theText, final Color theColor) {
        myQuestionPanel.getFeedbackLabel().setText("> " + theText);
        myQuestionPanel.getFeedbackLabel().setForeground(theColor);
    }

    /**
     * Checks the game state for victory.
     */
    private void checkGameState() {
        if (myController.isGameWon()) {
            SoundManager.playWin();
            endGame(true);
        }
    }

    /**
     * Ends the current game session.
     *
     * @param theWon whether the player won
     */
    private void endGame(final boolean theWon) {
        myGameEnded = true;
        myQuestionPanel.getAnswerField().setText("");
        myController.clearCurrentQuestion();

        if (theWon) {
            setFeedback("victory! choose new game or exit.", GameColors.GREEN_BUTTON);
            myQuestionPanel.getQuestionText().setText("You reached the exit!\n\nFinal Score: "
                    + String.format("%04d", myCorrectCount * POINTS_PER_CORRECT_ANSWER));
        } else {
            setFeedback("game over! choose new game or exit.", GameColors.RED_BORDER);
            myQuestionPanel.getQuestionText().setText(
                    "All paths are blocked.\n\nYour quest has failed.");
        }

        showEndGameDialog(theWon);
    }

    /**
     * Shows the end-game dialog.
     *
     * @param theWon whether the player won
     */
    private void showEndGameDialog(final boolean theWon) {
        final String title = theWon ? "Victory!" : "Game Over";
        final String message;

        if (theWon) {
            message = "You reached the exit!\nFinal Score: "
                    + String.format("%04d", myCorrectCount * POINTS_PER_CORRECT_ANSWER)
                    + "\n\nWhat would you like to do?";
        } else {
            message = "All paths are blocked. Your quest has failed!"
                    + "\n\nWhat would you like to do?";
        }

        final Object[] options = {"New Game", "Exit"};
        final int choice = JOptionPane.showOptionDialog(
                this,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                theWon ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else if (choice == JOptionPane.NO_OPTION) {
            final java.awt.Window window = SwingUtilities.getWindowAncestor(this);

            if (window != null) {
                window.dispose();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Resets the game with a fresh maze.
     */
    private void resetGame() {
        Database.init();

        final QuestionFactory factory = new QuestionFactory("trivia.db");
        final Maze newMaze = new Maze(mySize);
        newMaze.initializeDoors(factory);

        myMaze = newMaze;
        myController.resetForNewMaze(newMaze);
        myController.selectCharacter(mySelectedCharacter);
        myGameStarted = true;
        myGameEnded = false;
        myCorrectCount = 0;

        myScoreLabel.setText("SCORE: 0000");
        myQuestionPanel.getAnswerField().setText("");
        myQuestionPanel.getQuestionText().setText(
                "New game started!\n\nUse the arrow pad to move between rooms.");
        setFeedback("new quest begins!", GameColors.TEXT_DARK);
        myCharacterPanel.setGameStarted(true);
        updateGrid();
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

        updateRoomInfoLabel(currentRow, currentCol);
        updateDpadButtons();
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
        final JPanel cell = myMazePanel.getCell(theRow, theCol);
        final boolean isCurrent = theRow == theCurrentRow && theCol == theCurrentCol;
        final boolean isExit = theRow == mySize - 1 && theCol == mySize - 1;

        cell.removeAll();

        if (isCurrent) {
            updateCurrentCell(cell);
        } else if (isExit) {
            updateExitCell(cell);
        } else {
            updateEmptyCell(cell, theRow, theCol);
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
        theCell.setBackground(GameColors.SAND_LIGHT);
        theCell.setBorder(BorderFactory.createLineBorder(GameColors.GOLD_BORDER, 4));

        if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
            final JLabel placeholder = new JLabel("?");

            placeholder.setFont(GameColors.MONO_LARGE);
            placeholder.setForeground(GameColors.BROWN);
            theCell.add(placeholder);
        } else {
            theCell.add(new JLabel(myCharacterGridIcons[mySelectedCharacter]));
        }
    }

    /**
     * Updates the exit cell.
     *
     * @param theCell the exit cell
     */
    private void updateExitCell(final JPanel theCell) {
        final JLabel icon = new JLabel("*");

        theCell.setBackground(GameColors.BLUE_CELL);
        theCell.setBorder(BorderFactory.createLineBorder(GameColors.BLUE_BORDER, 3));
        icon.setFont(GameColors.MONO_LARGE);
        icon.setForeground(GameColors.BLUE_BORDER);
        theCell.add(icon);
    }

    /**
     * Updates an empty cell, showing red with a locked icon if the connecting
     * door from the current room to this cell is locked.
     *
     * @param theCell the empty cell
     * @param theRow the cell row
     * @param theCol the cell column
     */
    private void updateEmptyCell(final JPanel theCell,
                                 final int theRow,
                                 final int theCol) {
        if (isConnectingDoorLocked(theRow, theCol)) {
            final ImageIcon lockedIcon = new ImageIcon(
                    new ImageIcon("src/sprites/locked.png").getImage().getScaledInstance(
                            GRID_CHARACTER_ICON_SIZE,
                            GRID_CHARACTER_ICON_SIZE,
                            Image.SCALE_FAST));
            theCell.setBackground(LOCKED_ROOM);
            theCell.setBorder(BorderFactory.createLineBorder(LOCKED_ROOM_BORDER, 3));
            theCell.add(new JLabel(lockedIcon));
        } else {
            theCell.setBackground(GameColors.SAND);
            theCell.setBorder(BorderFactory.createLineBorder(GameColors.SAND_DARK, 3));
        }
    }

    /**
     * Returns true if the door between the current room and the given cell is locked.
     *
     * @param theRow the cell row
     * @param theCol the cell column
     * @return true if the connecting door is locked
     */
    private boolean isConnectingDoorLocked(final int theRow, final int theCol) {
        final int currentRow = myMaze.getCurrentRow();
        final int currentCol = myMaze.getCurrentCol();
        final Room currentRoom = myMaze.getCurrentRoom();
        boolean locked = false;

        if (theRow == currentRow - 1 && theCol == currentCol) {
            locked = currentRoom.hasDoor(NORTH) && currentRoom.isDoorLocked(NORTH);
        } else if (theRow == currentRow + 1 && theCol == currentCol) {
            locked = currentRoom.hasDoor(SOUTH) && currentRoom.isDoorLocked(SOUTH);
        } else if (theRow == currentRow && theCol == currentCol + 1) {
            locked = currentRoom.hasDoor(EAST) && currentRoom.isDoorLocked(EAST);
        } else if (theRow == currentRow && theCol == currentCol - 1) {
            locked = currentRoom.hasDoor(WEST) && currentRoom.isDoorLocked(WEST);
        }

        return locked;
    }

    /**
     * Updates the room info label.
     *
     * @param theRow the current row
     * @param theCol the current column
     */
    private void updateRoomInfoLabel(final int theRow, final int theCol) {
        final String visitedText = myMaze.getRoom(theRow, theCol).isVisited() ? "YES" : "NO";

        myRoomInfoLabel.setText("ROOM: ("
                + (theRow + 1) + ", " + (theCol + 1)
                + ") | VISITED: " + visitedText);
    }

    /**
     * Updates d-pad button enabled states.
     */
    private void updateDpadButtons() {
        updateDpadButton(myDpadPanel.getNorthButton(), NORTH);
        updateDpadButton(myDpadPanel.getSouthButton(), SOUTH);
        updateDpadButton(myDpadPanel.getEastButton(), EAST);
        updateDpadButton(myDpadPanel.getWestButton(), WEST);
    }

    /**
     * Updates one d-pad button.
     *
     * @param theButton the button to update
     * @param theDirection the direction
     */
    private void updateDpadButton(final JButton theButton, final String theDirection) {
        if (theButton != null) {
            final boolean canMove = myGameStarted
                    && !myGameEnded
                    && myController.currentRoomHasDoor(theDirection)
                    && !myController.isCurrentRoomDoorLocked(theDirection);

            theButton.setEnabled(canMove);
            theButton.setCursor(Cursor.getPredefinedCursor(
                    canMove ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));

            if (canMove) {
                theButton.setBackground(GameColors.SAND);
                theButton.setBorder(BorderFactory.createLineBorder(GameColors.GOLD_DARK, 2));
            } else {
                theButton.setBackground(GameColors.SAND_DARK);
                theButton.setBorder(BorderFactory.createLineBorder(GameColors.TEXT_MID, 2));
            }
        }
    }

    /**
     * Saves the current game.
     *
     * @param theFrame the parent frame
     */
    private void saveGame(final JFrame theFrame) {
        final boolean saved = myController.saveGame();

        if (saved) {
            SoundManager.playSaveGame();
            JOptionPane.showMessageDialog(theFrame,
                    "Game saved successfully.", "Save Game",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(theFrame,
                    "Game could not be saved.", "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads a saved game.
     *
     * @param theFrame the parent frame
     */
    private void loadGame(final JFrame theFrame) {
        final Maze loadedMaze = myController.loadGame();

        if (loadedMaze == null) {
            JOptionPane.showMessageDialog(theFrame,
                    "No saved game could be loaded.", "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (loadedMaze.getSize() != mySize) {
            JOptionPane.showMessageDialog(theFrame,
                    "Saved maze size does not match this game window.", "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            myMaze = loadedMaze;
            myController.loadMazeState(loadedMaze);
            SoundManager.playLoadGame();
            myGameStarted = true;
            myGameEnded = false;
            myCorrectCount = myController.getCorrectCount();
            myScoreLabel.setText("SCORE: 0000");

            if (mySelectedCharacter == NO_SELECTED_CHARACTER) {
                mySelectedCharacter = 0;
            }

            myController.selectCharacter(mySelectedCharacter);
            myQuestionPanel.getQuestionText().setText(
                    "Saved game loaded.\n\nUse the arrow pad to continue.");
            myQuestionPanel.getAnswerField().setText("");
            setFeedback("saved game loaded.", GameColors.TEXT_DARK);
            myCharacterPanel.setGameStarted(true);
            updateGrid();

            JOptionPane.showMessageDialog(theFrame,
                    "Game loaded successfully.", "Load Game",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}