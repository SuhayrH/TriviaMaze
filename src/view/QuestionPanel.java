/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * QuestionPanel displays the trivia question, answer input field,
 * submit and hint buttons, a countdown timer, and feedback text.
 * Callbacks are used to notify MazeGUI when the player submits
 * an answer or requests a hint.
 *
 * @author Suhayr Hassan
 * @version 24 May 2026
 */
public class QuestionPanel extends JPanel {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Sand light color for text area background. */
    private static final Color SAND_LIGHT = new Color(250, 238, 200);

    /** Dark sand color for borders. */
    private static final Color SAND_DARK = new Color(212, 184, 112);

    /** Gold color for panel background. */
    private static final Color GOLD = new Color(240, 216, 152);

    /** Dark gold color for panel border. */
    private static final Color GOLD_DARK = new Color(200, 160, 80);

    /** Gold border color for answer field. */
    private static final Color GOLD_BORDER = new Color(232, 160, 32);

    /** Brown color for caret. */
    private static final Color BROWN = new Color(90, 58, 26);

    /** Dark text color. */
    private static final Color TEXT_DARK = new Color(58, 42, 16);

    /** Medium text color for labels. */
    private static final Color TEXT_MID = new Color(138, 106, 48);

    /** Green button color. */
    private static final Color GREEN_BUTTON = new Color(74, 138, 32);

    /** Dark green button color for border. */
    private static final Color GREEN_BUTTON_DARK = new Color(58, 106, 24);

    /** Medium monospaced font. */
    private static final Font MONO_MEDIUM = new Font("Monospaced", Font.PLAIN, 12);

    /** Bold monospaced font. */
    private static final Font MONO_BOLD = new Font("Monospaced", Font.BOLD, 12);

    /** Small monospaced font for feedback. */
    private static final Font MONO_SMALL = new Font("Monospaced", Font.PLAIN, 10);

    /** Question text area. */
    private final JTextArea myQuestionText;

    /** Answer input field. */
    private final JTextField myAnswerField;

    /** Submit button. */
    private final JButton mySubmitButton;

    /** Hint button. */
    private final JButton myHintButton;

    /** Feedback label. */
    private final JLabel myFeedbackLabel;

    /** Question timer for countdown display. */
    private final QuestionTimer myQuestionTimer;

    /** Callback invoked when the player submits an answer. */
    private final Runnable myOnSubmit;

    /** Callback invoked when the player requests a hint. */
    private final Runnable myOnHint;

    /**
     * Constructs the QuestionPanel.
     *
     * @param theOnSubmit callback invoked when the submit button is clicked
     * @param theOnHint   callback invoked when the hint button is clicked
     */
    public QuestionPanel(final Runnable theOnSubmit, final Runnable theOnHint) {
        super(new GridBagLayout());

        myOnSubmit = theOnSubmit;
        myOnHint = theOnHint;
        myQuestionTimer = new QuestionTimer();

        myQuestionText = buildQuestionArea();
        myAnswerField = buildAnswerField();
        mySubmitButton = buildSubmitButton();
        myHintButton = buildHintButton();
        myFeedbackLabel = buildFeedbackLabel();

        setBackground(GOLD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD_DARK, 3),
                new EmptyBorder(10, 12, 10, 12)));

        buildLayout();
    }

    /**
     * Sets the question text displayed in the text area.
     *
     * @param theText the question text to display
     */
    public void setQuestionText(final String theText) {
        myQuestionText.setText(theText);
    }

    /**
     * Appends text to the existing question text area content.
     *
     * @param theText the text to append
     */
    public void appendQuestionText(final String theText) {
        myQuestionText.setText(myQuestionText.getText() + theText);
    }

    /**
     * Returns the current question text.
     *
     * @return the question text
     */
    public String getQuestionText() {
        return myQuestionText.getText();
    }

    /**
     * Returns the trimmed text from the answer field.
     *
     * @return the player's typed answer
     */
    public String getAnswer() {
        return myAnswerField.getText().trim();
    }

    /**
     * Clears the answer input field.
     */
    public void clearAnswer() {
        myAnswerField.setText("");
    }

    /**
     * Focuses the answer input field so the player can type immediately.
     */
    public void focusAnswerField() {
        myAnswerField.requestFocus();
    }

    /**
     * Sets the feedback label text and color.
     *
     * @param theText  the feedback message
     * @param theColor the text color
     */
    public void setFeedback(final String theText, final Color theColor) {
        myFeedbackLabel.setText("> " + theText);
        myFeedbackLabel.setForeground(theColor);
    }

    /**
     * Starts the question countdown timer with the given expiry callback.
     *
     * @param theOnExpire callback invoked when the timer reaches zero
     */
    public void startTimer(final Runnable theOnExpire) {
        myQuestionTimer.start(theOnExpire);
    }

    /**
     * Stops and resets the question countdown timer.
     */
    public void stopTimer() {
        myQuestionTimer.stop();
    }

    /**
     * Builds the layout of all components in the question panel.
     */
    private void buildLayout() {
        final GridBagConstraints constraints = new GridBagConstraints();
        final JLabel questionTag = new JLabel("QUESTION");
        final JLabel answerTag = new JLabel("YOUR ANSWER");
        final JScrollPane scroll = new JScrollPane(myQuestionText);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(4, 0, 4, 0);

        questionTag.setFont(MONO_BOLD);
        questionTag.setForeground(TEXT_MID);
        constraints.gridy = 0;
        add(questionTag, constraints);

        scroll.setBorder(BorderFactory.createLineBorder(SAND_DARK, 2));
        scroll.getViewport().setBackground(SAND_LIGHT);
        constraints.gridy = 1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(scroll, constraints);

        constraints.gridy = 2;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(myQuestionTimer.getLabel(), constraints);

        answerTag.setFont(MONO_BOLD);
        answerTag.setForeground(TEXT_MID);
        constraints.gridy = 3;
        add(answerTag, constraints);

        constraints.gridy = 4;
        add(myAnswerField, constraints);

        constraints.gridy = 5;
        add(buildButtonPanel(), constraints);

        constraints.gridy = 6;
        add(myFeedbackLabel, constraints);
    }

    /**
     * Builds the question text area.
     *
     * @return the styled text area
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
     * Builds the answer input field.
     *
     * @return the styled text field
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
        textField.addActionListener(theEvent -> myOnSubmit.run());

        return textField;
    }

    /**
     * Builds the submit button.
     *
     * @return the styled submit button
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
        button.addActionListener(theEvent -> myOnSubmit.run());

        return button;
    }

    /**
     * Builds the hint button.
     *
     * @return the styled hint button
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
        button.addActionListener(theEvent -> myOnHint.run());

        return button;
    }

    /**
     * Builds the feedback label shown below the buttons.
     *
     * @return the styled feedback label
     */
    private JLabel buildFeedbackLabel() {
        final JLabel label = new JLabel("> pick a character and press start");

        label.setFont(MONO_SMALL);
        label.setForeground(TEXT_MID);

        return label;
    }

    /**
     * Builds the panel containing the submit and hint buttons side by side.
     *
     * @return the button panel
     */
    private JPanel buildButtonPanel() {
        final JPanel panel = new JPanel(new GridLayout(1, 2, 6, 0));

        panel.setBackground(GOLD);
        panel.add(mySubmitButton);
        panel.add(myHintButton);

        return panel;
    }
}