/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 * Author Suhayr Hassan
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * The main game panel for the Trivia Maze game.
 * Displays the current room info, navigation controls,
 * and the trivia question area.
 *
 * @author Suhayr Hassan
 * @version 10 May 2026
 */
public class MazeGUI extends JPanel {

    /** The maze model. */
    private final Maze myMaze;

    /** Label showing the current room position. */
    private final JLabel myRoomLabel;

    /** Area displaying the current trivia question. */
    private final JTextArea myQuestionArea;

    /** Button to move north. */
    private final JButton myNorthButton;

    /** Button to move south. */
    private final JButton mySouthButton;

    /** Button to move east. */
    private final JButton myEastButton;

    /** Button to move west. */
    private final JButton myWestButton;

    /**
     * Constructs the MazeGUI panel with navigation and question display.
     *
     * @param theMaze the Maze model to display
     */
    public MazeGUI(final Maze theMaze) {
        super(new BorderLayout(10, 10));
        myMaze = theMaze;
        myRoomLabel = new JLabel("Current Room: (0, 0)", SwingConstants.CENTER);
        myQuestionArea = new JTextArea(5, 30);
        myNorthButton = new JButton("North");
        mySouthButton = new JButton("South");
        myEastButton  = new JButton("East");
        myWestButton  = new JButton("West");

        buildLayout();
        addListeners();
        updateRoomDisplay();
    }

    /**
     * Builds the panel layout with room info, question area, and nav buttons.
     */
    private void buildLayout() {
        myRoomLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(myRoomLabel, BorderLayout.NORTH);

        myQuestionArea.setEditable(false);
        myQuestionArea.setLineWrap(true);
        myQuestionArea.setWrapStyleWord(true);
        myQuestionArea.setText("Use the buttons below to navigate the maze.\n"
                + "Answer questions correctly to unlock doors!");
        myQuestionArea.setBorder(BorderFactory.createTitledBorder("Question"));
        add(myQuestionArea, BorderLayout.CENTER);

        final JPanel navPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        navPanel.setBorder(BorderFactory.createTitledBorder("Navigation"));
        navPanel.add(new JLabel());
        navPanel.add(myNorthButton);
        navPanel.add(new JLabel());
        navPanel.add(myWestButton);
        navPanel.add(new JLabel());
        navPanel.add(myEastButton);
        navPanel.add(new JLabel());
        navPanel.add(mySouthButton);
        navPanel.add(new JLabel());

        add(navPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds action listeners to the navigation buttons.
     */
    private void addListeners() {
        myNorthButton.addActionListener(e -> handleMove("north"));
        mySouthButton.addActionListener(e -> handleMove("south"));
        myEastButton.addActionListener(e -> handleMove("east"));
        myWestButton.addActionListener(e -> handleMove("west"));
    }

    /**
     * Handles a move attempt in the given direction.
     * Shows the question if the door exists, locks it on wrong answer,
     * or moves the player on correct answer.
     *
     * @param theDirection the direction to move
     */
    private void handleMove(final String theDirection) {
        final Room current = myMaze.getCurrentRoom();

        if (!current.hasDoor(theDirection)) {
            myQuestionArea.setText("There is no door to the " + theDirection + ".");
            return;
        }

        if (current.isDoorLocked(theDirection)) {
            myQuestionArea.setText("That door is permanently locked!");
            return;
        }

        final Door door = current.getDoor(theDirection);
        final Question question = door.getQuestion();
        final String userAnswer = JOptionPane.showInputDialog(
                this,
                question.getQuestionText(),
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE);

        if (userAnswer == null) {
            return;
        }

        if (question.checkAnswer(userAnswer.trim())) {
            myMaze.move(theDirection);
            myQuestionArea.setText("Correct! You moved " + theDirection + ".");
            updateRoomDisplay();
            checkGameState();
        } else {
            current.lockDoor(theDirection);
            myQuestionArea.setText("Wrong answer! That door is now permanently locked.\n"
                    + "The correct answer was: " + question.getCorrectAnswer());
            if (myMaze.isGameOver()) {
                JOptionPane.showMessageDialog(this,
                        "No more paths to the exit. Game over!",
                        "Game Over",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Checks if the game has been won and shows a message if so.
     */
    private void checkGameState() {
        if (myMaze.isGameWon()) {
            JOptionPane.showMessageDialog(this,
                    "Congratulations! You reached the exit. You win!",
                    "You Win!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Updates the room label to reflect the player's current position.
     */
    private void updateRoomDisplay() {
        myRoomLabel.setText("Current Room: ("
                + myMaze.getCurrentRow() + ", "
                + myMaze.getCurrentCol() + ")");
    }

    /**
     * Creates and displays the main game window.
     */
    private static void createAndShowGUI() {
        Database.init();
        final QuestionFactory factory = new QuestionFactory("trivia.db");
        final Maze maze = new Maze(4);
        maze.initializeDoors(factory);

        final JFrame frame = new JFrame("Trivia Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(buildMenuBar(frame));
        frame.add(new MazeGUI(maze), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Builds the menu bar with File and Help menus.
     *
     * @param theFrame the parent frame
     * @return the completed menu bar
     */
    private static JMenuBar buildMenuBar(final JFrame theFrame) {
        final JMenuBar menuBar = new JMenuBar();

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            final int choice = JOptionPane.showConfirmDialog(
                    theFrame, "Are you sure you want to exit?",
                    "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                theFrame.dispose();
            }
        });
        fileMenu.add(exitItem);

        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                theFrame,
                "Trivia Maze\nVersion 1.0\n\nSuhayr Hassan\nJinal Thummar\nRoman Pavlyshyn",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
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